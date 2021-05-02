/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.config.annotation.TokenToAdminUser;
import ltd.newbee.mall.entity.AdminUserToken;
import ltd.newbee.mall.util.NewBeeMallUtils;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Api(value = "v1", tags = "8-7.后台管理系统文件上传接口")
@RequestMapping("/manage-api/v1")
public class NewBeeAdminUploadAPI {

    private static final Logger logger = LoggerFactory.getLogger(NewBeeAdminUploadAPI.class);

    @Autowired
    private StandardServletMultipartResolver standardServletMultipartResolver;

    /**
     * 图片上传
     */
    @RequestMapping(value = "/upload/file", method = RequestMethod.POST)
    @ApiOperation(value = "单图上传", notes = "file Name \"file\"")
    public Result upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file, @TokenToAdminUser AdminUserToken adminUser) throws URISyntaxException {
        logger.info("adminUser:{}", adminUser.toString());
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        File fileDirectory = new File(Constants.FILE_UPLOAD_DIC);
        //创建文件
        File destFile = new File(Constants.FILE_UPLOAD_DIC + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            Result resultSuccess = ResultGenerator.genSuccessResult();
            resultSuccess.setData(NewBeeMallUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/" + newFileName);
            return resultSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }

    /**
     * 图片上传
     */
    @RequestMapping(value = "/upload/files", method = RequestMethod.POST)
    @ApiOperation(value = "多图上传", notes = "wangEditor图片上传")
    public Result uploadV2(HttpServletRequest httpServletRequest, @TokenToAdminUser AdminUserToken adminUser) throws URISyntaxException {
        logger.info("adminUser:{}", adminUser.toString());
        List<MultipartFile> multipartFiles = new ArrayList<>(8);
        if (standardServletMultipartResolver.isMultipart(httpServletRequest)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) httpServletRequest;
            Iterator<String> iter = multiRequest.getFileNames();
            int total = 0;
            while (iter.hasNext()) {
                if (total > 5) {
                    return ResultGenerator.genFailResult("最多上传5张图片");
                }
                total += 1;
                MultipartFile file = multiRequest.getFile(iter.next());
                multipartFiles.add(file);
            }
        }
        if (CollectionUtils.isEmpty(multipartFiles)) {
            return ResultGenerator.genFailResult("参数异常");
        }
        if (multipartFiles != null && multipartFiles.size() > 5) {
            return ResultGenerator.genFailResult("最多上传5张图片");
        }
        List<String> fileNames = new ArrayList(multipartFiles.size());
        for (int i = 0; i < multipartFiles.size(); i++) {
            String fileName = multipartFiles.get(i).getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //生成文件名称通用方法
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Random r = new Random();
            StringBuilder tempName = new StringBuilder();
            tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
            String newFileName = tempName.toString();
            File fileDirectory = new File(Constants.FILE_UPLOAD_DIC);
            //创建文件
            File destFile = new File(Constants.FILE_UPLOAD_DIC + newFileName);
            try {
                if (!fileDirectory.exists()) {
                    if (!fileDirectory.mkdir()) {
                        throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                    }
                }
                multipartFiles.get(i).transferTo(destFile);
                fileNames.add(NewBeeMallUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/" + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultGenerator.genFailResult("文件上传失败");
            }
        }
        Result resultSuccess = ResultGenerator.genSuccessResult();
        resultSuccess.setData(fileNames);
        return resultSuccess;
    }

}
