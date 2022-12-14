package board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.PostRepository;
import repository.UploadedFileRepository;

import java.io.IOException;

import javax.sql.DataSource;

import board.file.UploadedFile;

@WebServlet("/board/view")
public class ViewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postId = Integer.parseInt(request.getParameter("id"));
		PostRepository postRepository = new PostRepository();
		Post post = postRepository.findOnePostById(postId);
		if (post == null) {
			response.sendRedirect("/board");
			return;
		}
		
		UploadedFileRepository uploadedFileRepository = new UploadedFileRepository();
		postRepository.incrementViewCount(postId);
		request.setAttribute("post", post);
		request.setAttribute("uploadedFiles", uploadedFileRepository.listByPostId(postId));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/board/view.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
