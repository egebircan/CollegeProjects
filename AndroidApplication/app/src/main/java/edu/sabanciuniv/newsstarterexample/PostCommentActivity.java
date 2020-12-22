package edu.sabanciuniv.newsstarterexample;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;

import android.widget.EditText;


import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.InputStream;



import java.io.InputStreamReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;

import java.io.DataOutputStream;

import android.content.Intent;
import org.json.JSONObject;


import android.app.ProgressDialog;

import java.net.URL;


import static edu.sabanciuniv.newsstarterexample.R.layout.activity_page_post_comment;

public class PostCommentActivity extends AppCompatActivity {
    EditText nameInput;
    EditText commentInput;

    int idSelected;
    String name;
    String text;

    Button sendCommentButton;
    ProgressDialog dialogBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_page_post_comment);
        idSelected = (int) getIntent().getSerializableExtra("newsid");
        this.sendCommentButton = this.findViewById(R.id.postCommentBtn);
        this.sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                PostCommentTask task = new PostCommentTask();
                task.execute();
                Intent intent = new Intent(PostCommentActivity.this, CommentsScreenActivity.class);
                intent.putExtra("newsid", idSelected);
                startActivity(intent);
            }
        });
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try
        {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            try
            {
                is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    class PostCommentTask extends AsyncTask<Void, Void, Integer>
    {
        @Override
        protected void onPreExecute() {
            commentInput = findViewById(R.id.commentInput);
            nameInput = findViewById(R.id.nameInput);

            text = commentInput.getText().toString();
            name = nameInput.getText().toString();

            dialogBox = new ProgressDialog(PostCommentActivity.this);
            dialogBox.setTitle("processing");
            dialogBox.setMessage("wait");
            dialogBox.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialogBox.show();

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                //http request
                URL url = new URL("http://94.138.207.51:8080/NewsApp/service/news/savecomment");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("name", name);
                jsonParam.put("text", text);
                jsonParam.put("news_id", Integer.toString(idSelected));

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                String responseData = convertStreamToString(inputStream);

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());
                Log.i("RESPONSE", responseData);

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 1;
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            dialogBox.dismiss();
        }
    }
}
