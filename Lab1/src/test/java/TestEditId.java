import cn.edu.fdu.Lab1.domain.HTMLDocument;

public class TestEditId {
    public static void main(String[] args) {
        HTMLDocument document = new HTMLDocument();
        document.testExample(document);

        // 测试 1: 修改 div1 的 ID 为 div2,结果成功
        boolean success1 = document.editHTMLElementId("div1", "div2");
        document.printTree();
        System.out.println(success1 ? "Edit ID Success!" : "Edit ID Failed!");
        System.out.println();

        // 测试 2: 修改为重复的 ID,结果错误
        boolean success2 = document.editHTMLElementId("p1", "div2");
        document.printTree();
        System.out.println(success2 ? "Edit ID Success!" : "Edit ID Failed!");
        System.out.println();
    }
}
