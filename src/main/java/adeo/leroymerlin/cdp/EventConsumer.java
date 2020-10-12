package adeo.leroymerlin.cdp;

import java.util.function.Consumer;

@FunctionalInterface
interface EventConsumer extends Consumer<Event> {
    Consumer<Event> updateTitle = b -> b.setTitle(HelperFunction.newTitle.apply(b.getTitle(), b.getBands().size()));
}
