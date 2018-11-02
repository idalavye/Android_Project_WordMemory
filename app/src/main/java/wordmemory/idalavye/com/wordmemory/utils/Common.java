package wordmemory.idalavye.com.wordmemory.utils;

import java.util.ArrayList;

import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;

public class Common {

    public static ArrayList<WordListItemModel> getArrayList() {
        ArrayList<WordListItemModel> list = new ArrayList<>();
        list.add(new WordListItemModel("hello", "merhaba", "10/28/2018", R.drawable.foto,70));
        list.add(new WordListItemModel("car", "araba", "10/28/2018", R.drawable.foto,15));
        list.add(new WordListItemModel("page", "sayfa", "10/28/2018", R.drawable.foto,80));
        list.add(new WordListItemModel("private", "gizli", "10/28/2018", R.drawable.foto,40));
        list.add(new WordListItemModel("public", "genel", "10/28/2018", R.drawable.foto,17));
        list.add(new WordListItemModel("word", "kelime", "10/28/2018", R.drawable.foto,99));
       return list;
    }
}
