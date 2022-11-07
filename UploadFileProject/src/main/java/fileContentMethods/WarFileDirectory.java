package fileContentMethods;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import FolderMangmentMethod.folderDirectory;

public class WarFileDirectory {
	public boolean FolderIsDeleted = false;
	public boolean noWarFileFound = true;
	public final File warFile;
    public boolean folderNotChecked=true;
	public WarFileDirectory(File warFile) {
		this.warFile=warFile;
	}
	
    public boolean copyPasteWarFile(String toLocation) throws IOException, InterruptedException {
    	boolean deploy =false;
	 folderDirectory newFolder=new folderDirectory();
	 newFolder.file=this;
	 

		// check if exists
		if (warFile.exists()) {
			
			File Webappsfolder = new File(toLocation);
			File[] listOfFiles = Webappsfolder.listFiles();

			for (File file : listOfFiles) {

					// go to webapps and check if there is a file have the same name
					if (file.getName().equals(warFile.getName())) {

						System.out.println("we found another file have same name");
						file.delete();
						// checking if the file deleted completely
						if (file.exists()) { // can't delete war file ,that exist before in webapp folder
							
							System.out.println("we can't delete the file");
							
							noWarFileFound = false;

						} else { 
							System.out.println("we delete the file");

							// check if there a folder with the same war file name
							 newFolder.checkFolderIfExistByWarFileName(toLocation, warFile.getName());
						}
						break;
					
				}
			}
			if (folderNotChecked) {
				 newFolder.deleteWarFolder(toLocation);
			}

			if (FolderIsDeleted && noWarFileFound) {
				
				Path fromPath = warFile.toPath();
				
				Path toPath = new File(toLocation + warFile.getName()).toPath();

				Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("copied the file");
				deploy=true;
			}

		} else {
			System.out.print("file not exists");
			deploy=false;

		}
		 System.out.println("sss");

		return deploy;

	}
	
	

}
