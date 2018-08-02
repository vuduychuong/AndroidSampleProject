package com.chuongvd.app.signal.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chuongvd.app.signal.R

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    if (savedInstanceState == null) {
      val fragment = MainFragment()

      supportFragmentManager.beginTransaction()
          .add(R.id.fragment_container, fragment, MainFragment::class.java.name)
          .commit()
    }
  }

  companion object {

    fun newIntance(context: Context): Intent {
      return Intent(context, MainActivity::class.java)
    }
  }
}
