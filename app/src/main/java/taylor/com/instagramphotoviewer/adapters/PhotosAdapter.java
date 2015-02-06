package taylor.com.instagramphotoviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import taylor.com.instagramphotoviewer.R;
import taylor.com.instagramphotoviewer.model.Photo;

/**
 * Created by rtteal on 2/5/2015.
 */
public class PhotosAdapter extends ArrayAdapter<Photo>{

    private static class ViewHolder{
        ImageView ivPhotos;
        TextView tvCaption;
    }

    public PhotosAdapter(Context context, List<Photo> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Photo photo = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_photo, parent, false);
            viewHolder.ivPhotos = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCaption.setText(photo.caption);
        viewHolder.ivPhotos.setImageResource(0);
        Picasso.with(getContext()).load(photo.url).into(viewHolder.ivPhotos);
        return convertView;
    }
}
