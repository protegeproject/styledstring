
package edu.stanford.bmir.styledstring.attributes;

import edu.stanford.bmir.styledstring.html.CssConstants;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.text.StyleConstants;
import javax.xml.soap.Text;

import java.awt.font.TextAttribute;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

@RunWith(MockitoJUnitRunner.class)
public class ItalicAttribute_TestCase {

    private ItalicAttribute italicAttribute;

    private Italic italic = Italic.ITALIC;

    @Before
    public void setUp() {
        italicAttribute = new ItalicAttribute(italic);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = java.lang.NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_italic_IsNull() {
        new ItalicAttribute(null);
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(italicAttribute, is(italicAttribute));
    }

    @Test
    @SuppressWarnings("ObjectEqualsNull")
    public void shouldNotBeEqualToNull() {
        assertThat(italicAttribute.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(italicAttribute, is(new ItalicAttribute(italic)));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_italic() {
        assertThat(italicAttribute, is(not(new ItalicAttribute(Italic.NORMAL))));
    }

    @Test
    public void shouldBeEqualToOtherHashCode() {
        assertThat(italicAttribute.hashCode(), is(new ItalicAttribute(italic).hashCode()));
    }

    @Test
    public void shouldImplementToString() {
        assertThat(italicAttribute.toString(), startsWith("ItalicAttribute"));
    }

    @Test
    public void should_getStyledDocumentStyleAttribute() {
        assertThat(italicAttribute.getStyledDocumentStyleAttribute(), is(StyleConstants.Italic));
    }

    @Test
    public void should_getAttributedStringAttribute() {
        assertThat(italicAttribute.getAttributedStringAttribute(), is(TextAttribute.POSTURE));
    }

    @Test
    public void should_getStyledDocumentStyleAttributeValue() {
        assertThat(italicAttribute.getStyledDocumentStyleAttributeValue(), is(true));
    }

    @Test
    public void should_getAttributedStringValue() {
        assertThat(italicAttribute.getAttributedStringValue(), is(TextAttribute.POSTURE_OBLIQUE));
    }

    @Test
    public void should_getCssPropertyName() {
        assertThat(italicAttribute.getCssPropertyName(), is(CssConstants.FONT_STYLE.getName()));
    }

    @Test
    public void should_getCssPropertyValue() {
        assertThat(italicAttribute.getCssPropertyValue(), is(CssConstants.ITALIC.getName()));
    }

}
