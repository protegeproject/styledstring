package edu.stanford.bmir.styledstring.attributes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 07/12/14
 */
@RunWith(MockitoJUnitRunner.class)
public class ForegroundAttribute_TestCase {


    private ForegroundAttribute foregroundAttribute;

    private ForegroundAttribute otherForegroundAttribute;

    @Mock
    Color foreground;

    @Before
    public void setUp() throws Exception {
        foregroundAttribute = ForegroundAttribute.get(foreground);
        otherForegroundAttribute = ForegroundAttribute.get(foreground);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        new ForegroundAttribute(null);
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(foregroundAttribute, is(equalTo(foregroundAttribute)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(foregroundAttribute, is(not(equalTo(null))));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(foregroundAttribute, is(equalTo(otherForegroundAttribute)));
    }

    @Test
    public void shouldHaveSameHashCodeAsOther() {
        assertThat(foregroundAttribute.hashCode(), is(otherForegroundAttribute.hashCode()));
    }

    @Test
    public void shouldGenerateToString() {
        assertThat(foregroundAttribute.toString(), startsWith("ForegroundAttribute"));
    }

    @Test
    public void shouldReturnCorrectCssPropertyName() {
        assertThat(foregroundAttribute.getCssPropertyName(), is("color"));
    }

    @Test
    public void shouldReturnCorrectCssPropertyValue() {
        when(foreground.getRGB()).thenReturn(Color.MAGENTA.getRGB());
        assertThat(foregroundAttribute.getCssPropertyValue(), is("#ff00ff"));
    }

    @Test
    public void shouldReturnCorrectAttributedStringAttribute() {
        AttributedCharacterIterator.Attribute expected = TextAttribute.FOREGROUND;
        assertThat(foregroundAttribute.getAttributedStringAttribute(), is(expected));
    }

    @Test
    public void shouldReturnCorrectAttributedStringValue() {
        assertThat(foregroundAttribute.getAttributedStringValue(), is((Object) foreground));
    }

    @Test
    public void shouldReturnCorrectStyledDocumentStyleAttribute() {
        assertThat(foregroundAttribute.getStyledDocumentStyleAttribute(), is(StyleConstants.Foreground));
    }

    @Test
    public void shouldReturnCorrectStyledDocumentAttributeValue() {
        assertThat(foregroundAttribute.getStyledDocumentStyleAttributeValue(), is((Object) foreground));
    }
}