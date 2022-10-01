package com.swisscom.conf;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomDateValidator implements
ConstraintValidator<CustomDateConstraint, String> {

  private static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

  @Override
  public void initialize(CustomDateConstraint customDate) {
  }

  @Override
  public boolean isValid(String customDateField,
    ConstraintValidatorContext cxt) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
         try
      {
          sdf.setLenient(false);
          Date d = sdf.parse(customDateField);
          return true;
      }
      catch (Exception e)
      {
          return false;
      }
  }

}