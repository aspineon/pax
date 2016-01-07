package com.test.http.models;

/**
 * Callback interface specific to asynchronous http requests.
 */
public interface HttpRequestListener {

	/**
	 * Provides a means to hand back control after a request has completed, on the worker
	 * background thread.
	 * @param response result of the request that was executed.
	 */
	public void onComplete(HttpResponse response);

}
