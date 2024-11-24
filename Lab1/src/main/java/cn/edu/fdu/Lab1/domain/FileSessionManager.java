package cn.edu.fdu.Lab1.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileSessionManager {
    private List<FileSession> sessions = new ArrayList<>();
    private FileSession activeSession = null;
    private Scanner scanner = new Scanner(System.in);

    public void loadFile(String filename) {
        // 检查文件是否已经打开
        for (FileSession session : sessions) {
            if (session.getFilename().equals(filename)) {
                setActiveSession(session);
                System.out.println("文件 " + filename + " 已经打开，设置为当前编辑文件。");
                return;
            }
        }

        // 创建新的文件会话
        FileSession newSession = new FileSession(filename);
        try {
            newSession.load(); // 加载文件内容
            sessions.add(newSession);
            setActiveSession(newSession);
            System.out.println("文件 " + filename + " 加载成功，设置为当前编辑文件。");
        } catch (IOException e) {
            System.err.println("加载文件 " + filename + " 时发生错误: " + e.getMessage());
            e.printStackTrace();
        }catch (Exception e) {  // 修改这里，将 IOException 改为 Exception
            System.err.println("加载文件 " + filename + " 时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveFile(String filename) {
        for (FileSession session : sessions) {
            if (session.getFilename().equals(filename)) {
                try {
                    session.save();
                    System.out.println("文件 " + filename + " 保存成功");
                } catch (IOException e) {
                    System.err.println("保存文件 " + filename + " 时发生错误: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("未打开文件: " + filename);
    }

    public void closeFile() {
        if (activeSession != null) {
            if (activeSession.isModified()) {
                System.out.println("文件已修改，是否保存更改？(y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if ("y".equals(response)) {
                    try {
                        activeSession.save();
                        System.out.println("文件 " + activeSession.getFilename() + " 保存成功");
                    } catch (IOException e) {
                        System.err.println("保存文件 " + activeSession.getFilename() + " 时发生错误: " + e.getMessage());
                    }
                }
            }
            sessions.remove(activeSession);
            System.out.println("文件 " + activeSession.getFilename() + " 已关闭。");
            if (!sessions.isEmpty()) {
                setActiveSession(sessions.get(0));
                System.out.println("设置第一个文件 " + activeSession.getFilename() + " 为当前编辑文件。");
            } else {
                activeSession = null;
                System.out.println("所有文件都已关闭");
            }
        } else {
            System.out.println("当前未打开任何文件");
        }
    }

    public void setActiveSession(FileSession session) {
        if (session != null) {
            activeSession = session;
        } else {
            System.out.println("没有设置当前编辑文件。");
        }
    }

    public FileSession getActiveSession() {
        return activeSession;
    }

    public List<FileSession> getSessionList() {
        return sessions;
    }

    public void listEditors() {
        System.out.println("当前打开的文件:");
        for (FileSession session : sessions) {
            String marker = session == activeSession ? ">" : " ";
            String modifiedMarker = session.isModified() ? "*" : "";
            System.out.println(marker + " " + session.getFilename() + modifiedMarker);
        }
    }


    public void editFile(String filename) {
        FileSession session = getSessionByFilename(filename);
        if (session != null) {
            setActiveSession(session);
            System.out.println("切换到编辑器: " + filename);
        } else {
            System.out.println("未打开文件: " + filename);
        }
    }


    public FileSession getSessionByFilename(String filename) {
        for (FileSession session : sessions) {
            if (session.getFilename().equals(filename)) {
                return session;
            }
        }
        return null;
    }
}