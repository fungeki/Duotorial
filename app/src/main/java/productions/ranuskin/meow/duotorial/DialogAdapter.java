package productions.ranuskin.meow.duotorial;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ran Loock on 4/6/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */

public class DialogAdapter extends BaseAdapter {
    private ArrayList<DuotorialDialogPreview> data;
    private Context context;
    private int currentStep;

    public DialogAdapter(ArrayList<DuotorialDialogPreview> data, Context context, int currentStep) {
        this.data = data;
        this.context = context;
        this.currentStep = currentStep;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DuotorialDialogPreview item = data.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.steps_dialog_row, parent, false);
        ImageView ivImage = v.findViewById(R.id.ivStepImage);
        TextView tvBold = v.findViewById(R.id.tvHeadline);
        if (currentStep == position) {
            v.setBackgroundColor(context.getResources().getColor(R.color.brightGreen));
        }
        if (item.getImageURL().contains(".gif")){
            Uri uri = Uri.parse(item.getImageURL());
            Glide
                    .with(context)
                    .load(uri)
                    .into(ivImage);
        }else {
            Picasso.with(context).load(item.getImageURL()).into(ivImage);
        }


        tvBold.setText(item.getBoldPreview());

        return v;
    }
}
