package hr.foi.air.crvenkappica.login;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by domagoj on 27.12.15..
 */

//Klasa za rad za SharedPreferences-ima, omogućuje korisniku da ostane prijavljen u aplikaciju.

public class LoginPreference {
    private SharedPreferences sharedPreferences;
    private static final String loginPreference = "loginPreference";
    private SharedPreferences.Editor editor;
    private static final String loginString = "loginKey";
    private Context context;

    //Konstruktor, zahtijeva kontekst aplikacije kao argument
    public LoginPreference(Context ctx){
        context = ctx;
        sharedPreferences = ctx.getSharedPreferences(loginPreference,Context.MODE_PRIVATE);
    }

    //Metoda koja sprema u SharedPreference oznaku da se korisnik prijavio.
    public void Login(String UserName,String userId){
        editor = sharedPreferences.edit();
        editor.putString("username",UserName);
        editor.putString("userid",userId);
        editor.putBoolean(loginString, true);
        editor.commit();
    }

    //Pri odjavi iz aplikacije brisu se podaci iz preferences-a.
    public void LogOut(){
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /*
    Provjera je li korisnik već prijavljen u aplikaciju.
    Vraća true - ukoliko postoji loginString, inače false.
     */
    public boolean CheckLoggedIn(){
        return sharedPreferences.getBoolean(loginString,false);
    }

    public String GetUsername(){
        return sharedPreferences.getString("username","");
    }

    public String GetUserId(){
        return  sharedPreferences.getString("userid","");
    }
}
