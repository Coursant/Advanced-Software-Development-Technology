package cn.edu.fdu.Lab1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

@Getter
@Setter
@AllArgsConstructor
public class FileSessionManager {

    private List<FileSession> sessions ; //已经打开的文件列表
    private FileSession activeSession; //当前活动文件
    private FileSession previousSession; //当前活动文件的原始状态
//    private Scanner scanner;

    public FileSessionManager(){
        this.sessions = new ArrayList<>();
        this.activeSession = new FileSession();
//        this.scanner = new Scanner(System.in);
    }


    public void setActiveSession(FileSession session) {
        if (session != null) {
            // 保存文件的原始状态
            previousSession = session;
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


    public FileSession getSessionByFilename(String filename) {
        for (FileSession session : sessions) {
            if (session.getFilename().equals(filename)) {
                return session;
            }
        }
        return null;
    }
}