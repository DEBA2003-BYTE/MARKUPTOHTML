package markup;


import java.util.List;

public class ParagraphBlock extends MarkupList implements MarkupBlock {
    public ParagraphBlock(List<MarkupElement> elements) {
        super(elements);
    }

    @Override
    protected void putHtmlTag(StringBuilder stringBuilder) {
        stringBuilder.append("p");
    }

}

