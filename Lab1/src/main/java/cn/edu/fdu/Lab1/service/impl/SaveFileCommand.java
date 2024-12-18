package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.FileSession;
import cn.edu.fdu.Lab1.domain.FileSessionManager;
import cn.edu.fdu.Lab1.service.Command;

import java.io.IOException;
import java.util.List;

public class SaveFileCommand implements Command {
    private final FileSessionManager fileSessionManager;
    private final String filename;
    private List<FileSession> sessions;

    public SaveFileCommand(FileSessionManager fileSessionManager, String filename) {
        this.fileSessionManager = fileSessionManager;
        this.filename = filename;
        this.sessions = fileSessionManager.getSessionList();
    }

    @Override
    public void execute() {
        saveFile(filename);
    }

    @Override
    public void undo() {
        // 可以在这里实现撤销保存的操作，例如恢复到之前的版本
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }

    public void saveFile(String filename) {
        for (FileSession session : sessions) {
            if (session.getFilename().equals(filename)) {
                try {
                    session.save();
                    fileSessionManager.setPreviousSession(session); //修改上一文件记录
                    System.out.println("文件 " + filename + " 保存成功");
                } catch (IOException e) {
                    System.err.println("保存文件 " + filename + " 时发生错误: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("未打开文件: " + filename);
    }
}