package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;

public class PrintIndentCommand implements Command {
    private HTMLElement rootElement;
    private int indent;

    public PrintIndentCommand(HTMLElement rootElement, int indent) {
        this.rootElement = rootElement;
        this.indent = indent;
    }

    @Override
    public void execute() {
        printIndent(rootElement, indent);
    }

    @Override
    public void undo() {
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }

    private void printIndent(HTMLElement element, int indent) {
        printIndentHelper(element, indent, 0);
    }

    private void printIndentHelper(HTMLElement element, int indent, int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(indent).repeat(Math.max(0, depth)));

        sb.append("<").append(element.getTagName()).append(" id=\"").append(element.getId()).append("\">");
        if (element.getTextContent() != null && !element.getTextContent().isEmpty()) {
            sb.append(element.getTextContent());
        }
        sb.append("</").append(element.getTagName()).append(">\n");

        // 递归遍历
        for (HTMLElement child : element.getChildren()) {
            printIndentHelper(child, indent, depth + 1);
        }

        System.out.print(sb);
    }
}