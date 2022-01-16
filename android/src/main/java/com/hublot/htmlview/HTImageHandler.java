package com.hublot.htmlview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import com.facebook.react.uimanager.PixelUtil;
import net.nightwhistler.htmlspanner.handlers.ImageHandler;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.net.URL;

import static android.graphics.Bitmap.Config.RGB_565;

public class HTImageHandler extends ImageHandler {

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end) {
        String src = node.getAttributeByName("src");

        builder.append("\uFFFC");

        Bitmap bitmap = loadBitmap(src);
        String widthString = node.getAttributeByName("width");
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        if (widthString != null && widthString.endsWith("pt")) {
            int reloadWidth = (int)Double.parseDouble(widthString.substring(0, widthString.length() - 2));
            reloadWidth = (int) PixelUtil.toPixelFromDIP(reloadWidth);
            height = height * reloadWidth / width;
        	width = reloadWidth;
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        }

        if (bitmap != null) {
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, width,
                height);
            builder.setSpan(new ImageSpan(drawable), start, end + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    protected Bitmap loadBitmap(String url) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = RGB_565;
            return BitmapFactory.decodeStream(new URL(url).openStream(), null, options);
        } catch (IOException io) {
            return null;
        }
    }

}
