package edu.stanford.bmir.styledstring.attributes;

import javax.annotation.Nonnull;
import javax.swing.text.StyleConstants;
import javax.xml.soap.Text;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 4 Nov 2016
 */
public class FontFamilyAttribute extends StyleAttribute {

    private final String fontFamily;

    public FontFamilyAttribute(@Nonnull String fontFamily) {
        this.fontFamily = checkNotNull(fontFamily);
    }

    @Nonnull
    public String getFontFamily() {
        return fontFamily;
    }

    @Override
    public int hashCode() {
        return fontFamily.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FontFamilyAttribute)) {
            return false;
        }
        FontFamilyAttribute other = (FontFamilyAttribute) obj;
        return this.fontFamily.equals(other.fontFamily);
    }


    @Override
    public String toString() {
        return toStringHelper("FontFamilyAttribute")
                .addValue(fontFamily)
                .toString();
    }

    @Override
    public StyleConstants getStyledDocumentStyleAttribute() {
        return (StyleConstants.FontConstants) StyleConstants.FontFamily;
    }

    @Override
    public AttributedCharacterIterator.Attribute getAttributedStringAttribute() {
        return TextAttribute.FAMILY;
    }

    @Override
    public Object getStyledDocumentStyleAttributeValue() {
        return fontFamily;
    }

    @Override
    public Object getAttributedStringValue() {
        return fontFamily;
    }

    @Override
    public String getCssPropertyName() {
        return "font-family";
    }

    @Override
    public String getCssPropertyValue() {
        return fontFamily;
    }
}
