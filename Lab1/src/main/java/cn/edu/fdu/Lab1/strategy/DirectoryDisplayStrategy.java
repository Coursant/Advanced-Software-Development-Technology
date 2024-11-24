package cn.edu.fdu.Lab1.strategy;

import cn.edu.fdu.Lab1.domain.FileSession;

import java.util.List;

/**
 * 策略模式的接口
 */
public interface DirectoryDisplayStrategy {

    void display(List<FileSession> fileNodes, String activeFile); //接口为：文件列表，当前活动的文件名
}
