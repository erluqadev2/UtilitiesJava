/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
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
                    File newFile = new File(newName);
                    if (newFile.createNewFile()) {
                        Files.copy(Paths.get(path), new FileOutputStream(newFile));
                    }
                }
                if (i < mod) {
                    String newName = nameWithoutExt + "_" + j + ext;
                    new File(newName).createNewFile();
                }
            }
        }
    }
    
    public static List<String[]> readCSV(String path, String separator, boolean withHeader) throws Exception {
            try {
                List<String[]> lista = new ArrayList<>();
                List<String> listRows = readFileInList(path);
                if (listRows == null || listRows.isEmpty()) {
                    throw new Exception("No se encontraron datos en el archivo pasado");
                }
                for (int i = (withHeader ? 1 : 0); i < listRows.size(); i++) {
                    String row = listRows.get(i);
                    String[] split = row.split("\\;");
                    lista.add(split);
                }
                return lista;
            } catch (Exception e) {
                throw e;
            }
    }
    
    public static String getExtensionFile(File f) throws Exception {
        if (f == null) {
            throw new Exception("File is null");
        }
        if (!f.isFile()) {
            throw new Exception("Not is File");
        }
        String name = f.getName();
        int lastPosPoint = name.lastIndexOf(".");
        return name.substring(lastPosPoint, name.length());
    }
    
    public static void copyFile(File source, File dest) throws IOException {
        FileChannel in = (new FileInputStream(source)).getChannel();
        FileChannel out = (new FileOutputStream(dest)).getChannel();
        in.transferTo(0, source.length(), out);
        in.close();
        out.close();
    }
    
    public static void getAllFilesDirectory(File directory, List<File> files) {
        if (files == null) {
            files = new ArrayList<>();
        }
        File[] filesDir = directory.listFiles();
        for (File f : filesDir) {
            if (f.isFile()) {
                files.add(f);
            } else {
                getAllFilesDirectory(f, files);
            }
        }
    }
    
    public static void getAllFilesDirectory(String pathIn, String pathOut, String filterExt) throws Exception {
        File directotyIn = new File(pathIn);
        if (!directotyIn.isDirectory()) {
            throw new Exception("Path IN is not Directory");
        }
        if (!new File(pathOut).isDirectory()) {
            throw new Exception("Path OUT is not Directory");
        }
        List<File> listFiles = new ArrayList<>();
        //Recorremos todos los archivos del directory de entrada
        getAllFilesDirectory(directotyIn, listFiles);
        
        for (File f : listFiles) {
            if (filterExt != null && !filterExt.equals("")) {
                String ext = getExtensionFile(f);
                if (!filterExt.contains(ext)) {
                    continue;
                }
            }
            System.out.println(f.getAbsolutePath());
            String pathNewFile = pathOut + File.separator + f.getName();
            File newFile = new File(pathNewFile);
            copyFile(f, newFile);
        }
    }
    
    public static void main(String[] args) {
//        try {
//            List<String[]> list = HelperFiles.readCSV("C:\\Users\\PERTISAN\\Desktop\\escuadron\\ubigeo_peru.csv", ";", true);
//            List<String> listScript = new ArrayList<>();
//            String comment = "-- #### SCRIPT DE INSERCION DE UBIGEO(DEPARTAMENTOS, PROVINCIA Y DISTRITOS) DEL PERÚ ####";
//            listScript.add(comment);
//            String ubigeoDpto = "";
//            String ubigeoPrv = "";
//            for (String[] array : list) {
//                String departamento = array[0].trim();
//                String provincia = array[1].trim();
//                String distrito = array[2].trim();
//                String ubigeo = array[3].trim();
//                if (provincia.equals("") && distrito.equals("")) {
//                    listScript.add("");
//                    listScript.add("");
//                    listScript.add("-- ==============================");
//                    comment = "-- #### INSERT DEPARTAMENTO ####";
//                    System.out.println(comment);
//                    listScript.add(comment);
//                    listScript.add("-- ==============================");
//                    String abrDpto = "";
//                    String[] split = array[0].split("\\s");
//                    String nombreDpto = "";
//                    if (split.length > 1) {
//                        for (int i = 1; i < split.length; i++) {
//                            nombreDpto += split[i] + " ";
//                        }
//                         nombreDpto = nombreDpto.trim();
//                    }
//                    ubigeoDpto = ubigeo;
//                    String insertDpto = "INSERT INTO departamento(dep_codubigeo, dep_abreviatura, dep_nombre)"
//                            + " VALUES('" + ubigeo + "', '" + abrDpto + "', '" + nombreDpto + "');";
//                    listScript.add(insertDpto);
//                    System.out.println(insertDpto);
//                } else if (!provincia.equals("") && distrito.equals("")) {
//                    listScript.add("");
//                    comment = "-- #### INSERT PROVINCIA ####";
//                    System.out.println(comment);
//                    listScript.add(comment);
//                    String nomPrv = "";
//                    String split[] = provincia.split("\\s");
//                    if (split.length > 1) {
//                        for (int i = 1; i < split.length; i++) {
//                            nomPrv += split[i] + " ";
//                        }
//                        nomPrv = nomPrv.trim();
//                    }
//                    ubigeoPrv = ubigeo;
//                    String insertPrv = "INSERT INTO provincia(pro_codubigeo, pro_nombre, dep_codubigeo)"
//                            + " VALUES('" + ubigeo + "', '" + nomPrv + "', '" + ubigeoDpto + "');";
//                    listScript.add(insertPrv);
//                    listScript.add("");
//                    System.out.println(insertPrv);
//                } else if (!distrito.equals("")) {
//                    comment = "-- #### INSERT DISTRITO ####";
//                    System.out.println(comment);
//                    listScript.add(comment);
//                    String nomDtr = "";
//                    String[] split = distrito.split("\\s");
//                    if (split.length > 1) {
//                        for (int i = 1; i < split.length; i++) {
//                            nomDtr += split[i] + " ";
//                        }
//                        nomDtr = nomDtr.trim();
//                    }
//                    String insertDtr = "INSERT INTO distrito(dis_codubigeo, dis_nombre, pro_codubigeo)"
//                            + " VALUES('" + ubigeo + "', '" + nomDtr + "', '" + ubigeoPrv + "');";
//                    listScript.add(insertDtr);
//                    System.out.println(insertDtr);
//                }
//            }
//            String pathScript = "C:\\Users\\PERTISAN\\Desktop\\escuadron\\implementacion\\backend\\BD_PNP\\inserts\\insert_ubigeo.sql";
//            HelperFiles.writeListInFile(listScript, pathScript);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        String pathDirIn = "D:\\Ericson\\Proyectos\\VEP\\Desarrollo\\fuentes_harvest\\07-11-2017\\FUENTES_APP\\AppRetail\\retail_pos";
        String pathDirOut = "D:\\Ericson\\Proyectos\\VEP\\DIFF_APP_RETAIL_DEV_TEST[15-11-2017]\\DEV";
        try {
            getAllFilesDirectory(pathDirIn, pathDirOut, ".java|.jsp|.xml");
            System.out.println("FIN.....");
        } catch (Exception ex) {
            Logger.getLogger(HelperFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
            
}
