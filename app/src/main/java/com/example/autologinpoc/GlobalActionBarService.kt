package com.example.autologinpoc

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout

import android.view.LayoutInflater

import android.view.Gravity

import android.view.WindowManager

import android.graphics.PixelFormat
import android.view.accessibility.AccessibilityNodeInfo
import timber.log.Timber

import android.os.Bundle


class GlobalActionBarService : AccessibilityService() {
    override fun onServiceConnected() {
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val layout = FrameLayout(this)
        val lp = WindowManager.LayoutParams()
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        lp.format = PixelFormat.TRANSLUCENT
        lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.TOP or Gravity.END
        val inflater = LayoutInflater.from(this)
        inflater.inflate(R.layout.action_bar, layout)
        wm.addView(layout, lp)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        Timber.d(event.toString())
        Timber.d(event.source.toString())
        val nodeInfo = rootInActiveWindow
        val un = nodeInfo.findAccessibilityNodeInfosByViewId("com.example.samplelogin:id/username")
        un?.firstOrNull()?.let{
            Timber.d("s: ${it.text}")
            if(it.text != "Email") return
            val arguments = Bundle()
            arguments.putCharSequence(
                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "username"
            )
            it.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
        }

        val pw = nodeInfo.findAccessibilityNodeInfosByViewId("com.example.samplelogin:id/password")
        pw?.firstOrNull()?.let{
            Timber.d("pwd: ${it.text}")
            if(it.text != "Password") return
            val arguments = Bundle()
            arguments.putCharSequence(
                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "password111"
            )
            it.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
        }
        nodeInfo.recycle()
    }

    override fun onInterrupt() {
    }
}