# Comparable을 구현할지 고려하라.
## Compareable 인터페이스의 compareTo 메서드와 Object의 equals의 차이점
1. compareTo는 단순 동치성 비교에 더해 순서까지 비교할 수 있으며, 제네릭하다. </br>그래서 Comparable을 구현한 객체들의 배열은 다음처럼 쉽게 정렬할 수 있다.
```java
Arrays.sort(a);
```
2. 타입이 다른 객체</br>
모든 객체에 대해 전역 동치 관계를 부여하는 equals 메서드와 달리, compareTo는 타입이 다른 객체를 신경쓰지 않아도 된다. 타입이 다른 객체가 주어지면 대부분 ClassCastException을 던진다.

## Comparable 인터페이스의 구현 
자바 플랫폼 라이브러리의 모든 값 클래스와 열거타입([Item34]())이 Comparable을 구현했다. 알파벳, 숫자, 연대 같이 순서가 명확한 값 클래스를 작성한다면 반드시 Comparable을 구현하는 것이 낫다.
```java
public interface Comparable<T> {
    int comparTo(T t);
}
```

## compareTo 규약
1. 두 객체 참조의 순서를 바꿔 비교해도 예상한 결과가 나와야 한다.
2. 첫 번째가 두 번째보다 크고 두 번째가 세 번째보다 크면, 첫 번재는 세 번째보다 커야 한다.
3. 크기가 같은 객체들끼리는 어떤 객체와 비교하더라도 항상 같아야 한다.

