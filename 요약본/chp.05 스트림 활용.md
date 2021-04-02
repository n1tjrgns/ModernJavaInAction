## 모던자바인액션 5장_스트림 활용

----

### 스트림 API가 지원하는 연산

- 필터링
- 슬라이싱
- 매핑
- 검색
- 매칭
- 리듀싱

----

#### 5.1 필터링

#### 5.1.1 predicate로 필터링(boolean 반환)

```java
List<Dish> vegetarianMenu = menu.stream()
										.filter(Dish::isVegetarian)
										.collect(toList());
```



#### 5.1.2 고유 요소 필터링

distinct를 사용해 중복 제거 가능

```java
List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,2,2);
        numbers.stream()
                .filter(i -> i%2 ==0)
                .distinct()
                .forEach(System.out::println);
```



#### 5.2.1 프레디케이트를 이용한 슬라이싱

자바9에서 스트림의 요소를 효과적으로 선택할 수 있도록 takeWhile, dropWhile 두 가지 메소드를 지원한다.

```java
				//효과적인 슬라이싱 takeWhile
        List<Dish> filteredMenuWithTake = menu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        //dropWhile
        List<Dish> filteredMenuwithDrop = menu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());
```

takeWhile을 사용하는 경우 320보다 같거나 큰 요소가 나오면 반복해서 탐색하는 작업을 중단하게된다.

따라서 불필요한 반복을 줄일 수 있다.

탐색을 중단하기 때문에 먼저 정렬이 되어있다는 전제조건이 필요하다.



dropWhile은 predicate가 거짓이 되는 지점에서 작업을 중단하고 남은 요소를 반환한다.

칼로리가 320보다 같거나 큰 요소를 찾으면 탐색을 중단하고 탐색하지않고 남은 요소들을 반환한다.



#### 5.2.2 스트림 축소

limit, skip을 사용해 스트림 요소를 제한하거나 뛰어넘을 수 있다.



#### 5.3 매핑

특정 데이터를 선택하는 기능 제공 ( map, flatMap 메소드)



#### 5.3.1 스트림의 각 요소에 함수 적용하기

스트림은 함수를 인수로 받는 map 메소드를 지원한다.

인수로 제공된 함수는 각 요소에 적용되며 함수를 적용한 결과가 새로운 요소로 매핑된다. (새로 만들기 때문에 변환이라기 보다 **매핑**이라고 한다.)

```java
List<Integer> dishNameLengths = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
```



#### 5.3.2 스트림 평면화

{"hello","world"} 리스트가 있을때 고유 문자로 이루어진 리스트 반환

```java
List<String> strList = str.stream()
							.map(workd -> word.split(""))
							.distinct()
							.collect(toList());
```

위와 같이 생각하기 쉽다.

하지만, 위 코드는 컴파일 에러가 발생한다.

그 이유는 word.split의 반환형은 Strea<String[]>  인데, 우리가 원하는 반환형은 List<String> 이기 때문이다.



> 그럼 어떻게 해결해야할까?
>
> - flatMap

우선 배열 스트림 대신 문자열 스트림이 필요하다.

Arrays.stream() 메소드를 활용할 수 있다.

```java
				//String[] arrayOfwords = {"hello","world"};
        //Stream<String> streamOfwords = Arrays.stream(arrayOfwords);

        List<String> tempStr = str.stream()
                .map(str -> str.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());
```

하지만 이 방법 역시 실패다.

List<Stream<String>>의 형태가 되어버리기 때문이다.



flatMap을 사용하면 한 번에 해결이 가능하다.

```java
List<String> flatMapList = str.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());
```



예제)

```java
				//2개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트 반환하기
        List<Integer> num1 = Arrays.asList(1,2,3);
        List<Integer> num2 = Arrays.asList(3,4);

        List<int[]> collectNum = num1.stream()
                .flatMap(i->num2.stream()
                        .map(j->new int[]{i, j}))
                .collect(toList());

       
        //합이 3으로 나누어떨어지는 쌍만 반환
        List<int[]> collectSum = num1.stream()
                .flatMap(i->num2.stream()
                        .filter(j->(i + j) % 3 == 0)
                        .map(j->new int[]{i, j}))
                .collect(toList());

       
```



#### 5.4 검색과 매칭

특정 속성이 데이터 집합에 있는지 여부를 검색하는 데이터 처리도 자주 사용된다.

