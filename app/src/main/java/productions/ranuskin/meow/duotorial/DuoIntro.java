package productions.ranuskin.meow.duotorial;

import android.support.annotation.DrawableRes;

/**
 * Created by Ran on 3/31/2018.
 */

public class DuoIntro {
    String title;
    String intro;

    @DrawableRes
    int imageRes;

    public DuoIntro(String title, String intro, int imageRes) {
        this.title = title;
        this.intro = intro;
        this.imageRes = imageRes;
    }

    @Override
    public String toString() {
        return "DuoIntro{" +
                "title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", imageRes=" + imageRes +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
