package markup;

import java.util.List;

public class Header extends MarkupList implements MarkupBlock {
    protected final int level;

    public Header(List<MarkupElement> elements, int level) {
        super(elements);
        this.level = level;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append("#".repeat(level));
        super.toMarkdown(stringBuilder);
    }

    @Override
    protected void putHtmlTag(StringBuilder stringBuilder) {
        stringBuilder.append("h").append(level);
    }
}
