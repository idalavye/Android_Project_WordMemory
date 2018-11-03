package wordmemory.idalavye.com.wordmemory.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.security.FacebookLogin;
import wordmemory.idalavye.com.wordmemory.utils.Login;

public class LoginPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button facebook_loginButton = findViewById(R.id.login_with_facebook);

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
        Toast.makeText(LoginPageActivity.this, String.valueOf(R.string.logged_in), Toast.LENGTH_SHORT).show();

        Intent accountIntent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(accountIntent);
        finish();
    }
}
