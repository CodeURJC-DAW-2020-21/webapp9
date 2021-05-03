package urjc.ugc.ultragamecenter.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.util.logging.*;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.models.User;

@Service
public class ImageService {

	private final static Logger log = Logger.getLogger("urjc.ugc.ultragamecenter.services.ImageService");
	private static final SimpleDateFormat FILE_NAME_FORMAT = new SimpleDateFormat("'img-'yyyyMMdd-hhmmss-SSS");
	public static final String IMG_FOLDER = "userImg/";
	public static final String IMG_CONTROLLER_URL = "/images/uploadImages/userImg/";
	private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
			"GIT/ultragamecenter/src/main/resources/static/images/uploadImages");

	public boolean isValidImage(MultipartFile file) {
		return file != null && !file.isEmpty();
	}

	public String getImagePath() {
		return IMG_CONTROLLER_URL;
	}

	public String uploadImage(MultipartFile file, String name) {
		Path folder = FILES_FOLDER.resolve(IMG_FOLDER + name + "/");
		File directory = folder.toFile();
		if (!directory.exists()) {
			if (directory.mkdirs()) {
				log.warning("New directory");
			} else {
				log.warning("Cannot create directory");
			}
		}
		String fileName = "fotoPerfil";
		Path newImage = folder.resolve(fileName + ".jpg");
		System.out.println(newImage);
		try {
			file.transferTo(newImage);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fileName + ".jpg";
	}

	private synchronized String generateFileName() {
		return FILE_NAME_FORMAT.format(new Date());
	}

	public void uploadImageEvent(MultipartFile file, String id, String name) {
		Path folder = FILES_FOLDER.resolve(IMG_FOLDER + id);
		File directory = folder.toFile();
		if (!directory.exists()) {
			if (directory.mkdirs()) {
				log.warning("New directory");
			} else {
				log.warning("Cannot create directory");
			}
		}
		Path newImage = folder.resolve(name + ".jpg");
		System.out.println(newImage);
		try {
			file.transferTo(newImage);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public ResponseEntity<Object> createResponseFromImage(User user) throws MalformedURLException {
		Path folder = FILES_FOLDER.resolve(IMG_FOLDER + user.getEmail() + "/");
		File directory = folder.toFile();
		if (!directory.exists()) {
			if (directory.mkdirs()) {
				log.warning("New directory");
			} else {
				log.warning("Cannot create directory");
			}
		}
		Path imagePath = folder.resolve("fotoPerfil.jpg");
		System.out.println(imagePath);
		log.warning("up is imagepath");
		Resource file = new UrlResource(imagePath.toUri());
		if (!Files.exists(imagePath)) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(file);
		}
	}

	/* public static String changeEmail(String email) {
		String last = "";
		for (String aux : email.split("@")) {
			last += aux;
			last += "_";
		}
		return last;
	} */

	public static void CREATE_FOLDER_USER(String email) throws IOException {
		File original = new File(System.getProperty("user.dir"),
				"GIT/ultragamecenter/src/main/resources/static/images/uploadImages/userImg/defaultuser.jpg");
		Path folder = FILES_FOLDER.resolve(IMG_FOLDER + email + "/");
		File directory = folder.toFile();
		if (!directory.exists()) {
			if (directory.mkdirs()) {
				log.warning("New directory");
			} else {
				log.warning("Cannot create directory");
			}
		}
		File newFile = new File(folder + "/fotoPerfil.jpg");
		FileUtils.copyFile(original, newFile);
	}

	public static void CREATE_FOLDER_EVENT(String id) throws IOException {
		Path folder = FILES_FOLDER.resolve(IMG_FOLDER + id + "/");
		File directory = folder.toFile();
		if (!directory.exists()) {
			if (directory.mkdirs()) {
				log.warning("New directory");
			} else {
				log.warning("Cannot create directory");
			}
		}
		File original = new File(System.getProperty("user.dir"),
				"GIT/ultragamecenter/src/main/resources/static/images/uploadImages/userImg/defaultuser.jpg");
		File newFile = new File(folder + "/baner.jpg");
		FileUtils.copyFile(original, newFile);
		newFile = new File(folder + "/galery1.jpg");
		FileUtils.copyFile(original, newFile);
		newFile = new File(folder + "/galery2.jpg");
		FileUtils.copyFile(original, newFile);
		newFile = new File(folder + "/galery3.jpg");
		FileUtils.copyFile(original, newFile);
	}

	public static String getSource(String email) {
		return IMG_CONTROLLER_URL + email + "/fotoPerfil.jpg";
	}
}
