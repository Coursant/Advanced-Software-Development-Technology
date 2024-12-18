package cn.edu.fdu.Lab1.service.impl;

import java.io.IOException;
import java.util.Scanner;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.FileSessionManager;
import cn.edu.fdu.Lab1.service.Command;

public class CloseFileCommand implements Command {
    private final FileSessionManager fileSessionManager;
    private Scanner scanner;


    public CloseFileCommand(FileSessionManager fileSessionManager) {
        this.fileSessionManager = fileSessionManager;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        closeFile();
    }

    @Override
    public void undo() {
        // 可以在这里实现撤销关闭文件的操作，例如重新打开文件
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }

    public void closeFile() {
        if (fileSessionManager.getActiveSession() != null) {
            if (fileSessionManager.getActiveSession().isModified()) {
                System.out.println("文件已修改，是否保存更改？(y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if ("y".equals(response)) {
                    try {
                        fileSessionManager.getActiveSession().save();
                        System.out.println("文件 " + fileSessionManager.getActiveSession().getFilename() + " 保存成功");
                    } catch (IOException e) {
                        System.err.println("保存文件 " + fileSessionManager.getActiveSession().getFilename() + " 时发生错误: " + e.getMessage());
                    }
                }
                else if("n".equals(response)){
                    fileSessionManager.setActiveSession(fileSessionManager.getPreviousSession());
                }
            }
            fileSessionManager.getSessionList().remove(fileSessionManager.getActiveSession());
            System.out.println("文件 " + fileSessionManager.getActiveSession().getFilename() + " 已关闭。");
            if (!fileSessionManager.getSessionList().isEmpty()) {
                fileSessionManager.setActiveSession(fileSessionManager.getSessionList().get(0));
                System.out.println("设置第一个文件 " + fileSessionManager.getActiveSession().getFilename() + " 为当前编辑文件。");
            } else {
                fileSessionManager.setActiveSession(null);
                System.out.println("所有文件都已关闭");
            }
        } else {
            System.out.println("当前未打开任何文件");
        }
    }

}