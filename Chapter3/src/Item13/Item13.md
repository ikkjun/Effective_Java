# clone 재정의는 주의해서 진행하라
Cloneable은 복제해도 되는 클래스임을 명시하는 용도의 믹스인 인터페이스(mixin interface, [Item20]())이다.

## clone 메서드의 문제점
clone메서드가 선언된 곳이 Cloneable이 아닌 Object이고, 그 마저도 protected라는 데 있다. 그래서 Cloneable을 구현하는 것만으로는 외부 객체에서 clone메서드를 호출할 수 없다. 

## Cloneable 인터페이스의 역할
Object의 protected 메서드인 clone의 동작 방식을 결정한다. Cloneable을 구현한 클래스의 인스턴스에서 clone을 호추하면 그 객체의 필드들을 하나씩 복사한 객체를 반환하며, 그렇지 않은 클래스의 인스턴스에서 호출하면 CloneNotSupportedException을 던진다.

실무에서 Cloneable을 구현한 클래스는 clone메서드를 public으로 제공하며, 사용자는 당연히 복제가 제대로 이뤄질 것이라고 기대한다.

## clone 메서드 구현 방법
먼저 super.clone을 호출한다.

```java
@Override public PhoneNumber clone() {
    try {
        return (PhoneNumber) super.clone();
    } catch (CloneNotSupportedException e) {
        throw new AssertionError(); // 일어날 수 없는 일이다.
    }
}
```
### 가변 상태를 참조하는 클래스용 clone 메서드
clone메서드는 사실상 생성자와 같은 효과를 낸다. 즉, clone은 원본 객체에 아무런 해를 끼치지 않는 동시에 복제된 객체의 불변식을 보장해야 한다.
그래서 Stack의 clone메서드는 제대로 동작하려면 스택 내부 정보를 복사해야 하는데, 가장 쉬운 방법은 elements 배열의 clone을 재귀적으로 호출해 주는 것이다.

```java
@Override public Stack clone() {
    try {
        Stack result = (Stack) super.clone();
        result.elements = elements.clone();
        return result;
    }  catch (CloneNotSupportedException e) {
        throw new AssertionError();
    }
}
```

Cloneable 아키텍처는 '가변 객체를 참조하는 필드는 final로 선언하라'는 일반용법과 충돌한다. 원본과 복제된 객체가 가변 객체를 공유해다 안전한 경우가 아니라면, 복제할 수 있는 클래스를 만들기 위해 일부 필드에서 final 한정자를 제거할 수도 있다.

```java
public class HashTable implements Cloneable {
    private Entry[] buckets = new Entry[10];
    
    private static class Entry {
        final Object key;
        Object value;
        Entry next;
        
        Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        // 이 엔트리가 가리키는 연결 리스트를 재귀적으로 복사
        Entry deepCopy() {
            return new Entry(key, value, next == null? null : next.deepCopy());
        }
    }
    
    @Override public HashTable clone() {
        try {
            HashTable result = (HashTable) super.clone();
            result.buckets = new Entry[buckets.length];
            for (int i=0;i<buckets.length;i++) 
                if(buckets[i] != null)
                    result.buckets[i] = buckets[i].deepCopy();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
//    // 잘못된 clone 메서드 - 가변 상태를 공유한다!
//    // 이 배열은 원본과 같은 연결 리스트를 참조하여 원본과 복제본 모두 예기치 않게 동작할 가능성이 생긴다.
//    @Override public HashTable clone() {
//        try {
//            HashTable result = (HashTable) super.clone();
//            result.buckets = buckets.clone();
//            return result;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
```

연결리스트를 복제하는 방법은 재귀 호출 때문에 리스트의 원소 수만큼 스택 프레임을 소비하여, 리스트가 길면 스택 오버플로를 일으킬 위험이 있기 때문에 좋은 방법은 아니다. 이 문제를 피하려면 deepCopy를 재귀 호출 대신 반복자를 써서 순회하는 방향으로 수정해야 한다.
## clone 메서드 구현 시기

## clone 메서드의 대안