package edu.stanford.bmir.styledstring.swing;

import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringLink;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Optional;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class StyledStringPanel extends JPanel {

    public static final int DEFAULT_ICON_PADDING = 7;

    private static final Insets NULL_INSETS = new Insets(0, 0, 0, 0);

    public static final FontRenderContext NULL_FONT_RENDER_CONTEXT = new FontRenderContext(new AffineTransform(), false, false);

    private StyledString styledString;

    private Optional<Icon> icon = Optional.empty();

    private Optional<StyledStringLayout> theLayout = Optional.empty();

    private final StyledStringLinkRenderer styledStringLinkRenderer = new StyledStringLinkRenderer();

    public StyledStringPanel() {
        setOpaque(true);
        setStyledString(StyledString.emptyString());
    }

    public void setStyledString(StyledString styledString) {
        this.styledString = styledString;
        styledStringLinkRenderer.setStyledString(styledString);
        rebuildPaintedString(styledString, RepaintRequest.DO_NOT_REPAINT);
    }

    public void setIcon(Icon icon) {
        this.icon = Optional.of(icon);
    }

    public void clearIcon() {
        this.icon = Optional.empty();
    }

    public void setCursorPosition(final int ptX, final int ptY, RepaintRequest repaintRequest) {
        theLayout.ifPresent(l -> {
            FontRenderContext frc = getFontRenderContext();
            Point point = getPointRelativeToTextBounds(ptX, ptY);
            styledStringLinkRenderer.setCursorPosition(point.x, point.y, frc);
            StyledString paintedString = styledStringLinkRenderer.getStyledStringToRender();
            rebuildPaintedString(paintedString, repaintRequest);
        });
    }

    private FontRenderContext getFontRenderContext() {
        Graphics2D g2 = (Graphics2D) getGraphics();
        if(g2 == null) {
            return NULL_FONT_RENDER_CONTEXT;
        }
        else {
            return g2.getFontRenderContext();
        }
    }

    public Optional<StyledStringLink> getLinkAt(int ptX, int ptY) {
        return theLayout.flatMap(l -> {
            Point p = getPointRelativeToTextBounds(ptX, ptY);
            return l.getLinkAt(p.x, p.y, getFontRenderContext());
        });
    }

    public void clearCursorPosition(RepaintRequest repaintRequest) {
        styledStringLinkRenderer.clearCursorPosition();
        rebuildPaintedString(styledString, repaintRequest);
    }


    private void rebuildPaintedString(StyledString styledString, RepaintRequest repaintRequest) {
        if(theLayout.filter(l -> l.isLayoutFor(styledString)).isPresent()) {
            return;
        }
        this.theLayout = Optional.of(StyledStringLayout.create(styledString));
        if (repaintRequest == RepaintRequest.REPAINT) {
            repaint();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            Shape clip = g2.getClip();
            g2.setColor(getBackground());
            g2.fill(clip);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Insets insets = getInsets();
            if (insets != null) {
                g2.translate(insets.left, insets.top);
            }

            theLayout.ifPresent(l -> {
                g2.setFont(getFont());
                icon.ifPresent(i -> {
                    int firstLineHeight = l.getLineHeight(0, g2.getFontRenderContext()).orElse(0);
                    int iconY;
                    int yOffset;
                    if(i.getIconHeight() > firstLineHeight) {
                        iconY = 0;
                        yOffset = (i.getIconHeight() - firstLineHeight) / 2;
                    }
                    else {
                        iconY = (firstLineHeight - i.getIconHeight()) / 2;
                        yOffset = 0;
                    }
                    i.paintIcon(this, g2, 0, iconY);
                    g2.translate(i.getIconWidth() + DEFAULT_ICON_PADDING, yOffset);
                });

                g2.setColor(getForeground());
                l.draw(g2, 0, 0);
            });

        } finally {
            g2.dispose();
        }
    }

    private Point getPointRelativeToTextBounds(int ptX, int ptY) {
        Insets insets = getInsets();
        final int insetsLeft;
        final int insetsTop;
        if (insets != null) {
            insetsLeft = insets.left;
            insetsTop = insets.top;
        }
        else {
            insetsLeft = 0;
            insetsTop = 0;
        }

        int relX;
        int relY;

        if (icon.isPresent()) {
            Icon i = this.icon.get();
            // Adjust text horizontal offset
            int iconXOffset = i.getIconWidth() + DEFAULT_ICON_PADDING;
            relX = ptX - iconXOffset - insetsLeft;

            // Adjust text vertical offset
            // The text vertical offset depends upon whether the icon height is larger than the text height.
            int textYOffset = 0;
            if(theLayout.isPresent()) {
                StyledStringLayout l = theLayout.get();
                float textHeight = l.getHeight(getFontRenderContext());
                if(i.getIconHeight() > textHeight) {
                    textYOffset = (int) ((i.getIconHeight() - textHeight) / 2);
                }
            }
            else {
                textYOffset = 0;
            }
            relY = ptY - insetsTop - textYOffset;
        }
        else {
            relX = ptX - insetsLeft;
            relY = ptY - insetsTop;
        }

        return new Point(relX, relY);
    }

    @Override
    public Dimension getPreferredSize() {
        return theLayout.map(l -> {
            Insets i = getInsets();
            if(i == null) {
                i = NULL_INSETS;
            }
            int width = (int) l.getWidth(getFontRenderContext()) + i.left + i.right;
            if(icon.isPresent()) {
                width += icon.get().getIconWidth() + DEFAULT_ICON_PADDING;
            }
            int textHeight = (int) l.getHeight(getFontRenderContext());
            int iconHeight = 0;
            if(icon.isPresent()) {
                iconHeight = icon.get().getIconHeight();
            }
            int height = Math.max(textHeight, iconHeight) + i.top + i.bottom;

            return new Dimension(width, height);
        }).orElse(new Dimension(10, 10));
    }
}
