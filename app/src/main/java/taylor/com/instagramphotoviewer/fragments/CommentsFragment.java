package taylor.com.instagramphotoviewer.fragments;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import taylor.com.instagramphotoviewer.activities.PhotoActivity;
import taylor.com.instagramphotoviewer.R;
import taylor.com.instagramphotoviewer.adapters.CommentsAdapter;
import taylor.com.instagramphotoviewer.model.Comment;

/**
 * Created by rtteal on 2/7/2015.
 */
public class CommentsFragment extends DialogFragment {
    // 914831468823935392_421481629 is the photo id
    private static final String API_COMMENTS_ENDPOINT_PART1 =
            "https://api.instagram.com/v1/media/";
    private static final String API_COMMENTS_ENDPOINT_PART2 =
            "/comments?client_id=82bd7291b71f472f825a09229df548c4";
    private List<Comment> comments = new ArrayList<>();
    private CommentsAdapter adapter;
    private final String TAG = this.getClass().getSimpleName();
    private AsyncHttpClient client = new AsyncHttpClient();

    public static CommentsFragment newInstance(String id) {
        CommentsFragment frag = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container);
        getDialog().setTitle("Comments");
        adapter = new CommentsAdapter(PhotoActivity.context, comments);
        ListView lvComments = (ListView) view.findViewById(R.id.lvComments);
        lvComments.setAdapter(adapter);
        fetchComments(getArguments().getString("id"));
        return view;
    }

    private void fetchComments(String id){
        client.get(API_COMMENTS_ENDPOINT_PART1 + id + API_COMMENTS_ENDPOINT_PART2, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        Comment comment = Comment.fromJson(photoJSON);
                        Log.d(TAG, comment.toString());
                        if (null != comment) comments.add(comment);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
