package edu.stanford.bmir.styledstring;

import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.text.AttributedString;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 * <p>
 * A simple first in first out cache for TextLayout objects of AttributedStrings.
 * </p>
 */
public class TextLayoutCache {

    private final AttributedString attributedString;

    private FontRenderContext cachedFontRenderContext;

    private TextLayout cachedLayout;

    public TextLayoutCache(AttributedString attributedString) {
        this.attributedString = checkNotNull(attributedString);
    }

    public AttributedString getAttributedString() {
        return attributedString;
    }

    public TextLayout getTextLayout(FontRenderContext fontRenderContext) {
        if (!isCachedFontRenderContext(fontRenderContext)) {
            cachedLayout = new TextLayout(attributedString.getIterator(), fontRenderContext);
            cachedFontRenderContext = fontRenderContext;
        }
        return cachedLayout;
    }

    public float getHeight(FontRenderContext fontRenderContext) {
        TextLayout tl = getTextLayout(fontRenderContext);
        return tl.getLeading() + tl.getAscent() + tl.getDescent();
    }

    public float getVisibleAdvance(FontRenderContext fontRenderContext) {
        TextLayout tl = getTextLayout(fontRenderContext);
        return tl.getVisibleAdvance();
    }

    public float getBaseline(FontRenderContext fontRenderContext) {
        TextLayout tl = getTextLayout(fontRenderContext);
        return tl.getLeading() + tl.getAscent();

    }


    private boolean isCachedFontRenderContext(FontRenderContext fontRenderContext) {
        return cachedFontRenderContext != null && cachedFontRenderContext.equals(fontRenderContext);
    }
}
