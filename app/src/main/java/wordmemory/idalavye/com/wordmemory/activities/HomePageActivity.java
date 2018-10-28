package wordmemory.idalavye.com.wordmemory.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.adapters.HomePagePagerAdapter;
import wordmemory.idalavye.com.wordmemory.fragments.homepage.ExercisesFragment;
import wordmemory.idalavye.com.wordmemory.fragments.homepage.WordsListingFragment;

public class HomePageActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomAppBar bar;
    private FloatingActionButton fab;
    private LinearLayout addNewWordLayout;
    private boolean fbModeCenter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        init();

        tabLayout.addTab(tabLayout.newTab().setText("Kelimelerim"));
        tabLayout.addTab(tabLayout.newTab().setText("Alıştırmalar"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final HomePagePagerAdapter adapter = new HomePagePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fbModeCenter) {
                    bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                    fab.setImageResource(R.drawable.ic_arrow);
                    fbModeCenter = false;


                    tabLayout.setVisibility(View.GONE);
                    WordsListingFragment.listView.setVisibility(View.GONE);
                    ExercisesFragment.exercise_fragment.setVisibility(View.GONE);
                    addNewWordLayout.setVisibility(View.VISIBLE);

                } else {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
                    bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                    fab.setImageResource(R.drawable.ic_add);
                    fbModeCenter = true;


                    tabLayout.setVisibility(View.VISIBLE);
                    WordsListingFragment.listView.startAnimation(animation);
                    WordsListingFragment.listView.setVisibility(View.VISIBLE);
                    ExercisesFragment.exercise_fragment.startAnimation(animation);
                    ExercisesFragment.exercise_fragment.setVisibility(View.VISIBLE);
                    addNewWordLayout.setVisibility(View.GONE);
                }
            }
        });

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMessage("NavigationMenu");
            }
        });

    }

    private void init() {
        this.tabLayout = findViewById(R.id.tab_layout);
        this.viewPager = findViewById(R.id.pager);
        this.bar = findViewById(R.id.bar);
        setSupportActionBar(bar);
        this.fab = findViewById(R.id.fab);
        this.addNewWordLayout = findViewById(R.id.add_new_word);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_page_menu, menu);
        return true;
    }

    public boolean search(MenuItem menuItem) {
        getMessage("Search");
        return true;
    }

    private void getMessage(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }
}
