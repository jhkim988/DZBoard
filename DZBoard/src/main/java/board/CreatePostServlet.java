package board;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import board.file.UploadedFile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.Member;
import repository.MemberRepository;
import repository.PostRepository;
import repository.UploadedFileRepository;

@WebServlet("/board/createPost")
public class CreatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject jsonOut = new JSONObject();
		
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File("c:\\upload"));
		ServletFileUpload upload = new ServletFileUpload(factory);
		Map<String, List<FileItem>> items = upload.parseParameterMap(request);
		
		String title = items.get("title").get(0).getString("UTF-8");
		String category = items.get("category").get(0).getString("UTF-8");
		String content = items.get("content").get(0).getString("UTF-8");
		
		Post post = Post.builder()
				.title(title)
				.content(content)
				.category(category)
				.build();

		PostRepository postRepository = new PostRepository();
		int postId = postRepository.createPost(post, member);
		
		if (postId < 0) {
			out.print(jsonOut(false, "게시글 저장 실패!"));
			return;
		}
		
		if (items.containsKey("file")) {
			boolean success = items.get("file").stream()
				.filter(fileItem -> fileItem.getSize() > 0)
				.allMatch(fileItem -> storeFile(fileItem, postId));
			out.print(jsonOut(success, success ? "성공!" : "파일 저장 실패!"));
		}

		MemberRepository memberRepository = new MemberRepository();
		memberRepository.updateUpdatedAt(member);
		session.setAttribute("member", memberRepository.findOneMemberById(member.getId()));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean storeFile(FileItem fileItem, int postId) {
		String real_name = "c:\\upload\\" + System.nanoTime();
		String org_name = fileItem.getName();
		UploadedFile uploadedFile = UploadedFile.builder()
				.post_id(postId)
				.org_name(org_name)
				.real_name(real_name)
				.content_type(fileItem.getContentType())
				.content_length(fileItem.getSize())
				.build();
		UploadedFileRepository uploadedFileRepository = new UploadedFileRepository();
		uploadedFileRepository.insertUploadedFile(uploadedFile);
		try {
			fileItem.write(new File(real_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}	
	private JSONObject jsonOut(boolean status, String message) {
		JSONObject jsonOut = new JSONObject();
		jsonOut.put("status", status);
		jsonOut.put("message", message);
		return jsonOut;
	}
}
