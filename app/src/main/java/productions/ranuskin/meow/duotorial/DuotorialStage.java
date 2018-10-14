package productions.ranuskin.meow.duotorial;

import java.util.ArrayList;

/**
 * Created by Ran Loock on 4/2/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */

public class DuotorialStage {
    private ArrayList<DuotorialStep> steps;
    private String title;

    public DuotorialStage(ArrayList<DuotorialStep> steps, String title) {
        this.steps = steps;
        this.title = title;
    }

    public ArrayList<DuotorialStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<DuotorialStep> steps) {
        this.steps = steps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "DuotorialStage{" +
                "steps=" + steps +
                ", title='" + title + '\'' +
                '}';
    }
}
