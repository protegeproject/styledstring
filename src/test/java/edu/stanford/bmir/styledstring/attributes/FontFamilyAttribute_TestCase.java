
package edu.stanford.bmir.styledstring.attributes;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.text.StyleConstants;
import java.awt.font.TextAttribute;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

@RunWith(MockitoJUnitRunner.class)
public class FontFamilyAttribute_TestCase {

    private FontFamilyAttribute fontFamilyAttribute;

    private String fontFamily = "The fontFamily";

    @Before
    public void setUp() {
        fontFamilyAttribute = new FontFamilyAttribute(fontFamily);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_fontFamily_IsNull() {
        new FontFamilyAttribute(null);
    }

    @Test
    public void shouldReturnSupplied_fontFamily() {
        assertThat(fontFamilyAttribute.getFontFamily(), is(this.fontFamily));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(fontFamilyAttribute, is(fontFamilyAttribute));
    }

    @Test
    @SuppressWarnings("ObjectEqualsNull")
    public void shouldNotBeEqualToNull() {
        assertThat(fontFamilyAttribute.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(fontFamilyAttribute, is(new FontFamilyAttribute(fontFamily)));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_fontFamily() {
        assertThat(fontFamilyAttribute, is(not(new FontFamilyAttribute("String-0b535e82-57d2-471d-92bb-5bbe60d69aca"))));
    }

    @Test
    public void shouldBeEqualToOtherHashCode() {
        assertThat(fontFamilyAttribute.hashCode(), is(new FontFamilyAttribute(fontFamily).hashCode()));
    }

    @Test
    public void shouldImplementToString() {
        assertThat(fontFamilyAttribute.toString(), startsWith("FontFamilyAttribute"));
    }

    @Test
    public void should_getStyledDocumentStyleAttribute() {
        assertThat(fontFamilyAttribute.getStyledDocumentStyleAttribute(), is(StyleConstants.FontFamily));
    }

    @Test
    public void should_getAttributedStringAttribute() {
        assertThat(fontFamilyAttribute.getAttributedStringAttribute(), is(TextAttribute.FAMILY));
    }

    @Test
    public void should_getStyledDocumentStyleAttributeValue() {
        assertThat(fontFamilyAttribute.getStyledDocumentStyleAttributeValue(), is(fontFamily));
    }

    @Test
    public void should_getAttributedStringValue() {
        assertThat(fontFamilyAttribute.getAttributedStringValue(), is(fontFamily));
    }

    @Test
    public void should_getCssPropertyName() {
        assertThat(fontFamilyAttribute.getCssPropertyName(), is("font-family"));
    }

    @Test
    public void should_getCssPropertyValue() {
        assertThat(fontFamilyAttribute.getCssPropertyValue(), is(fontFamily));
    }

}
