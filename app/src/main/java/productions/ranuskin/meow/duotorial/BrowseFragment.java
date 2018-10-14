package productions.ranuskin.meow.duotorial;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


/**
 * Created by Ran Loock on 4/23/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */
public class BrowseFragment extends Fragment {

    private GridView gvFirstCategories;
    private boolean isHidden;


    public BrowseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isHidden", isHidden);
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
        gvFirstCategories = view.findViewById(R.id.gvFirstCategories);
        if (savedInstanceState != null) {
            isHidden = savedInstanceState.getBoolean("isHidden");
        }
        if (savedInstanceState == null || !isHidden) {//prevents list override bug
            isHidden = false;
            gvFirstCategories.setVisibility(View.VISIBLE);


            BrowseAdapter adapter = new BrowseAdapter(CategoryDataLibrary.getData(), getContext());
            gvFirstCategories.setAdapter(adapter);
            gvFirstCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    gvFirstCategories.setVisibility(View.GONE);
                    isHidden = true;
                    TextView tvHeader = view.findViewById(R.id.tvCategoryTitle);
                    TextView tvHeader2 = view.findViewById(R.id.tvCategoryTitle2);
                    String target = tvHeader.getText().toString() + " " + tvHeader2.getText().toString();
                    CategoryDisplayFragment fragment = CategoryDisplayFragment.newInstance(target);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.category_container, fragment).commit();
                }
            });

        } else {
            gvFirstCategories.setVisibility(View.GONE);
        }


    }

}

