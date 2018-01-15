package my.edu.tarc.armenu;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class favProcess extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://limch-wa15.000webhostapp.com/fav.php";
    private Map<String, String> params;

    public favProcess(int foodid, int userid, int fav, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("foodid_fv",foodid + "");
        params.put("userid_fv", userid + "");
        params.put("favor", fav + "");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
