package cn.edu.fdu.Lab1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommandContext {
    /**
     * 存储所有 HTML 元素
     */
    private Map<String, HTMLElement> idMap = new HashMap<>();
    /**
     * 被删除的元素
     */
    private HTMLElement deletedElement;

    /**
     * 查找某个元素的父元素
     *
     * @param element
     * @return
     */
    public HTMLElement findParent(HTMLElement element) {
        for (HTMLElement e : idMap.values()) {
            if (e.getChildren().contains(element)) {
                return e;
            }
        }
        return null;
    }


    public boolean parser(String htmlContent) throws Exception {

        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            throw new IllegalArgumentException("HTML content cannot be null or empty");
        }

        Stack<HTMLElement> elementStack = new Stack<>();
        elementStack.push(new HTMLElement("html", "html", null)); // 假定 root 为 HTML

        // 正则匹配 HTML 标签和内容
//        String regex = "<(\\w+)(?:\\s+([^>]*))?>|</(\\w+)>|([^<]+)";

        String regex = "<(\\w+)(?:\\s+([^>]*))?>|</(\\w+)>|([^<>]+)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(htmlContent);
        idMap.clear();
        Map<String, Integer> tagCounter = new HashMap<>();

        while (matcher.find()) {
//            System.out.println("Group 1 (Start Tag): " + matcher.group(1));
//            System.out.println("Group 2 (Attributes): " + matcher.group(2));
//            System.out.println("Group 3 (End Tag): " + matcher.group(3));
//            System.out.println("Group 4 (Text): " + matcher.group(4)+"\n");
            if (matcher.group(1) != null) {

                String tagName = matcher.group(1);
                String idValue = matcher.group(2) != null ? matcher.group(2).trim() : "";
                // 如果没有 id 属性，生成唯一 id
                if ( idValue.isEmpty()) {
                    tagCounter.put(tagName, tagCounter.getOrDefault(tagName, 0) + 1);
                    idValue = tagName + tagCounter.get(tagName); // 生成唯一 id
                }
                // 创建新元素
                HTMLElement newElement = new HTMLElement(tagName, idValue, null);

                // 将新元素添加为栈顶元素的子节点
                HTMLElement parent = elementStack.peek();
                if(!newElement.getTagName().equals("html")){
                    parent.addChild(newElement);
                    // 将新元素插入 HTMLDocument 的 idMap
                    // 入栈
                    elementStack.push(newElement);
                }else{
                    idMap.put(idValue, newElement);

                }


            } else if (matcher.group(3) != null) {
                if(elementStack.peek().getTagName().equals("html")){
                    idMap.put("html1",elementStack.peek());

                }
                elementStack.pop();

            } else if (matcher.group(4) != null) { // 匹配文本内容
                String textContent = matcher.group(4).trim();
                if (!textContent.isEmpty()) {
                    HTMLElement currentElement = elementStack.peek();
                    currentElement.setTextContent(textContent);

                }
            }
        }
//        htmlelement = idMap.get("html1");
        return true;

    }
}

