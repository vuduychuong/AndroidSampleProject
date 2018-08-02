package com.chuongvd.app.signal.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chuongvd.app.signal.R

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    if (savedInstanceState == null) {
      val fragment = SplashFragment()
      supportFragmentManager.beginTransaction()
          .add(R.id.fragment_container, fragment, SplashFragment::class.java.name)
          .commit()
    }
  }

  companion object {
    private val DELAY_TIME: Long = 2000
  }
}
