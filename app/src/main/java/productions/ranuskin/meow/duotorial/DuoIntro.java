package productions.ranuskin.meow.duotorial;

/**
 * Created by Ran Loock on 3/31/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */
public class DuoIntro {
    String title;
    String intro;
    String imageURL;

    public DuoIntro(String title, String intro, String imageURL) {
        this.title = title;
        this.intro = intro;
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "DuoIntro{" +
                "title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", imageURL=" + imageURL +
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
