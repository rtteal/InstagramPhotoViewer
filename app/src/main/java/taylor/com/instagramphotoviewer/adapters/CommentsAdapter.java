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
import taylor.com.instagramphotoviewer.model.Comment;

/**
 * Created by rtteal on 2/7/2015.
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {
    Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(30)
            .oval(false)
            .build();

    private static class ViewHolder {
        TextView tvComment;
        ImageView ivCommentProfilePic;
    }

    public CommentsAdapter(Context context, List<Comment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Comment comment = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_comment, parent, false);
            viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
            viewHolder.ivCommentProfilePic = (ImageView) convertView.findViewById(R.id.ivCommentProfilePic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String formattedComment = comment.comment;
        formattedComment = "<font color=\"#125674\"><b>" + comment.userName + "</b></font>  " + formattedComment;
        viewHolder.tvComment.setText(Html.fromHtml(formattedComment));
        viewHolder.ivCommentProfilePic.setImageResource(0);
        Picasso.with(getContext()).load(comment.profilePic).transform(transformation).resize(64,64).into(viewHolder.ivCommentProfilePic);
        return convertView;
    }
}
