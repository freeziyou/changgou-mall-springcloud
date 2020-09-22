package com.changgou.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSUtil;
import entity.Result;
import entity.StatusCode;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Dylan Guo
 * @date 9/17/2020 16:55
 * @description TODO
 */
@RestController
@RequestMapping("/upload")
@CrossOrigin
public class FileUploadController {

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 消息结果
     * @throws Exception 异常
     */
    @PostMapping
    public Result upload(@RequestParam("file") MultipartFile file) throws Exception {
        // 封装文件信息
        FastDFSFile fastDFSFile = new FastDFSFile(
                // 文件名字
                file.getOriginalFilename(),
                // 文件字节数组
                file.getBytes(),
                // 获取文件扩展名
                StringUtils.getFilenameExtension(file.getOriginalFilename())
        );

        // 调用 FastDFSUtil 工具类将文件传入到 FastDFS 中
        String[] uploads = FastDFSUtil.upload(fastDFSFile);

        // 拼接访问地址 ex: url = http://192.168.211.132:8080/group1/M00/01/02/abc.jpg
        String url = FastDFSUtil.getTrackerInfo() + uploads[0] + "/" + uploads[1];
        return new Result(true, StatusCode.OK, "上传成功!", url);
    }
}
