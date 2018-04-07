package productions.ranuskin.meow.duotorial;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {

    private ListView lvCategories;


    public BrowseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvCategories = view.findViewById(R.id.lvCategories);
        lvCategories.setDivider(null);


        BrowseAdapter adapter = new BrowseAdapter(CategoryDataLibrary.getData(), getContext());
        lvCategories.setAdapter(adapter);
        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvCategories.setVisibility(View.GONE);
                TextView tvHeader = view.findViewById(R.id.tvCategoryTitle);
                TextView tvHeader2 = view.findViewById(R.id.tvCategoryTitle2);
                String target = tvHeader.getText().toString() + " "+ tvHeader2.getText().toString();
                CategoryDisplayFragment fragment = CategoryDisplayFragment.newInstance(target);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.category_container,fragment).commit();
            }
        });
    }

}

