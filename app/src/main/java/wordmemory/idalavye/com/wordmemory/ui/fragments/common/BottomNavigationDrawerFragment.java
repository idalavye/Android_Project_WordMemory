package wordmemory.idalavye.com.wordmemory.ui.fragments.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import wordmemory.idalavye.com.wordmemory.R;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    private FirebaseAuth firebaseAuth;


    public static BottomNavigationDrawerFragment getInstance() {
        return new BottomNavigationDrawerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_navigation_drawer_fragment, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        TextView username = view.findViewById(R.id.user_name);
        TextView user_email = view.findViewById(R.id.user_email);
        ImageView user_image = view.findViewById(R.id.user_image);
        username.setText(currentUser.getDisplayName());
        user_email.setText(currentUser.getEmail());

        Picasso.get().load(currentUser.getPhotoUrl().toString()).into(user_image);

//       ImageDownloader imageDownloader = new ImageDownloader();
//        try {
//            Bitmap bitmap = imageDownloader.execute(currentUser.getPhotoUrl().toString()).get();
//            user_image.setImageBitmap(bitmap);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return view;
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {

                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream stream = urlConnection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(stream);

                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


}
