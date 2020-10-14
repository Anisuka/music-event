package adeo.leroymerlin.cdp;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EventControllerIntegrationTest {

    @Autowired
    private EventController eventController;

    @Test
    @DisplayName("Given Event Controller When Find Events Then Return All Events")
    public void givenEventControllerWhenFindEventsThenReturnAllEvents() {
        List<Event> result = eventController.findEvents();
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("Given Event Controller When Find Events By Member Name Then Return Only One Event")
    public void givenEventControllerWhenFindEventsByMemberNameThenReturnOnlyOneEvent() {
        List<Event> result = eventController.findEvents("Wal");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Given Event Controller When Update Event On None Existing Event Then Throw ResourceNotFoundException")
    public void givenEventControllerWhenUpdateEventOnNoneExistingEventThenThrowResourceNotFoundException() {
        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> {
            eventController.updateEvent(-1L, new Event());
        });
    }

    @Test
    @DisplayName("Given Event Controller When Delete Event By ID  Then Event no longer exists")
    public void givenEventControllerWhenDeleteEventByIdThenEventNoLongerExists() {
        eventController.deleteEvent(1000L);
        assertThat(eventController.findEvents().stream().anyMatch(e -> e.getId() == 1000L)).isEqualTo(false);
    }

    @Test
    @DisplayName("Given Event Controller When update event then Event is updated")
    public void givenEventControllerWhenUpdateEventThenEventIsUpdated() throws ResourceNotFoundException {
        Random random = new Random();
        String updatedComment = random.ints(97, 122 + 1)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Integer updatedNbStars = random.nextInt(6);
        eventController.updateEvent(1000L, Event.builder().setNbStars(updatedNbStars).setComment(updatedComment).build());

        assertThat(eventController.findEvents().stream().anyMatch(e -> e.getId() == 1000L
                && updatedComment.equals(e.getComment())
                && updatedNbStars == e.getNbStars())).isEqualTo(true);
    }

}
