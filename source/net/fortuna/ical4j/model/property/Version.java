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

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;

/**
 * Defines a VERSION iCalendar property.
 *
 * When creating a new calendar you should always add a version property with value "2.0".
 * There is actually a constant defined in the Version class for this. Note: if you use the
 * default constructor for Version it will automatically set the maxVersion = "2.0". e.g:
 *
 * <code>    Calendar calendar = new Calendar();</code>
 * <code>    calendar.getProperties().add(new Version());</code>
 *
 * @author Ben Fortuna
 */
public class Version extends Property {

    public static final String VERSION_2_0 = "2.0";

    private String minVersion;

    private String maxVersion;

    /**
     * Default constructor.
     */
    public Version() {
        super(VERSION);
        maxVersion = VERSION_2_0;
    }

    /**
     * @param aList
     *            a list of parameters for this component
     * @param aValue
     *            a value string for this component
     */
    public Version(final ParameterList aList, final String aValue) {
        super(VERSION, aList);
        setValue(aValue);
    }

    /**
     * @param aVersion1
     *            a string representation of the minimum version
     * @param aVersion2
     *            a string representation of the maximum version
     */
    public Version(final String aVersion1, final String aVersion2) {
        super(VERSION);
        minVersion = aVersion1;
        maxVersion = aVersion2;
    }

    /**
     * @param aList
     *            a list of parameters for this component
     * @param aVersion1
     *            a string representation of the minimum version
     * @param aVersion2
     *            a string representation of the maximum version
     */
    public Version(final ParameterList aList, final String aVersion1,
            final String aVersion2) {
        super(VERSION, aList);
        minVersion = aVersion1;
        maxVersion = aVersion2;
    }

    /**
     * @return Returns the maxVersion.
     */
    public final String getMaxVersion() {
        return maxVersion;
    }

    /**
     * @return Returns the minVersion.
     */
    public final String getMinVersion() {
        return minVersion;
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.model.Property#setValue(java.lang.String)
     */
    public final void setValue(final String aValue) {
        if (aValue.indexOf(';') >= 0) {
            this.minVersion = aValue.substring(0, aValue.indexOf(';') - 1);
            this.maxVersion = aValue.substring(aValue.indexOf(';'));
        }
        else {
            this.maxVersion = aValue;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see net.fortuna.ical4j.model.Property#getValue()
     */
    public final String getValue() {
        StringBuffer b = new StringBuffer();

        if (getMinVersion() != null) {
            b.append(getMinVersion());

            if (getMaxVersion() != null) {
                b.append(';');
            }
        }

        if (getMaxVersion() != null) {
            b.append(getMaxVersion());
        }

        return b.toString();
    }

    /**
     * @param maxVersion The maxVersion to set.
     */
    public final void setMaxVersion(final String maxVersion) {
        this.maxVersion = maxVersion;
    }

    /**
     * @param minVersion The minVersion to set.
     */
    public final void setMinVersion(final String minVersion) {
        this.minVersion = minVersion;
    }
}