/*
 * Created on 10/02/2005
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

import java.io.FileInputStream;
import java.text.ParseException;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.ComponentTest;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.parameter.FbType;
import net.fortuna.ical4j.model.parameter.TzId;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.FreeBusy;
import net.fortuna.ical4j.model.property.RRule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ben Fortuna
 */
public class VFreeBusyTest extends ComponentTest {

    private static Log log = LogFactory.getLog(VFreeBusyTest.class);

    private TimeZoneRegistry registry;

    private VTimeZone tz;

    private TzId tzParam;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        // create timezone property..
        tz = registry.getTimeZone("Australia/Melbourne").getVTimeZone();
        // create tzid parameter..
        tzParam = new TzId(tz.getProperties().getProperty(Property.TZID)
                .getValue());
    }

    /*
     * Class under test for void VFreeBusy(ComponentList)
     */
    public final void testVFreeBusyComponentList() throws Exception {
        ComponentList components = new ComponentList();

        DateTime startDate = new DateTime(0);
        DateTime endDate = new DateTime();

        VEvent event = new VEvent();
        event.getProperties().add(new DtStart(startDate));
        // event.getProperties().add(new DtEnd(new Date()));
        event.getProperties().add(new Duration(new Dur(0, 1, 0, 0)));
        components.add(event);

        VEvent event2 = new VEvent();
        event2.getProperties().add(new DtStart(startDate));
        event2.getProperties().add(new DtEnd(endDate));
        components.add(event2);

        VFreeBusy request = new VFreeBusy(startDate, endDate);

        VFreeBusy fb = new VFreeBusy(request, components);

        if (log.isDebugEnabled()) {
            log.debug("\n==\n" + fb.toString());
        }
    }

    /*
     * Class under test for void VFreeBusy(ComponentList)
     */
    public final void testVFreeBusyComponentList2() throws Exception {
        FileInputStream fin = new FileInputStream("etc/samples/valid/core.ics");

        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);

        DateTime startDate = new DateTime(0);
        DateTime endDate = new DateTime();

        // request all busy time between 1970 and now..
        VFreeBusy requestBusy = new VFreeBusy(startDate, endDate);

        VFreeBusy fb = new VFreeBusy(requestBusy, calendar.getComponents());

        log.info("\n==\n" + fb.toString());

        // request all free time between 1970 and now of duration 2 hours or
        // more..
        VFreeBusy requestFree = new VFreeBusy(startDate, endDate, new Dur(0, 2,
                0, 0));

        VFreeBusy fb2 = new VFreeBusy(requestFree, calendar.getComponents());

        log.debug("\n==\n" + fb2.toString());
    }

    public final void testVFreeBusyComponentList3() throws Exception {
        ComponentList components = new ComponentList();

        DateTime startDate = new DateTime(0);
        DateTime endDate = new DateTime();

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(java.util.Calendar.HOUR_OF_DAY, 1);

        VEvent event = new VEvent(startDate, new Dur(0, 1, 0, 0),
                "Progress Meeting");
        // VEvent event = new VEvent(startDate, cal.getTime(), "Progress
        // Meeting");
        // add timezone information..
        event.getProperties().getProperty(Property.DTSTART).getParameters()
                .add(tzParam);
        components.add(event);

        // add recurrence..
        Recur recur = new Recur(Recur.YEARLY, 20);
        recur.getMonthList().add(new Integer(1));
        recur.getMonthDayList().add(new Integer(26));
        recur.getHourList().add(new Integer(9));
        recur.getMinuteList().add(new Integer(30));
        event.getProperties().add(new RRule(recur));

        log.debug("\n==\n" + event.toString());

        VFreeBusy request = new VFreeBusy(startDate, endDate);

        VFreeBusy fb = new VFreeBusy(request, components);

        log.info("\n==\n" + fb.toString());
    }

    public final void testVFreeBusyComponentList4() throws Exception {
        ComponentList components = new ComponentList();

        java.util.Calendar cal = java.util.Calendar.getInstance();

        DateTime startDate = new DateTime(cal.getTime());
        cal.add(java.util.Calendar.DATE, 3);
        DateTime endDate = new DateTime(cal.getTime());

        VEvent event = new VEvent();
        event.getProperties().add(new DtStart(startDate));
        // event.getProperties().add(new DtEnd(new Date()));
        event.getProperties().add(new Duration(new Dur(0, 1, 0, 0)));
        components.add(event);

        VEvent event2 = new VEvent();
        event2.getProperties().add(new DtStart(startDate));
        event2.getProperties().add(new DtEnd(endDate));
        components.add(event2);

        VFreeBusy request = new VFreeBusy(startDate, endDate, new Dur(0, 1, 0,
                0));

        VFreeBusy fb = new VFreeBusy(request, components);

        log.debug("\n==\n" + fb.toString());
    }

    public final void testAngelites() {
        log.info("angelites test:\n================");

        Calendar FreeBusyTest = new Calendar();

        // add an event
        java.util.Calendar start = java.util.Calendar.getInstance();
        java.util.Calendar end = java.util.Calendar.getInstance();
        start.add(java.util.Calendar.DATE, -1);
        VEvent dteStartOnly = new VEvent(new Date(start.getTime().getTime()),
                "DATE START ONLY");
        VEvent dteEnd = new VEvent(new Date(start.getTime().getTime()),
                new Date(end.getTime().getTime()), "DATE END INCLUDED");
        VEvent duration = new VEvent(new Date(start.getTime().getTime()),
                new Dur(0, 1, 0, 0), "DURATION");
        FreeBusyTest.getComponents().add(dteEnd);
        FreeBusyTest.getComponents().add(duration);

        java.util.Calendar dtstart = java.util.Calendar.getInstance();
        java.util.Calendar dtend = java.util.Calendar.getInstance();
        dtstart.add(java.util.Calendar.DATE, -2);
        VFreeBusy getBusy = new VFreeBusy(new DateTime(dtstart.getTime()),
                new DateTime(dtend.getTime()));
        VFreeBusy requestFree = new VFreeBusy(new DateTime(dtstart.getTime()),
                new DateTime(dtend.getTime()), new Dur(0, 0, 30, 0));

        log.debug("GET BUSY: \n" + getBusy.toString());
        log.debug("REQUEST FREE: \n" + requestFree.toString());

        Calendar FreeBusyTest2 = new Calendar();

        VFreeBusy replyBusy = new VFreeBusy(getBusy, FreeBusyTest
                .getComponents());
        VFreeBusy replyFree = new VFreeBusy(requestFree, FreeBusyTest
                .getComponents());

        log.debug("REPLY BUSY: \n" + replyBusy.toString());
        log.debug("REPLY FREE: \n" + replyFree.toString());

        FreeBusyTest2.getComponents().add(replyBusy);
        VFreeBusy replyBusy2 = new VFreeBusy(getBusy, FreeBusyTest2
                .getComponents());
        VFreeBusy replyFree2 = new VFreeBusy(requestFree, FreeBusyTest2
                .getComponents());

        log.debug("REPLY BUSY2: \n" + replyBusy2.toString());
        log.debug("REPLY FREE2: \n" + replyFree2.toString());
    }

    /**
     * A test for a request for free time where the VFreeBusy instance doesn't
     * consume any time in the specified range. Correct behaviour should see the
     * return of a VFreeBusy specifying the entire range as free.
     * 
     * @throws ParseException
     */
    public final void testRequestFreeTime() throws ParseException {
        ComponentList components = new ComponentList();

        VEvent event1 = new VEvent(new DateTime("20050101T080000"), new Dur(0,
                0, 15, 0), "Consultation 1");
        components.add(event1);

        DateTime start = new DateTime("20050103T000000");
        DateTime end = new DateTime("20050104T000000");

        VFreeBusy requestFree = new VFreeBusy(start, end, new Dur(0, 0, 5, 0));

        VFreeBusy freeBusy = new VFreeBusy(requestFree, components);

        FreeBusy fg = (FreeBusy) freeBusy.getProperties().getProperty(
                Property.FREEBUSY);
        assertNotNull(fg);
        // free/busy type should be FREE..
        assertEquals(FbType.FREE, fg.getParameter(Parameter.FBTYPE));
        // should be only one period..
        assertEquals(1, fg.getPeriods().size());
        // period should be from the start to the end date..
        assertEquals(new Period(start, end), fg.getPeriods().first());
    }

    public void testBusyTime() throws ParseException {

        VEvent event1 = new VEvent(new DateTime("20050103T080000Z"), new Dur(0,
                5, 0, 0), "Event 1");

        Recur rRuleRecur = new Recur(
                "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR");
        RRule rRule = new RRule(rRuleRecur);
        event1.getProperties().add(rRule);

        ComponentList list = new ComponentList();
        list.add(event1);

        DateTime start = new DateTime("20050104T1100000Z");
        Period period = new Period(start, new Dur(0, 0, 30, 0));

        VFreeBusy request = new VFreeBusy(period.getStart(), period.getEnd());
        VFreeBusy busyTime = new VFreeBusy(request, list);
        FreeBusy fg = (FreeBusy) busyTime.getProperties().getProperty(
                Property.FREEBUSY);
        assertNotNull(fg);
        PeriodList periods = fg.getPeriods();
        assertNotNull(periods);
        assertTrue(periods.size() == 1);
        Period busy1 = (Period) periods.iterator().next();
        // TODO: further work needed to "splice" events based on the amount
        // of time that intersects a free-busy request..
//        assertEquals(new DateTime("20050104T1100000Z"), busy1.getStart());
//        assertEquals("PT30M", busy1.getDuration().toString());
        assertEquals(new DateTime("20050104T0800000Z"), busy1.getStart());
        assertEquals("PT5H", busy1.getDuration().toString());
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.model.ComponentTest#testIsCalendarComponent()
     */
    public void testIsCalendarComponent() {
        assertIsCalendarComponent(new VFreeBusy());
    }
}
