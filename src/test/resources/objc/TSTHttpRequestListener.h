#import <Foundation/Foundation.h>

/**
 * Callback interface specific to asynchronous http requests.
 */
@protocol TSTHttpRequestListener <NSObject>

@required

/**
 * Provides a means to hand back control after a request has completed, on the worker
 * background thread.
 * @param response result of the request that was executed.
 */
- (void)onComplete:(id<TSTHttpResponseProtocol>)response;

@end