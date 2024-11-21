import cn.edu.fdu.Lab1.domain.HTMLDocument;

public class TestAllCommands {
    public static void main(String[] args) {
        HTMLDocument document = new HTMLDocument();

        document.insertHTMLElement("div", "div1", "body", "This is a div.");
        document.insertHTMLElement("p", "p1", "div1", "This is a paragraph inside div.");
        document.insertHTMLElement("ul", "ul1", "body", "This is a list.");
        document.insertHTMLElement("li", "li1", "ul1", "Item 1");

        System.out.println("Initial tree:");
        document.printTree();
        System.out.println();

        // 修改元素
        document.editHTMLElementText("p1", "Updated paragraph text.");
        document.editHTMLElementId("li1", "li2");

        System.out.println("Now tree:");
        document.printTree();
        System.out.println();

        // 删除元素
        document.deleteHTMLElement("ul1");

        // 打印最终树结构
        System.out.println("Final tree:");
        document.printTree();
    }
}
