package board;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import entities.Category;
import entities.Member;
import entities.Post;
import entities.UploadedFile;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.GoodBadRepository;
import repository.MemberRepository;
import repository.PostRepository;
import repository.UploadedFileRepository;
import server.Action;
import server.RequestMapping;
import server.Utility;

@Action
public class PostAction {
	
	@RequestMapping("/board")
	public String boardMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/board/board.jsp";
	}
	
	@RequestMapping("/board/create")
	public String createForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member member = (Member) request.getSession().getAttribute("member");
		List<Category> categoryList = getCategoryList(member.getAuthority());
		request.setAttribute("categoryList", categoryList);
		return "/resources/board/create.jsp";
	}
	
	@RequestMapping("/board/createPost")
	public JSONObject createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File("c:\\upload"));
		ServletFileUpload upload = new ServletFileUpload(factory);
		Map<String, List<FileItem>> items = upload.parseParameterMap(request);
		
		String title = items.get("title").get(0).getString("UTF-8");
		String category = items.get("category").get(0).getString("UTF-8");
		String content = items.get("content").get(0).getString("UTF-8");
		String parent = items.get("parent") == null ? null : items.get("parent").get(0).getString("UTF-8");
		
		Post post = Post.builder()
				.title(title)
				.content(content)
				.category(category)
				.build();
		if (parent != null) {
			post.setParent(Integer.parseInt(parent));
		}

		PostRepository postRepository = new PostRepository();
		int postId = parent == null
				? postRepository.createPost(post, member)
				: postRepository.createReplyPost(post, member);
		System.out.println("postId: " + postId);
		if (postId < 0) {
			return jsonOut(false, "게시글 저장 실패!");
		}
		
		MemberRepository memberRepository = new MemberRepository();
		memberRepository.updateUpdatedAt(member);
		session.setAttribute("member", memberRepository.findOneMemberById(member.getId()));

		if (items.containsKey("file")) {
			boolean success = items.get("file").stream()
				.filter(fileItem -> fileItem.getSize() > 0)
				.allMatch(fileItem -> storeFile(fileItem, postId));
			return jsonOut(success, success ? "성공!" : "파일 저장 실패!");
		}
		return jsonOut(true, "성공!");
	}
	
	@RequestMapping("/board/replyForm")
	public String replyForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int parentId = Integer.parseInt(request.getParameter("parent"));
		PostRepository postRepository = new PostRepository();
		Post parentPost = postRepository.findOnePostById(parentId);
		request.setAttribute("parentPost", parentPost);
		
		UploadedFileRepository fileRepository = new UploadedFileRepository();
		List<UploadedFile> uploadedFiles = fileRepository.listByPostId(parentId);
		request.setAttribute("uploadedFiles", uploadedFiles);
		
		Member member = (Member) request.getSession().getAttribute("member");
		List<Category> categoryList = getCategoryList(member.getAuthority());
		request.setAttribute("categoryList", categoryList);
		return "/resources/board/replyForm.jsp";
	}
	
	@RequestMapping("/board/deletePost")
	public String deletePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("member");
		
		if (deleteResult(id, loginMember)) {
			response.sendRedirect("/DZBoard/board");
		} else {
			response.sendRedirect("/DZBoard/board/view?id="+id);			
		}
		return "";
	}
	
	@RequestMapping("/board/goodBad")
	public JSONObject goodBad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		String type = jsonIn.getString("type");
		int postId = jsonIn.getInt("postid");
		Member member = (Member) request.getSession().getAttribute("member");
		
		PostRepository postRepository = new PostRepository();
		GoodBadRepository goodBadRepository = new GoodBadRepository();
		
		JSONObject jsonOut = new JSONObject();
		jsonOut.put("message", "이미 클릭하셨습니다.");
		if ("good".equals(type)) {
			if (goodBadRepository.click("y", member.getId(), postId)) {
				postRepository.incrementGood(postId);
				jsonOut.put("message", "추천!");
			}
		} else if ("bad".equals(type)) {
			if (goodBadRepository.click("n", member.getId(), postId)) {
				postRepository.incrementBad(postId);
				jsonOut.put("message", "비추천!");
			}
		} else {
			throw new UnsupportedOperationException();
		}
		
		return jsonOut;
	}
	
	@RequestMapping("/board/updateForm")
	public String updatePostForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("member");
		int postId = Integer.parseInt(request.getParameter("id"));
		
		request.setAttribute("categoryList", getCategoryList(loginMember.getAuthority()));
		
		PostRepository postRepository = new PostRepository();
		Post oldPost = postRepository.findOnePostById(postId);
		if (oldPost.isSameAuthor(loginMember)) {
			request.setAttribute("oldPost", oldPost);
			return "/resources/board/update.jsp";
		}
		return request.getHeader("referer");
	}
	
	@RequestMapping("/board/updatePost")
	public JSONObject updatePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("member");
		int postId = jsonIn.getInt("id");
	
		PostRepository postRepository = new PostRepository();
		Post oldPost = postRepository.findOnePostById(postId);
		
		String title = jsonIn.getString("title");
		String content = jsonIn.getString("content");
		String category = jsonIn.getString("category");
		Post newPost = Post.builder()
				.title(title)
				.content(content)
				.category(category)
				.build();
		
		JSONObject json = new JSONObject();
		if (oldPost != null && oldPost.isSameAuthor(loginMember) && postRepository.updatePost(oldPost, newPost)) {
			json.put("status", true);
			json.put("message", "수정!");
			json.put("url", "/DZBoard/board/view?id="+postId);
		} else {
			json.put("status", false);
			json.put("message", "실패!");
			json.put("url", "/DZBoard/board/updatePost?id="+postId);
		}
		return json;
	}
	
	@RequestMapping("/board/view")
	public String viewPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postId = Integer.parseInt(request.getParameter("id"));
		PostRepository postRepository = new PostRepository();
		Post post = postRepository.findOnePostById(postId);
		if (post == null) {
			response.sendRedirect("/board");
			return "";
		}
		
		UploadedFileRepository uploadedFileRepository = new UploadedFileRepository();
		postRepository.incrementViewCount(postId);
		request.setAttribute("post", post);
		request.setAttribute("uploadedFiles", uploadedFileRepository.listByPostId(postId));
		return "/resources/board/view.jsp";
	}
	
	private List<Category> getCategoryList(int authority) {
		@SuppressWarnings("unchecked")
		List<Category> allCategories = (List<Category>) Utility.getServletContext().getAttribute("allCategoryList");
		return allCategories.stream().filter(category -> category.getAuthority() <= authority).collect(Collectors.toList());
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
	
	private boolean deleteResult(int postId, Member loginMember) {
		PostRepository postRepository = new PostRepository();
		Post post = postRepository.findOnePostById(postId);
		
		if (post == null) return false;
		
		MemberRepository memberRepository = new MemberRepository();
		Member author = memberRepository.findOneMemberById(post.getAuthor());
		
		if (author == null) {
			if (loginMember.getAuthority() == 99) {
				postRepository.deletePost(post);
				return true;
			}
			return false;
		}
		
		if (author.getAuthority() < loginMember.getAuthority() || post.isSameAuthor(loginMember)) {
			return postRepository.deletePost(post);
		}
		return false;
	}
}
