/*
 * $Id$ [Apr 5, 2004]
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
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.model.component;

import java.util.Iterator;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.PropertyValidator;

/**
 * Defines an iCalendar VTIMEZONE component.
 * 
 * <pre>
 *    4.6.5 Time Zone Component
 *    
 *       Component Name: VTIMEZONE
 *    
 *       Purpose: Provide a grouping of component properties that defines a
 *       time zone.
 *    
 *       Formal Definition: A &quot;VTIMEZONE&quot; calendar component is defined by the
 *       following notation:
 *    
 *         timezonec  = &quot;BEGIN&quot; &quot;:&quot; &quot;VTIMEZONE&quot; CRLF
 *    
 *                      2*(
 *    
 *                      ; 'tzid' is required, but MUST NOT occur more
 *                      ; than once
 *    
 *                    tzid /
 *    
 *                      ; 'last-mod' and 'tzurl' are optional,
 *                    but MUST NOT occur more than once
 *    
 *                    last-mod / tzurl /
 *    
 *                      ; one of 'standardc' or 'daylightc' MUST occur
 *                    ..; and each MAY occur more than once.
 *    
 *                    standardc / daylightc /
 *    
 *                    ; the following is optional,
 *                    ; and MAY occur more than once
 *    
 *                      x-prop
 *    
 *                      )
 *    
 *                      &quot;END&quot; &quot;:&quot; &quot;VTIMEZONE&quot; CRLF
 *    
 *         standardc  = &quot;BEGIN&quot; &quot;:&quot; &quot;STANDARD&quot; CRLF
 *    
 *                      tzprop
 *    
 *                      &quot;END&quot; &quot;:&quot; &quot;STANDARD&quot; CRLF
 *    
 *         daylightc  = &quot;BEGIN&quot; &quot;:&quot; &quot;DAYLIGHT&quot; CRLF
 *    
 *                      tzprop
 *    
 *                      &quot;END&quot; &quot;:&quot; &quot;DAYLIGHT&quot; CRLF
 *    
 *         tzprop     = 3*(
 *    
 *                    ; the following are each REQUIRED,
 *                    ; but MUST NOT occur more than once
 *    
 *                    dtstart / tzoffsetto / tzoffsetfrom /
 *    
 *                    ; the following are optional,
 *                    ; and MAY occur more than once
 *    
 *                    comment / rdate / rrule / tzname / x-prop
 *    
 *                    )
 * </pre>
 * 
 * @author Ben Fortuna
 */
public class VTimeZone extends Component {

    private static final long serialVersionUID = 5629679741050917815L;

    private ComponentList observances;

    /**
     * Constructs a new instance containing the specified properties.
     * 
     * @param properties
     *            a list of properties
     */
    public VTimeZone(final PropertyList properties) {
        super(VTIMEZONE, properties);
        this.observances = new ComponentList();
    }

    /**
     * Constructs a new vtimezone component with no properties and the specified
     * list of type components.
     * 
     * @param observances
     *            a list of type components
     */
    public VTimeZone(final ComponentList observances) {
        super(VTIMEZONE);
        this.observances = observances;
    }

    /**
     * Constructor.
     * 
     * @param properties
     *            a list of properties
     * @param observances
     *            a list of timezone types
     */
    public VTimeZone(final PropertyList properties, final ComponentList observances) {
        super(VTIMEZONE, properties);
        this.observances = observances;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        return BEGIN + ":" + getName() + "\r\n" + getProperties() + observances + END
                + ":" + getName() + "\r\n";
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.fortuna.ical4j.model.Component#validate(boolean)
     */
    public final void validate(final boolean recurse)
            throws ValidationException {

        /*
         * ; 'tzid' is required, but MUST NOT occur more ; than once
         * 
         * tzid /
         */
        PropertyValidator.getInstance().assertOne(Property.TZID,
                getProperties());

        /*
         * ; 'last-mod' and 'tzurl' are optional, but MUST NOT occur more than
         * once last-mod / tzurl /
         */
        PropertyValidator.getInstance().assertOneOrLess(
                Property.LAST_MODIFIED, getProperties());
        PropertyValidator.getInstance().assertOneOrLess(Property.TZURL,
                getProperties());

        /*
         * ; one of 'standardc' or 'daylightc' MUST occur ..; and each MAY occur
         * more than once.
         * 
         * standardc / daylightc /
         */
        if (getObservances().getComponent(Observance.STANDARD) == null
                && getObservances().getComponent(Observance.DAYLIGHT) == null) {
            throw new ValidationException("Sub-components ["
                    + Observance.STANDARD + "," + Observance.DAYLIGHT
                    + "] must be specified at least once");
        }

        /*
         * ; the following is optional, ; and MAY occur more than once
         * 
         * x-prop
         */

        if (recurse) {
            validateProperties();
        }
    }

    /**
     * @return Returns the types.
     */
    public final ComponentList getObservances() {
        return observances;
    }
    
    /**
     * Returns the latest applicable timezone observance for the specified
     * date.
     * @param date the latest possible date for a timezone observance onset
     * @return the latest applicable timezone observance for the specified
     * date or null if there are no applicable observances
     */
    public final Observance getApplicableObservance(final Date date) {
        Observance latestObservance = null;
        Date latestOnset = null;
        for (Iterator i = getObservances().iterator(); i.hasNext();) {
            Observance observance = (Observance) i.next();
            Date onset = observance.getLatestOnset(date);
            if (latestOnset == null || (onset != null && onset.after(latestOnset))) {
                latestOnset = onset;
                latestObservance = observance;
            }
        }
        return latestObservance;
    }
}
