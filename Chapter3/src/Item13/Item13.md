# clone 재정의는 주의해서 진행하라
Cloneable은 복제해도 되는 클래스임을 명시하는 용도의 믹스인 인터페이스(mixin interface, [Item20]())이다.

## clone 메서드의 문제점
clone메서드가 선언된 곳이 Cloneable이 아닌 Object이고, 그 마저도 protected라는 데 있다. 그래서 Cloneable을 구현하는 것만으로는 외부 객체에서 clone메서드를 호출할 수 없다. 

## Cloneable 인터페이스의 역할
Object의 protected 메서드인 clone의 동작 방식을 결정한다. Cloneable을 구현한 클래스의 인스턴스에서 clone을 호추하면 그 객체의 필드들을 하나씩 복사한 객체를 반환하며, 그렇지 않은 클래스의 인스턴스에서 호출하면 CloneNotSupportedException을 던진다.

실무에서 Cloneable을 구현한 클래스는 clone메서드를 public으로 제공하며, 사용자는 당연히 복제가 제대로 이뤄질 것이라고 기대한다.
## clone 메서드 구현 방법

## clone 메서드 구현 시기

## clone 메서드의 대안