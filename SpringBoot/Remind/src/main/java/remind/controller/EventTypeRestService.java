package remind.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import remind.model.EventType;
import remind.model.EventTypeRespository;

@Controller
@RequestMapping(path = "/EventType")
public class EventTypeRestService {

	@Autowired
	private EventTypeRespository eventTypeRespository;

	@GetMapping(path = "/add") // Map ONLY GET Requests
	public @ResponseBody String addEventType(@RequestParam String name, @RequestParam(required = false) String description) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		if(eventTypeRespository.findByName(name).size() > 0 ) {
			return String.format("type '%s' already has one", name);
		}
		EventType et = new EventType();
		et.setName(name);
		et.setDescription(description);
		et.setPublicationDate(new Date());
		eventTypeRespository.save(et);
		return String.format("Added, %d types in total", eventTypeRespository.count());
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<EventType> getAllEventTypes() {
		// This returns a JSON or XML with the users
		return eventTypeRespository.findAll();
	}

	@GetMapping(path = "/delete")
	public @ResponseBody String deleteEvent(@RequestParam String name) {
		if(eventTypeRespository.findByName(name).size() > 0 ) {
			EventType et = eventTypeRespository.findByName(name).get(0);
			int id = et.getId();
			eventTypeRespository.deleteById(id);
			return "Deleted";
		}
		else {
			return "No such type";
		}
	}
}
