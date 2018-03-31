package productions.ranuskin.meow.duotorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ran on 3/31/2018.
 */

public class IntroAdapter {

    private ArrayList<DuoIntro> data;
    private Context context;

    public IntroAdapter(ArrayList<DuoIntro> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public View getView(int i, View view, ViewGroup parent) {
        DuoIntro item = data.get(i);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.popular_list_row,parent,false);
        ImageView ivIcon = v.findViewById(R.id.ivIcon);
        TextView tvTitle = v.findViewById(R.id.tvRowTitle);
        TextView tvIntro = v.findViewById(R.id.tvIntro);


        tvTitle.setText(item.getTitle());
        ivIcon.setImageResource(item.getImageRes());
        tvIntro.setText(item.getIntro());

        return v;
    }
}
