package com.fssa.freshtime.dao;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

//public class WeekNumberExample {
//    public static void main(String[] args) {
//        // Define the date
//        LocalDate date = LocalDate.of(2023, 8, 11);
//
//        // Define the WeekFields object based on ISO standards and the desired locale
//        WeekFields weekFields = WeekFields.of(Locale.getDefault());
//
//        // Get the week number for the given date
//        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
//
//        System.out.println("Week number for " + date + " is " + weekNumber);
//    }
//}
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeekStartDateEndDate {

    public static void main(String[] args) {
        // Get user input date in string format
        String userInputDate = "2023-08-12"; // Replace with user input

        // Parse user input date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate userDate = LocalDate.parse(userInputDate, dateFormatter);

        // Calculate start and end dates of the week
        LocalDate startOfWeek = userDate.minusDays(userDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
        LocalDate endOfWeek = userDate.plusDays(DayOfWeek.SUNDAY.getValue() - userDate.getDayOfWeek().getValue());

        // Print the results
//        System.out.println("User Input Date: " + userDate);
//        System.out.println("Start of the Week: " + startOfWeek);
//        System.out.println("End of the Week: " + endOfWeek);

        System.out.println(userDate.getDayOfWeek().getValue());
        System.out.println(DayOfWeek.MONDAY.getValue());
    }
}


