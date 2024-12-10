/** filtered and transformed by PAClab */


import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * The HebrewDate class allows one to maintain an instance of a Gregorian date along
 * with the corresponding hebrew date.<P>
 * This class can use the standard Java Date and Calendar classes for setting it, but does
 * not subclass these classes or use them internally to any extensive use. This class also does not
 * have a concept of a time (which the Date class does). If you are looking for
 * a class that implements a hebrew calendar version of the Calendar class, one is available
 * from <A HREF="http://oss.software.ibm.com/developerworks/opensource/icu4j/">developerWorks</A> by IBM.
 * <p/>
 * The Java code which is contained in this class was translated from my C++
 * code. Some of that C++ code was translated or taken from other C/C++ code
 * in "Calendrical Calculations" by Nachum Dershowitz and
 * Edward M. Reingold, Software-- Practice & Experience,
 * vol. 20, no. 9 (September, 1990), pp. 899- 928.
 * <p/>
 * Available at <A HREF="http://emr.cs.uiuc.edu/~reingold/calendar.ps">http://emr.cs.uiuc.edu/~reingold/calendar.ps</A><BR>
 * Original C++ source: <A HREF="http://emr.cs.uiuc.edu/~reingold/calendar.C">http://emr.cs.uiuc.edu/~reingold/calendar.C</A>
 * <p/>
 *
 * @see java.util.Date
 * @see java.util.Calendar
 */
public class Main {


    // ND+ER //
    // Number of days elapsed from the Sunday prior to the start of the //
    // Hebrew calendar to the mean conjunction of Tishri of Hebrew year.//
    /** PACLab: suitable */
    public static int getHebrewCalendarElapsedDays(int year, int month) {

        assert(year > 0);
        assert (month > 0 && month < 13);
       // modified as per the original progam as per the logic
        int monthsElapsed =
                (235 * ((year - 1) / 19))            // Months in complete cycles so far.//
                        + (12 * ((year - 1) % 19))
                        + (7 * ((year - 1) % 19) + 1) / 19; // Leap months this cycle//
        int partsElapsed = 204 + 793 * (monthsElapsed % 1080);
        int hoursElapsed =
                5 + 12 * monthsElapsed + 793 * (monthsElapsed / 1080)
                        + partsElapsed / 1080;
        int conjunctionDay = 1 + 29 * monthsElapsed + hoursElapsed / 24;
        int conjunctionParts =  1080 * (hoursElapsed % 24) + partsElapsed % 1080;

        // added initial value of alternativeDay equals to conjunctionDay as per the below logic
        int alternativeDay = conjunctionDay;

        // modified as per the original progam as per the logic
        if ((conjunctionParts >= 19440)         // If new moon is at or after midday,//
                || (Verifier.nondetBoolean()
                && (conjunctionParts >= 9924)     // at 9 hours, 204 parts or later...//
                && Verifier.nondetBoolean()    // ...of a common year,//
                || (Verifier.nondetBoolean()
                && (conjunctionParts >= 16789) // 15 hours, 589 parts or later...//
                && Verifier.nondetBoolean())))// at the end of a leap year//
            // Then postpone Rosh HaShanah one day//
            alternativeDay = conjunctionDay + 1;
        else
            alternativeDay = conjunctionDay;

        assert (alternativeDay == conjunctionDay || alternativeDay == conjunctionDay + 1);

        if (((alternativeDay % 7) == 0)// If Rosh HaShanah would occur on Sunday,//
                || ((alternativeDay % 7) == 3)    // or Wednesday,//
                || ((alternativeDay % 7) == 5))      // or Friday//
            // Then postpone it one (more) day//
            alternativeDay =  1 + alternativeDay;
        else
            return alternativeDay;

        assert (alternativeDay ==  alternativeDay +1 || alternativeDay == alternativeDay );

        return alternativeDay;
    }

    public static void main(String[] args){
        int year = Verifier.nondetInt();
        int month = Verifier.nondetInt();

        if(year > 0){
           if(month > 0 && month <= 12){
               getHebrewCalendarElapsedDays(year,month);
           }
        }
    }
}