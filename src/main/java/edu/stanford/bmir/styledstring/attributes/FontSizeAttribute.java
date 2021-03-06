package edu.stanford.bmir.styledstring.attributes;

import com.google.common.base.Objects;
import edu.stanford.bmir.styledstring.html.CssConstants;

import javax.swing.text.StyleConstants;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class FontSizeAttribute extends StyleAttribute {

    private int fontSize;

    public FontSizeAttribute(int fontSize) {
        this.fontSize = fontSize;
    }

    public static FontSizeAttribute get(int fontSize) {
        return new FontSizeAttribute(fontSize);
    }

    @Override
    public String getCssPropertyName() {
        return CssConstants.FONT_SIZE.getName();
    }

    @Override
    public String getCssPropertyValue() {
        return fontSize + "pt";
    }

    @Override
    public AttributedCharacterIterator.Attribute getAttributedStringAttribute() {
        return TextAttribute.SIZE;
    }

    @Override
    public Object getAttributedStringValue() {
        return fontSize;
    }

    @Override
    public StyleConstants getStyledDocumentStyleAttribute() {
        return (StyleConstants.FontConstants) StyleConstants.FontSize;
    }

    @Override
    public Object getStyledDocumentStyleAttributeValue() {
        return fontSize;
    }

    @Override
    public int hashCode() {
        return FontSizeAttribute.class.getSimpleName().hashCode() + fontSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FontSizeAttribute)) {
            return false;
        }
        FontSizeAttribute other = (FontSizeAttribute) obj;
        return this.fontSize == other.fontSize;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper("FontSizeAttribute")
                .addValue(fontSize)
                .toString();
    }
}
