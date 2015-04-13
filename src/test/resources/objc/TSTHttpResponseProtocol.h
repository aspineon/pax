#import <Foundation/Foundation.h>

/**
 * Interface for defining the HttpResponse helper class API.
 */
@protocol TSTHttpResponseProtocol <NSObject>

@required

/**
 * @return The url for this HTTP response.
 */
- (NSString*)getUrl;

/**
 * @param url Sets the response code for this HTTP response.
 */
- (void)setUrl:(NSString*)url;

/**
 * @return The response code for this HTTP response.
 */
- (int)getCode;

/**
 * @param code Sets the response code for this HTTP response.
 */
- (void)setCode:(int)code;

/**
 * @return The response message for this HTTP response, usually populated when there is an error.
 */
- (NSString*)getMessage;

/**
 * @param message Sets the response message for this HTTP response, usually for errors.
 */
- (void)setMessage:(NSString*)message;

/**
 * @return The response body for this HTTP response.
 */
- (NSString*)getBody;

/**
 * @param body Sets the response body for this HTTP response.
 */
- (void)setBody:(NSString*)body;

/**
 * @return The headers for this HTTP response.
 */
- (NSDictionary*)getHeaders;

/**
 * @param headers Sets the headers for this HTTP response.
 */
- (void)setHeaders:(NSDictionary*)headers;

@end