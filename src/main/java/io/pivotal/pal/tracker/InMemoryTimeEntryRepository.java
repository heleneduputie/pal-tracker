package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long, TimeEntry> timeEntriesMap = new HashMap<>();

    Long lastId = 0L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        lastId ++;

        TimeEntry newTimeEntry = new TimeEntry(lastId, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        timeEntriesMap.put(lastId, newTimeEntry);

        return newTimeEntry;
    }

    @Override
    public TimeEntry find(long id) {

        return timeEntriesMap.get(id);
    }

    @Override
    public List<TimeEntry> list() {

        return timeEntriesMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        TimeEntry te = this.find(id);

        if(Objects.nonNull(te)){


            te.setProjectId(timeEntry.getProjectId());
            te.setUserId(timeEntry.getUserId());
            te.setDate(timeEntry.getDate());
            te.setHours(timeEntry.getHours());

            timeEntriesMap.replace(id, te);
        }

        return te;
    }

    @Override
    public void delete(long id) {
        timeEntriesMap.remove(id);
    }
}
