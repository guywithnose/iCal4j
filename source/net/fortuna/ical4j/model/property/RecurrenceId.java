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

import java.text.ParseException;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.util.ParameterValidator;

/**
 * Defines a RECURRENCE-ID iCalendar component property.
 * 
 * <pre>
 * 4.8.4.4 Recurrence ID
 * 
 *    Property Name: RECURRENCE-ID
 * 
 *    Purpose: This property is used in conjunction with the "UID" and
 *    "SEQUENCE" property to identify a specific instance of a recurring
 *    "VEVENT", "VTODO" or "VJOURNAL" calendar component. The property
 *    value is the effective value of the "DTSTART" property of the
 *    recurrence instance.
 * 
 *    Value Type: The default value type for this property is DATE-TIME.
 *    The time format can be any of the valid forms defined for a DATE-TIME
 *    value type. See DATE-TIME value type definition for specific
 *    interpretations of the various forms. The value type can be set to
 *    DATE.
 * 
 *    Property Parameters: Non-standard property, value data type, time
 *    zone identifier and recurrence identifier range parameters can be
 *    specified on this property.
 * 
 *    Conformance: This property can be specified in an iCalendar object
 *    containing a recurring calendar component.
 * 
 *    Description: The full range of calendar components specified by a
 *    recurrence set is referenced by referring to just the "UID" property
 *    value corresponding to the calendar component. The "RECURRENCE-ID"
 *    property allows the reference to an individual instance within the
 *    recurrence set.
 * 
 *    If the value of the "DTSTART" property is a DATE type value, then the
 *    value MUST be the calendar date for the recurrence instance.
 * 
 *    The date/time value is set to the time when the original recurrence
 *    instance would occur; meaning that if the intent is to change a
 *    Friday meeting to Thursday, the date/time is still set to the
 *    original Friday meeting.
 * 
 *    The "RECURRENCE-ID" property is used in conjunction with the "UID"
 *    and "SEQUENCE" property to identify a particular instance of a
 *    recurring event, to-do or journal. For a given pair of "UID" and
 *    "SEQUENCE" property values, the "RECURRENCE-ID" value for a
 *    recurrence instance is fixed. When the definition of the recurrence
 *    set for a calendar component changes, and hence the "SEQUENCE"
 *    property value changes, the "RECURRENCE-ID" for a given recurrence
 *    instance might also change.The "RANGE" parameter is used to specify
 *    the effective range of recurrence instances from the instance
 *    specified by the "RECURRENCE-ID" property value. The default value
 *    for the range parameter is the single recurrence instance only. The
 *    value can also be "THISANDPRIOR" to indicate a range defined by the
 *    given recurrence instance and all prior instances or the value can be
 *    "THISANDFUTURE" to indicate a range defined by the given recurrence
 *    instance and all subsequent instances.
 * 
 *    Format Definition: The property is defined by the following notation:
 * 
 *      recurid    = "RECURRENCE-ID" ridparam ":" ridval CRLF
 * 
 *      ridparam   = *(
 * 
 *                 ; the following are optional,
 *                 ; but MUST NOT occur more than once
 * 
 *                 (";" "VALUE" "=" ("DATE-TIME" / "DATE)) /
 *                 (";" tzidparam) / (";" rangeparam) /
 * 
 *                 ; the following is optional,
 *                 ; and MAY occur more than once
 * 
 *                 (";" xparam)
 * 
 *                 )
 * 
 *      ridval     = date-time / date
 *      ;Value MUST match value type
 * </pre>
 *
 * @author Ben Fortuna
 */
public class RecurrenceId extends Property {
    
    private static final long serialVersionUID = 4456883817126011006L;

    private Date time;

    // default value determined through inspection
    // of iCal-generated files..
    private boolean utc = false;

    /**
     * Default constructor.
     */
    public RecurrenceId() {
        super(RECURRENCE_ID);
        time = new DateTime();
    }
    
    /**
     * @param aList
     *            a list of parameters for this component
     * @param aValue
     *            a value string for this component
     * @throws ParseException
     *             where the specified value string is not a valid
     *             date-time/date representation
     */
    public RecurrenceId(final ParameterList aList, final String aValue)
            throws ParseException {
        super(RECURRENCE_ID, aList);
        setValue(aValue);
    }

    /**
     * Constructor. Date or Date-Time format is determined based
     * on the presence of a VALUE parameter.
     * @param aDate
     *            a date representation of a date or date-time
     */
    public RecurrenceId(final Date aDate) {
        super(RECURRENCE_ID);
        time = aDate;
    }

    /**
     * Constructor. Date or Date-Time format is determined based
     * on the presence of a VALUE parameter.
     * @param aList
     *            a list of parameters for this component
     * @param aDate
     *            a date representation of a date or date-time
     */
    public RecurrenceId(final ParameterList aList, final Date aDate) {
        super(RECURRENCE_ID, aList);
        time = aDate;
    }

    /**
     * @return Returns the time.
     */
    public final Date getTime() {
        return time;
    }

    /**
     * @see net.fortuna.ical4j.model.Property#validate()
     */
    public final void validate() throws ValidationException {

        /*
         * ; the following are optional, ; but MUST NOT occur more than once
         *
         * (";" "VALUE" "=" ("DATE-TIME" / "DATE)) / (";" tzidparam) / (";"
         * rangeparam) /
         */
        ParameterValidator.getInstance().validateOneOrLess(Parameter.VALUE,
                getParameters());

        Parameter valueParam = getParameters().getParameter(Parameter.VALUE);

        if (valueParam != null
                && !Value.DATE_TIME.equals(valueParam)
                && !Value.DATE.equals(valueParam)) {
            throw new ValidationException(
                "Parameter [" + Parameter.VALUE + "] is invalid");
        }

        if (isUtc()) {
            ParameterValidator.getInstance().validateNone(Parameter.TZID,
                    getParameters());
            
        }
        else {
            ParameterValidator.getInstance().validateOneOrLess(Parameter.TZID,
                    getParameters());
        }

        ParameterValidator.getInstance().validateOneOrLess(Parameter.RANGE,
                getParameters());

        /*
         * ; the following is optional, ; and MAY occur more than once
         *
         * (";" xparam)
         */
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.model.Property#setValue(java.lang.String)
     */
    public final void setValue(final String aValue) throws ParseException {
        // value can be either a date-time or a date..
        Parameter valueParam = getParameters().getParameter(Parameter.VALUE);

        if (valueParam != null && Value.DATE.equals(valueParam)) {
            time = new Date(aValue);
        }
        else {
            time = new DateTime(aValue);
        }
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.model.Property#getValue()
     */
    public final String getValue() {
        /*
        Parameter valueParam = getParameters().getParameter(Parameter.VALUE);
        if (valueParam != null && Value.DATE.equals(valueParam)) {
            return DateFormat.getInstance().format(getTime());
        }
        // return local time..
        return DateTimeFormat.getInstance().format(getTime(), isUtc());
        */
        return getTime().toString();
    }

    /**
     * @return Returns the utc.
     */
    public final boolean isUtc() {
        return utc;
    }

    /**
     * @param utc The utc to set.
     */
    public final void setUtc(final boolean utc) {
        if (Value.DATE_TIME.equals(getParameters().getParameter(Parameter.VALUE))) {
            ((DateTime) getTime()).setUtc(utc);
        }
        this.utc = utc;
    }
    
    /**
     * @param time The time to set.
     */
    public final void setTime(final Date time) {
        this.time = time;
    }
}
