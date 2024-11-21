import cn.edu.fdu.Lab1.domain.HTMLDocument;

public class TestInsert {
    public static void main(String[] args) {
        HTMLDocument document = new HTMLDocument();

        // 测试insert
        // 测试 1: 在 body 中插入 <div> 元素，结果成功
        boolean success1 = document.insertHTMLElement("div", "div1", "body", "This is a div.");
        document.printTree();
        System.out.println(success1 ? "Insert Success!" : "Insert Failed!");
        System.out.println();

        // 测试 2: 在 div1 内插入 <p> 元素，结果成功
        boolean success2 = document.insertHTMLElement("p", "p1", "div1", "This is a paragraph inside div.");
        document.printTree();
        System.out.println(success2 ? "Insert Success!" : "Insert Failed!");
        System.out.println();

        // 测试 3: 插入重复的 <p> 元素，结果失败
        boolean success3 = document.insertHTMLElement("p", "p1", "body", "Duplicate ID test.");
        document.printTree();
        System.out.println(success3 ? "Insert Success!" : "Insert Failed!");
        System.out.println();
    }
}

