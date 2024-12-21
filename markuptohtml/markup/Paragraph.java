package markup;

import java.util.List;

public class Paragraph implements MarkupBlock, MarkdownConvertible {
    protected List<MarkupElement> elements;
    public Paragraph(List<MarkupElement> elements) {
        this.elements = elements;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        for (MarkdownConvertible element : elements) {
            element.toMarkdown(stringBuilder);
        }
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        for (HtmlConvertible element : elements) {
            element.toHtml(stringBuilder);
        }
    }
}
