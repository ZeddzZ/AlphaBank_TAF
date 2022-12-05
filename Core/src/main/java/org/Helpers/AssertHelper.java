package org.Helpers;

import java.util.HashMap;
import java.util.Map;

public class AssertHelper {
    /**
     * Takes multiple asserts and run them all, even if one of them failed
     * As a result will throw new AssertionError with message contains all
     * the assertions failed during execution in format '%Number_of_Assert_in_method_params%. Assertion_Message'
     * @param asserts List of asserts to check in one time
     */
    public static void AssertMultiple(Runnable... asserts) {
        Map<Integer,AssertionError> errors = new HashMap<>();
        for(int i = 0; i < asserts.length; i++) {
            try {
                asserts[i].run();
            } catch (AssertionError error) {
                errors.put(i + 1, error);
            }
        }
        if (errors.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for(Integer assertNumber: errors.keySet()) {
                sb.append(String.format("%d. %s", assertNumber, errors.get(assertNumber).getMessage()));
                sb.append("\n");
            }
            throw new AssertionError(sb.toString());
        }
    }
}
