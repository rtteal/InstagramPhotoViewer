package taylor.com.instagramphotoviewer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import taylor.com.instagramphotoviewer.R;
import taylor.com.instagramphotoviewer.model.Photo;
import taylor.com.instagramphotoviewer.util.DeviceDimensionsHelper;

/**
 * Created by rtteal on 2/5/2015.
 */
public class PhotosAdapter extends ArrayAdapter<Photo>{
    public final int DEVICE_HEIGHT = DeviceDimensionsHelper.getDisplayHeight(getContext());
    public final int DEVICE_WIDTH = DeviceDimensionsHelper.getDisplayWidth(getContext());
    Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(30)
            .oval(false)
            .build();

    public static class ViewHolder{
        ImageView ivProfilePic;
        ImageView ivPhotos;
        TextView tvCaption;
        TextView tvPhotoHeader;
        TextView tvTime;
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
            viewHolder.ivProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);
            viewHolder.tvPhotoHeader = (TextView) convertView.findViewById(R.id.tvPhotoHeader);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String formattedCaption = photo.caption;
        //formattedCaption = formattedCaption.replaceAll("(^|[^0-9A-Z&/]+)(#|\\uFF03)([0-9A-Z_]*[A-Z_]+[a-z0-9_\\\\u00c0-\\\\u00d6\\\\u00d8-\\\\u00f6\\\\u00f8-\\\\u00ff]*)", "<font color=\"#125674\"><b>$1</b></font>");
        formattedCaption = "<font color=\"#125674\"><b>" + photo.userName + "</b></font>  " + formattedCaption;
        viewHolder.tvCaption.setText(Html.fromHtml(formattedCaption));
        viewHolder.tvTime.setText(photo.createdTime);
        viewHolder.tvPhotoHeader.setText(photo.userName);
        viewHolder.ivPhotos.setImageResource(0);
        viewHolder.ivProfilePic.setImageResource(0);
        Picasso.with(getContext()).load(photo.url).into(viewHolder.ivPhotos);
        //Picasso.with(getContext()).load(photo.profilePicture).into(viewHolder.ivProfilePic);
        Picasso.with(getContext()).load(photo.profilePicture).transform(transformation).resize(64,64).into(viewHolder.ivProfilePic);
        /*
        if (photo.width > DEVICE_WIDTH){
            Picasso.with(getContext()).load(photo.url).resize(0, DEVICE_WIDTH).into(viewHolder.ivPhotos);
        } else {
            Picasso.with(getContext()).load(photo.url).fit().into(viewHolder.ivPhotos);
        } */
        return convertView;
    }
}
