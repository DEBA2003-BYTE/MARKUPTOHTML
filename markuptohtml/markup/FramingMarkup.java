package markup;

import java.util.List;

abstract class FramingMarkup extends MarkupList implements MarkupElement {
    protected FramingMarkup(List<MarkupElement> elements) {
        super(elements);
    }
    protected abstract void markdownFrame(StringBuilder stringBuilder);

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        markdownFrame(stringBuilder);
        super.toMarkdown(stringBuilder);
        markdownFrame(stringBuilder);
    }
}
