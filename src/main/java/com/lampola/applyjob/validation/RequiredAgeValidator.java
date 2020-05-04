/*
 * (C) Markus Lampola 2016
 */
package com.lampola.applyjob.validation;

import java.util.Calendar;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Markus Lampola
 */
public class RequiredAgeValidator implements ConstraintValidator<RequiredAge, Date> {

    @Override
    public final void initialize(final RequiredAge annotation) {
    }

    @Override
    public final boolean isValid(final Date value,
            final ConstraintValidatorContext context) {

        if (value != null) {
            Date now = new Date();
            
            Calendar c100 = Calendar.getInstance(); // 100 years old
            c100.setTime(now);
            c100.add(Calendar.YEAR, -100);

            Calendar c15 = Calendar.getInstance();  // 15 years old
            c15.setTime(now);
            c15.add(Calendar.YEAR, -15);

            return value.after(c100.getTime()) && value.before(c15.getTime());
        } else {
            return false;
        }
    }
}
