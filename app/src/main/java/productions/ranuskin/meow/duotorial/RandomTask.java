package productions.ranuskin.meow.duotorial;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ran on 4/7/2018.
 */

public class RandomTask extends AsyncTask<Void ,Void,String> {

    private FirebaseUser user;

    private Context context;

    public RandomTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            return HttpIO.getJson("https://www.wikihow.com/api.php?action=app&format=json&num=100&subcmd=featured");
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
            Intent intent = new Intent(context,DuotorialActivity.class);
            String title = selectedRandom.getString("title");
            String getDescription = selectedRandom.getString("abstract");
            String imageURL = selectedRandom.getJSONObject("image").getString("url");
            intent.putExtra("TITLE",title);
            intent.putExtra("DESCRIPTION", getDescription);
            intent.putExtra("IMAGE", imageURL);
            AddToDuotorialDatabase.addData(title,imageURL);
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
