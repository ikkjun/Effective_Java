# 생성자 대신 정적 팩터리 메서드를 고려해볼 것
```java
public static Boolean valueOf(boolean b) {
    return b? Boolean.TRUE : Boolean.FALSE;
}
```
클라이언트가 클래스의 인스턴스를 얻는 방법은 생성자 말고도 클래스는 정적 팩터리 메서드를 제공할 수 있다. 아래의 메서드는 기본 타입인 boolean을 받아 Boolean 객체 참조로 변환해준다.

## 정적 팩터리의 장점
### 1. 이름을 가질 수 있다.</br>
생성자는 클래스와 동일한 이름을 가져야 하기 때문이다. 이로 인해 생성자에 넘기는 매개변수와 생성자 이름만으로 반환될 객체의 특성을 제대로 설명하지 못하지만, 정적 팩터리는 이름을 통해 반환될 객체의 특성을 쉽게 묘사할 수 있다. ex) BigInteger.probablePrime

그리고 하나의 시그니처로는 생성자를 하나만 만들 수 있다. 똑같은 타입을 매개변수로 받는 생성자를 새로 추가할 수 없다는 것이다.
### 2. 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다.
불변 클래스(immutable class; Item17)는 인스턴스를 미리 만들어 놓거나 새로 생성한 인스턴스를 캐싱하여 재활용하는 식으로 불필요한 객체 생성을 피할 수 있다. Boolean.valueOf(boolean) 메서드는 객체를 아예 생성하지 않는다.

### 3. 반환 타입의 하위 타입 객체를 반환할 수 있다.
- 반환할 객체의 클래스를 자유롭게 선택할 수 있는 유연성이 있다. 
- 인터페이스 기반 프레임워크: 구현한 모든 클래스를 공개하지 않고 인터페이스 객체를 반환할 수 있다. 

### 4. 입력 매개 변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
- 반환 타입의 하위 타입이기만 하면 어떤 클래스의 객체를 반환하든 상관 없다.
- EnumSet 클래스는 오직 정적 팩터리만 제공한다.

### 5. 정적 펙터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
- 서비스 제공자 프레임워크(service provider framework) ex) JDBC(Java Database Connectivity)
  - 제공자(provider): 서비스의 구현체
  - 3가지 핵심 컴포넌트
    - 서비스 인터페이스(service interface; JDBC - Connection): 구현체의 동작 정의
    - 제공자 등록 API(provider registration API; JDBC - DriverManager.registerDriver): 제공자가 구현체를 등록할 때 사용
    - 서비스 접근 API(service access API; JDBC - DriverManager.getConnection): 클라이언트가 서비스의 인스턴스를 얻을 때 사용(유연한 정적 팩터리의 실체)
  - 추가(네 번째 컴포넌트)
    - 서비스 제공자 인터페이스(service provider interface; JDBC - Driver): 클라이언트는 원하는 구현체의 조건을 명시

## 정적 팩터리의 단점
### 1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.
### 2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.

## 정적 팩터리 메서드에 자주 사용하는 명명 방식
| 메서드                    | 설명                                                       | 예시                                                          |
|------------------------|----------------------------------------------------------|-------------------------------------------------------------|
| from                   | 매개변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 형변환 메서드                   | Date d = Date.from(instant);                                |
| of                     | 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드                    | Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);        |
| valueOf                | from과 of의 더 자세한 버전                                       | BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);   |
| instance 혹은 getInstance| 매개변수를 받는다면 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지는 않는다.   | StackWalker luke = StackWalker.getInstance(options);        |
| create 혹은 newInstance  |instance 혹은 getInstance와 동일하지만, 항상 새로운 인스턴스를 생성해서 반환한다.| Object newArray = Array.newInstance(classObject, arrayLen); |
|getType| getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다. | FileStore fs = Files.getFileStore(path);                    |
|newType| newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다. | BufferedReader br = Files.newBufferecReader(path);          |
|type| getType나 newType의 간결한 버전                                 |List<Complaint> litany = Collections.list(legacyLitany);|