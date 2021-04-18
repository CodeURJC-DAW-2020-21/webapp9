package urjc.ugc.ultragamecenter.Controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class ImageController {
    public static final String IMG_FOLDER = "uploadImages/";
	public static final String IMG_CONTROLLER_URL = "/images/uploads/";
    @RequestMapping(value = { IMG_CONTROLLER_URL + "{filename}",
			"/https://localhost:8443" + IMG_CONTROLLER_URL + "{filename}" })
	public void handleFileDownload(@PathVariable String filename, HttpServletResponse res) throws IOException {

		File img = new File(IMG_FOLDER + filename);

		res.setContentType("image/*");
		Long l=img.length();
		res.setContentLength(l.intValue());
		FileCopyUtils.copy(new FileInputStream(img), res.getOutputStream());
	}
}
