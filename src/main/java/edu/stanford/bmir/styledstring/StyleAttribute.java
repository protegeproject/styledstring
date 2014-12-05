package edu.stanford.bmir.styledstring;

import javax.swing.text.StyleConstants;
import java.text.AttributedCharacterIterator;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public abstract class StyleAttribute {

    public abstract String getCSSPropertyName();

    public abstract String getCSSValue();
    

    public abstract AttributedCharacterIterator.Attribute getAttributedStringAttribute();

    public abstract Object getAttributesStringValue();


    public abstract StyleConstants getTextAttribute();

    public abstract Object getTextValue();

}
