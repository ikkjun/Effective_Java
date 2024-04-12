# Item 72. 표준 예외를 사용하라
## 표준 예외를 사용할 때의 장점
1. 자신이 설계한 API가 다른 사람이 익히고 사용하기 쉬워진다.
2. API를 사용한 프로그램도 낯선 예외를 사용하지 않게 되어 읽기 쉽게 된다. 
3. 예외 클래스 수가 적을수록 메모리 사용량도 줄고 클래스를 적재하는 시간도 적게 걸린다.

## 표준 예외의 종류
- IllegalArgumentException: 호출자가 인수로 부적절한 값을 넘길 때
- IllegalStateException: 대상 객체의 상태가 호출된 메서드를 수행하기에 적합하지 않을 때 
- NullPointerException: null 값을 허용하지 않는 메서드에 null이 들어올 때
- IndexoutofBoundsException: 어떤 시퀀스의 허용 범위를 넘는 값을 건넬 때
- ConcurrentModificationException: 단일 스레드에서 사용하려고 설계한 객체를 여러 스레드가 동시에 수정하려 할 때
- UnsupportedoperationException: 클라이언트가 요청한 동작을 대상 객체가 지원하지 않을 때 


Exception, RuntimeException, ThrowabLe, Error는 직접 재사용하면 안 된다. 이 예외들은 여러 성격의 예외들을 포괄하는 클래스이므로 안정적으로 테스트할 수 없다.

상황에 부합한다면 항상 표준 예외를 재사용하자. 
이때 API 문서를 참고해 그 예외가 어떤 상황에서 던져지는지 확인해야 한다.

인수 값이 무엇이었든 어차피 실패했을 거라면 ILLegalStateException을, 
그렇지 않으면 ILLegalArgumentException을 던져야 한다.