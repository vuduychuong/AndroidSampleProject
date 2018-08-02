package com.chuongvd.app.signal.ui.auth.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.view.View
import com.chuongvd.app.signal.BuildConfig
import com.chuongvd.app.signal.R
import com.chuongvd.app.signal.data.entity.UserEntity
import com.chuongvd.app.signal.data.source.remote.request.LoginRequest
import com.chuongvd.app.signal.data.source.remote.service.RequestHelper
import com.chuongvd.app.signal.databinding.FragmentLoginBinding
import com.chuongvd.app.signal.ui.common.BaseDataBindingFragment
import com.chuongvd.app.signal.ui.main.MainActivity
import com.chuongvd.app.signal.viewmodel.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.iid.FirebaseInstanceId

class LoginFragment : BaseDataBindingFragment<FragmentLoginBinding, LoginViewModel>(), View.OnClickListener, FacebookCallback<LoginResult> {
  private var mFacebookCallbackManager: CallbackManager? = null
  private var mFacebookHandler: FacebookHandler? = null

  override val contentViewLayoutId: Int
    get() = R.layout.fragment_login

  override fun initData() {
    val factory = LoginViewModel.Factory(activity!!.application)
    mViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
    mBinding.setOnClickLogin(this)
    mFacebookHandler = FacebookHandler(context!!)
    mFacebookCallbackManager = CallbackManager.Factory.create()
    LoginManager.getInstance().registerCallback(mFacebookCallbackManager!!, this)
  }

  override fun subscribeToViewModel() {
    mViewModel?.user?.observe(this, Observer { userEntity ->
      run {
        if (null == userEntity) return@Observer
        if (isValidUser(userEntity)) {
          //Go to Main flow
          RequestHelper.setAccessToken(userEntity.accessToken)
          goToMain()
        }
      }
    })
  }

  private fun isValidUser(userEntity: UserEntity): Boolean {
    return null != userEntity.accessToken
  }

  private fun goToMain() {
    context?.let { startActivity(MainActivity.newIntance(it)) }
    activity?.finish()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    mFacebookCallbackManager!!.onActivityResult(requestCode, resultCode, data)
    super.onActivityResult(requestCode, resultCode, data)
  }

  override fun onClick(view: View) {
    //FIXME: remove commend code below if you need to login with facebook
    //        LoginManager.getInstance()
    //                .logInWithReadPermissions(this, mFacebookHandler.getFacebookPermission());
    goToMain()
  }

  /**
   * Callback login using facebook
   */
  override fun onSuccess(loginResult: LoginResult?) {
    if (mFacebookHandler == null || loginResult == null) return
    FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
      val token: String
      if (!task.isSuccessful) {
        token = ""
      } else {
        token = task.result.token
      }
      val request = LoginRequest(BuildConfig.VERSION_NAME, BuildConfig.OS_TYPE, token,
          loginResult.accessToken.token, "FACEBOOK")
      mViewModel!!.login(request)
    }
  }

  override fun onCancel() {
    logoutFacebook()
  }

  override fun onError(error: FacebookException) {
    logoutFacebook()
  }

  private fun logoutFacebook() {
    LoginManager.getInstance().logOut()
  }
}
