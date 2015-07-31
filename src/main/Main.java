/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controllers.PoemController;

/**
 *
 * @author ivanmoya
 */
public class Main {
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PoemController poemController = new PoemController();
        poemController.readLines("/Users/ivanmoya/Desktop/Rules.txt");
        System.out.print(PoemController.getPoem());
    }
    
}
