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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.parameter.Encoding;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.util.ParameterValidator;

/**
 * Defines an ATTACH iCalendar component property.
 *
 * @author benf
 */
public class Attach extends Property {

    private URI uri;

    private byte[] binary;

    /**
     * Default constructor.
     */
    public Attach() {
        super(ATTACH);
    }
    
    /**
     * @param aList
     *            a list of parameters for this component
     * @param aValue
     *            a value string for this component
     * @throws IOException
     *             when there is an error reading the binary stream
     * @throws URISyntaxException
     *             where the specified string is not a valid uri
     */
    public Attach(final ParameterList aList, final String aValue)
            throws IOException, URISyntaxException {
        super(ATTACH, aList);
        setValue(aValue);
    }
    
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.model.Property#setValue(java.lang.String)
     */
    public void setValue(final String aValue) throws IOException, URISyntaxException {

        // determine if ATTACH is a URI or an embedded
        // binary..
        Parameter encodingParam = getParameters().getParameter(
                Parameter.ENCODING);
        Parameter valueParam = getParameters().getParameter(Parameter.VALUE);

        if (encodingParam != null
                && Encoding.BASE64.equals(encodingParam.getValue())
                && valueParam != null
                && Value.BINARY.equals(valueParam.getValue())) {

            ByteArrayOutputStream bout = new ByteArrayOutputStream(aValue
                    .length());

            OutputStreamWriter writer = new OutputStreamWriter(bout);
            writer.write(aValue);

            binary = bout.toByteArray();
        }
        // assume URI..
        else {

            uri = new URI(aValue);
        }
    }

    /**
     * @param data
     *            binary data
     */
    public Attach(final byte[] data) {
        super(ATTACH);
        this.binary = data;
    }

    /**
     * @param aList
     *            a list of parameters for this component
     * @param data
     *            binary data
     */
    public Attach(final ParameterList aList, final byte[] data) {
        super(ATTACH, aList);

        this.binary = data;
    }

    /**
     * @param aUri
     *            a URI
     */
    public Attach(final URI aUri) {
        super(ATTACH);
        this.uri = aUri;
    }

    /**
     * @param aList
     *            a list of parameters for this component
     * @param aUri
     *            a URI
     */
    public Attach(final ParameterList aList, final URI aUri) {
        super(ATTACH, aList);
        this.uri = aUri;
    }

    /**
     * @see net.fortuna.ical4j.model.Property#validate()
     */
    public final void validate() throws ValidationException {

        /*
         * ; the following is optional, ; but MUST NOT occur more than once
         *
         * (";" fmttypeparam) /
         */
        ParameterValidator.getInstance().validateOneOrLess(Parameter.FMTTYPE,
                getParameters());

        /*
         * ; the following is optional, ; and MAY occur more than once
         *
         * (";" xparam)
         */
    }

    /**
     * @return Returns the binary.
     */
    public final byte[] getBinary() {
        return binary;
    }

    /**
     * @return Returns the uri.
     */
    public final URI getUri() {
        return uri;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.fortuna.ical4j.model.Property#getValue()
     */
    public final String getValue() {

        if (getUri() != null) {
            return getUri().toString();
        }
        else if (getBinary() != null) { return String.valueOf(getBinary()); }

        return null;
    }
    
    /**
     * @param binary The binary to set.
     */
    public final void setBinary(final byte[] binary) {
        this.binary = binary;
        // unset uri..
        this.uri = null;
    }
    
    /**
     * @param uri The uri to set.
     */
    public final void setUri(final URI uri) {
        this.uri = uri;
        // unset binary..
        this.binary = null;
    }
}