/*
 * $Id$
 *
 * Created on 30/06/2005
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

import java.util.TimeZone;

import net.fortuna.ical4j.util.TimeZoneUtils;

/**
 * A type used to represent iCalendar time values.
 * @author Ben Fortuna
 */
public class Time extends Iso8601 {
    
    private static final long serialVersionUID = -8401010870773304348L;
    
    /**
     * FORM #1: LOCAL TIME.
     */
    private static final String DEFAULT_PATTERN = "HHmmss";
    
    /**
     * FORM #2: UTC TIME.
     */
    private static final String UTC_PATTERN = "HHmmss'Z'";

    /**
     * Default constructor.
     */
    public Time(final TimeZone timezone) {
        super(TimeZoneUtils.isUtc(timezone) ? UTC_PATTERN : DEFAULT_PATTERN);
        getFormat().setTimeZone(timezone);
    }

    /**
     * @param time
     * @param utc
     */
    public Time(final long time, final TimeZone timezone) {
        super(time, (TimeZoneUtils.isUtc(timezone) ? UTC_PATTERN : DEFAULT_PATTERN));
        getFormat().setTimeZone(timezone);
    }

    /**
     * @param time
     * @param utc
     */
    public Time(final java.util.Date time, final TimeZone timezone) {
        super(time, (TimeZoneUtils.isUtc(timezone) ? UTC_PATTERN : DEFAULT_PATTERN));
        getFormat().setTimeZone(timezone);
    }

    /**
     * @return Returns the utc.
     */
    public final boolean isUtc() {
        return TimeZoneUtils.isUtc(getFormat().getTimeZone());
    }
}
