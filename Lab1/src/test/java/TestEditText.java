import cn.edu.fdu.Lab1.domain.HTMLDocument;

public class TestEditText {
    public static void main(String[] args) {
        HTMLDocument document = new HTMLDocument();
        document.testExample(document);

        // 测试 1: 修改 div1 的文本内容，结果成功
        boolean success1 = document.editHTMLElementText("div1", "This is the new text for div.");
        document.printTree();
        System.out.println(success1 ? "Edit Text Success!" : "Edit Text Failed!");
        System.out.println();

        // 测试 2: 清空 p1 的文本内容，结果成功
        boolean success2 = document.editHTMLElementText("p1", "");
        document.printTree();
        System.out.println(success2 ? "Edit Text Success!" : "Edit Text Failed!");
        System.out.println();
    }
}
