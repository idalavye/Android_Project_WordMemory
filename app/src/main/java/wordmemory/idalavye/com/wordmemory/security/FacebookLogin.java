package wordmemory.idalavye.com.wordmemory.security;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import androidx.annotation.NonNull;
import wordmemory.idalavye.com.wordmemory.ui.activities.HomePageActivity;
import wordmemory.idalavye.com.wordmemory.utils.Login;

public class FacebookLogin {

    public static void facebook_login(final View loginButton, final Activity activity) {
        // Initialize Firebase Auth
        Login.mAuth = FirebaseAuth.getInstance();

        // Initialize Facebook Login button
        Login.mCallbackManager = CallbackManager.Factory.create();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.setEnabled(false);
                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(Login.mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        AccessToken token = loginResult.getAccessToken();

                        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                        Login.mAuth.signInWithCredential(credential)
                                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            loginButton.setEnabled(true);
                                            Intent intent = new Intent(activity, HomePageActivity.class);
                                            activity.startActivity(intent);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(activity, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            loginButton.setEnabled(true);
                                            Intent intent = new Intent(activity, HomePageActivity.class);
                                            activity.startActivity(intent);
                                        }

                                    }
                                });
                    }

                    @Override
                    public void onCancel() {
                        Log.d("FACELOG", "facebook:onCancel");
                        loginButton.setEnabled(true);
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("FACELOG", "facebook:onError", error);
                        Toast.makeText(activity, "Giriş Yapılamadı. Lütfen İnternet bağlantınızı kontrol ediniz.", Toast.LENGTH_LONG).show();
                        loginButton.setEnabled(true);
                        // ...
                    }
                });
            }
        });
    }


}
