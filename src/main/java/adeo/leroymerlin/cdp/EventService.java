package adeo.leroymerlin.cdp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    public Optional<Event> findById(Long id){
        return eventRepository.findById(id);
    }

    public void updateEventReview(Event updatedEvent){
        eventRepository.save(updatedEvent);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return events.stream()
                .peek(EventConsumer.updateTitle)
                .filter(event -> event.getBands().stream()
                        .peek(BandConsumer.updateName)
                        .anyMatch(e -> e.getMembers().stream()
                                .anyMatch(HelperFunction.memberNameContains(query))))
                .collect(Collectors.toList());
    }
}
