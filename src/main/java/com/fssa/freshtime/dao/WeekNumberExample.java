package com.fssa.freshtime.dao;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekNumberExample {
    public static void main(String[] args) {
        // Define the date
        LocalDate date = LocalDate.of(2023, 8, 11);

        // Define the WeekFields object based on ISO standards and the desired locale
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // Get the week number for the given date
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());

        System.out.println("Week number for " + date + " is " + weekNumber);
    }
}

