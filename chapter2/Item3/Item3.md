# private 생성자나 열거 타입으로 싱글턴임을 보증하라
## 싱글턴(singleton)이란? 
인스턴스를 오직 하나만 생성할 수 있는 클래스이다.</br>
ex. 함수([Item24](https://github.com/ikkjun/Effective_Java/blob/main/Item24.md))와 같은 무상태(stateless) 객체나 설계상 유일해야 하는 시스템 컴포넌트

### 단점
클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워질 수 있다.

## 싱글턴을 만드는 방식
공통점: 생성자는 private으로 숨기고, 유일한 인스턴스에게 접근할 수 있는 public static 멤버를 하나 마련해둔다.
### 1. public static final 필드 방식
- 예외</br>
권한이 있는 클라이언트는 리플렉션 API([Item65](https://github.com/ikkjun/Effective_Java/blob/main/Item65.md))인 AccessibleObject.setAccessible을 사용해 private 생성자를 호출할 수 있다. 이러한 공격을 방어하려면 생성자를 수정하여 두 번째 객체가 생성되려 할 때 예외를 던지게 하면 된다.
- 장점
  - 해당 클래스가 싱글턴임이 API에 명백히 드러난다.
  - 간결함
### 2. static factory 방식
정적 팩터리 메서드를 public static 멤버로 제공한다.
- 장점
  - API를 바꾸지 않고도 싱글턴이 아니게 변경할 수 있다.
  - 정적 팩터리를 제네릭 싱글턴 팩터리로 만들수 있다.([Item30](https://github.com/ikkjun/Effective_Java/blob/main/Item30.md))
  - 정적 팩터리의 메서드 참조를 공급자로 사용할 수 있다.
### 3. 열거 타입 선언
대부분의 상황에서 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다.</br>
단, 만들려는 싱글턴이 Enum 외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없다.

## 싱글턴 클래스 직렬화 방법
Serialzable을 구현한다고 선언하는 것만으로는 부족하다. 
모든 인스턴스 필드를 일시적(transient)라고 선언하고 readResolve 메서드를 제공해야 한다.([Item89](https://github.com/ikkjun/Effective_Java/blob/main/Item89.md))
이렇게 하지 않으면 직렬화된 인스턴스를 역직렬화할 때마다 새로운 인스턴스가 만들어진다.