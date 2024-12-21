package markup;

public class Image implements MarkupElement {
    protected final String alt;
    protected final String source;

    public Image(String alt, String source) {
        this.alt = alt;
        this.source = source;
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        stringBuilder.append("<img alt='");
        stringBuilder.append(alt);
        stringBuilder.append("' src='");
        stringBuilder.append(source);
        stringBuilder.append("'>");
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append("![");
        stringBuilder.append(alt);
        stringBuilder.append("](");
        stringBuilder.append(source);
        stringBuilder.append(")");
    }
}
