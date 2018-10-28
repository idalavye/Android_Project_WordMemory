package wordmemory.idalavye.com.wordmemory.fragments.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import wordmemory.idalavye.com.wordmemory.R;

public class ExercisesFragment extends Fragment {

    public static LinearLayout exercise_fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercises_fragment, container, false);
        exercise_fragment = view.findViewById(R.id.exercises_fragment);

        return view;
    }
}
