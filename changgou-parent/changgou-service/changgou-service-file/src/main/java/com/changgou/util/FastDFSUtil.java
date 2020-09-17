package com.changgou.util;

import com.changgou.file.FastDFSFile;
import entity.Result;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Dylan Guo
 * @date 9/17/2020 15:34
 * @description 实现FastDFS文件管理
 * 文件上传, 下载, 删除, 文件信息获取, Storage/tracker 信息获取
 */
public class FastDFSUtil {

    static {
        try {
            // 查找 classpath 下的文件路径
            String filename = new ClassPathResource("fdfs_client.conf").getPath();

            //加载 Tracker 链接信息
            ClientGlobal.init(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 StorageClient
     *
     * @return StorageClient
     * @throws IOException 异常
     */
    private static StorageClient getStorageClient() throws Exception {
        // 获取 trackerServer 对象
        TrackerServer trackerServer = getTrackerServer();

        // 通过 TrackerServer 的链接信息可以获取 Storage 的链接信息, 创建 StorageClient 对象存储 Storage 的链接信息
        return new StorageClient(trackerServer, null);
    }

    /**
     * 获取 TrackerServer
     *
     * @return TrackerServer
     * @throws IOException 异常
     */
    private static TrackerServer getTrackerServer() throws Exception {
        // 创建一个 tracker 访问的客户端对象 trackerClient
        TrackerClient trackerClient = new TrackerClient();

        // 通过 trackerClient 访问 TrackerServer 服务, 获取连接信息
        return trackerClient.getConnection();
    }

    /**
     * 文件上传
     *
     * @param fastDFSFile 上传的文件信息封装
     * @return 上传文件存储在 Storage 的信息
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        // 附加参数
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("author", fastDFSFile.getAuthor());

        // 获取 StorageClient 对象
        StorageClient storageClient = getStorageClient();

        // 通过 StorageClient 访问 Storage, 实现文件上传, 并且获取文件上传后的存储信息
        // 1.上传文件的直接数组 2.文件的扩展名 3.附加参数
        // uploads[]:
        //      uploads[0]: 文件上传所存储的 Storage 的组名字 ex: group1
        //      uploads[1]: 文件存储到 Storage 的文件名字 ex: M00/01/02/abc.jpg
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), metaList);
        return uploads;
    }

    /**
     * 获取文件信息
     *
     * @param groupName      文件组名 group1
     * @param remoteFileName 文件的存储路径 M00/01/02/abc.jpg
     * @return 文件信息
     */
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        // 获取 StorageClient 对象
        StorageClient storageClient = getStorageClient();

        // 获取文件信息
        return storageClient.get_file_info(groupName, remoteFileName);
    }

    /**
     * 文件下载
     *
     * @param groupName      文件组名 group1
     * @param remoteFileName 文件的存储路径 M00/01/02/abc.jpg
     * @return 输入字节流
     */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception {
        // 获取 StorageClient 对象
        StorageClient storageClient = getStorageClient();

        // 文件下载
        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(buffer);
    }

    /**
     * 文件删除
     *
     * @param groupName      文件组名 group1
     * @param remoteFileName 文件的存储路径 M00/01/02/abc.jpg
     */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        // 获取 StorageClient 对象
        StorageClient storageClient = getStorageClient();

        // 文件删除
        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 获取 Storage 信息
     *
     * @return Storage
     * @throws Exception 异常
     */
    public static StorageServer getStorages() throws Exception {
        // 创建一个 tracker 访问的客户端对象 trackerClient
        TrackerClient trackerClient = new TrackerClient();

        // 通过 trackerClient 访问 TrackerServer 服务, 获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();

        // 获取 Storage 信息
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 获取 Storage 的 IP 和端口信息
     *
     * @return Storage 组信息
     * @throws Exception 异常
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception {
        // 创建一个 tracker 访问的客户端对象 trackerClient
        TrackerClient trackerClient = new TrackerClient();

        // 通过 trackerClient 访问 TrackerServer 服务, 获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();

        // 获取 Storage 的 IP 和端口信息
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    /**
     * 获取 Tracker 信息
     *
     * @return Tracker 信息: http://192.168.211.132:8080
     * @throws Exception 异常
     */
    public static String getTrackerInfo() throws Exception {
        // 获取 trackerServer
        TrackerServer trackerServer = getTrackerServer();

        // Tracker 的 IP, Http 端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int trackerHttpPort = ClientGlobal.getG_tracker_http_port();
        String url = "http://" + ip + ":" + trackerHttpPort;
        return url + "/";
    }


    public static void main(String[] args) throws Exception {
//        FileInfo fileInfo = getFile("group1", "M00/00/00/wKjThF9jWliAJtdxAASoXdziGaU130.jpg");
//        System.out.println(fileInfo.getSourceIpAddr());
//        System.out.println(fileInfo.getFileSize());

//        deleteFile("group1", "M00/00/00/wKjThF9jSsCAKt4oAADaM-iueBo736.jpg");

//        StorageServer storages = getStorages();
//        System.out.println(storages.getStorePathIndex());

//        ServerInfo[] groups = getServerInfo("group1", "M00/00/00/wKjThF9jX92AY-kPAAFd4XVtbCo293.jpg");
//        for (ServerInfo group : groups) {
//            System.out.println(group.getIpAddr());
//            System.out.println(group.getPort());
//        }

        System.out.println(getTrackerInfo());
    }
}
