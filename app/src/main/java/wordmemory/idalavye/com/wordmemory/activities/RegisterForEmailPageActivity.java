package wordmemory.idalavye.com.wordmemory.activities;

import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.security.EmailLogin;

import android.os.Bundle;
import android.widget.TextView;

public class RegisterForEmailPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_email_page);

        TextView loginTextView = findViewById(R.id.loginTextView);
        EmailLogin.email_loginTextView(loginTextView,RegisterForEmailPageActivity.this);
    }
}
