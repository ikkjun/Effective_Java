package Item11;

import java.util.Objects;

public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode   = rangeCheck(areaCode,  999, "area code");
        this.prefix     = rangeCheck(prefix,    999, "prefix");
        this.lineNum    = rangeCheck(lineNum,  9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode==areaCode;
    }

    // 전형적인 hashCode 메서드
    @Override public int hashCode() {
        int result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        return result;
    }

    // 코드 11-3 한 줄짜리 hashCode 메서드 - 성능이 살짝 아쉽다.
//    @Override public int hashCode() {
//        return Objects.hash(lineNum, prefix, areaCode);
//    }

//    // 코드 11-4 해시코드를 지연 초기화하는 hashCode 메서드 - 스레드 안정성까지 고려해야 한다.
//    private int hashCode;
//
//    @Override public int hashCode() {
//        int result = hashCode;
//        if (result == 0) {
//            result = Short.hashCode(areaCode);
//            result = 31 * result + Short.hashCode(prefix);
//            result = 31 * result + Short.hashCode(lineNum);
//            hashCode = result;
//        }
//        return result;
//    }
}

/*
질문
1. 핵심 필드 3개의 hashCode를 다 동일하게 설정한 뒤, equals로 비교하는거죠?
2. rangeCheck()에서 short값으로 반환하는 이유는 무엇인가요?
 */