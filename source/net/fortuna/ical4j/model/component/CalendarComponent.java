/**
 * Copyright (c) 2009, Ben Fortuna
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

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.ValidationException;

/**
 * $Id$
 *
 * Created on 26/02/2006
 *
 * Base class for components that may be added to a calendar.
 * @author Ben Fortuna
 */
public abstract class CalendarComponent extends Component {

    /**
     * 
     */
    private static final long serialVersionUID = -5832972592377720592L;

    /**
     * @param name component name
     */
    public CalendarComponent(final String name) {
        super(name);
    }

    /**
     * @param name component name
     * @param properties component properties
     */
    public CalendarComponent(final String name, final PropertyList properties) {
        super(name, properties);
    }

    /**
     * Apply validation for METHOD=PUBLISH.
     * @throws ValidationException where the component does not comply with RFC2446
     */
    public abstract void validatePublish() throws ValidationException;

    /**
     * Apply validation for METHOD=REQUEST.
     * @throws ValidationException where the component does not comply with RFC2446
     */
    public abstract void validateRequest() throws ValidationException;

    /**
     * Apply validation for METHOD=REPLY.
     * @throws ValidationException where the component does not comply with RFC2446
     */
    public abstract void validateReply() throws ValidationException;

    /**
     * Apply validation for METHOD=ADD.
     * @throws ValidationException where the component does not comply with RFC2446
     */
    public abstract void validateAdd() throws ValidationException;

    /**
     * Apply validation for METHOD=CANCEL.
     * @throws ValidationException where the component does not comply with RFC2446
     */
    public abstract void validateCancel() throws ValidationException;

    /**
     * Apply validation for METHOD=REFRESH.
     * @throws ValidationException where the component does not comply with RFC2446
     */
    public abstract void validateRefresh() throws ValidationException;

    /**
     * Apply validation for METHOD=COUNTER.
     * @throws ValidationException where the component does not comply with RFC2446
     */
    public abstract void validateCounter() throws ValidationException;

    /**
     * Apply validation for METHOD=DECLINE-COUNTER.
     * @throws ValidationException where the component does not comply with RFC2446
     */
    public abstract void validateDeclineCounter() throws ValidationException;
}
