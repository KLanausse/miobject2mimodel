package com.lanausse.miobject2mimodel;
/**
 * This program will try its best to convert a Mine-imator Object (miobject) to a Mine-imator Model (mimodel)
 *
 * @author Lanausse
 * @version 0.1
 * @since December 1, 2022
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2){
            //Help 'Docs'
            System.out.println("Example: miobject2mimodel.jar object.miobject output.mimodel");
        }else {
            System.out.println("Input: " + args[0]);
            System.out.println("Output: "+ args[1]);

            Model newModel = new Model();
            Part newPart   = new Part();
            Shape cube     = new Shape();

            newPart.addShape(cube);
            newModel.addPart(newPart);

            System.out.println(newModel);
            saveToFile(args[1], newModel);
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
}
