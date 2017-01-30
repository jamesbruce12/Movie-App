package uk.ac.lincoln.students.bru14476346.moviefinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieDetails extends Activity {

    String filename = "movieData";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getExtras();
        String movie_name = bundle.getString("val");

        String overview = bundle.getString("val2");
        String release_date = bundle.getString("val3");
        String poster_image = bundle.getString("val4");

        setTitle("Movie Details");

        TextView overv = (TextView)findViewById(R.id.textView3);
        overv.setText(overview);

        TextView relDate = (TextView)findViewById(R.id.textView2);
        relDate.setText(release_date);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://image.tmdb.org/t/p/w500");
        stringBuilder.append(poster_image);
        String imageUrl = new String(stringBuilder.toString());








        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this)
                .load(imageUrl)
                .into(imageView);


    }

    public void saveMov(View view)
    {
        Bundle bundle = getIntent().getExtras();
        String movie_name = bundle.getString("val");

        File file = getFileStreamPath(filename);
        FileOutputStream outputStream;
        Toast.makeText(getApplicationContext(),movie_name +" has been saved!",Toast.LENGTH_LONG).show();

        if (file == null || !file.exists()) {
            try
            {
                outputStream = openFileOutput(filename, MODE_PRIVATE);
                outputStream.write(movie_name.getBytes());
                outputStream.write(",".getBytes());
                outputStream.close();
            }
            catch (Exception e)
            {

            }
        }
        else if(file.exists())
        {
        try
        {
            outputStream = openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(movie_name.getBytes());
            outputStream.write(",".getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
