package productions.ranuskin.meow.duotorial;

import java.util.ArrayList;

/**
 * Created by Ran on 4/2/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */

public class
CategoryDataLibrary {

    public static ArrayList<DuoCategory> getData() {
        ArrayList<DuoCategory> data = new ArrayList<>();

        data.add(new DuoCategory("Arts", "and Entertainment", R.drawable.ic_arts));
        data.add(new DuoCategory("Pets", "and Animals", R.drawable.ic_pet_paw));
        data.add(new DuoCategory("Sports", "and Fitness", R.drawable.ic_sports));
        data.add(new DuoCategory("Relationships", "", R.drawable.ic_relationship));
        data.add(new DuoCategory("Travel", "", R.drawable.ic_travel));
        data.add(new DuoCategory("Computers", "and Electronics", R.drawable.ic_computers_2));
        data.add(new DuoCategory("Health", "", R.drawable.ic_health));
        data.add(new DuoCategory("Youth", "", R.drawable.ic_youth));
        data.add(new DuoCategory("Hobbies", "and Crafts", R.drawable.ic_hobbies_and_crafts));
        data.add(new DuoCategory("Person Care", "and Style", R.drawable.ic_lifestyle));
        data.add(new DuoCategory("Cars", "& Other Vehicles", R.drawable.ic_cars_and_vehicles));
        data.add(new DuoCategory("Finance", "and Business", R.drawable.ic_finance));
        data.add(new DuoCategory("Work", "World", R.drawable.ic_work));
        data.add(new DuoCategory("Family", "Life", R.drawable.ic_family_life));
        data.add(new DuoCategory("Philosophy", "and Religion", R.drawable.ic_philosophy));
        data.add(new DuoCategory("Holidays", "and Traditions", R.drawable.ic_holidays_and_tradition));
        data.add(new DuoCategory("Home", "and Garden", R.drawable.ic_gardens));


        return data;
    }
}
