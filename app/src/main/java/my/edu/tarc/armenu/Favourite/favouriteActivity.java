package my.edu.tarc.armenu.Favourite;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import my.edu.tarc.armenu.Adapter.FoodAdapter;
import my.edu.tarc.armenu.Model.Foodlist;
import my.edu.tarc.armenu.R;
import my.edu.tarc.armenu.Register.SharePreferenceManager;
import my.edu.tarc.armenu.Register.SignUp;

public class favouriteActivity extends AppCompatActivity  implements  SearchView.OnQueryTextListener{



    private int userid = SharePreferenceManager.getmInstance(my.edu.tarc.armenu.Favourite.favouriteActivity.this).getUserID();
    private static String URL_favor= "https://limch-wa15.000webhostapp.com/viewfav.php?userid_fv=";

    RecyclerView recyclerView;
    private FoodAdapter adapter;
    MenuItem menuItem;
    SearchView searchView;
    Toolbar tb;
    ArrayList<Foodlist> food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.favRecylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        food = new ArrayList<>();

        loadFavourite();

    }

    public void loadFavourite(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_favor + userid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject foods = array.getJSONObject(i);

                                //adding the product to product list
                                food.add(new Foodlist(
                                        foods.getInt("foodid"),
                                        foods.getString("name"),
                                        foods.getString("foodimage"),
                                        foods.getString("fooddesc"),
                                        foods.getDouble("price"),
                                        foods.getDouble("rating")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            adapter = new FoodAdapter(my.edu.tarc.armenu.Favourite.favouriteActivity.this, food);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        menuItem = menu.findItem(R.id.action_search1);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        MenuItem item = menu.findItem(R.id.action_highrating);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.action_highprice);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.action_lowprice);
        item3.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Foodlist> filtered = new ArrayList<>();
        for (Foodlist foods : food) {
            String name = foods.getName().toLowerCase();
            if (name.contains(newText)) {
                filtered.add(foods);
            }

        }
        adapter.setFilter(filtered);
        return true;
    }

}
