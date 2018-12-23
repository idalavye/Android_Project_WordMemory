package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.security.EmailLogin;
import wordmemory.idalavye.com.wordmemory.utils.FirebaseMethods;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        mContext = RegisterForEmailPageActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);

        initWidgets();
        setupFirebaseAuth();
        init();

        //TextView loginTextView = findViewById(R.id.loginTextView);
        //Button btnRegister = findViewById(R.id.btnRegister);

        //EmailLogin.email_btnRegister(btnRegister,RegisterForEmailPageActivity.this);
        //EmailLogin.email_loginTextView(loginTextView,RegisterForEmailPageActivity.this);
    }

    private void init(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();

                if(isStringNull(email) || isStringNull(username) || isStringNull(password)){
                    Toast.makeText(mContext, "Fill All Fields", Toast.LENGTH_SHORT).show();

                }else{
                    firebaseMethods.registerNewEmail(email,password,username);
                }

            }
        });
    }

    /*
initialize the activity's widgets
 */
    private void initWidgets(){
        mEmail = findViewById(R.id.emailEditText);
        mUsername = findViewById(R.id.nameEditText);
        mPassword = findViewById(R.id.passwordEditText);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private boolean isStringNull(String string){
        if(string.equals("")){
            return true;
        }else{
            return false;
        }
    }

    /**
     *  -------------------------------    Firebase --------------------------------------
     */

    /**
     *  Setup the firebase authentication
     */
    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
