package com.lanausse.miobject2mimodel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Shape {
    JSONObject shape = new JSONObject();

    public Shape(){
        //X, Y, Z
        double[] pivot = {-8, 0, -8};
        double[] position = {0, 0, 0};

        shape.put("type","block");
        shape.put("description","block"); //Name
        shape.put("from", ArrayToJSONArray(pivot)); //pivot point
        shape.put("to", ArrayToJSONArray(toArrayFromPivotAndPosition(pivot,position)));
        shape.put("uv", ArrayToJSONArray(new double[]{0, 0}));
    }
    
    public JSONObject JSON(){
        return shape;
    }

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
     * @param position position cords
     * @return the correct numbers for the "to" property
     */
    private static double[] toArrayFromPivotAndPosition(double[] pivot, double[] position){
        double[] newArray = new double[3];
        for (int i = 0; i < 3; i++) {
            newArray[i] = position[i] + 16 + pivot[i];
        }
        return newArray;
    }
}
