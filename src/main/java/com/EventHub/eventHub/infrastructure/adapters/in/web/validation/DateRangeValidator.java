package com.EventHub.eventHub.infrastructure.adapters.in.web.validation;

import com.EventHub.eventHub.infrastructure.adapters.in.web.dto.EventDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRange, EventDTO> {

    @Override
    public void initialize(DateRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(EventDTO eventDTO, ConstraintValidatorContext context) {
        if (eventDTO.getStartDate() == null || eventDTO.getEndDate() == null) {
            return true;
        }
        return !eventDTO.getEndDate().isBefore(eventDTO.getStartDate());
    }
}
