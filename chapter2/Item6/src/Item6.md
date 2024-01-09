# 불필요한 객체 생성을 피하라
똑같은 기능의 객체를 매번 생성하기보다는 객체 하나를 재사용하는 것이 빠를 수 있다. 특히 불변 객체([Item17]())는 언제든 재사용할 수 있다.

생성자 대신 정적 팩터리 메서드([Item1]())를 제공하는 불변 클래스에서는 정적 팩터리 메서드를 사용해 불필요한 객체 생성을 피할 수 있다.

```java
static boolean isRomanNumeral(String s) {
    return s.matches("^(?=.)M*(C[MD]|D?C{0,3") + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
}
```
String.matches는 정규표현식으로 문자열 형태를 확인하는 가장 쉬운 방법이지만, 성능이 중요한 상황에서 반복적으로 사용하기에는 부적절하다.

성능을 개선하려면 필요한 정규표현식을 표현하는 (불변인)Pattern 인스턴스를 클래스 초기화(정적 초기화) 과정에서 직접 생성해 캐싱해두고, 나중에 isRomanNumeral 메서드가 호출될 때마다 이 인스턴스를 재사용한다.

```java
import java.util.regex.Pattern;

public class RomanNumerals {
    public static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3)"
            + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$"
    );

        static boolean isRomanNumeral (String s){
            return ROMAN.matcher(s).matches();
        }
    }
}
```