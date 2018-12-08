package wordmemory.idalavye.com.wordmemory.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.models.CategoryClass;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

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

    public void upload(View view) {
        /* UPDATE SELECTED CATEGORY*/
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if(key!= null && intent.getStringExtra("key").equals("edit")){
            final int selectpos = intent.getIntExtra("position",0);
            String userID = mAuth.getCurrentUser().getUid();


            StorageReference photoRefEdit = FirebaseStorage.getInstance().getReferenceFromUrl(CategoryClass.categoryImage.get(selectpos));
            photoRefEdit.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                    Log.d(TAG, "onFailure: did not delete file");
                }
            });

            UUID uuid = UUID.randomUUID();
            final String imageName = "images/" + uuid +".jpg";
            StorageReference storageReference = mStorageRef.child(imageName);
            storageReference.putFile(selectedImage).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //download url
                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadURL = uri.toString();
                            String userID = mAuth.getCurrentUser().getUid();
                            String title = categoryTitleText.getText().toString();

                            // Dbye kaydet
                            String itemUid = CategoryClass.categoryUid.get(selectpos);
                            DatabaseReference drEdit = FirebaseDatabase.getInstance().getReference("records").child(userID).child(itemUid);
                            drEdit.child("ImageUrl").setValue(downloadURL);
                            drEdit.child("Title").setValue(title);

                            CategoryClass.categoryImage.remove(selectpos);
                            CategoryClass.categoryImage.add(selectpos,"imgURL");
                            CategoryClass.categoryTitle.remove(selectpos);
                            CategoryClass.categoryTitle.add(selectpos,"title");

                            Intent intent = new Intent(getApplicationContext(),ListCategoryActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateCategoryActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            });


        }else{
            /*CREATE A NEW CATEGORY*/
            UUID uuid = UUID.randomUUID();
            final String imageName = "images/" + uuid +".jpg";
            StorageReference storageReference = mStorageRef.child(imageName);
            storageReference.putFile(selectedImage).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //download url
                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadURL = uri.toString();
                            String userID = mAuth.getCurrentUser().getUid();
                            String title = categoryTitleText.getText().toString();

                            // Dbye kaydet
                            UUID uuid = UUID.randomUUID();
                            String uuidStr = uuid.toString();
                            myRef.child("records").child(userID).child(uuidStr).child("Title").setValue(title);
                            myRef.child("records").child(userID).child(uuidStr).child("ImageUrl").setValue(downloadURL);
                            Intent intent = new Intent(getApplicationContext(),ListCategoryActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateCategoryActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

    public void catListele(View view){
        Intent intent = new Intent(getApplicationContext(),ListCategoryActivity.class);
        startActivity(intent);
    }

    public void selectImage(View view) {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // izin yok izin iste
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else {
            // izin var
            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // izin var
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data!=null) {
            selectedImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                categoryImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
