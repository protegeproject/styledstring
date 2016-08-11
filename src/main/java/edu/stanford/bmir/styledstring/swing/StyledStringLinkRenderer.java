package edu.stanford.bmir.styledstring.swing;

import edu.stanford.bmir.styledstring.Style;
import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringLink;

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

    private StyledString styledString;

    private StyledString stringToRender;

    public StyledStringLinkRenderer() {
        this.styledString = StyledString.emptyString();
        this.stringToRender = styledString;
    }

    public void setStyledString(StyledString styledString) {
        this.styledString = checkNotNull(styledString);
    }

    public StyledString getStyledStringToRender() {
        return stringToRender;
    }

    public void setCursorPosition(int ptX, int ptY, StyledStringLayout layout, FontRenderContext frc) {
        checkNotNull(layout);
        checkNotNull(frc);
        Optional<StyledStringLink> linkAtPt = layout.getLinkAt(ptX, ptY, frc);
        if(linkAtPt.isPresent()) {
            StyledStringLink link = linkAtPt.get();
            StyledString.Builder builder = layout.getStyledString().toBuilder();
            builder.applyStyle(link.getStartIndex(), link.getEndIndex(), linkStyle);
            stringToRender = builder.build();
        }
        else {
            stringToRender = styledString;
        }
    }

    public void clearCursorPosition() {
        stringToRender = styledString;
    }
}
