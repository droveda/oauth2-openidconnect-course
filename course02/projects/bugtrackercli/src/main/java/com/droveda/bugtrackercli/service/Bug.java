package com.droveda.bugtrackercli.service;

import jakarta.validation.constraints.NotNull;

public record Bug(
        Long id,
        String submitter,
        @NotNull String headline,
        @NotNull String description,
        @NotNull String project,
        BugSeverity severity,
        BugState state

) {

    public enum BugSeverity {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum BugState {
        OPEN, CLOSED
    }

    public static Bug emptyBug(String submitter) {
        return new Bug(null, submitter, null, null, null, BugSeverity.LOW, BugState.OPEN);
    }


}
