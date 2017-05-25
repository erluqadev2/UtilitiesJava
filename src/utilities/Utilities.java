/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import files.HelperFiles;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import list.HelperList;

/**
 *
 * @author Editorial_03
 */
public class Utilities {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
//        String path = "C:\\Users\\Editorial_03\\Desktop\\list_files.txt";
//        try {
//            List<String> list = HelperFiles.readFileInList(path);
//            System.out.println("numero de archivos : " + list.size());
//            List<String> listWithoutRepeating = HelperList.clearRepeat(list);
//            System.out.println("ahora hay : " + listWithoutRepeating.size());
//            String newPath = HelperFiles.newPathDirectory(path);
//            HelperFiles.writeListInFile(listWithoutRepeating, newPath);
//            List<String> listDeleting = HelperList.getRepeatingList(list);
//            System.out.println("count repeating : " + listDeleting.size());
//            for(String str : listDeleting){
//                System.out.println(str);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
//        }


        String path = "D:\\others";
        try {
            long size = HelperFiles.sizeFile(path);
            System.out.println(HelperFiles.sizeFileToString(size));
        } catch (Exception ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
