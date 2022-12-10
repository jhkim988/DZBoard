package repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import admin.urlauth.UrlAuth;

public class UrlAuthRepository {
	private Repository repository = new Repository();
	private ResultSet rs;
	
	public UrlAuth findOneUrlAuth(String url) {
		repository.open();
		try {
			rs = repository.executeQuery("select * from tb_dzboard_urlauth where url = ?", url);
			if (rs.next()) {
				return UrlAuth.builder()
						.url(rs.getString("url"))
						.authority(rs.getInt("authority"))
						.note(rs.getString("note"))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return null;
	}
	
	public Map<String, UrlAuth> findAllUrlAuth() {
		Map<String, UrlAuth> ret = new HashMap<>();
		repository.open();
		try {
			rs = repository.executeQuery("select * from tb_dzboard_urlauth");
			while (rs.next()) {
				String url = rs.getString("url");
				int authority = rs.getInt("authority");
				String note = rs.getString("note");
				ret.put(url, UrlAuth.builder()
							.url(url)
							.authority(authority)
							.note(note)
							.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return ret;
	}
	
	public boolean createUrlAuth(UrlAuth urlAuth) {
		return repository.executeUpdatePreparedStatement(
				"insert into tb_dzboard_urlauth (url, authority, note) values (?, ?, ?)"
				, urlAuth.getUrl()
				, urlAuth.getAuthority()
				, urlAuth.getNote()) == 1;
	}
	
	public boolean updateUrlAuth(UrlAuth newAuth) {
		return repository.executeUpdatePreparedStatement(
				"update tb_dzboard_urlauth set authority = ?, note = ? where url = ?"
				, newAuth.getAuthority()
				, newAuth.getNote()
				, newAuth.getUrl()) == 1;
	}
	
	public boolean deleteUrlAuthByUrl(String url) {
		return repository.executeUpdatePreparedStatement(
				"delete from tb_dzboard_urlauth where url = ?"
				, url) == 1;
 	}
}
