package edu.stanford.bmir.styledstring.swing;

import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringLink;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 *
 * Represents a component that can draw StyledString objects.  The component allows an icon to be specified (rather
 * like a JLabel does).  Multiple lines are possible and links are also possible.  Note that links are rendered
 * passively on "hover" by the client calling the {@link #setCursorPosition(int, int, RepaintRequest)} method or the
 * {@link #clearCursorPosition(RepaintRequest)} method.
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
        setFont(new Font("menlo", Font.PLAIN, 12));
    }


    /**
     * Sets the StyledString to be drawn by this component.
     * @param styledString The StyledString.
     */
    public void setStyledString(@Nonnull StyledString styledString) {
        checkNotNull(styledString);
        cachedPreferredSize = null;
        this.styledString = styledString;
        styledStringLinkRenderer.setStyledString(styledString);
        rebuildPaintedString(styledString, RepaintRequest.DO_NOT_REPAINT);
    }

    /**
     * Sets the Icon to be drawn by this component.
     * @param icon The icon.
     */
    public void setIcon(@Nonnull Icon icon) {
        this.icon = Optional.of(icon);
        cachedPreferredSize = null;
    }

    /**
     * Removes the icon drawn by this component.
     */
    public void clearIcon() {
        this.icon = Optional.empty();
        cachedPreferredSize = null;
    }

    /**
     * Sets the cursor position in order to decide whether links should be rendered.
     * @param ptX The X co-ordinate of the cursor position relative to the top left of this component.
     * @param ptY The Y co-ordinate of the cursor position relative to the top left of this component.
     * @param repaintRequest Specifies whether the componet should be repainted after setting the cursor position.  If
     *                       this component has been added to another component in a UI then this should usually be set
     *                       to {@link RepaintRequest#REPAINT}.  If, however, this component is used as a cell renderer
     *                       in a tree or list etc. then the repaintRequest value should usually be set to
     *                       {@link RepaintRequest#DO_NOT_REPAINT}.
     */
    public void setCursorPosition(final int ptX, final int ptY, RepaintRequest repaintRequest) {
        theLayout.ifPresent(l -> {
            FontRenderContext frc = getFontRenderContext();
            Point point = getPointRelativeToTextBounds(ptX, ptY);
            styledStringLinkRenderer.setCursorPosition(point.x, point.y, frc);
            StyledString paintedString = styledStringLinkRenderer.getStyledStringToRender();
            rebuildPaintedString(paintedString, repaintRequest);
        });
    }

    /**
     * Clears a previously set cursor position.
     * @param repaintRequest Specifies whether or not the component should be repainted after clearing the cursor.
     *                       Please see {@link #setCursorPosition(int, int, RepaintRequest)} for an explanation of
     *                       this parameter.
     */
    public void clearCursorPosition(RepaintRequest repaintRequest) {
        styledStringLinkRenderer.clearCursorPosition();
        rebuildPaintedString(styledString, repaintRequest);
    }


    /**
     * Gets the link at the specified point.
     * @param ptX The X co-ordinate of the cursor position relative to the top left of this component.
     * @param ptY The Y co-ordinate of the cursor position relative to the top left of this component.
     * @return An optional {@link StyledStringLink} that is located at the specified point.  An empty value indicates
     * that there is no link at the specified point.
     */
    public Optional<StyledStringLink> getLinkAt(int ptX, int ptY) {
        return theLayout.flatMap(l -> {
            Point p = getPointRelativeToTextBounds(ptX, ptY);
            return l.getLinkAt(p.x, p.y, getFontRenderContext());
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
//                    int firstLineHeight = l.getLineHeight(0, g2.getFontRenderContext()).orElse(0);
                    int firstLineHeight = (int) l.getHeight(g2.getFontRenderContext());
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

    private Dimension cachedPreferredSize = null;

    @Override
    public Dimension getPreferredSize() {
        if(cachedPreferredSize != null) {
            return cachedPreferredSize;
        }
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
            int iconHeight = icon.map(Icon::getIconHeight).orElse(0);
            int height = Math.max(textHeight, iconHeight) + i.top + i.bottom;
            return cachedPreferredSize = new Dimension(width, height);
        }).orElse(new Dimension(10, 10));
    }
}
