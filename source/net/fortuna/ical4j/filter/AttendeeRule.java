/*
 * $Id$
 *
 * Created on 2/02/2006
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
package net.fortuna.ical4j.filter;

import java.util.Iterator;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Attendee;

/**
 * A rule that matches any component containing the specified attendee. Note that
 * this rule ignores any parameters matching only on the value of the Attendee
 * property.
 * @author Ben Fortuna
 */
public class AttendeeRule extends ComponentRule {

    private Attendee attendee;
    
    /**
     * Constructs a new instance with the specified attendee.
     * @param attendee
     */
    public AttendeeRule(final Attendee attendee) {
        this.attendee = attendee;
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.filter.ComponentRule#match(net.fortuna.ical4j.model.Component)
     */
    public boolean match(final Component component) {
        PropertyList attendees = component.getProperties().getProperties(Property.ATTENDEE);
        for (Iterator i = attendees.iterator(); i.hasNext();) {
            Attendee a = (Attendee) i.next();
//            if (attendee.equals(a)) {
            if (attendee.getValue().equals(a.getValue())) {
                return true;
            }
        }
        return false;
    }

}
