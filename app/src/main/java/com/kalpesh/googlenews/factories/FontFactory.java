package com.kalpesh.googlenews.factories;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by abc on 02-10-2014.
 */
public class FontFactory {
    private static HashMap<String, Typeface> fontsHashMap = new HashMap<String, Typeface>();
    private static final String FONT_PATH = "fonts/";

    public static Typeface getFont(String font, Context context) {
        if (!fontsHashMap.containsKey(font)) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), FONT_PATH + font);
            fontsHashMap.put(font, typeface);
        }
        return fontsHashMap.get(font);
    }
}
