package com.microsoft.azure.samples.hellospringboot.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.transaction.Transactional;

import com.microsoft.azure.samples.hellospringboot.dao.EmployeeDao;
import com.microsoft.azure.samples.hellospringboot.entity.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;




@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
				.toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			// throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}



    @Transactional
	public List<EmployeeEntity> storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		List<EmployeeEntity> empEntity=null;
		try {
			// Check if the file's name contains invalid characters
			if(fileName.contains("..")) {
				// throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			Scanner scanner=null;
			try {
				scanner = new Scanner(new File( targetLocation.toString()));
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			Scanner dataScanner = null;
			int index = 0;
			List<EmployeeEntity> empList = new ArrayList<>();

			while (scanner.hasNextLine()) {
				dataScanner = new Scanner(scanner.nextLine());
				dataScanner.useDelimiter(",");

				EmployeeEntity emp = new EmployeeEntity();

				while (dataScanner.hasNext()) {

					String data = dataScanner.next();

					/*	if(data.equals("name")) {
						continue;
					}*/

					if (index == 0)
						emp.setId(Integer.parseInt(data));
					else if (index == 1)
						emp.setName(data);

					else
						System.out.println("invalid data::" + data);
					index++;

				}
				index = 0;
				empList.add(emp);
				 
			}

			scanner.close();

			//System.out.println(empList);
			 empEntity=(List<EmployeeEntity>) employeeDao.saveAll(empList);
          //  return fileName;
		} catch (IOException ex) {
			//  throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
		return empEntity;

	}
}
