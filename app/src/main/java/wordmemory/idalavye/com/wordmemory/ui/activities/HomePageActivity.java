package wordmemory.idalavye.com.wordmemory.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.StatisticController;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef;
import wordmemory.idalavye.com.wordmemory.models.StatisticModel;
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel;
import wordmemory.idalavye.com.wordmemory.ui.adapters.ExpandableListViewAdapter;
import wordmemory.idalavye.com.wordmemory.ui.adapters.HomePagePagerAdapter;
import wordmemory.idalavye.com.wordmemory.ui.fragments.common.BottomNavigationDrawerFragment;
import wordmemory.idalavye.com.wordmemory.ui.fragments.homepage.ExercisesFragment;
import wordmemory.idalavye.com.wordmemory.ui.fragments.homepage.StatisticsFragment;
import wordmemory.idalavye.com.wordmemory.ui.fragments.homepage.WordsListingFragment;
import wordmemory.idalavye.com.wordmemory.utils.Animations;
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
    private TextInputEditText word;
    private TextView wordMean;
    private ExpandableListView expandableListView;
    private LinearLayout homepage_content_layout, spin_layout_homepage, spin_layout_add_new_word;

    private String lastWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        init();
        events();

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_words)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_exercises)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_statistics)));
        tabLayout.addTab(tabLayout.newTab().setText("Categories"));
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


        WordListItemController.INSTANCE.addListenerForWordItemDataChange(new WordListItemController.WordItemDataChangeListener() {
            @Override
            public void onWordItemDataChange() {
                if (pagerAdapter == null) {
                    pagerAdapter = new HomePagePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                    viewPager.setAdapter(pagerAdapter);
                    spin_layout_homepage.setVisibility(View.GONE);
                }
                runOnUiThread(() -> {
                    expandableListView = WordsListingFragment.getExpandableListView();
                    ExpandableListViewAdapter adapter = (ExpandableListViewAdapter) expandableListView.getExpandableListAdapter();
                    adapter.notifyDataSetChanged();
                });
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableListView == null) {
                    Log.e(TAG, "onClick: ", new NullPointerException());
                    return;
                }
                if (fbModeCenter) {
                    showNewWordLayout();
                } else {
                    hideAddNewWordLayout();
                }
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
        wordMean = findViewById(R.id.word_mean_tw);
        homepage_content_layout = findViewById(R.id.homepage_content_layout);
        spin_layout_homepage = findViewById(R.id.spin_layout_homepage);
        spin_layout_add_new_word = findViewById(R.id.spin_layout_add_new_word);
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

        ProgressBar progressBar = findViewById(R.id.spin_kit);
        ProgressBar progressBar1 = findViewById(R.id.spin_kit_add_new_word);
        Sprite doubleBounce = new DoubleBounce();
        Sprite doubleBounce2 = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar1.setIndeterminateDrawable(doubleBounce2);

        RxTextView.textChanges(word).debounce(2, TimeUnit.SECONDS).subscribe(changedText -> {
            if (!word.getText().toString().equals("")) {
                try {
                    final String word = changedText.toString();
                    Translate translate = TranslateOptions
                            .newBuilder().setApiKey("AIzaSyCtTUTemBtKKpdBS7rUmjjKlgTx9xDfgvY")
                            .build().getService();

                    Translation translation = translate.translate(
                            word,
                            Translate.TranslateOption.sourceLanguage("en"),
                            Translate.TranslateOption.targetLanguage("tr")
                    );

                    System.out.println("Text : " + word);
                    System.out.println("Translation : " + translation.getTranslatedText());

                    runOnUiThread(() -> {
                        wordMean.setText(translation.getTranslatedText());
                        wordMean.setVisibility(View.VISIBLE);
                        spin_layout_add_new_word.setVisibility(View.GONE);
                    });

                } catch (Exception e) {

                    System.out.println("Thread error : " + e.getMessage());
                }
            } else {
                runOnUiThread(() -> {
                    wordMean.setText("Öğrenmek istediğin kelimeyi gir");
                    wordMean.setVisibility(View.VISIBLE);
                    spin_layout_add_new_word.setVisibility(View.GONE);
                });
            }
        });


        word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordMean.setVisibility(View.GONE);
                spin_layout_add_new_word.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        add_new_word_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (word.getText().toString().equals("")){
                    createAlertDiolag("Lütfen bir kelime girin");
                    return;
                }
                if (DatabaseBuilder.INSTANCE.findWord(word.getText().toString())){
                    createAlertDiolag("Bu kelime daha önce eklenmiş");
                    return;
                }
                if (!(wordMean.getText().toString().equals("") || wordMean.getText().toString().equals("Öğrenmek istediğin kelimeyi gir") || wordMean.getText().toString().equals(lastWord))) {

                    WordListItemModel item = new WordListItemModel();
                    final String word1 = word.getText().toString();
                    item.setWord(word1);
                    item.setMeaning(wordMean.getText().toString());
                    DatabaseBuilder.INSTANCE.addWordItems(DatabaseRef.INSTANCE.getWordsRef(), item);
                    lastWord = wordMean.getText().toString();

                    wordMean.setText("");
                    word.setText("");

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    hideAddNewWordLayout();
                    WordListItemController.INSTANCE.pullWordItems();
                }else{
                    createAlertDiolag("Kelimenin çevrilmesini bekleyiniz");
                }
            }
        });
    }

    private void hideAddNewWordLayout() {
        lastWord = "";
        addNewWordLayout.clearAnimation();
        bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        fab.setImageResource(R.drawable.ic_add);
        fbModeCenter = true;

        homepage_content_layout.startAnimation(Animations.createSlideInLeft(getApplicationContext(), 500));
        homepage_content_layout.setVisibility(View.VISIBLE);
        addNewWordLayout.setVisibility(View.GONE);
    }

    private void showNewWordLayout() {

        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(
                InputMethodManager.SHOW_FORCED, 0);

        bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        fab.setImageResource(R.drawable.ic_reply);
        fbModeCenter = false;

        homepage_content_layout.setVisibility(View.GONE);
        addNewWordLayout.startAnimation(Animations.createFadeInAnimation(getApplicationContext(), 1000));
        addNewWordLayout.setVisibility(View.VISIBLE);
    }

    private void createAlertDiolag(String warning) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Uyarı");

        alertDialogBuilder.setMessage(warning)
                .setCancelable(true);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
