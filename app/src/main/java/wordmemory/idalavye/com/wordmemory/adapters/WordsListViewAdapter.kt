package wordmemory.idalavye.com.wordmemory.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import wordmemory.idalavye.com.wordmemory.R
import wordmemory.idalavye.com.wordmemory.data.AnimationViewHolder
import wordmemory.idalavye.com.wordmemory.data.WordListViewHolder
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel

class WordsListViewAdapter(activity: Activity, private val wordList: MutableList<WordListItemModel>) : ArrayAdapter<WordListItemModel>(activity, R.layout.custom_listview_for_wordlist, wordList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        var listView: View? = convertView

        if (listView == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            listView = inflater.inflate(R.layout.custom_listview_expandable_for_wordlist, parent, false)

            viewHolder = ViewHolder(
                    wordListViewHolder = WordListViewHolder(
                            listView.findViewById(R.id.learning_word),
                            listView.findViewById(R.id.learning_word_mean),
                            listView.findViewById(R.id.word_adding_date),
                            listView.findViewById(R.id.word_image),
                            listView.findViewById(R.id.word_progress)),
                    animationViewHolder = AnimationViewHolder(
                            AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left))
            )

            listView.tag = viewHolder
        } else
            viewHolder = listView.tag as ViewHolder

        val wordListViewHolder: WordListViewHolder = viewHolder.wordListViewHolder
        wordListViewHolder.wordTextView.text = wordList[position].word
        wordListViewHolder.wordMeanTextView.text = wordList[position].word_mean
        wordListViewHolder.dateTextView.text = wordList[position].date
        wordListViewHolder.wordImage.setImageDrawable(ContextCompat.getDrawable(context, wordList[position].img))
        wordListViewHolder.progress.progress = wordList[position].word_progress

        val animationViewHolder: AnimationViewHolder = viewHolder.animationViewHolder
        listView!!.startAnimation(animationViewHolder.animation)

        return listView
    }

    private data class ViewHolder(
            val wordListViewHolder: WordListViewHolder,
            val animationViewHolder: AnimationViewHolder
    )
}
