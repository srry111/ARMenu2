package my.edu.tarc.armenu.Register;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SecretofAC on 12/16/2017.
 */

public class SharePreferenceManager {
    private static SharePreferenceManager mInstance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USER_ID = "user_id";

    private SharePreferenceManager(Context context){
        ctx = context;
    }

    public static synchronized SharePreferenceManager getmInstance(Context context){
        if(mInstance == null){
            mInstance = new SharePreferenceManager(context);
        }
        return mInstance;

    }

    public boolean userLogin(String email,int userid){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(KEY_EMAIL,email);
        editor.putInt(KEY_USER_ID, userid);


        editor.apply();
        return true;
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_EMAIL,null) != null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public Integer getUserID(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID,0);
    }


}
