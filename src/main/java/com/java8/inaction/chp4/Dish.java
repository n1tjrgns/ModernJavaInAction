package com.java8.inaction.chp4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public enum Type {
        MEAT, OTHER, FISH
    }

    @Override
    public java.lang.String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", vegetarian=" + vegetarian +
                ", calories=" + calories +
                ", type=" + type +
                '}';
    }

    public static final List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 400, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );

    public static void main(String[] args) {
        //칼로리가 300넘는 음식 순서대로 3개
        List<String> threeHighCaloriecDishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("threeHighCaloriecDishNames = " + threeHighCaloriecDishNames);

        //피자의 칼로리 구하기
        List<Integer> getPizzaCalories = menu.stream()
                .filter(dish -> "pizza".equals(dish.getName()))
                .map(Dish::getCalories)
                .collect(Collectors.toList());

        System.out.println("pizza Calories : " + getPizzaCalories);

        //야채가 들어간 음식 찾기
        List<String> getInputVegetable = menu.stream()
                .filter(Dish::isVegetarian)
                .map(Dish::getName)
                .collect(Collectors.toList());

        System.out.println("getInputVegetable = " + getInputVegetable);
    }
}
