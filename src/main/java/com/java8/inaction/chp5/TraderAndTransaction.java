package com.java8.inaction.chp5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TraderAndTransaction {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul","Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transaction = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //1. 2011년에 일어난 모든 트랜잭션을 찾아 오름차순으로 정리
        List<Transaction> transaction2011Sorted = transaction.stream()
                .filter(t->t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        System.out.println("transaction2011Sorted = " + transaction2011Sorted);

        //2. 거래자가 근무하는 모든 도시를 중복 없이 나열해라
        List<String> getTraderCiryName = transaction.stream()
                .map(t->t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        System.out.println("getTraderCiryName = " + getTraderCiryName);

        //3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬
        List<Trader> cambridgeTraderSortedByName = transaction.stream()
                .map(Transaction::getTrader)
                .filter(t->t.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        System.out.println("cambridgeTraderSortedByName = " + cambridgeTraderSortedByName);

        //4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환
        String allTraderNameOrderByAsc = transaction.stream()
                .map(t->t.getTrader().getName()) //모든 이름
                .distinct()
                .sorted()
                .reduce("", (n1, n2)->n1 + n2);

        System.out.println("allTraderNameOrderByAsc = " + allTraderNameOrderByAsc);

        //5. 밀라노에 거주자가 있는가?
        boolean milan = transaction.stream()
                .anyMatch(t->t.getTrader().getCity().equals("Milan"));

        System.out.println("milan = " + milan);

        //6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력해라
        Consumer<Integer> cambridgeTraderValue = System.out::println;
        transaction.stream()
                .filter(t->"Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(cambridgeTraderValue);

        //7. 전체 트랜잭션 중 최댓값??
        Optional<Integer> maxValue = transaction.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        System.out.println("maxValue = " + maxValue);

        //8. 전체 트랜잭션 중 최솟값??
        Optional<Integer> minValue = transaction.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);

        System.out.println("minValue = " + minValue);
    }
}
