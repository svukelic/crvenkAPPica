package hr.foi.air.crvenkappica;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Mario on 5.12.2015..
 */
//Klasa za kreiranje JSON objekta iz registracijskih podataka i podataka za spremanje slike u bazu.
public class JSONParser {
    private String JSON_object;
    public JSONParser(Registration_Data r){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        JSON_object = "?json="+ gson.toJson(r) ;
    }
    public JSONParser(ImageItem i){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        JSON_object = "?json="+ gson.toJson(i) ;
    }
    public String getString(){
        return JSON_object;
    }
}
