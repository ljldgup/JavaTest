package remind.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import remind.model.Event;

public interface EventRespository  extends CrudRepository<Event, Integer>{
	
	List<EventType> findByType(String type);
	
	List<Event> findByStartDateBeforeAndStartDateAfter(Date beforeDate, Date afterDate);
}
