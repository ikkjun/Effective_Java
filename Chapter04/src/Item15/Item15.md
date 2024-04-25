# 클래스와 멤버의 접근 권한을 최소화하라.

잘 설계된 컴포넌트는 모든 내부 구현을 완벽히 숨겨, 구현과 API를 깔끔히 분리한다. 오직 API를 통해서만 다른 컴포넌트와 소통하며 서로의 내부 동작 방식에는 전혀 개의치 않는다. 이러한 정보 은닉이라는 개념은 소프트웨어 설계의 근간이 되는 원리이다.

## 자바의 정보 은닉 장치
자바의 접근 제어 메커니즘은 클래스, 인터페이스, 멤버의 접근성을 명시한다. 각 요소의 접근성은 그 요소가 선언된 위치와 접근 제한자(private, protected, public)으로 정해진다. 이 접근 제한자를 제대로 활용하는 것이 정보 은닉의 핵심이다. 

## 정보 은닉의 기본 원칙
1. 모든 클래스와 멤버의 접근성을 가능한 최소한으로 해야 한다.</br>
가장 바깥이라는 의미의 톱레벨 클래스와 인터페이스에 부여할 수 있는 접근 수준은 package-private와 public 두 가지이다. 
패키지 외부에서 쓸 이유가 없다면 package-private으로 선언해야 한다.
이로 인해 클라이언트에게 아무런 피해 없이 수정, 교체, 제거할 수 있다. 
반면, public으로 선언한다면 API가 되므로 하위 호환을 위해 계속 관리를 해야 한다.
그러므로 public일 필요가 없는 클래스의 접근 수준을 package-private의 톱레벨 클래스로 좁혀야 한다.
테스트만을 위해 클래스, 인터페이스, 멤버를 공개 API로 만들어서는 안 된다.

2. public 클래스의 인스턴스 필드는 되도록 public이 아니어야 한다. </br> public 가변 필드를 갖는 클래스는 일반적으로 스레드 안전하지 않다. 다만, 해당 클래스가 표현하는 추상 개념을 완성하는 데 꼭 필요한 구성요소로써의 상수라면 public static final 필드로 공개해도 좋다.
public static final 필드가 참조하는 객체가 불변인지 확인하라.

## 멤버 접근성을 좁히지 못하게 하는 제약
상위 클래스의 메서드를 재정의할 때는 그 접근 수준을 상위 클래스에서보다 좁게 설정할 수 없다.

## 보안 허점이 존재하는 코드 수정하기
```java
public static final Thing[] VALUES = { /*...*/ };
```
### 해결방안
#### 1. public 배열을 private으로 만들고 public 불변 리스트를 추가
```java
private static final Thing[] PRIVATE_VALUES = { /*...*/ };
public static final List<Thing> VALUES = 
        Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
```
#### 2. 배열을 private으로 만들고 복사본을 반환하는 public 메서드 추가(방어적 복사)
