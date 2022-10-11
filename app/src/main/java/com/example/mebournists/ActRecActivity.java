package com.example.mebournists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.mebournists.movie.MovieListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class ActRecActivity extends AppCompatActivity{

    public static ArrayList<Recommendation> recList = new ArrayList<Recommendation>();

    private ListView listView;
    private Button sortButton;
    private Button filterButton;
    private LinearLayout filterView1;
    private LinearLayout filterView2;
    private LinearLayout sortView;

    boolean sortHidden = true;
    boolean filterHidden = true;

    private Button allButton, lowButton, midButton, highButton;
    private Button durAscButton, durDescButton, nameAscButton, nameDescButton;

    private ArrayList<String> selectedFilters = new ArrayList<String>();
    private String currentSearchText = "";
    private SearchView searchView;

    private int white, darkGray, blue;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_rec);

        initSearchWidgets();
        initWidgets();
        setupData();
        setupList();
        setupOnclickListener();
        hideFilter();
        hideSort();
        initColors();
        lookSelected(durAscButton);
        lookSelected(allButton);
        selectedFilters.add("all");

        //navigation bar
        bottomNavigationView = findViewById(R.id.navigationBar);

        bottomNavigationView.setSelectedItemId(R.id.activity);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(),NewCalendarMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.activity:
                        return true;
                    case R.id.recommendation:
                        startActivity(new Intent(getApplicationContext(), MovieListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }


    private void initColors()
    {
        white = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        blue = ContextCompat.getColor(getApplicationContext(), R.color.blue);
        darkGray = ContextCompat.getColor(getApplicationContext(), R.color.darkerGray);
    }

    // Set up sort button functions
    private void unSelectAllSortButtons()
    {
        lookUnSelected(durAscButton);
        lookUnSelected(durDescButton);
        lookUnSelected(nameAscButton);
        lookUnSelected(nameDescButton);
    }

    private void unSelectAllFilterButtons()
    {
        lookUnSelected(allButton);
        lookUnSelected(lowButton);
        lookUnSelected(midButton);
        lookUnSelected(highButton);

    }

    // Changes colour to show whether a button is selected
    private void lookSelected(Button parsedButton)
    {
        parsedButton.setTextColor(white);
        parsedButton.setBackgroundColor(blue);
    }

    private void lookUnSelected(Button parsedButton)
    {
        parsedButton.setTextColor(blue);
        parsedButton.setBackgroundColor(darkGray);
    }

    // Initialize all widgets
    private void initWidgets()
    {
        sortButton = (Button) findViewById(R.id.sortButton);
        filterButton = (Button) findViewById(R.id.filterButton);
        filterView1 = (LinearLayout) findViewById(R.id.filterTabsLayout);
        filterView2 = (LinearLayout) findViewById(R.id.filterTabsLayout2);
        sortView = (LinearLayout) findViewById(R.id.sortTabsLayout2);

        lowButton = (Button) findViewById(R.id.lowFilter);
        midButton = (Button) findViewById(R.id.midFilter);
        highButton = (Button) findViewById(R.id.highFilter);
        allButton = (Button) findViewById(R.id.allFilter);

        durAscButton  = (Button) findViewById(R.id.durationAsc);
        durDescButton  = (Button) findViewById(R.id.durationDesc);
        nameAscButton  = (Button) findViewById(R.id.nameAsc);
        nameDescButton  = (Button) findViewById(R.id.nameDesc);
    }

    // initialize search widget, if no filter then display all activities
    private void initSearchWidgets()
    {
        searchView = (SearchView) findViewById(R.id.recListSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                currentSearchText = s;
                ArrayList<Recommendation> filteredRecommendations = new ArrayList<Recommendation>();

                for(Recommendation recommendation: recList)
                {
                    if(recommendation.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        if(selectedFilters.contains("all"))
                        {
                            filteredRecommendations.add(recommendation);
                        }
                        else
                        {
                            for(String filter: selectedFilters)
                            {
                                if (recommendation.getName().toLowerCase().contains(filter))
                                {
                                    filteredRecommendations.add(recommendation);
                                }
                            }
                        }
                    }
                }
                setAdapter(filteredRecommendations);

                return false;
            }
        });
    }

    // Initialize data with values
    private void setupData()
    {
        Recommendation act1 = new Recommendation("0", "Movies", 4, "Low", R.drawable.movie,"Movies are a great way to bond with your parent, you can find personalised recommendations from our Movie function!");
        recList.add(act1);

        Recommendation act2 = new Recommendation("1", "Camping", 5, "High",R.drawable.camping, "Camping can be a great way to get away from all the technology in our daily life and enjoy your time together. Make sure you plan ahead and make sure sleeping arrangements are appropriate for your parent and be aware of your their routine whether that includes medications or other habits.");
        recList.add(act2);

        Recommendation act3 = new Recommendation("2", "Games", 3, "Low",R.drawable.games,"There are plenty of fun games to enjoy between you and your parent. You can enjoy a game that you have played in the past, learn a new game together or teach one of your favourites to your parent. Fun games you can try include: Scrabble, Chess, Mahjong, Backgammon, Poker, Monopoly, Puzzles and many more!");
        recList.add(act3);

        Recommendation act4 = new Recommendation("3", "Shopping", 6, "Medium",R.drawable.shopping,"Walking doesn't always have to be outdoors. There are many indoor shopping centres which you can walk around with your aging parent in, these are ideal as they usually have air conditioning, flat surfaces and escalators");
        recList.add(act4);

        Recommendation act5 = new Recommendation("4", "Walk around neighbourhood", 2, "Medium", R.drawable.walk, "A short walk around your or your parent's neighbourhood can be a great way to catch some fresh air and have a nice conversation.");
        recList.add(act5);

        Recommendation act6 = new Recommendation("5", "Phone Call", 1, "Low",R.drawable.phonecall,"Phone calls can be a great way to get in contact with your parent and can mean a lot if they spend most of their time alone. Video calls are even better as your parent will be happy to see your face and know how you are doing!");
        recList.add(act6);

        Recommendation act7 = new Recommendation("6", "Fishing", 4, "Low",R.drawable.fishing,"Fishing is a relaxing activity can lower stress and anxiety as well as involving light physical activity which can be beneficial to seniors.");
        recList.add(act7);

        Recommendation act8 = new Recommendation("7", "Cooking",2,"Low",R.drawable.cooking, "Cooking a family recipe or incorporating new ingredients into your meals can be fun way to spend time with your parents. It is simple, doesn't require too much time and can bring you closer together.");
        recList.add(act8);

        Recommendation act9 = new Recommendation("8","Museum",3,"Medium",R.drawable.museum,"Learning about history can be interesting at any age, the museum is also indoors which is nice for when the weather is too hot to be outside.");
        recList.add(act9);

        Recommendation act10 = new Recommendation("9","Family", 3,"Low", R.drawable.familyalbum,"Going through old family photo albums or videos can be a great way to reminisce and bond over times spend together in the past.");
        recList.add(act10);
    }

    private void setupList()
    {
        listView = (ListView) findViewById(R.id.recListView);

        setAdapter(recList);
    }

    // Setup onclick listener
    private void setupOnclickListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)
            {
                Recommendation selectRecommendation = (Recommendation) (listView.getItemAtPosition(pos));
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id",selectRecommendation.getId());
                startActivity(showDetail);
            }

        });

    }

    // Function called to filter activities
    private void filterList(String status)
    {
        if(status != null && !selectedFilters.contains(status))
            selectedFilters.add(status);

        ArrayList<Recommendation> filteredRecommendations = new ArrayList<Recommendation>();

        for(Recommendation recommendation: recList)
        {
            for(String filter: selectedFilters)
            {
                if(recommendation.getActivityLevel().toLowerCase().contains(filter))
                {
                    if(currentSearchText == "")
                    {
                        filteredRecommendations.add(recommendation);
                    }
                    else
                    {
                        if(recommendation.getName().toLowerCase().contains(currentSearchText.toLowerCase()))
                        {
                            filteredRecommendations.add(recommendation);
                        }
                    }
                }
            }
        }

        setAdapter(filteredRecommendations);
    }

    // Setup filter and sort button functionalities
    public void allFilterTapped(View view)
    {
        selectedFilters.clear();
        selectedFilters.add("all");

        unSelectAllFilterButtons();
        lookSelected(allButton);

        setAdapter(recList);
    }

    public void lowFilterTapped(View view)
    {
        filterList("low");
        lookSelected(lowButton);
        lookUnSelected(allButton);

    }

    public void midFilterTapped(View view)
    {
        filterList("medium");
        lookSelected(midButton);
        lookUnSelected(allButton);

    }

    public void highFilterTapped(View view)
    {
        filterList("high");
        lookSelected(highButton);
        lookUnSelected(allButton);

    }

    public void showFilterTapped(View view)
    {
        if(filterHidden == true)
        {
            filterHidden = false;
            showFilter();
        }
        else
        {
            filterHidden = true;
            hideFilter();
        }
    }

    public void showSortTapped(View view)
    {
        if(sortHidden == true)
        {
            sortHidden = false;
            showSort();
        }
        else
        {
            sortHidden = true;
            hideSort();
        }
    }

    private void hideFilter()
    {
        searchView.setVisibility(View.GONE);
        filterView1.setVisibility(View.GONE);
        filterView2.setVisibility(View.GONE);
        filterButton.setText("FILTER");
    }

    private void showFilter()
    {
        searchView.setVisibility(View.VISIBLE);
        filterView1.setVisibility(View.VISIBLE);
        filterView2.setVisibility(View.VISIBLE);
        filterButton.setText("HIDE");
    }

    private void hideSort()
    {
        sortView.setVisibility(View.GONE);
        sortButton.setText("SORT");
    }

    private void showSort()
    {
        sortView.setVisibility(View.VISIBLE);
        sortButton.setText("HIDE");
    }

    public void durationASCTapped(View view)
    {
        Collections.sort(recList, Recommendation.durationAscending);
        checkForFilter();
        unSelectAllSortButtons();
        lookSelected(durAscButton);
    }

    public void durationDESCTapped(View view)
    {
        Collections.sort(recList, Recommendation.durationAscending);
        Collections.reverse(recList);
        checkForFilter();
        unSelectAllSortButtons();
        lookSelected(durDescButton);
    }

    public void nameASCTapped(View view)
    {
        Collections.sort(recList, Recommendation.nameAscending);
        checkForFilter();
        unSelectAllSortButtons();
        lookSelected(nameAscButton);
    }

    public void nameDESCTapped(View view)
    {
        Collections.sort(recList, Recommendation.nameAscending);
        Collections.reverse(recList);
        checkForFilter();
        unSelectAllSortButtons();
        lookSelected(nameDescButton);
    }
    /* Checks if any filters are selected, if not shows all activities, otherwise show all
    activities matching the filter
     */
    private void checkForFilter()
    {
        if(selectedFilters.contains("all"))
        {
            if(currentSearchText.equals(""))
            {
                setAdapter(recList);
            }
            else
            {
                ArrayList<Recommendation> filteredRecommendations = new ArrayList<Recommendation>();
                for(Recommendation recommendation: recList)
                {
                    if(recommendation.getName().toLowerCase().contains(currentSearchText))
                    {
                        filteredRecommendations.add(recommendation);
                    }
                }
                setAdapter(filteredRecommendations);
            }
        }
        else
        {
            filterList(null);
        }
    }

    private void setAdapter(ArrayList<Recommendation> recList)
    {
        RecAdapter adapter = new RecAdapter(getApplicationContext(), 0, recList);
        listView.setAdapter(adapter);
    }


}