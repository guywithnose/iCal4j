/*
 * $Id$
 * 
 * Created: Apr 5, 2004
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
package net.fortuna.ical4j.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.ParseException;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentFactory;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.component.VToDo;

/**
 * Parses and builds an iCalendar model from an input stream.
 * Note that this class is not thread-safe.
 *
 * @author benf
 * @version 2.0
 */
public class CalendarBuilder implements ContentHandler {
    
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private CalendarParser parser;
    
    private Calendar calendar;
    
    private Component component;
    
    private Component subComponent;
    
    private Property property;
    
    /**
     * Default constructor.
     */
    public CalendarBuilder() {
        this(new CalendarParserImpl());
    }
    
    /**
     * Constructs a new calendar builder using the specified
     * calendar parser.
     * @param parser a calendar parser used to parse calendar files
     */
    public CalendarBuilder(final CalendarParser parser) {
        this.parser = parser;
    }

    /**
     * Builds an iCalendar model from the specified input stream.
     *
     * @param in
     * @return a calendar
     * @throws IOException
     * @throws ParserException
     */
    public final Calendar build(final InputStream in) throws IOException,
            ParserException {
        return build(new InputStreamReader(in, DEFAULT_CHARSET));
    }

    /**
     * Builds an iCalendar model from the specified reader.
     * An <code>UnfoldingReader</code> is applied to the specified
     * reader to ensure the data stream is correctly unfolded where
     * appropriate.
     *
     * @param in
     * @return a calendar
     * @throws IOException
     * @throws ParserException
     */
    public final Calendar build(final Reader in) throws IOException, ParserException {
        UnfoldingReader uin = new UnfoldingReader(in);

        // re-initialise..
        calendar = null;
        component = null;
        subComponent = null;
        property = null;
        
        parser.parse(uin, this);
        
        return calendar;
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.data.ContentHandler#endCalendar()
     */
    public void endCalendar() {
        // do nothing..
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.data.ContentHandler#endComponent(java.lang.String)
     */
    public void endComponent(final String name) {
        if (component != null) {
            if (subComponent != null) {
                if (component instanceof VTimeZone) {
                    ((VTimeZone) component).getTypes().add(subComponent);
                }
                else if (component instanceof VEvent) {
                    ((VEvent) component).getAlarms().add(subComponent);
                }
                else if (component instanceof VToDo) {
                    ((VToDo) component).getAlarms().add(subComponent);
                }
                subComponent = null;
            }
            else {
                calendar.getComponents().add(component);
                component = null;
            }
        }
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.data.ContentHandler#endProperty(java.lang.String)
     */
    public void endProperty(final String name) {
        if (property != null) {
            if (component != null) {
                if (subComponent != null) {
                    subComponent.getProperties().add(property);
                }
                else {
                    component.getProperties().add(property);
                }
            }
            else if (calendar != null) {
                calendar.getProperties().add(property);
            }
            
            property = null;
        }
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.data.ContentHandler#parameter(java.lang.String, java.lang.String)
     */
    public void parameter(final String name, final String value) throws URISyntaxException {
        if (property != null) {
            // parameter names are case-insensitive, but convert to upper case to simplify further processing
            property.getParameters().add(ParameterFactory.getInstance().createParameter(name.toUpperCase(), value));
        }
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.data.ContentHandler#propertyValue(java.lang.String)
     */
    public void propertyValue(final String value) throws URISyntaxException, ParseException, IOException {
        if (property != null) {
            property.setValue(value);
        }
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.data.ContentHandler#startCalendar()
     */
    public void startCalendar() {
        calendar = new Calendar();
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.data.ContentHandler#startComponent(java.lang.String)
     */
    public void startComponent(final String name) {
        if (component != null) {
            subComponent = ComponentFactory.getInstance().createComponent(name);
        }
        else {
            component = ComponentFactory.getInstance().createComponent(name);
        }
    }
    
    /* (non-Javadoc)
     * @see net.fortuna.ical4j.data.ContentHandler#startProperty(java.lang.String)
     */
    public void startProperty(final String name) {
        // property names are case-insensitive, but convert to upper case to simplify further processing
        property = PropertyFactory.getInstance().createProperty(name.toUpperCase());
    }
}