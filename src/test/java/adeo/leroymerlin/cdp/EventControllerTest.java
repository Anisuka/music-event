package adeo.leroymerlin.cdp;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EventControllerTest {

    private static Event summerEvent;

    private static Event springEvent;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        summerEvent = Event.builder().setTitle("Summer Event").setComment("Best Summer Ever").setNbStars(5).build();
        springEvent = Event.builder().setTitle("Spring Event").setComment("Best Spring Ever").setNbStars(2).build();
    }


    @Test
    @DisplayName("When eventService returns no event, then controller find events return no events too")
    void whenGetEventsReturnNoResultThenMethodCallOnlyOnce() {
        Mockito.when(eventService.getEvents()).thenReturn(Arrays.asList());
        assertThat(eventController.findEvents().size()).isEqualTo(0);
        Mockito.verify(eventService, Mockito.times(1)).getEvents();
    }

    @Test
    @DisplayName("When eventService return 2 Events, controller find Events returns the same Events")
    void whenServiceGetEventsReturnsNEventsThenControllerFindEventsReturnSameNEvents() {
        //GIVEN
        List<Event> result = Arrays.asList(summerEvent, springEvent);

        //WHEN
        Mockito.when(eventService.getEvents()).thenReturn(result);

        //THEN
        assertThat(eventController.findEvents()).isEqualTo(result);
        Mockito.verify(eventService, Mockito.times(1)).getEvents();
    }

    @Test
    @DisplayName("When controller delete an event, then deleteById is called only once by eventService")
    void whenDeleteEventThenCallDeleteById() {
        //GIVEN

        //WHEN
        eventController.deleteEvent(5L);

        //THEN
        Mockito.verify(eventService, Mockito.times(1)).deleteById(5L);
    }

    @Test
    @DisplayName("When controller update an Event, then updateEventReview is called only once on eventService")
    void whenUpdateEventReview() throws ResourceNotFoundException {
        //GIVEN

        //WHEN
        Mockito.when(eventService.findById(5L)).thenReturn(Optional.of(summerEvent));
        eventController.updateEvent(5L, summerEvent);

        //THEN
        Mockito.verify(eventService, Mockito.times(1)).updateEventReview(summerEvent);
    }
}
