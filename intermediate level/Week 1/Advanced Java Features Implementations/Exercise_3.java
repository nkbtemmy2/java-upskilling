class DataValidator {
    // Generic method to validate data based on type
    public static <T> boolean validate(T data, Validator<T> validator) {
        return validator.isValid(data);
    }

    // Interface for validators
    interface Validator<T> {
        boolean isValid(T data);
    }

    // Example validators
    static class StringLengthValidator implements Validator<String> {
        private int minLength;

        public StringLengthValidator(int minLength) {
            this.minLength = minLength;
        }

        @Override
        public boolean isValid(String data) {
            return data != null && data.length() >= minLength;
        }
    }

    static class NumberRangeValidator implements Validator<Number> {
        private double min;
        private double max;

        public NumberRangeValidator(double min, double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean isValid(Number data) {
            return data != null && data.doubleValue() >= min && data.doubleValue() <= max;
        }
    }
}