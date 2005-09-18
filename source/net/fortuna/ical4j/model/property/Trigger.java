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

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.util.ParameterValidator;

/**
 * Defines a TRIGGER iCalendar component property.
 * 
 * <pre>
 * 4.8.6.3 Trigger
 * 
 *    Property Name: TRIGGER
 * 
 *    Purpose: This property specifies when an alarm will trigger.
 * 
 *    Value Type: The default value type is DURATION. The value type can be
 *    set to a DATE-TIME value type, in which case the value MUST specify a
 *    UTC formatted DATE-TIME value.
 * 
 *    Property Parameters: Non-standard, value data type, time zone
 *    identifier or trigger relationship property parameters can be
 *    specified on this property. The trigger relationship property
 *    parameter MUST only be specified when the value type is DURATION.
 * 
 *    Conformance: This property MUST be specified in the "VALARM" calendar
 *    component.
 * 
 *    Description: Within the "VALARM" calendar component, this property
 *    defines when the alarm will trigger. The default value type is
 *    DURATION, specifying a relative time for the trigger of the alarm.
 *    The default duration is relative to the start of an event or to-do
 *    that the alarm is associated with. The duration can be explicitly set
 * 
 *    to trigger from either the end or the start of the associated event
 *    or to-do with the "RELATED" parameter. A value of START will set the
 *    alarm to trigger off the start of the associated event or to-do. A
 *    value of END will set the alarm to trigger off the end of the
 *    associated event or to-do.
 * 
 *    Either a positive or negative duration may be specified for the
 *    "TRIGGER" property. An alarm with a positive duration is triggered
 *    after the associated start or end of the event or to-do. An alarm
 *    with a negative duration is triggered before the associated start or
 *    end of the event or to-do.
 * 
 *    The "RELATED" property parameter is not valid if the value type of
 *    the property is set to DATE-TIME (i.e., for an absolute date and time
 *    alarm trigger). If a value type of DATE-TIME is specified, then the
 *    property value MUST be specified in the UTC time format. If an
 *    absolute trigger is specified on an alarm for a recurring event or
 *    to-do, then the alarm will only trigger for the specified absolute
 *    date/time, along with any specified repeating instances.
 * 
 *    If the trigger is set relative to START, then the "DTSTART" property
 *    MUST be present in the associated "VEVENT" or "VTODO" calendar
 *    component. If an alarm is specified for an event with the trigger set
 *    relative to the END, then the "DTEND" property or the "DSTART" and
 *    "DURATION' properties MUST be present in the associated "VEVENT"
 *    calendar component. If the alarm is specified for a to-do with a
 *    trigger set relative to the END, then either the "DUE" property or
 *    the "DSTART" and "DURATION' properties MUST be present in the
 *    associated "VTODO" calendar component.
 * 
 *    Alarms specified in an event or to-do which is defined in terms of a
 *    DATE value type will be triggered relative to 00:00:00 UTC on the
 *    specified date. For example, if "DTSTART:19980205, then the duration
 *    trigger will be relative to19980205T000000Z.
 * 
 *    Format Definition: The property is defined by the following notation:
 * 
 *      trigger    = "TRIGGER" (trigrel / trigabs)
 * 
 *      trigrel    = *(
 * 
 *                 ; the following are optional,
 *                 ; but MUST NOT occur more than once
 * 
 *                   (";" "VALUE" "=" "DURATION") /
 *                   (";" trigrelparam) /
 * 
 *                 ; the following is optional,
 *                 ; and MAY occur more than once
 * 
 *                   (";" xparam)
 *                   ) ":"  dur-value
 * 
 *      trigabs    = 1*(
 * 
 *                 ; the following is REQUIRED,
 *                 ; but MUST NOT occur more than once
 * 
 *                   (";" "VALUE" "=" "DATE-TIME") /
 * 
 *                 ; the following is optional,
 *                 ; and MAY occur more than once
 * 
 *                   (";" xparam)
 * 
 *                   ) ":" date-time
 * </pre>
 *
 * @author Ben Fortuna
 */
public class Trigger extends Property {
    
    private static final long serialVersionUID = 5049421499261722194L;

    private Dur duration;

    /**
     * The value type can be set to a DATE-TIME value type, in which case the
     * value MUST specify a UTC formatted DATE-TIME value.
     */
    private DateTime dateTime;

