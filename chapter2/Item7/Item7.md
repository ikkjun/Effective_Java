# 다 쓴 객체를 참조를 해제하라.
```java
import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if(size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }

    private void ensureCapacity() {
        if(elements.length == size)
            elements = Arrays.copyOf(elements, 2*size + 1);
    }
}
```
이 스택을 사용하는 프로그램을 오래 실행하다 보면 점차 가비지 컬렉션 활동과 메모리 사용량이 늘어나 결국 성능이 저하될 것이다.