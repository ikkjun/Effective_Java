# finalizer와 cleaner 사용을 피하라.

자바는 두 가지 객체 소멸자를 제공한다. 그 중 finalizer는 예측할 수 없고, 상황에 따라 위험할 수 있어 일반적으로 불필요하다. 그래서 자바9에서는 finalizer를 deprecated API로 지정하고 cleaner를 그 대안으로 소개했다. cleaner는 finalizer보다는 덜 위험하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.

finalizer와 cleaner는 즉시 수행된다는 보장이 없다. 즉, finalizer와 cleaner로는 제때 실행되어야 하는 작업은 절대 할 수 없다. finalizer 스레드의 낮은 우선순위로 인한 문제를 예방할 수 있는 해법은 오직 finalizer를 사용하지 않는 것이다. 이와 달리 cleaner는 자신을 수행할 스레드를 제어할 수 있다는 면에서 조금 낫다. 그러나 여전히 백그라운드에서 수행되며 가비지 컬렉터의 통제하에 있으므로 바로 수행된다는 보장은 없다.

따라서 프로그램 생애주기와 상관 없는, 상태를 영구적으로 수정하는 작업에서는 절대 finalizer나 cleaner에 의존해서는 안 된다.

