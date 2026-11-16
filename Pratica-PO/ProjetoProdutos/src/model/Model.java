/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author laboratorio
 */
public class Model {
    public static ArrayList<Produto> lista = new ArrayList<>();
    
    public static HashMap<Integer, Produto> mapaPorId = new HashMap<>();
    public static HashMap<String, ArrayList<Produto>> mapaPorCategoria = new HashMap<>(); 
    
}
