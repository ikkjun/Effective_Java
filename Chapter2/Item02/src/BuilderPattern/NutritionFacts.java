package BuilderPattern;

public class NutritionFacts {
    private final int servingSize; // (ml, 1회 제공량)  필수
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbonbydrate;

    public static class Builder {
        // 필수 매개변수
        private final int servingSize;
        private final int servings;

        // 선택 매개변수 - 기본값으로 초기화한다.
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbonhydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val)
            {calories = val;    return this;}
        public Builder fat(int val)
            {fat = val;    return this;}
        public Builder sodium(int val)
            {sodium = val;    return this;}
        public Builder carbohydrate(int val)
            {carbonhydrate = val;    return this;}

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    public NutritionFacts(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbonbydrate = builder.carbonhydrate;
    }
