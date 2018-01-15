package my.edu.tarc.armenu.Login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import my.edu.tarc.armenu.MainActivity;
import my.edu.tarc.armenu.R;
import my.edu.tarc.armenu.Register.SharePreferenceManager;
import my.edu.tarc.armenu.Register.SignUp;

public class loginFunction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharePreferenceManager.getmInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,MainActivity.class));
            return;
        }

        final EditText p_email = (EditText) findViewById(R.id.username);
        final EditText p_password = (EditText) findViewById(R.id.password);
        final Button LoginButton = (Button) findViewById(R.id.loginbtn);
        final Button Signupbtn = (Button) findViewById(R.id.Signup);

        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(loginFunction.this, SignUp.class);
                loginFunction.this.startActivity(registerIntent);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = p_email.getText().toString().trim();
                final String password = p_password.getText().toString().trim();
                if (isInternetConnected() == true) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    SharePreferenceManager.getmInstance(getApplicationContext())
                                            .userLogin(
                                                    jsonResponse.getString("email"),
                                                    jsonResponse.getInt("user_id")

                                            );
                                    Integer id = jsonResponse.getInt("user_id");
                                    String name = jsonResponse.getString("name");



                                    Intent intent = new Intent(loginFunction.this, MainActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("user_id",id);
                                    intent.putExtra("email", username);

                                    startActivity(intent);
                                    finish();
                                } else {
                                    ;
                                    Toast.makeText(loginFunction.this, "Email Or Password is incorrect", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginProcess loginProcess = new LoginProcess(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(loginFunction.this);
                    queue.add(loginProcess);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Could Not Connect to Server",Toast.LENGTH_LONG).show();
            }

            }
        });
    }
    public boolean isInternetConnected(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
            connected = true;
        }
        else
            connected = false;

        return connected;

    }
}
