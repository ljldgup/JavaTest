package ljl.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


//@Component
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("调用了自定义的BeanFactoryPostProcessor " + beanFactory);

		String[] names = beanFactory.getBeanDefinitionNames();
		// 获取了所有的bean名称列表
		for(int i=0; i<names.length; i++){
			String name = names[i];

			BeanDefinition bd = beanFactory.getBeanDefinition(name);
			System.out.println(name + " bean properties: " + bd.getPropertyValues().toString());
			// 本内容只是个demo，打印持有的bean的属性情况
		}
	}
}
