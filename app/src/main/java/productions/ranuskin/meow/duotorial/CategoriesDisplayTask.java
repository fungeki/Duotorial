package productions.ranuskin.meow.duotorial;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ran on 4/7/2018.
 */

public class CategoriesDisplayTask extends AsyncTask<String,Void,Document> {

        Context context;
        ListView lvCategoryDisplay;
        ProgressBar pbCategoryLoad;

        public CategoriesDisplayTask(Context context, ListView lvCategoryDisplay, ProgressBar pbCategoryLoad) {
            this.context = context;
            this.lvCategoryDisplay = lvCategoryDisplay;
            this.pbCategoryLoad = pbCategoryLoad;
        }

        @Override
        protected Document doInBackground(String... strings) {
            try {

                return Jsoup.connect("https://www.wikihow.com/Category:" + strings[0]).timeout(6000).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            final ArrayList <CategoryDispObj> item = new ArrayList<>();
            final Elements body = document.select("div#bodycontents");
            int counter = 0;
            //go one by one
            for (Element element : body.select(".thumbnail")) {

                String imageURL=element.select("img").first().attr("data-src");
                String title = element.select(".text").first().text();

                if (counter<100){
                item.add(new CategoryDispObj(title,imageURL));}
                counter++;
            }

            pbCategoryLoad.setVisibility(View.GONE);
            lvCategoryDisplay.animate().alpha(1f).setDuration(300);
            CategoriesAdapter adapter = new CategoriesAdapter(item,context);
            lvCategoryDisplay.setAdapter(adapter);
            lvCategoryDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String getImage = body.select("div#bodycontents").select(".thumbnail").get(position)
                            .select("img").first().attr("data-src");
                    String getTitle = body.select("div#bodycontents").select(".thumbnail").get(position)
                            .select(".text span").first().text();


                    Intent intent = new Intent(context,DuotorialActivity.class);
                    intent.putExtra("TITLE",getTitle);
                    intent.putExtra("DESCRIPTION", "");
                    intent.putExtra("IMAGE", getImage);
                    context.startActivity(intent);
                }
            });

        }
    }
