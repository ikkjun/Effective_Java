# @Override 애너테이션을 일관되게 사용하라.
재정의한 모든 메서드에 @Override 애너테이션을 의식적으로 달면 실수했을 때 컴파일러가 바로 알려준다.

## 예외
구체 클래스에서 상위 클래스의 추상메서드를 재정의한 경우에는 이 에너테이션을 달지 않아도 된다.