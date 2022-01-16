package com.hublot.htmlview;

import android.os.AsyncTask;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.facebook.react.bridge.*;
import com.facebook.react.uimanager.*;
import com.facebook.react.uimanager.annotations.ReactProp;
import net.nightwhistler.htmlspanner.HtmlSpanner;

public class HTHtmlViewManager extends SimpleViewManager<HTHtmlView> {

    private HtmlSpanner htmlSpanner = new HtmlSpanner();

    private HTImageHandler imageHandler = new HTImageHandler();

    @Override
    public String getName() {
        return "HTHtmlView";
    }

    @NonNull
    @Override
    protected HTHtmlView createViewInstance(@NonNull ThemedReactContext reactContext) {
        HTHtmlView view = new HTHtmlView(reactContext);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        htmlSpanner.registerHandler("img", imageHandler);
        return view;
    }

    @ReactProp(name = "maxTextSize")
    public void setMaxTextSize(HTHtmlView view, ReadableMap maxTextSize) {
        view.maxTextSize = maxTextSize;
    }

    @ReactProp(name = "value")
    public void setValue(HTHtmlView view, String value) {
        view.value = value;
    }

    private void reloadText(final HTHtmlView view, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Spanned text = htmlSpanner.fromHtml(view.value);
                UiThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.invoke(text);
                    }
                });
            }
        }).start();
    }

    @Override
    public void updateProperties(@NonNull final HTHtmlView view, ReactStylesDiffMap props) {
        super.updateProperties(view, props);
        reloadText(view, new Callback() {
            @Override
            public void invoke(Object... args) {
                Spanned text = (Spanned) args[0];
                view.setText(text);
                if (view.getLayoutParams() == null) {
                    view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
                int maxWidth = (int) PixelUtil.toPixelFromDIP(view.maxTextSize.getDouble("width"));
                int maxHeight = (int) PixelUtil.toPixelFromDIP(view.maxTextSize.getDouble("height"));
                view.measure(
                    MeasureSpec.makeMeasureSpec(maxWidth, maxWidth == 0 ? MeasureSpec.UNSPECIFIED : MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(maxHeight, maxHeight == 0 ? MeasureSpec.UNSPECIFIED : MeasureSpec.AT_MOST)
                );
                final int measuredWidth = view.getMeasuredWidth();
                final int measuredHeight = view.getMeasuredHeight();
                final int id = view.getId();
                if (measuredWidth != view.getWidth() || measuredHeight != view.getHeight()) {
                    final ReactContext reactContext = (ReactContext) view.getContext();
                    reactContext.runOnNativeModulesQueueThread(new Runnable() {
                        @Override
                        public void run() {
                            reactContext.getNativeModule(UIManagerModule.class)
                                .updateNodeSize(id, measuredWidth, measuredHeight);
                        }
                    });
                }
            }
        });
    }
}
