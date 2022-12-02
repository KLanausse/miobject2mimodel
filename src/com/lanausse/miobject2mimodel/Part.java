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

    public Part(String name, String id){
        part.put("name",name);
        part.put("id",id);
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
    @Deprecated
    public void setColorBlend(String hex){
        if (!part.containsKey("color_blend"))
            part.put("color_blend","");
        part.replace("color_blend", hex);
    }

    @Deprecated
    public void setColorInherit(int bool){
        if (!part.containsKey("color_inherit"))
            part.put("color_inherit","");
        part.replace("color_inherit", bool);
    }

    @Deprecated
    public void setPosition(double[] position){
        if (!part.containsKey("position"))
            part.put("position","");
        part.replace("position", ArrayToJSONArray(position));
    }

    @Deprecated
    public void setRotation(double[] rotation){
        if (!part.containsKey("rotation"))
            part.put("rotation","");
        part.replace("rotation", ArrayToJSONArray(rotation));
    }

    @Deprecated
    public void setScale(double[] scale){
        if (!part.containsKey("scale"))
            part.put("scale","");
        part.replace("scale", ArrayToJSONArray(scale));
    }

    @Deprecated
    public void setTexture(String textureName){
        if (!part.containsKey("texture"))
            part.put("texture","");
        part.replace("texture", textureName);
    }

        //Set Value
    public void setValue(String name, String value){
        if (!part.containsKey(name))
            part.put(name,"");
        part.replace(name, value);
    }

    public void setValue(String name, int value){
        if (!part.containsKey(name))
            part.put(name,"");
        part.replace(name, value);
    }

    public void setValue(String name, double value){
        if (!part.containsKey(name))
            part.put(name,"");
        part.replace(name, value);
    }

    public void setValue(String name, String[] value){
        if (!part.containsKey(name))
            part.put(name,"");
        part.replace(name, ArrayToJSONArray(value));
    }

    public void setValue(String name, int[] value){
        if (!part.containsKey(name))
            part.put(name,"");
        part.replace(name, ArrayToJSONArray(value));
    }

    public void setValue(String name, double[] value){
        if (!part.containsKey(name))
            part.put(name,"");
        part.replace(name, ArrayToJSONArray(value));
    }



    //Functions
        //ArrayToJSONArray
    private static JSONArray ArrayToJSONArray(double[] array){
        JSONArray newJSONArray = new JSONArray();
        for (double number:array) {
            newJSONArray.add(number);
        }
        return newJSONArray;
    }

    private static JSONArray ArrayToJSONArray(int[] array){
        JSONArray newJSONArray = new JSONArray();
        for (int number:array) {
            newJSONArray.add(number);
        }
        return newJSONArray;
    }

    private static JSONArray ArrayToJSONArray(String[] array){
        JSONArray newJSONArray = new JSONArray();
        for (String str:array) {
            newJSONArray.add(str);
        }
        return newJSONArray;
    }
}
