package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;

public class MatchWordExerciseActivity extends AppCompatActivity {

    private ImageView close;
    private RoundCornerProgressBar progressBar;
    private Button left_btn1, left_btn2, left_btn3, left_btn4, left_btn5, left_btn6, left_btn7, left_btn8;
    private Button rigt_btn1, rigt_btn2, rigt_btn3, rigt_btn4, rigt_btn5, rigt_btn6, rigt_btn7, rigt_btn8;

    private ArrayList<WordListItemModel> wordList;
    private ArrayList<String> words;
    private ArrayList<String> word_means;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_word_exercise);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.exerciseBackgroundColor));

        init();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar.setMax(wordList.size());
        progressBar.setProgress(0);

        newQuestion();
    }

    private void newQuestion() {
//        Random rand = new Random();
//
//
//
//        for (int i = 0; i < 8; i++) {
//            int r = rand.nextInt(wordList.size());
//            int r1 = rand.nextInt(wordList.size());
//            int r2 = rand.nextInt(wordList.size());
//
//        }
//
//        left_btn1.setText(words.get(0));
//        left_btn2.setText(words.get(1));
//        left_btn3.setText(words.get(2));
//        left_btn4.setText(words.get(3));
//        left_btn5.setText(words.get(4));
//        left_btn6.setText(words.get(5));
//        left_btn7.setText(words.get(6));
//        left_btn8.setText(words.get(7));
//
//        rigt_btn1.setText(word_means.get(0));
//        rigt_btn2.setText(word_means.get(1));
//        rigt_btn3.setText(word_means.get(2));
//        rigt_btn4.setText(word_means.get(3));
//        rigt_btn5.setText(word_means.get(4));
//        rigt_btn6.setText(word_means.get(5));
//        rigt_btn7.setText(word_means.get(6));
//        rigt_btn8.setText(word_means.get(7));
    }

    public void matchWordClickRightButtons(View view) {
        view.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));
    }

    public void matchWordClickLeftButtons(View view) {
        view.setBackgroundTintList(getResources().getColorStateList(R.color.questionColor));
    }

    private void init() {
        progressBar = findViewById(R.id.match_word_pb);
        close = findViewById(R.id.match_word_close_iw);
        left_btn1 = findViewById(R.id.match_word_btn1_left);
        left_btn2 = findViewById(R.id.match_word_btn2_left);
        left_btn3 = findViewById(R.id.match_word_btn3_left);
        left_btn4 = findViewById(R.id.match_word_btn4_left);
        left_btn5 = findViewById(R.id.match_word_btn5_left);
        left_btn6 = findViewById(R.id.match_word_btn6_left);
        left_btn7 = findViewById(R.id.match_word_btn7_left);
        left_btn8 = findViewById(R.id.match_word_btn8_left);
        rigt_btn1 = findViewById(R.id.match_word_btn1_right);
        rigt_btn2 = findViewById(R.id.match_word_btn2_right);
        rigt_btn3 = findViewById(R.id.match_word_btn3_right);
        rigt_btn4 = findViewById(R.id.match_word_btn4_right);
        rigt_btn5 = findViewById(R.id.match_word_btn5_right);
        rigt_btn6 = findViewById(R.id.match_word_btn6_right);
        rigt_btn7 = findViewById(R.id.match_word_btn7_right);
        rigt_btn8 = findViewById(R.id.match_word_btn8_right);
        wordList = WordListItemController.INSTANCE.getWords();
        words = new ArrayList<>();
        word_means = new ArrayList<>();

//        for (int i = 0; i < wordList.size(); i++) {
//            words.add(wordList.get(i).getWord());
//        }
//
//        for (int i = 0; i < wordList.size(); i++) {
//            word_means.add(wordList.get(i).getWord_mean());
//        }
    }

}


