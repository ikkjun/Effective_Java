# Item 61. 박싱된 타입보다는 기본 타입을 이용하라
자바의 데이터 타입은 크게 두 가지로 나눌 수 있다. 
int, double, boolean 같은 기본 타입과 String, List 같은 참조 타입이다. 
그리고 각각의 기본 타입에는 대응하는 참조 타입이 하나씩 있으며, 이를 박싱된 기본 타입이라고 한다. 

기본 타입과 박싱된 기본 타입의 주된 차이는 크게 세 가지다. 
1. 기본 타입은 값만 가지고 있으나, 박싱된 기본 타입은 값에 더해 식별성(identity; 주소)을 갖는다.
2. 기본 타입의 값은 언제나 유효하나, 박싱된 기본 타입은 유효하지 않은 값, 즉 null을 가질 수 있다. 
3. 기본 타입이 박싱된 기본 타입보다 시간과 메모리 사용면에서 더 효율적이다.

박싱된 기본 타입에 = 연산자를 사용하면 오류가 발생한다.
이 오류를 해결하려면 지역변수 2개를 두어 각각 박싱된 Integer 매개변수의 값을 기본 타입 정수로 저장한 다음, 모든 비교를 이 기본 타입 변수로 수행해야 한다. 
실무에서 기본 타입을 다루는 비교자가 필요하면 Comparator naturaLorder()를 사용해야 한다. 

기본 타입과 박싱된 기본 타입을 혼용한 연산에서는 박싱된 기본 타입의 박싱이 자동으로 풀린다. 
그리고 null 참조를 언박싱하면 NullPointerException이 발생한다.

박싱된 기본 타입을 사용하는 경우
1. 컬렉션의 원소, 키, 값으로 쓴다. <br>매개변수화 타입이나 매개변수화 메서드의 타입 매개변수로는 박싱된 기본 타입을 써야 한다. 
2. 리플렉션을 통해 메서드를 호출할 때도 박싱된 기본 타입을 사용해야 한다.