package com.extremekod.hotelsny;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class ImageSizeTransformation implements Transformation {

    private static final int START_PIXEL = 1;
    private static final int BAD_PIXELS = 2;

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth() - BAD_PIXELS;
        int height = source.getHeight() - BAD_PIXELS;

        // Уменьшаем картинку на 1px с кажой стороны, что бы убрать красную рамку по периметру
        Bitmap result = Bitmap.createBitmap(source, START_PIXEL, START_PIXEL, width, height);

        if (result != source) {
            source.recycle();
        }

        return result;
    }

    @Override
    public String key() {
        return "ImageSize";
    }


}
