package wordmemory.idalavye.com.wordmemory.ui.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.StatisticController;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;
import wordmemory.idalavye.com.wordmemory.utils.Animations;
import wordmemory.idalavye.com.wordmemory.utils.CommonTimer;
import wordmemory.idalavye.com.wordmemory.utils.DatabaseBuilder;

public class FindCorrectTranslateExerciseActiviy extends AppCompatActivity {

    private ImageView close;
    private Button btn2, btn3, btn4;
    private MaterialButton btn1;
    private TextView word;
    private RoundCornerProgressBar progressBar;
    private RingProgressBar ringProgressBar;
    private int correct_answer_location;
    private int ourWord;
    private LinearLayout find_ct_word_layout;

    private ArrayList<String> answers = new ArrayList<>();
    private ArrayList<WordListItemModel> list;
    private ArrayList<WordListItemModel> questions;
    CommonTimer commonTimer;
    private int repeatedWord;
    private int correctRepeatedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_correct_translate_exercise_activiy);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.exerciseBackgroundColor));

        init();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar.setMax(list.size());
        progressBar.setProgress(0);

        ringProgressBar.setOnProgressListener(new RingProgressBar.OnProgressListener() {
            @Override
            public void progressToComplete() {
                newQuestion();
            }
        });

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
        list = WordListItemController.INSTANCE.getWords();
        questions = new ArrayList<>(list);
        find_ct_word_layout = findViewById(R.id.find_ct_word_layout);
        ringProgressBar = findViewById(R.id.find_ct_ringProgress);
        commonTimer = new CommonTimer();
        repeatedWord = StatisticController.INSTANCE.getStatisticsForCurrentUser().getTotalRepeated();
        correctRepeatedWord = StatisticController.INSTANCE.getStatisticsForCurrentUser().getTotalCorrectRepeated();
    }

    public void choosingAnswer(View view) {
        repeatedWord++;
        if (view.getTag().toString().equals(String.valueOf(correct_answer_location))) {
            correctRepeatedWord++;
            view.setBackgroundTintList(getResources().getColorStateList(R.color.correctAnswer));
            questions.get(ourWord).setWord_progress(questions.get(ourWord).getWord_progress() + 1);
            DatabaseBuilder.INSTANCE.updateWordItem(questions.get(ourWord), "word_progress");
            questions.remove(ourWord);
            progressBar.setProgress(progressBar.getProgress() + 1);
            commonTimer.cancelTimer();
            newQuestion();
        } else {
            view.setBackgroundTintList(getResources().getColorStateList(R.color.wrongAnswer));
            view.setEnabled(false);
        }
    }

    public void newQuestion() {

        find_ct_word_layout.startAnimation(Animations.createFadeInAnimation(getApplicationContext(), 1500));
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn1.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));
        btn2.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));
        btn3.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));
        btn4.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));

        if (questions.size() > 0) {
            ringProgressBar.setProgress(0);
            commonTimer.startTimer(ringProgressBar, ringProgressBar.getMax() * 1000 + 1000, 1000);

            Random random = new Random();
            ourWord = random.nextInt(questions.size());
            correct_answer_location = random.nextInt(4);
            answers.clear();

            word.setText(questions.get(ourWord).getWord());
            for (int i = 0; i < 4; i++) {
                if (i == correct_answer_location) {
                    answers.add(questions.get(ourWord).getMeaning());
                } else {
                    int wrongAnswer = random.nextInt(list.size());
                    while (answers.contains(list.get(wrongAnswer).getMeaning()) ||
                            questions.get(ourWord).getMeaning().contains(list.get(wrongAnswer).getMeaning())) {
                        wrongAnswer = random.nextInt(list.size());
                    }
                    answers.add(list.get(wrongAnswer).getMeaning());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WordListItemController.INSTANCE.pullWordItems();
        StatisticController.INSTANCE.updateStatisticsAfterExercise("totalRepeated",repeatedWord);
        StatisticController.INSTANCE.updateStatisticsAfterExercise("totalCorrectRepeated",correctRepeatedWord);
    }
}
