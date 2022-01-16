//
//  HTHtmlViewManager.m
//  react-native-html-view
//
//  Created by hublot on 2021/2/2.
//

#import "HTHtmlViewManager.h"
#import <React/RCTUIManager.h>
#import <React/RCTUtils.h>
#import "HTHtmlView.h"

@interface HTHtmlViewManager ()

@end

@implementation HTHtmlViewManager

RCT_EXPORT_MODULE()

- (UIView *)view {
    HTHtmlView *view = [[HTHtmlView alloc] init];
    view.bridge = self.bridge;
    return view;
}

RCT_EXPORT_VIEW_PROPERTY(maxTextSize, CGSize)

RCT_EXPORT_VIEW_PROPERTY(value, NSString *)


@end
