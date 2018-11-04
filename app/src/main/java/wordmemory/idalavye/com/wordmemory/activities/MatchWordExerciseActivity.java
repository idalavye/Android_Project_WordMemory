package wordmemory.idalavye.com.wordmemory.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import wordmemory.idalavye.com.wordmemory.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MatchWordExerciseActivity extends AppCompatActivity {

    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_word_exercise);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.exerciseBackgroundColor));

        init();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void matchWordClickRightButtons(View view){

    }

    public void matchWordClickLeftButtons(View view){

    }

    private void init() {
        close = findViewById(R.id.match_word_close_iw);
    }
}
