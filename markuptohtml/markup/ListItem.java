package markup;

import java.util.List;

public class ListItem extends FramingHtml {
    private final List<MarkupBlock> elements;

    public ListItem(List<MarkupBlock> elements) {
        this.elements = elements;
    }

    @Override
    protected void putHtmlContent(StringBuilder stringBuilder) {
        for (HtmlConvertible element : elements) {
            element.toHtml(stringBuilder);
        }
    }

    @Override
    protected void putHtmlTag(StringBuilder stringBuilder) {
        stringBuilder.append("li");
    }
}
