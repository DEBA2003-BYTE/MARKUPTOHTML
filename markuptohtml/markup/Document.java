package markup;

import java.util.List;

public class Document implements HtmlConvertible {
    protected final List<MarkupBlock> blocks;

    public Document(List<MarkupBlock> blocks) {
        this.blocks = blocks;
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        if (blocks.size() == 0) {
            return;
        }
        blocks.get(0).toHtml(stringBuilder);
        for (int i = 1; i < blocks.size(); i++) {
            stringBuilder.append(System.lineSeparator());
            blocks.get(i).toHtml(stringBuilder);
        }
    }
}
