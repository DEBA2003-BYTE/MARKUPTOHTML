package markup;

import java.util.List;

public class OrderedList extends ListItemSequence {
    public OrderedList(List<ListItem> elements) {
        super(elements);
    }

    @Override
    protected void putHtmlTag(StringBuilder stringBuilder) {
        stringBuilder.append("ol");
    }
}
