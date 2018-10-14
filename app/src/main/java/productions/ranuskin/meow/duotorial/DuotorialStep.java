package productions.ranuskin.meow.duotorial;

/**
 * Created by Ran Loock on 4/2/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */

public class DuotorialStep {
    int number;
    String title;
    String description;
    String imageURL;

    public DuotorialStep(int number, String title, String description, String imageURL) {
        this.number = number;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "DuotorialStep{" +
                "number=" + number +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
