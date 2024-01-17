# Item 11. equals를 재정의하려면 hashCode도 재정의하라
equals를 재정의한 클래스 모두에서 hashCode도 재정의해야 한다. equals(Object)가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야 한다. 즉, 논리적으로 같은 객체는 같은 해시코드를 반환해야 한다. 그렇지 않으면 프로그램이 제대로 동작하지 않을 것이다.

## 올바른 hashCode 메서드의 모습
```java
// 최악의 (하지만 적법한) hashCode 구현 - 사용 금지!
@Override public int hashCode() { return 42; }
```
이 코드는 모든 객체에게 똑같은 값만 반환하므로 모든 객체가 해시테이블의 버킷 하나에 담겨 linked list(연결 리스트)처럼 동작한다. 그 결과 평균 수행 시간이 O(1)인 해시테이블이 O(n)으로 느려져서 객체가 많아지면 쓸 수가 없다.

### 좋은 hashCode 작성 요령
좋은 해시 함수라면 서로 다른 인스턴스에 다른 해시코드를 반환한다.
1. int 변수 result를 선언한 후 값 c로 초기화한다.
2. 해당 객체의 나머지 핵심  필드 f 각각에 대해 다음 작업을 수행한다.
   1. 해당 필드의 해시코드 c를 계산한다.
      1. 기본 타입 필드 </br>
      Type.hashCode(f)를 수행한다. 여기서 Type은 해당 기본 타입의 박싱 클래스다.
      2. 참조 타입 필드면서 이 클래스의 equals메서드가 이 필드의 equals를 재귀적으로 호출해 비교하는 경우</br>
         - 필드의 hashCode를 재귀적으로 호출
         - 필드의 표준형(canonical representation)을 만들어 그 표준형의 hashCode를 호출
         - 필드의 값이 null이면 0을 사용
      3. 필드가 배열인 경우
         - 핵심 원소 각각을 별도 필드처럼 다룬다.
         - 배열에 핵심 원소가 하나도 없다면 상수(0)을 사용
         - 모든 원소가 핵심 원소라면 Arrays.hashCode를 사용한다.
   2. 2.i에서 계산한 해시코드 c로 result를 갱신한다.</br> result = 31 * result + c;
3. result를 반환한다.

파생 필드는 해시코드 계산에서 제외해도 된다. 즉, 다른 필드로부터 계산해낼 수 있는 필드는 모두 무시해도 된다. 또한 equals 비교에 사용되지 않은 필드는 '반드시' 제외해야 한다.
곱할 숫자를 31로 정한 이유는 31이 홀수이면서 소수(prime)이기 때문이다. 만약 이 숫자가 짝수이고 오버플로가 발생한다면 정보를 잃게 된다.

```java
// 코드 11-2 전형적인 hashCode 메서드
@Override public int hashCode() {
int result = Short.hashCode(areaCode);
result = 31 * result + Short.hashCode(prefix);
result = 31 * result + Short.hashCode(lineNum);
return result;
}
```
코드 11-2의 메서드는 PhoneNumber 인스턴스의 핵심 필드 3개만을 사용해 간단한 계산만 수행한다. 해시 충돌이 더욱 적인 방법을 꼭 써야 한다면 구아바의 com.google.common.hash.Hashing을 참고하면 된다.</br></br>
Objects 클래스는 임의의 개수만큼 객체를 받아 해시코드를 계산해주는 정적 메서드인 hash를 제공한다. 하지만 아쉽게도 속도는 더 느리다.
```java
// 코드 11-3 한 줄짜리 hashCode 메서드 - 성능이 살짝 아쉽다.
@Override public int hashCode() {
    return Objects.hash(lineNum, prefix, areaCode);
}
```

클래스가 불변이고 해시코드를 계산하는 비용이 크다면, 매번 새로 계산하기 보다 캐싱하는 방식을 고려해야 한다. 
- 이 타입의 객체가 주로 해시의 키로 사용되는 경우 </br> 인스턴스가 만들어질 때 해시코드를 계산해둬야 한다. 
- 해시의 키로 사용되지 않는 경우 </br> hashCode가 처음 불릴 때 계산하는 지연초기화(lazy initialization) 전략을 사용해야 한다. hashCode 필드의 초깃값은 흔히 생성되는 객체의 해시코드와는 달라야 한다. 

```java
// 코드 11-4 해시코드를 지연 초기화하는 hashCode 메서드 - 스레드 안정성까지 고려해야 한다.
private int hashCode;

@Override public int hashCode() {
   int result = hashCode;
   if (result == 0) {
      result = Short.hashCode(areaCode);
      result = 31 * result + Short.hashCode(prefix);
      result = 31 * result + Short.hashCode(lineNum);
      hashCode = result;
   }
   return result;
}
```

## hashCode 생성시 주의사항
- 성능을 높이기 위해 핵심 필드를 생략해서 해시 코드를 계산하면 안 된다.
- hashCode가 반환하는 값의 생성 규칙을 API 사용자에게 자세히 공표하지 말자. </br> 그래야 클라이언트가 이 값에 의지하지 않게 되고, 추후에 계산 방식을 바꿀 수 있다.