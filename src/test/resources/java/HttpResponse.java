package com.test.http.models;

import java.util.List;
import java.util.Map;

/**
 * Interface for defining the HttpResponse helper class API.
 */
public interface HttpResponse {

	/**
	 * @return The url for this HTTP response.
	 */
	public String getUrl();

	/**
	 * @param url Sets the response code for this HTTP response.
	 */
	public void setUrl(String url);

	/**
	 * @return The response code for this HTTP response.
	 */
	public int getCode();

	/**
	 * @param code Sets the response code for this HTTP response.
	 */
	public void setCode(int code);

	/**
	 * @return The response message for this HTTP response, usually populated when there is an error.
	 */
	public String getMessage();

	/**
	 * @param message Sets the response message for this HTTP response, usually for errors.
	 */
	public void setMessage(String message);

	/**
	 * @return The response body for this HTTP response.
	 */
	public String getBody();

	/**
	 * @param body Sets the response body for this HTTP response.
	 */
	public void setBody(String body);

	/**
	 * @return The headers for this HTTP response.
	 */
	public Map<String, List<String>> getHeaders();

	/**
	 * @param headers Sets the headers for this HTTP response.
	 */
	public void setHeaders(Map<String, List<String>> headers);

}
