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
 * Created by Ran on 3/31/2018.
 */

public class IntroAdapter extends BaseAdapter {

    private ArrayList<DuoIntro> data;
    private Context context;

    public IntroAdapter(ArrayList<DuoIntro> data, Context context) {
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
        final DuoIntro item = data.get(i);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.intro_list_row, parent, false);
        ImageView ivIcon = v.findViewById(R.id.ivIcon);
        TextView tvTitle = v.findViewById(R.id.tvRowTitle);
        TextView tvIntro = v.findViewById(R.id.tvIntro);
        tvTitle.setText(item.getTitle());
        Picasso.with(context).load(item.getImageURL()).into(ivIcon);
        tvIntro.setText(item.getIntro());

        return v;
    }
}
//
////        View.OnClickListener listener = new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String getTitle = item.getTitle();
////                String getDescription = item.getIntro();
////                getDescription = Jsoup.parse(getDescription).text();
////                String getImage = item.getImageURL();
////                final Intent intent = new Intent(context, DuotorialActivity.class);
////                intent.putExtra("TITLE", getTitle);
////                intent.putExtra("DESCRIPTION", getDescription);
////                intent.putExtra("IMAGE", getImage);
////                AddToDuotorialDatabase.addData(getTitle,getImage);
////                context.startActivity(intent);
////
////
////            }
////        };
//        tvIntro.setOnClickListener(listener);
//        tvTitle.setOnClickListener(listener);
//        ivIcon.setOnClickListener(listener);