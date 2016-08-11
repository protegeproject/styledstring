package edu.stanford.bmir.styledstring.swing;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10 Aug 16
 */
public class HitInfo {

    private final int lineNumber;

    private final int lineIndex;

    private final int absoluteIndex;

    public HitInfo(int lineNumber, int lineIndex, int absoluteIndex) {
        checkArgument(lineNumber >= 0, "Line number must be greater or equal to zero");
        this.lineNumber = lineNumber;
        this.lineIndex = lineIndex;
        this.absoluteIndex = absoluteIndex;
    }

    public int getAbsoluteIndex() {
        return absoluteIndex;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    @Override
    public int hashCode() {
        return lineNumber * 13 + lineIndex * 7 + absoluteIndex;
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
        return this.lineNumber == other.lineNumber && this.lineIndex == other.lineIndex && this.absoluteIndex == other.absoluteIndex;
    }

    @Override
    public String toString() {
        return toStringHelper("HitInfo")
                .add("line", lineNumber)
                .add("column", lineIndex)
                .add("absoluteIndex", absoluteIndex)
                .toString();
    }
}
