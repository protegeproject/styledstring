package edu.stanford.bmir.styledstring;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10 Aug 16
 */
public class StyledStringLink {

    private final int startIndex;

    private final int endIndex;

    private final Object linkObject;

    public StyledStringLink(int startIndex, int endIndex, Object linkObject) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.linkObject = checkNotNull(linkObject);
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public Object getLinkObject() {
        return linkObject;
    }

    public boolean containsIndex(int index) {
        return startIndex <= index && index < endIndex;
    }

    @Override
    public int hashCode() {
        return startIndex * 13 + endIndex * 7 + linkObject.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StyledStringLink)) {
            return false;
        }
        StyledStringLink other = (StyledStringLink) obj;
        return this.startIndex == other.startIndex
                && this.endIndex == other.endIndex
                && this.linkObject.equals(other.linkObject);
    }


    @Override
    public String toString() {
        return toStringHelper("StyledStringLink")
                .add("start", startIndex)
                .add("end", endIndex)
                .add("value", linkObject)
                .toString();
    }
}
