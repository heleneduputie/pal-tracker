package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Repository("TimeEntryRepository")
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Map<Long, TimeEntry> timeEntriesMap = new HashMap<>();

    Long lastId = 0L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        lastId ++;

        LocalDate formattedDate = LocalDate.parse(timeEntry.getDate().format(formatter));

        TimeEntry newTimeEntry = new TimeEntry(lastId, timeEntry.getProjectId(), timeEntry.getUserId(), formattedDate, timeEntry.getHours());
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

            LocalDate formattedDate = LocalDate.parse(timeEntry.getDate().format(formatter));

            te.setProjectId(timeEntry.getProjectId());
            te.setUserId(timeEntry.getUserId());
            te.setDate(formattedDate);
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
