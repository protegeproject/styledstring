package edu.stanford.bmir.styledstring;

import java.text.AttributedCharacterIterator;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
public interface HasAttributedStringRepresentation {

    AttributedCharacterIterator.Attribute getAttributedStringAttribute();

    Object getAttributedStringValue();
}
