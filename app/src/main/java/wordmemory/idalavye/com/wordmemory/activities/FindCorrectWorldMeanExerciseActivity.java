package wordmemory.idalavye.com.wordmemory.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;
import wordmemory.idalavye.com.wordmemory.utils.Animations;
import wordmemory.idalavye.com.wordmemory.utils.Common;

public class FindCorrectWorldMeanExerciseActivity extends AppCompatActivity {

    private ImageView close;
    private Button btn2, btn3, btn4;
    private MaterialButton btn1;
    private TextView word;
    private RoundCornerProgressBar progressBar;
    private int correct_answer_location;
    private int ourWord;
    private LinearLayout find_cwm_word_layout;

    private ArrayList<String> answers = new ArrayList<>();
    private ArrayList<WordListItemModel> list;
    private ArrayList<WordListItemModel> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_correct_world_mean_exercise);

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
        close = findViewById(R.id.find_correct_translate_close_page_cwm);
        btn1 = findViewById(R.id.find_cwm_btn1);
        btn2 = findViewById(R.id.find_cwm_btn2);
        btn3 = findViewById(R.id.find_cwm_btn3);
        btn4 = findViewById(R.id.find_cwm_btn4);
        word = findViewById(R.id.find_cwm_word);
        progressBar = findViewById(R.id.find_cwm_pb);
        list = Common.getArrayList();
        questions = new ArrayList<>(list);
        find_cwm_word_layout = findViewById(R.id.find_cwm_word_layout);
    }

    public void choosingAnswerCWM(View view) {
        if (view.getTag().toString().equals(String.valueOf(correct_answer_location))) {
            view.setBackgroundTintList(getResources().getColorStateList(R.color.correctAnswer));
            find_cwm_word_layout.startAnimation(Animations.createFadeInAnimation(getApplicationContext(), 1500));
            questions.remove(ourWord);
            progressBar.setProgress(progressBar.getProgress() + 1);
            newQuestion();
        } else {
            view.setBackgroundTintList(getResources().getColorStateList(R.color.wrongAnswer));
            view.setEnabled(false);
        }
    }

    private void newQuestion() {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn1.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));
        btn2.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));
        btn3.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));
        btn4.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));

        if (questions.size() > 0) {
            Random random = new Random();
            ourWord = random.nextInt(questions.size());
            correct_answer_location = random.nextInt(4);
            answers.clear();
            word.setText(questions.get(ourWord).getWord_mean());
            for (int i = 0; i < 4; i++) {
                if (i == correct_answer_location) {
                    answers.add(questions.get(ourWord).getWord());
                } else {
                    int wrongAnswer = random.nextInt(list.size());
                    while (answers.contains(list.get(wrongAnswer).getWord()) ||
                            questions.get(ourWord).getWord().contains(list.get(wrongAnswer).getWord())) {
                        wrongAnswer = random.nextInt(list.size());
                    }
                    answers.add(list.get(wrongAnswer).getWord());
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
            word.setText(getString(R.string.all_words_were_studied));
        }
    }

}
