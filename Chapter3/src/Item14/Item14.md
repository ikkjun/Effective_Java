# Comparable을 구현할지 고려하라.
## Compareable 인터페이스의 compareTo 메서드와 Object의 equals의 차이점
compareTo는 단순 동치성 비교에 더해 순서까지 비교할 수 있으며, 제네릭하다. 그래서 Comparable을 구현한 객체들의 배열은 다음처럼 쉽게 정렬할 수 있다.
```java
Arrays.sort(a);
```

## Comparable 인터페이스의 구현 
자바 플랫폼 라이브러리의 모든 값 클래스와 열거타입([Item34]())이 Comparable을 구현했다. 알파벳, 숫자, 연대 같이 순서가 명확한 값 클래스를 작성한다면 반드시 Comparable을 구현하는 것이 낫다.

