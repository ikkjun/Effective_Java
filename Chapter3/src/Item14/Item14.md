# Comparable을 구현할지 고려하라.
## Compareable 인터페이스의 compareTo 메서드와 Object의 equals의 차이점
compareTo는 단순 동치성 비교에 더해 순서까지 비교할 수 있으며, 제네릭하다. 그래서 Comparable을 구현한 객체들의 배열은 다음처럼 쉽게 정렬할 수 있다.
```java
Arrays.sort(a);
```