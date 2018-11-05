package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Random;

public class WriteWordMeanExerciseActivity extends AppCompatActivity {

    private RoundCornerProgressBar progressBar;
    private ImageView close;
    private TextView word;
    private TextInputEditText input;
    private MaterialButton hintButton;

    private ArrayList<WordListItemModel> list;
    private String questionWord;
    private String correctWord;
    private String ourWriteWord = "";
    private int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_word_mean_exercise);

        init();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar.setMax(list.size());
        progressBar.setProgress(0);

        newQuestion();
    }

    private void newQuestion() {
        Random random = new Random();
        location = random.nextInt(list.size());

        questionWord = list.get(location).getWord();
        correctWord = list.get(location).getMeaning();

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ourWriteWord = input.getText().toString();
                if (ourWriteWord.equalsIgnoreCase(correctWord)){
                    if (list.size()>0) {
                        newQuestion();
                        progressBar.setProgress(progressBar.getProgress() + 1);
                        input.setText("");
                        list.remove(location);
                    }else{
                        input.setEnabled(false);
                        input.setText(":)");
                        hintButton.setEnabled(false);
                        word.setText(getString(R.string.all_words_were_studied));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        word.setText(questionWord);
    }

    private void init() {
        close = findViewById(R.id.write_word_mean_close_iw);
        progressBar = findViewById(R.id.write_word_mean_pb);
        word = findViewById(R.id.write_word_mean_tw);
        input = findViewById(R.id.write_word_mean_et);
        list = WordListItemController.INSTANCE.getWords();
        hintButton = findViewById(R.id.write_word_mean_hint_button);
    }
}