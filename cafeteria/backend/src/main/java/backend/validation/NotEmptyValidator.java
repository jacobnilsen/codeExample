package backend.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Jacob on 07.06.2017.
 */
public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {
   public void initialize(NotEmpty constraint) {
   }

   public boolean isValid(String obj, ConstraintValidatorContext context) {
      return false;
   }
}
