package FolderMangmentMethod;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import databaseManagment.CurrentTimeTracker;
import fileContentMethods.WarFileDirectory;

public class folderDirectory {
     public WarFileDirectory file;
     String folderLocation;
     
	public  void checkFolderIfExistByWarFileName(String folderLocation,String Warname) throws InterruptedException, IOException {
		 if(file!=null) {
		 String warFolderLocation = folderLocation
				+ Warname.substring(0,Warname.length() - 4);
		
		Path WarFolder = Paths.get(warFolderLocation);
		
		file.folderNotChecked = false;
		// check if there folder have same name like war file
		if (Files.exists(WarFolder)) { // folder with same war name exist
			// wait 30 seconds to check the folder
			System.out.println(CurrentTimeTracker.GetCurrentTime() +" folder is still there");

			long startTime = System.currentTimeMillis();

			do {

				Thread.sleep(3000);

				System.out.println(CurrentTimeTracker.GetCurrentTime() +" checking if folder still there");

				if (!(Files.exists(WarFolder))) {
					System.out.println(CurrentTimeTracker.GetCurrentTime() +" folder is gone");
					file.FolderIsDeleted = true;
				}

			} while (!file.FolderIsDeleted && ((System.currentTimeMillis() - startTime) < 30000));
			if(!file.FolderIsDeleted) {
				this.deleteWarFolder(folderLocation);
			}
		} else { // there is no folder with same name
			file.FolderIsDeleted = true;
		}
		}else {
			System.out.print(CurrentTimeTracker.GetCurrentTime() +" can't check folder ,because warfile is null");
		}

	}

	public  void deleteWarFolder(String folderLocation) throws IOException {
		if(file!=null) {
		String warFolderLocation = folderLocation
				+ file.warFile.getName().substring(0, file.warFile.getName().length() - 4);
		
		Path WarFolder = Paths.get(warFolderLocation);
		// check if there folder have same name like war file
		if (Files.exists(WarFolder)) {
			deleteDirectoryRecursion(WarFolder);
		if (Files.exists(WarFolder)) {
            System.out.println(CurrentTimeTracker.GetCurrentTime() +" folder is not deleted");
            file.FolderIsDeleted=false;
		}
		else {
			System.out.println(CurrentTimeTracker.GetCurrentTime() +" folder is deleted");
            file.FolderIsDeleted=true;
		}
		}
		else {
            file.FolderIsDeleted=true;
		}
		}else {
			System.out.print(CurrentTimeTracker.GetCurrentTime() +" can't check folder ,because warfile is null");

		}
	}
	
    private  void deleteDirectoryRecursion(Path path) throws IOException {

		  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
		    try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
		      for (Path entry : entries) {
		        deleteDirectoryRecursion(entry);
		      }
		    }
		  }
		  Files.delete(path);
		}

}
