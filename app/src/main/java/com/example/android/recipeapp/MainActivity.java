package com.example.android.recipeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText recipeName;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeName = (EditText) findViewById(R.id.recipeName);
        search = (Button) findViewById(R.id.searchButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncFetch().execute();

            }
        });
    }

    //Private inner class used to fetch data from API
    private class AsyncFetch extends AsyncTask<Object, Void, Object> {

        //Recipe that user has entered
        String recipe = recipeName.getText().toString();

        //Personal API key
        final String apiKey = "d64d0997b5e154a79dd3740fd9424e35";
        final String apiUrl = "https://www.food2fork.com/api/search?key=" + apiKey + "&q=" + recipe;

        //List used to store recipes returned by the API
        final ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        @Override
        protected Object doInBackground(Object[] objects) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiUrl).build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                return response.body().string();
            }
            catch(IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            String apiData = o.toString();

            try {
                JSONObject json = new JSONObject(apiData);
                JSONArray jsonRecipes = json.getJSONArray("recipes");

                for(int i = 0; i < jsonRecipes.length(); i++) {
                    JSONObject jsonRecipe = jsonRecipes.getJSONObject(i);
                    String publisher = jsonRecipe.getString("publisher");
                    String title = jsonRecipe.getString("title");
                    String imgUrl = jsonRecipe.getString("image_url");
                    String sourceUrl = jsonRecipe.getString("source_url");
                    Recipe recipe = new Recipe(publisher, title, imgUrl, sourceUrl);
                    recipes.add(recipe);
                }

                Intent recipesPage = new Intent(MainActivity.this, RecipesActivity.class);
                Bundle bundle = new Bundle();

                bundle.putParcelableArrayList("recipeList", recipes);
                recipesPage.putExtras(bundle);
                startActivity(recipesPage);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
