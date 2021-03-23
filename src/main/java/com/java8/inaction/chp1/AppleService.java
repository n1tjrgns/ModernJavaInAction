package com.java8.inaction.chp1;

import java.util.ArrayList;
import java.util.List;

public class AppleService {

    //자바8 이전 초록 사과를 골라내는 방식
    public List<Apple> filterGreeanApples(List<Apple> inventory){
        List<Apple> greenApple = new ArrayList<>();

        for (Apple apple : inventory) {
            if ("Green".equals(apple.getColor())){
                greenApple.add(apple);
            }
        }

        return greenApple;
    }

    //자바8 이전 무게가 150 이상인 사과를 골라내는 방식
    public List<Apple> filterWeightOverApples(List<Apple> inventory){
        List<Apple> greenApple = new ArrayList<>();

        for (Apple apple : inventory) {
            if (apple.getWeight() > 150){
                greenApple.add(apple);
            }
        }

        return greenApple;
    }


}
