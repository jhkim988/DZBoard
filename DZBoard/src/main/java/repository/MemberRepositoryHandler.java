package repository;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MemberRepositoryHandler implements InvocationHandler {
	private MemberRepository repository;
	
	public MemberRepositoryHandler(MemberRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object ret = null;
		repository.open();
		try {
			ret = method.invoke(repository, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return ret;
	}
}
