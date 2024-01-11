# finalizer와 cleaner 사용을 피하라.

자바는 두 가지 객체 소멸자를 제공한다. 그 중 finalizer는 예측할 수 없고, 상황에 따라 위험할 수 있어 일반적으로 불필요하다. 그래서 자바9에서는 finalizer를 deprecated API로 지정하고 cleaner를 그 대안으로 소개했다. cleaner는 finalizer보다는 덜 위험하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.

## finalizer와 cleaner의 문제점
1. 인스턴스의 자원 회수 지연</br>
finalizer와 cleaner는 즉시 수행된다는 보장이 없다. finalizer와 cleaner로는 제때 실행되어야 하는 작업은 절대 할 수 없다. finalizer 스레드의 낮은 우선순위로 인한 문제를 예방할 수 있는 해법은 오직 finalizer를 사용하지 않는 것이다. </br>
이와 달리 cleaner는 자신을 수행할 스레드를 제어할 수 있다는 면에서 조금 낫다. 그러나 여전히 백그라운드에서 수행되며 가비지 컬렉터의 통제하에 있으므로 바로 수행된다는 보장은 없다.

2. finalizer 동작 중 발생한 예외는 무시되며, 처리할 작업이 남았더라도 그 순간 종료된다.</br>
보통의 경우엔 잡지 못한 예외가 스레드를 중단시키고 스택 추적 내역을 출력하지만, finalizer에서 발생한 예외는 경고조차 출력하지 않는다.

3. finalizer와 cleaner는 심각한 성능 문제 동반
하지만 안전망 형태로만 사용한다면 훨씬 빨라진다.

4. finalizer를 사용한 클래스는 finalizer 공격에 노출되어 심각한 보안 문제 초래
생성자나 직렬화 과정에서 예외가 발생하면, 이 생성되다 만 객체에서 악의적인 하위 클래스의 finalizer를 수행하여 객체를 만들 수 있다. 객체 생성을 막으려면 생성자에서 예외를 던지는 것만으로 충분하지만, finalizer가 있다면 그렇지 않다. final이 아닌 클래스를 finalizer 공격으로부터 방어하려면 아무 일도 하지 않는 finalize 메서드를 만들고 final로 선언해야 한다.

따라서 프로그램 생애주기와 상관 없는, 상태를 영구적으로 수정하는 작업에서는 절대 finalizer나 cleaner에 의존해서는 안 된다.

## 객체의 클래스에서 finalizer와 cleaner의 대안
AutoCloseable을 구현해주고, 클라이언트에서 인스턴스를 다 쓰고 나면 close 메서드를 호출하면 된다(일반적으로 예외가 발생해도 제대로 종료되도록 try-with-resources를 사용해야 한다).

## cleaner와 finalizer의 용도
### 1. 안전망 역할
자원의 소유자가 close 메서드를 호출하지 않는 것에 대비한 안전망 역할.</br>
자바 라이브러리의 일부 클래스는 안전망 역할의 finalizer를 제공한다. ex) FileInputStream, FileOutputStream, ThreadPoolExcutor

### 2. 네이티브 피어(native peer)와 연결된 객체
네이티브 피어란 일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 네이티브 객체를 말한다.</br>
단, 성능 저하를 감당할 수 있고 네이티브 피어가 심각한 자원을 가지고 있지 않을 때에만 해당된다. 성능 저하를 감당할 수 없거나 네이티브 피어가 사용하는 자원을 즉시 회수해야 한다면 close메서드를 사용해야 한다.

