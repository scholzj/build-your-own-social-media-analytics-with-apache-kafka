package cz.scholz.tagcloud.streams;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TopologyProducerTest {
    @Test
    public void testSpecialCharactersRemoval()  {
        assertThat(TopologyProducer.stripSpecialCharactersFromTheEnd("#avfc"), is("#avfc"));
        assertThat(TopologyProducer.stripSpecialCharactersFromTheEnd("@jakub"), is("@jakub"));
        assertThat(TopologyProducer.stripSpecialCharactersFromTheEnd("@jakub:"), is("@jakub"));
        assertThat(TopologyProducer.stripSpecialCharactersFromTheEnd("@jakub..."), is("@jakub"));
        assertThat(TopologyProducer.stripSpecialCharactersFromTheEnd("etc..."), is("etc"));
        assertThat(TopologyProducer.stripSpecialCharactersFromTheEnd("...."), is(""));
    }
}
