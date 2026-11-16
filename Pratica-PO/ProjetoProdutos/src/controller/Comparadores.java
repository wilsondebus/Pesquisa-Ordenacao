/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.Comparator;
import model.Produto;

/**
 *
 * @author laboratorio
 */
public class Comparadores {
    
    public static final Comparator<Produto> POR_ID = (a, b) -> Integer.compare(a.getId(), b.getId());
    
    public static final Comparator<Produto> POR_NOME = (a, b) -> a.getNome().compareToIgnoreCase(b.getNome());
    
    public static final Comparator<Produto> POR_PRECO = (a, b) -> Double.compare(a.getPreco(), b.getPreco());
    
}
