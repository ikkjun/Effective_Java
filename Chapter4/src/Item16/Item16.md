# public 클래스에서는 public 필드가 아닌 접근자 메서드를 이용하라.
```java
// 이처럼 퇴보한 클래스는 public이어서는 안 된다
class Point {
    public double x;
    public double y;
}
```
이런 클래스는 데이터 필드에 직접 접근할 수 있으니, 캡슐화의 이점을 제공하지 못한다([Item15]()). 
철저한 객체 지향 프로그래머는 이런 클래스를 상당히 싫어해서 필드들을 모두 private으로 바꾸고 public 접근자(getter)를 추가한다.

```java
// 접근자와 변경자(mutator) 메서드를 활용해 데이터를 캡슐화한다.
class Point {
    private double x;
    private double x;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
```

패키지 바깥에서 접근할 수 있는 클래스라면 접근자를 제공함으로써 유연하게 클래스 내부 표현 방식을 바꿀 수 있다.
하지만 package-private 클래스 혹은 private 중첩 클래스라면 데이터 필드를 노출한다고 해도 문제가 없다.
public 클래스의 필드가 불변이라면 직접 노출할 때 단점이 조금 줄어들지라도 좋은 방법은 아니다.
API를 변경하지 않고는 표현 방식을 바꿀 수 없고, 필드를 읽을 때 부수 작업을 수행할 수 없기 때문이다.

## 핵심 정리
public 클래스는 절대 가변 필드를 직접 노출해서는 안 된다. 불변 필드라면 노출해도 덜 위험하지만, 완전히 안심할 수 없다.
하지만 package-private 클래스나 private 중첩 클래스에서는 종종 불변이든 가변이든 필드를 노출하는 편이 나을 때도 있다.