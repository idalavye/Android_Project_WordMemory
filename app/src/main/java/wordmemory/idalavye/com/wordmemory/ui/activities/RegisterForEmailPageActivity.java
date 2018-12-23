package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.security.EmailLogin;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterForEmailPageActivity extends AppCompatActivity {

    private Context mContext;
    private String email,username,password;
    private EditText mEmail,mUsername,mPassword;
    private Button btnRegister;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_email_page);

        //TextView loginTextView = findViewById(R.id.loginTextView);
        //Button btnRegister = findViewById(R.id.btnRegister);

        //EmailLogin.email_btnRegister(btnRegister,RegisterForEmailPageActivity.this);
        //EmailLogin.email_loginTextView(loginTextView,RegisterForEmailPageActivity.this);
    }
}
