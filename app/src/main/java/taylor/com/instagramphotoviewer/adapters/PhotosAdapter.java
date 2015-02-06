package taylor.com.instagramphotoviewer.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import taylor.com.instagramphotoviewer.R;
import taylor.com.instagramphotoviewer.model.Photo;

/**
 * Created by rtteal on 2/5/2015.
 */
public class PhotosAdapter extends ArrayAdapter<Photo>{

    public PhotosAdapter(Context context, List<Photo> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
}
