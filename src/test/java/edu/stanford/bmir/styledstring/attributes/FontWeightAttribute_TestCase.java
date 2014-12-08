package edu.stanford.bmir.styledstring.attributes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 07/12/14
 */
@RunWith(MockitoJUnitRunner.class)
public class FontWeightAttribute_TestCase {


    private FontWeightAttribute fontWeightAttribute;

    private FontWeightAttribute otherFontWeightAttribute;

    private FontWeight fontWeight;

    @Before
    public void setUp() throws Exception {
        fontWeight = FontWeight.BOLD;
        fontWeightAttribute = new FontWeightAttribute(fontWeight);
        otherFontWeightAttribute = new FontWeightAttribute(fontWeight);
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(fontWeightAttribute, is(equalTo(fontWeightAttribute)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(fontWeightAttribute, is(not(equalTo(null))));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(fontWeightAttribute, is(equalTo(otherFontWeightAttribute)));
    }

    @Test
    public void shouldHaveSameHashCodeAsOther() {
        assertThat(fontWeightAttribute.hashCode(), is(otherFontWeightAttribute.hashCode()));
    }

    @Test
    public void shouldGenerateToString() {
        assertThat(fontWeightAttribute.toString(), containsString(fontWeight.toString()));
    }
}