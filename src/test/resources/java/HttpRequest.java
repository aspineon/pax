package com.test.http.models;

/**
 * Interface for defining the HttpRequest helper class API.
 */
public interface HttpRequest {

	/**
	 * @param url resource endpoint to send the request to
	 * @return the HttpRequest being altered
	 */
	public HttpRequest setResource(String url);

	/**
	 * @return the resource endpoint this request is being sent to
	 */
	public String getResource();

	/**
	 * Set a header field and value on this request
	 * @param header name of the header field
	 * @param value value for the header field
	 * @return the HttpRequest being altered
	 */
	public HttpRequest setHeader(String header, String value);

	/**
	 * @param header name of the header field
	 * @return value for the header field
	 */
	public String getValueForHeader(String header);

	/**
	 * Set the body of the request, defaulting to UTF-8 charset
	 * @return the HttpRequest being altered
	 */
	public HttpRequest setBody(String body);

	/**
	 * Set the body of the request with a specified charset
	 * @param body body of the request
	 * @param charset charset the body is encoded in
	 * @return the HttpRequest being altered
	 */
	public HttpRequest setBody(String body, String charset);

	/**
	 * Set the body of the request with a valid json string, defaulting to UTF-8 charset
	 * @param jsonBody body of the request, as a valid json string
	 * @return the HttpRequest being altered
	 */
	public HttpRequest setJsonBody(String jsonBody);

	/**
	 * Set the body of the request with a valid json string and a specified charset
	 * @param jsonBody body of the request, as a valid json string
	 * @param charset charset the body is encoded in
	 * @return the HttpRequest being altered
	 */
	public HttpRequest setJsonBody(String jsonBody, String charset);

	/**
	 * Execute a GET request with object as constructed
	 * @return Response object populated with execution results
	 */
	public HttpResponse get();

	/**
	 * Execute a PUT request with object as constructed
	 * @return Response object populated with execution results
	 */
	public HttpResponse put();

	/**
	 * Execute a POST request with object as constructed
	 * @return Response object populated with execution results
	 */
	public HttpResponse post();

	/**
	 * Execute a DELETE request with object as constructed
	 * @return Response object populated with execution results
	 */
	public HttpResponse delete();

	/**
	 * Execute a GET request with object as constructed, on a background worker thread
	 * @param requestListener optional callback when request is complete
	 */
	public void getAsync(final HttpRequestListener requestListener);

	/**
	 * Execute a PUT request with object as constructed, on a background worker thread
	 * @param requestListener optional callback when request is complete
	 */
	public void putAsync(final HttpRequestListener requestListener);

	/**
	 * Execute a POST request with object as constructed, on a background worker thread
	 * @param requestListener optional callback when request is complete
	 */
	public void postAsync(final HttpRequestListener requestListener);

	/**
	 * Execute a DELETE request with object as constructed, on a background worker thread
	 * @param requestListener optional callback when request is complete
	 */
	public void deleteAsync(final HttpRequestListener requestListener);

}