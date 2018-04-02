package productions.ranuskin.meow.duotorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ran on 4/2/2018.
 */

public class BrowseAdapter extends BaseAdapter {
    private ArrayList<DuoCategory> data;
    private Context context;
    @Override
    public int getCount() {
        return data.size();
    }

    public BrowseAdapter(ArrayList<DuoCategory> data, Context context) {
        this.data = data;
        this.context=context;
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
        DuoCategory item = data.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.category_list_row,parent,false);
        ImageView ivIcon = v.findViewById(R.id.ivCategoryIcon);
        TextView tvTitle = v.findViewById(R.id.tvCategoryTitle);
        TextView tvTitle2 = v.findViewById(R.id.tvCategoryTitle2);


        tvTitle.setText(item.getHeaderFirst());
        ivIcon.setImageResource(item.getImageRes());
        tvTitle2.setText(item.getHeaderSecond());

        return v;
    }
}
