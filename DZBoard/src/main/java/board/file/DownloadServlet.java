package board.file;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.UploadedFileRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONObject;

@WebServlet("/board/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int bufSize = 4096;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long f_id = Long.valueOf(request.getParameter("f_id"));
		
		UploadedFileRepository repository = new UploadedFileRepository();
		UploadedFile uploadedFile = repository.findByF_id(f_id);
		
		response.setContentType(uploadedFile.getContent_type());
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + uploadedFile.getOrg_name());
		InputStream fis = new FileInputStream(new File(uploadedFile.getReal_name()));
		OutputStream out = response.getOutputStream();
		int readSize = 0;
		byte[] buff = new byte[bufSize];
		while (true) {
			readSize = fis.read(buff);
			if (readSize <= 0) break;
			out.write(buff, 0, readSize);
		}
		
		fis.close();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
