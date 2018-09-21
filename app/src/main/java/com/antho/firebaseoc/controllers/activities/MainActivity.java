package com.antho.firebaseoc.controllers.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.antho.firebaseoc.R;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

import butterknife.OnClick;

import static com.firebase.ui.auth.AuthUI.*;

public class MainActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    public int getFragmentLayout() {
        return 0;
    }

    @OnClick(R.id.main_activity_button_login)
    public void onClickLoginButton() {
        this.startSignInActivity();
    }

    public void startSignInActivity() {
        startActivityForResult(
                getInstance()
                    .createSignInIntentBuilder()
                    .setTheme(R.style.LoginTheme)
                    .setAvailableProviders(Arrays.asList(new IdpConfig.Builder(EMAIL_PROVIDER).build()))
                    .setIsSmartLockEnabled(false, true)
                    .setLogo(R.drawable.ic_logo_auth)
                    .build(),
                RC_SIGN_IN
        );
    }
}
