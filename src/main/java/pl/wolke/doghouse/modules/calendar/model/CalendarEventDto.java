package pl.wolke.doghouse.modules.calendar.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CalendarEventDto {
    private int id;
    private String dateFrom;
    private String dateTo;
    private String color;
    private String commonKey;
    private String title;
    private boolean allDay;
    private String description;
    private String icon;
    private Long userId;
}
