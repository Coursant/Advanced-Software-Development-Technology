package cn.edu.fdu.Lab1.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HTMLElement {
    private String tagName;
    private String id;
    private String textContent;
    private List<HTMLElement> children;
    private String parentId;  // 存储父元素的 ID

    public HTMLElement(String tagName, String id, String textContent) {
        this.tagName = tagName;
        this.id = (id == null || id.isEmpty()) ? tagName : id;
        this.textContent = textContent;
        this.children = new ArrayList<>();
        this.parentId = null;  // 默认没有父元素
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    // 获取父元素 ID
    public String getParentId() {
        return parentId;
    }

    public void addChild(HTMLElement child) {
        children.add(child);
        child.setParentId(this.id);  // 设置父元素的 ID
    }


    public void addChildAt(int index, HTMLElement element) {
        if (index >= 0 && index <= children.size()) {
            children.add(index, element);
            element.setParentId(this.id);
        } else {
            this.addChild(element);
        }
    }

    public void removeChild(HTMLElement child) {
        children.remove(child);
    }

    public void editText(String newTextContent) {
        this.textContent = newTextContent;
    }

    public void editId(String newId) {
        this.id = newId;
    }

    public String print(int indent) {
        StringBuilder sb = new StringBuilder();
        String indentStr = " ".repeat(indent);

        sb.append(indentStr).append("<").append(tagName)
                .append(" id=\"").append(id).append("\">");

        if (textContent != null && !textContent.isEmpty()) {
            sb.append("\n").append(indentStr).append("  ").append(textContent);
        }

        if (!children.isEmpty()) {
            sb.append("\n");
            for (HTMLElement child : children) {
                sb.append(child.print(indent + 2)).append("\n");
            }
        }

        sb.append("\n").append(indentStr).append("</").append(tagName).append(">");
        return sb.toString();
    }
}
