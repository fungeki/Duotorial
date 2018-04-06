package productions.ranuskin.meow.duotorial;

/**
 * Created by Ran on 4/6/2018.
 */

public class DuotorialDialogPreview {

   private String boldPreview;
   private String imageURL;

   public DuotorialDialogPreview(String boldPreview, String imageURL) {
      this.boldPreview = boldPreview;
      this.imageURL = imageURL;
   }

   @Override
   public String toString() {
      return "DuotorialDialogPreview{" +
              "boldPreview='" + boldPreview + '\'' +
              ", imageURL='" + imageURL + '\'' +
              '}';
   }

   public String getBoldPreview() {
      return boldPreview;
   }

   public void setBoldPreview(String boldPreview) {
      this.boldPreview = boldPreview;
   }

   public String getImageURL() {
      return imageURL;
   }

   public void setImageURL(String imageURL) {
      this.imageURL = imageURL;
   }
}
