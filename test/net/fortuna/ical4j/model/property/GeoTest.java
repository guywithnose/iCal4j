/*
 * $Id$
 *
 * Created on 18/10/2006
 *
 * Copyright (c) 2006, Ben Fortuna
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
 * A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.model.property;

import java.math.BigDecimal;
import java.math.RoundingMode;

import junit.framework.TestCase;

/**
 * Unit tests for GEO property implementation.
 * @author Ben Fortuna
 */
public class GeoTest extends TestCase {

    /**
     * Ensure no loss of precision in lattitude/longitude.
     */
    public void testPrecision() {
        Geo geo = new Geo("37.386013;-122.082932");
        
        assertEquals("37.386013;-122.082932", geo.getValue());
    }
    
    /**
     * Unit testing for {@link Geo#Geo(BigDecimal, BigDecimal)}.
     */
    public void testGeoBigDecimal() {
        BigDecimal latitude = BigDecimal.valueOf(65.35);
        BigDecimal longitude = BigDecimal.valueOf(22.01);
        
        Geo geo = new Geo(latitude, longitude);
        assertEquals("65.35;22.01", geo.getValue());
    }
}
