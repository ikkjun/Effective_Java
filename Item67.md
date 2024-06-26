# Item 67. 최적화는 신중히 하라
최적화는 좋은 결과보다는 해로운 결과로 이어지기 쉽다.
성능 때문에 견고한 구조를 희생하지 말자. 빠른 프로그램보다는 좋은 프로그램을 작성해야 한다. 
좋은 프로그램은 정보 은닉 원칙을 따르므로 개별 구성요소의 내부를 독립적으로 설계할 수 있다.

아키텍처의 결함이 성능을 제한하는 상황이라면 시스템 전체를 다시 작성해서 해결할 수도 있다.
따라서 설계 단계에서 성능을 반드시 고려해야 한다.

성능을 제한하는 설계를 피해야 한다.
특히 API를 설계할 때 성능에 주는 영향을 고려해야 한다.

잘 설계된 API는 성능도 좋은 게 보통이다. 
그래서 성능을 위해 API를 왜곡하는 건 매우 좋지 않다. 

## 최적화 규칙
1. "하지 마라"
2. "(전문가 한정) 아직 하지 마라"
3. "각각의 최적화 시도 전후로 성능을 측정하라" 

프로파일링 도구(profiling tool)는 최적화 노력을 어디에 집중해야 할지 찾는 데 도움을 준다. 
JMH는 프로파일러는 아니지만 자바 코드의 상세한 성능을 알기 쉽게 보여주는 마이크로 벤치마킹 프레임워크다.

프로그래머가 작성하는 코드와 CPU에서 수행하는 명령 사이의 '추상화 격차'가 커서 최적화로 인한 성능 변화를 일정하게 예측하기 어렵다.