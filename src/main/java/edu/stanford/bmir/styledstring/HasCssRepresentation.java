package edu.stanford.bmir.styledstring;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
public interface HasCssRepresentation {

    /**
     * Gets the CSS property name.
     *
     * @return A string representing the CSS property name.  Not {@code null}.
     */
    String getCssPropertyName();

    /**
     * Get the value of the CSS property.
     *
     * @return A string representing the value of the CSS property.  Not {@code null}.
     */
    String getCssPropertyValue();

}
