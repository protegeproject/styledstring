
package edu.stanford.bmir.styledstring.swing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

@RunWith(MockitoJUnitRunner.class)
public class HitInfo_TestCase {

    private HitInfo hitInfo;

    private int lineNumber = 1;

    private int lineIndex = 2;

    private int absoluteIndex = 3;

    @Before
    public void setUp() {
        hitInfo = new HitInfo(lineNumber, lineIndex, absoluteIndex);
    }

    @Test
    public void shouldReturnSupplied_lineNumber() {
        assertThat(hitInfo.getLineNumber(), is(this.lineNumber));
    }

    @Test
    public void shouldReturnSupplied_lineIndex() {
        assertThat(hitInfo.getLineIndex(), is(this.lineIndex));
    }

    @Test
    public void shouldReturnSupplied_absoluteIndex() {
        assertThat(hitInfo.getAbsoluteIndex(), is(this.absoluteIndex));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(hitInfo, is(hitInfo));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(hitInfo.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(hitInfo, is(new HitInfo(lineNumber, lineIndex, absoluteIndex)));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_lineNumber() {
        assertThat(hitInfo, is(not(new HitInfo(4, lineIndex, absoluteIndex))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_lineIndex() {
        assertThat(hitInfo, is(not(new HitInfo(lineNumber, 5, absoluteIndex))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_absoluteIndex() {
        assertThat(hitInfo, is(not(new HitInfo(lineNumber, lineIndex, 6))));
    }

    @Test
    public void shouldBeEqualToOtherHashCode() {
        assertThat(hitInfo.hashCode(), is(new HitInfo(lineNumber, lineIndex, absoluteIndex).hashCode()));
    }

    @Test
    public void shouldImplementToString() {
        assertThat(hitInfo.toString(), startsWith("HitInfo"));
    }

}
