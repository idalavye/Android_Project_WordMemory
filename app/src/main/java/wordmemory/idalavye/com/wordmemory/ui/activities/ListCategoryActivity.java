package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static wordmemory.idalavye.com.wordmemory.utils.Login.mAuth;

public class ListCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
    }

    public  void getDataFromFirebase(){

        String userID = mAuth.getCurrentUser().getUid();
        DatabaseReference newReference = firebaseDatabase.getReference("records").child(userID);

        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();
                    categoryUidFromFB.add(ds.getKey());
                    categoryTitleFromFB.add(hashMap.get("Title"));
                    categoryImageFromFB.add(hashMap.get("ImageUrl"));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListCategory.this, "Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
