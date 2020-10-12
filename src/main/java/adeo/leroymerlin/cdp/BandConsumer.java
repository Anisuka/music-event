package adeo.leroymerlin.cdp;

import java.util.function.Consumer;

@FunctionalInterface
interface BandConsumer extends Consumer<Band> {
    Consumer<Band> updateName = b -> b.setName(HelperFunction.newTitle.apply(b.getName(), b.getMembers().size()));
}
