package productions.ranuskin.meow.duotorial;

import java.util.ArrayList;

/**
 * Created by Ran on 4/2/2018.
 */

public class
CategoryDataLibrary {

    public static ArrayList<DuoCategory> getData(){
        ArrayList<DuoCategory> data = new ArrayList<>();
        data.add(new DuoCategory("Computers","and Electronics",R.drawable.ic_computer));
        data.add(new DuoCategory("Arts","and Entertainment",R.drawable.ic_arts));
        data.add(new DuoCategory("Pets","and Animals",R.drawable.ic_pet_paw));
        data.add(new DuoCategory("Food","and Entertaining",R.drawable.ic_food));

        return data;
    }
}
