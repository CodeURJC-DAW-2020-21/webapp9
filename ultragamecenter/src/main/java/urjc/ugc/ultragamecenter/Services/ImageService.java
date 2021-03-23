package urjc.ugc.ultragamecenter.services;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {


	private static final SimpleDateFormat FILE_NAME_FORMAT = new SimpleDateFormat("'img-'yyyyMMdd-hhmmss-SSS");
	public static final String IMG_FOLDER = "src/main/resources/static/images/uploads/";
	public static final String IMG_CONTROLLER_URL = "/images/uploads/";

	public boolean isValidImage(MultipartFile file) {
        return file != null && !file.isEmpty();
	}

	public String uploadImage(MultipartFile file) {
		String fileName = generateFileName();
		File uploadFile = new File(new File(IMG_FOLDER).getAbsolutePath(), fileName);
		try {
			file.transferTo(uploadFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return IMG_CONTROLLER_URL + fileName;
	}

	private synchronized String generateFileName() {
		return FILE_NAME_FORMAT.format(new Date());
	}

}