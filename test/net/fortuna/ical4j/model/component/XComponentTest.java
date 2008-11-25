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
package net.fortuna.ical4j.model.component;

import junit.framework.TestSuite;
import net.fortuna.ical4j.model.ComponentTest;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.CompatibilityHints;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Unit tests for <code>XComponent</code>
 * @author Ben Fortuna
 */
public class XComponentTest extends ComponentTest {

    private static Log log = LogFactory.getLog(XComponentTest.class);
    
    /**
     * @param testMethod
     * @param component
     */
    public XComponentTest(String testMethod, XComponent component) {
        super(testMethod, component);
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION,
                false);
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.model.ComponentTest#testIsCalendarComponent()
     */
    public void testIsCalendarComponent() {
        assertIsCalendarComponent(component);
    }
    
    /**
     * Test experimental component validation.
     */
    public void testValidation() throws ValidationException {
        component.validate();
        
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION,
                true);
        new XComponent("TEST").validate();
    }
    
    /**
     * 
     */
    public void testValidationException() {
        assertValidationException(component);
    }
    
    /**
     * @return
     */
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        
        suite.addTest(new XComponentTest("testIsCalendarComponent", new XComponent("X-TEST")));
        suite.addTest(new XComponentTest("testValidation", new XComponent("X-TEST")));
        suite.addTest(new XComponentTest("testValidationException", new XComponent("TEST")));
        
        return suite;
    }
}
