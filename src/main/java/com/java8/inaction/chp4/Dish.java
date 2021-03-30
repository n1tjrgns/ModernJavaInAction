package com.java8.inaction.chp4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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
                .collect(toList());

        System.out.println("threeHighCaloriecDishNames = " + threeHighCaloriecDishNames);

        //피자의 칼로리 구하기
        List<Integer> getPizzaCalories = menu.stream()
                .filter(dish -> "pizza".equals(dish.getName()))
                .map(Dish::getCalories)
                .collect(toList());

        System.out.println("pizza Calories : " + getPizzaCalories);

        //야채가 들어간 음식 찾기
        List<String> getInputVegetable = menu.stream()
                .filter(Dish::isVegetarian)
                .map(Dish::getName)
                .collect(toList());

        System.out.println("getInputVegetable = " + getInputVegetable);

        //distinct로 중복 제거
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,2,2);
        numbers.stream()
                .filter(i -> i%2 ==0)
                .distinct()
                .forEach(System.out::println);

        //효과적인 슬라이싱 takeWhile
        List<Dish> filteredMenuWithTake = menu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(toList());

        //dropWhile
        List<Dish> filteredMenuwithDrop = menu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(toList());

        System.out.println("filteredMenuWithTake = " + filteredMenuWithTake);
        System.out.println("filteredMenuwithDrop = " + filteredMenuwithDrop);

        List<Integer> dishNameLengths = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());

        System.out.println("dishNameLengths = " + dishNameLengths);


        //flatMap을 사용해 하나의 평면화된 스트림으로 반환시켜준다.
        List<String> str = Arrays.asList("hello","world");
        String[] arrayOfwords = {"hello","world"};
        Stream<String> streamOfwords = Arrays.stream(arrayOfwords);

        List<String> flatMapList = str.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());

        System.out.println("flatMapList = " + flatMapList);

        //숫자 리스트가 주어질때 제곱근 리스트로 반환하기
        List<Integer> numList = Arrays.asList(1,2,4,8,16,20);
        List<Integer> doubleNumList = numList.stream()
                .map(num->num * num)
                .collect(toList());

        System.out.println("numList = " + numList);
        System.out.println("doubleNumList = " + doubleNumList);

        //2개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트 반환하기
        List<Integer> num1 = Arrays.asList(1,2,3);
        List<Integer> num2 = Arrays.asList(3,4);

        List<int[]> collectNum = num1.stream()
                .flatMap(i->num2.stream()
                        .map(j->new int[]{i, j}))
                .collect(toList());

        System.out.println("collectNum = " + Arrays.toString(new List[]{collectNum}));

        //합이 3으로 나누어떨어지는 쌍만 반환
        List<int[]> collectSum = num1.stream()
                .flatMap(i->num2.stream()
                        .filter(j->(i + j) % 3 == 0)
                        .map(j->new int[]{i, j}))
                .collect(toList());

        System.out.println("collectSum = " + collectSum);

        //map, reduce를 사용해서 dish의 요리갯수 카운트하기
        Integer count = menu.stream()
                .map(d->1)
                .reduce(0, (a, b)->a + b);
        System.out.println("count = " + count);

        long count2 = menu.stream().count();
        System.out.println("count2 = " + count2);
    }

}
