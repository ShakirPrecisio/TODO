package com.todo.app

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.text.TextUtils
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.todo.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var looper: Looper
    private lateinit var handler: Handler
    private lateinit var handlerThread: HandlerThread
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handlerThread = HandlerThread("thread1")
        handlerThread.start()
        looper = handlerThread.looper
        handler = Handler(looper)





        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.GET_ACCOUNTS,

                ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    handler.postDelayed(Runnable {

                        startActivity(Intent(this@MainActivity, SignInActivity::class.java))

                    },2000)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    handler.postDelayed(Runnable {

                        startActivity(Intent(this@MainActivity, SignInActivity::class.java))

                    },2000)
                }

            }).check()



    }


    override fun onBackPressed() {

    }



}