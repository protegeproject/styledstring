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
public class StrikeThroughAttribute extends StyleAttribute {


    private static final StrikeThroughAttribute SINGLE_STRIKE_THROUGH = new StrikeThroughAttribute(StrikeThrough.SINGLE);

    private StrikeThrough strikeThrough;

    private StrikeThroughAttribute(StrikeThrough strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    public static StrikeThroughAttribute get(StrikeThrough strikeThrough) {
        return new StrikeThroughAttribute(strikeThrough);
    }

    public static StrikeThroughAttribute getSingle() {
        return SINGLE_STRIKE_THROUGH;
    }

    @Override
    public String getCssPropertyName() {
        return CSSConstants.TEXT_DECORATION.getName();
    }

    @Override
    public String getCssPropertyValue() {
        return strikeThrough == StrikeThrough.SINGLE ? CSSConstants.LINE_THROUGH.getName() : CSSConstants.NONE.getName();
    }

    @Override
    public AttributedCharacterIterator.Attribute getAttributedStringAttribute() {
        return TextAttribute.STRIKETHROUGH;
    }

    @Override
    public Object getAttributesStringValue() {
        return strikeThrough == StrikeThrough.SINGLE;
    }

    @Override
    public StyleConstants getTextAttribute() {
        return (StyleConstants.CharacterConstants) StyleConstants.StrikeThrough;
    }

    @Override
    public Object getTextValue() {
        return strikeThrough == StrikeThrough.SINGLE;
    }
}
