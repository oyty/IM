package com.iteacher.android.net.callback;

import com.iteacher.android.net.AppException;
import com.iteacher.android.net.IRequestListener;
import com.iteacher.android.net.OnUploadProgressChangedListener;

import org.apache.http.HttpResponse;

import java.io.OutputStream;
import java.net.HttpURLConnection;


/**
 * @author Stay
 * @version create time：Sep 15, 2014 12:42:45 PM
 */
public interface ICallback<T> {
	void onSuccess(T result);
	void onFailure(AppException exception);
	T handle(HttpResponse response, IRequestListener listener) throws AppException;
	T handle(HttpResponse response) throws AppException;
	void cancel(boolean force);
	int retryCount();
	T bindData(String content) throws AppException;
	T preRequest();
	T postRequest(T t);
	boolean isForceCancelled();
	boolean onCustomOutput(OutputStream out, OnUploadProgressChangedListener listener) throws AppException;
	T handle(HttpURLConnection connection, IRequestListener listener) throws AppException;
	T handle(HttpURLConnection connection) throws AppException;

}
