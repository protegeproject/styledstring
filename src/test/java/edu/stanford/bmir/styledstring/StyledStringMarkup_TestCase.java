package edu.stanford.bmir.styledstring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
@RunWith(MockitoJUnitRunner.class)
public class StyledStringMarkup_TestCase {

    private static final int START = 2;

    private static final int END = 5;

    private StyledStringMarkup markup;

    @Mock
    private Style style;

    @Before
    public void setUp() throws Exception {
        markup = new StyledStringMarkup(START, END, style);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfStyleIsNull() {
        new StyledStringMarkup(START, END, null);
    }

    @Test
    public void shouldReturnSuppliedStart() {
        assertThat(markup.getStart(), is(START));
    }

    @Test
    public void shouldReturnSuppliedEnd() {
        assertThat(markup.getEnd(), is(END));
    }

    @Test
    public void shouldReturnSuppliedStyle() {
        assertThat(markup.getStyle(), is(style));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(markup, is(markup));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(markup, is(new StyledStringMarkup(START, END, style)));
    }

    @Test
    public void shouldHaveSameHashCode() {
        assertThat(markup.hashCode(), is(new StyledStringMarkup(START, END, style).hashCode()));
    }

    @Test
    public void shouldCompareEqual() {
        assertThat(markup.compareTo(markup), is(0));
    }
}
