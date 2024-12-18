package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.FileSession;
import cn.edu.fdu.Lab1.domain.FileSessionManager;
import cn.edu.fdu.Lab1.service.Command;

public class ListEditorsCommand implements Command {
    private final FileSessionManager fileSessionManager;

    public ListEditorsCommand(FileSessionManager fileSessionManager) {
        this.fileSessionManager = fileSessionManager;
    }

    @Override
    public void execute() {
        listEditors();
    }

    @Override
    public void undo() {
        // 列出编辑器的操作通常不需要撤销
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }

    public void listEditors() {
        System.out.println("当前打开的文件:");
        for (FileSession session : fileSessionManager.getSessionList()) {
            String marker = session == fileSessionManager.getActiveSession() ? ">" : " ";
            String modifiedMarker = session.isModified() ? "*" : "";
            System.out.println(marker + " " + session.getFilename() + modifiedMarker);
        }
    }
}