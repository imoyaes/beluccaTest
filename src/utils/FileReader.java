/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivanmoya
 */
public class FileReader {
    
    public static ArrayList<String> readFile(String path){
        Path filePath = Paths.get(path);
        try {
            return (ArrayList<String>) Files.readAllLines(filePath);
        } catch (IOException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
