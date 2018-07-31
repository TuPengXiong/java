package com.tupengxiong.jsoup;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class JsoupTest {
    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
    private static final int timeout = 5 * 1000;

    @SuppressWarnings("unused")
	public static void main(String... args) throws IOException {
        final String url = "https://a.cnaidai.com/webjr/invest/detail.htm?id=749&bidType=2";
        final String selector = null;

        // fetch the specified URL and parse to a HTML DOM
        Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(timeout)
        		.cookie("sdcm.sid", "s%3AOomjqANtqDqbKyfQ-ZbCXy9T0hC0x2-f.ZSHE7o7QZRPdjrNhvsjMieL3sO5SGM2GTjIKDr277j0")
        		.cookie("UM_distinctid", "16426349f2fb0e-060711cb6e1b17-396b4e08-1fa400-16426349f304be")
        		.cookie("Hm_lvt_53f7e7fa076e6897da0a039eb736b10b", "1529659983")
        		.cookie("sdcm.sid", "s%3AOomjqANtqDqbKyfQ-ZbCXy9T0hC0x2-f.ZSHE7o7QZRPdjrNhvsjMieL3sO5SGM2GTjIKDr277j0")
        		.get();

        HtmlToPlainText formatter = new HtmlToPlainText();

        if (selector != null) {
            Elements elements = doc.select(selector); // get each element that matches the CSS selector
            for (Element element : elements) {
                String plainText = formatter.getPlainText(element); // format that element to plain text
                System.out.println("------------"+plainText);
            }
        } else { // format the whole doc
        	 System.out.println("-----start-------");
            String plainText = formatter.getPlainText(doc);
            System.out.println("------plainText------"+plainText);
            System.out.println("-----end-------");
        }
    }

    /**
     * Format an Element to plain-text
     * @param element the root element to format
     * @return formatted text
     */
    public String getPlainText(Element element) {
        FormattingVisitor formatter = new FormattingVisitor();
        NodeTraversor traversor = new NodeTraversor(formatter);
        traversor.traverse(element); // walk the DOM, and call .head() and .tail() for each node

        return formatter.toString();
    }

    // the formatting rules, implemented in a breadth-first DOM traverse
    private class FormattingVisitor implements NodeVisitor {
        private static final int maxWidth = 80;
        private int width = 0;
        private StringBuilder accum = new StringBuilder(); // holds the accumulated text

        // hit when the node is first seen
        public void head(Node node, int depth) {
            String name = node.nodeName();
            if (node instanceof TextNode)
                append(((TextNode) node).text()); // TextNodes carry all user-readable text in the DOM.
            else if (name.equals("li"))
                append("\n * ");
            else if (name.equals("dt"))
                append("  ");
            else if (StringUtil.in(name, "p", "h1", "h2", "h3", "h4", "h5", "tr"))
                append("\n");
        }

        // hit when all of the node's children (if any) have been visited
        public void tail(Node node, int depth) {
            String name = node.nodeName();
            if (StringUtil.in(name, "br", "dd", "dt", "p", "h1", "h2", "h3", "h4", "h5"))
                append("\n");
            else if (name.equals("a"))
                append(String.format(" <%s>", node.absUrl("href")));
        }

        // appends text to the string builder with a simple word wrap method
        private void append(String text) {
            if (text.startsWith("\n"))
                width = 0; // reset counter if starts with a newline. only from formats above, not in natural text
            if (text.equals(" ") &&
                    (accum.length() == 0 || StringUtil.in(accum.substring(accum.length() - 1), " ", "\n")))
                return; // don't accumulate long runs of empty spaces

            if (text.length() + width > maxWidth) { // won't fit, needs to wrap
                String words[] = text.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    boolean last = i == words.length - 1;
                    if (!last) // insert a space if not the last word
                        word = word + " ";
                    if (word.length() + width > maxWidth) { // wrap and reset counter
                        accum.append("\n").append(word);
                        width = word.length();
                    } else {
                        accum.append(word);
                        width += word.length();
                    }
                }
            } else { // fits as is, without need to wrap text
                accum.append(text);
                width += text.length();
            }
        }

        @Override
        public String toString() {
            return accum.toString();
        }
    }
}