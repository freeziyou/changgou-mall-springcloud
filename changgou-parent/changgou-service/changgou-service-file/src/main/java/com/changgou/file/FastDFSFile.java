package com.changgou.file;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Dylan Guo
 * @date 9/17/2020 15:30
 * @description 封装文件上传信息: 时间, author, type, size, 附加信息, 后缀, 文件内容
 */
public class FastDFSFile implements Serializable {

    /**
     * 文件名字
     */
    private String name;

    /**
     * 文件内容
     */
    private byte[] content;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * 文件MD5摘要值
     */
    private String md5;

    /**
     * 文件创建作者
     */
    private String author;

    @Override
    public String toString() {
        return "FastDFSFile{" +
                "name='" + name + '\'' +
                ", content=" + Arrays.toString(content) +
                ", ext='" + ext + '\'' +
                ", md5='" + md5 + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content, String ext, String md5, String author) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.md5 = md5;
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

}
