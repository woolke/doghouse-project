package pl.wolke.doghouse.modules.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.wolke.doghouse.modules.calendar.model.CalendarEvent;

import java.util.Date;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Integer> {
    Set<CalendarEvent> findByDateToGreaterThanEqualAndDateFromLessThanEqual(Date startDate , Date endDate);
    Set<CalendarEvent> findByCommonKey(String commonKey);
    Integer countAllByCommonKey (String commonKey);

    @Modifying
    @Query("delete from CalendarEvent c where c.commonKey = ?1")
    void deleteAllByCommonKeyEquals(String key);
}
