# 다 쓴 객체를 참조를 해제하라.
## 메모리 누수를 초래하는 원인
### 1. Stack클래스에서의 객체 참조
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
이 코드에서는 스택이 그 객체들의 다 쓴 참조(obsolete reference)를 여전히 가지고 있기 때문에, 스택이 커졌다가 줄어들었을 때 스택에서 꺼내진 객체들을 가비지 컬렉터가 회수하지 않는다.
elements 배열의 활성 영역 밖의 참조들이 모두 여기에 해당한다. 활성 영역은 인덱스가 size보다 작은 원소들로 구성된다.

객체 참조 하나를 살려두면 가비지 컬렉터는 그 객체뿐 아니라 그 객체가 참조하는 모든 객체를 회수해가지 못한다. 그래서 단 몇 개의 객체가 매우 많은 객체를 회수되지 못하게 할 수 있고 잠재적으로 성능에 악영향을 줄 수 있다.

Stack 클래스는 자기 메모리를 직접 관리하기 때문에 메모리 누수에 취약하다. 배열의 활성 영역에 속한 원소들만 사용되고, 비활성 영역은 쓰이지 않더라도, 가비지 컬렉터가 보기에는 비활성 영역에서 참조하는 객체도 유효한 객체로 본다.
그러므로 비활성 영역이 되는 순간 null 처리를 해서 해당 객체를 더는 쓰지 않을 것임을 가비지 컬렉터에 알려야 한다.

### 2. 캐시
객체 참조를 캐시에 넣고, 그 객체를 다 쓴 뒤로도 한참을 그냥 놔두는 경우에 메모리 누수가 발생한다.

### 3. 리스터(listener) 혹은 콜백(callback)
클라이언트가 콜백을 등록만 하고 명확히 해지하지 않는다면, 조치가 있지 않는 한 콜백은 계속 쌓여 간다.

## 메모리 누수 해결 방안
### 1. 객체 참조 null처리하기
해당 참조를 다 썼을 때 null처리(참조 해제)하면 된다.

```java
public Object pop() {
    if(size == 0)
        throw new EmptyStackException();
    Object result = elements[--size];
    elements[size] = null;   // 다 쓴 참조 해제
    return result;
}
```
다 쓴 참조를 null 처리할 때의 장점: null로 처리한 참조를 실수로 사용하려 하면 프로그램은 즉시 NullPointerException을 던지며 종료된다.
그러나 모든 객체를 다 쓰자마자 하나씩 null 처리를 할 필요는 없고, 객체 참조를 null 처리하는 일은 예외적인 경우여야 한다.
다 쓴 참조를 해제하는 가장 좋은 방법은 그 참조를 담은 변수를 유효 범위(scope) 밖으로 밀어내는 것이다.

### 2-1. WeakHashMap을 사용해 캐시 만들기
캐시 외부에서 키(key)를 참조하는 동안만 엔트리가 살아 있는 캐시가 필요한 상황이라면 WeakHashMap을 사용해 캐시를 만들면 된다.

### 2-2. 백그라운드 스레드를 활용하거나 캐시에 새 엔트리를 추가할 때 부수 작업으로 엔트리를 청소 방법.
- 백그라운드 스레드를 활용: 캐시 엔트리의 유효 기간을 정확히 정의하기 어려운 경우 시간이 지날수록 엔트리의 가치를 떨어뜨리는 방식을 흔히 사용한다. 이런 방식에서는 쓰지 않는 엔트리를 청소해주는 Scheduled ThreadPoolExecuter을 사용한다.
- 캐시에 새 엔트리를 추가할 때 부수 작업으로 엔트리를 청소 방법: LinkedHashMap은 removeEldestEntry 메서드 활용.

### 3. 콜백을 약한 참조(weak reference)로 저장하기
콜백을 WeakHashMap에 키로 저장하면 가비지 컬렉터가 즉시 수거해간다.