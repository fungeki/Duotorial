package productions.ranuskin.meow.duotorial;

import java.util.ArrayList;

/**
 * Created by Ran on 4/2/2018.
 */

public class DuotorialObject {
    DuoIntro intro;
    ArrayList<DuotorialStage> stages;

    public DuotorialObject(DuoIntro intro, ArrayList<DuotorialStage> stages) {
        this.intro = intro;
        this.stages = stages;
    }

    @Override
    public String toString() {
        return "DuotorialObject{" +
                "intro=" + intro +
                ", stages=" + stages +
                '}';
    }

    public DuoIntro getIntro() {
        return intro;
    }

    public void setIntro(DuoIntro intro) {
        this.intro = intro;
    }

    public ArrayList<DuotorialStage> getStages() {
        return stages;
    }

    public void setStages(ArrayList<DuotorialStage> stages) {
        this.stages = stages;
    }
}
