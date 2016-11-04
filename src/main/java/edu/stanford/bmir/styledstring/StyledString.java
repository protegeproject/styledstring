package edu.stanford.bmir.styledstring;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import edu.stanford.bmir.styledstring.attributes.StyleAttribute;

import com.google.common.base.Objects;

import java.util.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Matthew Horridge
 * Stanford University
 * Bio-Medical Informatics Research Group
 * Date: 5th December 2014
 * <p>
 * A string marked up with various attributes.  Each StyledString is immutable.
 * </p>
 */
public final class StyledString implements CharSequence, Comparable<StyledString> {

    private static final String EMPTY_STRING = "";

    private static final StyledString EMPTY_STYLED_STRING = new StyledString();

    private final String plainString;

    private final ImmutableList<StyledStringMarkup> plainStringMarkup;

    private final ImmutableList<StyledStringLink> links;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    /**
     * Constructs a StyledString with the specified plain string marked up with the specified markup.
     * @param text The plain string.  Not {@code null}.
     * @param markup The markup.  Not {@code null}.  May be empty.
     * @throws NullPointerException if {@code text} is {@code null}, if {@code markup} is {@code null}.
     * @throws NullPointerException if {@code text} is {@code null}, if {@code markup} is {@code null}.
     */
    public StyledString(String text, List<StyledStringMarkup> markup, List<StyledStringLink> links) {
        this.plainString = checkNotNull(text);
        this.plainStringMarkup = ImmutableList.copyOf(checkNotNull(markup));
        this.links = ImmutableList.copyOf(checkNotNull(links));
    }

    /**
     * A convenience method that returns an empty styled string.
     * @return A StyledString that is the empty string. Not {@code null}.
     */
    public static StyledString emptyString() {
        return EMPTY_STYLED_STRING;
    }

    /**
     * Constructs an empty StyledString.
     */
    private StyledString() {
        this(EMPTY_STRING);
    }

