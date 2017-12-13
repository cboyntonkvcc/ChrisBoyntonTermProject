package com.example.chrisboynton.termproject;

import android.support.v4.app.Fragment;

/**
 * Created by chrisboynton on 12/1/17.
 */

public class ContactActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new ContactFragment();
    }
}
