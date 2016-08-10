package edu.stanford.bmir.styledstring.swing;

import com.google.common.base.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10 Aug 16
 */
public class HitInfo {

    private final int lineNumber;

    private final int lineIndex;

    public HitInfo(int lineNumber, int lineIndex) {
        this.lineNumber = lineNumber;
        this.lineIndex = lineIndex;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    @Override
    public int hashCode() {
        return lineNumber * 13 + lineIndex * 7;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HitInfo)) {
            return false;
        }
        HitInfo other = (HitInfo) obj;
        return this.lineNumber == other.lineNumber && this.lineIndex == other.lineIndex;
    }

    @Override
    public String toString() {
        return toStringHelper("HitInfo")
                .add("line", lineNumber)
                .add("column", lineIndex)
                .toString();
    }
}
