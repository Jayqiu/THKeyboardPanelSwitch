package com.threehalf.keyboardswitch

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.threehalf.kpswitch.entity.THPanelTriggerEntity
import com.threehalf.kpswitch.utils.THKPSwitchConflictUtils
import com.threehalf.kpswitch.widget.THPanelSwitchFrameLayout
import com.threehalf.kpswitch.widget.THPanelSwitchRootFrameLayout

class MainActivity : AppCompatActivity() {
    private lateinit var mRootView: THPanelSwitchRootFrameLayout
    private lateinit var mTHPanelSwitchFrameLayout: THPanelSwitchFrameLayout
    private lateinit var mConstraintLayout: ConstraintLayout
    private lateinit var mConstraintLayoutIm: ConstraintLayout
    private lateinit var mEditText: EditText
    private lateinit var mImageView: ImageView
    private lateinit var mEmotion: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        setContentView(R.layout.activity_main)

        mRootView = findViewById(R.id.th_root_view)
//        mRootView.setFitsSystemWindows(true)
        mTHPanelSwitchFrameLayout = findViewById(R.id.th_fl_root_view)
        mTHPanelSwitchFrameLayout = findViewById(R.id.th_fl_root_view)
        mConstraintLayout = findViewById(R.id.cl_root_view)
        mConstraintLayoutIm = findViewById(R.id.cl_root_im)
        mEditText = findViewById(R.id.et_message)
        mImageView = findViewById(R.id.iv_message)
        mEmotion = findViewById(R.id.iv_emotion)
        THKPSwitchConflictUtils.attach(mTHPanelSwitchFrameLayout, mEditText, THPanelTriggerEntity(mConstraintLayout, mImageView), THPanelTriggerEntity(mConstraintLayoutIm, mEmotion))
    }
}