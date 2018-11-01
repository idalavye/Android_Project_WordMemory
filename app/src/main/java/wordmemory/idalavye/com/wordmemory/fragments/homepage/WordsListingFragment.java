package wordmemory.idalavye.com.wordmemory.fragments.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.adapters.ExpandableListViewAdapter;
import wordmemory.idalavye.com.wordmemory.adapters.WordsListViewAdapter;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;

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
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition){
                    listView.collapseGroup(lastExpandedPosition);
                }

                lastExpandedPosition = groupPosition;
            }
        });

        return view;
    }

    private void init(View view) {
        this.listView = view.findViewById(R.id.words_listView);
        adapter = new ExpandableListViewAdapter(getActivity(), getArrayList());
    }

    private ArrayList<WordListItemModel> getArrayList() {
        ArrayList<WordListItemModel> list = new ArrayList<>();

        list.add(new WordListItemModel("hello", "merhaba", "10/28/2018", R.drawable.foto,70));
        list.add(new WordListItemModel("car", "araba", "10/28/2018", R.drawable.foto,15));
        list.add(new WordListItemModel("page", "sayga", "10/28/2018", R.drawable.foto,80));
        list.add(new WordListItemModel("private", "gizli", "10/28/2018", R.drawable.foto,40));
        list.add(new WordListItemModel("public", "genel", "10/28/2018", R.drawable.foto,17));
        list.add(new WordListItemModel("word", "kelime", "10/28/2018", R.drawable.foto,99));
        list.add(new WordListItemModel("hello", "merhaba", "10/28/2018", R.drawable.foto,78));
        list.add(new WordListItemModel("car", "araba", "10/28/2018", R.drawable.foto,12));
        list.add(new WordListItemModel("page", "sayga", "10/28/2018", R.drawable.foto,0));
        list.add(new WordListItemModel("private", "gizli", "10/28/2018", R.drawable.foto,1));
        list.add(new WordListItemModel("public", "genel", "10/28/2018", R.drawable.foto,78));
        list.add(new WordListItemModel("word", "kelime", "10/28/2018", R.drawable.foto,99));
        list.add(new WordListItemModel("hello", "merhaba", "10/28/2018", R.drawable.foto,33));
        list.add(new WordListItemModel("car", "araba", "10/28/2018", R.drawable.foto,56));
        list.add(new WordListItemModel("page", "sayga", "10/28/2018", R.drawable.foto,14));
        list.add(new WordListItemModel("private", "gizli", "10/28/2018", R.drawable.foto,78));
        list.add(new WordListItemModel("public", "genel", "10/28/2018", R.drawable.foto,65));
        list.add(new WordListItemModel("word", "kelime", "10/28/2018", R.drawable.foto,54));

        return list;
    }
}
