package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO time_entries (PROJECT_ID, USER_ID, DATE, HOURS) values (?, ?, ?, ?)";

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"ID"});
            ps.setString(1, String.valueOf(timeEntry.getProjectId()));
            ps.setString(2, String.valueOf(timeEntry.getUserId()));
            ps.setString(3, String.valueOf(timeEntry.getDate()));
            ps.setString(4, String.valueOf(timeEntry.getHours()));
            return ps;
        }, keyHolder);

        timeEntry.setId(keyHolder.getKey().longValue());

        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {

        TimeEntry te = new TimeEntry();

        try {
            te = (TimeEntry) this.jdbcTemplate.queryForObject("SELECT * FROM time_entries WHERE ID = ?", new Object[]{id}, new BeanPropertyRowMapper(TimeEntry.class));
        } catch(EmptyResultDataAccessException e){
            te = null;
        }

        return te;
    }

    @Override
    public List<TimeEntry> list() {

        return this.jdbcTemplate.query("SELECT * FROM time_entries", new BeanPropertyRowMapper(TimeEntry.class));
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        String sql = "UPDATE time_entries " +
                "SET PROJECT_ID = ?, USER_ID = ?, DATE = ?, HOURS = ? " +
                "WHERE ID = ?";
        this.jdbcTemplate.update(sql, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours(), id);

        timeEntry.setId(id);

        return timeEntry;
    }

    @Override
    public void delete(long id) {

        this.jdbcTemplate.update("DELETE FROM time_entries WHERE ID = ?", id);
    }
}
