package fileContentMethods;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

   public class TxtFileContent {

	static HashMap<String, Integer> visited=new HashMap<>();
	final String txtFileLocation;

	
	public TxtFileContent(String txtFileLocation) {
		this.txtFileLocation=txtFileLocation;


	}
	
	public void AddTextAfterWordInTxtFile(String exist_Word) throws IOException, InterruptedException {
		File txtFile=new File(txtFileLocation);
		Path path = Paths.get(txtFile.getPath());

		Charset charset = StandardCharsets.UTF_8;
		// take the content of the txt file and stored in content
		String content = new String(Files.readAllBytes(path), charset);
		// check if the the word already added before
		 if(content.contains(exist_Word+" down ")) {
		     System.out.println("error to add 'down',because already added");
 
		 }else {
		     // replace the word with itself and the added word
			 content = content.replaceFirst(exist_Word, exist_Word+" down");
			 // write the content after replacment in txt file
		     Files.write(path, content.getBytes(charset));
             System.out.println("added 'down' successfully");
         }
	}
	
	public void DeleteTextAfterWordInTxtFile(String serverIP) throws IOException, InterruptedException {

	File txtFile=new File(txtFileLocation);
		
		Path path = Paths.get(txtFile.getPath());

		Charset charset = StandardCharsets.UTF_8;
		// take the content of the txt file and stored in content
		String content = new String(Files.readAllBytes(path), charset);

		// check if the the word already added before
		 if(content.contains(serverIP+" down ")) {
 
		  // replace the word with itself and the added word
			 content = content.replaceFirst(serverIP+" down",serverIP);
			 // write the content after replacment in txt file
		     Files.write(path, content.getBytes(charset));
             System.out.println("removed down from '"+serverIP+"' successfully");
		 }else {
		     System.out.println("no down word on "+serverIP);

         }
	}
  

	}


