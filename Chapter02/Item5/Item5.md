# Item 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.

많은 클래스가 하나 이상의 자원에 의존할 때, 그 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다.

대신 클래스가 여러 자원 인스턴스를 지원해야 하며, 클라이언트가 원하는 자원을 사용해야 한다. 이를 위해 인스턴스를 생성할 때 생성자에 필요한 자원(혹은 그 자원을 만들어주는 팩터리를) 생성자에 넘겨주는 의존 객체 주입 방식을 사용해야 한다. 이를 통해 유연성과 테스트 용이성을 향상시킬 수 있다.

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
위의 예에서는 하나의 자원인 dictionary만을 사용하지만, 자원의 개수와는 상관없이 잘 작동한다. 그리고 의존 객체 주입 패턴은 불변([Item17]())을 보장하여 여러 클라이언트가 의존 객체들을 안전히 공유할 수 있다. 의존 객체 주입은 생성자, 정적 팩터리([Item1]()), 빌더([Item2]())에 응용할 수 있다.

의존 객체 주입 패턴의 변형으로 생성자에 자원 팩터리를 넘겨주는 방식이 있다. 팩터리란 호출할 때마다 특정한 타입의 인스턴스를 반복해서 만들어주는 객체를 말한다. 즉, 팩터리 메서드 패턴(Factory Method pattern)을 구현한 것이다. Suppier<T> 인터페이스가 팩터리를 표현한 예시이다. 이 방식을 사용해 클라이언트는 자신이 명시한 하위 타입을 무엇이든 생성할 수 있는 팩터리를 만들 수 있다.

```java
Mosaic create(Supplier<? extends Tile> tileFactory) {...}
```
위의 코드는 클라이언트가 제공한 팩터리가 생성한 Tile들로 구성된 Mosaic를 만드는 메서드이다. 의존 객체 주입이 유연성과 테스트 용이성을 개선해주긴 하지만, 의존성이 수 천 개나 되는 큰 프로젝트에서는 코드를 어지럽게 만들기도 한다. Dagger, Guice, Spring 같은 의존 객체 주입 프레임워크를 사용하면 이런 어지러움을 해결할 수 있다.