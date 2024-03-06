/*
 * Copyright 2002-2018 the original author or authors.
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

import org.springframework.beans.BeansException;

/**
 * 定义一个工厂，在调用时可以返回一个对象实例（可能是共享的或独立的）。
 *
 * <p>该接口通常用于封装通用工厂，该工厂在每次调用时返回某个目标对象的新实例（原型）。
 *
 * <p>该接口类似于 FactoryBean，但后者的实现通常意味着在 BeanFactory 中定义为 SPI 实例，
 * 而此类的实现通常意味着作为 API 提供给其他 bean（通过注入）。
 * 因此，getObject() 方法具有不同的异常处理行为。
 *
 * @author Colin Sampaleanu
 * @since 1.0.2
 * @param <T> the object type
 * @see FactoryBean
 */
@FunctionalInterface
public interface ObjectFactory<T> {

	/**
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）。
	 * @return 结果实例
	 * @throws BeansException 如果出现创建错误
	 */
	T getObject() throws BeansException;

}
