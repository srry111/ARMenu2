package my.edu.tarc.armenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import my.edu.tarc.armenu.Adapter.FoodAdapter;
import my.edu.tarc.armenu.Model.Foodlist;
import my.edu.tarc.armenu.Register.SharePreferenceManager;

public class addrating extends AppCompatActivity {
    private static String URL_rating= "https://limch-wa15.000webhostapp.com/rating.php";

    private RatingBar userBar;
    private TextView userRateView;
    private TextView userTitle;

    private int foodid;
    private int userid;
    private double rating;
    private int id;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrating);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = FoodAdapter.getFoodName();
        userTitle = (TextView)findViewById(R.id.textTitleView);
        userBar = (RatingBar)findViewById(R.id.userRating);
        userRateView = (TextView)findViewById(R.id.ratingTextView);
        final Button subBtn = (Button)findViewById(R.id.rateSubmitBtn);
        final Intent intent = getIntent();
        id = SharePreferenceManager.getmInstance(this).getUserID();
        userTitle.setText(title);

        userBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating = userBar.getRating();
                userRateView.setText("Rating: " + rating);
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid = id;
                rating = userBar.getRating();
                foodid = FoodAdapter.Foodid();


                final Response.Listener<String> responseListener =
                        new Response.Listener<String>(){
                            public void onResponse(String response){
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success) {
                                        Toast.makeText(getApplicationContext(),"Userid: " + userid + " Rating: " + rating + " Foodid: " + foodid,Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(addrating.this);
                                        builder.setMessage("Userid: " + userid + " Rating: " + rating + " Foodid: " + foodid)
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                    else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(addrating.this);
                                        builder.setMessage("Userid: " + userid + " Rating: " + rating + " Foodid: " + foodid)
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(addrating.this);
                        builder.setMessage("Thank You for Rating")
                                .setCancelable(false)
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                final Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                                ratingProcess ratingProcess2 = new ratingProcess(foodid, userid, rating, responseListener);
                                                RequestQueue queue = Volley.newRequestQueue(addrating.this);
                                                queue.add(ratingProcess2);
                                                finish();
                                                startActivity(intent);
                                    }
                                })
                                .create()
                                .show();

                        /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        ratingProcess ratingProcess2 = new ratingProcess(foodid, userid, rating, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(addrating.this);
                        queue.add(ratingProcess2);*/



            }
        });



    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();


        }
        return super.onOptionsItemSelected(item);
    }

}
