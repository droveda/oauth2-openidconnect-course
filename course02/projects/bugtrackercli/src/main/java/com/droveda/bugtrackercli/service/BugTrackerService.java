package com.droveda.bugtrackercli.service;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@Service
public class BugTrackerService {

    private static final List<String> INITIAL_APPS = List.of("App1", "App2", "App3", "App4", "App5");

    private final AtomicReference<BugTrackerConfiguration> trackerConfig = new AtomicReference<>(new BugTrackerConfiguration(INITIAL_APPS));

    private final AtomicLong idGenerator = new AtomicLong();

    private final List<Bug> bugs = Collections.synchronizedList(new ArrayList<>());

    @PostConstruct
    public void initialize() {
        long id = idGenerator.getAndIncrement();
        Bug b1 = new Bug(id, "johndoe",
                "Test does not work",
                "teste are not working because...",
                "App1",
                Bug.BugSeverity.LOW, Bug.BugState.OPEN);

        bugs.add(b1);

        long id2 = idGenerator.getAndIncrement();
        Bug b2 = new Bug(id2, "johndoe",
                "Integration does not work",
                "Integration is not working because...",
                "App2",
                Bug.BugSeverity.HIGH, Bug.BugState.OPEN);

        bugs.add(b2);

        long id3 = idGenerator.getAndIncrement();
        Bug b3 = new Bug(id3, "johndoe",
                "Null pointer exception when saving",
                "Null pointer...",
                "App3",
                Bug.BugSeverity.LOW, Bug.BugState.OPEN);

        bugs.add(b3);

        long id4 = idGenerator.getAndIncrement();
        Bug b4 = new Bug(id4, "johndoe",
                "Out of memory error",
                "OOO...",
                "App3",
                Bug.BugSeverity.HIGH, Bug.BugState.CLOSED);

        bugs.add(b4);
    }

    public Bug createBug(Bug bug) {
        //extract the user of the application
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OidcUser principal = (OidcUser) token.getPrincipal();

        //create a new bug
        long id = idGenerator.getAndIncrement();
        Bug clonedBug = new Bug(
                id,
                principal.getPreferredUsername(),
                bug.headline(),
                bug.description(),
                bug.project(),
                bug.severity(),
                bug.state()
        );

        bugs.add(clonedBug);

        return clonedBug;
    }

    public Bug updateBug(Bug bug) {
        int index = IntStream.range(0, this.bugs.size())
                .filter(i -> this.bugs.get(i).equals(bug.id()))
                .findFirst()
                .orElseThrow();

        this.bugs.set(index, bug);

        return bug;
    }

    public List<Bug> findAllBugs() {
        return List.copyOf(this.bugs);
    }

    public boolean deleteBug(Long bugId) {
        return this.bugs.removeIf(bug -> bug.id().equals(bugId));
    }

    public Bug getBug(Long bugId) {
        return this.bugs.stream()
                .filter(b -> b.id().equals(bugId))
                .findFirst()
                .orElseThrow();
    }

    public BugTrackerConfiguration getConfiguration() {
        return trackerConfig.get();
    }

    public void addProject(String newProject) {
        BugTrackerConfiguration configuration = trackerConfig.get();
        List<String> projects = configuration.projects();

        List<String> newApps = new ArrayList<>();
        newApps.add(newProject);
        newApps.addAll(projects);

        trackerConfig.set(new BugTrackerConfiguration(newApps));
    }

    public void removeProject(String project) {
        BugTrackerConfiguration configuration = trackerConfig.get();
        List<String> apps = configuration.projects();

        List<String> newAppList = apps.stream()
                .filter(a -> !a.equalsIgnoreCase(project)).toList();

        trackerConfig.set(new BugTrackerConfiguration(newAppList));
    }

}
