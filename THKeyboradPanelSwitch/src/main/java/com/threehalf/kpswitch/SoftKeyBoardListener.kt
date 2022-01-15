package com.threehalf.kpswitch

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.threehalf.kpswitch.listener.OnSoftKeyBoardChangeListener

/**
 * @author: JayQiu
 * @date: 2022/1/13
 * @description:
 */
class SoftKeyBoardListener {
    companion object {
        fun setListener(context: Context, onSoftKeyBoardChangeListener: OnSoftKeyBoardChangeListener): SoftKeyBoardListener {
            val softKeyBoardListener = SoftKeyBoardListener(context)
            softKeyBoardListener.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener)
            return softKeyBoardListener
        }
    }

    private var mContext: Context? = null
    private var onSoftKeyBoardChangeListener: OnSoftKeyBoardChangeListener? = null
    private var mOldHeight = -1
    var mScreenHeight = 0
    private var rootView: View? = null
    private var listener: OnGlobalLayoutListener? = null

    constructor(context: Context) {
        mContext = context
        softKeyBoardListener()
    }
    private fun setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener: OnSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener
    }


    private fun softKeyBoardListener() {
        rootView = (mContext as Activity).window.decorView //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        listener = OnGlobalLayoutListener {

            //获取当前根视图在屏幕上显示的大小
            val r = Rect()
            rootView?.getWindowVisibleDisplayFrame(r)
            if (mScreenHeight == 0) {
                mScreenHeight = r.bottom
            }
            val heightDiff = mScreenHeight - r.bottom
            if (mOldHeight != -1 && heightDiff != mOldHeight) {
                if (heightDiff > 0) {
                    onSoftKeyBoardChangeListener?.onKeyBoardShow(heightDiff)
                } else {
                    onSoftKeyBoardChangeListener?.onKeyBoardHide()
                }
            }
            mOldHeight = heightDiff
        }
        rootView?.viewTreeObserver?.addOnGlobalLayoutListener(listener)
        addLifeObServer(mContext as Activity)
    }

    private fun addLifeObServer(activity: Activity?) {
        if (activity is LifecycleOwner) {
            val owner = activity as LifecycleOwner
            owner.lifecycle.addObserver(LifecycleEventObserver { _: LifecycleOwner?, event: Lifecycle.Event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    rootView?.viewTreeObserver?.removeOnGlobalLayoutListener(listener)
                }
            })
        }
    }
}