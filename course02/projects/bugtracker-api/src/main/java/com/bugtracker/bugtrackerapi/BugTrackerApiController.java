package com.bugtracker.bugtrackerapi;

import com.bugtracker.bugtrackerapi.service.Bug;
import com.bugtracker.bugtrackerapi.service.BugStatistics;
import com.bugtracker.bugtrackerapi.service.BugTrackerConfiguration;
import com.bugtracker.bugtrackerapi.service.BugTrackerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BugTrackerApiController {

    private final BugTrackerService bugService;

    public BugTrackerApiController(BugTrackerService bugService) {
        this.bugService = bugService;
    }

    @PostMapping("/bugtrackerapi")
    public Bug createBug(@Valid @RequestBody Bug bug) {
        return bugService.createBug(bug);
    }

    @PutMapping("/bugtrackerapi")
    public Bug updateBug(@Valid @RequestBody Bug bug) {
        return bugService.updateBug(bug);
    }

    @GetMapping("/bugtrackerapi/{bugId}")
    public Bug getBug(@PathVariable Long bugId) {
        return bugService.getBug(bugId);
    }

    @GetMapping("/bugtrackerapi")
    public List<Bug> findAllBugs() {
        return bugService.findAllBugs();
    }

    @DeleteMapping("/bugtrackerapi/{bugId}")
    public boolean deleteBug(@PathVariable Long bugId) {
        return bugService.deleteBug(bugId);
    }

    @GetMapping("/bugtrackerapi/statistics")
    public BugStatistics getBugStatistics() {
        return bugService.getBugStatistics();
    }

    @GetMapping("/bugtrackerapi/configuration")
    public BugTrackerConfiguration getConfiguration() {
        return bugService.getConfiguration();
    }

    @PostMapping("/bugtrackerapi/administration/project/{project}")
    public void addProject(@PathVariable @NotNull String project) {
        bugService.addProject(project);
    }

    @DeleteMapping("/bugtrackerapi/administration/project/{project}")
    public void removeProject(@PathVariable @NotNull String project) {
        bugService.removeProject(project);
    }


}
