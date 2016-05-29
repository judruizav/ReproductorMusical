/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Julian
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Integer> array= new ArrayList<Integer>();
        array.add(12);
        array.add(14);
        array.add(2);
        array.add(15);
        array.add(11);
        //for(int i=0; i<array.size(); i++){
            //System.out.println(array.get(i));
        //}
        //System.out.println(array.isEmpty());
        for(int i=0;i<(array.size()-1);i++){
            for(int j=i+1;j<array.size();j++){
                if(array.get(i)<array.get(j)){
                    int variableauxiliar=array.get(i);
                    array.add(i, array.get(j));
                    array.add(j, variableauxiliar);
                }
            }
        }
        System.out.println(array.isEmpty());
    }
}
