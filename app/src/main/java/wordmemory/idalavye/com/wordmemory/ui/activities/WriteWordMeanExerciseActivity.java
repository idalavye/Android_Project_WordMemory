package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.StatisticController;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;
import wordmemory.idalavye.com.wordmemory.utils.Animations;
import wordmemory.idalavye.com.wordmemory.utils.DatabaseBuilder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private LinearLayout layout;

    private ArrayList<WordListItemModel> list;
    private String questionWord;
    private String correctWord;
    private String ourWriteWord = "";
    private int location;
    private String checkWord;
    private int our_word_p = 0;
    private int repeatedWord;
    private int correctRepeatedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_word_mean_exercise);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.exerciseBackgroundColor));

        init();
        events();

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

    private void events() {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ourWriteWord = input.getText().toString();
                if (ourWriteWord.equalsIgnoreCase(correctWord)) {
                    if (list.size() > 0) {
                        correctRepeatedWord++;
                        progressBar.setProgress(progressBar.getProgress() + 1);
                        list.get(location).setWord_progress(list.get(location).getWord_progress() + 1);
                        DatabaseBuilder.INSTANCE.updateWordItem(list.get(location), "word_progress");
                        list.remove(location);
                        newQuestion();
                        input.setText("");
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
                checkWord = "";
                boolean check = true;
                for (int i = 0; i < ourWriteWord.length(); i++) {
                    if ((ourWriteWord.charAt(i) == correctWord.charAt(i))) {
                        checkWord += correctWord.charAt(i);
                        check = true;
                    } else {
                        our_word_p = i;
                        check = false;
                        checkWord += correctWord.charAt(i);
                        input.setText(checkWord);
                        if (correctWord.length() > checkWord.length())
                            input.setSelection(input.getText().length());
                        break;
                    }
                }

                if (check && (correctWord.length() > checkWord.length())) {
                    checkWord += correctWord.charAt(our_word_p);
                    if (correctWord.length() > checkWord.length()) {
                        our_word_p++;
                    }
                    input.setText(checkWord);
                    input.setSelection(input.getText().length());
                }
            }
        });
    }

    private void newQuestion() {
        repeatedWord++;
        layout.startAnimation(Animations.createFadeInAnimation(getApplicationContext(), 1500));
        our_word_p = 0;
        Random random = new Random();
        location = random.nextInt(list.size());
        questionWord = list.get(location).getWord();
        correctWord = list.get(location).getMeaning();

        word.setText(questionWord);
    }

    private void init() {
        close = findViewById(R.id.write_word_mean_close_iw);
        progressBar = findViewById(R.id.write_word_mean_pb);
        word = findViewById(R.id.write_word_mean_tw);
        input = findViewById(R.id.write_word_mean_et);
        list = WordListItemController.INSTANCE.getWords();
        hintButton = findViewById(R.id.write_word_mean_hint_button);
        layout = findViewById(R.id.write_word_mean_layout);
        repeatedWord = StatisticController.INSTANCE.getStatisticsForCurrentUser().getTotalRepeated();
        correctRepeatedWord = StatisticController.INSTANCE.getStatisticsForCurrentUser().getTotalCorrectRepeated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WordListItemController.INSTANCE.pullWordItems();
        StatisticController.INSTANCE.updateStatisticsWithRepeatedAndCorrectRepeated(repeatedWord,correctRepeatedWord);
    }
}
