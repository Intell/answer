//package com.intell.lesson.auth.filter;
//
//import org.apache.shiro.config.Ini;
//import org.apache.shiro.config.Ini.Section;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.FactoryBean;
//
//
///**
// * 应用启动时，根据数据库中的定义，动态创建permission
// *
// *
// */
//public class FilterChainBeanFactory  implements FactoryBean<Section>  {
//
//
//
//	//shiro默认的链接定义
//	private String filterChainDefinitions;
//
//    private String iniResourcePath;
//
//
//    public String getIniResourcePath() {
//        return iniResourcePath;
//    }
//
//    public void setIniResourcePath(String iniResourcePath) {
//        this.iniResourcePath = iniResourcePath;
//    }
//
//    /**
//	 * 通过filterChainDefinitions对默认的链接过滤定义
//	 *
//	 * @param filterChainDefinitions 默认的接过滤定义
//	 */
//	public void setFilterChainDefinitions(String filterChainDefinitions) {
//		this.filterChainDefinitions = filterChainDefinitions;
//	}
//
//
//    @Override
//	public Section getObject() throws BeansException {
//		Ini ini = new Ini();
//        //加载默认的url
//        ini.loadFromPath(iniResourcePath);
//        Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
//        return section;
//	}
//
//	@Override
//	public Class<?> getObjectType() {
//		return Section.class;
//	}
//
//	@Override
//	public boolean isSingleton() {
//		return true;
//	}
//
//
//}
