package wordmemory.idalavye.com.wordmemory.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseExpandableListAdapter
import androidx.core.content.ContextCompat
import wordmemory.idalavye.com.wordmemory.R
import wordmemory.idalavye.com.wordmemory.data.AnimationViewHolder
import wordmemory.idalavye.com.wordmemory.data.WordListViewHolder
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel

class ExpandableListViewAdapter(private val context: Context, private val wordList: List<WordListItemModel>) : BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): Any {
        return wordList[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: WordListViewHolder
        var listView: View? = convertView

        if (listView == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            listView = inflater.inflate(R.layout.custom_listview_for_wordlist, parent, false)

            viewHolder = WordListViewHolder(
                    listView.findViewById(R.id.learning_word),
                    listView.findViewById(R.id.learning_word_mean),
                    listView.findViewById(R.id.word_adding_date),
                    listView.findViewById(R.id.word_image),
                    listView.findViewById(R.id.word_progress)
            )

            listView.tag = viewHolder
        } else
            viewHolder = listView.tag as WordListViewHolder

        viewHolder.wordTextView.text = wordList[groupPosition].word
        viewHolder.wordMeanTextView.text = wordList[groupPosition].word_mean
        viewHolder.dateTextView.text = wordList[groupPosition].date
        viewHolder.wordImage.setImageDrawable(ContextCompat.getDrawable(context, wordList[groupPosition].img))
        viewHolder.progress.progress = wordList[groupPosition].word_progress

        return listView!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return getGroup(groupPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: AnimationViewHolder
        var listView: View? = convertView

        if (listView == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            listView = inflater.inflate(R.layout.custom_listview_expandable_for_wordlist, parent, false)

            viewHolder = AnimationViewHolder(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
        } else
            viewHolder = listView.tag as AnimationViewHolder

        viewHolder.animation.duration = 2000
        listView!!.startAnimation(viewHolder.animation)

        return listView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return wordList.size
    }
}
