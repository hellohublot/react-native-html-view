//
//  HTHtmlView.h
//  react-native-html-view
//
//  Created by hublot on 2021/2/2.
//

#import <UIKit/UIKit.h>
#import <React/RCTBridge.h>

NS_ASSUME_NONNULL_BEGIN

@interface HTHtmlView : UITextView

@property (nonatomic, weak) RCTBridge *bridge;

@property (nonatomic, assign) CGSize maxTextSize;

@property (nonatomic, strong) NSString *value;

@end

NS_ASSUME_NONNULL_END
