# try_finally. try-finally보다는 try-with-resources를 사용하라.
자바 라이브러리에는 close메서드를 호출해 직접 닫아줘야 하는 자원이 많다. InputStream, OutputStream, java.sql.Connection 등이 좋은 예다. 전통적으로 자원이 제대로 닫힘을 보장하는 수단으로 try-finally가 쓰였다. 그러나 자원이 둘 이상이면 try-finally 방식은 너무 지저분하다.

## try-finally문의 문제점
예외는 try블록과 finally블록 모두에서 발생할 수 있다. 그러나 두 번재 예외가 첫 번째 예외를 완전히 집어삼켜 버리면 스택의 추적 내역에 관한 정보는 남지 않아서 실제 시스템에서의 디버깅을 어렵게 한다.</br>
실전에서는 프로그래머에게 보여줄 예외 하나만 보존되고 여러 개의 다른 예외가 숨겨질 수도 있다.

## try-finally문의 해결방안
### 1. try-with-resources문
이 구조를 사용하려면 해당 자원이 AutoCloseable 인터페이스를 구현해야 한다.

try-with-resources 버전이 짧고 읽기 쉬울 뿐만 아니라 문제를 진단하는 데에도 유용하다.

### 2. try-with-resource + catch절