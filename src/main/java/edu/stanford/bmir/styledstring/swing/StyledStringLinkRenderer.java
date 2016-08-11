package edu.stanford.bmir.styledstring.swing;

import edu.stanford.bmir.styledstring.Style;
import edu.stanford.bmir.styledstring.StyledString;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 11 Aug 16
 */
public class StyledStringLinkRenderer {

    private final Style linkStyle = Style.builder().withForeground(Color.BLUE).withUnderline().build();

    private StyledString styledString = StyledString.emptyString();

    private StyledString stringToRender = StyledString.emptyString();

    private Optional<StyledStringLayout> layout = Optional.empty();

    public StyledStringLinkRenderer() {
    }

    /**
     * Sets the nominal string that should be rendered.
     * @param styledString The string to be rendered.
     */
    public void setStyledString(@Nonnull StyledString styledString) {
        this.styledString = checkNotNull(styledString);
        this.stringToRender = styledString;
        this.layout = Optional.of(StyledStringLayout.create(styledString));
    }

    /**
     * Gets the string to render.
     * @return  The string to render.  This may or may not be the value set by the setter method.  The result depends
     * on calls to setCursorPosition.
     */
    @Nonnull
    public StyledString getStyledStringToRender() {
        return stringToRender;
    }

    /**
     * Sets the cursor position relative to the bounding box for the styled string held by this manager.  This method
     * should be called after the call to setStyledString.
     * @param ptX The x co-ordinate of the cursor position.
     * @param ptY The y co-ordinate of the cursor position.
     * @param frc The font render context that is used to compute character widths for hit testing.
     */
    public void setCursorPosition(int ptX, int ptY, @Nonnull FontRenderContext frc) {
        checkNotNull(frc);
        // Set the default value - i.e. the nominal string
        stringToRender = styledString;
        layout.flatMap(l -> l.getLinkAt(ptX, ptY, frc))
                .ifPresent(link -> {
                    // Apply link style
                    StyledString.Builder builder = styledString.toBuilder();
                    builder.applyStyle(link.getStartIndex(), link.getEndIndex(), linkStyle);
                    stringToRender = builder.build();
                });
    }

    public void clearCursorPosition() {
        stringToRender = styledString;
    }
}
