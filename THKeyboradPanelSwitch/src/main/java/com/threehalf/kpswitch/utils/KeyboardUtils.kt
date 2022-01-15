package com.threehalf.kpswitch.utils

import android.content.Context
import androidx.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author: JayQiu
 * @date: 2022/1/13
 * @description:
 */
class KeyboardUtils {
    companion object {
        private var mDefKeyboardHeight = -1
        private const val DEF_KEYBOARD_HEIGHT_WITH_DP = 300
        private const val EXTRA_DEF_KEYBOARD_HEIGHT = "DEF_KEYBOARD_HEIGHT"
        fun dip2px(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun showKeyboard(view: View) {
            view.requestFocus()
            val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, 0)
        }

        fun hideKeyboard(view: View) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun getDefKeyboardHeight(context: Context): Int {
            if (mDefKeyboardHeight < 0) {
                mDefKeyboardHeight = dip2px(context, DEF_KEYBOARD_HEIGHT_WITH_DP.toFloat())
            }
            val height = PreferenceManager.getDefaultSharedPreferences(context).getInt(EXTRA_DEF_KEYBOARD_HEIGHT, 0)
            return if (height > 0 && mDefKeyboardHeight != height) height else mDefKeyboardHeight.also { mDefKeyboardHeight = it }
        }

        fun setDefKeyboardHeight(context: Context, height: Int) {
            if (mDefKeyboardHeight != height) {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(EXTRA_DEF_KEYBOARD_HEIGHT, height).apply()
                mDefKeyboardHeight = height
            }
        }
    }
}