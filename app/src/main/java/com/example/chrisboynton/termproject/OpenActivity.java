package com.example.chrisboynton.termproject;

import android.support.v4.app.Fragment;

/**
 * Created by chrisboynton on 12/12/17.
 */

public class OpenActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new OpenFragment();
    }
}
