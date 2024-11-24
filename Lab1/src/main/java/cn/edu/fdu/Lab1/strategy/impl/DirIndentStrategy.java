package cn.edu.fdu.Lab1.strategy.impl;

import cn.edu.fdu.Lab1.domain.FileSession;
import cn.edu.fdu.Lab1.strategy.DirectoryDisplayStrategy;

import java.util.List;

public class DirIndentStrategy implements DirectoryDisplayStrategy {

    @Override
    public void display(List<FileSession> fileNodes, String activeFile){
        for(FileSession node: fileNodes){
            displayFile(node,activeFile,0);
        }
    }

    /**
     * 缩进显示
     * @param file
     * @param activeFile
     * @param indent
     */
    private void displayFile(FileSession file ,String activeFile,int indent){
        String indentStr = " ".repeat(indent * 4);
        System.out.println(indentStr + "- " + file.getFilename() + (file.getFilename().equals(activeFile) ? " *" : ""));

        //递归显示子文件
        for (FileSession node : file.getFileSessionList()) {
            displayFile(node, activeFile, indent + 1);
        }
    }
}
