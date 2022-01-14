package com.example.cross_commerce_store.cross_commerce_store.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Numbers {

    @JsonProperty("numbers")
    List<Double> numbers;

    Numbers() {

    }

    public List<Double> getNumbers() {
        return numbers;
    }

    public void setNumbers(List numbers) {
        this.numbers = numbers;
    }


    @Override
    public String toString() {
        return "Numbers{" +
                "numbers=" + numbers +
                '}';
    }
}
