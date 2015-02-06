package taylor.com.instagramphotoviewer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import taylor.com.instagramphotoviewer.adapters.PhotosAdapter;
import taylor.com.instagramphotoviewer.model.Photo;


public class PhotoActivity extends ActionBarActivity {
    private static final String CLIENT_ID = "82bd7291b71f472f825a09229df548c4";
    private static final String API_ENDPOINT = "https://api.instagram.com/v1/media/popular?client_id=";
    private List<Photo> photos = new ArrayList<>();
    private PhotosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        adapter = new PhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(adapter);
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(API_ENDPOINT + CLIENT_ID, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++){
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        Photo photo = Photo.fromJson(photoJSON);
                        Log.d("PhotoActivity", photo.toString());
                        if (null != photo) photos.add(photo);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
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
