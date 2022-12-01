package com.lanausse.miobject2mimodel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Part {
    JSONObject part = new JSONObject();
    JSONArray shapes = new JSONArray();
    JSONArray parts = new JSONArray();

    public Part(){
        part.put("name","New part");
        part.put("position",ArrayToJSONArray(new double[]{0, 0, 0}));
        part.put("shapes", shapes);
        part.put("parts", parts);
    }

    public JSONObject JSON(){
        return part;
    }

    public void addShape(Shape shape){
        shapes.add(shape.JSON());
    }

    public void addPart(Part part){
        parts.add(part.JSON());
    }

    //Functions
    private static JSONArray ArrayToJSONArray(double[] array){
        JSONArray newJSONArray = new JSONArray();
        for (double number:array) {
            newJSONArray.add(number);
        }
        return newJSONArray;
    }
}
