package adeo.leroymerlin.cdp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
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
public class EventServiceTest {

    private static Event summerEvent;

    private static Event springEvent;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        Band Metallica = initBand("Metallica", Arrays.asList("Alex", "David"));
        Band Nirvana = initBand("Nirvana", Arrays.asList("Kurt", "Cobain"));
        Set<Band> summerBands = new HashSet<>();
        summerBands.addAll(Arrays.asList(Metallica, Nirvana));
        summerEvent = Event.builder().setTitle("Summer Event").setComment("Best Summer Ever").setNbStars(5).setBands(summerBands).build();

        Set<Band> springBands = new HashSet<>();
        springBands.add(Nirvana);
        springEvent = Event.builder().setTitle("Spring Event").setComment("Best Spring Ever").setNbStars(2).setBands(springBands).build();

    }


    @Test
    @DisplayName("Given 2 Events in database when EventService getEvent then returns 2")
    void whenFindAllThenReturnListOfEntities() {
        Mockito.when(eventRepository.findAll()).thenReturn(Arrays.asList(springEvent, summerEvent));
        assertThat(eventService.getEvents().size()).isEqualTo(2);
        Mockito.verify(eventRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Given 2 Events in database when EventService findById then returns event corresponding to that id")
    void whenFindByIdThenReturnOneEntity() {
        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.of(springEvent));
        assertThat(eventService.findById(1L).get()).isEqualTo(springEvent);
        Mockito.verify(eventRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Given 2 Events in database when EventService deleteById then eventRepository deleteById is called only once")
    void whenDeleteByIdThenMethodCalledOnlyOnce() {
        eventService.deleteById(1L);
        Mockito.verify(eventRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Given 2 Events in database when EventService updateEventReview then eventRepository eventRepository is called only once")
    void whenSaveThenMethodCalledOnlyOnce() {
        eventService.updateEventReview(summerEvent);
        Mockito.verify(eventRepository, Mockito.times(1)).save(summerEvent);
    }


    @Test
    @DisplayName("When Filter Event By Band Name Return None Empty Result")
    void WhenFilterEventByBandNameReturnNoneEmptyResult() throws ResourceNotFoundException {
        //GIVEN

        //WHEN
        Mockito.when(eventRepository.findAll()).thenReturn(Arrays.asList(summerEvent, springEvent));

        //THEN
        assertThat(eventService.getFilteredEvents("bain").size()).isEqualTo(2);
    }

    @Test
    @DisplayName("When Filter Event By Band Name Return Empty Result")
    void WhenFilterEventByBandNameReturnEmptyResult() throws ResourceNotFoundException {
        //GIVEN

        //WHEN
        Mockito.when(eventRepository.findAll()).thenReturn(Arrays.asList(summerEvent, springEvent));

        //THEN
        assertThat(eventService.getFilteredEvents("bin").size()).isEqualTo(0);
    }

    private static Band initBand(String bandName, List<String> membersName) {
        Band Metallica = new Band();
        Metallica.setName("Metallica");
        Set<Member> members = new HashSet<>();
        members.addAll(membersName.stream().map(name -> new Member(name)).collect(Collectors.toList()));
        Metallica.setMembers(members);
        return Metallica;
    }
}
