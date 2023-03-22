package uswo.inc.uswofinal.controller;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



public class WknoValidator implements Validator {
    
    private static final String WKNO_PATTERN = "^[0-9]{2}-[0-9]{4}$";
    
    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        String wkno = (String) target;
        if (!Pattern.matches(WKNO_PATTERN, wkno)) {
            errors.rejectValue("wkno", "Invalid week number format. Please use 'ww-yyyy' format.");
        }
    }
}

