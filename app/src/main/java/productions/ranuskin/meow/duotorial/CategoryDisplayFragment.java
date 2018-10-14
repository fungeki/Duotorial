package productions.ranuskin.meow.duotorial;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;


/**
 * Created by Ran Loock on 4/23/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */
public class CategoryDisplayFragment extends Fragment {


    private ListView lvCategoryDisplayList;
    private FloatingActionButton fabRestart;
    private ProgressBar pbCategoryLoad;

    public CategoryDisplayFragment() {
        // Required empty public constructor
    }

    public static CategoryDisplayFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("Category_Name", title);
        CategoryDisplayFragment fragment = new CategoryDisplayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_display, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        pbCategoryLoad = view.findViewById(R.id.pbCategory);
        lvCategoryDisplayList = view.findViewById(R.id.lvCategoryDisplayList);
        String title = bundle.getString("Category_Name");

        fabRestart = view.findViewById(R.id.fabRestart);
        fabRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.category_container, new BrowseFragment()).commit();

            }
        });
        CategoriesDisplayTask task = new CategoriesDisplayTask(getContext(), lvCategoryDisplayList, pbCategoryLoad);
        task.execute(title);
    }
}
