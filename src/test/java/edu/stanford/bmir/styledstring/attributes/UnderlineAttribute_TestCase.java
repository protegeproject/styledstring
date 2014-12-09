package edu.stanford.bmir.styledstring.attributes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.text.StyleConstants;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 09/12/14
 */
@RunWith(MockitoJUnitRunner.class)
public class UnderlineAttribute_TestCase {


    private UnderlineAttribute underlineAttribute;

    private UnderlineAttribute otherUnderlineAttribute;


    @Before
    public void setUp() throws Exception {
        Underline underline = Underline.SINGLE;
        underlineAttribute = new UnderlineAttribute(underline);
        otherUnderlineAttribute = new UnderlineAttribute(underline);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        new UnderlineAttribute(null);
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(underlineAttribute, is(equalTo(underlineAttribute)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(underlineAttribute, is(not(equalTo(null))));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(underlineAttribute, is(equalTo(otherUnderlineAttribute)));
    }

    @Test
    public void shouldHaveSameHashCodeAsOther() {
        assertThat(underlineAttribute.hashCode(), is(otherUnderlineAttribute.hashCode()));
    }

    @Test
    public void shouldGenerateToString() {
        assertThat(underlineAttribute.toString(), startsWith("UnderlineAttribute"));
    }


    @Test
    public void shouldReturnCorrectCssPropertyName() {
        assertThat(underlineAttribute.getCssPropertyName(), is("text-decoration"));
    }

    @Test
    public void shouldReturnCorrectCssPropertyValue() {
        assertThat(underlineAttribute.getCssPropertyValue(), is("underline"));
    }

    @Test
    public void shouldReturnCorrectAttributedStringAttribute() {
        AttributedCharacterIterator.Attribute expected = TextAttribute.UNDERLINE;
        assertThat(underlineAttribute.getAttributedStringAttribute(), is(expected));
    }

    @Test
    public void shouldReturnCorrectAttributedStringValue() {
        assertThat(underlineAttribute.getAttributedStringValue(), is((Object) TextAttribute.UNDERLINE_ON));
    }

    @Test
    public void shouldReturnCorrectStyledDocumentStyleAttribute() {
        assertThat(underlineAttribute.getStyledDocumentStyleAttribute(), is(StyleConstants.Underline));
    }

    @Test
    public void shouldReturnCorrectStyledDocumentAttributeValue() {
        assertThat(underlineAttribute.getStyledDocumentStyleAttributeValue(), is((Object) true));
    }
}