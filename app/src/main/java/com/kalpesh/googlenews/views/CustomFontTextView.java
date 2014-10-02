package com.kalpesh.googlenews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kalpesh.googlenews.R;
import com.kalpesh.googlenews.factories.FontFactory;

/**
 * Created by abc on 02-10-2014.
 */
public class CustomFontTextView extends TextView{
    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        consumeAttrs(attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        consumeAttrs(attrs);
    }

    private void consumeAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomFontTextView, 0, 0);
        try {
            String font  = typedArray.getString(R.styleable.CustomFontTextView_font);
            Typeface typeface = FontFactory.getFont(font, getContext());
            if(typeface != null){
                setTypeface(typeface);
            }
        } finally {
            typedArray.recycle();
        }
    }
}
