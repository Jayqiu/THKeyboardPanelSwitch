package com.threehalf.kpswitch.utils

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.threehalf.kpswitch.entity.THPanelTriggerEntity
import com.threehalf.kpswitch.widget.THPanelSwitchFrameLayout
import com.threehalf.kpswitch.widget.THPanelSwitchRootFrameLayout

/**
 * @author: JayQiu
 * @date: 2022/1/13
 * @description:
 */
class THKPSwitchConflictUtils {
    companion object {
        fun attach(panelLayout: View, focusView: View, vararg subPanelAndTriggers: THPanelTriggerEntity) {
            subPanelAndTriggers.forEach {
                bindSubPanel(panelLayout, focusView, it, *subPanelAndTriggers)
            }
            focusView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    panelLayout.visibility = View.VISIBLE
                }
                false
            }
        }

        private fun bindSubPanel(panelLayout: View, focusView: View, thPanelTriggerEntity: THPanelTriggerEntity, vararg subPanelAndTriggers: THPanelTriggerEntity) {
            val triggerView: View = thPanelTriggerEntity.triggerView
            val boundTriggerSubPanelView: View = thPanelTriggerEntity.panelView  // 切换的面版
            triggerView.setOnClickListener {
                panelLayout as THPanelSwitchFrameLayout
                if (panelLayout.visibility == View.VISIBLE) {
                    if (boundTriggerSubPanelView.visibility == View.VISIBLE) {
                        if (panelLayout.mIsKeyboardShowing) { // 显示键盘
                            panelLayout.mIsHide = true
                            KeyboardUtils.hideKeyboard(panelLayout)
                        } else {
                            KeyboardUtils.showKeyboard(focusView)
                        }
                    } else {
                        panelLayout.mIsHide = true
                        KeyboardUtils.hideKeyboard(panelLayout)
                        showBoundTriggerSubPanel(boundTriggerSubPanelView, *subPanelAndTriggers)
                    }
                } else {
                    panelLayout.setVisibility(true)
                    showBoundTriggerSubPanel(boundTriggerSubPanelView, *subPanelAndTriggers)
                }
            }
        }


        private fun showBoundTriggerSubPanel(boundTriggerSubPanelView: View, vararg subPanelAndTriggers: THPanelTriggerEntity) {
            for (panelAndTrigger in subPanelAndTriggers) {
                if (panelAndTrigger.panelView !== boundTriggerSubPanelView) {
                    panelAndTrigger.panelView.visibility = View.GONE
                }
            }
            boundTriggerSubPanelView.visibility = View.VISIBLE
        }
    }


}