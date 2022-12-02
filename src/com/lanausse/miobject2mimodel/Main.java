package com.lanausse.miobject2mimodel;
/**
 * This program will try its best to convert a Mine-imator Object (miobject) to a Mine-imator Model (mimodel)
 * Version 2: Deprecates "setTexture()",ect. in the Part and Object Class and replaces it with "setValue()"
 *
 * @author Lanausse
 * @version 0.2
 * @since December 2, 2022
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2){
            //Help 'Docs'
            System.out.println("Example: miobject2mimodel.jar object.miobject output.mimodel");
        }else {
            System.out.println("Input: " + args[0]);
            System.out.println("Output: "+ args[1]);

            Model convertedModel = convert(args[0]);
            System.out.println(convertedModel);
            saveToFile(args[1], convertedModel);
        }
    }

    public static void saveToFile(String name, Model model){
        try {
            //check if a file already exist with that name
            File mimodel = new File(name);
            if (mimodel.createNewFile()) {
                System.out.println("File created: " + mimodel.getName());
            } else {
                System.out.println("File already exists. Overwrite it? (y/N)");
            }

            //Write to file
            FileWriter writer = new FileWriter(mimodel);
            writer.write(model.toString());
            writer.close();

            System.out.println("Saved to " + mimodel.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Model convert(String filepath) throws FileNotFoundException {
        Model convertedModel = new Model();
        FileReader miobject = new FileReader(filepath);

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(miobject);
            JSONObject jsonObject = (JSONObject)obj;

            String format = jsonObject.get("format").toString();
            String createdIn = jsonObject.get("created_in").toString();
            JSONArray timelines = (JSONArray)jsonObject.get("timelines");

            System.out.println("Format: " + format);
            System.out.println("Course: " + createdIn);
            System.out.println("Timeline:");

            //CREATE PARTS & SHAPES
            Iterator iterator = timelines.iterator();
            int counter = 0; //To make sure there are no dupe part names
            while (iterator.hasNext()) {
                JSONObject object = (JSONObject)iterator.next();
                Part newPart = new Part(object.get("name").toString() + counter, object.get("id").toString());
                counter++;

                //Set Properties
                //TODO: THERE HAS TO BE A BETTER WAY TO DO THIS THAT I HAVEN'T REALISED YET
                JSONObject firstKeyframe = (JSONObject)parser.parse(((JSONObject)parser.parse(object.get("keyframes").toString())).get("0").toString());
                System.out.println(firstKeyframe);

                    //Position
                double POS_X = 0;
                double POS_Y = 0;
                double POS_Z = 0;

                //THANK YOU TO https://stackoverflow.com/questions/21690413/decoding-floating-point-number-in-simple-json-java
                //BEEN TRYING TO FIX THIS FOR AT LEAST AN HOUR
                if (firstKeyframe.get("POS_X") != null)
                    POS_X = ((Number)firstKeyframe.get("POS_X")).doubleValue();
                if (firstKeyframe.get("POS_Y") != null)
                    POS_Y = ((Number)firstKeyframe.get("POS_Y")).doubleValue();
                if (firstKeyframe.get("POS_Z") != null)
                    POS_Z = ((Number)firstKeyframe.get("POS_Z")).doubleValue();

                newPart.setValue("Position", new double[]{POS_X, POS_Y, POS_Z});

                //Color
                if ( ((JSONObject)((JSONObject)object.get("keyframes")).get("0")).get("RGB_MUL").toString() != null)
                    newPart.setColorBlend( ((JSONObject)((JSONObject)object.get("keyframes")).get("0")).get("RGB_MUL").toString() );

                //objets
                if (object.get("type").toString().equals("cube") || object.get("type").toString().equals("sphere"))
                    newPart.addShape(new Shape());

                convertedModel.addPart(newPart); //Append to model
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return convertedModel;
    }
}
