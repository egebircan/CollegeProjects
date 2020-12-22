package edu.sabanciuniv.newsstarterexample;

import android.graphics.Bitmap;



import java.io.InputStream;

import java.net.MalformedURLException;

import android.graphics.BitmapFactory;

import android.os.AsyncTask;

import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;

import java.net.URL;

import edu.sabanciuniv.newsstarterexample.model.News;

public class FetchImage extends AsyncTask<News, Void, Bitmap> {

    ImageView imageView;


    public FetchImage(ImageView imgView) {

        this.imageView = imgView;
    }

    @Override
    protected Bitmap doInBackground(News... newsItems) {
        News current = newsItems[0];
        Bitmap bitmap = null;
        try {
            URL url = new URL(current.getImagePath());
            InputStream is = new BufferedInputStream(url.openStream());
            bitmap = BitmapFactory.decodeStream(is);
            current.setBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
