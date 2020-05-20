package remind.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import remind.model.Event;
import remind.model.EventRespository;
import remind.model.EventTypeRespository;

@Controller
@RequestMapping(path = "/Event")
public class EventRestService {
	
	@Autowired
	private EventRespository eventRespository;
	
	@Autowired
	private EventTypeRespository eventTypeRespository;
	
	@GetMapping(path = "/add") // Map ONLY GET Requests
	public @ResponseBody String addEvent(@RequestParam String type, @RequestParam String description, 
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		if (eventTypeRespository.findByName(type).size() < 1) {
			return String.format("No such type!");
		}
		Event event = new Event();
		event.setType(type);
		event.setDescription(description);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		eventRespository.save(event);
		return String.format("Added, %d", event.getId());
	}
	
	@GetMapping(path = "/update") // Map ONLY GET Requests
	public @ResponseBody String updateEvent(@RequestParam int id, @RequestParam String type, @RequestParam String description, 
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request


		if (eventTypeRespository.findByName(type).size() < 1) {
			return String.format("No such type!");
		}
		
		Event event = eventRespository.findById(id).get();
		event.setType(type);
		event.setDescription(description);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		eventRespository.save(event);
		return String.format("Updated, %d", event.getId());
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Event> getAllEvents() {
		// This returns a JSON or XML with the users
		return eventRespository.findAll();
	}

	
	@GetMapping(path = "/delete")
	public @ResponseBody String deleteEvent(@RequestParam int id) {
		eventRespository.deleteById(id);
		return "Deleted";
	}
	
	@GetMapping(path = "/test")
	public @ResponseBody List<Event> test(@RequestParam(required = false) Date beforeDate, @RequestParam(required = false) Date afterDate) {
		return eventRespository.findByStartDateBeforeAndStartDateAfter(beforeDate, afterDate);
	}
	
}
