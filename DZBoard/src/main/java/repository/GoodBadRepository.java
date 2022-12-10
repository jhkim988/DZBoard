package repository;

public class GoodBadRepository {
	private Repository repository = new Repository();
	
	public boolean click(String isGood, String memberId, int postId) {
			return repository.executeUpdatePreparedStatement(
					"insert into tb_dzboard_goodbad (good, memberid, postid) values (?, ?, ?)"
					, isGood
					, memberId
					, postId) == 1;
	}
}
