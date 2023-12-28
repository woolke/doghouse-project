package pl.wolke.doghouse.modules.calendar;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wolke.doghouse.modules.calendar.model.CalendarEvent;

import java.util.*;

import static pl.wolke.doghouse.utils.StaticFunc.equalsWithoutTime;
import static pl.wolke.doghouse.utils.StaticFunc.removeTime;

@Service
public class CalendarServiceImpl implements CalendarService {

    private final CalendarEventRepository calendarEventRepository;

    @Autowired
    public CalendarServiceImpl(CalendarEventRepository calendarEventRepository) {
        this.calendarEventRepository = calendarEventRepository;
    }

    @Override
    public Set<CalendarEvent> getEvents(Date start, Date end) {
        return calendarEventRepository.findByDateToGreaterThanEqualAndDateFromLessThanEqual(start, end);
    }

    @Override
    public boolean save(CalendarEvent event) {
        if (event.getCommonKey() != null) {
            calendarEventRepository.deleteAllByCommonKeyEquals(event.getCommonKey());
        }
        if (event.isAllDay() || equalsWithoutTime(event.getDateFrom(), event.getDateTo())) {
            if (event.isAllDay()) {
                event.setDateFrom(removeTime(event.getDateFrom()));
                event.setDateTo(removeTime(event.getDateTo()));
            }
            event.setCommonKey(UUID.randomUUID().toString());
            calendarEventRepository.save(event);
        } else {
            calendarEventRepository.saveAll(
                    event.convertEventToSingleEvents(UUID.randomUUID().toString())
            );
        }
        return true;
    }

    @Override
    @Transactional
    public Status delete(Integer id, boolean confirm, boolean deleteWithCommon) {
        try {
            Optional<CalendarEvent> event = calendarEventRepository.findById(id);
            if (event.isEmpty())
                return Status.NOT_FOUND;
            boolean multi = calendarEventRepository.countAllByCommonKey(event.get().getCommonKey()) > 1;
            if (!confirm) {
                if (multi)
                    return Status.CONFIRM_MULTI;
                else
                    return Status.CONFIRM;
            } else if (!multi || Objects.equals(deleteWithCommon, false)) {
                calendarEventRepository.delete(event.get());
            } else {
                calendarEventRepository.deleteAllByCommonKeyEquals(event.get().getCommonKey());
            }
            return Status.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return Status.ERROR;
        }
    }

    @Override
    public Status resize(Integer id, Date endDate, boolean confirm, boolean resizeAll) {
        try {
            Optional<CalendarEvent> event = calendarEventRepository.findById(id);
            Set<CalendarEvent> events = new HashSet<>();
            if (event.isEmpty())
                return Status.NOT_FOUND;
            boolean multi = calendarEventRepository.countAllByCommonKey(event.get().getCommonKey()) > 1;
            if (!confirm && multi) {
                    return Status.CONFIRM_MULTI;
            } else if (multi && Objects.equals(resizeAll, true)) {
                events = calendarEventRepository.findByCommonKey(event.get().getCommonKey());
            } else {
                events.add(event.get());
            }
            String timeString = CalendarEvent.sdfMinutes.format(endDate);
            for (CalendarEvent calendarEvent : events) {
                String dayString = CalendarEvent.sdfDay.format(calendarEvent.getDateTo());
                calendarEvent.setDateTo(CalendarEvent.sdf.parse(dayString + " " + timeString));
            }
            calendarEventRepository.saveAll(events);
            return Status.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return Status.ERROR;
        }
    }

    @Override
    public Status move(Integer id, Date startDate, Date endDate, boolean allDay, boolean confirm, boolean removeCommonKey) {
        try {
            Optional<CalendarEvent> event = calendarEventRepository.findById(id);
            if (event.isEmpty())
                return Status.NOT_FOUND;
            boolean multi = calendarEventRepository.countAllByCommonKey(event.get().getCommonKey()) > 1;
            if (!confirm && multi) {
                return Status.CONFIRM_MULTI;
            } else if (multi && Objects.equals(removeCommonKey, true)) {
                event.get().setCommonKey(UUID.randomUUID().toString());
            }
            event.get().setDateFrom(startDate);
            event.get().setDateTo(endDate);
            event.get().setAllDay(allDay);
            calendarEventRepository.save(event.get());
            return Status.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return Status.ERROR;
        }
    }
}
