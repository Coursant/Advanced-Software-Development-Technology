package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;

public class EditHTMLElementIdCommand implements Command {
    private CommandContext context;
    private String oldId;
    private String newId;

    public EditHTMLElementIdCommand(CommandContext context, String oldId, String newId) {
        this.context = context;
        this.oldId = oldId;
        this.newId = newId;
    }

    @Override
    public void execute() {
        if (context.getIdMap().containsKey(newId)) {
            System.out.println(ErrorMessageEnum.ID_UNIQUE_ERROR.getType());
            return;
        }

        HTMLElement element = context.getIdMap().get(oldId);
        if (element != null) {
            element.editId(newId);
            context.getIdMap().remove(oldId);
            context.getIdMap().put(newId, element);
            System.out.println("Edited ID: " + oldId + " -> " + newId);
        } else {
            System.out.println("Error: Element with ID " + oldId + " not found.");
        }
    }

    @Override
    public void undo() {
        HTMLElement element = context.getIdMap().get(newId);
        if (element != null) {
            element.editId(oldId);
            context.getIdMap().remove(newId);
            context.getIdMap().put(oldId, element);
            System.out.println("Undone edit ID: " + newId + " -> " + oldId);
        }
    }
}

