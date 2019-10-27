package com.example.cloud.page

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videoplayer.controller.MediaPlayerControl
import com.example.cloud.R
import com.example.cloud.util.Constant
import com.example.cloud.util.SPUtils

import kotlinx.android.synthetic.main.activity_show_file.*


class ShowFileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_file)

        val controller = StandardVideoController<MediaPlayerControl>(this)
        //根据屏幕方向自动进入/退出全屏
        controller.setEnableOrientation(true)
        val isLive = intent.getBooleanExtra("isLive", false)
        if (isLive) {
            controller.setLive()
        }
        val title = intent.getStringExtra("title")
        controller.setTitle(title)

        player.setVideoController(controller)

        val map = HashMap<String, String>()

        val token = SPUtils.get(Constant.TOKEN, "") as String

        map.put("AUTHORIZATION",token)

        player.setUrl(intent.getStringExtra("filePath"),map)
        
        player.start()

    }
}