package com.threehalf.kpswitch.widget

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
import com.threehalf.kpswitch.SoftKeyBoardListener
import com.threehalf.kpswitch.listener.OnSoftKeyBoardChangeListener

/**
 * @author: JayQiu
 * @date: 2022/1/13
 * @description:
 */
class THPanelSwitchRootFrameLayout @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, def: Int = 0) : FrameLayout(context, attr, def), OnSoftKeyBoardChangeListener {
    private var mSoftKeyBoardListener: SoftKeyBoardListener = SoftKeyBoardListener.setListener(context, this)
    private var configurationChangedFlag = false
    private var maxParentHeight = 0
    private var panelView: THPanelSwitchFrameLayout? = null
    var mIsKeyboardShowing = false
    override fun onKeyBoardShow(height: Int) {
        mIsKeyboardShowing = true
        val view = getPanelLayout(this) as THPanelSwitchFrameLayout
        view?.mIsKeyboardShowing = mIsKeyboardShowing
        view.mHeight = height
        view?.setVisibility(true)
    }


    override fun onKeyBoardHide() {
        mIsKeyboardShowing = false
        val view = getPanelLayout(this) as THPanelSwitchFrameLayout
        view?.mIsKeyboardShowing = mIsKeyboardShowing
        view?.setVisibility(false)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (maxParentHeight == 0) {
            maxParentHeight = h
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (configurationChangedFlag) {
            configurationChangedFlag = false
            val r = Rect()
            (context as Activity).window.decorView.getWindowVisibleDisplayFrame(r)
            if (mSoftKeyBoardListener.mScreenHeight == 0) {
                mSoftKeyBoardListener.mScreenHeight = r.bottom
            }
            maxParentHeight = mSoftKeyBoardListener.mScreenHeight - r.bottom
        }

        if (maxParentHeight != 0) {
            val heightMode = MeasureSpec.getMode(heightMeasureSpec)
            val expandSpec = MeasureSpec.makeMeasureSpec(maxParentHeight, heightMode)
            super.onMeasure(widthMeasureSpec, expandSpec)
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        configurationChangedFlag = true
        mSoftKeyBoardListener.mScreenHeight = 0
    }

    private fun getPanelLayout(view: View): View? {
        if (panelView != null) return panelView
        if (view is THPanelSwitchFrameLayout) {
            panelView = view
            return panelView
        }
        if (view is ViewGroup) {
            view.forEachIndexed { index, _ ->
                val childView = (getPanelLayout(view.getChildAt(index)))
                if (childView is THPanelSwitchFrameLayout) {
                    panelView = childView
                    return panelView
                }
            }
        }
        return null
    }
}