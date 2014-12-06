package edu.stanford.bmir.styledstring.swing;

import edu.stanford.bmir.styledstring.StyledString;

import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
public class RtfRenderer {

    public String toRtf(StyledString styledString) {
        try {
            RTFEditorKit editorKit = new RTFEditorKit();
            StyledDocument document = (StyledDocument) editorKit.createDefaultDocument();
            StyledDocumentRenderer renderer = new StyledDocumentRenderer();
            renderer.renderIntoStyledDocument(styledString, document);
            MutableAttributeSet fontFamily = new SimpleAttributeSet();
            StyleConstants.setFontFamily(fontFamily, Font.SANS_SERIF);
            document.setParagraphAttributes(0, document.getLength(), fontFamily, false);
            document.setCharacterAttributes(0, 4, fontFamily, false);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            editorKit.write(os, document, 0, document.getLength());
            return new String(os.toByteArray());
        }
        catch (IOException e) {
            throw new RuntimeException("Problem rendering string into RTF", e);
        }
        catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }
}
