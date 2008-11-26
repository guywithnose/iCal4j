/*
 * $Id$
 *
 * Created on 12/11/2005
 *
 * Copyright (c) 2005, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.model;

import junit.framework.TestCase;

import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.util.CompatibilityHints;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Unit tests for <code>Component</code> base class.
 * @author Ben Fortuna
 */
public abstract class ComponentTest extends TestCase {

    private static final Log LOG = LogFactory.getLog(ComponentTest.class);

    protected Component component;
    
    /**
     * @param component
     */
    public ComponentTest(String testMethod, Component component) {
        super(testMethod);
    	this.component = component;
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION, false);
    }
    
    /**
     * Test whether the component is a calendar component.
     */
    public final void testIsCalendarComponent() {
        assertIsCalendarComponent(component);
    }
    
    /**
     * @param c
     */
    protected void assertIsCalendarComponent(final Component c) {
        assertTrue("Component is not a calendar component", (c instanceof CalendarComponent));
    }
    
    /**
     * Test whether the component is a calendar component.
     */
    public final void testIsNotCalendarComponent() {
        assertIsNotCalendarComponent(component);
    }
    
    /**
     * @param c
     */
    protected void assertIsNotCalendarComponent(final Component c) {
        assertFalse("Component is a calendar component", (c instanceof CalendarComponent));
    }
    
    /**
     * Test component validation.
     */
    public final void testValidation() throws ValidationException {
        component.validate();
    }
    
    /**
     * Test component validation.
     */
    public final void testRelaxedValidation() throws ValidationException {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION, true);
        component.validate();
    }
    
    /**
     * 
     */
    public final void testValidationException() {
        assertValidationException(component);
    }
    
    /**
     * @param component
     */
    protected void assertValidationException(final Component component) {
        try {
            component.validate();
            fail("ValidationException should be thrown!");
        }
        catch (ValidationException ve) {
            LOG.debug("Exception caught", ve);
        }
    }
}
