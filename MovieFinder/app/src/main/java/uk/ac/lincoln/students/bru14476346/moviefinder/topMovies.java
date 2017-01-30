package uk.ac.lincoln.students.bru14476346.moviefinder;


import java.io.StringReader;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


public class topMovies extends Activity{



    private final String TMDB_API_KEY = "ef68bfed72780ce7ae801b9daba23069";
    ArrayList<String> movieTitle = new ArrayList<String>();
    ArrayList<String> overview = new ArrayList<String>();
    ArrayList<String> releasedate = new ArrayList<String>();
    ArrayList<String> image = new ArrayList<String>();

    // json test string
    String jsonTest;

    //http://www.androidhive.info/2011/10/android-listview-tutorial/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_movies);
        setTitle("Top 20");
        new AsyncTaskParseXml().execute();

        ListView listView = (ListView) findViewById(R.id.listView2);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Start your Activity according to the item just clicked.

                //test toast
                //Toast.makeText(getApplicationContext(), "Button:" + val, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(topMovies.this, MovieDetails.class);
                Bundle bundle = new Bundle();

                String val = (String) parent.getItemAtPosition(position);
                String overview1 = overview.get(position);
                String relasedate1 = releasedate.get(position);
                String image1 = image.get(position);
                bundle.putString("val", val);
                bundle.putString("val2", overview1);
                bundle.putString("val3", relasedate1);
                bundle.putString("val4",image1);

                intent.putExtras(bundle);

                startActivity(intent);


            }
        });




    }



    public class AsyncTaskParseXml extends AsyncTask<String,String,String> {



        @Override
        protected void onPreExecute()
        {}

        @Override
        protected String doInBackground(String... arg0) {


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://api.themoviedb.org/3/movie/top_rated");
            stringBuilder.append("?api_key=" + TMDB_API_KEY);
            stringBuilder.append("&language=en-US&&page=1");
            String yourXmlServiceUrl = new String(stringBuilder.toString());

            try {

                JsonMovie jParser = new JsonMovie();
                String json = jParser.getJsonFromUrl(yourXmlServiceUrl);
                jsonTest = json.toString();
                JSONObject jsonResult = new JSONObject(jsonTest);

                JSONArray results = jsonResult.getJSONArray("results");

                for(int i = 0; i < results.length();i++)
                {
                    JSONObject result = results.getJSONObject(i);
                    String original_title = result.getString("original_title");
                    movieTitle.add(result.getString("title"));
                    overview.add(result.getString("overview"));
                    releasedate.add(result.getString("release_date"));
                    image.add(result.getString("poster_path"));


                }
            }


        catch(Exception e)
        {
            e.printStackTrace();
        }
            return null;
    }




        @Override
        protected void onPostExecute(String strFromDoInBg)
        {

            //TextView textView2 = (TextView)findViewById(R.id.jsonText);
            //textView2.setText(jsonTest);

            ListView list = (ListView)findViewById(R.id.listView2);
            ArrayAdapter<String> tweetArrayAdapter = new ArrayAdapter<String>(topMovies.this, android.R.layout.simple_expandable_list_item_1, movieTitle);
            list.setAdapter(tweetArrayAdapter);
            


        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_movies, menu);
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
