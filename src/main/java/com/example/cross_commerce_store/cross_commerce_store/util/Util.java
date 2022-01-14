package com.example.cross_commerce_store.cross_commerce_store.util;

import java.util.ArrayList;
import java.util.List;

public class Util {

    /* MÉTODO DE ORDENAÇÃO D0S VALORES.
    RETORNA UM ARRAY COM VALORES EM ORDEM CRESCENTE.*/
    public static List<Double> transform(List<Double> list){
        double lowerValue = 0, highestValue = 0, number = 0;
        int indexPrevious = 0;

        ArrayList<Double> listAux = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            number = list.get(i);
            if (i == 0) {
                highestValue = number;
                lowerValue = number;
            }
            if (number >= highestValue) {
                listAux.add(number);
                highestValue = number;
            } else if (number <= lowerValue) {
                listAux.add(0, number);
                lowerValue = number;
            } else if (number < indexPrevious) {
                listAux.add(indexPrevious - 1, number);
            } else if (number > indexPrevious) {
                listAux.add(indexPrevious, number);
            }
            indexPrevious = i;
            System.out.println(listAux);
        }
        return  listAux;
    }
}
