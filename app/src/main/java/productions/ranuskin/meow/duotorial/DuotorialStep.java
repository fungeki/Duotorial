package productions.ranuskin.meow.duotorial;

/**
 * Created by Ran on 4/2/2018.
 */

public class DuotorialStep {
    int number;
    String summary;
    String description;
    String imageURL;

    public DuotorialStep(int number, String summary, String description, String imageURL) {
        this.number = number;
        this.summary = summary;
        this.description = description;
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "DuotorialStep{" +
                "number=" + number +
                ", summary='" + summary + '\'' +
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
