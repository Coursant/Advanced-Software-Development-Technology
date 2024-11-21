import cn.edu.fdu.Lab1.domain.HTMLDocument;

public class SpellCheckTest {

    public static void main(String[] args) {
        // 创建一个 HTML 文档
        HTMLDocument document = new HTMLDocument();

        // 插入一个新的 div 元素
        document.insertHTMLElement("div", "div1", "body", "This is a div.");

        // 插入一个新的 p 元素，并在其中添加拼写错误的文本
        document.insertHTMLElement("p", "p1", "div1", "This is a paraagraph with a typo.");

        // 插入另一个元素，包含多个拼写错误
        document.insertHTMLElement("p", "p2", "div1", "Here is anothr paragraph with mistakes.");

        // 插入带有正确拼写的文本元素
        document.insertHTMLElement("p", "p3", "div1", "This is a correct sentence.");

        // 修改其中某个元素的文本
        document.editHTMLElementText("div1", "This is a div with a spelling mistake in the text.");

        // 执行拼写检查
        System.out.println("Running spell-check on the HTML document...\n");
        document.spellCheck();
    }
}