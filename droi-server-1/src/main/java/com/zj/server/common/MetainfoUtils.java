package com.zj.server.common;

import io.netty.handler.codec.http.HttpResponseStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import com.zj.server.anno.BizCourse;
import com.zj.server.anno.SignalCode;
import com.zj.server.exception.LogicException;
import com.zj.server.utils.PackageUtil;

@Slf4j
public class MetainfoUtils {
	
	@Value("${droi.server.MetainfoUtils.package}")
	private String bizMethodpackageName;
	@Value("${droi.server.businessCourse.package}")
	private String coursePackageName;
	
	private DefaultMsgCode2TypeMetainfo typeMetainfo;
	
	private Collection<Class<?>> courses;

	@PostConstruct
	public void init(){
		typeMetainfo = new DefaultMsgCode2TypeMetainfo();
		courses = new ArrayList<Class<?>>();
		ArrayList<String> packages = new ArrayList<String>();
		packages.add(bizMethodpackageName);
		
		createTypeMetainfo(packages);
		packages.remove(bizMethodpackageName);
		packages.add(coursePackageName);
		
		createActions(packages);
	}
	
	public DefaultMsgCode2TypeMetainfo createTypeMetainfo(
			Collection<String> packages) {
		if (null != packages && packages.size() > 0) {
			for (String pkgName : packages) {
				try {
					String[] clsNames = PackageUtil.findClassesInPackage(
							pkgName, null, null);
					for (String clsName : clsNames) {
						try {
							ClassLoader cl = Thread.currentThread()
									.getContextClassLoader();
							if (log.isDebugEnabled()) {
								log.debug("using ClassLoader {" + cl
										+ "} to load Class {" + clsName + "}");
							}
							Class<?> cls = cl.loadClass(clsName);
							SignalCode attr = cls
									.getAnnotation(SignalCode.class);
							if (null != attr) {
								int value = attr.messageCode();
								typeMetainfo.add(value, cls);
								log.info("metainfo: add " + value + ":=>"
										+ cls);
							}
						} catch (ClassNotFoundException e) {
							log.error("createTypeMetainfo: ", e);
							e.printStackTrace();
						}
					}

				} catch (IOException e) {
					log.error("createTypeMetainfo: ", e);
					e.printStackTrace();
				}
			}
		}else{
			throw new LogicException(HttpResponseStatus.EXPECTATION_FAILED.code(), "Expectation Failed of Xip package file path");
		}
		return typeMetainfo;
	}

	/**
	 * 废弃，反射获取的Action 没法注入 serive调用
	 * 待修改
	 * @param packages
	 * @return
	 */
	public Collection<Class<?>> createActions(Collection<String> packages) {
		if (null != packages && packages.size() > 0) {
			for (String pkgName : packages) {
				try {
					String[] clsNames = PackageUtil.findClassesInPackage(
							pkgName, null, null);
					for (String clsName : clsNames) {
						try {
//							ClassLoader cl = Thread.currentThread()
//									.getContextClassLoader();
//							if (log.isDebugEnabled()) {
//								log.debug("using ClassLoader {" + cl
//										+ "} to load Class {" + clsName + "}");
//							}
//							Class<?> cls = cl.loadClass(clsName);
							Class<?> cls = Class.forName(clsName);
							BizCourse action = cls
									.getAnnotation(BizCourse.class);
							if (null != action) {
								courses.add(cls);
								log.info("course : add " + cls);
							}
						} catch (ClassNotFoundException e) {
							log.error("createActions: ", e);
							e.printStackTrace();
						}
					}

				} catch (IOException e) {
					log.error("createActions: ", e);
					e.printStackTrace();
				}
			}
		}else{
			throw new LogicException(HttpResponseStatus.EXPECTATION_FAILED.code(), "Expectation Failed of BusinessCourse package file path");
		}
		return courses;
	}

	public DefaultMsgCode2TypeMetainfo getTypeMetainfo() {
		return typeMetainfo;
	}

	public Collection<Class<?>> getCourses() {
		return courses;
	}
	

}
