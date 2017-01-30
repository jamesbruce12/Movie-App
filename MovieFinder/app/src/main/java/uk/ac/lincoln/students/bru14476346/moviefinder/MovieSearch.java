package uk.ac.lincoln.students.bru14476346.moviefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
// add below
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import android.view.View;
import android.widget.*;
import java.util.Date;
import android.util.Log;

public class MovieSearch extends Activity {


    private final String TMDB_API_KEY = "ef68bfed72780ce7ae801b9daba23069";
    ArrayList<String> movieTitle = new ArrayList<String>();
    ArrayList<String> overview = new ArrayList<String>();
    ArrayList<String> releasedate = new ArrayList<String>();
    ArrayList<String> image = new ArrayList<String>();
    // json test string
    String jsonTest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        setTitle("Movie Search");
        ListView listView = (ListView) findViewById(R.id.listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Start your Activity according to the item just clicked.
                String val = (String) parent.getItemAtPosition(position);
                String overview1 = overview.get(position);
                String relasedate1 = releasedate.get(position);
                String image1 = image.get(position);
                Intent intent = new Intent(MovieSearch.this, MovieDetails.class);


                Bundle bundle = new Bundle();

                bundle.putString("val", val);
                bundle.putString("val2", overview1);
                bundle.putString("val3", relasedate1);
                bundle.putString("val4", image1);
                intent.putExtras(bundle);

                startActivity(intent);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_search, menu);
        return true;
    }

    //searchMov = Button, editText = Text box

    public boolean searchMov(View view)
    {



        new AsyncTaskParseXml1().execute();

        return false;
        }

        public class AsyncTaskParseXml1 extends AsyncTask<String ,String ,String >
        {


            @Override
            protected void onPreExecute()
            {


            }


            @Override
            protected String doInBackground(String... arg0) {


                //MAKE THE STRING
                EditText query = (EditText) findViewById(R.id.editText);

                String query1 = query.getText().toString();
                query1 = query1.replace(' ', '+');
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("http://api.themoviedb.org/3/search/movie");
                stringBuilder.append("?api_key=" + TMDB_API_KEY);
                stringBuilder.append("&language=en-US&query=" + query1);
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



                        movieTitle.add(result.getString("title"));
                        overview.add(result.getString("overview"));
                        releasedate.add(result.getString("release_date"));
                        image.add(result.getString("poster_path"));
                    }

                    //jsonTest is all the data the query returns,
                    //i need to separate it and clean it up
                    //allow for another screen that can show the movie details in fullscreen
                    //UI design

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

               // TextView textView2 = (TextView) findViewById(R.id.textView);
                //textView2.setText(jsonTest);

                ListView list = (ListView)findViewById(R.id.listView);
                ArrayAdapter<String> tweetArrayAdapter = new ArrayAdapter<String>(MovieSearch.this, android.R.layout.simple_expandable_list_item_1, movieTitle);
                list.setAdapter(tweetArrayAdapter);


            }

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
