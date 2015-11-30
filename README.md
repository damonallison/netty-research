# Netty Research #

A simple client / server application using Netty.

## Netty Fundamentals

The goal of netty is to provide a clean, modern, safe, and well designed I/O stack in Java. It abstracts away Java NIO's pain points (threading, eventing). Netty's API encourages "separation of concerns" by providing constructs to abstract pieces of functionality into individual classes called ChannelHandlers.

The fundamental construct in Netty is a channel. A channel is a connection through which data flows. A channel contains a pipeline. A pipeline contains a set of handlers which are invoked in order when data flows through the channel.

Within Netty, all I/O events happen on the Netty event loop. The only time you need to implement thread safety is when you need to execute code from multiple EventLoops concurrently.
## Netty Overview

* Bootstrapping : The startup code that configures a netty listener (server) which accepts I/O connections.
* Future : Netty extended Java's <code>Future</code> class. The built in future is cumbersome - it does not allow you to attach a listener to event completion. You can only check if it's complete or block until completion. Netty's <code>ChannelFuture</code> allows you to attach listeners to a future.
* Channel : The abstraction which represents a connection to a socket, file, or object that is capable of performing I/O operations.
* ChannelHandler : Basic abstraction to handle callbacks in response to a specific channel event (read / write).
* ChannelPipeline : A chain of ChannelHandlers attached to a Channel.

#### Todo

* Create a small protocol ([size][payload]) and exchange messages between the client / server.
* Interacting with the channel from a handler : always through the context?
* Attribute / AttributeKey : Per channel attributes that are associated to a channel through the <code>ChannelContext</code>
* What's a "User Event"?

## ChannelHandler Class Hierarchy ##

The ChannelHandler class hierarchy with Netty 4.1:

<pre>
interface ChannelHandler
    abstract class ChannelHandlerAdapter
    interface ChannelOutboundHandler
        ChannelOutboundHandler
    interface ChannelInboundHandler
        class ChannelInboundHandlerAdapter

</pre>


