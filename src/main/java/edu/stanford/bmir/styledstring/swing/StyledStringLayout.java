package edu.stanford.bmir.styledstring.swing;

import com.google.common.collect.ImmutableList;
import edu.stanford.bmir.styledstring.StyledString;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class StyledStringLayout {


    private final ImmutableList<TextLayoutCache> textLayoutLines;

    public StyledStringLayout(StyledString styledString) {
        textLayoutLines = createLines(styledString);
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
            builder.add(new TextLayoutCache(attributedLine));
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


}
