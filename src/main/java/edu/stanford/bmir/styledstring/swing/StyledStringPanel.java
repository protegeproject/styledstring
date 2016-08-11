package edu.stanford.bmir.styledstring.swing;

import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringLink;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.Optional;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class StyledStringPanel extends JPanel {

    public static final int DEFAULT_ICON_PADDING = 7;

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
        return ((Graphics2D) getGraphics()).getFontRenderContext();
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
                    int iconY = (firstLineHeight - i.getIconHeight()) / 2;
                    i.paintIcon(this, g2, 0, iconY);
                    g2.translate(i.getIconWidth() + DEFAULT_ICON_PADDING, 0);
                });

                g2.setColor(getForeground());
                l.draw(g2, 0, 0);
            });

        } finally {
            g2.dispose();
        }
    }

    private Point getPointRelativeToTextBounds(int ptX, int ptY) {
        // Translate
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
        if (icon.isPresent()) {
            relX = ptX - icon.get().getIconWidth() - DEFAULT_ICON_PADDING - insetsLeft;
        }
        else {
            relX = ptX - insetsLeft;
        }
        int relY = ptY - insetsTop;
        return new Point(relX, relY);
    }

    @Override
    public Dimension getPreferredSize() {
        return theLayout.map(l -> {
            int width = (int) l.getWidth(getFontRenderContext());
            if(icon.isPresent()) {
                width += icon.get().getIconWidth() + DEFAULT_ICON_PADDING;
            }
            int height = (int) l.getHeight(getFontRenderContext());
            return new Dimension(width, height);
        }).orElse(new Dimension(10, 10));
    }
}
