package edu.stanford.bmir.styledstring.html;

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

    FONT_STYLE("font-style"),

    BACKGROUND("background"),

    COLOR("color"),

    BOLD("bold"),

    ITALIC("italic"),

    NORMAL("normal"),

    LINE_THROUGH("line-through"),

    UNDERLINE("underline"),

    NONE("none");


    String name;

    CssConstants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
