package com.example.chrisboynton.termproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TextActivity extends SingleFragmentActivity {

  @Override
    protected Fragment createFragment(){
      return new TextFragment();
  }
}
