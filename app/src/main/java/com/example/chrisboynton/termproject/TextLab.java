package com.example.chrisboynton.termproject;

import android.content.Context;

/**
 * Created by chrisboynton on 12/12/17.
 */

public class TextLab {
    private static TextLab sTextLab;

    public static TextLab get(Context context){
        if(sTextLab == null){
            sTextLab = new TextLab(context);
        }
        return sTextLab;
    }

    private TextLab(Context context){

    }
}
