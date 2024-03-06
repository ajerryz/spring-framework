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

package org.springframework.context;

/**
 * A common interface defining methods for start/stop lifecycle control.
 * The typical use case for this is to control asynchronous processing.
 * <b>NOTE: This interface does not imply specific auto-startup semantics.
 * Consider implementing {@link SmartLifecycle} for that purpose.</b>
 * <p>定义启动/停止生命周期控制方法的通用接口。 其典型用例是控制异步处理。 注意：此接口并不暗示特定的自动启动语义。 考虑为此目的实施 SmartLifecycle。</p>
 *
 * <p>Can be implemented by both components (typically a Spring bean defined in a
 * Spring context) and containers  (typically a Spring {@link ApplicationContext}
 * itself). Containers will propagate start/stop signals to all components that
 * apply within each container, e.g. for a stop/restart scenario at runtime.
 * <p>可以由组件（通常是在 Spring 上下文中定义的 Spring bean）和容器（通常是 Spring ApplicationContext 本身）来实现。 容器会将启动/停止信号传播到每个容器内应用的所有组件，例如 用于运行时停止/重新启动的场景。</p>
 *
 * <p>Can be used for direct invocations or for management operations via JMX.
 * In the latter case, the {@link org.springframework.jmx.export.MBeanExporter}
 * will typically be defined with an
 * {@link org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler},
 * restricting the visibility of activity-controlled components to the Lifecycle
 * interface.
 * <p>可用于直接调用或通过 JMX 进行管理操作。 在后一种情况下， org.springframework.jmx.export.MBeanExporter 通常使用 org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler 进行定义，从而限制活动控制组件对 Lifecycle 接口的可见性。</p>
 *
 * <p>Note that the present {@code Lifecycle} interface is only supported on
 * <b>top-level singleton beans</b>. On any other component, the {@code Lifecycle}
 * interface will remain undetected and hence ignored. Also, note that the extended
 * {@link SmartLifecycle} interface provides sophisticated integration with the
 * application context's startup and shutdown phases.
 * <p>请注意，当前的 Lifecycle 接口仅在顶级单例 bean 上受支持。 在任何其他组件上，生命周期接口都不会被检测到，因此会被忽略。 另请注意，扩展的 SmartLifecycle 接口提供了与应用程序上下文的启动和关闭阶段的复杂集成。</p>
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see SmartLifecycle
 * @see ConfigurableApplicationContext
 * @see org.springframework.jms.listener.AbstractMessageListenerContainer
 * @see org.springframework.scheduling.quartz.SchedulerFactoryBean
 */
public interface Lifecycle {

	/**
	 * Start this component.
	 * <p>Should not throw an exception if the component is already running.
	 * <p>In the case of a container, this will propagate the start signal to all
	 * components that apply.
	 * <p>启动该组件。
	 * 如果组件已经在运行，则不应引发异常。
	 * 对于容器，这会将启动信号传播到所有适用的组件。</p>
	 * @see SmartLifecycle#isAutoStartup()
	 */
	void start();

	/**
	 * Stop this component, typically in a synchronous fashion, such that the component is
	 * fully stopped upon return of this method. Consider implementing {@link SmartLifecycle}
	 * and its {@code stop(Runnable)} variant when asynchronous stop behavior is necessary.
	 * <p>通常以同步方式停止此组件，以便在返回此方法时完全停止该组件。 当需要异步停止行为时，请考虑实现 SmartLifecycle 及其 stop(Runnable) 变体。</p>
	 *
	 * <p>Note that this stop notification is not guaranteed to come before destruction:
	 * On regular shutdown, {@code Lifecycle} beans will first receive a stop notification
	 * before the general destruction callbacks are being propagated; however, on hot
	 * refresh during a context's lifetime or on aborted refresh attempts, a given bean's
	 * destroy method will be called without any consideration of stop signals upfront.
	 * <p>请注意，不保证在销毁之前发出此停止通知：在定期关闭时，生命周期 bean 将在传播一般销毁回调之前首先收到停止通知； 但是，在上下文生命周期内进行热刷新或中止刷新尝试时，将调用给定 bean 的 destroy 方法，而不预先考虑停止信号。</p>
	 *
	 * <p>Should not throw an exception if the component is not running (not started yet).
	 * <p>如果组件未运行（尚未启动），则不应引发异常。</p>
	 *
	 * <p>In the case of a container, this will propagate the stop signal to all components
	 * that apply.
	 * <p>对于容器，这会将停止信号传播到所有适用的组件。</p>
	 * @see SmartLifecycle#stop(Runnable)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	void stop();

	/**
	 * Check whether this component is currently running.
	 * <p>In the case of a container, this will return {@code true} only if <i>all</i>
	 * components that apply are currently running.
	 * <p>检查该组件当前是否正在运行。
	 * 对于容器，仅当所有适用的组件当前正在运行时才会返回 true。</p>
	 * @return whether the component is currently running
	 */
	boolean isRunning();

}
