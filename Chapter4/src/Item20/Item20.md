# Item20. 추상 클래스보다는 인터페이스를 우선하라.
다중 구현용 타입으로는 인터페이스가 가장 적합하다. 
복잡한 인터페이스라면 구현하는 수고를 덜어주는 골격 구현을 함께 제공하는 방법을 생각해볼 필요가 있다.
골격 구현은 '가능한 한' 인터페이스의 디폴트 메서드로 제공하여 그 인터페이스를 구현한 모든 곳에서 활용하도록 하는 것이 좋다.
'가능한 한'이라고 한 이유는 인터페이스에 걸려 있는 구현상의 제약 때문에 골격 구현을 추상클래스로 제공하는 경우가 더 흔하기 때문이다.