package jp.co.poweredge.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {

	private final String UPLOAD_DIR = "C:\\Users\\hassannaoko\\Desktop\\directoryforfileexample\\";


	@RequestMapping(value="/")
	public ModelAndView index(ModelAndView mav) {

		mav.setViewName("index");

		return mav;
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
		//check if file is empty
		if(file.isEmpty()) {
			attributes.addFlashAttribute("message", "Please select a file to upload!!!!");
			return "redirect:/";
		}

		//normalize the file path
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		//save the file on the local file system

		try {
			Path path = Paths.get(UPLOAD_DIR + fileName);
			Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException e ) {
			e.printStackTrace();
		}

		// return success response
		attributes.addFlashAttribute("message", "You have successfully uploaded " + fileName + "!!!!");
		attributes.addFlashAttribute("message2", "You have 2 uploaded " + fileName + "!!!!");
		return "redirect:/";
	}


}
