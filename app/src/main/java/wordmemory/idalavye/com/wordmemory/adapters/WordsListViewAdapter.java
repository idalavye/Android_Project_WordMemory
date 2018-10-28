package wordmemory.idalavye.com.wordmemory.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;

public class WordsListViewAdapter extends ArrayAdapter<WordListItemModel> {

    private Activity activity;
    private ArrayList<WordListItemModel> arrayList;
    private int lastPosition = -1;

    public WordsListViewAdapter(Activity activity, ArrayList<WordListItemModel> arrayList) {
        super(activity, R.layout.custom_listview_for_wordlist, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview_for_wordlist, parent, false);
        }

        TextView word = convertView.findViewById(R.id.learning_word);
        TextView word_mean = convertView.findViewById(R.id.learning_word_mean);
        TextView date = convertView.findViewById(R.id.word_adding_date);
        ImageView image = convertView.findViewById(R.id.word_image);

        image.setImageDrawable(activity.getResources().getDrawable(
                arrayList.get(position).getImg()
        ));
        word.setText(arrayList.get(position).getWord());
        word_mean.setText(arrayList.get(position).getWord_mean());
        date.setText(arrayList.get(position).getDate());

        Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
        convertView.startAnimation(animation);

        return convertView;
    }
}
