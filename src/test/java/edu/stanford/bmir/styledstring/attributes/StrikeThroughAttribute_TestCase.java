package edu.stanford.bmir.styledstring.attributes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 07/12/14
 */
@RunWith(MockitoJUnitRunner.class)
public class StrikeThroughAttribute_TestCase {


    private StrikeThroughAttribute strikeThroughAttribute;

    private StrikeThroughAttribute otherStrikeThroughAttribute;
    private StrikeThrough strikeThrough;


    @Before
    public void setUp() throws Exception {
        strikeThrough = StrikeThrough.SINGLE;
        strikeThroughAttribute = new StrikeThroughAttribute(strikeThrough);
        otherStrikeThroughAttribute = new StrikeThroughAttribute(strikeThrough);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        new StrikeThroughAttribute(null);
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(strikeThroughAttribute, is(equalTo(strikeThroughAttribute)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(strikeThroughAttribute, is(not(equalTo(null))));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(strikeThroughAttribute, is(equalTo(otherStrikeThroughAttribute)));
    }

    @Test
    public void shouldHaveSameHashCodeAsOther() {
        assertThat(strikeThroughAttribute.hashCode(), is(otherStrikeThroughAttribute.hashCode()));
    }

    @Test
    public void shouldGenerateToString() {
        assertThat(strikeThroughAttribute.toString(), startsWith("StrikeThroughAttribute"));
    }
}