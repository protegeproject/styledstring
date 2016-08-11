package edu.stanford.bmir.styledstring.swing;

import com.google.common.collect.ImmutableList;
import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringLink;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 *
 * An object that represents the layout information for a StyledString. The layout is capable of drawing the string
 * on a Graphics object.
 */
public class StyledStringLayout {


    private final ImmutableList<TextLayoutCache> textLayoutLines;

    private final StyledString styledString;

    /**
     * Creates a layout for a styled string.  A layout consists of a list of lines and can be used for computing the
     * bounds of a styled string for a given FontRenderContext.
     * @param styledString The styled string.
     * @return A layout for the styled string.
     */
    @Nonnull
    public static StyledStringLayout create(@Nonnull StyledString styledString) {
        checkNotNull(styledString);
        ImmutableList<TextLayoutCache> lines = createLines(styledString);
        return new StyledStringLayout(styledString, lines);
    }

    /**
     * Creates lines for the layout.  This essentially splits the string into separate lines (on the new line
     * character '\n') and computes AttributedStrings for these lines.
     * @param styledString The string to compute the lines for.
     * @return The lines.
     */
    private static ImmutableList<TextLayoutCache> createLines(StyledString styledString) {
        if (styledString.isEmpty()) {
            return ImmutableList.of();
        }
        String[] lines = styledString.getString().split("\\n");
        int lineStart = 0;
        final AttributedStringRenderer attributedStringRenderer = new AttributedStringRenderer();
        final AttributedCharacterIterator iterator = attributedStringRenderer.toAttributedString(styledString).getIterator();
        ImmutableList.Builder<TextLayoutCache> builder = ImmutableList.builder();
        for (String line : lines) {
            int lineEnd = lineStart + line.length();
            AttributedString attributedLine = new AttributedString(iterator, lineStart, lineEnd);
            builder.add(new TextLayoutCache(attributedLine, line));
            lineStart = lineEnd + 1;
        }
        return builder.build();
    }

    /**
     * Private Constuctor.  Client code should use the static initializer method.
     * @param styledString The styled string.
     * @param textLayoutLines The styled string's layout line.
     */
    private StyledStringLayout(@Nonnull StyledString styledString, @Nonnull ImmutableList<TextLayoutCache> textLayoutLines) {
        this.styledString = styledString;
        this.textLayoutLines = textLayoutLines;
    }

    @Nonnull
    public StyledString getStyledString() {
        return styledString;
    }

    /**
     * Determines whether this layout is a layout for the specified string.
     * @param styledString The string.
     * @return true if this is a layout for the specified string, otherwise false.
     */
    public boolean isLayoutFor(@Nonnull StyledString styledString) {
        return this.styledString.equals(styledString);
    }

    /**
     * Gets the width of the bounding box of this styled string.
     * @param fontRenderContext The FontRenderContext for the graphics object that is used to draw the text.
     * @return The width of the bounding box.
     */
    public float getWidth(@Nonnull FontRenderContext fontRenderContext) {
        float maxWidth = 0;
        for (TextLayoutCache cache : textLayoutLines) {
            float visibleAdvance = cache.getVisibleAdvance(fontRenderContext);
            if (visibleAdvance > maxWidth) {
                maxWidth = visibleAdvance;
            }
        }
        return maxWidth;
    }

    /**
     * Gets the height of the bounding box of this styled string.
     * @param fontRenderContext The FontRenderContext for the graphics object that is used to draw the text.
     * @return The height.
     */
    public float getHeight(@Nonnull FontRenderContext fontRenderContext) {
        float height = 0;
        for (TextLayoutCache textLayoutCache : textLayoutLines) {
            height += textLayoutCache.getHeight(fontRenderContext);
        }
        return height;
    }

    /**
     * Draws the styled string represented by this layout.
     * @param g2 The Graphics2D that should be used to draw the string.
     * @param x The left location of the string bounding box.
     * @param y The top location of the string bounding box.
     */
    public void draw(@Nonnull Graphics2D g2, float x, float y) {
        float yOffset = y;
        float leading = 0;
        float ascent;
        float descent = 0;
        for (TextLayoutCache cache : textLayoutLines) {
            FontRenderContext frc = g2.getFontRenderContext();
            TextLayout textLayout = cache.getTextLayout(frc);

            ascent = textLayout.getAscent();
            yOffset += leading + ascent + descent;
            textLayout.draw(g2, x, yOffset);

            leading = textLayout.getLeading();
            descent = textLayout.getDescent();
        }
    }

    /**
     * Gets the hit info at the specified point. The point is considered relative to the top left corner of the text
     * layout.
     * @param ptX The x coordinate of the point.
     * @param ptY The y coordinate of the point.
     * @param fontRenderContext The FontRenderContext for the graphics object that is used to draw the text.
     * @return The HitInfo.  If the point is outside of the bounds of the rendered text then an empty value will
     * be returned.
     */
    @Nonnull
    public Optional<HitInfo> getHitInfo(int ptX, int ptY, @Nonnull FontRenderContext fontRenderContext) {
        if(ptX < 0) {
            return Optional.empty();
        }
        if(ptY < 0) {
            return Optional.empty();
        }
        int lineNumber = 0;
        float y0 = 0;
        int offsetIndex = 0;
        for(TextLayoutCache cache : textLayoutLines) {
            float y1 = y0 + cache.getHeight(fontRenderContext);
            if(y0 <= ptY && ptY < y1) {
                int charIndexAtPoint = cache.getCharIndexAtPoint(ptX, ptY, fontRenderContext);
                if(charIndexAtPoint != -1) {
                    int absoluteIndex = offsetIndex + charIndexAtPoint;
                    return Optional.of(new HitInfo(lineNumber, charIndexAtPoint, absoluteIndex));
                }
            }
            y0 = y1;
            offsetIndex += cache.getPlainString().length() + 1; // Add 1 for new line character
            lineNumber++;
        }
        return Optional.empty();
    }

    /**
     * Gets the link at the specified point.  The point is considered relative to the top left corner of the text layout.
     * @param ptX The x coordinate of the point.
     * @param ptY The y coordinate of the point.
     * @param fontRenderContext The FontRenderContext for the graphics object that is used to draw the text.
     * @return The link at the specified location.  An absent value indicates that there is no link at the specified
     * location.
     */
    @Nonnull
    public Optional<StyledStringLink> getLinkAt(int ptX, int ptY, @Nonnull FontRenderContext fontRenderContext) {
        return getHitInfo(ptX, ptY, fontRenderContext)
                .flatMap(hit -> styledString.getLinkAt(hit.getAbsoluteIndex()));
    }

    /**
     * Gets the height of a given line.
     * @param lineIndex The index of the line.
     * @param fontRenderContext The FontRenderContext for the graphics object that is used to draw the text.
     * @return The height.  If the specified index it out of bounds then an empty value will be returned.
     */
    @Nonnull
    public OptionalInt getLineHeight(int lineIndex, @Nonnull FontRenderContext fontRenderContext) {
        if(textLayoutLines.isEmpty()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of((int) textLayoutLines.get(lineIndex).getHeight(fontRenderContext));
    }
}
