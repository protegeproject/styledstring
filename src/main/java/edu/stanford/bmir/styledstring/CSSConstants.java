package edu.stanford.bmir.styledstring;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public enum CssConstants {

    TEXT_DECORATION("text-decoration"),

    FONT_WEIGHT("font-weight"),

    FONT_SIZE("font-size"),

    BACKGROUND("background"),

    COLOR("color"),

    BOLD("bold"),

    NORMAL("normal"),

    LINE_THROUGH("line-through"),

    UNDERLINE("underline"),

    NONE("none");


    String name;

    private CssConstants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
