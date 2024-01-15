# Chapter3. 모든 객체의 공통 메서드
Object에서 final이 아닌 메서드(equals, hashCode, toString, clone, finalize)는 모두 overriding을 염두해두고 설계되어서 재정의할 때 지켜야 하는 일반 규약이 정의되어 있다. 그래서 Object를 상속하는 모든 클래스는 