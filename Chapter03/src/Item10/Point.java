package Item10;

import java.awt.*;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

//    색상 정보는 무시한 채 비교 수행
//    @Override public boolean equals(Object o) {
//        if (!(o instanceof Point)) return false;
//        Point p = (Point)o;
//        return p.x == x&&p.y == y;

//    코드 10-2 잘못된 코드 - 대칭성 위배!
//    @Override public boolean equals(Object o) {
//        if(!(o instanceof ColorPoint))
//            return false;
//        return super.equals(o) && ((ColorPoint) o).color == color;
//    }

//    // 코드 10-3 잘못된 코드 - 추이성 위배!
//    @Override public boolean equals(Object o) {
//        if (!(o instanceof Point)) return false;
//
//        // o가 일반 Point면 색상을 무시하고 비교한다.
//        if (!(o instanceof ColorPoint)) return o.equals(this);
//
//        // o가 ColorPoint면 색상까지 비교한다.
//        return super.equals(o) && ((ColorPoint) o).color == color;
//    }

//    // 코드 10-4 잘못된 코드 - 리스코프 치환 원칙(59쪽) 위배!
//    @Override public boolean equals(Object o) {
//        if(o==null || o.getClass() != getClass())
//            return false;
//        Point p = (Point) o;
//        return p.x == x && p.y == y;
//    }
}

class ColorPoint extends Point {
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }
}
