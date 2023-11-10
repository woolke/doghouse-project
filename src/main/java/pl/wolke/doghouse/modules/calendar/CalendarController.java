package pl.wolke.doghouse.modules.calendar;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;
import pl.wolke.doghouse.modules.calendar.model.CalendarEvent;
import pl.wolke.doghouse.modules.calendar.model.CalendarEventDto;
import pl.wolke.doghouse.modules.calendar.model.CalendarEventsRequest;
import pl.wolke.doghouse.utils.StaticFunc;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CalendarEventDto event) {
        try {
            CalendarEvent calendarEvent = new CalendarEvent(event);
//			CalendarEvent calendarEvent = new CalendarEvent();
            return ResponseEntity.ok(calendarService.save(calendarEvent));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/events")
    public ResponseEntity<Set<CalendarEventDto>> getEvents(@RequestParam String from, @RequestParam String to) {
        try {
            Date dateFrom = StaticFunc.parseDateFromString(from);
            Date dateTo = StaticFunc.parseDateFromString(to);
            Set<CalendarEventDto> events = calendarService.getEvents(dateFrom, dateTo)
                    .stream()
                    .map(CalendarEvent::dto)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(events);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<CalendarService.Status> delete(@RequestBody CalendarEventsRequest deleteReqest) {
        try {
            if (!CalendarEventsRequest.Type.DELETE.equals(deleteReqest.getType()))
                throw new RequestRejectedException("Brak zdeklaorwanego typu");
            return ResponseEntity.ok(calendarService.delete(deleteReqest.getId(), deleteReqest.isConfirm(), deleteReqest.isDeleteWithCommon()));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/resize")
    public ResponseEntity<CalendarService.Status> resize(@RequestBody CalendarEventsRequest resizeRequest) {
        try {
            if (!CalendarEventsRequest.Type.RESIZE.equals(resizeRequest.getType()))
                throw new RequestRejectedException("Brak zdeklarowanego typu");
            return ResponseEntity.ok(calendarService.resize(
                    resizeRequest.getId(),
                    StaticFunc.parseDateFromString(resizeRequest.getDateTo()),
                    resizeRequest.isConfirm(),
                    resizeRequest.isResizeAll()));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/move")
    public ResponseEntity<CalendarService.Status> move(@RequestBody CalendarEventsRequest moveRequest) {
        try {
            if (!CalendarEventsRequest.Type.MOVE.equals(moveRequest.getType()))
                throw new RequestRejectedException("Brak zdeklaorwanego typu");
            return ResponseEntity.ok(calendarService.move(
                    moveRequest.getId(),
                    StaticFunc.parseDateFromString(moveRequest.getDateFrom()),
                    StaticFunc.parseDateFromString(moveRequest.getDateTo()),
                    moveRequest.isAllDay(),
                    moveRequest.isConfirm(),
                    moveRequest.isRemoveCommonKey()));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
