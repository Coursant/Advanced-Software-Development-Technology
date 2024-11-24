package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.FileSession;
import cn.edu.fdu.Lab1.service.Command;
import cn.edu.fdu.Lab1.strategy.DirectoryDisplayStrategy;
import cn.edu.fdu.Lab1.strategy.impl.DirIndentStrategy;
import cn.edu.fdu.Lab1.strategy.impl.DirTreeStrategy;

import java.util.List;
import java.util.Objects;

public class DirCommand implements Command {
    private String activeFile;//当前在编辑的文件
    private List<FileSession> openFiles; //已经打开的文件列表
    private DirectoryDisplayStrategy directoryDisplayStrategy;//显示策略(根据策略模式的实现有两种策略)

    /**
     * 构造函数传入参数
     * @param openFiles
     * @param arg
     * @param activeFile
     */
    public DirCommand(List<FileSession> openFiles, String arg, String activeFile){
        this.openFiles = openFiles;
        if(Objects.equals(arg, "dir-tree")){
            this.directoryDisplayStrategy = new DirTreeStrategy();
        }else if (Objects.equals(arg, "dir-ident")){
            this.directoryDisplayStrategy = new DirIndentStrategy();
        }
        this.activeFile = activeFile;
    }

    @Override
    public void execute(){
        directoryDisplayStrategy.display(openFiles,activeFile);
    }

    @Override
    public void undo() {
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }

}
