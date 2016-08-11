package edu.stanford.bmir.styledstring.html;

import com.google.common.collect.ImmutableList;
import edu.stanford.bmir.styledstring.StyledString;
import edu.stanford.bmir.styledstring.StyledStringMarkup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 09/12/14
 */
@RunWith(MockitoJUnitRunner.class)
public class HtmlRenderer_TestCase {


    private HtmlRenderer htmlRenderer;

    @Before
    public void setUp() throws Exception {
        htmlRenderer = new HtmlRenderer();
    }

    @Test
    public void shouldGenerateToString() {
        assertThat(htmlRenderer.toString(), startsWith("HtmlRenderer"));
    }

    private String getRendering(String input) {
        return htmlRenderer.toHTML(new StyledString(input, ImmutableList.of(), ImmutableList.of()));
    }

    @Test
    public void shouldReplaceTabWithNonBreakingSpaces() {
        String input = "\t";
        String rendering = getRendering(input);
        assertThat(rendering, containsString("&nbsp;&nbsp;&nbsp;&nbsp;"));
    }
}