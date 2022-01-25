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
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Integer readStyleWidth = readStyleScale(node, "width");
        Integer readStyleHeight = readStyleScale(node, "height");
        if (readStyleWidth != null && readStyleHeight != null) {
            width = readStyleWidth;
            height = readStyleHeight;
        } else if (readStyleWidth != null) {
            height = readStyleWidth * height / width;
            width = readStyleWidth;
        } else if (readStyleHeight != null) {
            width = readStyleHeight * width / height;
            height = readStyleHeight;
        }
        if (readStyleWidth != null || readStyleHeight != null) {
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

    private Integer readStyleScale(TagNode node, String attributedName) {
        String scaleString = node.getAttributeByName(attributedName);
        if (scaleString == null) {
            return null;
        }
        int reloadScale = (int)Double.parseDouble(scaleString.substring(0, scaleString.length() - 2));
        if (scaleString.endsWith("pt")) {
            reloadScale = (int) PixelUtil.toPixelFromDIP(reloadScale);
        }
        return reloadScale;
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
