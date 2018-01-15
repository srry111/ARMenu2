package my.edu.tarc.armenu.Food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import my.edu.tarc.armenu.AR.ImageTargets.ImageTargets;
import my.edu.tarc.armenu.R;

public class CrabFood extends AppCompatActivity {


    private ImageButton Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crab_food);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Btn = (ImageButton)findViewById(R.id.crabBtn);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrabFood.this ,ImageTargets.class);
                startActivity(intent);
            }
        });


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();


        }
        return super.onOptionsItemSelected(item);
    }


    }
