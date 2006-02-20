/*
 * $Id$ [06-Apr-2004]
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
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A reader which performs iCalendar unfolding as it reads.
 * Note that unfolding rules may be "relaxed" to allow unfolding of
 * non-conformant *.ics files. By specifying the system property
 * "ical4j.unfolding.relaxed=true" iCalendar files created with
 * Mozilla Calendar/Sunbird may be correctly unfolded.
 *
 * @author Ben Fortuna
 */
public class UnfoldingReader extends PushbackReader {

    /**
     * A system property key to enable relaxed unfolding. Relaxed
     * unfolding is enabled by setting this system property to
     * "true".
     */
    public static final String KEY_UNFOLDING_RELAXED = "ical4j.unfolding.relaxed";

    private static Log log = LogFactory.getLog(UnfoldingReader.class);

    /**
     * The pattern used to identify a fold in an iCalendar data stream.
     */
    private static final char[] DEFAULT_FOLD_PATTERN = {'\r', '\n', ' '};
    
    /**
     * The pattern used to identify a fold in Mozilla Calendar/Sunbird
     * and KOrganizer.
     */
    private static final char[] RELAXED_FOLD_PATTERN = {'\n', ' '};
    
    private static final List FOLD_PATTERNS = new ArrayList();
    
    static {
        FOLD_PATTERNS.add(DEFAULT_FOLD_PATTERN);
        if ("true".equals(System.getProperty(KEY_UNFOLDING_RELAXED))) {
            FOLD_PATTERNS.add(RELAXED_FOLD_PATTERN);
        }
    }

    private List buffers;
    
    private int linesUnfolded;

    /**
     * @param in a reader to read from
     */
    public UnfoldingReader(final Reader in) {
        super(in, DEFAULT_FOLD_PATTERN.length);
        buffers = new ArrayList();
        for (Iterator i = FOLD_PATTERNS.iterator(); i.hasNext();) {
            char[] pattern = (char[]) i.next();
            buffers.add(new char[pattern.length]);
        }
    }

    /**
     * @return number of lines unfolded so far while reading
     */
    public final int getLinesUnfolded() {
        return linesUnfolded;
    }

    /**
     * @see java.io.PushbackReader#read()
     */
    public final int read() throws IOException {
        /*
        for (int i = 0; i < buffers.size(); i++) {
            char[] buffer = (char[]) buffers.get(i);
            int read = super.read(buffer);
            if (read > 0) {
                if (!Arrays.equals((char[]) FOLD_PATTERNS.get(i), buffer)) {
                    unread(buffer, 0, read);
                }
                else {
                    if (log.isDebugEnabled()) {
                        log.debug("Unfolding..");
                    }
                    linesUnfolded++;
                    // return as soon as unfolding occurs..
                    return super.read();
                }
            }
            else {
                return read;
            }
        }
        return super.read();
        */
        boolean didUnfold;

        // need to loop since one line fold might be directly followed by another
        do {
            didUnfold = false;

            for (int i = 0; i < buffers.size(); i++) {
                char[] buffer = (char[]) buffers.get(i);
                int read = super.read(buffer);
                if (read > 0) {
                    if (!Arrays.equals((char[]) FOLD_PATTERNS.get(i), buffer)) {
                        unread(buffer, 0, read);
                    }
                    else {
                        if (log.isDebugEnabled()) {
                            log.debug("Unfolding...");
                        }
                        linesUnfolded++;
                        didUnfold = true;
                    }
                }
                else {
                    return read;
                }
            }
        } while (didUnfold);

        return super.read();
    }
}
