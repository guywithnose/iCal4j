/*
 * $Id$
 *
 * Created on 8/08/2005
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
package net.fortuna.ical4j.model.property;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.ValidationException;

/**
 * Superclass for all properties with date-time values that must be specified in UTC time.
 * @author Ben Fortuna
 */
public abstract class UtcProperty extends DateProperty {

    /**
     * 
     */
    private static final long serialVersionUID = 4850079486497487938L;

    /**
     * @param name
     * @param parameters
     */
    public UtcProperty(final String name, final ParameterList parameters) {
        super(name, parameters);
        setDate(new DateTime(true));
    }

    /**
     * @param name
     */
    public UtcProperty(final String name) {
        super(name);
        setDate(new DateTime(true));
    }

    /**
     * @return Returns the date-time.
     */
    public final DateTime getDateTime() {
        return (DateTime) getDate();
    }

    /**
     * @param dateTime The dateTime to set.
     */
    public void setDateTime(final DateTime dateTime) {
        // time must be in UTC..
        if (dateTime != null) {
            dateTime.setUtc(true);
        }
        setDate(dateTime);
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.model.property.DateProperty#setTimeZone(net.fortuna.ical4j.model.TimeZone)
     */
    public void setTimeZone(TimeZone timezone) {
        throw new UnsupportedOperationException("Cannot set timezone for UTC properties");
    }
    
    /*
     * (non-Javadoc)
     * @see net.fortuna.ical4j.model.Property#validate()
     */
    public void validate() throws ValidationException {
        super.validate();

        if (getDate() != null && !(getDate() instanceof DateTime)) {
            throw new ValidationException(
                    "Property must have a DATE-TIME value");
        }

        DateTime dateTime = (DateTime) getDate();

        if (dateTime != null && !dateTime.isUtc()) {
            throw new ValidationException(
                    "DATE-TIME value must be specified in UTC time");
        }
    }
}
