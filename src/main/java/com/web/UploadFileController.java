package com.web;

import com.config.AppConfig;
import com.vo.FileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/a/upload")
public class UploadFileController {
    @Autowired
    private AppConfig appConfig;
    @ResponseBody
    @RequestMapping("/file")
    public FileResult fileUpload(MultipartFile file){
        String filename = "";
        if(!file.isEmpty()){
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            filename =System.currentTimeMillis()+suffix;
            String savePath =appConfig.getFilePath()+"/film/"+filename;
            File dest = new File(savePath);
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdir();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                return new FileResult("300","文件上传失败");
            }
        }else {
            return new FileResult("300","文件上传失败");
        }
        String fileVisitPath =appConfig.getUrlPath()+"/public/film/"+filename;
        String filePath = filename;
        List<String> list = new ArrayList<>();
        list.add(fileVisitPath);
        list.add(filePath);
        return new FileResult("200",list);
    }
}
