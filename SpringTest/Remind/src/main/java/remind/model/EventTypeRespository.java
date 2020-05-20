package remind.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import remind.model.EventType;

public interface EventTypeRespository  extends CrudRepository<EventType, Integer>{

	List<EventType> findByName(String name);

}
