package my.edu.tarc.armenu.Foodlist;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
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
import my.edu.tarc.armenu.Sorting.HighestPrice;
import my.edu.tarc.armenu.Sorting.LowestPrice;
import my.edu.tarc.armenu.Sorting.highestratings;

public class food extends AppCompatActivity implements  SearchView.OnQueryTextListener{

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static String URL_food = "https://limch-wa15.000webhostapp.com/food2.php";

    //a li st to store all the products
    ArrayList<Foodlist> food;

    //the recyclerview
    RecyclerView recyclerView;
    FoodAdapter adapter;
    MenuItem menuItem;
    SearchView searchView;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //initializing the productlist
        food = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();
    }

    private void loadProducts() {

        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_food,
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
                            adapter = new FoodAdapter(my.edu.tarc.armenu.Foodlist.food.this, food);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        menuItem = menu.findItem(R.id.action_search1);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();

        } else if (id== R.id.action_highprice){
            Intent i =new Intent(this,HighestPrice.class);
            this.startActivity(i);
        } else if (id == R.id.action_highrating)
        {
            Intent i =new Intent(this,highestratings.class);
            this.startActivity(i);
        } else if(id == R.id.action_lowprice){
            Intent i =new Intent(this,LowestPrice.class);
            this.startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

        @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
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
