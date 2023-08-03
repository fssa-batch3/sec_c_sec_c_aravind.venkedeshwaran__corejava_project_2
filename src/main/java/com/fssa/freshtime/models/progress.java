package com.fssa.freshtime.models;

public class progress {
    private int id;
    private int dailyProgress;
    private int weeklyProgress;
    private int monthlyProgress;
    private int overallProgress;

    public progress(int id, int dailyProgress, int weeklyProgress, int monthlyProgress, int overallProgress) {
        this.id = id;
        this.dailyProgress = dailyProgress;
        this.weeklyProgress = weeklyProgress;
        this.monthlyProgress = monthlyProgress;
        this.overallProgress = overallProgress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDailyProgress() {
        return dailyProgress;
    }

    public void setDailyProgress(int dailyProgress) {
        this.dailyProgress = dailyProgress;
    }

    public int getWeeklyProgress() {
        return weeklyProgress;
    }

    public void setWeeklyProgress(int weeklyProgress) {
        this.weeklyProgress = weeklyProgress;
    }

    public int getMonthlyProgress() {
        return monthlyProgress;
    }

    public void setMonthlyProgress(int monthlyProgress) {
        this.monthlyProgress = monthlyProgress;
    }

    public int getOverallProgress() {
        return overallProgress;
    }

    public void setOverallProgress(int overallProgress) {
        this.overallProgress = overallProgress;
    }
}
