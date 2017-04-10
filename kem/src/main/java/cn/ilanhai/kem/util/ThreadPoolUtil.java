package cn.ilanhai.kem.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {
	public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
}	
