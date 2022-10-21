package com.gmail.bradik.resumebuilder.service;

import com.gmail.bradik.resumebuilder.dto.ResumeData;
import com.gmail.bradik.resumebuilder.dto.experiences.Experience;
import com.gmail.bradik.resumebuilder.util.DateUtil;
import com.gmail.bradik.resumebuilder.util.Mode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.text.ChoiceFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

@Service("ResumeDataServiceRu")
public class ResumeDataServiceRu extends ResumeDataServiceBase {

    public static final Locale LocaleRU = new Locale("ru");

    @Override
    protected Mode getMode() {
        return Mode.RU;
    }

    @Override
    public String getPersonInfo(ResumeData data) {
        //<!--Мужчина, 41 год, родился 4 декабря 1975-->
        LocalDate birthdate = DateUtil.toLocalDate(data.getPersonal().getBirthdate(), "yyyy-MM-dd");
        int age = (int) ChronoUnit.YEARS.between(birthdate, LocalDate.now());

        String born = birthdate.format(DateTimeFormatter.ofPattern("d MMMM yyyy г", LocaleRU));

        StringBuilder sb = new StringBuilder();
        sb.append(data.getPersonal().getGender()).append(", ");
        if (age > 0) {
            sb.append(yearsToString(age)).append(", ");
        }
        sb.append("родился ").append(born);

        return sb.toString();
    }

    @Override
    public String getSeniority(Experience experience) {
        LocalDate start = DateUtil.toLocalDate(experience.getStart(), "yyyy-MM");

        StringBuilder sb = new StringBuilder()
                .append(start.getMonth().getDisplayName(TextStyle.FULL, LocaleRU)).append(" ")
                .append(Integer.valueOf(start.getYear()).toString()).append(" - ");

        if (Strings.isEmpty(experience.getEnd())) {
            sb.append("по настоящее время");
        } else {
            LocalDate end = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
            sb
                    .append(end.getMonth().getDisplayName(TextStyle.FULL, LocaleRU)).append(" ")
                    .append(Integer.valueOf(end.getYear()).toString());
        }

        return sb.toString();
    }

    @Override
    public String getDuration(Experience experience) {
        LocalDate start = DateUtil.toLocalDate(experience.getStart(), "yyyy-MM");
        LocalDate end;
        if (Strings.isEmpty(experience.getEnd())) {
            end = LocalDate.now();
        } else {
            end = DateUtil.toLocalDate(experience.getEnd(), "yyyy-MM");
            end = end.with(TemporalAdjusters.lastDayOfMonth());
        }

        Period between = Period.between(start, end);
        int years = between.getYears();
        int months = between.getMonths();

        StringBuilder sb = new StringBuilder();

        if (years > 0 ) {
            sb.append(yearsToString(years));
            sb.append(" ");
        }

        if (months > 0 ) {
            sb.append(monthsToString(months));
        }

        return sb.toString();
    }

    public String yearsToString(int num) {
        double[] limits = {0, 1, 2, 5};
        String[] strings = {"лет", "год", "года", "лет"};
        ChoiceFormat format = new ChoiceFormat(limits, strings);
        int rule = 11 <= (num % 100) && (num % 100) <= 14 ? num : num % 10;
        return String.valueOf(num) + ' ' + format.format(rule);
    }

    public String monthsToString(int num) {
        double[] limits = {0, 1, 2, 5};
        String[] strings = {"месяцев", "месяц", "месяца", "месяцев"};
        ChoiceFormat format = new ChoiceFormat(limits, strings);
        int rule = 11 <= (num % 100) && (num % 100) <= 14 ? num : num % 10;
        return String.valueOf(num) + ' ' + format.format(rule);
    }


}
