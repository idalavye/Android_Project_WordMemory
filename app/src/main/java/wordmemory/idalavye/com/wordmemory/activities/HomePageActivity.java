package wordmemory.idalavye.com.wordmemory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.facebook.login.LoginManager;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.adapters.HomePagePagerAdapter;
import wordmemory.idalavye.com.wordmemory.fragments.homepage.BottomNavigationDrawerFragment;
import wordmemory.idalavye.com.wordmemory.fragments.homepage.ExercisesFragment;
import wordmemory.idalavye.com.wordmemory.fragments.homepage.WordsListingFragment;
import wordmemory.idalavye.com.wordmemory.utils.Login;

public class HomePageActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomAppBar bar;
    private FloatingActionButton fab;
    private LinearLayout addNewWordLayout;
    private MaterialSearchView searchView;
    private boolean fbModeCenter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        init();

        tabLayout.addTab(tabLayout.newTab().setText(String.valueOf(R.string.my_words)));
        tabLayout.addTab(tabLayout.newTab().setText(String.valueOf(R.string.my_exercises)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final HomePagePagerAdapter adapter = new HomePagePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
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
        };
        tabLayout.addOnTabSelectedListener(tabSelectedListener);

        final Animation animationSlideInLeft = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        final Animation animationForNewWordPage = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        animationForNewWordPage.setDuration(2000);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fbModeCenter) {
                    bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                    fab.setImageResource(R.drawable.ic_reply);
                    fbModeCenter = false;


                    tabLayout.setVisibility(View.GONE);
                    WordsListingFragment.listView.setVisibility(View.GONE);
                    ExercisesFragment.exercise_fragment.setVisibility(View.GONE);
                    addNewWordLayout.startAnimation(animationForNewWordPage);
                    addNewWordLayout.setVisibility(View.VISIBLE);

                } else {
                    addNewWordLayout.clearAnimation();
                    bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                    fab.setImageResource(R.drawable.ic_add);
                    fbModeCenter = true;


                    tabLayout.setVisibility(View.VISIBLE);
                    WordsListingFragment.listView.startAnimation(animationSlideInLeft);
                    WordsListingFragment.listView.setVisibility(View.VISIBLE);
                    ExercisesFragment.exercise_fragment.startAnimation(animationSlideInLeft);
                    ExercisesFragment.exercise_fragment.setVisibility(View.VISIBLE);
                    addNewWordLayout.setVisibility(View.GONE);
                }
            }
        });

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomNavigationDrawerFragment drawerFragment = BottomNavigationDrawerFragment.getInstance();
                drawerFragment.show(getSupportFragmentManager(), "Custom Button Sheet");
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
        Login.mAuth = FirebaseAuth.getInstance();
        searchView = findViewById(R.id.search_word);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_page_menu, menu);

        MenuItem item = menu.findItem(R.id.m_search);
        searchView.setMenuItem(item);

        return true;
    }

    public void exitApp(MenuItem item) {
        Login.mAuth.signOut();
        LoginManager.getInstance().logOut();
        updateUI();
    }

    public void searchWord(MenuItem item) {
        fab.hide();
    }

    private void updateUI() {
        Intent accountIntent = new Intent(getApplicationContext(), LoginPageActivity.class);
        startActivity(accountIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = Login.mAuth.getCurrentUser();
        if (currentUser == null) {
            updateUI();
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
