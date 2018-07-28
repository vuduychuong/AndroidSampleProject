package com.chuongvd.app.signal.data.source.remote.service;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.IOException;
import retrofit2.Response;

public class BaseException extends RuntimeException {

    public static final String METHOD_NOT_ALLOW = "405";

    private Type type;
    @Nullable
    private static BaseErrorResponse errorResponse;
    @Nullable
    private Response response;

    public BaseException(Type type, Throwable cause) {
        super(cause.getMessage(), cause);
        this.type = type;
        errorResponse = null;
    }

    private BaseException(Type type, @Nullable BaseErrorResponse errorResponse) {
        this.type = type;
        this.errorResponse = errorResponse;
    }

    private BaseException(Type type, @Nullable Response response) {
        this.type = type;
        this.response = response;
    }

    public static BaseException toHttpError(Response response) {
        return new BaseException(Type.HTTP, response);
    }

    public static BaseException toHttpError(Response response, BaseErrorResponse error) {
        errorResponse = error;
        return new BaseException(Type.HTTP, response);
    }

    public static BaseException toNetworkError(Throwable cause) {
        return new BaseException(Type.NETWORK, cause);
    }

    public static BaseException toServerError(BaseErrorResponse errorResponse) {
        return new BaseException(Type.SERVER, errorResponse);
    }

    public static BaseException toUnexpectedError(Throwable cause) {
        return new BaseException(Type.UNEXPECTED, cause);
    }

    public Type getErrorType() {
        return type;
    }

    public String getMessage() {
        try {
            switch (type) {
                case SERVER:
                    if (errorResponse != null) {
                        return errorResponse.getErrorMessage();
                    }
                    return "";
                case NETWORK:
                    return getNetworkErrorMessage(getCause());
                case HTTP:
                    if (response != null) {
                        return getHttpErrorMessage(response.code());
                    }
                    return "";
                case UNEXPECTED:
                default:
                    if (getCause() != null) {
                        return getCause().getMessage();
                    }
                    break;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Nullable
    public BaseErrorResponse getErrorResponse() {
        return errorResponse;
    }

    @Nullable
    public String getErrorCode() {
        if (errorResponse == null) {
            return null;
        }
        return errorResponse.getCode();
    }

    private String getNetworkErrorMessage(Throwable throwable) {

        return "Network error";
    }

    private String getHttpErrorMessage(int httpCode) {

        if (errorResponse != null && !TextUtils.isEmpty(errorResponse.getErrorMessage())) {
            return errorResponse.getErrorMessage();
        }
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
        return "Http error";
    }

    public enum Type {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK, /**
         * A non-2xx HTTP status code was received from the server.
         */
        HTTP, /**
         * A error server with code & message
         */
        SERVER, /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}
