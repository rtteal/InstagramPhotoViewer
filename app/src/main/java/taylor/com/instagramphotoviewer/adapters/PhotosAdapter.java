package taylor.com.instagramphotoviewer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import taylor.com.instagramphotoviewer.activities.PhotoActivity;
import taylor.com.instagramphotoviewer.R;
import taylor.com.instagramphotoviewer.fragments.CommentsFragment;
import taylor.com.instagramphotoviewer.model.Photo;

/**
 * Created by rtteal on 2/5/2015.
 */
public class PhotosAdapter extends ArrayAdapter<Photo>{
    Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(30)
            .oval(false)
            .build();

    private static class ViewHolder {
        ImageView ivProfilePic;
        ImageView ivPhotos;
        TextView tvCaption;
        TextView tvPhotoHeader;
        TextView tvTime;
        TextView tvLikes;
        TextView tvComments;
    }

    public PhotosAdapter(Context context, List<Photo> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Photo photo = getItem(position);
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
            viewHolder.tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.tvComments = (TextView) convertView.findViewById(R.id.tvComments);
            viewHolder.tvComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentsFragment comments = CommentsFragment.newInstance(photo.id);
                    comments.show(PhotoActivity.fragmentManager, "fragment_comments");
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String formattedCaption = photo.caption;
        // attempting to highlight hashtags...
        //formattedCaption = formattedCaption.replaceAll("(^|[^0-9A-Z&/]+)(#|\\uFF03)([0-9A-Z_]*[A-Z_]+[a-z0-9_\\\\u00c0-\\\\u00d6\\\\u00d8-\\\\u00f6\\\\u00f8-\\\\u00ff]*)", "<font color=\"#125674\"><b>$1</b></font>");
        formattedCaption = "<font color=\"#125674\"><b>" + photo.userName + "</b></font>  " + formattedCaption;
        viewHolder.tvCaption.setText(Html.fromHtml(formattedCaption));
        viewHolder.tvTime.setText(photo.createdTime);
        viewHolder.tvPhotoHeader.setText(photo.userName);
        viewHolder.tvComments.setText("view all " + NumberFormat.getNumberInstance(Locale.US).format(photo.commentsCount) + " comments");
        viewHolder.tvLikes.setText(NumberFormat.getNumberInstance(Locale.US).format(photo.likesCount) + " likes");
        viewHolder.ivPhotos.setImageResource(0);
        viewHolder.ivProfilePic.setImageResource(0);
        Picasso.with(getContext()).load(photo.url).placeholder(R.drawable.placeholder).into(viewHolder.ivPhotos);
        Picasso.with(getContext()).load(photo.profilePicture).transform(transformation).resize(64,64).into(viewHolder.ivProfilePic);
        return convertView;
    }

    public void showDialog(){
        Log.d("debug", "im the wrong class");
    }
}
