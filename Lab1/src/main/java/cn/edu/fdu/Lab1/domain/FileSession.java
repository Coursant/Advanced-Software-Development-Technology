package cn.edu.fdu.Lab1.domain;

import cn.edu.fdu.Lab1.util.impl.SpellCheckUtilImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileSession {
    private String filename;
    private boolean modified;
    private CommandContext commandContext;
    private CommandHistory commandHistory;
    private List<FileSession> fileSessionList; //子目录列表
    private SpellCheckUtilImpl spellChecker;


    public FileSession(String filename) {
        this.filename = filename;
        this.modified = false;
        this.commandContext = new CommandContext(); // 初始化新的命令上下文
        this.commandHistory = new CommandHistory();
        this.fileSessionList = new ArrayList<>();
        this.spellChecker = new SpellCheckUtilImpl();
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

    public void markAsUnModified() {
        this.modified = false;
    }

    public void save() throws IOException {
        // 实现文件保存逻辑

        HTMLElement HTML = commandContext.getIdMap().get("html");
        String content = HTML.print(4);
        FileWriter writer = new FileWriter(new File(filename));
        writer.write(content);
        writer.close();
        this.modified = false; // 文件保存后，标记为未修改
    }

    public CommandContext getCommandContext() {
        return commandContext;
    }

    public void setCommandContext(CommandContext context) {
         commandContext = context;
    }

    public CommandHistory getCommandHistory() {
        return commandHistory;
    }

}