# 비검사 경고를 제거하라
제네릭을 사용하기 시작하면 수 많은 컴파일러 경고를 보게 된다. 
비검사 형변환 경고, 비검사 메서드 호출 경고, 비검사 매개변수화 가변인수 타입 경고, 비검사 변환 경고 등이다.
할 수 있는 한 모든 비검사 경고를 제거해야 한다.
이로 인해 코드는 타입 안전성이 보장된다.

경고를 제거할 수는 없지만 타입이 안전하다고 확신할 수 있다면 @SuppressWarnings("unchecked") 애너테이션을 달아 경고를 숨길 수 있다.
SuppressWarnings 애너테이션은 항상 가능한 한 좁은 범위에 적용해야 한다.
보통은 변수 선언, 아주 짧은 메서드, 혹은 생성자가 될 것이다.

애너테이션은 선언에만 달 수 있기 때문에 return 문에는 @SuppressWarnings)를 달 수 없다.
반환값을 담을 지역변수를 하나 선언하고 그 변수에 애너테이션을 달면 된다.
```java
public <T> T[] toArray(T[] a) {
    if (a.length < size) {
        // 생성한 배열과 매개변수로 받은 배열의 타입이 모두 T[]로 같으므로
        // 올바른 형변환이다.
        @SuppressWarnings("unchecked") T[] result =
                (T[]) Arrays.copyOf(elements, size, a.getClass());
        return result;
    }
    System.arraycopy(elements, 0, a, 0, size);
    if (a.length > size)
        a[size] = null;
    return a;
}
```
@SuppressWarnings("unchecked") 애너테이션을 사용할 때면 그 경고를 무시해도 안전한 이유를 항상 주석으로 남겨야 한다.