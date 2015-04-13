#import <Foundation/Foundation.h>

/**
 * Interface for defining the HttpRequest helper class API.
 */
@protocol TSTHttpRequestProtocol <NSObject>

@required

/**
 * @param url resource endpoint to send the request to
 * @return the HttpRequest being altered
 */
- (id<TSTHttpRequestProtocol>)setResource:(NSString*)url;

/**
 * @return the resource endpoint this request is being sent to
 */
- (NSString*)getResource;

/**
 * Set a header field and value on this request
 * @param header name of the header field
 * @param value value for the header field
 * @return the HttpRequest being altered
 */
- (id<TSTHttpRequestProtocol>)setHeader:(NSString*)header withValue:(NSString*)value;

/**
 * @param header name of the header field
 * @return value for the header field
 */
- (String)getValueForHeader:(NSString*)header;

/**
 * Set the body of the request, defaulting to UTF-8 charset
 * @return the HttpRequest being altered
 */
- (id<TSTHttpRequestProtocol>)setBody:(NSString*)body;

/**
 * Set the body of the request with a specified charset
 * @param body body of the request
 * @param charset charset the body is encoded in
 * @return the HttpRequest being altered
 */
- (id<TSTHttpRequestProtocol>)setBody:(NSString*)body withCharset:(NSString*)charset;

/**
 * Set the body of the request with a valid json string, defaulting to UTF-8 charset
 * @param jsonBody body of the request, as a valid json string
 * @return the HttpRequest being altered
 */
- (id<TSTHttpRequestProtocol>)setJsonBody:(NSString*)jsonBody;

/**
 * Set the body of the request with a valid json string and a specified charset
 * @param jsonBody body of the request, as a valid json string
 * @param charset charset the body is encoded in
 * @return the HttpRequest being altered
 */
- (id<TSTHttpRequestProtocol>)setJsonBody:(NSString*)jsonBody withCharset:(NSString*)charset;

/**
 * Execute a GET request with object as constructed
 * @return Response object populated with execution results
 */
- (id<TSTHttpResponseProtocol>)get;

/**
 * Execute a PUT request with object as constructed
 * @return Response object populated with execution results
 */
- (id<TSTHttpResponseProtocol>)put;

/**
 * Execute a POST request with object as constructed
 * @return Response object populated with execution results
 */
- (id<TSTHttpResponseProtocol>)post;

/**
 * Execute a DELETE request with object as constructed
 * @return Response object populated with execution results
 */
- (id<TSTHttpResponseProtocol>)delete;

/**
 * Execute a GET request with object as constructed, on a background worker thread
 * @param requestListener optional callback when request is complete
 */
- (void)getAsync:(id<TSTHttpRequestListener> const)requestListener;

/**
 * Execute a PUT request with object as constructed, on a background worker thread
 * @param requestListener optional callback when request is complete
 */
- (void)putAsync:(id<TSTHttpRequestListener> const)requestListener;

/**
 * Execute a POST request with object as constructed, on a background worker thread
 * @param requestListener optional callback when request is complete
 */
- (void)postAsync:(id<TSTHttpRequestListener> const)requestListener;

/**
 * Execute a DELETE request with object as constructed, on a background worker thread
 * @param requestListener optional callback when request is complete
 */
- (void)deleteAsync:(id<TSTHttpRequestListener> const)requestListener;

@end