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
import java.lang.reflect.Array;
import java.util.ArrayList;
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
            JSONObject partsList = new JSONObject();
            ArrayList<Part> arr = new ArrayList<Part>(); //TODO: give var a better name

            Iterator iterator = timelines.iterator();
            int counter = 0; //To make sure there are no dupe part names
            while (iterator.hasNext()) {
                JSONObject object = (JSONObject)iterator.next();
                Part newPart = new Part(object.get("name").toString() + counter, object.get("id").toString());
                partsList.put(object.get("id").toString(), counter);
                arr.add(counter, newPart);
                counter++;

                //Set Properties
                //TODO: THERE HAS TO BE A BETTER WAY TO DO THIS THAT I HAVEN'T REALISED YET
                JSONObject firstKeyframe = (JSONObject)parser.parse(((JSONObject)parser.parse(object.get("keyframes").toString())).get("0").toString());
                System.out.println(firstKeyframe);

                //Parent?
                if ( object.get("parent") != null)
                    newPart.setValue("parent", object.get("parent").toString());

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

                newPart.setValue("position", new double[]{POS_X, POS_Y, POS_Z});

                    //Scale
                double SCA_X = 0;
                double SCA_Y = 0;
                double SCA_Z = 0;


                if (firstKeyframe.get("SCA_X") != null)
                    SCA_X = ((Number)firstKeyframe.get("SCA_X")).doubleValue();
                if (firstKeyframe.get("SCA_Y") != null)
                    SCA_Y = ((Number)firstKeyframe.get("SCA_Y")).doubleValue();
                if (firstKeyframe.get("SCA_Z") != null)
                    SCA_Z = ((Number)firstKeyframe.get("SCA_Z")).doubleValue();

                newPart.setValue("scale", new double[]{SCA_X, SCA_Y, SCA_Z});

                    //Rotation
                double ROT_X = 0;
                double ROT_Y = 0;
                double ROT_Z = 0;


                if (firstKeyframe.get("ROT_X") != null)
                    ROT_X = ((Number)firstKeyframe.get("ROT_X")).doubleValue();
                if (firstKeyframe.get("ROT_Y") != null)
                    ROT_Y = ((Number)firstKeyframe.get("ROT_Y")).doubleValue();
                if (firstKeyframe.get("ROT_Z") != null)
                    ROT_Z = ((Number)firstKeyframe.get("ROT_Z")).doubleValue();

                newPart.setValue("rotation", new double[]{ROT_X, ROT_Y, ROT_Z});

                    //Color
                if ( firstKeyframe.get("RGB_MUL") != null)
                    newPart.setValue("color_blend", ((JSONObject)((JSONObject)object.get("keyframes")).get("0")).get("RGB_MUL").toString());


                //Append shape to part
                if (object.get("type").toString().equals("cube") || object.get("type").toString().equals("sphere") || object.get("type").toString().equals("cone") || object.get("type").toString().equals("cylinder"))
                    newPart.addShape(new Shape());

                convertedModel.addPart(newPart); //Append to model
            }

            System.out.println();

            //SET THE CORRECT PARENT
            //TODO: find a better way to parent things
            for (Part part: arr ) {
                if (((Number)partsList.get(part.getValue("parent"))) != null)
                    arr.get( ((Number)partsList.get(part.getValue("parent"))).intValue() ).addPart(part);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return convertedModel;
    }
}
