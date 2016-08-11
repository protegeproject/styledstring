package edu.stanford.bmir.styledstring.swing;

import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextHitInfo;
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

    private final String plainString;

    public TextLayoutCache(AttributedString attributedString, String plainString) {
        this.attributedString = checkNotNull(attributedString);
        this.plainString = plainString;
    }

    public String getPlainString() {
        return plainString;
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

    public int getCharIndexAtPoint(int pointX, int pointY, FontRenderContext fontRenderContext) {
        TextLayout layout = getTextLayout(fontRenderContext);
        TextHitInfo info = layout.hitTestChar(pointX, pointY);
        return info.getCharIndex();
    }


    private boolean isCachedFontRenderContext(FontRenderContext fontRenderContext) {
        return cachedFontRenderContext != null && cachedFontRenderContext.equals(fontRenderContext);
    }
}
