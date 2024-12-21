package markup;

import java.util.List;

abstract class ListItemSequence extends FramingHtml implements MarkupBlock {
    protected final List<ListItem> elements;
    protected ListItemSequence(List<ListItem> elements) {
        this.elements = elements;
    }

    @Override
    protected void putHtmlContent(StringBuilder stringBuilder) {
        for (ListItem element : elements) {
            element.toHtml(stringBuilder);
        }
    }
}
