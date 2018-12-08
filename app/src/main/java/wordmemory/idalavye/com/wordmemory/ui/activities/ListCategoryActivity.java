package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.models.CategoryClass;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

import static wordmemory.idalavye.com.wordmemory.utils.Login.mAuth;

public class ListCategoryActivity extends AppCompatActivity {
    private static final String TAG = "ListCategory";
    ListView lstview;
    CategoryClass adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> categoryUidFromFB;
    ArrayList<String> categoryTitleFromFB;
    ArrayList<String> categoryImageFromFB;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);

        // Get UI elements
        lstview = findViewById(R.id.lstview);

        // Initialize ArrayLists
        categoryUidFromFB = new ArrayList<String>();
        categoryTitleFromFB = new ArrayList<String>();
        categoryImageFromFB = new ArrayList<String>();
        // Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        //Auth
        mAuth = FirebaseAuth.getInstance();
        // Storage
        mStorageRef = FirebaseStorage.getInstance().getReference();

        // Adapter
        adapter = new CategoryClass(categoryUidFromFB,categoryTitleFromFB,categoryImageFromFB,this);
        lstview.setAdapter(adapter);
        Log.i(TAG, "onCreate: "+ "okey");
        getDataFromFirebase();
    }

    public  void getDataFromFirebase(){
        clearListViewArrays();
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
                Toast.makeText(ListCategoryActivity.this, "Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearListViewArrays(){
        categoryUidFromFB.clear();
        categoryTitleFromFB.clear();
        categoryImageFromFB.clear();
    }

}
