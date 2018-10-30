package wordmemory.idalavye.com.wordmemory.fragments.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import wordmemory.idalavye.com.wordmemory.R;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    public static BottomNavigationDrawerFragment getInstance(){
        return new BottomNavigationDrawerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_navigation_drawer_fragment, container, false);
    }

}
