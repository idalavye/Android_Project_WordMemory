package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.models.Cards;
import wordmemory.idalavye.com.wordmemory.ui.adapters.CustomListAdapter;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WordCategoryActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    FirebaseDatabase database;
    DatabaseReference myRef;

    ListView cardListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_category);

        cardListView = findViewById(R.id.cardListView);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        ArrayList<Cards> cardList = new ArrayList<Cards>();
        cardList.add(new Cards("drawable://" + R.drawable.a,"Resim1"));
        cardList.add(new Cards("drawable://" + R.drawable.b,"Resim2"));
        cardList.add(new Cards("drawable://" + R.drawable.a,"Resim1"));
        cardList.add(new Cards("drawable://" + R.drawable.b,"Resim2"));
        cardList.add(new Cards("drawable://" + R.drawable.b,"Resim1"));
        cardList.add(new Cards("drawable://" + R.drawable.a,"Resim2"));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);


                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.");
            }
        });

     /*   ArrayList<Cards> cardList = new ArrayList<Cards>();
        cardList.add(new Cards("drawable://" + R.drawable.a,"Resim1"));
        cardList.add(new Cards("drawable://" + R.drawable.b,"Resim2"));
        cardList.add(new Cards("drawable://" + R.drawable.a,"Resim1"));
        cardList.add(new Cards("drawable://" + R.drawable.b,"Resim2"));
        cardList.add(new Cards("drawable://" + R.drawable.b,"Resim1"));
        cardList.add(new Cards("drawable://" + R.drawable.a,"Resim2"));*/

        CustomListAdapter adapter = new CustomListAdapter(this,R.layout.listview_category,cardList);
        cardListView.setAdapter(adapter);

    }
}
