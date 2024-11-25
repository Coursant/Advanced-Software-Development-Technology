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
    private Boolean hasSpellError; //用于判断是否存在拼写错误
    private Boolean IsShowId; //是否展示#id

    public HTMLElement(String tagName, String id, String textContent) {
        this.tagName = tagName;
        this.id = (id == null || id.isEmpty()) ? tagName : id;
        this.textContent = textContent;
        this.children = new ArrayList<>();
        this.parentId = null;  // 默认没有父元素
        this.hasSpellError = true;
        this.IsShowId = false;
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

        sb.append(indentStr);
        // 检查拼写错误有错误添加[X]
//        if (hasSpellError) {
//            sb.append("[X] ");
//        }

        sb.append("<").append(tagName)
                .append(" id=\"").append(id).append("\">");
        //展示#id
        if(IsShowId){
            sb.append(" #id");
        }

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

    /**
     * 递归修改isShowId
     * @param isShow
     */
    public void changeShowId(boolean isShow){
        this.IsShowId = isShow;
        if(!children.isEmpty()){
            for (HTMLElement child : children) {
                child.setIsShowId(isShow);
            }
        }
    }
}
