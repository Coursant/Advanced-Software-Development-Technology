package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.FileSession;
import cn.edu.fdu.Lab1.domain.FileSessionManager;
import cn.edu.fdu.Lab1.service.Command;

public class EditFileCommand implements Command {
    private final FileSessionManager fileSessionManager;
    private final String filename;

    public EditFileCommand(FileSessionManager fileSessionManager, String filename) {
        this.fileSessionManager = fileSessionManager;
        this.filename = filename;
    }

    @Override
    public void execute() {
        editFile(filename);
    }

    @Override
    public void undo() {
        // 可以在这里实现撤销编辑文件的操作，例如切换回之前的文件
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }

    public void editFile(String filename) {
        FileSession session = fileSessionManager.getSessionByFilename(filename);
        if (session != null) {
            fileSessionManager.setActiveSession(session);
            System.out.println("切换到编辑器: " + filename);
        } else {
            System.out.println("未打开文件: " + filename);
        }
    }
}