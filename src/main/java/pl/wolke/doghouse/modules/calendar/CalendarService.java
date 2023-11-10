package pl.wolke.doghouse.modules.calendar;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.wolke.doghouse.modules.calendar.model.CalendarEvent;

import java.util.Date;
import java.util.Set;

@Service
public interface CalendarService {
    Set<CalendarEvent> getEvents(Date start, Date end);

    @Transactional
    boolean save(CalendarEvent event);

    @Transactional
    CalendarServiceImpl.Status delete(Integer id, boolean confirm, boolean deleteWithCommon);

    @Transactional
    CalendarServiceImpl.Status resize(Integer id, Date endDate, boolean confirm, boolean resizeAll);

    @Transactional
    CalendarServiceImpl.Status move(Integer id, Date startDate, Date endDate, boolean allDay, boolean confirm, boolean resizeAll);

    public enum Status {
        OK, CONFIRM, CONFIRM_MULTI, NOT_FOUND, ERROR
    }
}
