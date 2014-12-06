package edu.stanford.bmir.styledstring;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
public class EmptyStyledString_TestCase {

    private StyledString emptyString;

    @Before
    public void setUp() throws Exception {
        emptyString = StyledString.emptyString();
    }

    @Test
    public void shouldNotBeNull() {
        assertThat(emptyString != null, is(true));
    }

    @Test
    public void shouldBeEmpty() {
        assertThat(emptyString.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnStringEqualToEmptyString() {
        assertThat(emptyString.getString(), is(""));
    }

}
