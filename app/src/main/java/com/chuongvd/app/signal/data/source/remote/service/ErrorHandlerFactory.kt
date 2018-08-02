//package com.chuongvd.app.signal.data.source.remote.service
//
//import android.util.Log
//import com.google.gson.Gson
//import com.google.gson.JsonSyntaxException
//import io.reactivex.Observable
//import io.reactivex.ObservableSource
//import io.reactivex.annotations.Nullable
//import io.reactivex.functions.Function
//import retrofit2.Call
//import retrofit2.CallAdapter
//import retrofit2.HttpException
//import retrofit2.Retrofit
//import java.io.IOException
//import java.lang.reflect.Type
//
//class ErrorHandlerFactory private constructor() : CallAdapter.Factory() {
//  private val mInstance: ErrorHandlerFactory
//
//  init {
//    mInstance = ErrorHandlerFactory()
//  }
//
//  @Nullable
//  override operator fun get(type: Type, annotations: Array<Annotation>,
//      retrofit: Retrofit): CallAdapter<*, Observable<*>> {
//    return RxCallAdapterWrapper(retrofit, mInstance.get(type, annotations, retrofit))
//  }
//
//  private inner class RxCallAdapterWrapper<R>(internal val mRetrofit: Retrofit,
//      internal val mWrapped: CallAdapter<R, Observable<*>>) : CallAdapter<R, Observable<*>> {
//
//    override fun adapt(call: Call<R>?): Observable<*> {
//    }
//
//    override fun responseType(): Type {
//      return mWrapped.responseType()
//    }
//
////    override fun adapt(call: Call<R>): Observable<*> {
////      return (mWrapped.adapt(call) as Observable<*>).onErrorResumeNext(
////          object : Function<Throwable, ObservableSource<*>> {
////            override fun apply(throwable: Throwable): ObservableSource<*> {
////              return Observable.error<Any>(convertToBaseException(throwable))
////            }
////          })
////    }
//
//    private fun convertToBaseException(throwable: Throwable): BaseException {
//      if (throwable is BaseException) {
//        return throwable
//      } else if (throwable is IOException) {
//        return BaseException.toNetworkError(throwable)
//      } else if (throwable is HttpException) {
//        val httpException = throwable as HttpException?
//        val response = httpException!!.response()
//        if (response.errorBody() == null) {
//          return BaseException.toHttpError(response)
//        }
//        try {
//          val errorResponse = response.errorBody()!!.string()
//          val baseErrorResponse = deserializeErrorBody(errorResponse)
//          return if (baseErrorResponse != null && baseErrorResponse.isError) {
//            //Get error data from Server
//            BaseException.toServerError(baseErrorResponse)
//          } else {
//            //Get error data cause http connection
//            baseErrorResponse?.let { BaseException.toHttpError(response, it) }!!
//          }
//        } catch (e: IOException) {
//          Log.e(this.javaClass.simpleName, e.message)
//        }
//
//      }
//      return BaseException.toUnexpectedError(throwable!!)
//    }
//
//    private fun deserializeErrorBody(errorString: String): BaseErrorResponse? {
//      try {
//        return Gson().fromJson(errorString, BaseErrorResponse::class.java)
//      } catch (e: JsonSyntaxException) {
//        return null
//      }
//
//    }
//  }
//
//  companion object {
//
//    fun create(): CallAdapter.Factory {
//      return ErrorHandlerFactory()
//    }
//  }
//}