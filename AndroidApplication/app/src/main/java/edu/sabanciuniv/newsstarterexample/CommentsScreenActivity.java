package edu.sabanciuniv.newsstarterexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;
import android.app.ProgressDialog;

import android.content.DialogInterface;

import android.content.Intent;

import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.URL;

import java.util.ArrayList;

import java.util.List;


import edu.sabanciuniv.newsstarterexample.model.Comment;

public class CommentsScreenActivity extends AppCompatActivity {
    ProgressDialog commentsProgressDialog;
    CommentsScreenCustomAdapter commentsCustomAdapter;
    RecyclerView CommentsRecyclerView;
    List<Comment> commentsArr;
    int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_screen);
        commentsArr = new ArrayList<>();
        CommentsRecyclerView = findViewById(R.id.recViewComments);
        CommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedId = (int) getIntent().getSerializableExtra("newsid");
        commentsCustomAdapter = new CommentsScreenCustomAdapter(commentsArr, this);
        CommentsRecyclerView.setAdapter(commentsCustomAdapter);
        FetchComments task = new FetchComments();
        //request task execute
        task.execute("http://94.138.207.51:8080/NewsApp/service/news/getcommentsbynewsid/" + Integer.toString(selectedId));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            //finish();
            onBackPressed();
        }
        else if(item.getItemId() == R.id.action_create_comment)
        {
            Intent intent = new Intent(CommentsScreenActivity.this, PostCommentActivity.class);
            intent.putExtra("newsid", this.selectedId);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_comment_button, menu);
        return true;
    }

    class FetchComments extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            commentsProgressDialog = new ProgressDialog(CommentsScreenActivity.this);
            commentsProgressDialog.setTitle("Processing");
            commentsProgressDialog.setMessage("Wait");
            commentsProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            commentsProgressDialog.show();
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
            commentsArr.clear();
            try {
                JSONObject obj = new JSONObject(s);
                //validation
                if(obj.getInt("serviceMessageCode") == 1) {
                    JSONArray arr = obj.getJSONArray("items");
                    //loop
                    for(int i = 0; i < arr.length(); i++) {
                        JSONObject current = (JSONObject) arr.get(i);
                        Comment comment = new Comment(
                                current.getInt("id"),
                                current.getString("name"),
                                current.getString("text")
                        );
                        commentsArr.add(comment);
                    }
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(CommentsScreenActivity.this).create();
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
                commentsCustomAdapter.notifyDataSetChanged();
                commentsProgressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
