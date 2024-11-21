package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.HTMLElementOperationService;
import org.jetbrains.annotations.NotNull;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.spelling.SpellingCheckRule;

import java.io.IOException;
import java.util.*;

public class HTMLElementOperationServiceImpl implements HTMLElementOperationService {
    private Map<String, HTMLElement> idMap;
    private HTMLElement htmlelement;

    public HTMLElementOperationServiceImpl(Map<String, HTMLElement> idMap, HTMLElement htmlelement) {
        this.idMap = idMap;
        this.htmlelement = htmlelement;
    }

    @Override
    public boolean insertHTMLElement(String tagName, String idValue, String insertLocation, String textContent) {
        if (idMap.containsKey(idValue)) {
            System.out.println(ErrorMessageEnum.ID_UNIQUE_ERROR.getType());
            return false;
        }

        HTMLElement parent = idMap.get(insertLocation);
        if (parent == null) {
            System.out.println(ErrorMessageEnum.PARNET_ELEMENT_NOTFOUND_ERROR.getType());
            return false;
        }

        HTMLElement newHTMLElement = new HTMLElement(tagName, idValue, textContent);
        int index = parent.getChildren().indexOf(idMap.get(insertLocation));
        if (index != -1) {
            parent.addChildAt(index, newHTMLElement);
        } else {
            parent.addChild(newHTMLElement);
        }

        idMap.put(idValue, newHTMLElement);
        return true;
    }

    @Override
    public boolean appendHTMLElement(String tagName, String idValue, String parentElementId, String textContent) {
        if (idMap.containsKey(idValue)) {
            System.out.println(ErrorMessageEnum.ID_UNIQUE_ERROR.getType());
            return false;
        }

        HTMLElement parent = idMap.get(parentElementId);
        if (parent == null) {
            System.out.println(ErrorMessageEnum.PARNET_ELEMENT_NOTFOUND_ERROR.getType());
            return false;
        }

        HTMLElement newHTMLElement = new HTMLElement(tagName, idValue, textContent);
        parent.addChild(newHTMLElement);
        idMap.put(idValue, newHTMLElement);
        return true;
    }

    @Override
    public boolean editHTMLElementId(String oldId, String newId) {
        if (idMap.containsKey(newId)) {
            System.out.println(ErrorMessageEnum.ID_UNIQUE_ERROR.getType());
            return false;
        }

        HTMLElement HTMLElement = idMap.get(oldId);
        if (HTMLElement == null) {
            System.out.println(ErrorMessageEnum.ELEMENT_NOTFOUND_ERROR.getType());
            return false;
        }

        HTMLElement.editId(newId);
        idMap.put(newId, HTMLElement);
        idMap.remove(oldId);
        return true;
    }

    @Override
    public boolean editHTMLElementText(String id, String newTextContent) {
        HTMLElement HTMLElement = idMap.get(id);
        if (HTMLElement == null) {
            System.out.println(ErrorMessageEnum.ELEMENT_NOTFOUND_ERROR.getType());
            return false;
        }

        HTMLElement.editText(newTextContent);
        return true;
    }

    @Override
    public boolean deleteHTMLElement(String id) {
        HTMLElement HTMLElement = idMap.get(id);
        if (HTMLElement == null) {
            System.out.println(ErrorMessageEnum.ELEMENT_NOTFOUND_ERROR.getType());
            return false;
        }

        HTMLElement parent = findParent(HTMLElement);
        if (parent != null) {
            parent.removeChild(HTMLElement);
            idMap.remove(id);
            return true;
        }

        System.out.println(ErrorMessageEnum.ROOT_DELETE_ERROR.getType());
        return false;
    }

    private HTMLElement findParent(HTMLElement HTMLElement) {
        for (HTMLElement e : idMap.values()) {
            if (e.getChildren().contains(HTMLElement)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void printTree() {
        System.out.println(htmlelement.print(0));
    }

    @Override
    public void spellCheck() {
        try {
            List<String> misspelledWords = getErrorSpelling();

            if (misspelledWords.isEmpty()) {
                System.out.println("No spelling errors found.");
            } else {
                System.out.println("Spelling errors:");
                for (String error : misspelledWords) {
                    System.out.println(error);
                }
            }
        } catch (Exception e) {
            System.out.println("Error initializing LanguageTool: " + e.getMessage());
        }
    }

    private @NotNull List<String> getErrorSpelling() throws IOException {
        JLanguageTool langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-US"));
        List<String> misspelledWords = new ArrayList<>();

        for (Rule rule : langTool.getAllActiveRules()) {
            if (rule instanceof SpellingCheckRule) {
                List<String> wordsToIgnore = Arrays.asList("specialword", "myotherword");
                ((SpellingCheckRule) rule).addIgnoreTokens(wordsToIgnore);
            }
        }

        for (HTMLElement element : idMap.values()) {
            String text = element.getTextContent();
            if (text != null && !text.isEmpty()) {
                List<RuleMatch> matches = langTool.check(text);
                for (RuleMatch match : matches) {
                    String errorText = "Error: " + match.getMessage() +
                            " at position " + match.getFromPos() +
                            " - " + match.getToPos();
                    misspelledWords.add(errorText);
                }
            }
        }

        return misspelledWords;
    }

    @Override
    public void printIndent(int indent) {
        printIndentHelper(htmlelement, indent, 0);
    }

    private void printIndentHelper(HTMLElement element, int indent, int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(indent).repeat(Math.max(0, depth)));

        sb.append("<").append(element.getTagName()).append(" id=\"").append(element.getId()).append("\">");
        if (element.getTextContent() != null && !element.getTextContent().isEmpty()) {
            sb.append(element.getTextContent());
        }
        sb.append("</").append(element.getTagName()).append(">\n");

        for (HTMLElement child : element.getChildren()) {
            printIndentHelper(child, indent, depth + 1);
        }

        System.out.print(sb);
    }
}
