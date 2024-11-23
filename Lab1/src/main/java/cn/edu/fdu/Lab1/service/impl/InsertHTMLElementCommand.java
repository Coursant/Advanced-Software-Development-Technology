package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
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
        // 获取插入位置元素
        HTMLElement locationElement = context.getIdMap().get(insertLocation);

        // 校验：插入位置元素是否存在，以及新元素 ID 是否唯一
        if (locationElement != null) {
            if (!context.getIdMap().containsKey(idValue)) {
                HTMLElement parentElement = context.findParent(locationElement);
                if (parentElement != null) {
                    int insertIndex = parentElement.getChildren().indexOf(locationElement);

                    if (insertIndex != -1) {
                        HTMLElement newElement = new HTMLElement(tagName, idValue, textContent);
                        // 将新元素插入到指定位置之前
                        parentElement.addChildAt(insertIndex, newElement);
                        context.getIdMap().put(idValue, newElement);

                        System.out.println("Inserted element: " + idValue);
                    } else {
                        System.out.println(ErrorMessageEnum.ELEMENT_NOTFOUND_ERROR.getType());
                    }
                } else {
                    System.out.println(ErrorMessageEnum.PARNET_ELEMENT_NOTFOUND_ERROR.getType());
                }
            } else {
                    System.out.println(ErrorMessageEnum.ELEMENT_NOTFOUND_ERROR.getType());
            }
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
                System.out.println("Undone insert: " + idValue);
            }
        }
    }
}
