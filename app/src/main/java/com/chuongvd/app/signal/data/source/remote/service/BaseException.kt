package com.chuongvd.app.signal.data.source.remote.service

import android.text.TextUtils
import retrofit2.Response
import java.io.IOException

class BaseException : RuntimeException {

  var errorType: Type? = null
    private set
  private var response: Response<*>? = null

  val errorCode: String?
    get() = if (errorResponse == null) {
      null
    } else errorResponse!!.code

  constructor(type: Type, cause: Throwable) : super(cause.message, cause) {
    this.errorType = type
    errorResponse = null
  }

  private constructor(type: Type, error: BaseErrorResponse?) {
    this.errorType = type
    errorResponse = error
  }

  private constructor(type: Type, response: Response<*>?) {
    this.errorType = type
    this.response = response
  }

  override val message: String?
    get() {
      try {
        when (errorType) {
          BaseException.Type.SERVER -> {
            return if (errorResponse != null) {
              errorResponse!!.errorMessage
            } else ""
          }
          BaseException.Type.NETWORK -> return cause?.let { getNetworkErrorMessage(it) }
          BaseException.Type.HTTP -> {
            return if (response != null) {
              getHttpErrorMessage(response!!.code())
            } else ""
          }
          BaseException.Type.UNEXPECTED -> if (cause != null) {
            return cause.message
          }
          else -> if (cause != null) {
            return cause.message
          }
        }
      } catch (e: Exception) {
        return null
      }

      return null
    }

  fun getErrorResponse(): BaseErrorResponse? {
    return errorResponse
  }

  private fun getNetworkErrorMessage(throwable: Throwable): String {

    return "Network error"
  }

  private fun getHttpErrorMessage(httpCode: Int): String? {

    return if (errorResponse != null && !TextUtils.isEmpty(errorResponse!!.errorMessage)) {
      errorResponse!!.errorMessage
    } else "Http error"
    //        if (httpCode >= 300 && httpCode <= 308) {
    //            // Redirection
    //            return KnotApplication.self().getString(R.string.error_redirection);
    //        }
    //        if (httpCode >= 400 && httpCode <= 451) {
    //            // Client error
    //            return KnotApplication.self().getString(R.string.error_client);
    //        }
    //        if (httpCode >= 500 && httpCode <= 511) {
    //            // Server error
    //            return KnotApplication.self().getString(R.string.error_server);
    //        }

    // Unofficial error
  }

  enum class Type {
    /**
     * An [IOException] occurred while communicating to the server.
     */
    NETWORK,
    /**
     * A non-2xx HTTP status code was received from the server.
     */
    HTTP,
    /**
     * A error server with code & message
     */
    SERVER,
    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
  }

  companion object {

    val METHOD_NOT_ALLOW = "405"
    private var errorResponse: BaseErrorResponse? = null

    fun toHttpError(response: Response<*>): BaseException {
      return BaseException(Type.HTTP, response)
    }

    fun toHttpError(response: Response<*>, error: BaseErrorResponse): BaseException {
      errorResponse = error
      return BaseException(Type.HTTP, response)
    }

    fun toNetworkError(cause: Throwable): BaseException {
      return BaseException(Type.NETWORK, cause)
    }

    fun toServerError(errorResponse: BaseErrorResponse): BaseException {
      return BaseException(Type.SERVER, errorResponse)
    }

    fun toUnexpectedError(cause: Throwable): BaseException {
      return BaseException(Type.UNEXPECTED, cause)
    }
  }
}
