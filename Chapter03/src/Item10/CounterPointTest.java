package Item10;

import org.w3c.dom.css.Counter;

import java.util.*;

public class CounterPointTest {
    // 단위 원 안의 모든 점을 포함하도록 unitCircle을 초기화한다.
    private static final Set<Point> unitCircle = Set.of(
            new Point( 1,  0), new Point( 0,  1),
            new Point(-1,  0), new Point( 0, -1));

    public static boolean onUnitCircle(Point p) {
        return unitCircle.contains(p);
    }

    public static void main(String[] args) {
        Point p1 = new Point(1,0);
        Point p2 = new CounterPoint(1, 0);

        System.out.println(onUnitCircle(p1));
        System.out.println(onUnitCircle(p2));
    }
}
