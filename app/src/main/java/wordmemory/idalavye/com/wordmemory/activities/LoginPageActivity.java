package wordmemory.idalavye.com.wordmemory.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseUser;


import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.security.FacebookLogin;
import wordmemory.idalavye.com.wordmemory.utils.Login;

public class LoginPageActivity extends AppCompatActivity {

    private Button facebook_loginButton;
    private Button google_loginButton;
    private Button email_loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        init();

        FacebookLogin.facebook_login(facebook_loginButton, LoginPageActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        Login.mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = Login.mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }
    }

    private void updateUI() {
        Toast.makeText(LoginPageActivity.this, "Hello You are login...", Toast.LENGTH_SHORT).show();

        Intent accountIntent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(accountIntent);
        finish();
    }

    private void init() {
        facebook_loginButton = findViewById(R.id.login_with_facebook);
        google_loginButton = findViewById(R.id.connect_with_google);
        email_loginButton = findViewById(R.id.login_with_email);
    }

}
