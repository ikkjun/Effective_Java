# equals는 일반 규약을 지켜 재정의하라
## equals를 재정의 하지 말아야 하는 경우
1. 각 인스턴스가 본질적으로 고유하다.ex. Thread
2. 인스턴스의 '논리적 동치성(logical equality)'을 검사할 일이 없다.
3. 상위 클래스에서 재정의한 equals가 하위 클래스에도 딱 들어맞는다.
4. 클래스가 private이거나 package-private이고 equals메서드를 호출할 일이 없다.

## equals를 구현하는 방식
```java
@Override
public boolean equals(Object o) {
    throw new AssertionError(); // 호출 금지!
        }
```

## equals를 재정의해야 하는 경우
객체 식별성(object identity; 두 객체가 물리적으로 같은가)이 아니라 논리적 동치성을 확인해야 하는 상위 클래스의 equals가 논리적 동치성을 비교하도록 재정의 되지 않았을 때다. 주로 값 클래스들이 해당한다. 값 클래스란 Integer와 String처럼 값을 표현하는 클래스를 말한다.

## equals를 재정의 하지 않아도 되는 경우
값 클래스라고 해도, 값이 같은 인스턴스가 둘 이상 만들어지지 않음을 보장하는 인스턴스 통제 클래스([Item 1]())라면 equals를 재저(으이 하지 않아도 된다. Enum([Item34]())도 여기에 해당한다.

## equals 메서드를 재정의 할 때 지켜야 할 일반 규약
equals메서드는 동치관계(equivalence relation)를 구현하며, 다음을 만족한다.

### 1. 반사성(reflexivity)
null이 아닌 모든 참조값 x에 대해, x.equals(x)는 true다.</br>
객체는 자기 자신과 같아야 한다.</br>
### 2. 대칭성(symmetry)
null이 아닌 모든 참조값 x, y에 대해, x.equals(y)가 true면 y.equals(x)도 true이다.</br>
두 객체는 서로에 대한 동치 여부에 똑같이 답해야 한다. 대소문자를 구분하는 경우 대칭성을 위배하지 않도록 주의해야 한다.</br>
equals 규약을 어기면 그 객체를 사용하는 다른 객체들이 어떻게 반응할지 알 수 없다.</br>

#### 해결방안
equals를 String과 연동하지 않기
```java
@Override public boolean equals(Object o) {
    return o instanceof CaseInsensitiveString && ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
}
```

### 3. 추이성(transivity)
null이 아닌 모든 참조값 x, y, z에 대해, x.equals(y)가 true이고 y.equals(z)도 true면 x.equals(z)도 true다.</br>
첫 번째 객체와 두 번째 객체가 같고, 두 번째 객체와 세 번째 객체가 같다면, 첫 번째 객체와 세 번재 객체도 같아야 한다는 뜻이다.

구체 클래스를 확장해 새로운 값을 추가하면서 equals 규약을 만족시킬 방법은 존재하지 않는다. 

리스코프 치환 원칙(Liskov substitution principle)에 따르면, 어떤 타입에 있어서 중요한 속성이라면 그 하위 타입에서도 마찬가지로 중요하다. 따라서 그 타입의 모든 메서드가 하위 타입에서도 똑같이 잘 작동해야 한다.

#### 해결방안
상속 대신 composition을 사용하라

### 4. 일관성(consistency)
null이 아닌 모든 참조값 x, y에 대해, x.equals(y)를 반복해서 호출하면 항상 true를 반환하거나 항상 false를 반환한다.</br>
일관성은 두 객체가 같다면 (어느 하나 혹은 두 객체 모두가 수정되지 않는 한) 앞으로도 영원히 같아야 한다는 뜻이다. 가변 객체는 비교 시점에 따라 서로 다를수도 있고 같을수도 있지만, 불변 객체는 한 번 다르면 끝까지 달라야 한다. 클래스가 불변이든 가변이든 equals의 판단에 신뢰할 수 없는 자원이 끼어들게 해서는 안 된다.
### 5. null-아님
null이 아닌 모든 참조 값 x에 대해, x.equals(null)은 false다.</br>
null-아님은 모든 객체가 null과 같지 않아야 한다는 뜻이다.
```java
// 명시적 null 검사 - 필요 없다!
@Override public boolean equals(Object o) {
    if (o == null) 
        return false;
}

// 묵시적 null 검사 - 이쪽이 낫다.
@Override public boolean equals(Object o) {
    if (!(o instanceof MyType))
        return false;
    MyType mt = (MyType) o;
}
```
equals가 타입을 확인하지 않으면 잘못된 타입이 인수로 주어졌을 때 ClassCastException을 던져서 일반 규약을 위배하게 된다.

## equals 메서드 구현 방법
1. == 연산자를 사용해 입력이 자기 자신의 참조인지 확인한다.
2. instanceof 연산자로 입력이 올바른 타입인지 확인한다.
3. 입력을 올바른 타입으로 형변환한다. instanceof 검사를 했으므로 항상 성공한다.
4. 입력 객체와 자기 자신의 대응되는 '핵심' 필드들이 모두 일치하는지 하나씩 검사한다.

float와 double을 제외한 기본 타입 필드는 == 연산자로 비교하고, 참조 타입 필드는 각각의 equals 메서드로, float와 double 필드는 각각 정적 메서드인 Float.compare(float, float)와 Double.compare(double, double)로 비교한다.</br>
equals를 다 구현했다면 대칭성, 추이성, 일관성이 있는지 검토해야 한다.

```java
// 전형적인 equals 메서드의 예
@Override public boolean equals(Object o) {
    if (o == this)
        return true;
    if(!(o instanceof PhoneNumber))
        return false;
    PhoneNumber pn = (PhoneNumber)o;
    return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
}
```

## equals 메서드 사용 시 주의사항
- equals를 재정의할 땐 hashCode도 반드시 재정의해야 한다.([Item11]())
- 너무 복잡하게 해결하려고 하지 말자. 필드들의 동치성만 검사해도 equals 규약을 어렵지 않게 지킬 수 있다.
- Object 외의 타입을 매개변수로 받는 equals 메서드는 선언하지 말자.
```java
// 잘못된 예 - 입력 타입은 반드시 Object여야 한다!!
public boolean equals(MyClass o) {
        ...
}
```
## equals를 쉽게 작성하게 해주는 프레임워크
equals를 작성하고 테스트 하는 일을 대신해줄 오픈소스가 바로 AutoValue 프레임워크다. 클래스에 애너테이션 하나만 추가하면 AutoValue가 이 메서드들을 알아서 작성해준다.