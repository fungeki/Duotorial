package productions.ranuskin.meow.duotorial;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyDuoFragment extends Fragment {
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private ListView lvMyDuo;
    private DataSnapshot data;


    public MyDuoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_duo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvMyDuo = view.findViewById(R.id.lvMyDuo);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRef = mDatabase.child("users").child(user.getUid()).child("history");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = dataSnapshot;
                MyDuoAdapter adapter = new MyDuoAdapter(dataSnapshot, getContext());
                lvMyDuo.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lvMyDuo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DuotorialActivity.class);
                String value[] = data.child(position + "").getValue().toString().split("=");
                String title = value[0].substring(1, value[0].length());
                String url = value[1].substring(0, value[1].length() - 1);
                intent.putExtra("TITLE", title);
                intent.putExtra("IMAGE", url);
                intent.putExtra("DESCRIPTION", "");
                getActivity().startActivity(intent);
            }
        });

    }
}
