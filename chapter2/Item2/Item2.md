# 생성자에 매개변수가 많다면 빌더를 고려해라

정적 팩터리와 생성자는 선택적 매개변수가 많을 때 적절하게 대처하기 어렵다는 공통점이 있다.

## 해결방안
### 점층적 생성자 패턴
점증적 생성자 패턴은 확장하기 어렵다. 
다시 말하면, 점층적 생성자 패턴을 사용할 수 있으나, 
매개변수의 개수가 많아지면 클라이언트가 코드를 작성하거나 읽기 어렵다. 
그리고 클라이언트가 실수로 매개변수의 순서를 바꿔서 새로운 인스턴스를 생성하더라도 
컴파일러는 런타임에 다른 동작을 하게 된다.([Item51](https://github.com/ikkjun/Effective_Java/blob/main/Item51.md))

### 자바빈즈 패턴(JavaBeans Pattern)
매개변수가 없는 생성자로 객체를 만든 후, setter 메서드들을 호추해 원하는 매개변수의 값을 설정하는 방식이다. 
그러나 자바빈즈 패턴에서는 객체 하나를 만들려면 메서드를 여러개 호출해야 하고, 
객체가 완전히 생성되기 전까지는 일관성이 무너진 상태에 놓이게 된다.
이처럼 일관성이 무너지는 문제 때문에 자바빈즈 패턴에서는 클래스를 불변([Item17](https://github.com/ikkjun/Effective_Java/blob/main/Item17.md))으로 만들 수 없다.

### 빌더 패턴(Builder pattern)
1. 클라이언트는 필요한 객체를 직접 만드는 대신, 필수 매개변수만으로 생성자(또는 정적 팩터리)를 호출해 빌더 객체를 얻는다. 
2. 빌더 객체가 제공하는 일정의 세터 메서드로 원하는 선택 매개변수들을 설정한다.
3. 매개변수가 없는 build 메서드를 호출해 필요한 객체를 얻는다.