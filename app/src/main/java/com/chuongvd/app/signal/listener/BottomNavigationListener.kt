package com.chuongvd.app.signal.listener

import android.view.View

interface BottomNavigationListener {
  fun onClickHome(v: View)

  fun onClickChart(v: View)

  fun onClickInfo(v: View)

  fun onClickProfile(v: View)
}
