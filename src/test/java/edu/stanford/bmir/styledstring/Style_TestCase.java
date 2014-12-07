package edu.stanford.bmir.styledstring;

import com.google.common.collect.Lists;
import edu.stanford.bmir.styledstring.attributes.StyleAttribute;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 07/12/14
 */
@RunWith(MockitoJUnitRunner.class)
public class Style_TestCase {

    private Collection<StyleAttribute> styleAttributes;

    @Mock
    private StyleAttribute firstAttribute;

    @Mock
    private StyleAttribute secondAttribute;

    private Style style;

    private Style otherStyle;

    @Before
    public void setUp() throws Exception {
        styleAttributes = Lists.newArrayList();
        styleAttributes.add(firstAttribute);
        styleAttributes.add(secondAttribute);
        style = new Style(styleAttributes);
        otherStyle = new Style(styleAttributes);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        new Style(null);
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(style, is(equalTo(style)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(style, is(not(equalTo(null))));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(style, is(equalTo(otherStyle)));
    }

    @Test
    public void shouldHaveSameHashCodeAsOther() {
        assertThat(style.hashCode(), is(otherStyle.hashCode()));
    }

    @Test
    public void shouldReturnSuppliedValue() {
        assertThat(style.getStyleAttributes(), is(equalTo(styleAttributes)));
    }

    @Test
    public void shouldReturnAttributesInSuppliedOrder() {
        List<StyleAttribute> attributeList = style.getStyleAttributes();
        assertThat(attributeList.get(0), is(firstAttribute));
        assertThat(attributeList.get(1), is(secondAttribute));
    }
}
