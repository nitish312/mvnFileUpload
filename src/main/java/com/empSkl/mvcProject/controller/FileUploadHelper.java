package com.empSkl.mvcProject.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class FileUploadHelper {

    public final String UPLOAD_DIR="C:\\";

    public boolean uploadFile(MultipartFile multiPartFile){

        boolean f = false;

        try {
//            InputStream is = multiPartFile.getInputStream();
//            byte data[] = new byte[is.available()];
//            is.read();
//
//
//            FileOutputStream fos = new FileOutputStream(UPLOAD_DIR + File.separator + multiPartFile.getOriginalFilename());
//            fos.write(data);
//
//            fos.flush();
//            fos.close();

            Files.copy(multiPartFile.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+multiPartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

            f = true;


        }
        catch(Exception e){
            e.printStackTrace();
        }

        return f;
    }
}
