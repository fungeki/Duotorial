package productions.ranuskin.meow.duotorial;

/**
 * Created by Ran on 4/7/2018.
 */

public class CategoryDispObj {
    String title;
    String imageURL;

    @Override
    public String toString() {
        return "CategoryDispObj{" +
                "title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public CategoryDispObj(String title, String imageURL) {
        this.title = title;
        this.imageURL = imageURL;
    }
}
