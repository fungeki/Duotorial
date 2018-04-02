package productions.ranuskin.meow.duotorial;

import java.util.ArrayList;

/**
 * Created by Ran on 4/2/2018.
 */

public class DuotorialStage {
    ArrayList<DuotorialStep> steps;

    public DuotorialStage(ArrayList<DuotorialStep> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "DuotorialStage{" +
                "steps=" + steps +
                '}';
    }

    public ArrayList<DuotorialStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<DuotorialStep> steps) {
        this.steps = steps;
    }
}
