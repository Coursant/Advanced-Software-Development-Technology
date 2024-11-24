package cn.edu.fdu.Lab1.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileSession {
    private String filename;
    private boolean modified;
    private CommandContext commandContext;
    private CommandHistory commandHistory;

    public FileSession(String filename) {
        this.filename = filename;
        this.modified = false;
        this.commandContext = new CommandContext(); // 初始化新的命令上下文
        this.commandHistory = new CommandHistory();
    }

    public void load() throws IOException,Exception {
        Path filePath = Paths.get(filename);

        if (!Files.exists(filePath)) {
            // 文件不存在，使用 initializeContext 创建初始上下文并保存到文件
            commandContext = initializeContext();
            save(); // 保存初始上下文到文件
        } else {
            // 文件存在，读取文件内容
            String content = Files.readString(filePath);
            // 解析文件内容并初始化上下文，这里parseContent还没完成
            System.out.println(content);
            System.out.println(commandContext.parser(content));
            // commandContext.parser(content);
        }

        // 文件加载后，标记为未修改
        this.modified = false;
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

        return context;
    }

    public String getFilename() {
        return filename;
    }

    public boolean isModified() {
        return modified;
    }

    public void markAsModified() {
        this.modified = true;
    }

    public void save() throws IOException {
        // 实现文件保存逻辑
        // 假设我们已经有了将CommandContext转换为字符串的方法
        //String content = commandContext.toHtmlString(); //待实现
        String content = commandContext.toString();//暂时代替toHtmlString()
        System.out.println(content);
        FileWriter writer = new FileWriter(new File(filename));
        writer.write(content);
        writer.close();
        this.modified = false; // 文件保存后，标记为未修改
    }

    public CommandContext getCommandContext() {
        return commandContext;
    }

    public CommandHistory getCommandHistory() {
        return commandHistory;
    }
}