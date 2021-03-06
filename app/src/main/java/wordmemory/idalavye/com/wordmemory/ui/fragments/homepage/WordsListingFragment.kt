package wordmemory.idalavye.com.wordmemory.ui.fragments.homepage

import android.database.DataSetObserver
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import wordmemory.idalavye.com.wordmemory.R
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController
import wordmemory.idalavye.com.wordmemory.ui.adapters.ExpandableListViewAdapter

class WordsListingFragment : Fragment() {


    companion object {
        @JvmStatic
        private val TAG: String = WordsListingFragment::javaClass.name

        // TODO Move this out of the static field
        @JvmStatic
        var expandableListView: ExpandableListView? = null
            private set

        @JvmStatic
        var lastExpandedGroupPosition: Int = -1

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.words_listing_fragment, container, false)

        val words = WordListItemController.words
        if (words == null) {
            Log.e(TAG, "words: ", NullPointerException())
            return view
        }
        val activity = activity
        if (activity == null) {
            Log.e(TAG, "activity: ", NullPointerException())
            return view
        }
        val adapter = ExpandableListViewAdapter(activity, WordListItemController.words)

        val expandableListView: ExpandableListView = view.findViewById(R.id.words_listView)

        expandableListView.isNestedScrollingEnabled = true
        expandableListView.setAdapter(adapter)
        expandableListView.setOnGroupExpandListener { groupPosition ->
            lastExpandedGroupPosition = if (expandableListView.isGroupExpanded(lastExpandedGroupPosition)) {
                expandableListView.collapseGroup(lastExpandedGroupPosition)
                groupPosition
            } else {
                -1
            }
        }

        expandableListView.setChildDivider(resources.getDrawable(android.R.color.transparent))
        WordsListingFragment.expandableListView = expandableListView
        return view
    }

}
