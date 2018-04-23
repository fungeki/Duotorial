package productions.ranuskin.meow.duotorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Ran on 4/21/2018.
 */

public class MyDuoAdapter extends BaseAdapter {
    private DataSnapshot snapshot;
    private Context context;

    public MyDuoAdapter(DataSnapshot snapshot, Context context) {
        this.snapshot = snapshot;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (int) snapshot.getChildrenCount();//the system only saves up to 50 items
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
        DataSnapshot item = snapshot.child(position+"");
        String value[] = item.getValue().toString().split("=");
        String title = value[0].substring(1,value[0].length());
        String url = value[1].substring(0,value[1].length()-1);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.category_display_row,parent,false);
        ImageView ivIcon = v.findViewById(R.id.ivIcon);
        TextView tvTitle = v.findViewById(R.id.tvRowTitle);


        tvTitle.setText(title);
        Picasso.with(context).load(url).into(ivIcon);

        return v;
    }
}
