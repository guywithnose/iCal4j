/*
 * Copyright (c) 2004, Ben Fortuna
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
package net.fortuna.ical4j.util;

import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.ValidationException;

/**
 * $Id$ [15-May-2004]
 *
 * Defines methods for validating properties and property lists.
 *
 * @author Ben Fortuna
 */
public final class PropertyValidator {

    private static PropertyValidator instance = new PropertyValidator();

    /**
     * Constructor made private to enforce singleton.
     */
    private PropertyValidator() {
    }

    /**
     * Ensure a property occurs no more than once.
     *
     * @param propertyName
     *            the property name
     * @param properties
     *            a list of properties to query
     * @throws ValidationException
     *             when the specified property occurs more than once
     */
    public void assertOneOrLess(final String propertyName,
            final PropertyList properties) throws ValidationException {

        if (properties.getProperties(propertyName).size() > 1) {
            throw new ValidationException(
                "Property [" + propertyName + "] must only be specified once");
        }
    }

    /**
     * Ensure a property occurs at least once.
     *
     * @param propertyName
     *            the property name
     * @param properties
     *            a list of properties to query
     * @throws ValidationException
     *             when the specified property occurs more than once
     */
    public void assertOneOrMore(final String propertyName,
            final PropertyList properties) throws ValidationException {

        if (properties.getProperties(propertyName).size() < 1) {
            throw new ValidationException(
                "Property [" + propertyName
                        + "] must be specified at least once");
        }
    }

    /**
     * Ensure a property occurs once.
     *
     * @param propertyName
     *            the property name
     * @param properties
     *            a list of properties to query
     * @throws ValidationException
     *             when the specified property does not occur once
     */
    public void assertOne(final String propertyName,
            final PropertyList properties) throws ValidationException {

        if (properties.getProperties(propertyName).size() != 1) {
            throw new ValidationException(
                "Property [" + propertyName + "] must be specified once");
        }
    }
    
    /**
     * Ensure a property doesn't occur in the specified list.
     * @param propertyName the name of a property
     * @param properties a list of properties
     * @throws ValidationException thrown when the specified property
     * is found in the list of properties
     */
    public void assertNone(final String propertyName, final PropertyList properties) throws ValidationException {
        if (properties.getProperties(propertyName).size() > 0) {
            throw new ValidationException(
                "Property [" + propertyName + "] is not applicable");
        }
    }

    /**
     * @return Returns the instance.
     */
    public static PropertyValidator getInstance() {
        return instance;
    }
}
