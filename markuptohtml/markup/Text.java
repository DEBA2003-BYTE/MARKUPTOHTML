package markup;

public class Text implements MarkupElement {
    private final String content;

    public Text(String content) {
        this.content = content;
    }
    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(content);
    }
    public void toHtml(StringBuilder stringBuilder) {
        stringBuilder.append(content);
    }
}
