package my.edu.tarc.armenu.Foodlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import my.edu.tarc.armenu.Model.Foodlist;
import my.edu.tarc.armenu.R;

public class Fooddetails extends AppCompatActivity {
    ImageView imgv;
    TextView tv;
    RatingBar rb;
    ArrayList<Foodlist> food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);




    }
}
