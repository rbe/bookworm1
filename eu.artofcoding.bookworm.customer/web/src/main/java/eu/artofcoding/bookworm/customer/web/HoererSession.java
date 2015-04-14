/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.customer.web.persistence.MyData;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
public class HoererSession implements Serializable {

    @Inject
    private MyData myData;

    /*
    @PrePassivate
    public void prePassivate() {
        System.out.println(this + ": prePassivate");
    }

    @PostActivate
    public void postActivate() {
        System.out.println(this + ": postActivate");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println(this + ": preDestroy");
    }

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        System.out.println(this + ": " + ctx.getMethod().getName());
        return ctx.proceed();
    }
    */

    public MyData getMyData() {
        return myData;
    }

}
