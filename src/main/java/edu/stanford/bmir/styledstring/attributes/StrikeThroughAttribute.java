package edu.stanford.bmir.styledstring.attributes;

import com.google.common.base.Objects;
import edu.stanford.bmir.styledstring.html.CssConstants;

import javax.swing.text.StyleConstants;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class StrikeThroughAttribute extends StyleAttribute {


    private static final StrikeThroughAttribute SINGLE_STRIKE_THROUGH = new StrikeThroughAttribute(StrikeThrough.SINGLE);

    private StrikeThrough strikeThrough;

    public StrikeThroughAttribute(StrikeThrough strikeThrough) {
        this.strikeThrough = checkNotNull(strikeThrough);
    }

    public static StrikeThroughAttribute get(StrikeThrough strikeThrough) {
        return new StrikeThroughAttribute(strikeThrough);
    }

    public static StrikeThroughAttribute getSingle() {
        return SINGLE_STRIKE_THROUGH;
    }

    @Override
    public String getCssPropertyName() {
        return CssConstants.TEXT_DECORATION.getName();
    }

    @Override
    public String getCssPropertyValue() {
        return strikeThrough == StrikeThrough.SINGLE ? CssConstants.LINE_THROUGH.getName() : CssConstants.NONE.getName();
    }

    @Override
    public AttributedCharacterIterator.Attribute getAttributedStringAttribute() {
        return TextAttribute.STRIKETHROUGH;
    }

    @Override
    public Object getAttributedStringValue() {
        return strikeThrough == StrikeThrough.SINGLE;
    }

    @Override
    public StyleConstants getStyledDocumentStyleAttribute() {
        return (StyleConstants.CharacterConstants) StyleConstants.StrikeThrough;
    }

    @Override
    public Object getStyledDocumentStyleAttributeValue() {
        return strikeThrough == StrikeThrough.SINGLE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StrikeThroughAttribute)) {
            return false;
        }
        StrikeThroughAttribute other = (StrikeThroughAttribute) obj;
        return this.strikeThrough == other.strikeThrough;
    }

    @Override
    public int hashCode() {
        return strikeThrough.hashCode();
    }


    @Override
    public String toString() {
        return Objects.toStringHelper("StrikeThroughAttribute")
                .addValue(strikeThrough)
                .toString();
    }
}
