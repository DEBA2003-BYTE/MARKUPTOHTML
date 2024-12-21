package markup;

import java.util.List;

public class InlineCode extends FramingMarkup {
    public InlineCode(List<MarkupElement> elements) {
        super(elements);
    }

    @Override
    protected void putHtmlTag(StringBuilder stringBuilder) {
        stringBuilder.append("code");
    }

    @Override
    protected void markdownFrame(StringBuilder stringBuilder) {
        stringBuilder.append("`");
    }
}
