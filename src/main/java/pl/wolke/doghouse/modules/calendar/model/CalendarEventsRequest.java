package pl.wolke.doghouse.modules.calendar.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class CalendarEventsRequest {
    private Type type;
    private Integer id;
    private String dateFrom;
    private String dateTo;
    private boolean allDay;
    private boolean deleteWithCommon;
    private boolean confirm;
    private boolean resizeAll;
    private boolean removeCommonKey;


    public enum Type {
        ADD, EDIT, DELETE, FETCH, RESIZE, MOVE
    }
}
