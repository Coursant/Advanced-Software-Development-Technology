package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;

public class InsertHTMLElementCommand implements Command {
    private CommandContext context;
    private String tagName;
    private String idValue;
    private String insertLocation;
    private String textContent;

    public InsertHTMLElementCommand(CommandContext context, String tagName, String idValue, String insertLocation, String textContent) {
        this.context = context;
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocation = insertLocation;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        HTMLElement parent = context.getIdMap().get(insertLocation);
        if (parent != null && !context.getIdMap().containsKey(idValue)) {
            HTMLElement newElement = new HTMLElement(tagName, idValue, textContent);
            parent.addChild(newElement);
            context.getIdMap().put(idValue, newElement);
            System.out.println("Inserted element: " + idValue);
        } else {
            System.out.println("Error: Element with ID " + idValue + " already exists or parent not found.");
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
                System.out.println("Undone insert: " + idValue);
            }
        }
    }
}
