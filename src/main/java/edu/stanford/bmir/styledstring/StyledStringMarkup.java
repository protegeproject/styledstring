package edu.stanford.bmir.styledstring;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 */
public class StyledStringMarkup implements Comparable<StyledStringMarkup> {

    private final int start;

    private final int end;

    private final Style style;

    public StyledStringMarkup(int start, int end, Style style) {
        this.start = start;
        this.end = end;
        this.style = checkNotNull(style);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public Style getStyle() {
        return style;
    }

    @Override
    public int hashCode() {
        return start + end + style.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StyledStringMarkup)) {
            return false;
        }
        StyledStringMarkup other = (StyledStringMarkup) obj;
        return this.start == other.start
                && this.end == other.end
                && this.style.equals(other.style);
    }

    public int compareTo(StyledStringMarkup o) {
        int startDiff = this.start - o.start;
        if (startDiff != 0) {
            return startDiff;
        }
        int endDiff = this.end - o.end;
        if (endDiff != 0) {
            return endDiff;
        }
        return 0;
    }
}
