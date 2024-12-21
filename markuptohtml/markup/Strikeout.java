package markup;

import java.util.List;

public class Strikeout extends FramingMarkup {

    public Strikeout(List<MarkupElement> elements) {
        super(elements);
    }

    @Override
    protected void markdownFrame(StringBuilder stringBuilder) {
        stringBuilder.append('~');
    }

    @Override
    protected void putHtmlTag(StringBuilder stringBuilder) {
        stringBuilder.append("s");
    }
}
