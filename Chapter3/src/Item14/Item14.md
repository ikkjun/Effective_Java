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
4. compareTo 메서드로 수행한 동치성 테스트의 결과가 equals와 같아야 한다.

## compareTo 메서드 작성 요령
Comparable은 타입을 인수로 받는 제너릭 인터페이스이므로 compareTo 메서드의 인수 타입은 컴파일타임에 정해진다. 또한 null을 인수로 넣어 호출하면 NullPointerException을 던져야 한다.
Comparable을 구현하지 않은 필드나 표준이 아닌 순서로 비교해야 한다면 비교자(Comparator)을 대신 사용한다.
compareTo 메서드에서 관계 연산자 <와 >를 사용하는 이전 방식은 거추장스럽고 오류를 유발한다.

```java
// 기본 타입 필드가 여러 개인 경우 비교
public int compareTo(PhoneNumber pn) {
    int result = Short.compare(areaCode, pn.areaCode);      // 가장 중요한 필드
    if (result == 0) {
        result = Short.compare(prefix, pn.prefix);          // 두 번재로 중요한 필드
        if (result == 0)
            result = Short.compare(lineNum, pn.lineNum);    // 세 번재로 중요한 필드
    }
    return result;
}
```
자바 8부터 Comparator 인터페이스가 일련의 비교자 생성 메서드(comparator construction method)와 팀을 꾸려 메서드 연쇄 방식으로 비교자를 생성할 수 있게 되었다.

```java
// 비교자 생성 메서드를 활용한 비교자
private static final Comparator<PhoneNumber> COMPARATOR = 
        comparingInt((PhoneNumber pn) -> pn.areaCode)
                .thenComparingInt(pn -> pn.prefix)
                .thenComparingInt(pn -> pn.lineNum);

public int compareTo(PhoneNumber pn) {
    return COMPARATOR.compare(this, pn);
}
```

## 정리
순서를 고려해야 하는 값 클래스를 작성한다면 꼭 Comparable 인터페이스를 구현하여, 그 인스턴스들을 쉽게 정렬하고, 검색하고, 비교 기능을 제공하는 컬렉션과 어우러지도록 해야 한다.
compareTo 메서드에서 필드의 값을 비교할 때 비교 연산자(<와 >)는 사용하지 말아야 한다.
그 대신 박싱된 기본 타입 클래스가 제공하는 정적 compare 메서드나 Comparator 인터페이스가 제공하는 비교자 생성 메서드를 사용해야 한다.
```java
// 정적 compare메서드를 활용한 비교자
static Comparator<Object> hashCodeOrder = new Comparator<>() {
    public int compare(Object o1, Object o2) {
        return Integer.compare(o1.hashCode(), o2.hashCode());
    }
};

// 비교자 생성 메서드를 활용한 비교자
static Comparator<Object> hashCodeOrder = 
    Comparator.comparingInt(o -> o.hashCode());
```