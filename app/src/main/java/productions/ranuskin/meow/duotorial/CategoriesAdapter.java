package productions.ranuskin.meow.duotorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ran on 4/7/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */

public class CategoriesAdapter extends BaseAdapter {
    private ArrayList<CategoryDispObj> data;
    private Context context;

    public CategoriesAdapter(ArrayList<CategoryDispObj> data, Context context) {
        this.data = data;
        this.context = context;
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

    public View getView(int i, View view, ViewGroup parent) {
        CategoryDispObj item = data.get(i);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.category_display_row, parent, false);
        ImageView ivIcon = v.findViewById(R.id.ivIcon);
        TextView tvTitle = v.findViewById(R.id.tvRowTitle);


        tvTitle.setText(item.getTitle());
        Picasso.with(context).load(item.getImageURL()).into(ivIcon);

        return v;
    }
}
