package cn.edu.fdu.Lab1.strategy.impl;

import cn.edu.fdu.Lab1.domain.FileSession;
import cn.edu.fdu.Lab1.strategy.DirectoryDisplayStrategy;

import java.util.List;

public class DirTreeStrategy implements DirectoryDisplayStrategy {
    @Override
    public void display(List<FileSession> fileNodes , String activeFile){
        for (FileSession file : fileNodes) {
            displayFile(file, activeFile, 0,false);
        }
    }

    /**
     * 输出函数，indent用于控制缩进距离,isLast表示是否是文件表示的最底层，递归显示树状态结构
     * @param file
     * @param activeFile
     * @param indent
     * @param isLast
     */
    private void displayFile (FileSession file, String activeFile, int indent,boolean isLast){
        StringBuilder sb = new StringBuilder();
        if (indent > 0) {
            sb.append("│   ".repeat(indent - 1));
            sb.append(isLast ? "└── " : "├── ");
        }

        sb.append(file.getFilename());
        //根据文件活动情况添加 *
        if (file.getFilename().equals(activeFile)) {
            sb.append(" *");
        }
        System.out.println(sb);

        //递归调用文件
        List<FileSession> nodeList = file.getFileSessionList();
        for (int i = 0; i < nodeList.size(); i++) {
            displayFile(nodeList.get(i), activeFile, indent + 1, i == nodeList.size() - 1); // （i == nodeList.size() - 1)判断是否是当前层的最后一个节点
        }
    }
}
