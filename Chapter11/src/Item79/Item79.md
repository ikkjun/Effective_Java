# Item 79. 과도한 동기화는 피하라
과도한 동기화는 성능을 떨어뜨리고, 교착상태에 빠뜨리고, 심지어 예측할 수 없는 동작을 낳기도 한다.
웅답 불가와 안전 실패를 피하려면 동기화 메서드나 동기화 블록 안에서는 제어를 절대로 클라이언트에 양도하면 안 된다. 
외계인 메서드(alien method)가 하는 일에 따라 동기화된 영역은 예외를 일으키거나, 교착상태에 빠지거나, 데이터를 훼손할 수도 있다.

실제 시스템(특히 GUI 툴킷)에서도 동기화된 영역 안에서 외계인 메서드를 호출하여 교착상태에 빠지는 사례가 자주 있다.

## reentrant lock의 장단점
자바 언어의 락은 재진입(reentrant)을 허용하므로 교착상태에 빠지지는 않는다.
그러나 재진입 가능 락은 객체 지향 멀티스레드 프로그램을 쉽게 구현할 수 있도록 해주지만, 응답 불가(교착상태)가 될 상황을 안전 실패(데이터 훼손)로 변모시킬 수도 있다.

## 해결책
외계인 메서드 호출을 동기화 블록 바깥으로 옮기면 된다.
자바의 동시성 컬렉션 라이브러리의 CopyOnwriteArrayList가 이 목적으로 특별히 설계된 것이다. 

동기화 영역 바깥에서 호출되는 외계인 메서드를 열린 호출 (open call)이라 한다.
열린 호출은 실패 방지 효과 외에도 동시성 효율을 크게 개선해준다.
기본 규칙은 동기화 영역에서는 가능한 한 일을 적게 하는 것이다. 

락을 얻 고, 공유 데이터를 검사하고, 필요하면 수정하고, 락을 놓는다. 오래 걸리는 작 업이라면 아이템 78의 지침을 어기지 않으면서 동기화 영역 바깥으로 옮기는 방법을 찾아보자.
자, 지금까지 정확성에 관해 이야기했으니 이제 성능 측면도 간단히 살펴보 자. 자바의 동기화 비용은 빠르게 낮아져 왔지만, 과도한 동기화를 피하는 일 은 오히려 과거 어느 때보다 중요하다. 멀티코어가 일반화된 오늘날, 과도한 동기화가 초래하는 진짜 비용은 락을 얻는 데 드는 CPU 시간이 아니다. 바로 경쟁하느라 낭비하는 시간, 즉 병렬로 실행할 기회를 잃고, 모든 코어가 메모 리를 일관되게 보기 위한 지연시간이 진짜 비용이다. 가상머신의 코드 최적화 를 제한한다는 점도 과도한 동기화의 또 다른 숨은 비용이다.
가변 클래스를 작성하려거든 다음 두 선택지 중 하나를 따르자. 첫 번째, 동