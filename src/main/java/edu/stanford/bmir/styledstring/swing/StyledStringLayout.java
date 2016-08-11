package edu.stanford.bmir.styledstring.swing;

import com.google.common.collect.ImmutableList;
import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringLink;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Optional;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class StyledStringLayout {


    private final ImmutableList<TextLayoutCache> textLayoutLines;

    private final StyledString styledString;

    public StyledStringLayout(StyledString styledString) {
        this.styledString = checkNotNull(styledString);
        this.textLayoutLines = createLines(styledString);
    }

    public StyledString getStyledString() {
        return styledString;
    }

    public boolean isLayoutFor(StyledString styledString) {
        return this.styledString.equals(styledString);
    }

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


    public float getWidth(FontRenderContext fontRenderContext) {
        float maxWidth = 0;
        for (TextLayoutCache cache : textLayoutLines) {
            float visibleAdvance = cache.getVisibleAdvance(fontRenderContext);
            if (visibleAdvance > maxWidth) {
                maxWidth = visibleAdvance;
            }
        }
        return maxWidth;
    }

    public float getHeight(FontRenderContext fontRenderContext) {
        float height = 0;
        for (TextLayoutCache textLayoutCache : textLayoutLines) {
            height += textLayoutCache.getHeight(fontRenderContext);
        }
        return height;
    }

    public void draw(Graphics2D g2, float x, float y) {
        float yOffset = y;
        float leading;
        float ascent;
        float descent = 0;
        for (TextLayoutCache cache : textLayoutLines) {
            FontRenderContext frc = g2.getFontRenderContext();
            TextLayout textLayout = cache.getTextLayout(frc);
            leading = textLayout.getLeading();
            ascent = textLayout.getAscent();
            yOffset += leading + ascent + descent;
            textLayout.draw(g2, x, yOffset);
            descent = textLayout.getDescent();
        }
    }

    public Optional<HitInfo> getHitInfo(int ptX, int ptY, FontRenderContext fontRenderContext) {
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
            offsetIndex += cache.getPlainString().length() + 1; // Add 1 for new line
            lineNumber++;
        }
        return Optional.empty();
    }

    public Optional<StyledStringLink> getLinkAt(int ptX, int ptY, FontRenderContext fontRenderContext) {
        return getHitInfo(ptX, ptY, fontRenderContext)
                .flatMap(hit -> styledString.getLinkAt(hit.getAbsoluteIndex()));
    }


}
