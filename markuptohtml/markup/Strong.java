package markup;

import java.util.List;

public class Strong extends FramingMarkup {

    public Strong(List<MarkupElement> elements) {
        super(elements);
    }

    @Override
    protected void markdownFrame(StringBuilder stringBuilder) {
        stringBuilder.append("__");
    }

    @Override
    protected void putHtmlTag(StringBuilder stringBuilder) {
        stringBuilder.append("strong");
    }
}
