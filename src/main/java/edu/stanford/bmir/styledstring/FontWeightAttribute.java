package edu.stanford.bmir.styledstring;

import javax.swing.text.StyleConstants;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class FontWeightAttribute extends StyleAttribute {

    private static final FontWeightAttribute BOLD = new FontWeightAttribute(FontWeight.BOLD);


    private FontWeight fontWeight;

    public FontWeightAttribute(FontWeight fontWeight) {
        this.fontWeight = fontWeight;
    }

    @Override
    public String getCssPropertyName() {
        return CSSConstants.FONT_WEIGHT.getName();
    }

    @Override
    public String getCssPropertyValue() {
        return fontWeight == FontWeight.BOLD ? CSSConstants.BOLD.getName() : CSSConstants.NORMAL.getName();
    }

    @Override
    public AttributedCharacterIterator.Attribute getAttributedStringAttribute() {
        return TextAttribute.WEIGHT;
    }

    @Override
    public Object getAttributedStringValue() {
        return TextAttribute.WEIGHT_BOLD;
    }

    public static FontWeightAttribute getBoldFontWeight() {
        return BOLD;
    }

    public static FontWeightAttribute getRegularFontWeight() {
        return new FontWeightAttribute(FontWeight.REGULAR);
    }

    @Override
    public StyleConstants getStyledDocumentStyleAttribute() {
        return (StyleConstants.FontConstants) StyleConstants.Bold;
    }

    @Override
    public Object getStyledDocumentStyleAttributeValue() {
        return fontWeight == FontWeight.BOLD;
    }

    @Override
    public int hashCode() {
        return FontWeightAttribute.class.getSimpleName().hashCode() + fontWeight.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FontWeightAttribute)) {
            return false;
        }
        FontWeightAttribute other = (FontWeightAttribute) obj;
        return this.fontWeight == other.fontWeight;
    }

}
