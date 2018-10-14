package productions.ranuskin.meow.duotorial;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ran on 4/7/2018.
 */

public class RandomTask extends AsyncTask<Void, Void, String> {

    private FirebaseUser user;

    private Context context;

    public RandomTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            if (HttpIO.isOnline(context)) {
                return HttpIO.getJson("https://www.wikihow.com/api.php?action=app&format=json&num=100&subcmd=featured");
            }
            else {
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                builder.setTitle("No internet Connection");
                builder.setMessage("Please turn on internet connection to continue");
                builder.setPositiveButton("connected", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doInBackground();
                    }
                });
            }
        } catch (IOException e) {
            Toast.makeText(context, "error! no connection", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONArray root = new JSONObject(s).getJSONObject("app").getJSONArray("articles");
            int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
            JSONObject selectedRandom = root.getJSONObject(randomNum);
            Intent intent = new Intent(context, DuotorialActivity.class);
            String title = selectedRandom.getString("title");
            String getDescription = selectedRandom.getString("abstract");
            String imageURL = selectedRandom.getJSONObject("image").getString("url");
            intent.putExtra("TITLE", title);
            intent.putExtra("DESCRIPTION", getDescription);
            intent.putExtra("IMAGE", imageURL);
            AddToDuotorialDatabase.addData(title, imageURL);
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
