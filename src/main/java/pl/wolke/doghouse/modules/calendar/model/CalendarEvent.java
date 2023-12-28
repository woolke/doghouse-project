package pl.wolke.doghouse.modules.calendar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.utils.StaticFunc;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.*;
import static pl.wolke.doghouse.utils.StaticFunc.parseDateFromString;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFrom;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTo;

    private String color;
    private String commonKey;
    private String title;
    private String description;
    private String icon;
    private boolean allDay;
    @ManyToOne()
    private User user;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat sdfDto = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdfMinutes = new SimpleDateFormat("HH:mm");

    public CalendarEvent(CalendarEventDto dto) {
        User user= new User();
        user.setId(dto.getUserId() !=null ? dto.getUserId() : 1);
        this.id = dto.getId();
        this.dateFrom = parseDateFromString(dto.getDateFrom());
        this.dateTo = parseDateFromString(dto.getDateTo());
        this.color = dto.getColor();
        this.commonKey = dto.getCommonKey();
        this.allDay = dto.isAllDay();
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.icon = dto.getIcon();
        this.user = user;
    }

    public CalendarEventDto dto() {
        if (allDay)
            this.dateTo = StaticFunc.addDays(dateTo, 1);
        return CalendarEventDto.builder()
                .id(this.id)
                .dateFrom(formatDate(dateFrom))
                .dateTo(formatDate(dateTo))
                .color(this.color)
                .commonKey(this.commonKey)
                .allDay(this.allDay)
                .title(this.title)
                .description(this.description)
                .icon(this.icon)
                .userId(this.user.getId())
                .build();
    }

    private String formatDate(Date date) {
        if (isAllDay())
            return sdfDay.format(date);
        else
            return sdfDto.format(date);
    }

    public Set<CalendarEvent> convertEventToSingleEvents(String commonKey) {
        Set<CalendarEvent> events = new HashSet<>();
        Calendar start = Calendar.getInstance();
        start.setTime(getDateFrom());
        Calendar end = Calendar.getInstance();
        end.setTime(getDateTo());
        while (start.before(end)) {
            CalendarEvent tmpEvent = new CalendarEvent(this.dto());
            Calendar tempCal = Calendar.getInstance();
            tempCal.setTime(start.getTime());
            tempCal.set(HOUR_OF_DAY, end.get(HOUR_OF_DAY));
            tempCal.set(MINUTE, end.get(MINUTE));
            tempCal.set(SECOND, end.get(SECOND));
            tempCal.set(MILLISECOND, end.get(MILLISECOND));

            tmpEvent.setDateFrom(start.getTime());
            tmpEvent.setDateTo(tempCal.getTime());
            tmpEvent.setCommonKey(commonKey);
            events.add(tmpEvent);
            start.add(Calendar.DATE, 1);
        }

        return events;
    }
}
