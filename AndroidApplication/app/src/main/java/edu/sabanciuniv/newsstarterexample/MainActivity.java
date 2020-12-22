package edu.sabanciuniv.newsstarterexample;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import androidx.recyclerview.widget.RecyclerView;
import android.widget.ArrayAdapter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.net.URL;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import android.os.AsyncTask;

import android.app.AlertDialog;

import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;


import java.util.ArrayList;
import java.util.Date;

import android.widget.AdapterView;

import java.util.List;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import edu.sabanciuniv.newsstarterexample.model.News;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    NewsScreenCustomAdapter newsScreenCustomAdapter;
    Spinner spinner;
    ProgressDialog categoryProgressDialog;
    RecyclerView newsRecyclerView;
    List<News> newsArray;
    ProgressDialog newsCateProgressDialog;
    ArrayAdapter<String> adapterCategory;
    List<String> categoriesArray;
    List<Integer> categoryIdsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoriesArray = new ArrayList<>();
        newsArray = new ArrayList<>();
        categoryIdsArray = new ArrayList<>();


        spinner = findViewById(R.id.spinner);
        newsRecyclerView = findViewById(R.id.mobile_list);
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesArray);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(categoryIdsArray.get(position) == 0) {
                    NewsTask task = new NewsTask();
                    //http request
                    task.execute("http://94.138.207.51:8080/NewsApp/service/news/getall");
                }
                else {
                    GetCategoryNewsTask getCategoryNewsTask = new GetCategoryNewsTask();
                    //http request
                    getCategoryNewsTask.execute("http://94.138.207.51:8080/NewsApp/service/news/getbycategoryid/" + categoryIdsArray.get(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { //solve problem
                //why cant it remove this method??
            }
        });

         newsScreenCustomAdapter = new NewsScreenCustomAdapter(newsArray, this, new NewsScreenCustomAdapter.NewsClickListener() {
            @Override
            public void newsClicked(News n) {
                Intent intent = new Intent(MainActivity.this, NewsDetailScreenActivity.class);
                intent.putExtra("selectednewsid", n.getId());
                intent.putExtra("selectednewstext", n.getText());
                intent.putExtra("selectednewstitle", n.getTitle());
                intent.putExtra("selectednewsbitmap", n.getBitmap());
                startActivity(intent);
            }
        });
        newsRecyclerView.setAdapter(newsScreenCustomAdapter);
        CategoriesAsync categoriesAsync = new CategoriesAsync();
        categoriesAsync.execute("http://94.138.207.51:8080/NewsApp/service/news/getallnewscategories");
    }

    class NewsTask extends AsyncTask<String, Void, String>  {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            StringBuilder buffer = new StringBuilder();
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = "";
                while((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            newsArray.clear();
            try {
                JSONObject obj = new JSONObject(s);
                if(obj.getInt("serviceMessageCode") == 1)
                {
                    JSONArray arr = obj.getJSONArray("items");

                    for(int i = 0; i < arr.length(); i++)
                    {
                        JSONObject current = (JSONObject) arr.get(i);

                        long date = current.getLong("date");
                        Date objDate = new Date(date);

                        News item = new News(
                                current.getInt("id"),
                                current.getString("title"),
                                current.getString("text"),
                                current.getString("image"),
                                objDate
                        );
                        newsArray.add(item);
                    }
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Service Message Code is 0! Service Error!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

                newsScreenCustomAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class CategoriesAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            categoryProgressDialog = new ProgressDialog(MainActivity.this);
            categoryProgressDialog.setTitle("processing");
            categoryProgressDialog.setMessage("wait");
            categoryProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            categoryProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            StringBuilder buffer = new StringBuilder();
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                while((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
            }
            catch (MalformedURLException e) {
                Log.e("DEV", e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("DEV", e.toString());
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            categoryIdsArray.clear();
            categoriesArray.clear();
            categoriesArray.add("All");
            categoryIdsArray.add(0);
            try {
                JSONObject obj = new JSONObject(s);
                if(obj.getInt("serviceMessageCode") == 1) {
                    JSONArray arr = obj.getJSONArray("items");
                    for(int i = 0; i < arr.length(); i++) {
                        JSONObject current = (JSONObject) arr.get(i);
                        categoriesArray.add(current.getString("name"));
                        categoryIdsArray.add(current.getInt("id"));
                    }
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("service down");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                adapterCategory.notifyDataSetChanged();
                categoryProgressDialog.dismiss();
            } catch (JSONException e) {
                Log.e("DEV", e.toString());
                e.printStackTrace();
            }
        }
    }

    class GetCategoryNewsTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            newsCateProgressDialog = new ProgressDialog(MainActivity.this);
            newsCateProgressDialog.setTitle("processing");
            newsCateProgressDialog.setMessage("wait");
            newsCateProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            newsCateProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            StringBuilder buffer = new StringBuilder();
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                while((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            newsArray.clear();
            try {
                JSONObject obj = new JSONObject(s);
                if(obj.getInt("serviceMessageCode") == 1) {
                    JSONArray arr = obj.getJSONArray("items");
                    for(int i = 0; i < arr.length(); i++) {
                        JSONObject current = (JSONObject) arr.get(i);
                        long date = current.getLong("date");
                        Date objDate = new Date(date);
                        News item = new News(
                                current.getInt("id"),
                                current.getString("title"),
                                current.getString("text"),
                                current.getString("image"),
                                objDate
                        );
                        newsArray.add(item);
                    }
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("service down");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                newsScreenCustomAdapter.notifyDataSetChanged();
                newsCateProgressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
