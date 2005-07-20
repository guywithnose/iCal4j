/*
 * $Id$ [18-Apr-2004]
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
package net.fortuna.ical4j.model.parameter;

import java.net.URI;
import java.net.URISyntaxException;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.util.StringUtils;

/**
 * Defines a Sent By parameter.
 * 
 * @author benfortuna
 */
public class SentBy extends Parameter {
    
    private static final long serialVersionUID = -1169413145174029391L;

    private URI address;

    /**
     * @param aValue
     *            a string representation of a sent by
     * @throws URISyntaxException
     *             when the specified string is not a valid (quoted) uri
     */
    public SentBy(final String aValue) throws URISyntaxException {
        this(new URI(StringUtils.unquote(aValue)));
    }

    /**
     * @param aUri
     *            a URI representing a sent by
     */
    public SentBy(final URI aUri) {
        super(SENT_BY);

        this.address = aUri;
    }

    /**
     * @return Returns the address.
     */
    public final URI getAddress() {
        return address;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.fortuna.ical4j.model.Parameter#getValue()
     */
    public final String getValue() {
        return StringUtils.quote(getAddress());
    }
}
