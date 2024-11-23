package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;

public class EditHTMLElementTextCommand implements Command {
    private CommandContext context;
    private String idValue;
    private String oldTextContent;
    private String newTextContent;

    public EditHTMLElementTextCommand(CommandContext context, String idValue, String newTextContent) {
        this.context = context;
        this.idValue = idValue;
        this.oldTextContent = context.getIdMap().get(idValue).getTextContent();
        this.newTextContent = newTextContent;
    }

    @Override
    public void execute() {
        HTMLElement element = context.getIdMap().get(idValue);
        if (element != null) {
            element.editText(newTextContent);
            System.out.println("Edited text content for element: " + idValue);
        } else {
            System.out.println(ErrorMessageEnum.ELEMENT_NOTFOUND_ERROR.getType());
        }
    }

    @Override
    public void undo() {
        HTMLElement element = context.getIdMap().get(idValue);
        if (element != null) {
            element.editText(oldTextContent);
            System.out.println("Undone text edit for element: " + idValue);
        }
    }
}

