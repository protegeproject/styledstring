
package edu.stanford.bmir.styledstring;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

@RunWith(MockitoJUnitRunner.class)
public class StyledStringLink_TestCase {

    private StyledStringLink styledStringLink;
    private int startIndex = 1;
    private int endIndex = 2;
    @Mock
    private Object linkObject;

    @Before
    public void setUp() {
        styledStringLink = new StyledStringLink(startIndex, endIndex, linkObject);
    }

    @Test
    public void shouldReturnSupplied_startIndex() {
        assertThat(styledStringLink.getStartIndex(), is(this.startIndex));
    }

    @Test
    public void shouldReturnSupplied_endIndex() {
        assertThat(styledStringLink.getEndIndex(), is(this.endIndex));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_linkObject_IsNull() {
        new StyledStringLink(startIndex, endIndex, null);
    }

    @Test
    public void shouldReturnSupplied_linkObject() {
        assertThat(styledStringLink.getLinkObject(), is(this.linkObject));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(styledStringLink, is(styledStringLink));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(styledStringLink.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(styledStringLink, is(new StyledStringLink(startIndex, endIndex, linkObject)));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_startIndex() {
        assertThat(styledStringLink, is(not(new StyledStringLink(3, endIndex, linkObject))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_endIndex() {
        assertThat(styledStringLink, is(not(new StyledStringLink(startIndex, 4, linkObject))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_linkObject() {
        assertThat(styledStringLink, is(not(new StyledStringLink(startIndex, endIndex, Mockito.mock(Object.class)))));
    }

    @Test
    public void shouldBeEqualToOtherHashCode() {
        assertThat(styledStringLink.hashCode(), is(new StyledStringLink(startIndex, endIndex, linkObject).hashCode()));
    }

    @Test
    public void shouldImplementToString() {
        assertThat(styledStringLink.toString(), startsWith("StyledStringLink"));
    }


    @Test
    public void shouldReturn_true_For_containsIndex() {
        assertThat(styledStringLink.containsIndex(1), is(true));
    }

    @Test
    public void shouldReturn_false_For_containsIndex_0() {
        assertThat(styledStringLink.containsIndex(0), is(false));
    }

    @Test
    public void shouldReturn_false_For_containsIndex_2() {
        assertThat(styledStringLink.containsIndex(2), is(false));
    }

    @Test
    public void shouldReturn_false_For_containsIndex_3() {
        assertThat(styledStringLink.containsIndex(3), is(false));
    }

}
