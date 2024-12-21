package markup;

import java.util.List;

public class UnorderedList extends ListItemSequence {
    public UnorderedList(List<ListItem> elements) {
        super(elements);
    }

    @Override
    protected void putHtmlTag(StringBuilder stringBuilder) {
        stringBuilder.append("ul");
    }
}
