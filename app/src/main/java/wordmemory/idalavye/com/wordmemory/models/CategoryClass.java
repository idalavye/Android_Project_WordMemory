package wordmemory.idalavye.com.wordmemory.models;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.ui.fragments.homepage.ListCategoryFragment;

public class CategoryClass extends ArrayAdapter<String> {
    public static ArrayList<String> categoryUid;
    public static ArrayList<String> categoryTitle;
    public static ArrayList<String> categoryImage;
    public static Activity context;

    public CategoryClass(ArrayList<String> categoryUid, ArrayList<String> categoryTitle, ArrayList<String> categoryImage, ListCategoryFragment context) {
        super(context,R.layout.custom_view,categoryTitle);
        this.categoryUid = categoryUid;
        this.categoryTitle = categoryTitle;
        this.categoryImage = categoryImage;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view,null,true);

        TextView categoryText = customView.findViewById(R.id.userEmailTextViewCustomView);
        ImageView imageView =customView.findViewById(R.id.imageViewCustomView);

        categoryText.setText(categoryTitle.get(position));
        Picasso.get().load(categoryImage.get(position)).into(imageView);

        return customView;
    }
}
