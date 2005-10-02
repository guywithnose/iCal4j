/*
 * $Id$
 *
 * Created on 13/09/2005
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

import java.util.Calendar;
import java.util.Date;

import net.fortuna.ical4j.model.component.Daylight;
import net.fortuna.ical4j.model.component.Observance;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.TzId;
import net.fortuna.ical4j.model.property.TzOffsetTo;

/**
 * A Java timezone implementation based on an underlying VTimeZone
 * definition.
 * @author Ben Fortuna
 */
public class TimeZone extends java.util.TimeZone {
    
    private VTimeZone vTimeZone;
    
    /**
     * Constructs a new instance based on the specified VTimeZone.
     * @param vTimeZone
     */
    public TimeZone(final VTimeZone vTimeZone) {
        this.vTimeZone = vTimeZone;
        TzId tzId = (TzId) vTimeZone.getProperties().getProperty(Property.TZID);
        setID(tzId.getValue());
    }

    /* (non-Javadoc)
     * @see java.util.TimeZone#getOffset(int, int, int, int, int, int)
     */
    public final int getOffset(final int era, final int year, final int month, final int day, final int dayOfWeek, final int milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.ERA, era);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_YEAR, day);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        cal.set(Calendar.MILLISECOND, milliseconds);
        Observance observance = vTimeZone.getApplicableObservance(new DateTime(cal.getTime()));
        if (observance != null) {
            TzOffsetTo offset = (TzOffsetTo) observance.getProperties().getProperty(Property.TZOFFSETTO);
            return (int) offset.getOffset().getOffset();
        }
        return 0;
    }

    /* (non-Javadoc)
     * @see java.util.TimeZone#getRawOffset()
     */
    public final int getRawOffset() {
        Component seasonalTime = vTimeZone.getObservances().getComponent(Observance.STANDARD);
        // if no standard time use daylight time..
        if (seasonalTime == null) {
            seasonalTime = vTimeZone.getObservances().getComponent(Observance.DAYLIGHT);
        }
        TzOffsetTo offsetTo = (TzOffsetTo) seasonalTime.getProperties().getProperty(Property.TZOFFSETTO);
        if (offsetTo != null) {
            return (int) offsetTo.getOffset().getOffset();
        }
        return 0;
    }

    /**
     * Determines if the specified date is in daylight time according to
     * this timezone. This is done by finding the latest supporting
     * observance for the specified date and identifying whether it is
     * daylight time.
     */
    public final boolean inDaylightTime(final Date date) {
        Observance observance = vTimeZone.getApplicableObservance(new DateTime(date));
        return (observance != null && observance instanceof Daylight);
    }

    /* (non-Javadoc)
     * @see java.util.TimeZone#setRawOffset(int)
     */
    public final void setRawOffset(final int offsetMillis) {
        throw new UnsupportedOperationException("Updates to the VTIMEZONE object must be performed directly");
    }

    /* (non-Javadoc)
     * @see java.util.TimeZone#useDaylightTime()
     */
    public final boolean useDaylightTime() {
        ComponentList daylights = vTimeZone.getObservances().getComponents(Observance.DAYLIGHT);
        return (!daylights.isEmpty());
    }

    /**
     * @return Returns the VTimeZone backing this instance.
     */
    public final VTimeZone getVTimeZone() {
        return vTimeZone;
    }
}
