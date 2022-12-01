package com.lanausse.miobject2mimodel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Model {
    JSONObject model = new JSONObject();
    JSONArray parts = new JSONArray();

    public Model(){
        model.put("name", "new model");
        model.put("texture", "Default texture.png");
        JSONArray texture_size = new JSONArray();
        texture_size.add(16);
        texture_size.add(16);
        model.put("texture_size", texture_size);
        model.put("parts", parts);
    }

    public JSONObject JSON(){
        return model;
    }

    public void addPart(Part part){
        parts.add(part.JSON());
    }


    @Override
    public String toString(){
        return model.toString();
    }
}
