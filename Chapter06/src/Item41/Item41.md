# 정의하려는 것이 타입이라면 마커 인터페이스를 사용하라.
## 마커 인터페이스 사용 시기
새로 추가하는 메서드 없이 단지 타입 정의가 목적인 경우. 적용 대상이 ElementType.TYPE인 마커 애너테이션을 작성하고 있다면 마커 인터페이스를 작성하라.
## 마터 애너테이션 사용 시기
클래스나 인터페이스 외의 프로그램 요소에 마킹해야 하거나, 애너테이션을 적극 활용하는 프레임워크의 일부로 그 마커를 포함시키려고 할 때.