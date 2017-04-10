package cn.ilanhai.framework.app;

public class SyncApplication {
	private static ThreadLocal<Application> threadLocal = new ThreadLocal<Application>();

	public static void setApplication(Application application) {
		threadLocal.set(application);
	}

	public static Application getApplication() {
		return threadLocal.get();
	}

	public static void removeApplication() {
		threadLocal.remove();
	}

}
