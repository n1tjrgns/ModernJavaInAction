package com.java8.inaction.chp1;

public class Apple {
    private String color;
    private int weight;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public static boolean isGreenApple(Apple apple){
        return "Green".equals(apple.getColor());
    }

    public static boolean isWeightOver(Apple apple){
        return apple.getWeight() > 150;
    }
}
