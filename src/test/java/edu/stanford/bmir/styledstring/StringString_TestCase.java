package edu.stanford.bmir.styledstring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
@RunWith(MockitoJUnitRunner.class)
public class StringString_TestCase {

    public static final String MY_TEXT = "MyText";

    @Mock
    private StyledStringMarkup markup;

    private StyledString styledString;

    private List<StyledStringMarkup> markupList;

    @Before
    public void setUp() throws Exception {
        markupList = Arrays.asList(markup);
        styledString = new StyledString(MY_TEXT, markupList);
    }


    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfTextIsNull() {
        new StyledString(null, markupList);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfMarkupListIsNull() {
        new StyledString(MY_TEXT, null);
    }

    @Test
    public void shouldReturnSuppliedString() {
        assertThat(styledString.getString(), is(MY_TEXT));
    }

    @Test
    public void shouldReturnCorrectLength() {
        assertThat(styledString.length(), is(MY_TEXT.length()));
    }

    @Test
    public void shouldReturnFalseForIsEmpty() {
        assertThat(styledString.isEmpty(), is(false));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsForNegativeIndex() {
        styledString.charAt(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsForTooLargeIndex() {
        styledString.charAt(MY_TEXT.length());
    }

    @Test
    public void shouldEqualSelf() {
        assertThat(styledString.equals(styledString), is(true));
    }

    @Test
    public void shouldEqualOther() {
        assertThat(styledString.equals(new StyledString(MY_TEXT, markupList)), is(true));
    }

    @Test
    public void shouldHaveSameHashCode() {
        assertThat(styledString.hashCode(), is(new StyledString(MY_TEXT, markupList).hashCode()));
    }

    @Test
    public void toStringShouldContainText() {
        assertThat(styledString.toString().contains(MY_TEXT), is(true));
    }

    @Test
    public void shouldReturnZeroForCompareToSelf() {
        assertThat(styledString.compareTo(styledString), is(0));
    }

}
