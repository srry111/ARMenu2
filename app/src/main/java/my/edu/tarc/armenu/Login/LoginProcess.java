package my.edu.tarc.armenu.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SecretofAC on 12/12/2017.
 */

public class LoginProcess extends StringRequest{

        private static final String LOGIN_REQUEST_URL = "http://limch-wa15.000webhostapp.com/LoginDB.php";
        private Map<String, String> params;

        public LoginProcess(String username, String password, Response.Listener<String> listener){
            super(Method.POST, LOGIN_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("email", username);
            params.put("password", password);

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
}
