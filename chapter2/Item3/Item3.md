# private 생성자나 열거 타입으로 싱글턴임을 보증하라
## 싱글턴(singleton)이란? 
인스턴스를 오직 하나만 생성할 수 있는 클래스이다.</br>
ex. 함수([Item24](https://github.com/ikkjun/Effective_Java/blob/main/Item24.md))와 같은 무상태(stateless) 객체나 설계상 유일해야 하는 시스템 컴포넌트

### 단점
클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워질 수 있다.

### 싱글턴을 만드는 방식
공통점: 생성자는 private으로 숨기고, 유일한 인스턴스에게 접근할 수 있는 public static 멤버를 하나 마련해둔다.
#### 1. public static final 필드 방식
    - 예외</br>
        권한이 있는 클라이언트는 리플렉션 API([Item65](https://github.com/ikkjun/Effective_Java/blob/main/Item65.md))인 AccessibleObject.setAccessible을 사용해 private 생성자를 호출할 수 있다. 이러한 공격을 방어하려면 생성자를 수정하여 두 번째 객체가 생성되려 할 때 예외를 던지게 하면 된다.
#### 2. static factory 방식
