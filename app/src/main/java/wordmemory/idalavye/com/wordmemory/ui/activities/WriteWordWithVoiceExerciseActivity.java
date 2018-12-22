package wordmemory.idalavye.com.wordmemory.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.StatisticController;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;
import wordmemory.idalavye.com.wordmemory.utils.DatabaseBuilder;

public class WriteWordWithVoiceExerciseActivity extends AppCompatActivity {

    private TextToSpeech mTTS;
    private ImageView write_word_with_voice_play_button;
    private RoundCornerProgressBar progressBar;
    private ImageView close;
    private TextInputEditText input;
    private MaterialButton hintButton;
    private TextInputLayout inputLayout;

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
        setContentView(R.layout.activity_write_word_with_voice_exercise);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.exerciseBackgroundColor));

        init();
        events();
        progressBar.setMax(list.size());
        progressBar.setProgress(0);

        newQuestion();
    }

    private void newQuestion() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(
                InputMethodManager.SHOW_FORCED, 0);

        inputLayout.setBoxBackgroundColor(getResources().getColor(R.color.writeWordLayoutBackground));
        input.setText("");
        input.setEnabled(true);

        repeatedWord++;
        our_word_p = 0;
        Random random = new Random();
        location = random.nextInt(list.size());
        questionWord = list.get(location).getWord();
        correctWord = questionWord;
        speak();
    }


    private void init() {
        write_word_with_voice_play_button = findViewById(R.id.write_word_with_voice_play_button);
        list = WordListItemController.INSTANCE.getNotLearningWords();
        close = findViewById(R.id.write_word_with_voice_close_page_cwm);
        progressBar = findViewById(R.id.write_word_with_voice_pb);
        input = findViewById(R.id.write_word_with_voice_et);
        hintButton = findViewById(R.id.write_word_with_voice_hint_button);
        repeatedWord = StatisticController.INSTANCE.getStatisticsForCurrentUser().getTotalRepeated();
        correctRepeatedWord = StatisticController.INSTANCE.getStatisticsForCurrentUser().getTotalCorrectRepeated();
        inputLayout = findViewById(R.id.write_word_with_voice_et_layout);
    }

    private void events() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        //button senEnable true
                    }
                } else {
                    Log.e("TTS", "initialization failed");
                }
            }
        });

        write_word_with_voice_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ourWriteWord = input.getText().toString();
                final int size = list.size();
                if (ourWriteWord.equalsIgnoreCase(correctWord)) {
                    if (size > 0) {
                        correctRepeatedWord++;
                        progressBar.setProgress(progressBar.getProgress() + 1);
                        list.get(location).setWord_progress(list.get(location).getWord_progress() + 1);
                        DatabaseBuilder.INSTANCE.updateWordItem(list.get(location), "word_progress");
                        list.remove(location);
                        inputLayout.setBoxBackgroundColor(getResources().getColor(R.color.correctAnswer));
                        input.setEnabled(false);

                        final int delay = size > 1 ? 1500 : 200;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (size > 1)
                                    newQuestion();
                                else {
                                    input.setEnabled(false);
                                    input.setText(":)");
                                    hintButton.setEnabled(false);
                                }
                            }
                        }, delay);
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

    private void speak() {
        mTTS.speak(questionWord, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        WordListItemController.INSTANCE.pullWordItems();
        StatisticController.INSTANCE.updateStatisticsWithRepeatedAndCorrectRepeated(repeatedWord,correctRepeatedWord);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        super.onDestroy();
    }
}
