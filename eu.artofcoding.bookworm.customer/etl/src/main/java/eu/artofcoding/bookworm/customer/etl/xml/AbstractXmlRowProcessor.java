/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.etl.xml;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.etl.xml.XmlRow;
import eu.artofcoding.bookworm.common.etl.xml.XmlRowProcessor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class AbstractXmlRowProcessor<T extends GenericEntity> implements XmlRowProcessor<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractXmlRowProcessor.class.toString());

    @PersistenceContext
    protected EntityManager entityManager;

    private String buildErrorMessage(final Iterator<ConstraintViolation<T>> iterator) {
        final StringBuilder errorMessages = new StringBuilder();
        while (iterator.hasNext()) {
            final ConstraintViolation constraintViolation = iterator.next();
            errorMessages.append("'").append(constraintViolation.getPropertyPath().toString()).append("' ");
            errorMessages.append(constraintViolation.getMessage());
            if (iterator.hasNext()) {
                errorMessages.append(", ");
            }
        }
        return errorMessages.toString();
    }

    protected Set<ConstraintViolation<T>> validate(final T entity) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        return validator.validate(entity);
    }

    @Override
    @Transactional
    public T validateAndMerge(final T entity) {
        final Set<ConstraintViolation<T>> violations = validate(entity);
        if (violations.size() == 0) {
            final T mergedEntity = entityManager.merge(entity);
            entityManager.flush();
            return mergedEntity;
        } else {
            final Iterator<ConstraintViolation<T>> iterator = violations.iterator();
            if (iterator.hasNext()) {
                final String errorMessages = buildErrorMessage(iterator);
                LOGGER.warning(String.format("There are %d validation errors for %s: %s", violations.size(), entity.getClass().getSimpleName(), errorMessages));
            }
            return entity;
        }
    }

    @Override
    @Transactional
    public List<T> validateAndMerge(final List<T> entities) {
        final List<T> mergedEntities = new ArrayList<>();
        for (T entity : entities) {
            mergedEntities.add(validateAndMerge(entity));
        }
        return mergedEntities;
    }

    @Override
    public T xmlRowToEntity(XmlRow xmlRow) {
        throw new UnsupportedOperationException();
    }

}
