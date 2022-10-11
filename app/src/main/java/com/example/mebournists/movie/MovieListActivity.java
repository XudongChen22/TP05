/*
 * Copyright 2019 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mebournists.movie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.mebournists.ActRecActivity;
import com.example.mebournists.CalendarActivity;
import com.example.mebournists.MainActivity;
import com.example.mebournists.NewCalendarMainActivity;
import com.example.mebournists.R;
import com.example.mebournists.movie.RecommendationClient.Result;
import com.example.mebournists.movie.data.FileUtil;
import com.example.mebournists.movie.data.MovieItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** The main activity to provide interactions with users. */
public class MovieListActivity extends AppCompatActivity
    implements MovieFragment.OnListFragmentInteractionListener,
        RecommendationFragment.OnListFragmentInteractionListener {
  private static final String TAG = "OnDeviceRecommendationDemo";
  private static final String CONFIG_PATH = "config.json";  // Default config path in assets.

  private Config config;
  private RecommendationClient client;
  private final List<MovieItem> allMovies = new ArrayList<>();
  private final List<MovieItem> selectedMovies = new ArrayList<>();

  private Handler handler;
  private MovieFragment movieFragment;
  private RecommendationFragment recommendationFragment;
  BottomNavigationView bottomNavigationView;


  @Override
  protected void onResume() {
    super.onResume();
    bottomNavigationView.setSelectedItemId(R.id.recommendation);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tfe_re_activity_main);
    Log.v(TAG, "onCreate");

    // Load config file.
    try {
      config = FileUtil.loadConfig(getAssets(), CONFIG_PATH);
    } catch (IOException ex) {
      Log.e(TAG, String.format("Error occurs when loading config %s: %s.", CONFIG_PATH, ex));
    }

    // Load movies list.
    try {
      allMovies.clear();
      allMovies.addAll(FileUtil.loadMovieList(getAssets(), config.movieList));
      Collections.shuffle(allMovies);
    } catch (IOException ex) {
      Log.e(TAG, String.format("Error occurs when loading movies %s: %s.", config.movieList, ex));
    }

    client = new RecommendationClient(this, config);
    handler = new Handler();
    movieFragment =
        (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.movie_fragment);
    recommendationFragment =
        (RecommendationFragment)
            getSupportFragmentManager().findFragmentById(R.id.recommendation_fragment);

    //navigation bar
    bottomNavigationView = findViewById(R.id.navigationBar);

    bottomNavigationView.setSelectedItemId(R.id.recommendation);

    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

          case R.id.home:
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(0,0);
            return true;
          case R.id.calendar:
            startActivity(new Intent(getApplicationContext(), NewCalendarMainActivity.class));
            overridePendingTransition(0,0);
            return true;
          case R.id.activity:
            startActivity(new Intent(getApplicationContext(), ActRecActivity.class));
            overridePendingTransition(0,0);
            return true;
          case R.id.recommendation:
            return true;

        }
        return false;
      }
    });
  }

  @SuppressWarnings("AndroidJdkLibsChecker")
  @Override
  protected void onStart() {
    super.onStart();
    Log.v(TAG, "onStart");

    // Add favorite movies to the fragment.
    List<MovieItem> favoriteMovies =
        allMovies.stream().limit(config.favoriteListSize).collect(Collectors.toList());
    movieFragment.setMovies(favoriteMovies);

    handler.post(
        () -> {
          client.load();
        });
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.v(TAG, "onStop");
    handler.post(
        () -> {
          client.unload();
        });
  }

  /** Sends selected movie list and get recommendations. */
  private void recommend(final List<MovieItem> movies) {
    handler.post(
        () -> {
          // Run inference with TF Lite.
          Log.d(TAG, "Run inference with TFLite model.");
          List<Result> recommendations = client.recommend(movies);

          // Show result on screen
          showResult(recommendations);
        });
  }

  /** Shows result on the screen. */
  private void showResult(final List<Result> recommendations) {
    // Run on UI thread as we'll updating our app UI
    runOnUiThread(() -> recommendationFragment.setRecommendations(recommendations));
  }

  @Override
  public void onItemSelectionChange(MovieItem item) {
    if (item.selected) {
      if (!selectedMovies.contains(item)) {
        selectedMovies.add(item);
      }
    } else {
      selectedMovies.remove(item);
    }

    if (!selectedMovies.isEmpty()) {
      // Log selected movies.
      StringBuilder sb = new StringBuilder();
      sb.append("Select movies in the following order:\n");
      for (MovieItem movie : selectedMovies) {
        sb.append(String.format("  movie: %s\n", movie));
      }
      Log.d(TAG, sb.toString());

      // Recommend based on selected movies.
      recommend(selectedMovies);
    } else {
      // Clear result list.
      showResult(new ArrayList<Result>());
    }
  }

  /** Handles click event of recommended movie. */
  @Override
  public void onClickRecommendedMovie(MovieItem item) {
    // Show message for the clicked movie.
    String message = String.format("Clicked recommended movie: %s.", item.title);
    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  }
}
