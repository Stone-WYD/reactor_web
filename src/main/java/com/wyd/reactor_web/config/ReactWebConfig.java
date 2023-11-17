package com.wyd.reactor_web.config;

import com.wyd.reactor_web.mvc.invoke.NettyMyMethodInvokeHandler;
import com.wyd.reactor_web.mvc.invoke.argument.binder.SpringMyWebDataBinderFactory;
import com.wyd.reactor_web.mvc.invoke.argument.resolver.MyHandlerMethodArgumentResolverComposite;
import com.wyd.reactor_web.mvc.invoke.interfaces.MyHandlerMethodArgumentResolver;
import com.wyd.reactor_web.mvc.invoke.interfaces.MyWebDataBinderFactory;
import com.wyd.reactor_web.mvc.mhandler.SpringMyMethodHandlerFactory;
import com.wyd.reactor_web.mvc.mhandler.assist.DelayAopOrderBeanFactoryPostProcessor;
import com.wyd.reactor_web.mvc.mhandler.SpringMyMethodParameterFactory;
import com.wyd.reactor_web.mvc.mhandler.assist.MyMethodInvokeGearFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: reactor_web
 * @description: 考虑到一些内容后期要封装成 spring-boot-starter 项目使用，此处使用一个配置类作为 starter 入口
 * @author: Stone
 * @create: 2023-11-13 18:19
 **/
@Configuration
public class ReactWebConfig {


    @Bean
    public DelayAopOrderBeanFactoryPostProcessor delayAopOrderBeanFactoryPostProcessor() {
        return new DelayAopOrderBeanFactoryPostProcessor();
    }

    @Bean
    public SpringMyMethodHandlerFactory springMyMethodHandlerFactory() {
        return new SpringMyMethodHandlerFactory();
    }

    @Bean
    public SpringMyMethodParameterFactory myMethodParameterHandlerFactory() {
        return new SpringMyMethodParameterFactory();
    }

    /**
    * @Description: 方法调用需要的 MyMethodInvokeGear 获取的工厂类
    * @Author: Stone
    * @Date: 2023/11/15
    */
    @Bean
    public MyMethodInvokeGearFactory myMethodInvokeGearFactory(SpringMyMethodHandlerFactory springMyMethodHandlerFactory,
                                                               SpringMyMethodParameterFactory myMethodParameterHandlerFactory) {
        return new MyMethodInvokeGearFactory(springMyMethodHandlerFactory, myMethodParameterHandlerFactory);
    }

    @Bean
    public MyHandlerMethodArgumentResolver argumentResolver() {
        // TODO: 2023/11/16 待加入一些新的参数处理类
        return new MyHandlerMethodArgumentResolverComposite();
    }

    @Bean
    public MyWebDataBinderFactory binderFactory() {
        // TODO: 2023/11/16 待完成
        return new SpringMyWebDataBinderFactory();
    }


    /**
    * @Description: netty handler 使用的方法调用类，类似于 springMVC 中 dispatcherServlet 的 service() 方法
    * @Author: Stone
    * @Date: 2023/11/15
    */
    @Bean
    public NettyMyMethodInvokeHandler nettyMyMethodInvokeHandler(MyMethodInvokeGearFactory myMethodInvokeGearFactory,
                                                                 MyHandlerMethodArgumentResolver argumentResolver,
                                                                 MyWebDataBinderFactory binderFactory) {
        return new NettyMyMethodInvokeHandler(myMethodInvokeGearFactory, argumentResolver, binderFactory);
    }
}
