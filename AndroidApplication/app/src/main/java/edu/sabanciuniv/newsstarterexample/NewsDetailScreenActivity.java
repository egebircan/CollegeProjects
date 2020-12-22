package edu.sabanciuniv.newsstarterexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.view.Menu;

import android.widget.TextView;
import android.graphics.Bitmap;
import android.view.MenuItem;


import android.os.Bundle;

import android.widget.ImageView;

public class NewsDetailScreenActivity extends AppCompatActivity {

    ImageView newsImage;
    int selectedId;

    TextView newsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_news_detail);
        newsImage = findViewById(R.id.imgnewsdetail);
        newsText = findViewById(R.id.txtnewsdesc);

        //assigning variables
        String selectedTitle = (String) getIntent().getSerializableExtra("selectednewstitle");

        Bitmap bitmap = getIntent().getParcelableExtra("selectednewsbitmap");
        String selectedText = (String) getIntent().getSerializableExtra("selectednewstext");

        int selectedId = (int) getIntent().getSerializableExtra("selectednewsid");

        //settings
        newsText.setText(selectedText);

        newsImage.setImageBitmap(bitmap);
        this.selectedId = selectedId;

        getSupportActionBar().setTitle(selectedTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        else if(item.getItemId() == R.id.action_comment) {
            Intent intent = new Intent(NewsDetailScreenActivity.this, CommentsScreenActivity.class);
            intent.putExtra("newsid", this.selectedId);
            startActivity(intent);
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.comments_button, menu);
        //returns boolean cause of the event listener
        return true;
    }


}
