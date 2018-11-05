package wordmemory.idalavye.com.wordmemory.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import wordmemory.idalavye.com.wordmemory.ui.fragments.homepage.ExercisesFragment
import wordmemory.idalavye.com.wordmemory.ui.fragments.homepage.WordsListingFragment

class HomePagePagerAdapter(fragmentManager: FragmentManager, private val numOfTabs: Int) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> WordsListingFragment()
            1 -> ExercisesFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return numOfTabs
    }
}
