package wordmemory.idalavye.com.wordmemory.security;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import wordmemory.idalavye.com.wordmemory.ui.activities.LoginPageActivity;
import wordmemory.idalavye.com.wordmemory.ui.activities.RegisterForEmailPageActivity;

public class EmailLogin {
    public static void email_login(final View loginButton, final Activity activity) {
        final Intent intent = new Intent(activity, RegisterForEmailPageActivity.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(intent);
            }
        });
    }

    public static void email_loginTextView(final View loginTextView, final Activity activity) {
        final Intent intent = new Intent(activity, LoginPageActivity.class);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(intent);
            }
        });
    }

    public static void email_btnRegister(final View btnRegister, final Activity activity) {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //firebase email login
            }
        });
    }

}
