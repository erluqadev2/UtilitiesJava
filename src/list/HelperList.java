/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Editorial_03
 */
public class HelperList {
    
    public static List<String> clearRepeat(List<String> list) throws Exception{
        List<String> copyList = new ArrayList<>(list);
        System.out.println(list.size());
        System.out.println(copyList.size());
        Collections.copy(copyList, list);
        List<String> newList = new ArrayList<>();
        while(copyList.size() > 0){
            newList.add(copyList.get(0));
            if(!deleteList(copyList, copyList.get(0))){
                throw new Exception("Error delete item list");
            }
        }
        return newList;
    }
    
    public static List<String> getRepeatingList(List<String> list) throws Exception{
        List<String> copyList = new ArrayList<>(list);
        Collections.copy(copyList, list);
        List<String> newList = new ArrayList<>();
        while(copyList.size() > 0){
            if(countRepeatList(copyList, copyList.get(0)) > 1){
                newList.add(copyList.get(0));
            }
            if(!deleteList(copyList, copyList.get(0))){
                throw new Exception("Error delete item list");
            }
        }
        return newList;
    }
    
    public static boolean deleteList(List<String> list, String search){
        int i = 0;
        while(list.size() > 0 && i < list.size()){
            String str = list.get(i);
            if(str.equals(search)){
                if(!list.remove(str)){
                    return false;
                }
            }
            else{
                i++;
            }
        }
        return true;
    }
    
    public static int countRepeatList(List<String> list, String search){
        int count = 0;
//        for(String str : list){
//            if(str.equals(search)){
//                count++;
//            }
//        }
        count = list.stream().filter((str) -> (str.equals(search))).map((_item) -> 1).reduce(count, Integer::sum);
        return count;
    }
}
