package edu.stanford.bmir.styledstring;

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
public class ForegroundAttribute extends StyleAttribute {

    private static final ForegroundAttribute BLACK = new ForegroundAttribute(Color.BLACK);

    private Color foreground;

    private ForegroundAttribute(Color foreground) {
        this.foreground = foreground;
    }

    public static ForegroundAttribute get(Color foreground) {
        return new ForegroundAttribute(foreground);
    }

    @Override
    public String getCssPropertyName() {
        return CSSConstants.COLOR.getName();
    }

    @Override
    public String getCssPropertyValue() {
//        String s = Integer.toHexString(foreground.getRGB() & 0x00ffffff);
        return String.format("#%06x", foreground.getRGB() & 0x00ffffff);
    }

    @Override
    public AttributedCharacterIterator.Attribute getAttributedStringAttribute() {
        return TextAttribute.FOREGROUND;
    }

    @Override
    public Object getAttributedStringValue() {
        return foreground;
    }

    public Color getForeground() {
        return foreground;
    }

    public ForegroundAttribute getBlack() {
        return BLACK;
    }

    @Override
    public StyleConstants getStyledDocumentStyleAttribute() {
        return (StyleConstants.ColorConstants) StyleConstants.Foreground;
    }

    @Override
    public Object getStyledDocumentStyleAttributeValue() {
        return foreground;
    }

    @Override
    public int hashCode() {
        return ForegroundAttribute.class.getSimpleName().hashCode() + foreground.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ForegroundAttribute)) {
            return false;
        }
        ForegroundAttribute other = (ForegroundAttribute) obj;
        return this.foreground.equals(other.foreground);
    }
}
