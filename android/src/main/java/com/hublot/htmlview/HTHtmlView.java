package com.hublot.htmlview;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.TextView;
import com.facebook.react.bridge.ReadableMap;

public class HTHtmlView extends TextView {

    public HTHtmlView(Context context) {
        super(context);
    }

    public String value;

    public ReadableMap maxTextSize;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = false;
        try {
            result = super.onTouchEvent(event);
        } catch (Exception e) {

        }
        return result;
    }
}