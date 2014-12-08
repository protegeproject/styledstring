package edu.stanford.bmir.styledstring.attributes;

import com.google.common.base.Objects;
import edu.stanford.bmir.styledstring.html.CssConstants;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class BackgroundAttribute extends StyleAttribute {


    private Color background;

    public BackgroundAttribute(Color background) {
        this.background = checkNotNull(background);
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
        return "#" + Integer.toHexString(background.getRGB() & 0x00ffffff);
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


    @Override
    public String toString() {
        return Objects.toStringHelper("BackgroundAttribute")
                .addValue(background)
                .toString();
    }
}
