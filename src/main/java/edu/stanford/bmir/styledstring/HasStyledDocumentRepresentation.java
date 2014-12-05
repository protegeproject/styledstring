package edu.stanford.bmir.styledstring;

import javax.swing.text.StyleConstants;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
public interface HasStyledDocumentRepresentation {

    StyleConstants getStyledDocumentStyleAttribute();

    Object getStyledDocumentStyleAttributeValue();
}
