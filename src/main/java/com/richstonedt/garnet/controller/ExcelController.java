package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.ExcelUtils;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.interceptory.LoginRequired;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.view.FileView;
import com.richstonedt.garnet.model.view.ResourceExcelView;
import com.richstonedt.garnet.model.view.ResourceView;
import com.richstonedt.garnet.service.ResourceService;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(value = "[Garnet]上传模块")
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

        ResponseEntity<?> head = upload(request, response, map, "excel");
        FileView fileVo = (FileView) head.getBody();
        String[] split = fileVo.getFilePath().split("\\.");
        String excelPath = GarnetContants.SAVE_PATH  + "/" + fileVo.getFilePath();
        try (FileInputStream fileInputStream = new FileInputStream(excelPath)) {
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
            e.printStackTrace();
            String error = "Import Excel Fail";
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(e.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, e);
        }
    }

    private ResponseEntity<?> upload(HttpServletRequest request, HttpServletResponse response, ModelMap map, String type) {
        MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
        MultipartFile file = mhs.getFile("file");
        ServletContext application = request.getSession().getServletContext();
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
                filePath = filePath + newFileName + "_" + file.getOriginalFilename();
                LOG.info("上传文件： " + filePath);
                FileUtils.writeByteArrayToFile(new File(savePath + filePath), file.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileView fileVo = new FileView();
        fileVo.setFilePath(filePath);
        try {
            return new ResponseEntity<>(fileVo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to upload file ! upload type: " + type;
            LOG.error(error, t);
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseMessage(error, t);
        }
    }

    @ApiOperation(value = "[Garnet]下载资源导入Excel模板", notes = "下载资源导入Excel模板")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/download/resourceexcel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> downloadExcelTemplate() {
//        String path = System.getProperty("user.dir") + "/src/main/resources/exceltemplate/upload_template.xlsx";;
        File file = new File(GarnetContants.SAVE_PATH + "/upload_template.xlsx");
        byte[] body = null;
        try(InputStream is = new FileInputStream(file)) {
            body = new byte[is.available()];
            is.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attchement;filename=" + file.getName());
            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
            return entity;
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
