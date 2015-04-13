package com.test.http.services;

import com.test.http.models.HttpRequest;

/**
 * Service interface for creating HTTP Requests.
 */
public interface HttpService {

	/**
	 * Returns a new HttpRequest for the specified URL resource.
	 * @param url A string representing the URL resource for this request
	 * @return A new HttpRequest object that can be further customized and executed.
	 */
	public HttpRequest getResource(final String url);

}