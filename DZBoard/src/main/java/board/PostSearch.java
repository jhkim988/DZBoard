package board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entities.Post;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.PostRepository;
import server.Action;
import server.RequestMapping;

@Action
public class PostSearch {
	
	@RequestMapping("/board/all")
	public JSONObject searchAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject jsonIn = new JSONObject(request.getReader().readLine());
		int pageNo = jsonIn.getInt("pageNo");
		int pageSize = jsonIn.getInt("pageSize");
		int offset = (pageNo-1)*pageSize;
		
		PostRepository repository = new PostRepository();
		List<Post> list = repository.listPostHeader(offset, pageSize);
		int total = repository.countPost();
		return getResultJSON(request, response, list, total);
	}

	@RequestMapping("/board/author")
	public JSONObject searchByAuthor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject jsonIn = new JSONObject(request.getReader().readLine());
		int pageNo = jsonIn.getInt("pageNo");
		int pageSize = jsonIn.getInt("pageSize");
		String author = jsonIn.getString("author");
		int offset = (pageNo-1)*pageSize;
		
		PostRepository repository = new PostRepository();
		List<Post> list = repository.listPostHeaderOfAuthor(offset, pageSize, author);
		int total = repository.countPostByAuthor(author);
		return getResultJSON(request, response, list, total);
	}
	
	@RequestMapping("/board/category")
	public JSONObject searchByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject jsonIn = new JSONObject(request.getReader().readLine());
		int pageNo = jsonIn.getInt("pageNo");
		int pageSize = jsonIn.getInt("pageSize");
		int offset = (pageNo-1)*pageSize;
		String category = jsonIn.getString("category");
		
		PostRepository repository = new PostRepository();
		List<Post> list = repository.listPostHeaderOfCategory(offset, pageSize, category);
		int total = repository.countPostByCategory(category);
		return getResultJSON(request, response, list, total);
	}

	@RequestMapping("/board/content")
	public JSONObject searchByContent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject jsonIn = new JSONObject(request.getReader().readLine());
		int pageNo = jsonIn.getInt("pageNo");
		int pageSize = jsonIn.getInt("pageSize");
		String content = jsonIn.getString("content");
		int offset = (pageNo-1)*pageSize;
		
		PostRepository repository = new PostRepository();
		List<Post> list = repository.listPostHeaderOfContent(offset, pageSize, content);
		int total = repository.countPostByContent(content);
		return getResultJSON(request, response, list, total);
	}

	@RequestMapping("/board/good")
	public JSONObject searchByGood(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject jsonIn = new JSONObject(request.getReader().readLine());
		int pageNo = jsonIn.getInt("pageNo");
		int pageSize = jsonIn.getInt("pageSize");
		int good = jsonIn.getInt("good");
		int offset = (pageNo-1)*pageSize;
		PostRepository repository = new PostRepository();
		List<Post> list = repository.listPostHeaderOfGood(offset, pageSize, good);
		int total = repository.countPostByGood(good);
		return getResultJSON(request, response, list, total);
	}

	@RequestMapping("/board/postId")
	public JSONObject searchByPostId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject jsonIn = new JSONObject(request.getReader().readLine());
		PostRepository repository = new PostRepository();
		int postId = 0;
		try {
			postId = jsonIn.getInt("postId");
		} catch (JSONException e) {
			return toJSON(false, "글 번호를 입력해주세요");
		}
		 
		Post post = repository.findOnePostById(postId);
		List<Post> list = new ArrayList<>();
		if (post != null) list.add(post);
		return getResultJSON(request, response, list, 1);
	}
	
	@RequestMapping("/board/title")
	public JSONObject searchByTitle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject jsonIn = new JSONObject(request.getReader().readLine());
		int pageNo = jsonIn.getInt("pageNo");
		int pageSize = jsonIn.getInt("pageSize");
		String title = jsonIn.getString("title");
		int offset = (pageNo-1)*pageSize;
		PostRepository repository = new PostRepository();
		List<Post> list = repository.listPostHeaderOfTitle(offset, pageSize, title);
		int total = repository.countPostByTitle(title);
		return getResultJSON(request, response, list, total);
	}
	
	private JSONObject toJSON(boolean status, String message) {
		JSONObject ret = new JSONObject();
		ret.put("status", status);
		ret.put("message", message);
		return ret;
	}
	
	private JSONObject getResultJSON(HttpServletRequest request, HttpServletResponse response, List<Post> posts, int total) throws ServletException, IOException {
		JSONArray jsonArr = new JSONArray();
		posts.forEach(post -> jsonArr.put(post.headertoJSON()));
		JSONObject jsonOut = new JSONObject();
		jsonOut.put("status", posts.size() > 0);
		jsonOut.put("data", jsonArr);
		jsonOut.put("total", total);
		return jsonOut;
	}
}
