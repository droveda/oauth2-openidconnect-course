package com.droveda.bugtrackercli.service;

import java.util.List;

public interface IBugTrackerService {

    Bug createBug(Bug bug);

    Bug updateBug(Bug bug);

    List<Bug> findAllBugs();

    boolean deleteBug(Long bugId);

    Bug getBug(Long bugId);

    BugTrackerConfiguration getConfiguration();

    void addProject(String newProject);

    void removeProject(String project);

    BugStatistics getBugStatistics(String token);

}
