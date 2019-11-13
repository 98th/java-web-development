package by.training.module1.validator;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    private Map<String, String> result;

    ValidationResult() {
        result = new HashMap<>();
    }

    public boolean isValid () {
        return result.isEmpty();
    }

    public Map<String, String> getResult() {
        return result;
    }

    public void addMessage(String key, String message) {
        result.put(key, message);
    }

}
