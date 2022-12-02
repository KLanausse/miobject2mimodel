package com.lanausse.miobject2mimodel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Part {
    JSONObject part = new JSONObject();
    JSONArray shapes = new JSONArray();
    JSONArray parts = new JSONArray();

    public Part(){
        this("New part");
    }

    public Part(String name){
        part.put("name",name);
        part.put("position",ArrayToJSONArray(new double[]{0, 0, 0}));
        part.put("shapes", shapes);
        part.put("parts", parts);
    }

    //Getters
    public JSONObject JSON(){
        return part;
    }

    //Setters
    public void addShape(Shape shape){
        shapes.add(shape.JSON());
    }

    public void addPart(Part part){
        parts.add(part.JSON());
    }

    //Setters
    public void setColorBlend(String hex){
        if (!part.containsKey("color_blend"))
            part.put("color_blend","");
        part.replace("color_blend", hex);
    }

    public void setColorInherit(int bool){
        if (!part.containsKey("color_inherit"))
            part.put("color_inherit","");
        part.replace("color_inherit", bool);
    }

    public void setPosition(double[] position){
        if (!part.containsKey("position"))
            part.put("position","");
        part.replace("position", ArrayToJSONArray(position));
    }

    public void setRotation(double[] rotation){
        if (!part.containsKey("rotation"))
            part.put("rotation","");
        part.replace("rotation", ArrayToJSONArray(rotation));
    }

    public void setScale(double[] scale){
        if (!part.containsKey("scale"))
            part.put("scale","");
        part.replace("scale", ArrayToJSONArray(scale));
    }

    public void setTexture(String textureName){
        if (!part.containsKey("texture"))
            part.put("texture","");
        part.replace("texture", textureName);
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
