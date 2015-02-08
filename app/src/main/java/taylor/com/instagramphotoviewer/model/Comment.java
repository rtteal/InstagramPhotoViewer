package taylor.com.instagramphotoviewer.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rtteal on 2/7/2015.
 */
public class Comment {
    public final String userName, comment, profilePic;

    public Comment(String userName, String comment, String profilePic){
        this.userName = userName;
        this.comment = comment;
        this.profilePic = profilePic;
    }

    public static Comment fromJson(JSONObject json) {
        if (null == json) return null;
        try {
            String userName = json.isNull("from") ? null : json.getJSONObject("from").getString("username");
            String profilePic = json.isNull("from") ? null : json.getJSONObject("from").getString("profile_picture");
            String comment = json.getString("text");
            return new Comment(userName, comment, profilePic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
