package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;

public class DeleteHTMLElementCommand implements Command {
    private CommandContext context;
    private String idValue;
    private HTMLElement deletedElement;
    private int originalIndex;

    public DeleteHTMLElementCommand(CommandContext context, String idValue) {
        this.context = context;
        this.idValue = idValue;
    }

    @Override
    public void execute() {
        HTMLElement element = context.getIdMap().get(idValue);
        if (element != null) {
            HTMLElement parent = context.findParent(element);
            if (parent != null) {
                originalIndex = parent.getChildren().indexOf(element);
                parent.removeChild(element);
                context.getIdMap().remove(idValue);
                context.setDeletedElement(element);
                System.out.println("Deleted element: " + idValue);
            }
        } else {
            System.out.println(ErrorMessageEnum.ELEMENT_NOTFOUND_ERROR.getType());
        }
    }

    @Override
    public void undo() {
        if (context.getDeletedElement() != null) {
            HTMLElement element = context.getDeletedElement();
            HTMLElement parent = context.findParentId(element);
            if (parent != null) {
                parent.addChildAt(originalIndex, element);
                context.getIdMap().put(element.getId(), element);
                System.out.println("Undone delete: " + element.getId());
            }
        }
    }
}
