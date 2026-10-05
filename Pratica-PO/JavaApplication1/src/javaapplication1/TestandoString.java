/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

/**
 *
 * @author laboratorio
 */
public class TestandoString {
    public static void main(String[] args) {
        String sFrase = "alexandre de oliveira zamberlam";
        StringBuffer sbfrase = new StringBuffer("alexandre de oliveira zamberlam");
        StringBuilder sbuilderFrase = new StringBuilder("alexandre de oliveira zamberlam");
        
        System.out.println(sFrase.contains("andre"));
        System.out.println(sFrase.indexOf("andre"));
    }
    
}
