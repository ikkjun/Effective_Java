# Item 26. 로 타입은 사용하지 말라
## 정의
- 클래스와 인터페이스 선언에 타입 매개변수가 쓰이면, 이를 제네릭 클래스 혹은 제네릭 인터페이스라 한다.
- 제네릭 클래스와 제네릭 인터페이스를 통틀어 제네릭 타입(generic type)이라 한다. 
- 각각의 제네릭 타입은 일련의 매개변수화 타입(parmeterized type)을 정의한다. 먼저 클래스(혹은 인터페이스) 이름이 나오고, 이어서 꺾쇠괄호 안에 실제 타입 매개변수들을 나열한다.
- 제네릭 타입을 하나 정의하면 그에 딸린 로 타입(raw type)도 함께 정의된다. 로 타입이란 제네릭 타입에서 타입 매개변수를 전혀 사용하지 않을 때를 말한다.

오류는 가능한 한 발생 즉시, 이상적으로는 컴파일할 때 발견하는 것이 좋다. 런타임에 문제를 겪는 코드와 원인을 제공한 코드가 물리적으로 상당히 떨어져 있을 수 있기 때문이다.

컴파일러는 컬렉션에서 원소를 꺼내는 모든 곳에 보이지 않는 형변환을 추가하여 절대 실패하지 않음을 보장한다.
로 타입을 쓰는 것을 언어 차원에서 막지는 않았지만 절대로 써서는 안 된다. 
로 타입을 쓰면 제네릭이 주는 안전성과 표현력을 모두 잃게 된다.
로 타입은 호환성 때문에 자바가 만들어 놓았다. 로 타입을 사용하는 메서드에 매개변수화 타입의 인스턴스를 넘겨도 동작해야 했기 때문이다.
List<Object>와 같은 매개변수화 타입을 사용할 때와 달리 List와 같은 로 타입을 사용하면 타입 안전성을 잃게 된다.

## Set<?>과 Set의 차이
로 타입 컬렉션에는 아무 원소나 넣을 수 있으니 타입 불변식을 훼손하기 쉽다.
반면 Collection<?>에는 (null 외에는) 어떤 원소도 넣을 수 없다.

## 예외
1. class 리터럴에는 로 타입을 써야 한다.
2. 런타임에는 제네릭 타입 정보가 지워지므로 instanceof 연산자는 비한정적 와일드카드 타입 이외의 매개변수화 타입에는 적용할 수 없다.