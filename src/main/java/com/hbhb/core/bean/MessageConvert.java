package com.hbhb.core.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author xiaokang
 * @since 2020-06-19
 */
@Component
public class MessageConvert implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        MessageConvert.beanFactory = beanFactory;
    }

    public static String convert(String code, Object... args) {
        MessageSource messageSource = beanFactory.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
