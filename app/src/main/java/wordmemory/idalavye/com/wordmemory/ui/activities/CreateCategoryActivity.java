package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import wordmemory.idalavye.com.wordmemory.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class CreateCategoryActivity extends AppCompatActivity {
    private static final String TAG = "CreateCategory";
    ImageView categoryImageView;
    EditText categoryTitleText;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        // Get UI elements
        categoryImageView = findViewById(R.id.categoryImageView);
        categoryTitleText = findViewById(R.id.categoryTitleText);

        //Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        //Auth
        mAuth = FirebaseAuth.getInstance();
        // Storage
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if(key!= null && intent.getStringExtra("key").equals("edit")){
            categoryTitleText.setText(intent.getStringExtra("title"));
            Picasso.get().load(intent.getStringExtra("image")).into(categoryImageView);
        }

    }

    public void catListele(View view){
        Intent intent = new Intent(getApplicationContext(),ListCategory.class);
        startActivity(intent);
    }
}
