package edu.stanford.bmir.styledstring.html;

import edu.stanford.bmir.styledstring.Style;
import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringMarkup;
import edu.stanford.bmir.styledstring.attributes.StyleAttribute;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

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

        List<StyledStringMarkup> sortedMarkups = new ArrayList<StyledStringMarkup>(styledString.getMarkup());
        Collections.sort(sortedMarkups);
        Set<Style> currentStyles = new HashSet<Style>();
        List<Integer> runLimits = new ArrayList<Integer>();
        for (int i = 0; i < styledString.length(); i++) {
            Set<Style> iStyles = new HashSet<Style>();
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
        int counter = 0;
        for (String line : lines) {
            counter++;
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
}
