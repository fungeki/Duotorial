package productions.ranuskin.meow.duotorial;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ran Loock on 5/21/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */
public class ClassroomFragment extends Fragment {

    public ClassroomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classroom, container, false);
    }


}
