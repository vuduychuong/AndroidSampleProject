package com.chuongvd.app.signal.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chuongvd.app.signal.R

class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    //        try {
    //            PackageInfo info = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID,
    //                    PackageManager.GET_SIGNATURES);
    //            for (Signature signature : info.signatures) {
    //                MessageDigest md = MessageDigest.getInstance("SHA");
    //                md.update(signature.toByteArray());
    //                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
    //            }
    //        } catch (PackageManager.NameNotFoundException e) {
    //
    //        } catch (NoSuchAlgorithmException e) {
    //
    //        }
    if (savedInstanceState == null) {
      val fragment = LoginFragment()
      supportFragmentManager.beginTransaction()
          .add(R.id.fragment_container, fragment, LoginFragment::class.java.name)
          .commit()
    }
  }

  companion object {

    fun newIntance(context: Context): Intent {
      return Intent(context, LoginActivity::class.java)
    }
  }
}
