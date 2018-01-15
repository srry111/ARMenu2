package my.edu.tarc.armenu;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Justzng on 1/11/2018.
 */

public class ratingProcess extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://limch-wa15.000webhostapp.com/rating.php";
    private Map<String, String> params;

    public ratingProcess(int foodid, int userid, double rating, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("foodid_fk",foodid + "");
        params.put("userid_fk", userid + "");
        params.put("ratingvalue", rating + "");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
