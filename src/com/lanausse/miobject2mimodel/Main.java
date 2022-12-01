package com.lanausse.miobject2mimodel;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Main {

    public static void main(String @NotNull [] args) {
	// write your code here
        if (args.length != 2){
            //Help Docs
            System.out.println("Example: miobject2mimodel.jar object.miobject output.mimodel");
        }else {
            if (args[0].endsWith(".miobject")) {
                System.out.println("Input: " + args[0]);
                System.out.println("Output: "+args[1]);
            }
        }
    }
}
