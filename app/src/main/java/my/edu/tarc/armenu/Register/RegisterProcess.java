package my.edu.tarc.armenu.Register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterProcess extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://limch-wa15.000webhostapp.com/RegisterDB.php";
    private Map<String, String> params;

    public RegisterProcess(String name, String username, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("email", username);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
