/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Editorial_03
 */
public class HelperFiles {
    
    
    public static String readFile(String path) throws FileNotFoundException, IOException {
        FileReader f = new FileReader(path);
        BufferedReader b = new BufferedReader(f);
        String cadena = "";
        String strAux = null;
        while ((strAux = b.readLine()) != null) {
            System.out.println(strAux);
            cadena += strAux;
        }
        b.close();
        return cadena;
    }
    
    public static List<String> readFileInList(String path)throws FileNotFoundException, IOException{
        List<String> list = new ArrayList<>();
        FileReader f = new FileReader(path);
        BufferedReader b = new BufferedReader(f);
        String strAux = null;
        while ((strAux = b.readLine()) != null) {
            if(!strAux.trim().equals("")){
                list.add(strAux.trim());
            }
        }
        System.out.println("***** " + list.size() + "******");
        b.close();
        return list;
    }
    
    public static void writeListInFile(List<String> list, String path){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
           for(String str : list){
               pw.println(str);
           }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    public static String newPathDirectory(String path){
        int lastIndex = path.lastIndexOf("\\");
        String pathDirectory = path.substring(0, lastIndex);
        String name = new Date().getTime()+"";
        return pathDirectory+"\\"+name+".txt";
    }
    
    public static long sizeFile(String path) throws Exception{
        File file = new File(path);
        return sizeFile(file);
    }
    
    public static long sizeFile(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("File not exist");
        }
        if(file.isDirectory()){
            return sizeDirectory(file);
        }
        return file.length();
    }
    
    public static long sizeDirectory(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("Directory not exist");
        }
        if(file.isFile()){
            return file.length();
        }
        long size = 0;
        for(File f : file.listFiles()){
            if(f.isFile()){
                size += f.length();
            }
            else{
                size += sizeDirectory(f);
            }
        }
        return size;
    }
    
    public static String sizeFileToString(long size){
        return sizeConverter(size, 0);
    }
    
    private static String sizeConverter(long size, int elevation){
        long base = 1024;
        long pow = (long)Math.pow(base, elevation);
        System.out.println(pow);
        float result = size/pow;
        System.out.println(result);
        if(result < base){
            String strResult = "";
            switch(elevation){
                case 0:
                    strResult = result + " bytes";
                    break;
                case 1:
                    strResult = result + " Kb";
                    break;
                case 2:
                    strResult = result + " Mb";
                    break;
                default:
                    strResult = "Z Gb o más";
                    break;
            }
            return strResult;
        }
        return sizeConverter(size, ++elevation);
    }
    
    public static void replicateFiles(String path, int quantity) throws FileNotFoundException, Exception {
        if (path == null || path.equals("")) {
            throw new IllegalArgumentException("Path is null or Empty");
        }
        File directory = new File(path);
        if (!directory.exists()) {
            throw new FileNotFoundException("File path not exist");
        }
        if (!directory.isDirectory()) {
            throw new Exception("The path especified not is a directory");
        }
        replicateFiles(directory, quantity);
    }
    
    public static void replicateFiles(File directory, int quantity) throws IOException {
        File[] files = directory.listFiles();
        int size = 0;
        for (File f : files) {
            if (f.isFile()) {
                size++;
            }
        }
        quantity = quantity - size;
        if (size > quantity) {
            return;
        }
        int mod = quantity%size;
        int div = quantity/size;
        for (int i = 0;i < files.length; i++) {
            File f = files[i];
            if (f.isFile()) {
                String path = f.getAbsolutePath();
                String[] split = path.split("\\.");
                String ext = "";
                if (split.length > 1) {
                    ext = "."+split[1];
                }
                String nameWithoutExt = path.substring(0, path.indexOf(ext));
                int j = 1;
                for (; j <= div ; j++) {
                    String newName = nameWithoutExt + "_" + j + ext;
                    System.out.println(newName);
                    new File(newName).createNewFile();
                }
                if (i < mod) {
                    String newName = nameWithoutExt + "_" + j + ext;
                    new File(newName).createNewFile();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            HelperFiles.replicateFiles("D:\\por_firmar", 200);
        } catch (Exception ex) {
            Logger.getLogger(HelperFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
            
}