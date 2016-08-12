package edu.stanford.bmir.styledstring.swing;

import javax.annotation.Nonnull;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextHitInfo;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 *
 * A simple cache for TextLayout objects of AttributedStrings.
 *
 */
public class TextLayoutCache {

    private final AttributedString attributedString;

    private FontRenderContext cachedFontRenderContext;

    private TextLayout cachedLayout;

    private final String plainString;

    /**
     * Cache the TextLayout for the specified AttributedString.
     * @param attributedString The AttributedString whose TextLayout is to be cached.
     * @param plainString A plain string version of the AttributedString.
     */
    public TextLayoutCache(@Nonnull AttributedString attributedString, @Nonnull String plainString) {
        this.attributedString = checkNotNull(attributedString);
        this.plainString = plainString;
    }

    /**
     * Gets the plain string that is cached by this layout cache.
     * @return The plain string.
     */
    @Nonnull
    public String getPlainString() {
        return plainString;
    }

    /**
     * Gets the AttributedString that is cached by this layout cache.
     * @return The AttributedString.
     */
    @Nonnull
    public AttributedString getAttributedString() {
        return attributedString;
    }

    /**
     * Gets the TextLayout for the string cached by this cache for the specified FontRenderContext.
     * @param fontRenderContext The FontRenderContext of the Graphics2D object that is used to draw the associated
     *                          AttributedString.
     * @return The TextLayout for the associated AttributedString.
     */
    @Nonnull
    public TextLayout getTextLayout(FontRenderContext fontRenderContext) {
        if (!isCachedFontRenderContext(fontRenderContext)) {
            cachedLayout = new TextLayout(attributedString.getIterator(), fontRenderContext);
            cachedFontRenderContext = fontRenderContext;
        }
        return cachedLayout;
    }

    /**
     * Gets the height of the associated AttributedString given the specified FontRenderContext.
     * @param fontRenderContext The FontRenderContext of the Graphics2D object that is used to draw the associated
     *                          AttributedString.
     * @return The height.
     */
    public float getHeight(FontRenderContext fontRenderContext) {
        TextLayout tl = getTextLayout(fontRenderContext);
        return tl.getLeading() + tl.getAscent() + tl.getDescent();
    }

    /**
     * Gets the visible advance of the associated AttributedString given the specified FontRenderContext.
     * @param fontRenderContext The FontRenderContext of the Graphics2D object that is used to draw the associated
     *                          AttributedString.
     * @return The visible advance.
     */
    public float getVisibleAdvance(FontRenderContext fontRenderContext) {
        TextLayout tl = getTextLayout(fontRenderContext);
        return tl.getVisibleAdvance();
    }

    /**
     * Gets the baseline of the associated AttributedString given the specified FontRenderContext.
     * @param fontRenderContext The FontRenderContext of the Graphics2D object that is used to draw the associated
     *                          AttributedString.
     * @return The baseline offset.
     */
    public float getBaseline(FontRenderContext fontRenderContext) {
        TextLayout tl = getTextLayout(fontRenderContext);
        return tl.getLeading() + tl.getAscent();
    }

    /**
     * Gets the character index of the character at the specified position in the context of the specified
     * FontRenderContext.
     * @param pointX The X co-ordinate of the position.
     * @param pointY The Y co-ordinate of the position.
     * @param fontRenderContext The FontRenderContext of the Graphics2D object that is used to draw the associated
     *                          AttributedString.
     * @return The index.  If there is not an index represented by the specified point then -1 is returned.
     */
    public int getCharIndexAtPoint(int pointX, int pointY, FontRenderContext fontRenderContext) {
        TextLayout layout = getTextLayout(fontRenderContext);
        Rectangle2D bounds = layout.getBounds();
        if(bounds.getMaxX() < pointX) {
            return -1;
        }
        if(pointX < 0) {
            return -1;
        }
        TextHitInfo info = layout.hitTestChar(pointX, pointY);
        return info.getCharIndex();
    }


    private boolean isCachedFontRenderContext(FontRenderContext fontRenderContext) {
        return cachedFontRenderContext != null && cachedFontRenderContext.equals(fontRenderContext);
    }
}
