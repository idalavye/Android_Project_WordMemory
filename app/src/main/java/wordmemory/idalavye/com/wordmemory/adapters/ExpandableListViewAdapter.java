package wordmemory.idalavye.com.wordmemory.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<WordListItemModel> word_list;

    public ExpandableListViewAdapter(Context context, List<WordListItemModel> word_list) {
        this.context = context;
        this.word_list = word_list;
    }

    @Override
    public int getGroupCount() {
        return this.word_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.word_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.word_list.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_listview_for_wordlist, parent, false);
        }

        TextView word = convertView.findViewById(R.id.learning_word);
        TextView word_mean = convertView.findViewById(R.id.learning_word_mean);
        TextView date = convertView.findViewById(R.id.word_adding_date);
        ImageView image = convertView.findViewById(R.id.word_image);
        ArcProgress progress = convertView.findViewById(R.id.word_progress);

        image.setImageDrawable(convertView.getResources().getDrawable(
                word_list.get(groupPosition).getImg()
        ));
        word.setText(word_list.get(groupPosition).getWord());
        word_mean.setText(word_list.get(groupPosition).getWord_mean());
        date.setText(word_list.get(groupPosition).getDate());
        progress.setProgress(word_list.get(groupPosition).getWord_progress());

//
//        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//        convertView.startAnimation(animation);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        WordListItemModel itemModel = word_list.get(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_listview_expandable_for_wordlist, null);
        }

        LinearLayout l1 = convertView.findViewById(R.id.linearLayout1);
        LinearLayout l2 = convertView.findViewById(R.id.linearLayout2);
        LinearLayout l3 = convertView.findViewById(R.id.linearLayout3);
        LinearLayout l4 = convertView.findViewById(R.id.linearLayout4);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Edit button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.setDuration(2000);
        convertView.startAnimation(animation);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
