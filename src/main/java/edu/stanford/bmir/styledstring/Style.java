package edu.stanford.bmir.styledstring;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import edu.stanford.bmir.styledstring.attributes.*;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 *
 * A Style represents formatting using various attributes.  For example, bold-magenta.
 */
public class Style {


    private static final Style EMPTY = builder().build();

    private static final Style STRIKE_THROUGH = builder().withStrikeThrough().build();

    private static final Style BOLD = builder().withBold().build();

    private static final Style UNDERLINE = builder().withUnderline().build();


    private final List<StyleAttribute> styleAttributes;

    public Style(Collection<StyleAttribute> styleAttributes) {
        this.styleAttributes = ImmutableList.copyOf(checkNotNull(styleAttributes));
    }

    /**
     * Gets the empty style.
     * @return The empty style.  Not {@code null}.
     */
    public static Style emptyStyle() {
        return EMPTY;
    }

    /**
     * Gets the bold style.
     * @return The bold style. Not {@code null}.
     */
    public static Style getBold() {
        return BOLD;
    }

    /**
     * Gets the single line strike through style.
     * @return The single line strike through style.  Not {@code null}.
     */
    public static Style getStrikeThrough() {
        return STRIKE_THROUGH;
    }

    /**
     * Gets the underline style.
     * @return The underline style.  Not {@code null}.
     */
    public static Style getUnderline() {
        return UNDERLINE;
    }

    public List<StyleAttribute> getStyleAttributes() {
        return Collections.unmodifiableList(styleAttributes);
    }

    /**
     * Creates and initialises a builder with the style attributes contained within this Style.
     * @return A Builder that is initialised with the attributes in this style.  Not {@code null}.
     */
    public Builder toBuilder() {
        return new Builder().withStyle(this);
    }

    /**
     * Creates a fresh Style Builder.
     * @return The Style Builder.  Not {@code null}.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Map<String, StyleAttribute> attributes = Maps.newLinkedHashMap();

        private void setAttribute(StyleAttribute attribute) {
            attributes.put(attribute.getCssPropertyName(), attribute);
        }

        public Builder withForeground(Color foreground) {
            setAttribute(ForegroundAttribute.get(foreground));
            return this;
        }


        public Builder withBackground(Color background) {
            setAttribute(BackgroundAttribute.get(background));
            return this;
        }

        public Builder withStrikeThrough() {
            setAttribute(StrikeThroughAttribute.getSingle());
            return this;
        }

        public Builder withBold() {
            setAttribute(FontWeightAttribute.getBoldFontWeight());
            return this;
        }

        public Builder withUnderline() {
            setAttribute(UnderlineAttribute.getSingle());
            return this;
        }

        public Builder withFontSize(int fontSize) {
            setAttribute(FontSizeAttribute.get(fontSize));
            return this;
        }

        public Builder withFontWeight(FontWeightAttribute fontWeight) {
            setAttribute(fontWeight);
            return this;
        }

        public Builder withFontFamily(String fontFamily) {
            setAttribute(new FontFamilyAttribute(fontFamily));
            return this;
        }

        public Style build() {
            return new Style(attributes.values());
        }

        public Builder withStyle(Style style) {
            for(StyleAttribute attribute : style.styleAttributes) {
                setAttribute(attribute);
            }
            return this;
        }
    }



    @Override
    public int hashCode() {
        return Style.class.getSimpleName().hashCode() + styleAttributes.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Style)) {
            return false;
        }
        Style other = (Style) obj;
        return this.styleAttributes.equals(other.styleAttributes);
    }
}
