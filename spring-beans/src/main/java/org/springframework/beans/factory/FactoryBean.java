/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory;

import org.springframework.lang.Nullable;

/**
 * <p>由 BeanFactory 中使用的对象实现的接口，这些对象本身就是各个对象的工厂。
 * 如果一个 bean 实现了这个接口，它就被用作要公开的对象的工厂，而不是直接作为将自身公开的 bean 实例。
 *
 * <p>注意：实现此接口的 bean 不能用作普通 bean。
 * FactoryBean 以 bean 样式定义，但为 bean 引用 (getObject()) 公开的对象始终是它创建的对象。
 *
 * <p>FactoryBeans 可以支持单例和原型，并且可以按需延迟创建对象或在启动时急切地创建对象。
 * SmartFactoryBean 接口允许公开更细粒度的行为元数据。
 *
 * <p>该接口在框架本身中大量使用，例如 AOP org.springframework.aop.framework.ProxyFactoryBean 或 org.springframework.jndi.JndiObjectFactoryBean。
 * 它也可以用于自定义组件； 然而，这仅对于基础设施代码很常见。
 *
 * <p>FactoryBean 是一个程序化合约。
 * 实现不应依赖注释驱动的注入或其他反射设施。
 * getObjectType() getObject() 调用可能会在引导过程的早期到达，甚至在任何后处理器设置之前。
 * 如果您需要访问其他 bean，请实现 BeanFactoryAware 并以编程方式获取它们。
 *
 * <p>容器只负责管理FactoryBean实例的生命周期，而不负责管理FactoryBean创建的对象的生命周期。
 * 因此，公开的 bean 对象上的 destroy 方法（例如 java.io.Closeable.close()）不会自动调用。
 * 相反，FactoryBean 应该实现 DisposableBean 并将任何此类关闭调用委托给底层对象。
 *
 * <p>最后，FactoryBean对象参与包含BeanFactory的bean创建的同步。
 * 除了 FactoryBean 本身（或类似）内的延迟初始化目的之外，通常不需要内部同步。
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 08.03.2003
 * @param <T> the bean type
 * @see org.springframework.beans.factory.BeanFactory
 * @see org.springframework.aop.framework.ProxyFactoryBean
 * @see org.springframework.jndi.JndiObjectFactoryBean
 */
public interface FactoryBean<T> {

	/**
	 * The name of an attribute that can be
	 * {@link org.springframework.core.AttributeAccessor#setAttribute set} on a
	 * {@link org.springframework.beans.factory.config.BeanDefinition} so that
	 * factory beans can signal their object type when it can't be deduced from
	 * the factory bean class.
	 * <p>可以在 org.springframework.beans.factory.config.BeanDefinition 上设置的属性名称，以便工厂 bean 可以在无法从工厂 bean 类推导其对象类型时发出信号。
	 * @since 5.2
	 */
	String OBJECT_TYPE_ATTRIBUTE = "factoryBeanObjectType";


	/**
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）。
	 * <p>与 BeanFactory 一样，这允许支持 Singleton 和 Prototype 设计模式。
	 * <p>如果该FactoryBean在调用时尚未完全初始化（例如因为涉及循环引用），则抛出相应的FactoryBeanNotInitializedException。
	 * <p>从 Spring 2.0 开始，FactoryBeans 被允许返回 null 对象。 工厂会将此视为正常值来使用； 在这种情况下，它不会再抛出 FactoryBeanNotInitializedException 。
	 * 现在鼓励 FactoryBean 实现自行抛出 FactoryBeanNotInitializedException（视情况而定）。
	 * @return bean 的实例（可以为 null）
	 * @throws Exception in case of creation errors
	 * @see FactoryBeanNotInitializedException
	 */
	@Nullable
	T getObject() throws Exception;

	/**
	 * 返回此 FactoryBean 创建的对象的类型，如果事先未知，则返回 null。
	 * <p>这允许人们在不实例化对象的情况下检查特定类型的 bean，例如自动装配。
	 * <p>对于创建单例对象的实现，此方法应尽可能避免创建单例； 它应该提前估计类型。
	 *  对于原型，建议在这里返回有意义的类型。
	 * <p>This method can be called <i>before</i> this FactoryBean has
	 * been fully initialized. It must not rely on state created during
	 * initialization; of course, it can still use such state if available.
	 * <p>可以在该 FactoryBean 完全初始化之前调用此方法。
	 * 它不能依赖于初始化期间创建的状态； 当然，如果有的话，它仍然可以使用这样的状态。
	 * <p><b>NOTE:</b> Autowiring will simply ignore FactoryBeans that return
	 * {@code null} here. Therefore it is highly recommended to implement
	 * this method properly, using the current state of the FactoryBean.
	 * <p>注意：自动装配将简单地忽略此处返回 null 的 FactoryBean。 因此，强烈建议使用 FactoryBean 的当前状态正确实现此方法。
	 * @return 此 FactoryBean 创建的对象的类型，如果在调用时未知，则为 null
	 * @see ListableBeanFactory#getBeansOfType
	 */
	@Nullable
	Class<?> getObjectType();

	/**
	 * Is the object managed by this factory a singleton? That is,
	 * will {@link #getObject()} always return the same object
	 * (a reference that can be cached)?
	 * <p><b>NOTE:</b> If a FactoryBean indicates to hold a singleton object,
	 * the object returned from {@code getObject()} might get cached
	 * by the owning BeanFactory. Hence, do not return {@code true}
	 * unless the FactoryBean always exposes the same reference.
	 * <p>The singleton status of the FactoryBean itself will generally
	 * be provided by the owning BeanFactory; usually, it has to be
	 * defined as singleton there.
	 * <p><b>NOTE:</b> This method returning {@code false} does not
	 * necessarily indicate that returned objects are independent instances.
	 * An implementation of the extended {@link SmartFactoryBean} interface
	 * may explicitly indicate independent instances through its
	 * {@link SmartFactoryBean#isPrototype()} method. Plain {@link FactoryBean}
	 * implementations which do not implement this extended interface are
	 * simply assumed to always return independent instances if the
	 * {@code isSingleton()} implementation returns {@code false}.
	 * <p>The default implementation returns {@code true}, since a
	 * {@code FactoryBean} typically manages a singleton instance.
	 * @return whether the exposed object is a singleton
	 * @see #getObject()
	 * @see SmartFactoryBean#isPrototype()
	 */
	default boolean isSingleton() {
		return true;
	}

}
