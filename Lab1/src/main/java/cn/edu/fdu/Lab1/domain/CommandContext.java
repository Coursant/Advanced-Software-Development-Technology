package cn.edu.fdu.Lab1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommandContext {
    /**
     * 存储所有 HTML 元素
     */
    private Map<String, HTMLElement> idMap;
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
}

