/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.crypt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotationProcessor {

    public interface FieldAnnotationAction {

        void process(Object object, Field field);

    }

    public void processFieldAnnotation(final Object object, final Class<? extends Annotation> annotationClass, final FieldAnnotationAction action) {
        final Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            final Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation fieldAnnotation : fieldAnnotations) {
                final boolean annotationPresent = fieldAnnotation.annotationType().isAnnotationPresent(annotationClass);
                if (annotationPresent) {
                    action.process(object, field);
                }
            }
        }
    }

}
