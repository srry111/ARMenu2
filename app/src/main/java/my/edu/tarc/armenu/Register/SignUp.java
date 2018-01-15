package my.edu.tarc.armenu.Register;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import my.edu.tarc.armenu.Login.loginFunction;
import my.edu.tarc.armenu.R;

public class SignUp extends AppCompatActivity {
    private EditText p_name;
    private EditText p_email;
    private EditText p_password;
    private String name;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        p_name = (EditText) findViewById(R.id.name);
        p_email = (EditText) findViewById(R.id.email);
        p_password = (EditText) findViewById(R.id.password);
        final Button rbutton = (Button) findViewById(R.id.Register);
        Button back = (Button) findViewById(R.id.Back);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),loginFunction.class));
            }
        });
        rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = p_name.getText().toString();
                username = p_email.getText().toString();
                password = p_password.getText().toString();

                register();
                if(isInternetConnected()== true) {

                    if(validate() == true) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success == true) {
                                        Intent intent = new Intent(SignUp.this, loginFunction.class);
                                        SignUp.this.startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(),"This Email is exist",Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                                        builder.setMessage("Register Failed")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };


                        RegisterProcess registerProcess = new RegisterProcess(name, username, password, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
                        queue.add(registerProcess);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Could Not Connect to Server",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void register(){
        intialize();

        if(!validate()){
            Toast.makeText(this,"Signup has Failed",Toast.LENGTH_LONG).show();

        }
        else{
            onSignupSuccess();
        }


    }
    public void onSignupSuccess(){
        //TODO Start show message


    }

    public boolean validate(){
        boolean valid = true;
        if(name.isEmpty()||name.length()>26){
            p_name.setError("Please Enter valid Name");
            valid = false;
        }
        if(username.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            p_email.setError("Please Enter valid Email Address");
            valid = false;
        }
        if(password.isEmpty()|| password.length()<8){
            p_password.setError("Password is invalid.(Password length atleast 8)");
            valid =  false;
        }
        return valid;
    }

    public void intialize(){
        name = p_name.getText().toString().trim();
        username = p_email.getText().toString().trim();
        password = p_password.getText().toString().trim();
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
