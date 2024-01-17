# Item 4. 인스턴스화를 막으려면 private 생성자를 사용하라.
생성자를 명시하지 않으면 컴파일러가 자동으로 기본 생성자를 만들어준다. 이로 인해, 매개 변수를 받지 않는 public 생성자가 만들어져서 의도하지 않게 인스턴스화 할 수 있게 된 클래스가 종종 목격된다.

추상 클래스로 만드는 것으로는 인스턴스화를 막을 수 없다. 컴파일러가 기본 생성자를 만드는 경우는 오직 명시된 생성자가 없을 때뿐이니 private 생성자를 추가하면 클래스의 인스턴스화를 막을 수 있다.
```java
public class UtilityClass {
    // 기본 생성자가 만들어지는 것을 막는다(인스턴스화 방지용)
    private UtilityClass() {
        throw new AssertionError();
    }
    // 나머지 코드는 생략
}
```
## 결과
상속을 불가능하게 한다.
- 모든 생성자는 명시적이든 묵시적이든 상위 클래스의 생성자를 호출하게 되는데, private으로 선언한 생성자를 가지고 있는 하위 클래스는 상위 클래스에 접근할 수 없게 된다.


## 정적 메서드와 정적 필드만을 담을 클래스가 필요한 경우
### 1. 기본 타입 값이나 배열 관련 메서드들을 모아 놓는 경우
ex. java.lang.Math와 java.util.Arrays
```java
public final class Math {

    /**
     * Don't let anyone instantiate this class.
     */
    private Math() {}

    /**
     * The {@code double} value that is closer than any other to
     * <i>e</i>, the base of the natural logarithms.
     */
    public static final double E = 2.7182818284590452354;

    /**
     * The {@code double} value that is closer than any other to
     * <i>pi</i>, the ratio of the circumference of a circle to its
     * diameter.
     */
    public static final double PI = 3.14159265358979323846;

    /**
     * Returns the trigonometric sine of an angle.  Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the
     * result is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     *
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param   a   an angle, in radians.
     * @return  the sine of the argument.
     */
    public static double sin(double a) {
        return StrictMath.sin(a); // default impl. delegates to StrictMath
    }
```
### 2. 특정 인터페이스를 구현하는 객체를 생성해주는 정적메서드를 모으는 경우
ex. java.util.Collections

### 3. final class와 관련한 메서드들을 모아 놓는 경우