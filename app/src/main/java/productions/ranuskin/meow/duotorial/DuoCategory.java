package productions.ranuskin.meow.duotorial;

import android.support.annotation.DrawableRes;

/**
 * Created by Ran on 4/2/2018.
 */

public class DuoCategory {
    private String headerFirst;
    private String headerSecond;

    @DrawableRes
    private int ImageRes;

    public DuoCategory(String headerFirst, String headerSecond, int imageRes) {
        this.headerFirst = headerFirst;
        this.headerSecond = headerSecond;
        ImageRes = imageRes;
    }

    @Override
    public String toString() {
        return "DuoCategory{" +
                "headerFirst='" + headerFirst + '\'' +
                ", headerSecond='" + headerSecond + '\'' +
                ", ImageRes=" + ImageRes +
                '}';
    }

    public String getHeaderFirst() {
        return headerFirst;
    }

    public void setHeaderFirst(String headerFirst) {
        this.headerFirst = headerFirst;
    }

    public String getHeaderSecond() {
        return headerSecond;
    }

    public void setHeaderSecond(String headerSecond) {
        this.headerSecond = headerSecond;
    }

    public int getImageRes() {
        return ImageRes;
    }

    public void setImageRes(int imageRes) {
        ImageRes = imageRes;
    }
}
