package edu.stanford.bmir.styledstring.attributes;

import edu.stanford.bmir.styledstring.html.CssConstants;

import javax.annotation.Nonnull;
import javax.swing.text.StyleConstants;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 22 Nov 2016
 */
public class ItalicAttribute extends StyleAttribute {

    private final Italic italic;

    public ItalicAttribute(@Nonnull Italic italic) {
        this.italic = checkNotNull(italic);
    }

    @Override
    public StyleConstants getStyledDocumentStyleAttribute() {
        return (StyleConstants) StyleConstants.Italic;
    }

    @Override
    public AttributedCharacterIterator.Attribute getAttributedStringAttribute() {
        return TextAttribute.POSTURE;
    }

    @Override
    public Object getStyledDocumentStyleAttributeValue() {
        return italic == Italic.ITALIC;
    }

    @Override
    public Object getAttributedStringValue() {
        if(italic == Italic.ITALIC) {
            return TextAttribute.POSTURE_OBLIQUE;
        }
        else {
            return TextAttribute.POSTURE_REGULAR;
        }
    }

    @Override
    public String getCssPropertyName() {
        return "font-style";
    }

    @Override
    public String getCssPropertyValue() {
        if(italic == Italic.ITALIC) {
            return CssConstants.ITALIC.getName();
        }
        else {
            return CssConstants.NORMAL.getName();
        }
    }

    @Override
    public int hashCode() {
        return italic.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ItalicAttribute)) {
            return false;
        }
        ItalicAttribute other = (ItalicAttribute) obj;
        return this.italic.equals(other.italic);
    }


    @Override
    public String toString() {
        return toStringHelper("ItalicAttribute")
                .addValue(italic)
                .toString();
    }
}
