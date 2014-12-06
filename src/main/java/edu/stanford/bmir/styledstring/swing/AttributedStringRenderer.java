package edu.stanford.bmir.styledstring.swing;

import edu.stanford.bmir.styledstring.Style;
import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringMarkup;
import edu.stanford.bmir.styledstring.attributes.StyleAttribute;

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
public class AttributedStringRenderer {

    public AttributedString toAttributedString(StyledString styledString) {
        AttributedString as = new AttributedString(styledString.getString());
        for (StyledStringMarkup markup : styledString.getMarkup()) {
            Style style = markup.getStyle();
            int start = markup.getStart();
            int end = markup.getEnd();
            if (start < end && -1 < start && start < styledString.length() && 0 < end && end <= styledString.length()) {
                for (StyleAttribute styleAttribute : style.getStyleAttributes()) {
                    AttributedCharacterIterator.Attribute att = styleAttribute.getAttributedStringAttribute();
                    Object val = styleAttribute.getAttributedStringValue();
                    as.addAttribute(att, val, start, end);
                }
            }
        }
        return as;
    }

}
