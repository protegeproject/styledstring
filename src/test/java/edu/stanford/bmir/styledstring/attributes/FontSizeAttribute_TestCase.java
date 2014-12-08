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
public class FontSizeAttribute_TestCase {

    public static final int FONT_SIZE = 50;

    private FontSizeAttribute fontSizeAttribute;

    private FontSizeAttribute otherFontSizeAttribute;


    @Before
    public void setUp() throws Exception {
        fontSizeAttribute = new FontSizeAttribute(FONT_SIZE);
        otherFontSizeAttribute = new FontSizeAttribute(FONT_SIZE);
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(fontSizeAttribute, is(equalTo(fontSizeAttribute)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(fontSizeAttribute, is(not(equalTo(null))));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(fontSizeAttribute, is(equalTo(otherFontSizeAttribute)));
    }

    @Test
    public void shouldHaveSameHashCodeAsOther() {
        assertThat(fontSizeAttribute.hashCode(), is(otherFontSizeAttribute.hashCode()));
    }

    @Test
    public void shouldGenerateToString() {
        assertThat(fontSizeAttribute.toString(), startsWith("FontSizeAttribute"));
    }

    @Test
    public void shouldReturnCorrectCssPropertyName() {
        assertThat(fontSizeAttribute.getCssPropertyName(), is("font-size"));
    }

    @Test
    public void shouldReturnCorrectCssPropertyValue() {
        assertThat(fontSizeAttribute.getCssPropertyValue(), is("50pt"));
    }

    @Test
    public void shouldReturnCorrectAttributedStringAttribute() {
        AttributedCharacterIterator.Attribute expected = TextAttribute.SIZE;
        assertThat(fontSizeAttribute.getAttributedStringAttribute(), is(expected));
    }

    @Test
    public void shouldReturnCorrectAttributedStringValue() {
        assertThat(fontSizeAttribute.getAttributedStringValue(), is((Object) FONT_SIZE));
    }

    @Test
    public void shouldReturnCorrectStyledDocumentStyleAttribute() {
        assertThat(fontSizeAttribute.getStyledDocumentStyleAttribute(), is(StyleConstants.Size));
    }

    @Test
    public void shouldReturnCorrectStyledDocumentAttributeValue() {
        assertThat(fontSizeAttribute.getStyledDocumentStyleAttributeValue(), is((Object) FONT_SIZE));
    }
}