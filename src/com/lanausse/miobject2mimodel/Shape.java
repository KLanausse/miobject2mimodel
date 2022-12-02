package com.lanausse.miobject2mimodel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Shape {
    JSONObject shape = new JSONObject();

    //TODO: Try and clean up the overloaded constructors a bit
    public Shape(){
        this("block");
    }

    public Shape(String type){
        this(type,type);
    }

    public Shape(String type, String name){
        this(type,name, new double[]{-8, 0, -8});
    }

    public Shape(String type, String name,double[] pivot){
        //X, Y, Z
        double[] unknown = {0, 0, 0};

        shape.put("type",type);
        shape.put("description",name); //Name
        shape.put("from", ArrayToJSONArray(pivot)); //pivot point
        shape.put("to", ArrayToJSONArray(toArrayFromPivotAndUnknown(pivot,unknown)));
        shape.put("uv", ArrayToJSONArray(new double[]{0, 0}));
    }

    public Shape(String type, String name,double[] pivot,String hex){
        this(type,name,pivot);
        this.setColorBlend(hex);
    }

    public Shape(String type, String name,double[] pivot,String hex,double[] position){
        this(type,name,pivot);
        this.setColorBlend(hex);
        this.setPosition(position);
    }

    public Shape(String type, String name, double[] position,double[] scale){
        this(type,name);
        this.setPosition(position);
        this.setScale(scale);
    }

    public Shape(String type, String name, double[] position,double[] rotation,double[] scale){
        this(type,name);
        this.setPosition(position);
        this.setRotation(rotation);
        this.setScale(scale);
    }

    public Shape(String type, String name,double[] pivot,String hex,double[] position,double[] rotation){
        this(type,name,pivot);
        this.setColorBlend(hex);
        this.setPosition(position);
        this.setRotation(rotation);
    }

    public Shape(String type, String name,double[] pivot,String hex,double[] position,double[] rotation,double[] scale){
        this(type,name,pivot);
        this.setColorBlend(hex);
        this.setPosition(position);
        this.setRotation(rotation);
        this.setScale(scale);
    }

    //Setters
    public void setColorBlend(String hex){
        if (!shape.containsKey("color_blend"))
            shape.put("color_blend","");
        shape.replace("color_blend", hex);
    }

    public void setPosition(double[] position){
        if (!shape.containsKey("position"))
            shape.put("position","");
        shape.replace("position", ArrayToJSONArray(position));
    }

    public void setRotation(double[] rotation){
        if (!shape.containsKey("rotation"))
            shape.put("rotation","");
        shape.replace("rotation", ArrayToJSONArray(rotation));
    }

    public void setScale(double[] scale){
        if (!shape.containsKey("scale"))
            shape.put("scale","");
        shape.replace("scale", ArrayToJSONArray(scale));
    }

    //Getters

    public JSONObject JSON(){
        return shape;
    }

    //Functions
    private static JSONArray ArrayToJSONArray(double[] array){
        JSONArray newJSONArray = new JSONArray();
        for (double number:array) {
            newJSONArray.add(number);
        }
        return newJSONArray;
    }

    /**
     * Will take the pivot offset and the position cords and combine them into the correct numbers for the "to" property
     * @param pivot pivot offset
     * @param unknown position cords
     * @return the correct numbers for the "to" property
     */
    private static double[] toArrayFromPivotAndUnknown(double[] pivot, double[] unknown){
        double[] newArray = new double[3];
        for (int i = 0; i < 3; i++) {
            newArray[i] = unknown[i] + 16 + pivot[i];
        }
        return newArray;
    }
}
