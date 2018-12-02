package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.models.Cards;
import wordmemory.idalavye.com.wordmemory.ui.adapters.CustomListAdapter;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class WordCategoryActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ListView cardListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_category);

        cardListView = findViewById(R.id.cardListView);

        ArrayList<Cards> cardList = new ArrayList<Cards>();
        cardList.add(new Cards("drawable://" + R.drawable.a,"Resim1"));
        cardList.add(new Cards("drawable://" + R.drawable.b,"Resim2"));
        cardList.add(new Cards("drawable://" + R.drawable.a,"Resim1"));
        cardList.add(new Cards("drawable://" + R.drawable.b,"Resim2"));
        cardList.add(new Cards("drawable://" + R.drawable.b,"Resim1"));
        cardList.add(new Cards("drawable://" + R.drawable.a,"Resim2"));

        CustomListAdapter adapter = new CustomListAdapter(this,R.layout.listview_category,cardList);
        cardListView.setAdapter(adapter);

    }
}
