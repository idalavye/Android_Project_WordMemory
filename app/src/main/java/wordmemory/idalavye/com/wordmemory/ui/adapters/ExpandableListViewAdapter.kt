package wordmemory.idalavye.com.wordmemory.ui.adapters

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseExpandableListAdapter
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import wordmemory.idalavye.com.wordmemory.R
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController
import wordmemory.idalavye.com.wordmemory.data.AnimationViewHolder
import wordmemory.idalavye.com.wordmemory.data.ExpandItemsViewHolder
import wordmemory.idalavye.com.wordmemory.data.WordListViewHolder
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel
import wordmemory.idalavye.com.wordmemory.utils.DatabaseBuilder

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

        var views: View? = convertView

        if (views == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            views = inflater.inflate(R.layout.custom_listview_for_wordlist, parent, false)

            viewHolder = WordListViewHolder(
                    views.findViewById(R.id.learning_word),
                    views.findViewById(R.id.learning_word_mean),
                    views.findViewById(R.id.word_adding_date),
                    views.findViewById(R.id.word_image),
                    views.findViewById(R.id.word_progress)
            )

            views.tag = viewHolder
        } else
            viewHolder = views.tag as WordListViewHolder



        viewHolder.wordTextView.text = wordList[groupPosition].word
        viewHolder.wordMeanTextView.text = wordList[groupPosition].meaning
        viewHolder.progress.progress = wordList[groupPosition].word_progress

        return views!!
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
        val expandHolder: ExpandItemsViewHolder

        if (listView == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            listView = inflater.inflate(R.layout.custom_listview_expandable_for_wordlist, parent, false)

            viewHolder = AnimationViewHolder(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))

            listView.tag = viewHolder
        } else
            viewHolder = listView.tag as AnimationViewHolder

        expandHolder = ExpandItemsViewHolder(
                listView!!.findViewById(R.id.expand_edit_layout),
                listView!!.findViewById(R.id.expand_delete_layout),
                listView!!.findViewById(R.id.expand_reset_layout),
                listView!!.findViewById(R.id.expand_detail_layout)
        )

        expandHolder.edit.setOnClickListener {
            Log.d("ERROR", "**************************Bujdasfds")
        }

        expandHolder.delete.setOnClickListener {
            DatabaseBuilder.removeWordItem(wordList[groupPosition])
            WordListItemController.pullWordItems()
            createSnackBar(it,"Kelime Silindi")
        }

        expandHolder.reset.setOnClickListener {
            createSnackBar(it,"Kelime İlerlemesi Sıfırlandı")
            DatabaseBuilder.resetProgressWordItem(wordList[groupPosition])
            WordListItemController.pullWordItems()
        }

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

    fun createSnackBar(view:View,text:String){
        val marginSide = 0
        val marginBottom = 100
        val snack = Snackbar.make(view, text, Snackbar.LENGTH_LONG).setAction("Geri Al") {
            //Geri alma işlemi
        }
        val snackbarView = snack.view
        val params = snackbarView.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(
                params.leftMargin + marginSide,
                params.topMargin,
                params.rightMargin + marginSide,
                params.bottomMargin + marginBottom
        )

        snackbarView.layoutParams = params
        snack.show()
    }
}
