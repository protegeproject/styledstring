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
import java.text.AttributedString;

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
public class BackgroundAttribute_TestCase {


    private BackgroundAttribute backgroundAttribute;

    private BackgroundAttribute otherBackgroundAttribute;

    @Mock
    private Color background;

    @Before
    public void setUp() throws Exception {
        backgroundAttribute = new BackgroundAttribute(background);
        otherBackgroundAttribute = new BackgroundAttribute(background);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        new BackgroundAttribute(null);
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(backgroundAttribute, is(equalTo(backgroundAttribute)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(backgroundAttribute, is(not(equalTo(null))));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(backgroundAttribute, is(equalTo(otherBackgroundAttribute)));
    }

    @Test
    public void shouldHaveSameHashCodeAsOther() {
        assertThat(backgroundAttribute.hashCode(), is(otherBackgroundAttribute.hashCode()));
    }

    @Test
    public void shouldGenerateToString() {
        assertThat(backgroundAttribute.toString(), startsWith("BackgroundAttribute"));
    }

    @Test
    public void shouldReturnCorrectCssPropertyName() {
        assertThat(backgroundAttribute.getCssPropertyName(), is("background"));
    }

    @Test
    public void shouldReturnCorrectCssPropertyValue() {
        when(background.getRGB()).thenReturn(Color.MAGENTA.getRGB());
        assertThat(backgroundAttribute.getCssPropertyValue(), is("#ff00ff"));
    }

    @Test
    public void shouldReturnCorrectAttributedStringAttribute() {
        AttributedCharacterIterator.Attribute expected = TextAttribute.BACKGROUND;
        assertThat(backgroundAttribute.getAttributedStringAttribute(), is(expected));
    }

    @Test
    public void shouldReturnCorrectAttributedStringValue() {
        assertThat(backgroundAttribute.getAttributedStringValue(), is((Object) background));
    }

    @Test
    public void shouldReturnCorrectStyledDocumentStyleAttribute() {
        assertThat(backgroundAttribute.getStyledDocumentStyleAttribute(), is(StyleConstants.Background));
    }

    @Test
    public void shouldReturnCorrectStyledDocumentAttributeValue() {
        assertThat(backgroundAttribute.getStyledDocumentStyleAttributeValue(), is((Object) background));
    }
}