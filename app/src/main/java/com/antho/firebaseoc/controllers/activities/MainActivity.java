package com.antho.firebaseoc.controllers.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.antho.firebaseoc.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public int getFragmentLayout() {
        return 0;
    }
}
