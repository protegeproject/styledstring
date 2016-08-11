package edu.stanford.bmir.styledstring.html;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import edu.stanford.bmir.styledstring.Style;
import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringMarkup;
import edu.stanford.bmir.styledstring.attributes.StyleAttribute;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/12/14
 */
public class HtmlRenderer {


    public String toHTML(StyledString styledString) {
        StringWriter sw = new StringWriter();
        renderIntoHTML(sw, styledString);
        return sw.getBuffer().toString();
    }



    private void renderIntoHTML(Writer writer, StyledString styledString) {
        StringBuilder pw = new StringBuilder();

        List<StyledStringMarkup> sortedMarkups = new ArrayList<>(styledString.getMarkup());
        Collections.sort(sortedMarkups);
        Set<Style> currentStyles = Sets.newHashSet();
        List<Integer> runLimits = Lists.newArrayList();
        for (int i = 0; i < styledString.length(); i++) {
            Set<Style> iStyles = Sets.newHashSet();
            for (StyledStringMarkup markup : sortedMarkups) {
                if (markup.getStart() <= i && i < markup.getEnd()) {
                    iStyles.add(markup.getStyle());
                }
            }
            if (!iStyles.equals(currentStyles) || styledString.charAt(i) == '\n') {
                runLimits.add(i);
                currentStyles.clear();
                currentStyles.addAll(iStyles);
            }
        }
        runLimits.add(styledString.length());
        int lastLimit = 0;
        for (Integer runLimit : runLimits) {
            List<StyleAttribute> styleAttributes = styledString.getMergedStyle(runLimit - 1).getStyleAttributes();
            if (!styleAttributes.isEmpty()) {
                pw.append("<span style=\"");
                for (StyleAttribute styleAttribute : styleAttributes) {
                    String propName = styleAttribute.getCssPropertyName();
                    String propValue = styleAttribute.getCssPropertyValue();
                    pw.append(propName);
                    pw.append(": ");
                    pw.append(propValue);
                    pw.append("; ");
                }
                pw.append("\">");
            }
            String substring = styledString.substring(lastLimit, runLimit).getString();
            pw.append(substring);
            if (!styleAttributes.isEmpty()) {
                pw.append("</span>");
            }
            lastLimit = runLimit;
        }


        String[] lines = pw.toString().split("\\n");
        PrintWriter p = new PrintWriter(writer);
        p.append("<div\">");
        for (String line : lines) {
            if (!line.equals("\n")) {
                p.append("<div class=\"line\">\n");
                p.append(line.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"));
                p.append("\n");
                p.append("</div>\n");
            }
        }
        p.append("</div>");
        p.flush();
    }


    @Override
    public String toString() {
        return Objects.toStringHelper("HtmlRenderer")
                .toString();
    }
}
