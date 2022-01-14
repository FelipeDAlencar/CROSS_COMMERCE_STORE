package com.example.cross_commerce_store.cross_commerce_store.controller;

import com.example.cross_commerce_store.cross_commerce_store.model.Numbers;
import com.example.cross_commerce_store.cross_commerce_store.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@RestController
public class Controller {

    ArrayList<Double> numbersList = new ArrayList<>();
    public static final String URI = "http://challenge.dienekes.com.br/api/numbers?page=";

    /* MÉTODO RESPONSÁVEL POR FAZER O MAPEAMENTO DA ROTA DE EXTRAÇÃO.
    DEVE SER PASSADO UM ARGUMENTO QUE CONRESPONDE A PÁGINA DE INÍCIO */
    @GetMapping(path = "/extract/{page}")
    public ResponseEntity<?> extract(@PathVariable("page") int page) {
        return new ResponseEntity<>(CallPagination(page), HttpStatus.OK);
    }

    /* MÉTODO RESPONSÁVEL POR FAZER O MAPEAMENTO DA ROTA DE CARREGAMENTO */
    @GetMapping(path = "/load")
    public ResponseEntity<?> load() {
        return new ResponseEntity<>(Util.transform(numbersList), HttpStatus.OK);
    }


    /* MÉTODO RESPONSÁVEL POR REALIZAR A CHAMADA DO MÉTODO RESPONSÁVEL PELAS
    REQUISIÇÕES, A DESSERIALIZAÇÃO DO JSON RETORNADO E A LÓGICA DE PAGINAÇÃO
    UM LAÇO WHILE FAZ AS REQUISIÇÕES ATÉ QUE SEJA RETORNADO O ARRAY VAZIO E RETORNA O ARRAY
    POPULADO DE TODOS OS NÚMEROS RETORNADOS DE TODAS AS REQUISIÇÕES.
    */
    public ArrayList<Double> CallPagination(int page) {
        String response = "";
        while (true) {
            try {
                response = responseRequest(page);
                final ObjectMapper mapper = new ObjectMapper();
                Numbers numbers = mapper.readValue(response, Numbers.class);


                if (numbers.getNumbers().isEmpty()) {
                    return numbersList;
                } else {
                    iteratorAddArray(numbers);
                    page += 1;
                    System.out.println("PAGE " + page);
                }
            } catch (JsonProcessingException e) {
                System.out.println("ERRO AO PROCESSAR JSON. REFAZENDO OPERAÇÃO..." + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("ARGUMENTO NÃO RECONHECIDO PARA PROCESSAMENTO. REFAZENDO OPERAÇÃO... " + e.getMessage());
            }
        }
    }
    /* MÉTODO RESPONSÁVEL PELAS REQUISIÇÕES. BUSCA E RETORNA A STRING CONTENDO O JSON*/
    public String responseRequest(int page) {
        try {
            RestTemplate rest = new RestTemplate();
            String response = rest.getForObject(URI + page, String.class);
            System.out.println("RESPONSE " + response);
            return response;
        } catch (Exception e) {
            System.out.println("ERRO AO REALIZAR REQUISIÇÃO. REFAZENDO REQUISIÇÃO... " + e.getMessage());
        }
        return null;
    }
    /* RESPONSÁVEL POR ITERAR O JSON E POPULAR O ARRAY COM OS MESMOS.*/
    public void iteratorAddArray(Numbers numbers) {
        for (Double number : numbers.getNumbers()) {
            numbersList.add(number);
        }
    }
}
