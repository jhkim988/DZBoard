package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.file.UploadedFile;

public class UploadedFileRepository {
	private Repository repository = new Repository();
	private ResultSet rs;
	
	public List<UploadedFile> listByPostId(int postId) {
		List<UploadedFile> ret = new ArrayList<>();
		repository.open();
		try {
			rs = repository.executeQuery("select * from tb_dzboard_uploaded_file where post_id = ?"
					, postId);
			while (rs.next()) {
				ret.add(UploadedFile.builder()
						.f_id(rs.getInt(1))
						.post_id(rs.getInt(2))
						.org_name(rs.getString(3))
						.real_name(rs.getString(4))
						.content_type(rs.getString(5))
						.content_length(rs.getLong(6))
						.createdAt(rs.getTimestamp(7))
						.build());
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return ret;
	}
	
	public boolean insertUploadedFile(UploadedFile uploadedFile) {
		return repository.executeUpdatePreparedStatement(
				"insert into tb_dzboard_uploaded_file (post_id, org_name, real_name, content_type, content_length) values (?, ?, ?, ?, ?)"
				, uploadedFile.getPost_id()
				, uploadedFile.getOrg_name()
				, uploadedFile.getReal_name()
				, uploadedFile.getContent_type()
				, uploadedFile.getContent_length()) == 1;
	}

	public UploadedFile findByF_id(long f_id) {
		UploadedFile ret = null;
		repository.open();
		try {
			rs = repository.executeQuery(
					"select * from tb_dzboard_uploaded_file where f_id = ?"
					, f_id);
			if (rs.next()) {
				ret = UploadedFile.builder()
						.f_id(rs.getInt(1))
						.post_id(rs.getInt(2))
						.org_name(rs.getString(3))
						.real_name(rs.getString(4))
						.content_type(rs.getString(5))
						.content_length(rs.getLong(6))
						.createdAt(rs.getTimestamp(7))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return ret;
	}
}
