package com.gmail.bradik.resumebuilder.service;

import com.gmail.bradik.resumebuilder.dto.ResumeData;
import com.gmail.bradik.resumebuilder.dto.experiences.Experience;
import com.gmail.bradik.resumebuilder.util.DateUtil;
import com.gmail.bradik.resumebuilder.util.Mode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

@Service("ResumeDataServiceEn")
public class ResumeDataServiceEn extends ResumeDataServiceBase {

    @Override
    protected Mode getMode() {
        return Mode.EN;
    }

    @Override
    public String getPersonInfo(ResumeData data) {
        //<!--Male, 41 years old, born December 4, 1975-->
        LocalDate birthdate = DateUtil.toLocalDate(data.getPersonal().getBirthdate(), "yyyy-MM-dd");
        long age = ChronoUnit.YEARS.between(birthdate, LocalDate.now());

        String born = birthdate.format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH));

        StringBuilder sb = new StringBuilder();
        sb.append(data.getPersonal().getGender()).append(", ");
        sb.append(age).append(" years old, ");
        sb.append("born ").append(born);

        return sb.toString();
    }

    @Override
    public String getSeniority(Experience experience) {
        LocalDate start = DateUtil.toLocalDate(experience.getStart(), "yyyy-MM");

        StringBuilder sb = new StringBuilder()
                .append(start.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)).append(" ")
                .append(Integer.valueOf(start.getYear()).toString()).append(" - ");

        if (Strings.isEmpty(experience.getEnd())) {
            sb.append("to date");
        } else {
            LocalDate end = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
            sb
                    .append(end.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)).append(" ")
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
            sb.append(years);
            if (years == 1) {
                sb.append(" year");
            } else {
                sb.append(" years");
            }
            sb.append(" ");
        }

        if (months > 0 ) {
            sb.append(months);
            if (months == 1) {
                sb.append(" month");
            } else {
                sb.append(" months");
            }
        }

        return sb.toString();
    }

}
