package cn.edu.fdu.Lab1.domain;

import cn.edu.fdu.Lab1.service.HTMLElementOperationService;
import cn.edu.fdu.Lab1.service.impl.HTMLElementOperationServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class HTMLDocument {
    /**
     * 具体实现
     */
    private final HTMLElementOperationService operations;

    public HTMLDocument() {
        HTMLElement htmlelement = new HTMLElement("html", "html", null);
        HTMLElement head = new HTMLElement("head", "head", null);
        HTMLElement body = new HTMLElement("body", "body", null);
        htmlelement.addChild(head);
        htmlelement.addChild(body);
        /**
         * 存储元素 id，确保唯一性
         */
        Map<String, HTMLElement> idMap = new HashMap<>();
        idMap.put("html", htmlelement);
        idMap.put("head", head);
        idMap.put("body", body);

        operations = new HTMLElementOperationServiceImpl(idMap, htmlelement);
    }

    /**
     * 添加新元素（插入到指定位置）
     * @param tagName
     * @param idValue
     * @param insertLocation
     * @param textContent
     * @return
     */
    public boolean insertHTMLElement(String tagName, String idValue, String insertLocation, String textContent) {
        return operations.insertHTMLElement(tagName, idValue, insertLocation, textContent);
    }


    /**
     * 在某元素内插入新元素
     * @param tagName
     * @param idValue
     * @param parentElementId
     * @param textContent
     * @return
     */
    public boolean appendHTMLElement(String tagName, String idValue, String parentElementId, String textContent) {
        return operations.appendHTMLElement(tagName, idValue, parentElementId, textContent);
    }


    /**
     * 修改元素 ID
     * @param oldId
     * @param newId
     * @return
     */
    public boolean editHTMLElementId(String oldId, String newId) {
        return operations.editHTMLElementId(oldId, newId);
    }

    /**
     * 修改元素文本内容
     * @param id
     * @param newTextContent
     * @return
     */
    public boolean editHTMLElementText(String id, String newTextContent) {
        return operations.editHTMLElementText(id, newTextContent);
    }

    /**
     * 删除元素
     * @param id
     * @return
     */
    public boolean deleteHTMLElement(String id) {
        return operations.deleteHTMLElement(id);
    }

    /**
     * 打印 HTML 树结构
     */
    public void printTree() {
        operations.printTree();
    }

    /**
     * 检查拼写错误,使用JLanguageTool
     * ref:https://dev.languagetool.org/java-api
     */
    public void spellCheck() {
        operations.spellCheck();
    }

    /**
     * 按缩进格式显示
     * @param indent
     */
    public void printIndent(int indent) {
        operations.printIndent(indent);
    }

    /**
     * 测试用例
     * @param document
     * @return
     */
    public HTMLDocument testExample(HTMLDocument document){
        document.insertHTMLElement("div", "div1", "body", "This is a div.");
        document.insertHTMLElement("p", "p1", "div1", "This is a paragraph inside div.");
        document.printTree();
        System.out.println();
        return document;
    }
}
