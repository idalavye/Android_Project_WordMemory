package wordmemory.idalavye.com.wordmemory.fragments.homepage;

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
import wordmemory.idalavye.com.wordmemory.activities.FindCorrectTranslateExerciseActiviy;
import wordmemory.idalavye.com.wordmemory.activities.FindCorrectWorldMeanExerciseActivity;

public class ExercisesFragment extends Fragment {

    public static LinearLayout exercise_fragment;
    private CardView find_correct_translate;
    private CardView find_correct_word_mean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.exercises_fragment, container, false);
        exercise_fragment = view.findViewById(R.id.exercises_fragment);

        find_correct_translate = view.findViewById(R.id.find_correct_translate);
        find_correct_word_mean = view.findViewById(R.id.find_correct_word_mean);

        find_correct_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),FindCorrectTranslateExerciseActiviy.class);
                startActivity(intent);
            }
        });

        find_correct_word_mean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),FindCorrectWorldMeanExerciseActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
