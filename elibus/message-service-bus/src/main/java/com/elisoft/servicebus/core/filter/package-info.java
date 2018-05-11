/**
 * 基于Message Header或者Message Payload 过滤一部分消息
 * 位于两个通道之间对于通道间的消息，可以选择行的放行或者丢弃
 * 自定义Filter，只需实现MessageSelector，重载accept方法，入参为Message，其中包含Header和Payload，之后可进行业务处理，返回 true或者false代表，放行或者丢弃。
 * 可配置discard-channel，负责接收被丢弃的消息，有路由的作用。
 * @author JiJie.LianG
 * 2018/5/8
 */
package com.elisoft.servicebus.core.filter;