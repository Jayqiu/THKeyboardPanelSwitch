package com.threehalf.kpswitch.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.threehalf.kpswitch.utils.KeyboardUtils

/**
 * @author: JayQiu
 * @date: 2022/1/13
 * @description:
 */
class THPanelSwitchFrameLayout @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, def: Int = 0) : FrameLayout(context, attr, def) {
    var mHeight = -1
    var mIsHide = false
    var mIsKeyboardShowing = false


    fun setVisibility(isVisible: Boolean) {
        var height = mHeight
        if (isVisible) {
            if (height == -1) {
                height = KeyboardUtils.getDefKeyboardHeight(context)
            }else {
                KeyboardUtils.setDefKeyboardHeight(context,height)
            }
            mIsHide = false
            visibility = VISIBLE
        } else {
            if (mIsHide) return
            height = 0
            visibility = GONE
        }
        refreshHeight(this, height)
    }

    private fun refreshHeight(view: View, aimHeight: Int) {
        var layoutParams = view.layoutParams
        if (layoutParams == null) {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, aimHeight)
            view.layoutParams = layoutParams
        } else {
            layoutParams.height = aimHeight
            view.requestLayout()
        }
    }
}