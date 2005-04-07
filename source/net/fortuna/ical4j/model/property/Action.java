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

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;

/**
 * Defines an ACTION iCalendar component property.
 *
 * @author benf
 */
public class Action extends Property {

    public static final Action AUDIO = new Action(new ParameterList(true), "AUDIO");

    public static final Action DISPLAY = new Action(new ParameterList(true), "DISPLAY");

    public static final Action EMAIL = new Action(new ParameterList(true), "EMAIL");

    public static final Action PROCEDURE = new Action(new ParameterList(true), "PROCEDURE");

    private String value;

    /**
     * Default constructor.
     */
    public Action() {
        super(ACTION);
    }
    
    /**
     * @param aValue
     *            a value string for this component
     */
    public Action(final String aValue) {
        super(ACTION);
        setValue(aValue);
    }

    /**
     * @param aList
     *            a list of parameters for this component
     * @param aValue
     *            a value string for this component
     */
    public Action(final ParameterList aList, final String aValue) {
        super(ACTION, aList);
        setValue(aValue);
    }
    
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.model.Property#setValue(java.lang.String)
     */
    public void setValue(final String aValue) {
        // can't modify constant instances..
        if (this.equals(AUDIO)
                || this.equals(DISPLAY)
                || this.equals(EMAIL)
                || this.equals(PROCEDURE)) {
            throw new UnsupportedOperationException("Cannot modify constant instances");
        }
        this.value = aValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.fortuna.ical4j.model.Property#getValue()
     */
    public String getValue() {
        return value;
    }
}