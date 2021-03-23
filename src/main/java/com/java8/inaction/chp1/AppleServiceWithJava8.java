package com.java8.inaction.chp1;

import java.util.ArrayList;
import java.util.List;

public class AppleServiceWithJava8 {


    public interface Predicate<T>{
        boolean judge(T t);
    }

    public List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p){
        List<Apple> filterAppleList = new ArrayList<>();
        for (Apple apple : inventory) {
            p.judge(apple);
        }

        return filterAppleList;
    }

    public static void main(String[] args) {
        AppleServiceWithJava8 a = new AppleServiceWithJava8();
        List<Apple> inventory = new ArrayList<>();
        Apple apple = new Apple();
        apple.setColor("Green");
        apple.setWeight(160);
        inventory.add(apple);

        //Predicate를 사용한 함수 호출
        a.filterApples(inventory, Apple::isGreenApple);
        a.filterApples(inventory, Apple::isWeightOver);

        //하지만 한 번만 쓰면 되는데 매번 일일이 함수에 대한 기능을 정의해줘야하나???
        //그렇지 않다. 그런 경우에는 람다식으로 바로 사용 가능
        a.filterApples(inventory, (Apple app) -> "Green".equals(app.getColor()));
        a.filterApples(inventory, (Apple app) -> app.getWeight() > 150);

    }
}
