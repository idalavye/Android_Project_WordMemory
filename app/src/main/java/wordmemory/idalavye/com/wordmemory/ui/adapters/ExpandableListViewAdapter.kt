package wordmemory.idalavye.com.wordmemory.ui.adapters

import android.app.Dialog
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.BaseExpandableListAdapter
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import wordmemory.idalavye.com.wordmemory.R
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController
import wordmemory.idalavye.com.wordmemory.data.AnimationViewHolder
import wordmemory.idalavye.com.wordmemory.data.ExpandItemsViewHolder
import wordmemory.idalavye.com.wordmemory.data.WordListViewHolder
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel
import wordmemory.idalavye.com.wordmemory.utils.DatabaseBuilder
import java.util.*

class ExpandableListViewAdapter(private val context: Context, private val wordList: List<WordListItemModel>) : BaseExpandableListAdapter() {
    private var mTTS: TextToSpeech? = null

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
        val views = convertView ?: let {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val views = inflater.inflate(R.layout.custom_listview_for_wordlist, parent, false)

            views.tag = WordListViewHolder(
                    views.findViewById(R.id.learning_word),
                    views.findViewById(R.id.learning_word_mean),
                    views.findViewById(R.id.word_adding_date),
                    views.findViewById(R.id.word_image),
                    views.findViewById(R.id.word_progress)
            )

            views
        }

        val viewHolder = views.tag as WordListViewHolder

        mTTS = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS!!.setLanguage(Locale.ENGLISH)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    //button senEnable true
                }
            } else {
                Log.e("TTS", "initialization failed")
            }
        })


        viewHolder.wordTextView.text = wordList[groupPosition].word
        viewHolder.wordMeanTextView.text = wordList[groupPosition].meaning
        viewHolder.progress.progress = wordList[groupPosition].word_progress
        viewHolder.wordImage.setOnClickListener { mTTS!!.speak(wordList[groupPosition].word, TextToSpeech.QUEUE_FLUSH, null) }

        return views
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
        val views = convertView ?: let {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val views = inflater.inflate(R.layout.custom_listview_expandable_for_wordlist, parent, false)

            views.tag = AnimationViewHolder(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
            views.tag = Pair(
                    AnimationViewHolder(AnimationUtils.loadAnimation(context, android.R.anim.fade_in)),
                    ExpandItemsViewHolder(
                            views.findViewById(R.id.expand_edit_layout),
                            views.findViewById(R.id.expand_delete_layout),
                            views.findViewById(R.id.expand_reset_layout),
                            views.findViewById(R.id.expand_detail_layout)
                    )
            )

            views
        }

        val tagPair = views.tag as Pair<*, *>
        val animationViewHolder = tagPair.first as AnimationViewHolder
        val expandItemsViewHolder = tagPair.second as ExpandItemsViewHolder

        expandItemsViewHolder.edit.setOnClickListener {
            Log.d("ERROR", "**************************Bujdasfds")
            val dialogs = Dialog(it.context)
            dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogs.setCancelable(false)
            dialogs.setContentView(R.layout.dialog_edit_word_item)

            val cancelBtn = dialogs.findViewById(R.id.dialog_edit_word_close) as MaterialButton
            val yesBtn = dialogs.findViewById(R.id.dialog_edit_word_apply) as MaterialButton
            val wordEdit = dialogs.findViewById(R.id.dialog_edit_word_et) as TextInputEditText
            val wordMeanEdit = dialogs.findViewById(R.id.dialog_edit_word_mean_et) as TextInputEditText

            wordEdit.setText(wordList[groupPosition].word)
            wordMeanEdit.setText(wordList[groupPosition].meaning)
            cancelBtn.setOnClickListener { dialogs.dismiss() }
            yesBtn.setOnClickListener {
                wordList[groupPosition].word = wordEdit.text.toString()
                wordList[groupPosition].meaning = wordMeanEdit.text.toString()
                DatabaseBuilder.updateWordItem(wordList[groupPosition])
                WordListItemController.pullWordItems()
                dialogs.dismiss()
            }
            dialogs.show()
        }

        expandItemsViewHolder.delete.setOnClickListener {
            DatabaseBuilder.removeWordItem(wordList[groupPosition])
            WordListItemController.pullWordItems()
            createSnackBar(it, "Kelime Silindi")
        }

        expandItemsViewHolder.reset.setOnClickListener {
            createSnackBar(it, "Kelime İlerlemesi Sıfırlandı")
            DatabaseBuilder.resetProgressWordItem(wordList[groupPosition])
            WordListItemController.pullWordItems()
        }

        animationViewHolder.animation.duration = 2000
        views.startAnimation(animationViewHolder.animation)

        return views
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return wordList.size
    }

    private fun createSnackBar(view: View, text: String) {
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