    /**
     * Default constructor.
     */
    public Trigger() {
        super(TRIGGER);
        dateTime = new DateTime();
    }
    
    /**
     * @param aList
     *            a list of parameters for this component
     * @param aValue
     *            a value string for this component
     */
    public Trigger(final ParameterList aList, final String aValue) {
        super(TRIGGER, aList);
        setValue(aValue);
    }

    /**
     * @param aDuration
     *            a duration in milliseconds
     */
    public Trigger(final Dur duration) {
        super(TRIGGER);
        setDuration(duration);
    }

    /**
     * @param aList
     *            a list of parameters for this component
     * @param aDuration
     *            a duration in milliseconds
     */
    public Trigger(final ParameterList aList, final Dur duration) {
        super(TRIGGER, aList);
        setDuration(duration);
    }

    /**
     * @param aDate
     *            a date representation of a date-time
     */
    public Trigger(final DateTime aDate) {
        super(TRIGGER);
        dateTime = aDate;
    }

    /**
     * @param aList
     *            a list of parameters for this component
     * @param aDate
     *            a date representation of a date-time
     */
    public Trigger(final ParameterList aList, final DateTime aDate) {
        super(TRIGGER, aList);
        dateTime = aDate;
    }

    /**
     * @see net.fortuna.ical4j.model.Property#validate()
     */
    public final void validate() throws ValidationException {

        if (getDateTime() != null) {
            /*
             * ; the following is REQUIRED, ; but MUST NOT occur more than once
             *
             * (";" "VALUE" "=" "DATE-TIME") /
             */
            ParameterValidator.getInstance().assertOne(Parameter.VALUE,
                    getParameters());

            Parameter valueParam = getParameters()
                    .getParameter(Parameter.VALUE);

            if (valueParam == null
                    || !Value.DATE_TIME.equals(valueParam.getValue())) { throw new ValidationException(
                            "Parameter [" + Parameter.VALUE + "=" + valueParam.getValue() + "] is invalid"); }

            /*
             * ; the following is optional, ; and MAY occur more than once
             *
             * (";" xparam) ) ":" date-time
             */
        }
        else { //if (duration > 0) {

            /*
             * ; the following are optional, ; but MUST NOT occur more than once
             *
             * (";" "VALUE" "=" "DURATION") / (";" trigrelparam) /
             */
            ParameterValidator.getInstance().assertOneOrLess(Parameter.VALUE,
                    getParameters());

            Parameter valueParam = getParameters()
                    .getParameter(Parameter.VALUE);

            if (valueParam != null
                    && !Value.DURATION.equals(valueParam.getValue())) { throw new ValidationException(
                    "Parameter [" + Parameter.VALUE + "=" + valueParam.getValue() + "] is invalid"); }

            ParameterValidator.getInstance().assertOneOrLess(
                    Parameter.RELATED, getParameters());

            /*
             * ; the following is optional, ; and MAY occur more than once
             *
             * (";" xparam) ) ":" dur-value
             */

        }
    }

    /**
     * @return Returns the dateTime.
     */
    public final DateTime getDateTime() {
        return dateTime;
    }

    /**
     * @return Returns the duration.
     */
    public final Dur getDuration() {
        return duration;
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.model.Property#setValue(java.lang.String)
     */
    public final void setValue(final String aValue) {
        try {
            dateTime = new DateTime(aValue);
            duration = null;
        }
        catch (Exception e) {
//            duration = DurationFormat.getInstance().parse(aValue);
            duration = new Dur(aValue);
            dateTime = null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see net.fortuna.ical4j.model.Property#getValue()
     */
    public final String getValue() {
        if (getDateTime() != null) {
            return getDateTime().toString();
        }
        else {
//            return DurationFormat.getInstance().format(getDuration());
            return duration.toString();
        }
    }
    
    /**
     * @param dateTime The dateTime to set.
     */
    public final void setDateTime(final DateTime dateTime) {
        // ensure date-time is in UTC..
        dateTime.setUtc(true);
        this.dateTime = dateTime;
        duration = null;
    }
    
    /**
     * @param duration The duration to set.
     */
    public final void setDuration(final Dur duration) {
        this.duration = duration;
        dateTime = null;
    }
}
