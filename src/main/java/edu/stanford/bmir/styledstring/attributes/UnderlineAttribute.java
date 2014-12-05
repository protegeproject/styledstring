package edu.stanford.bmir.styledstring.attributes;

import edu.stanford.bmir.styledstring.CssConstants;

import javax.swing.text.StyleConstants;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class UnderlineAttribute extends StyleAttribute {

    private static final UnderlineAttribute NONE = new UnderlineAttribute(Underline.NONE);

    private static final UnderlineAttribute SINGLE = new UnderlineAttribute(Underline.SINGLE);


    private Underline underline;

    public UnderlineAttribute(Underline underline) {
        this.underline = underline;
    }

    public Underline getUnderline() {
        return underline;
    }

    public static UnderlineAttribute getNone() {
        return NONE;
    }

    public static UnderlineAttribute getSingle() {
        return SINGLE;
    }

    @Override
    public String getCssPropertyName() {
        return CssConstants.TEXT_DECORATION.getName();
    }

    @Override
    public String getCssPropertyValue() {
        return underline == Underline.SINGLE ? CssConstants.UNDERLINE.getName() : CssConstants.NONE.getName();
    }

    @Override
    public AttributedCharacterIterator.Attribute getAttributedStringAttribute() {
        return TextAttribute.UNDERLINE;
    }

    @Override
    public Object getAttributedStringValue() {
        return TextAttribute.UNDERLINE_LOW_ONE_PIXEL;
    }

    @Override
    public StyleConstants getStyledDocumentStyleAttribute() {
        return (StyleConstants.CharacterConstants) StyleConstants.Underline;
    }

    @Override
    public Object getStyledDocumentStyleAttributeValue() {
        return underline == Underline.SINGLE;
    }

    @Override
    public int hashCode() {
        return UnderlineAttribute.class.getSimpleName().hashCode() + underline.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnderlineAttribute)) {
            return false;
        }
        UnderlineAttribute other = (UnderlineAttribute) obj;
        return this.underline.equals(other.underline);
    }
}
