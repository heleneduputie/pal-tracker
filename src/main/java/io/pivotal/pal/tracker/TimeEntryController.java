package io.pivotal.pal.tracker;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class TimeEntryController {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {

        TimeEntry te = this.timeEntryRepository.create(timeEntryToCreate);

        return new ResponseEntity(te, HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        ResponseEntity value = new ResponseEntity(HttpStatus.NOT_FOUND);

        TimeEntry timeEntry = this.timeEntryRepository.find(timeEntryId);

        if(Objects.nonNull(timeEntry)) {
            value = new ResponseEntity(timeEntry, HttpStatus.OK);
        }

        return value;
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity(this.timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {

        ResponseEntity value = new ResponseEntity(HttpStatus.NOT_FOUND);

        TimeEntry te = this.timeEntryRepository.update(timeEntryId, expected);

        if(Objects.nonNull(te)) {
            value = new ResponseEntity(te, HttpStatus.OK);
        }

        return value;
    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {

        this.timeEntryRepository.delete(timeEntryId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
