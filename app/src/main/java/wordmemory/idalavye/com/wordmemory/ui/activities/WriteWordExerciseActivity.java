package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Random;

public class WriteWordExerciseActivity extends AppCompatActivity {

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
    private String deneme;
    private int our_word_p = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_word_exercise);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.exerciseBackgroundColor));

        init();
        event();
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
        our_word_p = 0;
        Random random = new Random();
        location = random.nextInt(list.size());
        questionWord = list.get(location).getMeaning();
        correctWord = list.get(location).getWord();

        word.setText(questionWord);
    }

    private void event() {

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ourWriteWord = input.getText().toString();
                if (ourWriteWord.equalsIgnoreCase(correctWord)) {
                    if (list.size() > 0) {
                        newQuestion();
                        progressBar.setProgress(progressBar.getProgress() + 1);
                        input.setText("");
                        list.remove(location);
                    } else {
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

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deneme = "";
                boolean check = true;
                for (int i = 0; i < ourWriteWord.length(); i++) {
                    if ((ourWriteWord.charAt(i) == correctWord.charAt(i))) {
                        deneme += correctWord.charAt(i);
                        check = true;
                    } else {
                        our_word_p = i;
                        check = false;
                        deneme += correctWord.charAt(i);
                        input.setText(deneme);
                        if (correctWord.length() > deneme.length())
                            input.setSelection(input.getText().length());
                        break;
                    }
                }

                if (check && (correctWord.length() > deneme.length())) {
                    deneme += correctWord.charAt(our_word_p);
                    if (correctWord.length() > deneme.length()) {
                        our_word_p++;
                    }
                    input.setText(deneme);
                    input.setSelection(input.getText().length());
                }
            }
        });

    }

    private void init() {
        close = findViewById(R.id.write_word_close_iw);
        progressBar = findViewById(R.id.write_word_pb);
        word = findViewById(R.id.write_word_tw);
        input = findViewById(R.id.write_word_et);
        list = WordListItemController.INSTANCE.getWords();
        hintButton = findViewById(R.id.write_word_hint_button);
    }
}