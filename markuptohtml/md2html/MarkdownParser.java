package md2html;

import markup.*;

import java.util.ArrayList;
import java.util.List;

public class MarkdownParser {
    private int pos;
    private String blockContent;
    private boolean isCodeOpened = false;
    private boolean emphasisOpened = false;
    private boolean strongOpened = false;
    private boolean strikeoutOpened = false;

    private static boolean isLineSeparator(char c) {
        return c == '\n' || c == '\r' || c == '\u2028' || c == '\u2029' || c == '\u0085';
    }

    private static boolean isCrLf(String content, int pos) {
        return content.startsWith("\r\n", pos);
    }

    private boolean isSingleTwinTag() {
        return ((pos > 0 && isSpace(blockContent.charAt(pos - 1))) &&
                (pos + 2 < blockContent.length() &&
                        isSpace(blockContent.charAt(pos + 2))));
    }

    private boolean isStrong() {
        return ((blockContent.startsWith("__", pos) || blockContent.startsWith("**", pos)) && !isSingleTwinTag());
    }

    private boolean isSpace(char c) {
        return Character.isWhitespace(c);
    }

    private boolean isStrikeout() {
        return blockContent.startsWith("--", pos) && !isSingleTwinTag();
    }

    private boolean isCode() {
        return blockContent.charAt(pos) == '`';
    }

    private boolean isEscapable() {
        if (pos + 1 >= blockContent.length()) {
            return false;
        }
        final char nextChar = blockContent.charAt(pos + 1);
        return nextChar == '*' || nextChar == '-' || nextChar == '_' || nextChar == '\\';
    }

    private boolean isEscaping() {
        return pos > 0 && blockContent.charAt(pos) == '\\' && isEscapable();
    }

    private void addParsedText(List<MarkupElement> parsed, StringBuilder text) {
        if (text.length() != 0) {
            parsed.add(new Text(text.toString()));
            text.setLength(0);
        }
    }

    private void addCurrentAndEscape(StringBuilder text) {
        final char charToEscape = blockContent.charAt(pos);
        switch (charToEscape) {
            case '<' -> {
                text.append("&lt;");
                return;
            }
            case '>' -> {
                text.append("&gt;");
                return;
            }
            case '&' -> {
                text.append("&amp;");
                return;
            }
        }
        text.append(charToEscape);
    }

    private boolean isEmphasis() {
        final char tag = blockContent.charAt(pos);
        return ((tag == '*' || tag == '_') &&
                ((pos == 0 || !isSpace(blockContent.charAt(pos - 1))) ||
                        pos + 1 == blockContent.length() ||
                        !isSpace(blockContent.charAt(pos + 1))));
    }

    private int getHeaderLevel() {
        int level = 0;
        while (level < Math.min(blockContent.length(), 6) && blockContent.charAt(level) == '#') {
            level++;
        }
        if (level == blockContent.length() || !isSpace(blockContent.charAt(level))) {
            return 0;
        }
        return level;
    }

    private MarkupBlock parseBlock(String blockContent) {
        this.blockContent = blockContent;
        int headerLevel = getHeaderLevel();
        pos = headerLevel == 0 ? 0 : headerLevel + 1;
        emphasisOpened = false;
        strikeoutOpened = false;
        strongOpened = false;
        isCodeOpened = false;
        List<MarkupElement> innerMarkup = parseBlockContent();
        if (headerLevel == 0) {
            return new ParagraphBlock(innerMarkup);
        }
        return new Header(innerMarkup, headerLevel);
    }

    public Document parse(String content) {
        List<MarkupBlock> outputBlocks = new ArrayList<>();
        for (int i = 0; i < content.length();) {
            while (i < content.length() && isLineSeparator(content.charAt(i))) {
                i++;
            }
            final int start = i;
            int end = i;
            outer: while (i < content.length()) {
                i++;
                end = i;
                int lineBreakCount = 0;
                while (i < content.length() && isLineSeparator(content.charAt(i))) {
                    if (isCrLf(content, i)) {
                        i++;
                    }
                    i++;
                    lineBreakCount++;
                    if (lineBreakCount == 2) {
                        break outer;
                    }
                }
            }
            if (end - start > 0) {
                outputBlocks.add(parseBlock(content.substring(start, end)));
            }
        }
        return new Document(outputBlocks);
    }

    private boolean isImageStart() {
        return blockContent.charAt(pos) == '!';
    }

    private Image parseImage() {
        pos += 2;
        int tokenStart = pos;
        while (blockContent.charAt(pos) != ']') {
            pos++;
        }
        String alt = blockContent.substring(tokenStart, pos);
        pos += 2;
        tokenStart = pos;
        while (blockContent.charAt(pos) != ')') {
            pos++;
        }
        String source = blockContent.substring(tokenStart, pos);
        return new Image(alt, source);
    }

    private List<MarkupElement> parseBlockContent() {
        List<MarkupElement> parsed = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        for (; pos < blockContent.length(); pos++) {
            if (isEscaping()) {
                pos++;
                addCurrentAndEscape(text);
            } else if (isStrong()) {
				// :NOTE: отсюда и на несколько else if ниже идёт дублирование кода. Решение: сделать отдельную функцию со switch-case,
				//        проводящую соответствие между строчкой и тем, какой конструктор вызвать.
                strongOpened = !strongOpened;
                addParsedText(parsed, text);
                if (!strongOpened) {
                    pos++;
                    return List.of(new Strong(parsed));
                } else {
                    pos += 2;
                    parsed.addAll(parseBlockContent());
                }
            } else if (isEmphasis()) {
                emphasisOpened = !emphasisOpened;
                addParsedText(parsed, text);
                if (!emphasisOpened) {
                    return List.of(new Emphasis(parsed));
                } else {
                    pos++;
                    parsed.addAll(parseBlockContent());
                }
            } else if (isStrikeout()) {
                strikeoutOpened = !strikeoutOpened;
                addParsedText(parsed, text);
                if (!strikeoutOpened) {
                    pos++;
                    return List.of(new Strikeout(parsed));
                } else {
                    pos += 2;
                    parsed.addAll(parseBlockContent());
                }
            } else if (isCode()) {
                isCodeOpened = !isCodeOpened;
                addParsedText(parsed, text);
                if (!isCodeOpened) {
                    return List.of(new InlineCode(parsed));
                } else {
                    pos++;
                    parsed.addAll(parseBlockContent());
                }
            } else if (isImageStart()) {
                addParsedText(parsed, text);
                parsed.add(parseImage());
            } else {
                addCurrentAndEscape(text);
            }
        }
        addParsedText(parsed, text);
        return parsed;
    }
}
