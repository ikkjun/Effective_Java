# Item 31. 한정적 와일드카드를 사용해 API 유연성을 높이라.
## 불공변인 매개변수화 타입
매개변수화 타입은 불공변(invariant)이다. 
즉, 서로 다른 타입 Type1과 Type2가 있을 때 List<Type1>은 List<Type2>의 하위 타입도 상위 타입도 아니다.
하지만 때로는 불공변 방식보다 유연한 것이 필요할 때가 있다.

## 해결책
이러한 상황에 대처할 수 있는 한정적 와일드 타입이라는 특별한 매개변수화 타입을 지원한다.
입력 매개변수 타입은 'E의 Iterable'이 아니라 'E의 하위 타입의 Iterable'이어야 하며,
와일드 카드 타입 Iterable<? extends E>가 정확히 이런 뜻이다.

또는 입력 매개변수의 타입이 'E의 상위타입의 Collection'은 와일드 카드 타입을 사용한 Collection<? super E>가 정확히 이런 의미이다.

유연성을 극대화하려면 원소의 생상자나 소비자용 입력 매개변수에 와일드 카드 타입을 사용해야 한다.

## 펙스(PECS)
producer-extends, consumer-super
매개변수화 타입 T가 생산자라면 <? extends T>를 사용하고, 소비자라면 <? super T>를 사용하라.
PECS공식은 와일드 카드 타입을 사용하는 기본 원칙이다.

소비자: 매개변수로 받는 것
생산자: 반환타입으로 주는 것

반환타입에는 한정적 와일드카드 타입을 사용하면 안 된다.
유연성을 높여주지 않고, 클라이언트 코드에서도 와일드카드 타입을 써야 하기 때문이다.

클래스 사용자가 와일드카드 타입을 신경써야 한다면 그 API에 무슨 문제가 있을 가능성이 높다.

컴파일러가 올바른 타입을 추론하지 못할 때면 언제든 명시적 타입 인수(explicit type argument)를 사용해서 타입을 알려주면 된다.
목표 타이핑(target typing)은 자바 8부터 지원하기 시작했다.

#### 매개변수(parameter)와 인수(argument)의 차이
매개변수는 메서드 선언에 정의한 변수이고, 인수는 메서드 호출 시 넘기는 실젯값이다.

### Comparable와 Comparator
Comparable은 언제나 소비자이므로, 일반적으로 Comparable<E>보다는 Comparable<? super E>를 사용하는 편이 낫다.
Comparator도 일반적으로 Comparator<E> 보다는 Comparator<? super E>를 사용하는 편이 낫다.
Comparable(혹은 Comparator)을 직접 구현하지 않고 직접 구현한 다른 타입을 확장한 타입을 지원하기 위해 와일드카드가 필요하다.

## 타입 매개변수와 와일드 카드
```java
public static <E> void swap(List<E> list, int i, int j);
public static void swap(List<?> list, int i, int j);
```
타입 매개변수와 와일드 카드에는 공통되는 부분이 있어서, 메서드를 정의할 때 둘 중 어느 것을 사용해도 괜찮을 때가 많다.
첫 번째는 비한정적 타입 매개변수를 사용했고, 두 번째는 비한정적 와일드 카드를 사용했다.

public API라면 간단한 두 번재가 더 낫다.
#### 규칙: 메서드 선언에 타입 매개변수가 한 번만 나오면 와일드 카드로 대체하라.

### 비한정적 와일드 카드의 문제점
직관적으로 구현한 코드가 컴파일 되지 않는다.

### 해결책
와일드카드 타입의 실제 타입을 알려주는 메서드를 private 도우미 메서드로 따로 작성하여 활용하는 방법이다.
실제 타입을 알아내려면 이 도우미 메서드는 제네릭 메서드여야 한다.
```java
public static void swap(List<?> list, int i, int j) {
    swapHelper(list, i, j);
}

// 와일드카드 타입을 실제 타입으로 바꿔주는 private 도우미 메서드
private static <E> void swapHelper(List<E> list, int i, int j) {
    list.set(i, list.set(j, list.get(i)));
}
```
