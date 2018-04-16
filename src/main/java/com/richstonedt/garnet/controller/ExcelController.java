package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.ExcelUtils;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.view.FileView;
import com.richstonedt.garnet.model.view.ResourceExcelView;
import com.richstonedt.garnet.model.view.ResourceView;
import com.richstonedt.garnet.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(value = "[Torino Source]上传模块",tags = "[Torino Source]上传模块")
@RestController
@RequestMapping(value = "/api/v1.0")
public class ExcelController {

    @Autowired
    private ResourceService resourceService;

    private static final Logger LOG = LoggerFactory
            .getLogger(ExcelController.class);

    @ApiOperation(value = "上传Resource Excel", notes = "上传Resource Excel")
    @RequestMapping(value = "/upload/resourceexcel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = {RequestMethod.POST})
    @CrossOrigin
    public ResponseEntity<?> uploadResourceExcel(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws FileNotFoundException {
        FileInputStream fileInputStream = null;
        try {
            ResponseEntity<?> head = upload(request, response, map, "excel");
            FileView fileVo = (FileView) head.getBody();
            String[] split = fileVo.getFilePath().split("\\.");
            String excelPath = GarnetContants.SAVE_PATH  + "/" + fileVo.getFilePath();
            fileInputStream = new FileInputStream(excelPath);
            ExcelUtils<ResourceExcelView> excelUtils = new ExcelUtils<>(ResourceExcelView.class);
            List<ResourceExcelView> resources = new ArrayList<>();

            if ("xls".equals(split[split.length - 1])) {
                resources = excelUtils.importExcel("", fileInputStream, ExcelUtils.ExcelVersion.EXCEL_VERSION_03);
            } else if ("xlsx".equals(split[split.length - 1])) {
                resources = excelUtils.importExcel("", fileInputStream, ExcelUtils.ExcelVersion.EXCEL_VERSION_07);
            } else {
                return null;
            }

            resourceService.saveResourcesExcel(resources);
            File file = new File(excelPath);
            if (file.exists()) {
                file.delete();
            }
            return new ResponseEntity<>("Import Excel Success", HttpStatus.OK);
        } catch (Exception e) {
//            System.out.println("excel error == " + e.toString());
            String error = "Import Excel Fail";
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(e.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        return null;
    }

    private ResponseEntity<?> upload(HttpServletRequest request, HttpServletResponse response, ModelMap map, String type) {
        MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
        MultipartFile file = mhs.getFile("file");
        ServletContext application = request.getSession().getServletContext();
//        System.out.println("pic url: " + application.getRealPath("/"));
        LOG.info("pic url: " + application.getRealPath("/"));
        String savePath = GarnetContants.SAVE_PATH + "/";
        String filePath = "images/" + type + "/";
        String newFileName = "";
        try {
            if (file != null && !file.isEmpty()) {
                //检查是否有文件夹
                String folderName = savePath + "images/" + type;
                File folder = new File(folderName);
                //没有则创建
                if (!folder.exists() && !folder.isDirectory()) {
                    LOG.info("创建文件夹：" + folderName);
                    folder.mkdir();
                }

                SimpleDateFormat bartDateFormat = new SimpleDateFormat
                        ("yyyyMMddHHmmss");
                newFileName = String.valueOf(bartDateFormat.format(new Date()));
//                filePath = filePath + newFileName + "_" + file.getOriginalFilename();
                filePath = filePath + "upload_resource";
                LOG.info("上传文件： " + filePath);
                FileUtils.writeByteArrayToFile(new File(savePath + filePath), file.getBytes());
//                System.out.println(filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileView fileVo = new FileView();
        fileVo.setFilePath(filePath);
//        System.out.println(fileVo.getFilePath());
        try {
//              return fileVo;
            return new ResponseEntity<>(fileVo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to upload file ! upload type: " + type;
            LOG.error(error, t);
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseMessage(error, t);
        }

    }

}
