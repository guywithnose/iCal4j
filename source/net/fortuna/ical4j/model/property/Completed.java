/*
 * $Id$
 * 
 * Created: [Apr 6, 2004]
 *
 * Copyright (c) 2004, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 	o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 	o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 	o Neither the name of Ben Fortuna nor the names of any other contributors
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

import java.text.ParseException;
import java.util.Date;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.util.DateTimeFormat;

/**
 * Defines a COMPLETED iCalendar component property.
 *
 * @author benf
 */
public class Completed extends Property {

    /**
     * NOTE: The date and time MUST be in a UTC format.
     */
    private Date time;

    /**
     * @param aValue
     *            a value string for this component
     * @throws ParseException
     *             when the specified string is not a valid date-time
     *             represenation
     */
    public Completed(final String aValue)
            throws ParseException {
        super(COMPLETED);
        time = DateTimeFormat.getInstance().parse(aValue);
    }

    /**
     * @param aList
     *            a list of parameters for this component
     * @param aValue
     *            a value string for this component
     * @throws ParseException
     *             when the specified string is not a valid date-time
     *             represenation
     */
    public Completed(final ParameterList aList, final String aValue)
            throws ParseException {
        super(COMPLETED, aList);
        time = DateTimeFormat.getInstance().parse(aValue);
    }

    /**
     * @param aDate
     *            a date
     */
    public Completed(final Date aDate) {
        super(COMPLETED);
        time = aDate;
    }

    /**
     * @param aList
     *            a list of parameters for this component
     * @param aDate
     *            a date
     */
    public Completed(final ParameterList aList, final Date aDate) {
        super(COMPLETED, aList);

        time = aDate;
    }

    /**
     * @return Returns the time.
     */
    public final Date getTime() {
        return time;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.fortuna.ical4j.model.Property#getValue()
     */
    public String getValue() {
        return DateTimeFormat.getInstance().format(getTime());
    }
}