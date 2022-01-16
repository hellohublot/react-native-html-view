//
//  HTHtmlView.m
//  react-native-html-view
//
//  Created by hublot on 2021/2/2.
//

#import "HTHtmlView.h"
#import <React/UIView+React.h>
#import <React/RCTUtils.h>
#import <React/RCTUIManager.h>

@interface HTHtmlView () <UITextViewDelegate>

@end

@implementation HTHtmlView

- (instancetype)init {
    if (self = [super init]) {
        self.delegate = self;
        self.textContainerInset = UIEdgeInsetsZero;
        self.textContainer.lineFragmentPadding = 0;
        [self setEditable:false];
        [self setScrollEnabled:false];
    }
    return self;
}

- (void)didSetProps:(NSArray<NSString *> *)changedProps {
    dispatch_async(self.bridge.uiManager.methodQueue, ^{
        NSData *data = [self.value dataUsingEncoding:NSUTF8StringEncoding];
        NSAttributedString *attributedString = [[NSAttributedString alloc] initWithData:data options:@{
            NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType,
            NSCharacterEncodingDocumentAttribute: @(NSUTF8StringEncoding),
        } documentAttributes:nil error:nil];
        CGRect bounds = [attributedString boundingRectWithSize:self.maxTextSize options:NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading context:nil];
        RCTExecuteOnMainQueue(^{
            [self setAttributedText:attributedString];
            [self.bridge.uiManager setIntrinsicContentSize:bounds.size forView:self];
        });
    });
}

- (BOOL)textView:(UITextView *)textView shouldInteractWithTextAttachment:(NSTextAttachment *)textAttachment inRange:(NSRange)characterRange {
    return true;
}

@end
