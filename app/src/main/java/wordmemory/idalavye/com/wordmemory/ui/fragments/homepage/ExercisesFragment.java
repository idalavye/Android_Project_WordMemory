package wordmemory.idalavye.com.wordmemory.ui.fragments.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.ui.activities.FindCorrectTranslateExerciseActiviy;
import wordmemory.idalavye.com.wordmemory.ui.activities.FindCorrectWorldMeanExerciseActivity;
import wordmemory.idalavye.com.wordmemory.ui.activities.WriteWordExerciseActivity;
import wordmemory.idalavye.com.wordmemory.ui.activities.WriteWordMeanExerciseActivity;
import wordmemory.idalavye.com.wordmemory.ui.activities.WriteWordWithVoiceExerciseActivity;

public class ExercisesFragment extends Fragment {
    // TODO Move this out of the static field
    public static LinearLayout exercise_fragment;
    private CardView find_correct_translate;
    private CardView find_correct_word_mean;
    private CardView match_word_exercise;
    private CardView write_word_mean_exercise;
    private CardView write_word_exercise;
    private CardView write_word_with_voice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.exercises_fragment, container, false);
        exercise_fragment = view.findViewById(R.id.exercises_fragment);

        find_correct_translate = view.findViewById(R.id.find_correct_translate);
        find_correct_word_mean = view.findViewById(R.id.find_correct_word_mean);
        write_word_mean_exercise = view.findViewById(R.id.write_word_mean_exercise);
        write_word_exercise = view.findViewById(R.id.write_word_exercise);
        write_word_with_voice = view.findViewById(R.id.write_word_with_voice);

        find_correct_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), FindCorrectTranslateExerciseActiviy.class);
                startActivity(intent);
            }
        });

        find_correct_word_mean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), FindCorrectWorldMeanExerciseActivity.class);
                startActivity(intent);
            }
        });

        write_word_mean_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), WriteWordMeanExerciseActivity.class);
                startActivity(intent);
            }
        });

        write_word_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), WriteWordExerciseActivity.class);
                startActivity(intent);
            }
        });

        write_word_with_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),WriteWordWithVoiceExerciseActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
