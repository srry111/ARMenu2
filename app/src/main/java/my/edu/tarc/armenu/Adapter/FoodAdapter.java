package my.edu.tarc.armenu.Adapter;

/**
 * Created by you on 31/12/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import my.edu.tarc.armenu.AR.ImageTargets.ImageTargets;
import my.edu.tarc.armenu.MainActivity;
import my.edu.tarc.armenu.Model.Foodlist;
import my.edu.tarc.armenu.Interface.Itemclick;
import my.edu.tarc.armenu.R;
import my.edu.tarc.armenu.Register.SharePreferenceManager;
import my.edu.tarc.armenu.addrating;
import my.edu.tarc.armenu.favProcess;
import my.edu.tarc.armenu.ratingProcess;

/**
 * Created by Belal on 10/18/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ProductViewHolder> {



    private Context Ctx;
    private static int food_id;
    private static String title;
    private int userid;
    private int isfav;
    Dialog d;
    private ArrayList<Foodlist> food;
    public FoodAdapter(Context mCtx, ArrayList<Foodlist> food) {
        this.Ctx = mCtx;
        this.food = food;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Ctx);
        View view = inflater.inflate(R.layout.food_list, null);
        ProductViewHolder productViewHolder = new ProductViewHolder(view, Ctx ,food);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final Foodlist foo = food.get(position);

        //loading the image
        Glide.with(Ctx)
                .load(foo.getFoodimage())
                .into(holder.imageView);


        holder.textViewTitle.setText(foo.getName());
        holder.textViewRating.setText(String.valueOf(foo.getRating()));
        holder.textViewPrice.setText("Rm "+String.valueOf(foo.getPrice()));

        holder.setItemClickListener(new Itemclick() {
            @Override
            public void onItemClick(View v, int pos) {
                d= new Dialog(Ctx);

                d.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);

                d.setContentView(R.layout.activity_main3);

                TextView name = (TextView) d.findViewById(R.id.textView234);
                name.setText(foo.getName());
                ImageView imgv = (ImageView) d.findViewById(R.id.img123);
                imgv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                Glide.with(Ctx).load(foo.getFoodimage()).into(imgv);
                ImageButton btn = (ImageButton) d.findViewById(R.id.imgb2);
                RatingBar rb = (RatingBar) d.findViewById(R.id.ratingBar);
                ImageButton btn2 = (ImageButton)d.findViewById(R.id.addRateBtn);
                final ToggleButton favBtn2 = (ToggleButton) d.findViewById(R.id.favBtn2);
                rb.setRating((float) foo.getRating());
                food_id = foo.getFoodid();
                title = foo.getName();
                TextView tv7 = (TextView) d.findViewById(R.id.textView7);
                tv7.setText(foo.getFooddesc());
                int id = SharePreferenceManager.getmInstance(Ctx.getApplicationContext()).getUserID();
                userid = id;
                //todo link to Ar
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Ctx.getApplicationContext(), ImageTargets.class);
                        Ctx.startActivity(intent);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Ctx.getApplicationContext(), addrating.class);
                        Ctx.startActivity(intent);


                    }
                });
                favBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonRespond = new JSONObject(response);
                                    boolean success =  jsonRespond.getBoolean("success");
                                    if(success){
                                        Toast.makeText(Ctx.getApplicationContext(),"Added To Favourite",Toast.LENGTH_LONG).show();
                                        favBtn2.setBackgroundDrawable(ContextCompat.getDrawable(Ctx.getApplicationContext(),R.drawable.ic_favorite_black_24dp));
                                        isfav = 1;

                                    }else{
                                        Toast.makeText(Ctx.getApplicationContext(),"Remove from Favourite",Toast.LENGTH_LONG).show();
                                        favBtn2.setBackgroundDrawable(ContextCompat.getDrawable(Ctx.getApplicationContext(),R.drawable.ic_favorite_border_black_24dp));

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        };
                        favProcess favProcess2 = new favProcess(food_id, userid, isfav, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(Ctx.getApplicationContext());
                        queue.add(favProcess2);
                    }
                });


                d.show();


            }
        });




    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;
        RatingBar ratingbar;
        Itemclick itemclick;
        ToggleButton toggleButton;


        ArrayList<Foodlist> products = new ArrayList<Foodlist>();
        Context Ctx;


        public ProductViewHolder(View itemView, final Context Ctx, ArrayList <Foodlist> food) {
            super(itemView);

            this.Ctx = Ctx;
            this.products = food;
            itemView.setOnClickListener(this);
            ratingbar = itemView.findViewById(R.id.ratingBar);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            toggleButton = itemView.findViewById(R.id.favBtn2);


        }

        @Override
        public void onClick(View v) {
            this.itemclick.onItemClick(v,getLayoutPosition());
        }
        public void setItemClickListener(Itemclick ic){
            this.itemclick = ic;
        }
    }



    public void setFilter(ArrayList<Foodlist> foods){
        food = new ArrayList<>();
        food.addAll(foods);
        notifyDataSetChanged();

    }

    public static Integer Foodid(){
        int foodid = food_id;
        return  foodid;
    }
    public static String getFoodName(){
        String name = title;
        return name;
    }
}
