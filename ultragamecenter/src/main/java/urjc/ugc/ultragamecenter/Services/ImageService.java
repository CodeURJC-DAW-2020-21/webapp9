package urjc.ugc.ultragamecenter.services;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.logging.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.models.User;

@Service
public class ImageService{

	private final static Logger log = Logger.getLogger("urjc.ugc.ultragamecenter.services.ImageService");
	private static final SimpleDateFormat FILE_NAME_FORMAT = new SimpleDateFormat("'img-'yyyyMMdd-hhmmss-SSS");
	public static final String IMG_FOLDER = "userImg/";
	public static final String IMG_CONTROLLER_URL = "/uploadImages/userImg/";
	private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "uploadImages");


	public boolean isValidImage(MultipartFile file) {
        return file != null && !file.isEmpty();
	}

	public String getImagePath(){
		return IMG_CONTROLLER_URL;
	}

	public String uploadImage(MultipartFile file) {
		Path folder = FILES_FOLDER.resolve(IMG_FOLDER);
		System.out.println(folder);
		if (!Files.exists(folder)) {
			log.warning("no folder");
		}
		String fileName = generateFileName();
		Path newImage = folder.resolve(fileName+".jpg");
		System.out.println(newImage);
		try {
			file.transferTo(newImage);
		} catch (Exception e) {
			//TODO: handle exception
		}
		return fileName+".jpg";
	}

	private synchronized String generateFileName() {
		return FILE_NAME_FORMAT.format(new Date());
	}
	
	public ResponseEntity<Object> createResponseFromImage(User user) throws MalformedURLException {
		Path folder = FILES_FOLDER.resolve(IMG_FOLDER);
		System.out.println(folder);
		if (!Files.exists(folder)) {
			log.warning("no folder");
		}
		Path imagePath = folder.resolve(user.getProfileSrc());
		System.out.println(imagePath);
		
		Resource file = new UrlResource(imagePath.toUri());
		
		if(!Files.exists(imagePath)) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(file);
		}		
	}

}
