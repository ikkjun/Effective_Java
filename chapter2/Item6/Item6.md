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

## 불필요한 객체를 만들어내는 예시
### 1. 어댑터
어댑터의 실제 작업은 뒷단 객체에 위임하므로 뒷단 객체 당 어댑터 하나씩만 만들어지면 충분하다.
### 2. Map 인터페이스의 keySet 메서드
Map 인터페이스의 keySet 메서드는 Map 객체 안의 키 전부를 담은 Set 뷰를 반환한다. 모두가 똑같은 Map 인스턴스를 대변하기 때문에 반환한 객체 중 하나를 수정하면 다른 모든 객체가 따라서 바뀐다.
### 3. 오토박싱(auto boxing)
오토박싱은 기본 타입과 그에 대응하는 박싱된 기본 타입의 구분을 모호하게 하지만, 완전히 없애주는 것은 아니다.

```java
private static long sum() {
    Long sum = 0L;
    for(long i=0;i<=Integer.MAX_VALUE;i++) 
        sum += i;
}
```
위의 코드는 sum변수를 long이 아닌 Long으로 선언해서 long타입인 i가 Long 타입인 sum에 더해질 때마다 불필요한 Long 인스턴스가 생성된다. 따라서 박싱된 기본 타입보다는 기본 타입을 사용하고 의도하지 않은 오토박싱을 사용하지 않도록 주의해야 한다.

아주 무거운 객체가 아니면 단순히 객체 생성을 피하기 위해 자체 객체 풀을 만들면 안 된다.

방어적 복사([Item50]())가 필요한 상황에서 객체를 재사용했을 때의 피해가, 필요없는 객체를 반복 생성했을 때의 피해보다 훨씬 크다.