package cn.edu.fdu.Lab1.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.FileSession;
import cn.edu.fdu.Lab1.domain.FileSessionManager;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;
import cn.edu.fdu.Lab1.util.impl.SpellCheckUtilImpl;

public class LoadFileCommand implements Command {
    private final FileSessionManager fileSessionManager;
    private final String filename;
    private SpellCheckUtilImpl spellChecker;
    private FileSession newSession;

    public LoadFileCommand(FileSessionManager fileSessionManager, String filename) {
        this.fileSessionManager = fileSessionManager;
        this.filename = filename;
        //this.sessions = fileSessionManager.getSessionList();
        this.spellChecker = new SpellCheckUtilImpl();
        this.newSession = new FileSession(filename);
    }

    @Override
    public void execute() {
        loadFile(filename);
    }

    @Override
    public void undo() {
        // 可以在这里实现撤销加载文件的操作，例如关闭文件
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }

    public void loadFile(String filename) {
        // 检查文件是否已经打开
        for (FileSession session : fileSessionManager.getSessionList()) {
            if (session.getFilename().equals(filename)) {
                fileSessionManager.setActiveSession(session);
                System.out.println("文件 " + filename + " 已经打开，设置为当前编辑文件。");
                return;
            }
        }

        // 创建新的文件会话
        //FileSession newSession = new FileSession(filename);

        try {
            load(); // 加载文件内容
            fileSessionManager.getSessionList().add(newSession);
            fileSessionManager.setActiveSession(newSession);
            System.out.println("文件 " + filename + " 加载成功，设置为当前编辑文件。");
        } catch (IOException e) {
            System.err.println("加载文件 " + filename + " 时发生错误: " + e.getMessage());
            e.printStackTrace();
        }catch (Exception e) {  // 修改这里，将 IOException 改为 Exception
            System.err.println("加载文件 " + filename + " 时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void load() throws IOException,Exception {
        Path filePath = Paths.get(filename);

        if (!Files.exists(filePath)) {
            // 文件不存在，使用 initializeContext 创建初始上下文并保存到文件
            newSession.setCommandContext(initializeContext());
            newSession.save(); // 保存初始上下文到文件
            System.out.println("未找到该文件，将自动创建："+filename);
        } else {
            // 文件存在，读取文件内容
            String content = Files.readString(filePath);
            
            newSession.getCommandContext().parser(content);
            HTMLElement HTML = newSession.getCommandContext().getIdMap().get("html");
            spellChecker.checkSpellErrors(HTML);
        }

        // 文件加载后，标记为未修改
        newSession.markAsUnModified();
    }

    private CommandContext initializeContext() {
        CommandContext context = new CommandContext();
        HTMLElement htmlElement = new HTMLElement("html", "html", null);
        HTMLElement head = new HTMLElement("head", "head", null);
        HTMLElement body = new HTMLElement("body", "body", null);

        htmlElement.addChild(head);
        htmlElement.addChild(body);

        context.setIdMap(new HashMap<>());
        context.getIdMap().put("html", htmlElement);
        context.getIdMap().put("head", head);
        context.getIdMap().put("body", body);
        spellChecker.checkSpellErrors(htmlElement);
        return context;
    }
}

