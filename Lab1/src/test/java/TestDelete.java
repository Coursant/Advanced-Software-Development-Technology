import cn.edu.fdu.Lab1.domain.HTMLDocument;

public class TestDelete {
    public static void main(String[] args) {
        HTMLDocument document = new HTMLDocument();
        document.testExample(document);

        // 测试 1: 删除 div1 元素，结果成功
        boolean success1 = document.deleteHTMLElement("div1");
        document.printTree();
        System.out.println(success1 ? "Delete Success!" : "Delete Failed!");
        System.out.println();

        // 测试 2: 删除不存在的元素，结果失败
        boolean success2 = document.deleteHTMLElement("nonexistent");
        document.printTree();
        System.out.println(success2 ? "Delete Success!" : "Delete Failed!");
        System.out.println();
    }
}
