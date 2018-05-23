package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final DateFormat SDF_YYYY_MM = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);

    private static final SimpleDateFormat SDF_MMMM_YYYY = new SimpleDateFormat("LLLL YYYY");

    static final String PRESENT_TIME = "по настоящее время";

    public static Date toDate(String str) {
        Date date = null;

        if (str.isEmpty()) return date;

        try {
            date = SDF_YYYY_MM.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String seniority(Date start, Date end) {

        String retVal = "";

        try {

            if ((start != null) && (end != null)) {
                retVal = String.format("%1$s - %2$s", SDF_MMMM_YYYY.format(start), SDF_MMMM_YYYY.format(end));
            } else if ((start != null) && (end == null)) {
                retVal = String.format("%1$s - %2$s", SDF_MMMM_YYYY.format(start), PRESENT_TIME);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return retVal;
    }

    public static String duration(Date start, Date end) {
        String retVal = "";

        try {

            Period between = Period.between(toLocalDate(start), toLocalDate(end));

            int years = between.getYears();
            int months = between.getMonths();
            months++;
            years += months==12?1:0;
            months = months==12?0:months;

            retVal = String.format("%1$s %2$s", toYearsStrRu(years), toMonthsStrRu(months));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal;
    }

    private static Instant toInstant(Date date) {

        return date != null ? date.toInstant() : Instant.now();
    }

    private static LocalDateTime toLocalDateTime(Date date) {

        return LocalDateTime.ofInstant(toInstant(date), ZoneOffset.UTC);
    }

    private static LocalDate toLocalDate(Date date) {

        return toLocalDateTime(date).toLocalDate();
    }

    private static String toYearsStrRu(int num) {

        return num == 0 ? "" : String.format("%1$d %2$s", num, num == 1 ? "год" : num < 4 ? "года" : "лет");
    }

    private static String toMonthsStrRu(int num) {
        return num == 0 ? "" : String.format("%1$d %2$s", num, num == 1 ? "месяц" : num < 5 ? "месяца" : "месяцев");
    }

}
