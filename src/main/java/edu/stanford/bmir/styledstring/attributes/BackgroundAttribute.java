package edu.stanford.bmir.styledstring.attributes;

import edu.stanford.bmir.styledstring.CssConstants;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class BackgroundAttribute extends StyleAttribute {


    private Color background;

    private BackgroundAttribute(Color background) {
        this.background = background;
    }

    public static BackgroundAttribute get(Color color) {
        return new BackgroundAttribute(color);
    }

    @Override
    public String getCssPropertyName() {
        return CssConstants.BACKGROUND.getName();
    }

    @Override
    public String getCssPropertyValue() {
        return Integer.toHexString(background.getRGB() & 0x00ffffff);
    }

    @Override
    public AttributedCharacterIterator.Attribute getAttributedStringAttribute() {
        return TextAttribute.BACKGROUND;
    }

    @Override
    public Object getAttributedStringValue() {
        return background;
    }

    @Override
    public StyleConstants getStyledDocumentStyleAttribute() {
        return (StyleConstants.ColorConstants) StyleConstants.Background;
    }

    @Override
    public Object getStyledDocumentStyleAttributeValue() {
        return background;
    }

    @Override
    public int hashCode() {
        return BackgroundAttribute.class.getSimpleName().hashCode() + background.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BackgroundAttribute)) {
            return false;
        }
        BackgroundAttribute other = (BackgroundAttribute) obj;
        return this.background.equals(other.background);
    }
}
