/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.hoererimport.as400xml;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.api.xml.XmlRowProcessor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public abstract class AbstractXmlRowProcessor implements XmlRowProcessor {

    protected static final Logger LOGGER = Logger.getLogger(AbstractXmlRowProcessor.class.toString());

    @PersistenceContext
    protected EntityManager entityManager;

    protected <T extends GenericEntity> Set<ConstraintViolation<T>> validate(final T entity) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<T>> errors = validator.validate(entity);
        return errors;
    }

    protected <T extends GenericEntity> void validateAndMerge(final T entity) {
        final Set<ConstraintViolation<T>> violations = validate(entity);
        if (violations.size() == 0) {
            entityManager.merge(entity);
            entityManager.flush();
        } else {
            final StringBuilder errorMessages = new StringBuilder();
            final Iterator<ConstraintViolation<T>> iterator = violations.iterator();
            while (iterator.hasNext()) {
                final ConstraintViolation constraintViolation = iterator.next();
                errorMessages.append("'").append(constraintViolation.getPropertyPath().toString()).append("' ");
                errorMessages.append(constraintViolation.getMessage());
                if (iterator.hasNext()) {
                    errorMessages.append(", ");
                }
            }
            LOGGER.warning(String.format("There are %d validation errors for %s: %s", violations.size(), entity.getClass().getSimpleName(), errorMessages.toString()));
        }
    }

}
