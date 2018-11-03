package wordmemory.idalavye.com.wordmemory.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;
import wordmemory.idalavye.com.wordmemory.utils.Common;

public class FindCorrectTranslateExerciseActiviy extends AppCompatActivity {

    ImageView close;
    Button btn1, btn2, btn3, btn4;
    TextView word;
    RoundCornerProgressBar progressBar;
    int correct_answer_location;
    int ourWord;

    ArrayList<String> answers = new ArrayList<>();
    private ArrayList<WordListItemModel> list;
    private ArrayList<WordListItemModel> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_correct_translate_exercise_activiy);

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

    private void init() {
        close = findViewById(R.id.find_correct_translate_close_page_iw);
        btn1 = findViewById(R.id.find_ct_btn1);
        btn2 = findViewById(R.id.find_ct_btn2);
        btn3 = findViewById(R.id.find_ct_btn3);
        btn4 = findViewById(R.id.find_ct_btn4);
        word = findViewById(R.id.find_ct_word);
        progressBar = findViewById(R.id.find_ct_pb);
        list = Common.getArrayList();
        questions = new ArrayList<>(list);
    }

    public void choosingAnswer(View view) {
        if (view.getTag().toString().equals(String.valueOf(correct_answer_location))) {
            Toast.makeText(getApplicationContext(), String.valueOf(R.string.right_answer), Toast.LENGTH_SHORT).show();
            questions.remove(ourWord);
            progressBar.setProgress(progressBar.getProgress() + 1);
            newQuestion();
        }
    }

    public void newQuestion() {

        if (questions.size() > 0) {

            Random random = new Random();
            ourWord = random.nextInt(questions.size());
            correct_answer_location = random.nextInt(4);
            answers.clear();

            word.setText(questions.get(ourWord).getWord());

            for (int i = 0; i < 4; i++) {
                if (i == correct_answer_location) {
                    answers.add(questions.get(ourWord).getWord_mean());
                } else {
                    int wrongAnswer = random.nextInt(list.size());
                    while (answers.contains(list.get(wrongAnswer).getWord_mean()) ||
                            questions.get(ourWord).getWord_mean().contains(list.get(wrongAnswer).getWord_mean())) {
                        wrongAnswer = random.nextInt(list.size());
                    }
                    answers.add(list.get(wrongAnswer).getWord_mean());
                }
            }
            btn1.setText(answers.get(0));
            btn2.setText(answers.get(1));
            btn3.setText(answers.get(2));
            btn4.setText(answers.get(3));

        } else {

            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
            btn4.setEnabled(false);

            word.setText(String.valueOf(R.string.all_words_were_studied));
        }
    }
}
