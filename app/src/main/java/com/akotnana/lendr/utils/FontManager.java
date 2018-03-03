package com.akotnana.lendr.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by anees on 2/6/2018.
 */

public class FontManager {
    Context context;
    public static Typeface typeFaceLight;
    public static Typeface typeFaceRegular;
    public static Typeface typeFaceBold;

    public FontManager(Context context) {
        this.context = context;
        this.typeFaceLight = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Light.ttf");
        this.typeFaceRegular = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Regular.ttf");
        this.typeFaceBold = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Bold.ttf");
    }

    public Typeface getTypeFaceLight() {
        return this.typeFaceLight;
    }

    public Typeface getTypeFaceRegular() {
        return this.typeFaceRegular;
    }

    public Typeface getTypeFaceBold() {
        return this.typeFaceBold;
    }
}