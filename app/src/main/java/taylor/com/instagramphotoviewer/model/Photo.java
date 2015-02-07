package taylor.com.instagramphotoviewer.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rtteal on 2/4/2015.
 */
public class Photo {
    public final String id, createdTime, caption, url, userName, profilePicture;
    public final int height, width, likesCount;
    private final String TAG = this.getClass().getSimpleName();
    private static final int ONE_MINUTE = 60;
    private static final int ONE_HOUR = 60 * 60;
    private static final int ONE_DAY = 60 * 60 * 24;
    private static final int ONE_WEEK = 60 * 60 * 24 * 7;
    private static final int ONE_MONTH = 60 * 60 * 24 * 7 * 4;

    private Photo(String id, String createdTime, String caption, String url, String userName, String profilePicture,
                  int height, int width, int likesCount){
        this.id = id;
        this.createdTime = createdTime;
        this.url = url;
        this.userName = userName.trim();
        this.height = height;
        this.width = width;
        this.likesCount = likesCount;
        this.profilePicture = profilePicture;
        this.caption = null == caption ? "" : caption.trim();
    }

    public static Photo fromJson(JSONObject json){
        if (null == json) return null;
        try {
            String id = json.getString("id");
            String createdTime = getFormattedTime(json.getString("created_time"));
            String caption = json.isNull("caption") ? null : json.getJSONObject("caption").getString("text");
            String url = json.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            String userName = json.getJSONObject("user").getString("username");
            String profilePicture = json.getJSONObject("user").getString("profile_picture");
            int height = json.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            int width = json.getJSONObject("images").getJSONObject("standard_resolution").getInt("width");
            int likesCount = json.getJSONObject("likes").getInt("count");
            return new Photo(id, createdTime, caption, url, userName, profilePicture, height, width, likesCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFormattedTime(final String input){
        final long now = System.currentTimeMillis()/1000;
        final long longCreatedAt = Long.parseLong(input);
        final long elapsedTime = now - longCreatedAt;
        if (elapsedTime < 0) {
            return "1s";
        } else if(elapsedTime < ONE_MINUTE){
            return elapsedTime + "s";
        } else if (elapsedTime < ONE_HOUR){
            return Math.round(elapsedTime/ONE_MINUTE) + "m";
        } else if (elapsedTime < ONE_DAY){
            return Math.round(elapsedTime/ONE_HOUR) + "h";
        } else if (elapsedTime < ONE_WEEK){
            return Math.round(elapsedTime/ONE_DAY) + "d";
        } else {
            return Math.round(elapsedTime/ONE_WEEK) + "w";
        }
    }

    @Override
    public String toString(){
        return String.format("{caption: %s,  url: %s,  userName: %s,  height: %s, width: %s,  likesCount: %s}",
                caption, url, userName, height, width, likesCount);
    }
}
