# 복구할 수 있는 상황에는 검사 예외를 프로그래밍 오류에는 런타임 예외를 사용하라.
자바는 문제 상황을 알리는 타입(throwable)으로 검사 예외, 런타임 예외, 에러를 제공한다.
## 1. 호출하는 쪽에서 복구하리라 여겨지는 상황이라면 검사 예외를 사용하라.
이것이 검사와 비검사 예외를 구분하는 기본 규칙이다. 
검사 예외를 던지면 호출자가 그 예외를 catch로 잡아 처리하거나 더 바깥으로 전파하도록 강제한다. 

비검사 throwable은 두 가지로, 바로 런타임 예외와 에러다. 
이 둘은 프로그램에서 잡을 필요가 없거나 혹은 통상적으로는 잡지 말아야 한다. 
throwable을 잡지 않은 스레드는 적절한 오류 메시지를 내뱉으며 중단된다.

## 2. 프로그래밍 오류를 나타낼 때는 런타임 예외를 사용하자.
복구할 수 있다고 생각하면 검사 예외를, 그렇지 않다면 런타임 예외를 사용하면 된다. 
확신하기 어렵다면 비검사 예외를 선택하는 편이 낫다(Item71).  

우리가 구현하는 비검사 throwable은 모두 RuntimeException의 하위 클래스여야 한다. 
Exception, RuntimeException, Error를 상속하지 않는 throwable을 만들 수도 있다. 
그러나 throwable은 장점이 없어서 사용하지 않는 것이 좋다.
throwable은 정상적인 검사 예외보다 나을 게 하나도 없으면서 API 사용자를 헷갈리게 할 뿐이다.