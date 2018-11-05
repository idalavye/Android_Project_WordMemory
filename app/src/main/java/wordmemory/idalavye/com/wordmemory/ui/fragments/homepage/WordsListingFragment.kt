package wordmemory.idalavye.com.wordmemory.ui.fragments.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import wordmemory.idalavye.com.wordmemory.R
import wordmemory.idalavye.com.wordmemory.ui.adapters.ExpandableListViewAdapter
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController

class WordsListingFragment : Fragment() {
    var listView: ExpandableListView? = null
        private set
    private var lastExpandedPosition: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.words_listing_fragment, container, false)

        val words = WordListItemController.words ?: return view
        val activity = activity ?: return view
        val adapter = ExpandableListViewAdapter(activity, words)

        val listView: ExpandableListView = view.findViewById(R.id.words_listView)
        listView.isNestedScrollingEnabled = true
        listView.setAdapter(adapter)
        listView.setOnGroupExpandListener { groupPosition ->
            if (lastExpandedPosition >= 0 && lastExpandedPosition != groupPosition) {
                listView.collapseGroup(lastExpandedPosition)
            }

            lastExpandedPosition = groupPosition
        }

        this.listView = listView
        return view
    }
}
