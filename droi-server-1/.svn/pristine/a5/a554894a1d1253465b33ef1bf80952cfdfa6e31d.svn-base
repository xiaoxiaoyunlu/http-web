package com.zj.server.route;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import com.zj.server.anno.BizMethod;
import com.zj.server.common.BusinessCourse;
import com.zj.server.common.SimpleCache;
import com.zj.server.statistic.TransactionStatisticer;
import com.zj.server.transform.Receiver;
import com.zj.server.utils.ClassUtil;

@Slf4j
public class SimpleDispatcher implements Receiver {

	private Map<Class<?>, Class<?>> courseTab = new ConcurrentHashMap<Class<?>,Class<?>>();
	private ExecutorService mainExecutor = new ThreadPoolExecutor(1, 1, 0L,
			TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(10));
	private TransactionStatisticer statisticer = new TransactionStatisticer();
	private static final Method EMPTY_METHOD;
	private ApplicationContextHelper applicationContextHepler;
	private SimpleCache<Key, Method> bizMethodCache = new SimpleCache<Key, Method>();

	static {

		Method tmp = null;
		try {
			tmp = Key.class.getMethod("hashCode", new Class[0]);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		EMPTY_METHOD = tmp;
	}

	@Override
	public void messageReceived(final Object input) {
		Runnable task = new Runnable() {
			@Override
			public void run() {
				Object message = input;
				Class<?> course = courseTab.get(message.getClass());
				if (null == course) {
					if (log.isErrorEnabled()) {
						log.error("No course class found for ["
										+ message.getClass().getName()
										+ "]. Process stopped.");
					}
					return;
				}
				if(null != statisticer){
					statisticer.incHandledTransactionStart();
				}
				
				//废弃，因为spring加载顺序不定， 这时候，Course 可能为null
//				invokeBizMethod(course, message);
				String classLowerName = ClassUtil.getClassLowerName(course);
				
//				WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//              Object bean0 = currentWebApplicationContext.getBean(classLowerName);				
				Object bean = applicationContextHepler.getBean(classLowerName);
				invokeBizMethod((BusinessCourse) bean, message);
				
				if(null != statisticer){
					statisticer.incHandledTransactionEnd();
				}

			}
		};

		if (this.mainExecutor != null) {
			this.mainExecutor.submit(task);
		} else {
			task.run();
		}

	}

	private Method getBizMethod(final Class<?> courseClass,
			final Class<?> beanClass) {
		Method ret = this.bizMethodCache.get(new Key(courseClass, beanClass),
				new Callable<Method>() {

					@Override
					public Method call() throws Exception {
						Method[] methods = ClassUtil
								.getAllMethodOf(courseClass);
						for (Method method : methods) {
							BizMethod biz = (BizMethod) method
									.getAnnotation(BizMethod.class);
							if (null != biz) {
								Class<?>[] params = method.getParameterTypes();
								if (params.length < 1) {
									if (log.isWarnEnabled()) {
										log.warn("Method ["
												+ method.getName()
												+ "] found but only ["
												+ params.length
												+ "] parameters found, need to be 1.");
									}
								} else if (params[0]
										.isAssignableFrom(beanClass)) {
									return method;
								}
							}
						}
						return EMPTY_METHOD;
					}
				});
		return ret == EMPTY_METHOD ? null : ret;
	}

	private void invokeBizMethod(BusinessCourse course, Object msg) {

		Method bizMethod = getBizMethod(course.getClass(), msg.getClass());
		if (null != bizMethod) {
			try {
				bizMethod.invoke(course, new Object[] { msg });
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
				if (log.isErrorEnabled()) {
					log.error("Invoke biz method [" + bizMethod.getName()
							+ "] failed. ", e);
				}
			}
		} else if (log.isErrorEnabled()) {
			log.error("No biz method found for message ["
					+ msg.getClass().getName() + "]. No process execute.");
		}
	}

	public void setThreads(int threads) {
		mainExecutor = initExecutor(
				Runtime.getRuntime().availableProcessors() * 2, threads, 0L);
	}

	@SuppressWarnings("unused")
	private Class<?> getCourse(Class<?> clazz) {
		return this.courseTab.get(clazz);
	}

	public void setCourses(Collection<Class<?>> courses) {
		for (Class<?> course : courses) {
			Method[] methods = ClassUtil.getAllMethodOf(course);
			for (Method method : methods) {
				BizMethod biz = method.getAnnotation(BizMethod.class);
				if (null != biz) {
					Class<?>[] params = method.getParameterTypes();
					if (params.length >= 1) {
						courseTab.put(params[0], course);
					}
				}
			}
		}
	}

	public ThreadPoolExecutor initExecutor(int corePoolSize,
			int maximumPoolSize, long keepAliveTime) {
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
				keepAliveTime, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(maximumPoolSize),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.AbortPolicy());
	}

	public TransactionStatisticer getStatisticer() {
		return statisticer;
	}

	public void setStatisticer(TransactionStatisticer statisticer) {
		this.statisticer = statisticer;
	}

	private static final class Key {
		private Class<?> courseClass;
		private Class<?> beanClass;

		public Key(Class<?> courseClass, Class<?> beanClass) {
			this.courseClass = courseClass;
			this.beanClass = beanClass;
		}

		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = 31 * result
					+ (this.beanClass == null ? 0 : this.beanClass.hashCode());

			result = 31
					* result
					+ (this.courseClass == null ? 0 : this.courseClass
							.hashCode());

			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Key)) {
				return false;
			}
			Key other = (Key) obj;
			if (this.beanClass == null) {
				if (other.beanClass != null) {
					return false;
				}
			} else if (!this.beanClass.equals(other.beanClass)) {
				return false;
			}
			if (this.courseClass == null) {
				if (other.courseClass != null) {
					return false;
				}
			} else if (!this.courseClass.equals(other.courseClass)) {
				return false;
			}
			return true;
		}
	}

	public void setApplicationContextHepler(
			ApplicationContextHelper applicationContextHepler) {
		this.applicationContextHepler = applicationContextHepler;
	}

}