    /**
     * Constructs a StyledString for the specified plain string.  The string has no markup associated with it.
     * @param text The plain string.  Not {@code null}.
     * @throws NullPointerException if text is {@code null}.
     */
    public StyledString(String text) {
        this(text, ImmutableList.of(), ImmutableList.of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the {@code char} value at the specified index.  An index ranges from zero
     * to <tt>length() - 1</tt>.  The first {@code char} value of the sequence is at
     * index zero, the next at index one, and so on, as for array
     * indexing. </p>
     * <p/>
     * <p>If the {@code char} value specified by the index is a
     * <a href="Character.html#unicode">surrogate</a>, the surrogate
     * value is returned.
     * @param index the index of the {@code char} value to be returned
     * @return the specified {@code char} value
     * @throws IndexOutOfBoundsException if the <tt>index</tt> argument is negative or not less than
     *                                   <tt>length()</tt>
     */
    @Override
    public char charAt(int index) {
        return plainString.charAt(index);
    }

    /**
     * Returns a new {@code CharSequence} that is a subsequence of this sequence.
     * The subsequence starts with the {@code char} value at the specified index and
     * ends with the {@code char} value at index <tt>end - 1</tt>.  The length
     * (in {@code char}s) of the
     * returned sequence is <tt>end - start</tt>, so if <tt>start == end</tt>
     * then an empty sequence is returned. </p>
     * @param start the start index, inclusive
     * @param end the end index, exclusive
     * @return the specified subsequence
     * @throws IndexOutOfBoundsException if <tt>start</tt> or <tt>end</tt> are negative,
     *                                   if <tt>end</tt> is greater than <tt>length()</tt>,
     *                                   or if <tt>start</tt> is greater than <tt>end</tt>
     */
    @Override
    public StyledString subSequence(int start, int end) {
        checkStartAndEnd(start, end);
        return substring(start, end);
    }

    @Override
    public int length() {
        return plainString.length();
    }


    /**
     * Checks to see if {@code start} and {@code end} are within bounds for this string.
     * @param start The start
     * @param end The end
     * @throws IndexOutOfBoundsException if {@code start} or {@code end} are negative,
     *                                   if {@code end} is greater than {@code length()}
     *                                   if {@code start} is greater than {@code end}.
     */
    private void checkStartAndEnd(int start, int end) {
        if (start < 0) {
            throw new IndexOutOfBoundsException("start < 0");
        }
        if (end < 0) {
            throw new IndexOutOfBoundsException("end < 0");
        }
        if (end > length()) {
            throw new IndexOutOfBoundsException("end > length");
        }
        if (start > end) {
            throw new IndexOutOfBoundsException("start > end");
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Compares this StyledString with another StyledString.  The comparison is based purely on the lexical value of
     * the
     * each StyledString and not the markup.
     * @param o The other StyledString to compare to.
     * @return See {@link String#compareTo(Object)}.
     */
    @Override
    public int compareTo(StyledString o) {
        return this.plainString.compareTo(o.plainString);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StyledString)) {
            return false;
        }
        StyledString other = (StyledString) obj;
        return this.plainString.equals(other.plainString) && this.plainStringMarkup.equals(other.plainStringMarkup);
    }

    @Override
    public int hashCode() {
        return this.plainString.hashCode() + plainStringMarkup.hashCode();
    }


    @Override
    public String toString() {
        return Objects.toStringHelper("StyledString")
                .addValue(plainString)
                .add("markup", plainStringMarkup)
                .toString();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean isEmpty() {
        return plainString.isEmpty();
    }


    public String getString() {
        return plainString;
    }

    public ImmutableList<StyledStringMarkup> getMarkup() {
        return plainStringMarkup;
    }



    public StyledString substring(int start, int end) {
        if (end < start) {
            throw new IndexOutOfBoundsException("start (" + start + ") < end (" + end + ")");
        }
        if (start == end) {
            return EMPTY_STYLED_STRING;
        }
        String substring = plainString.substring(start, end);
        ImmutableList.Builder<StyledStringMarkup> substringMarkup = ImmutableList.builder();
        for (StyledStringMarkup markup : plainStringMarkup) {
            if (start < markup.getEnd() && end > markup.getStart()) {
                int substringMarkupStart;
                if (markup.getStart() < start) {
                    substringMarkupStart = start;
                }
                else {
                    substringMarkupStart = markup.getStart();
                }
                substringMarkupStart = substringMarkupStart - start;

                int substringMarkupEnd;
                if (markup.getEnd() > end) {
                    substringMarkupEnd = end;
                }
                else {
                    substringMarkupEnd = markup.getEnd();
                }
                substringMarkupEnd = substringMarkupEnd - start;
                substringMarkup.add(new StyledStringMarkup(substringMarkupStart, substringMarkupEnd, markup.getStyle()));
            }
        }
        ImmutableList.Builder<StyledStringLink> substringLinks = ImmutableList.builder();
        for(StyledStringLink link : links) {
            if(start < link.getEndIndex() && end > link.getStartIndex()) {
                int substringLinkStart;
                if (link.getStartIndex() < start) {
                    substringLinkStart = start;
                }
                else {
                    substringLinkStart = link.getStartIndex();
                }
                substringLinkStart = substringLinkStart - start;

                int substringLinkEnd;
                if (link.getEndIndex() > end) {
                    substringLinkEnd = end;
                }
                else {
                    substringLinkEnd = link.getEndIndex();
                }
                substringLinkEnd = substringLinkEnd - start;
                substringLinks.add(new StyledStringLink(substringLinkStart, substringLinkEnd, link.getLinkObject()));
            }
        }
        return new StyledString(substring, substringMarkup.build(), substringLinks.build());
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public List<Style> getStylesAt(int index) {
        List<Style> styles = new ArrayList<Style>();
        for (StyledStringMarkup markup : plainStringMarkup) {
            if (markup.getStart() <= index && index < markup.getEnd()) {
                styles.add(markup.getStyle());
            }
        }
        return styles;
    }

    public ImmutableList<StyledStringLink> getLinks() {
        return links;
    }

    public Optional<StyledStringLink> getLinkAt(int index) {
        for(StyledStringLink link : links) {
            if(link.containsIndex(index)) {
                return Optional.of(link);
            }
        }
        return Optional.empty();
    }

    public Style getMergedStyle(int index) {
        List<Style> styles = getStylesAt(index);
        if (styles.isEmpty()) {
            return Style.emptyStyle();
        }
        if (styles.size() == 1) {
            return styles.get(0);
        }
        Map<Class<? extends StyleAttribute>, StyleAttribute> atts = new HashMap<Class<? extends StyleAttribute>, StyleAttribute>();
        for (Style style : styles) {
            for (StyleAttribute styleAttribute : style.getStyleAttributes()) {
                atts.put(styleAttribute.getClass(), styleAttribute);
            }
        }
        return new Style(new ArrayList<StyleAttribute>(atts.values()));
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Gets a StyledString.Builder initialised with the contents of this StyledString.
     * @return A Builder.  Not {@code null}.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for StyledStrings.  The Builder allows strings to be appended to each other with specification of
     * Styles or StyleAttributes.
     */
    public static final class Builder {

        public static final String NEW_LINE = "\n";

        public static final String SPACE = " ";

        public static final String TAB = "    ";

        private StringBuilder buffer = new StringBuilder();

        private ImmutableList.Builder<StyledStringMarkup> markup = ImmutableList.builder();

        private ImmutableList.Builder<StyledStringLink> links = ImmutableList.builder();

        public Builder() {

        }

        public Builder(StyledString styledString) {
            buffer.append(styledString.plainString);
            markup.addAll(styledString.plainStringMarkup);
            links.addAll(styledString.links);
        }

        public int mark() {
            return buffer.length();
        }

        public void append(String s) {
            buffer.append(s);
        }

        public void appendWithStyle(String s, Style style) {
            int start = mark();
            buffer.append(s);
            int end = mark();
            markup.add(new StyledStringMarkup(start, end, style));
        }

        public void appendWithAttributes(String s, StyleAttribute... attributes) {
            int start = mark();
            buffer.append(s);
            int end = mark();
            Style style = new Style(Arrays.asList(attributes));
            markup.add(new StyledStringMarkup(start, end, style));
        }


        public void appendNewLine() {
            buffer.append(NEW_LINE);
        }

        public void appendSpace() {
            buffer.append(SPACE);
        }

        public void appendTab() {
            buffer.append(TAB);
        }

        public void append(Number i) {
            buffer.append(i);
        }

        public void appendStyledString(StyledString styledString) {
            int offset = mark();
            buffer.append(styledString.plainString);
            for (StyledStringMarkup markup : styledString.plainStringMarkup) {
                this.markup.add(new StyledStringMarkup(markup.getStart() + offset, markup.getEnd() + offset, markup.getStyle()));
            }
        }

        public void applyStyle(int from, int to, Style style) {
            markup.add(new StyledStringMarkup(from, to, style));
        }

        public void applyStyleAttributes(int from, int to, StyleAttribute... styleAttributes) throws IndexOutOfBoundsException {
            if (styleAttributes.length == 0) {
                return;
            }
            if (buffer.length() == 0) {
                return;
            }
            if (from < 0) {
                throw new IndexOutOfBoundsException("from < 0");
            }
            if (to < 0) {
                throw new IndexOutOfBoundsException("to < 0");
            }
            if (from >= buffer.length()) {
                throw new IndexOutOfBoundsException("from >= mark");
            }
            if (to > buffer.length()) {
                throw new IndexOutOfBoundsException("to >= mark");
            }
            Style style = new Style(Arrays.asList(styleAttributes));
            markup.add(new StyledStringMarkup(from, to, style));
        }

        public void applyStyleAttributes(StyleAttribute... styleAttributes) {
            applyStyleAttributes(0, mark(), styleAttributes);
        }


        public StyledString build() {
            return new StyledString(buffer.toString(), markup.build(), links.build());
        }

        public void addLink(StyledStringLink styledStringLink) {
            links.add(styledStringLink);
        }
    }

}
