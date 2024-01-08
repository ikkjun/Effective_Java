# Item 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.

많은 클래스가 하나 이상의 자원에 의존할 때, 사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다.

대신 클래스가 여러 자원 인스턴스를 지원해야 하며, 클라이언트가 원하는 자원을 사용해야 한다. 이를 위해 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 의존 객체 주입 방식을 채택해야 한다.

```java
import java.util.Objects;

public class SpellChecker {
    private final Lexicon dictionary;

    public SpellChecker(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }
    
    public boolean isValid(String word) {...}
    public List<String> suggestions(String type) {...}
}
```
