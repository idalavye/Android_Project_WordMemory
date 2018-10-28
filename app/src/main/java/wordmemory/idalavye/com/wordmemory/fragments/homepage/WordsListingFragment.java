package wordmemory.idalavye.com.wordmemory.fragments.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.adapters.WordsListViewAdapter;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;

public class WordsListingFragment extends Fragment {

    public static ListView listView;
    public static WordsListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.words_listing_fragment, container, false);

        init(view);

        listView.setNestedScrollingEnabled(true);
        listView.setAdapter(adapter);

        return view;
    }

    private void init(View view) {
        this.listView = view.findViewById(R.id.words_listView);
        adapter = new WordsListViewAdapter(getActivity(), getArrayList());
    }

    private ArrayList<WordListItemModel> getArrayList() {
        ArrayList<WordListItemModel> list = new ArrayList<>();

        list.add(new WordListItemModel("hello", "merhaba", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("car", "araba", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("page", "sayga", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("private", "gizli", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("public", "genel", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("word", "kelime", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("hello", "merhaba", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("car", "araba", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("page", "sayga", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("private", "gizli", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("public", "genel", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("word", "kelime", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("hello", "merhaba", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("car", "araba", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("page", "sayga", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("private", "gizli", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("public", "genel", "10/28/2018", R.drawable.foto));
        list.add(new WordListItemModel("word", "kelime", "10/28/2018", R.drawable.foto));

        return list;
    }
}