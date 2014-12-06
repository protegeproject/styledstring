package edu.stanford.bmir.styledstring.swing;

import java.awt.*;
import java.awt.font.FontRenderContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
public class StyledStringPainter {

    private final StyledStringLayout styledStringLayout;

    public StyledStringPainter(StyledStringLayout styledStringLayout) {
        this.styledStringLayout = checkNotNull(styledStringLayout);
    }

    public void draw(Graphics2D g2, final float x, final float y) {
        styledStringLayout.draw(g2, x, y);
    }

    public void drawVerticallyCentredInRect(Graphics2D g2, Rectangle rectangle) {
        FontRenderContext fontRenderContext = g2.getFontRenderContext();
        float height = styledStringLayout.getHeight(fontRenderContext);
        float x = rectangle.x;
        float y = rectangle.y + (float) (rectangle.getHeight() - height) / 2;
        draw(g2, x, y);
    }

    public void drawHorizontallyCenteredInRect(Graphics2D g2, Rectangle rectangle) {
        FontRenderContext fontRenderContext = g2.getFontRenderContext();
        float x = rectangle.x + (float) (rectangle.getWidth() - styledStringLayout.getWidth(fontRenderContext)) / 2;
        float y = rectangle.y;
        draw(g2, x, y);
    }

    public void drawCentredInRect(Graphics2D g2, Rectangle rectangle) {
        FontRenderContext fontRenderContext = g2.getFontRenderContext();
        float x = (float) (rectangle.getWidth() - styledStringLayout.getWidth(fontRenderContext)) / 2;
        float y = (float) (rectangle.getHeight() - styledStringLayout.getHeight(fontRenderContext)) / 2;
        draw(g2, x, y);
    }
}