allMatch, anyMatch, noneMatch, findFirst, findAny 등의 메소드도 제공한다.

전부일치, 적어도 하나 매치, 전부 불일치, 첫번째 찾기, 아무거나 찾기



findAny의 경우에는 아무 요소도 반환하지 않을 수 있기에 Optional과 같이 사용해야한다.

```java
Optional<Dish> dish = 
		menu.stream()
		.filter(Dish::isVegetarian)
		.findAny();
```



#### Optional

값의 존재나 부재 여부를 표현하는 컨테이너 클래스

- isPresent()

  Optional이 값을 포함하면 true, 아니면 false

- ifPresent(Consumer<T> block)

  값이 있으면 주어진 블록을 실행

- T get()

  값이 존재하면 값을 반환, 없으면 NoSuchElementException 발생

- T orElse

  값이 있으면 값을 반환, 없으면 기본값 반환.



#### findFirst와 findAny??

- 병렬성 때문에 두 가지 모두 필요하다.
- 병렬 실행에서는 첫 번째 요소를 찾기 힘들기 때문에
- 반환 순서가 상관없다면 병렬 스트림에서는 제약이 적은 findAny를 사용한다.



#### 5.5 리듀싱

**모든** 스트림 요소를 처리해서 값으로 도출하는 과정



#### 5.5.1 요소의 합

자바8 이전의 숫자 요소의 합

```
int sum=0;
for(int x : numbers){
	sum+=sum+x;
}
```



리듀스를 사용한 숫자 요소의 합

- sum 변수의 초깃값 0
- 리스트의 모든 요소를 조합하는 연산(+)

```
int sum = numbers.stream().reduce(0, (a,b) -> a + b);

//곱하기
int multi = numbers.stream().reduce(1, (a,b) -> a + b);
```



초깃값이 없는 경우 Optional 객체로 감싸줘야한다.

```
Optional<Integer> max = numbers.stream().reduce(Integer::max);
```



map과 reduce를 연결하는 기법을 **맵 리듀스 패턴**이라고 한다. 쉽게 병렬화하는 특징으로 구글이 웹 검색에 적용해 유명해졌다.



#### reduce 메소드의 장점과 병렬화

기존의 단계적 반복 합계 방식으로 구하는 것과의 차이점은??

- 내부 반복이 추상화되어 내부 구현에서 병렬로 reduce를 실행할 수 있게 된다. 반복적인 합계에서는 sum 변수를 공유해야 하므로 쉽게 병렬화하기 어렵기 때문에 강제적으로 동기화를 시켜야하는데, 그럼 병렬화로 얻을 이득이 없어진다.

- 대신 병렬로 실행하려면 reduce에 넘겨준 람다의 상태(인스턴스 변수 같은거)가 바뀌면 안된다. 연산이 어떤 순서로 실행되더라도 결과가 바뀌지 않는 구조여야 한다.

  ```java
  int sum = numbers.parallelStream().reduce(0, Integer::sum);
  ```



#### 스트림 연산 : 상태 없음과 상태 있음

map, filter는 각 요소를 받아 결과를 출력 스트림으로 보낸다.(사용자가 제공한 람다나 메소드 참조가 내부적인 가변 상태를 갖지않는다는 가정하에) **내부 상태를 갖지 않는 연산**

sorted, distinct는 스트림의 요소를 정렬하거나 중복을 제거하기 위해서는 과거의 이력을 알고있어야한다. 연산을 수행하는 데 필요한 저장소 크기는 정해져있지 않기에, 스트림의 크기가 무한이라면 문제가 생길 수 있다. **모든 요소가 버퍼에 추가되어있어야한다.** **내부 상태를 갖는 연산**이다.



#### 요약

- 스트림 API를 이용해 복잡한 데이터 처리 질의를 표현할 수 있다.
- filter, distinct, takeWhile, dropWhile, skip, limit 메소드로 스트림을 필터링하거나 자를 수 있다.
- takeWhile, dropWhile은 소스가 정렬되어있을 때 효과적으로 사용할 수 있다.
- map, flatMap 메소드로 스트림의 요소를 추출하거나 변환할 수 있다.
- findFirst, findAny 메소드로 스트림의 요소를 검색할 수 있다. allMatch, noneMatch, anyMatch 메소드를 사용해 주어진 predicate와 일치하는 요소를 스트림에서 검색할 수 있다.


