package markup;


abstract class FramingHtml implements HtmlConvertible {

    protected abstract void putHtmlContent(StringBuilder stringBuilder);
    protected abstract void putHtmlTag(StringBuilder stringBuilder);
    @Override
    public final void toHtml(StringBuilder stringBuilder) {
        stringBuilder.append("<");
        putHtmlTag(stringBuilder);
        stringBuilder.append(">");
        putHtmlContent(stringBuilder);
        stringBuilder.append("</");
        putHtmlTag(stringBuilder);
        stringBuilder.append(">");
    }
}
