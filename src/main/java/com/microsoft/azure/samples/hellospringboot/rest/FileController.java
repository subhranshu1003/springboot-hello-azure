package com.microsoft.azure.samples.hellospringboot.rest;

import java.util.List;

import com.microsoft.azure.samples.hellospringboot.entity.EmployeeEntity;
import com.microsoft.azure.samples.hellospringboot.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping(value="/api")
@CrossOrigin(origins = "https://demoangular.z21.web.core.windows.net")
public class FileController {

	@Autowired
    private FileStorageService fileStorageService;
	@PostMapping("/bucketlists/upload")
	public List<EmployeeEntity> uploadFile(@RequestParam("file") MultipartFile file) {
		return fileStorageService.storeFile(file);
           // return fileName;
		/*String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
*/
		/*return new UploadFileResponse(fileName, fileDownloadUri,
				file.getContentType(), file.getSize());*/
	}
}
