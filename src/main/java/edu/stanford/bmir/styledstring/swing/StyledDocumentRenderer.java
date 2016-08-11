package edu.stanford.bmir.styledstring.swing;

import edu.stanford.bmir.styledstring.Style;
import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringMarkup;
import edu.stanford.bmir.styledstring.attributes.StyleAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.swing.text.*;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 *
 * Renders a StyledString into a {@link StyledDocument}.
 */
public class StyledDocumentRenderer {

    private static final Logger logger = LoggerFactory.getLogger(StyledDocumentRenderer.class);

    /**
     * Render the specified StyledString into the specified StyledDocument.  Note that any existing document content
     * will be erased by calling this method.
     * @param styledString The StyledString
     * @param styledDocument The StyledDocument.
     */
    public void renderIntoStyledDocument(@Nonnull StyledString styledString, @Nonnull StyledDocument styledDocument) {
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
            logger.error("BadLocationException whilst rendering document", e);
        }
    }
}
