package wordmemory.idalavye.com.wordmemory.fragments.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.adapters.ExpandableListViewAdapter;
import wordmemory.idalavye.com.wordmemory.utils.Common;

public class WordsListingFragment extends Fragment {

    public static ExpandableListView listView;
    public static ExpandableListViewAdapter adapter;
    private int lastExpandedPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.words_listing_fragment, container, false);

        init(view);

        listView.setNestedScrollingEnabled(true);
        listView.setAdapter(adapter);
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    listView.collapseGroup(lastExpandedPosition);
                }

                lastExpandedPosition = groupPosition;
            }
        });

        return view;
    }

    private void init(View view) {
        listView = view.findViewById(R.id.words_listView);
        adapter = new ExpandableListViewAdapter(getActivity(), Common.getArrayList());
    }


}
