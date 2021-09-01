package cz.scholz.wordcloud.streams;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TopologyProducerTest {
    @Test
    public void testSpecialCharactersRemoval()  {
        assertThat(TopologyProducer.stripSpecialCharacters("#avfc"), is("avfc"));
        assertThat(TopologyProducer.stripSpecialCharacters("@jakub"), is("jakub"));
        assertThat(TopologyProducer.stripSpecialCharacters("@jakub:"), is("jakub"));
        assertThat(TopologyProducer.stripSpecialCharacters("@jakub..."), is("jakub"));
        assertThat(TopologyProducer.stripSpecialCharacters("etc..."), is("etc"));
        assertThat(TopologyProducer.stripSpecialCharacters("...."), is(""));
    }
}
