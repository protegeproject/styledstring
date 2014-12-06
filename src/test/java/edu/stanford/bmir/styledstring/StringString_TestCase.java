package edu.stanford.bmir.styledstring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
@RunWith(MockitoJUnitRunner.class)
public class StringString_TestCase {

    private static final String MY_TEXT = "MyText";

    private static final int STYLE_START_INDEX = 1;

    private static final int STYLE_END_INDEX = 2;

    @Mock
    private Style style;

    @Mock
    private StyledStringMarkup markup;

    private StyledString styledString;

    private List<StyledStringMarkup> markupList;

    @Before
    public void setUp() throws Exception {

        when(markup.getStart()).thenReturn(STYLE_START_INDEX);
        when(markup.getEnd()).thenReturn(STYLE_END_INDEX);
        when(markup.getStyle()).thenReturn(style);


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
    public void shouldReturnSuppliedMarkup() {
        assertThat(styledString.getMarkup(), is(markupList));
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

    @Test
    public void shouldReturnEmptyStylesListForEmptyString() {
        assertThat(styledString.getStylesAt(0), is(Collections.<Style>emptyList()));
    }

    @Test
    public void shouldReturnEmptyStylesList() {
        List<Style> stylesAt0 = styledString.getStylesAt(0);
        assertThat(stylesAt0.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnSpecifiedStylesAtStartIndex() {
        List<Style> stylesAt1 = styledString.getStylesAt(STYLE_START_INDEX);
        assertThat(stylesAt1, is(Arrays.asList(style)));
    }

}
