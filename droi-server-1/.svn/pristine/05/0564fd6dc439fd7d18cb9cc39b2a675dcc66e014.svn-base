package com.zj.server.route;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHelper implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

    public ApplicationContextHelper() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName) {
		if(null != applicationContext){
			return (T) applicationContext.getBean(beanName);
		}
		return null;
	}
	
	/**
	 * 国际化使用？
	 * @param key
	 * @return
	 */
	public String getMessage(String key){
		return applicationContext.getMessage(key,null,Locale.getDefault());
	}

	/**
	 * 获取当前环境
	 * @return
	 */
	public String[] getActiveProfiles(){
		return applicationContext.getEnvironment().getActiveProfiles();
	}
	
	// 判断当前环境是否为test/local
    public boolean isTestEnv(){
        String[] activeProfiles = getActiveProfiles();
        if (activeProfiles.length<1){
            return false;
        }
 
        for (String activeProfile : activeProfiles) {
            if (StringUtils.equals(activeProfile,"local")||
                    StringUtils.equals(activeProfile,"dev")){
                return true;
            }
        }
        return false;
    }

}
