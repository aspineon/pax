#import <Foundation/Foundation.h>

/**
 * Service interface for creating HTTP Requests.
 */
@protocol TSTHttpServiceProtocol <NSObject>

@required

/**
 * Returns a new HttpRequest for the specified URL resource.
 * @param url A string representing the URL resource for this request
 * @return A new HttpRequest object that can be further customized and executed.
 */
- (id<TSTHttpRequestProtocol>)getResource:(NSString* const)url;

@end