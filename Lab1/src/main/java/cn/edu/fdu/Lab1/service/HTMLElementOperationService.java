package cn.edu.fdu.Lab1.service;

public interface HTMLElementOperationService {
    boolean insertHTMLElement(String tagName, String idValue, String insertLocation, String textContent);
    boolean appendHTMLElement(String tagName, String idValue, String parentElementId, String textContent);
    boolean editHTMLElementId(String oldId, String newId);
    boolean editHTMLElementText(String id, String newTextContent);
    boolean deleteHTMLElement(String id);
    void printTree();
    void spellCheck();
    void printIndent(int indent);
}
