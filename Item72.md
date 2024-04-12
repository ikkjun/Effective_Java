# Item 72. 표준 예외를 사용하라
## 표준 예외를 사용할 때의 장점
1. 자신이 설계한 API가 다른 사람이 익히고 사용하기 쉬워진다.
2. API를 사용한 프로그램도 낯선 예외를 사용하지 않게 되어 읽기 쉽게 된다. 
3. 예외 클래스 수가 적을수록 메모리 사용량도 줄고 클래스를 적재하는 시간도 적게 걸린다.

## 표준 예외의 종류
- IllegalArgumentException: 호 출자가 인수로 부적절한 값을 넘길 때
- IllegalStateException: 이 예외는 대상 객체의 상태가 호출된 메서드를 수행하기에 적합하지 않을 때 
- NullPointerException: null 값을 허용하지 않는 메서드에 null을 건네면 NuLLPointerException을 던진다. 
- IndexoutofBoundsException: 어떤 시퀀스의 허용 범위를 넘는 값을 건넬 때
- ConcurrentModificationException: 단일 스레드에서 사용하려고 설계한 객체를 여러 스레드가 동시에 수정하려 할 때