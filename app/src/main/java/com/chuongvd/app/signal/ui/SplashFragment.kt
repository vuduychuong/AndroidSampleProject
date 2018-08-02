package com.chuongvd.app.signal.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Handler
import android.text.TextUtils
import com.chuongvd.app.signal.R
import com.chuongvd.app.signal.data.entity.UserEntity
import com.chuongvd.app.signal.data.source.remote.service.RequestHelper
import com.chuongvd.app.signal.databinding.FragmentSplashBinding
import com.chuongvd.app.signal.ui.auth.login.LoginActivity
import com.chuongvd.app.signal.ui.common.BaseDataBindingFragment
import com.chuongvd.app.signal.ui.main.MainActivity
import com.chuongvd.app.signal.viewmodel.SplashViewModel

class SplashFragment : BaseDataBindingFragment<FragmentSplashBinding, SplashViewModel>() {

  override val contentViewLayoutId: Int
    get() = R.layout.fragment_splash

  override fun initData() {
    val factory = SplashViewModel.Factory(activity!!.application)
    mViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
  }

  override fun subscribeToViewModel() {
    mViewModel.user.observe(this, Observer { userEntity ->
      run {
        Handler().postDelayed(Runnable {
          if (null == userEntity) {
            //Go to Auth flow
            goToLogin()
            return@Runnable
          }
          if (isValidUser(userEntity)) {
            //Go to Main flow
            RequestHelper.setAccessToken(userEntity.accessToken)
            goToMain()
          } else {
            //Go to Auth flow
            goToLogin()
          }
        }, DELAY_TIME)
      }
    })
  }

  private fun isValidUser(userEntity: UserEntity): Boolean {
    return !TextUtils.isEmpty(userEntity.accessToken)
  }

  private fun goToLogin() {
    context?.let { startActivity(LoginActivity.newIntance(it)) }
    activity?.finish()
  }

  private fun goToMain() {
    context?.let { startActivity(MainActivity.newIntance(it)) }
    activity?.finish()
  }

  companion object {
    private val DELAY_TIME: Long = 2000
  }
}
