package wordmemory.idalavye.com.wordmemory.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import wordmemory.idalavye.com.wordmemory.fragments.homepage.ExercisesFragment;
import wordmemory.idalavye.com.wordmemory.fragments.homepage.WordsListingFragment;

public class HomePagePagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public HomePagePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                WordsListingFragment wordsListingFragment = new WordsListingFragment();
                return wordsListingFragment;
            case 1:
                ExercisesFragment exercisesFragment = new ExercisesFragment();
                return exercisesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
