package taylor.com.instagramphotoviewer.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rtteal on 2/4/2015.
 */
public class Photo {
    public final String caption, url, userName;
    public final int height, likesCount;

    private Photo(String caption, String url, String userName, int height, int likesCount){
        this.caption = caption;
        this.url = url;
        this.userName = userName;
        this.height = height;
        this.likesCount = likesCount;
    }

    public static Photo fromJson(JSONObject json){
        if (null == json) return null;
        try {
            String caption = json.isNull("caption") ? null : json.getJSONObject("caption").getString("text");
            String url = json.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            String userName = json.getJSONObject("user").getString("username");
            int height = json.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            int likesCount = json.getJSONObject("likes").getInt("count");
            return new Photo(caption, url, userName, height, likesCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
/*
    @Override
    public String toString(){
        return String.format("{caption: %s,  url: %s,  userName: %s,  height: %s,  likesCount: %s}",
                caption, url, userName, height, likesCount);
    } */
}
