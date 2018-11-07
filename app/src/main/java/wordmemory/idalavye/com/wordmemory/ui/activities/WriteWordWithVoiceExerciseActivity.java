package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class WriteWordWithVoiceExerciseActivity extends AppCompatActivity {

    private TextToSpeech mTTS;
    private ImageView write_word_with_voice_play_button;
    private RoundCornerProgressBar progressBar;
    private ImageView close;
    private TextInputEditText input;
    private MaterialButton hintButton;

    private ArrayList<WordListItemModel> list;
    private String questionWord;
    private String correctWord;
    private String ourWriteWord = "";
    private int location;
    private String checkWord;
    private int our_word_p = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_word_with_voice_exercise);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.exerciseBackgroundColor));

        init();
        events();
        progressBar.setMax(list.size());
        progressBar.setProgress(0);
        
        newQuestion();
    }

    private void newQuestion() {
        our_word_p = 0;
        Random random = new Random();
        location = random.nextInt(list.size());
        questionWord = list.get(location).getWord();
        correctWord = questionWord;
        speak();
    }


    private void init() {
        write_word_with_voice_play_button = findViewById(R.id.write_word_with_voice_play_button);
        list = WordListItemController.INSTANCE.getWords();
        close = findViewById(R.id.write_word_with_voice_close_page_cwm);
        progressBar = findViewById(R.id.write_word_with_voice_pb);
        input = findViewById(R.id.write_word_with_voice_et);
        hintButton = findViewById(R.id.write_word_with_voice_hint_button);
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
                    }else{
                        //button senEnable true
                    }
                }else{
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

    private void speak(){
        mTTS.speak(questionWord,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }
}
