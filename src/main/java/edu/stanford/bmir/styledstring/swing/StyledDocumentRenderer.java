package edu.stanford.bmir.styledstring.swing;

import edu.stanford.bmir.styledstring.Style;
import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringMarkup;
import edu.stanford.bmir.styledstring.attributes.StyleAttribute;

import javax.swing.text.*;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
public class StyledDocumentRenderer {


    public void renderIntoStyledDocument(StyledString styledString, StyledDocument styledDocument) {
        try {
            styledDocument.remove(0, styledDocument.getLength());
            styledDocument.insertString(0, styledString.getString(), null);
            for (StyledStringMarkup markup : styledString.getMarkup()) {
                Style style = markup.getStyle();
                int length = markup.getEnd() - markup.getStart();
                MutableAttributeSet mas = new SimpleAttributeSet();
                for (StyleAttribute styleAttribute : style.getStyleAttributes()) {
                    StyleConstants styleConstants = styleAttribute.getStyledDocumentStyleAttribute();
                    Object value = styleAttribute.getStyledDocumentStyleAttributeValue();
                    mas.addAttribute(styleConstants, value);
                }
                styledDocument.setCharacterAttributes(markup.getStart(), length, mas, false);


            }
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
