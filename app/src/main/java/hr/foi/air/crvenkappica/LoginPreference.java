package hr.foi.air.crvenkappica;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by domagoj on 27.12.15..
 */
public class LoginPreference {
    private SharedPreferences sharedPreferences;
    private static final String loginPreference = "loginPreference";
    private SharedPreferences.Editor editor;
    private static final String loginString = "loginKey";
    private Context context;

    public LoginPreference(Context ctx){
        context = ctx;
        sharedPreferences = ctx.getSharedPreferences(loginPreference,Context.MODE_PRIVATE);
    }

    public void Login(){
        editor = sharedPreferences.edit();
        editor.putBoolean(loginString,true);
        editor.commit();
    }

    public void LogOut(){
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public boolean CheckLoggedIn(){
        return sharedPreferences.getBoolean(loginString,false);
    }

}
