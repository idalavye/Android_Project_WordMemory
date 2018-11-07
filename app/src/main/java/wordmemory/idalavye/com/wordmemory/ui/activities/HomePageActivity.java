package wordmemory.idalavye.com.wordmemory.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.facebook.login.LoginManager;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;
import wordmemory.idalavye.com.wordmemory.ui.adapters.HomePagePagerAdapter;
import wordmemory.idalavye.com.wordmemory.ui.fragments.common.BottomNavigationDrawerFragment;
import wordmemory.idalavye.com.wordmemory.ui.fragments.homepage.ExercisesFragment;
import wordmemory.idalavye.com.wordmemory.ui.fragments.homepage.WordsListingFragment;
import wordmemory.idalavye.com.wordmemory.utils.DatabaseBuilder;
import wordmemory.idalavye.com.wordmemory.utils.Login;

public class HomePageActivity extends AppCompatActivity {
    private static String TAG = HomePageActivity.class.getName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomAppBar bar;
    private FloatingActionButton fab;
    private LinearLayout addNewWordLayout;
    private MaterialSearchView searchView;
    private boolean fbModeCenter = true;
    private HomePagePagerAdapter pagerAdapter;
    private MaterialButton add_new_word_button;
    private TextInputEditText word, wordMean;

    private ArrayList<WordListItemModel> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        init();
        events();

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_words)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_exercises)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
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

        WordListItemController.INSTANCE.addListenerForWordItemDataChange(new WordListItemController.WordItemDataChangeListener() {
            @Override
            public void onWordItemDataChange() {
                if (pagerAdapter == null) {
                    pagerAdapter = new HomePagePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                    viewPager.setAdapter(pagerAdapter);
                }

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListView expandableListView = WordsListingFragment.getExpandableListView();
                        if (expandableListView == null) {
                            Log.e(TAG, "onClick: ", new NullPointerException());
                            return;
                        }

                        if (fbModeCenter) {
                            bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                            fab.setImageResource(R.drawable.ic_reply);
                            fbModeCenter = false;


                            tabLayout.setVisibility(View.GONE);
                            expandableListView.setVisibility(View.GONE);
                            ExercisesFragment.exercise_fragment.setVisibility(View.GONE);
                            addNewWordLayout.startAnimation(animationForNewWordPage);
                            addNewWordLayout.setVisibility(View.VISIBLE);

                        } else {
                            addNewWordLayout.clearAnimation();
                            bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                            fab.setImageResource(R.drawable.ic_add);
                            fbModeCenter = true;


                            tabLayout.setVisibility(View.VISIBLE);
                            expandableListView.startAnimation(animationSlideInLeft);
                            expandableListView.setVisibility(View.VISIBLE);
                            ExercisesFragment.exercise_fragment.startAnimation(animationSlideInLeft);
                            ExercisesFragment.exercise_fragment.setVisibility(View.VISIBLE);
                            addNewWordLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        WordListItemController.INSTANCE.pullWordItems();

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
        add_new_word_button = findViewById(R.id.add_new_word_button);
        word = findViewById(R.id.add_new_word_word_et);
        wordMean = findViewById(R.id.add_new_word_word_mean_et);
        list = new ArrayList<>();
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

    private void events() {
        add_new_word_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordListItemModel item = new WordListItemModel();
                item.setWord(word.getText().toString());
                item.setMeaning(wordMean.getText().toString());
                DatabaseBuilder.INSTANCE.addWordItems(DatabaseRef.INSTANCE.getWordsRef(),item);
            }
        });
    }
}
