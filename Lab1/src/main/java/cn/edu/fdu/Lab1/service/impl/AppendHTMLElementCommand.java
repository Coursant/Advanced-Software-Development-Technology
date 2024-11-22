package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;

public class AppendHTMLElementCommand implements Command {
    private CommandContext context;
    private String tagName;
    private String idValue;
    private String parentElementId;
    private String textContent;

    public AppendHTMLElementCommand(CommandContext context, String tagName, String idValue, String parentElementId, String textContent) {
        this.context = context;
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentElementId = parentElementId;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        HTMLElement parent = context.getIdMap().get(parentElementId);
        if (parent != null && !context.getIdMap().containsKey(idValue)) {
            HTMLElement newElement = new HTMLElement(tagName, idValue, textContent);
            parent.addChild(newElement);
            context.getIdMap().put(idValue, newElement);
            System.out.println("Appended element: " + idValue);
        } else {
            System.out.println(ErrorMessageEnum.PARNET_ELEMENT_NOTFOUND_ERROR.getType());
        }
    }

    @Override
    public void undo() {
        HTMLElement element = context.getIdMap().get(idValue);
        if (element != null) {
            HTMLElement parent = context.findParent(element);
            if (parent != null) {
                parent.removeChild(element);
                context.getIdMap().remove(idValue);
                System.out.println("Undone append: " + idValue);
            }
        }
    }
}

