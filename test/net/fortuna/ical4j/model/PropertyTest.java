/*
 * $Id$
 *
 * Created on 22/10/2006
 *
 * Copyright (c) 2006, Ben Fortuna
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import junit.framework.TestSuite;

import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.XProperty;

/**
 * Unit tests for Property-specific functionality.
 * @author Ben Fortuna
 */
public class PropertyTest extends AbstractPropertyTest {
    
    private Property property;
    
    private String expectedValue;
    
    /**
     * @param property
     */
    public PropertyTest(Property property) {
        super("testEquals");
        this.property = property;
    }
    
    /**
     * @param property
     * @param expectedValue
     */
    public PropertyTest(Property property, String expectedValue) {
        super("testGetValue");
        this.property = property;
        this.expectedValue = expectedValue;
    }
    
    /**
     * 
     */
    public void testGetValue() {
        assertEquals(expectedValue, property.getValue());
    }
    
    /**
     * Test equality of properties.
     */
    public void testEquals() {
        assertTrue(property.equals(property));
        
        Property notEqual = new Property("notEqual") {
            public String getValue() {
                return "";
            }
            public void setValue(String value) throws IOException,
                    URISyntaxException, ParseException {
            }
            public void validate() throws ValidationException {
            }
        };
        
        assertFalse("Properties are equal", property.equals(notEqual));
        assertFalse("Properties are equal", notEqual.equals(property));
    }
    
    /**
     * Test deep copy of properties.
     */
    public void testCopy() throws IOException, URISyntaxException, ParseException {
        Property copy = property.copy();
        assertEquals(property, copy);
        
        copy.getParameters().add(Value.BOOLEAN);
        assertFalse(property.equals(copy));
        assertFalse(copy.equals(property));
    }
    
    /**
     * @return
     */
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new PropertyTest(new XProperty("X-extended")));
        suite.addTest(new PropertyTest(new XProperty("X-extended", "value"), "value"));
        return suite;
    }
}
