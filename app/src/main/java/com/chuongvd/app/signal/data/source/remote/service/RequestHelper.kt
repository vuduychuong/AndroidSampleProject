package com.chuongvd.app.signal.data.source.remote.service

import android.text.TextUtils
import com.chuongvd.app.signal.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RequestHelper {
  val CONNECTION_TIMEOUT = 30
  val CONNECTION_TIMEOUT_LONG = 60 * 3
  private val MAXIMUM_REQUEST_RETRY = 5
  var instance: RocketApi? = null
  private var accessToken: String? = null

  val request: RocketApi
    get() {
      if (instance == null) instance = makeRequest(BuildConfig.END_POINT)
      return instance as RocketApi
    }

  private val httpLoggingInterceptor: HttpLoggingInterceptor
    get() {
      val interceptor = HttpLoggingInterceptor()
      interceptor.setLevel(if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
      else
        HttpLoggingInterceptor.Level.NONE)
      return interceptor
    }

  private// Install the all-trusting trust manager
  // Create an ssl socket factory with our all-trusting manager
  val unsafeOkHttpClient: OkHttpClient.Builder
    get() {
      try {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
          override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>,
              authType: String) {
          }

          override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>,
              authType: String) {
          }

          override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
          }
        })
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sslSocketFactory = sslContext.socketFactory
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.sslSocketFactory(sslSocketFactory)
            .hostnameVerifier(
                org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            .build()
        return httpClientBuilder
      } catch (e: Exception) {
        throw RuntimeException(e)
      }

    }

  private fun makeRequest(baseUrl: String): RocketApi {
    val logging = httpLoggingInterceptor
    val httpClient = unsafeOkHttpClient
    httpClient.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
    httpClient.readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
    httpClient.addInterceptor(logging)
    httpClient.addInterceptor(requestWithHeader())
    //        httpClient.authenticator(new TokenAuthenticator());
    val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(ErrorHandlerFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .client(httpClient.build())
        .build()
    return retrofit.create(RocketApi::class.java)
  }

  private fun requestWithHeader(): Interceptor {
    return Interceptor { chain ->
      var original = chain.request()
      var builder = addAuthenticateHeader(original.newBuilder(), original)
      var response = chain.proceed(builder.build())
      if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
        builder = addAuthenticateHeader(builder, original)
        original = builder.build()
        response = chain.proceed(original)
      }
      response
    }
  }

  private fun addAuthenticateHeader(builder: Request.Builder,
      original: Request): Request.Builder {
    builder.addHeader("Cache-Control", "no-cache")
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .method(original.method(), original.body())
    if (BuildConfig.DEBUG) {
      println(String.format("token: %s", accessToken))
    }
    if (!TextUtils.isEmpty(accessToken)) {
      builder.header("Authorization", "Bearer " + accessToken!!)
    }

    return builder
  }

  fun setAccessToken(accessToken: String) {
    RequestHelper.accessToken = accessToken
  }
}
