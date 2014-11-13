// Compiled by ClojureScript 0.0-2311
goog.provide('cljs.core.async');
goog.require('cljs.core');
goog.require('cljs.core.async.impl.channels');
goog.require('cljs.core.async.impl.dispatch');
goog.require('cljs.core.async.impl.ioc_helpers');
goog.require('cljs.core.async.impl.protocols');
goog.require('cljs.core.async.impl.channels');
goog.require('cljs.core.async.impl.buffers');
goog.require('cljs.core.async.impl.protocols');
goog.require('cljs.core.async.impl.timers');
goog.require('cljs.core.async.impl.dispatch');
goog.require('cljs.core.async.impl.ioc_helpers');
goog.require('cljs.core.async.impl.buffers');
goog.require('cljs.core.async.impl.timers');
cljs.core.async.fn_handler = (function fn_handler(f){if(typeof cljs.core.async.t10451 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t10451 = (function (f,fn_handler,meta10452){
this.f = f;
this.fn_handler = fn_handler;
this.meta10452 = meta10452;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t10451.cljs$lang$type = true;
cljs.core.async.t10451.cljs$lang$ctorStr = "cljs.core.async/t10451";
cljs.core.async.t10451.cljs$lang$ctorPrWriter = (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t10451");
});
cljs.core.async.t10451.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t10451.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return true;
});
cljs.core.async.t10451.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return self__.f;
});
cljs.core.async.t10451.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_10453){var self__ = this;
var _10453__$1 = this;return self__.meta10452;
});
cljs.core.async.t10451.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_10453,meta10452__$1){var self__ = this;
var _10453__$1 = this;return (new cljs.core.async.t10451(self__.f,self__.fn_handler,meta10452__$1));
});
cljs.core.async.__GT_t10451 = (function __GT_t10451(f__$1,fn_handler__$1,meta10452){return (new cljs.core.async.t10451(f__$1,fn_handler__$1,meta10452));
});
}
return (new cljs.core.async.t10451(f,fn_handler,null));
});
/**
* Returns a fixed buffer of size n. When full, puts will block/park.
*/
cljs.core.async.buffer = (function buffer(n){return cljs.core.async.impl.buffers.fixed_buffer.call(null,n);
});
/**
* Returns a buffer of size n. When full, puts will complete but
* val will be dropped (no transfer).
*/
cljs.core.async.dropping_buffer = (function dropping_buffer(n){return cljs.core.async.impl.buffers.dropping_buffer.call(null,n);
});
/**
* Returns a buffer of size n. When full, puts will complete, and be
* buffered, but oldest elements in buffer will be dropped (not
* transferred).
*/
cljs.core.async.sliding_buffer = (function sliding_buffer(n){return cljs.core.async.impl.buffers.sliding_buffer.call(null,n);
});
/**
* Returns true if a channel created with buff will never block. That is to say,
* puts into this buffer will never cause the buffer to be full.
*/
cljs.core.async.unblocking_buffer_QMARK_ = (function unblocking_buffer_QMARK_(buff){var G__10455 = buff;if(G__10455)
{var bit__4203__auto__ = null;if(cljs.core.truth_((function (){var or__3553__auto__ = bit__4203__auto__;if(cljs.core.truth_(or__3553__auto__))
{return or__3553__auto__;
} else
{return G__10455.cljs$core$async$impl$protocols$UnblockingBuffer$;
}
})()))
{return true;
} else
{if((!G__10455.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,cljs.core.async.impl.protocols.UnblockingBuffer,G__10455);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,cljs.core.async.impl.protocols.UnblockingBuffer,G__10455);
}
});
/**
* Creates a channel with an optional buffer, an optional transducer (like (map f),
* (filter p) etc or a composition thereof), and an optional exception handler.
* If buf-or-n is a number, will create and use a fixed buffer of that size. If a
* transducer is supplied a buffer must be specified. ex-handler must be a
* fn of one argument - if an exception occurs during transformation it will be called
* with the thrown value as an argument, and any non-nil return value will be placed
* in the channel.
*/
cljs.core.async.chan = (function() {
var chan = null;
var chan__0 = (function (){return chan.call(null,null);
});
var chan__1 = (function (buf_or_n){return chan.call(null,buf_or_n,null,null);
});
var chan__2 = (function (buf_or_n,xform){return chan.call(null,buf_or_n,xform,null);
});
var chan__3 = (function (buf_or_n,xform,ex_handler){var buf_or_n__$1 = ((cljs.core._EQ_.call(null,buf_or_n,(0)))?null:buf_or_n);if(cljs.core.truth_(xform))
{if(cljs.core.truth_(buf_or_n__$1))
{} else
{throw (new Error(("Assert failed: buffer must be supplied when transducer is\n"+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.call(null,new cljs.core.Symbol(null,"buf-or-n","buf-or-n",-1646815050,null))))));
}
} else
{}
return cljs.core.async.impl.channels.chan.call(null,((typeof buf_or_n__$1 === 'number')?cljs.core.async.buffer.call(null,buf_or_n__$1):buf_or_n__$1),xform,ex_handler);
});
chan = function(buf_or_n,xform,ex_handler){
switch(arguments.length){
case 0:
return chan__0.call(this);
case 1:
return chan__1.call(this,buf_or_n);
case 2:
return chan__2.call(this,buf_or_n,xform);
case 3:
return chan__3.call(this,buf_or_n,xform,ex_handler);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
chan.cljs$core$IFn$_invoke$arity$0 = chan__0;
chan.cljs$core$IFn$_invoke$arity$1 = chan__1;
chan.cljs$core$IFn$_invoke$arity$2 = chan__2;
chan.cljs$core$IFn$_invoke$arity$3 = chan__3;
return chan;
})()
;
/**
* Returns a channel that will close after msecs
*/
cljs.core.async.timeout = (function timeout(msecs){return cljs.core.async.impl.timers.timeout.call(null,msecs);
});
/**
* takes a val from port. Must be called inside a (go ...) block. Will
* return nil if closed. Will park if nothing is available.
* Returns true unless port is already closed
*/
cljs.core.async._LT__BANG_ = (function _LT__BANG_(port){throw (new Error("<! used not in (go ...) block"));
});
/**
* Asynchronously takes a val from port, passing to fn1. Will pass nil
* if closed. If on-caller? (default true) is true, and value is
* immediately available, will call fn1 on calling thread.
* Returns nil.
*/
cljs.core.async.take_BANG_ = (function() {
var take_BANG_ = null;
var take_BANG___2 = (function (port,fn1){return take_BANG_.call(null,port,fn1,true);
});
var take_BANG___3 = (function (port,fn1,on_caller_QMARK_){var ret = cljs.core.async.impl.protocols.take_BANG_.call(null,port,cljs.core.async.fn_handler.call(null,fn1));if(cljs.core.truth_(ret))
{var val_10456 = cljs.core.deref.call(null,ret);if(cljs.core.truth_(on_caller_QMARK_))
{fn1.call(null,val_10456);
} else
{cljs.core.async.impl.dispatch.run.call(null,((function (val_10456,ret){
return (function (){return fn1.call(null,val_10456);
});})(val_10456,ret))
);
}
} else
{}
return null;
});
take_BANG_ = function(port,fn1,on_caller_QMARK_){
switch(arguments.length){
case 2:
return take_BANG___2.call(this,port,fn1);
case 3:
return take_BANG___3.call(this,port,fn1,on_caller_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
take_BANG_.cljs$core$IFn$_invoke$arity$2 = take_BANG___2;
take_BANG_.cljs$core$IFn$_invoke$arity$3 = take_BANG___3;
return take_BANG_;
})()
;
cljs.core.async.nop = (function nop(_){return null;
});
cljs.core.async.fhnop = cljs.core.async.fn_handler.call(null,cljs.core.async.nop);
/**
* puts a val into port. nil values are not allowed. Must be called
* inside a (go ...) block. Will park if no buffer space is available.
* Returns true unless port is already closed.
*/
cljs.core.async._GT__BANG_ = (function _GT__BANG_(port,val){throw (new Error(">! used not in (go ...) block"));
});
/**
* Asynchronously puts a val into port, calling fn0 (if supplied) when
* complete. nil values are not allowed. Will throw if closed. If
* on-caller? (default true) is true, and the put is immediately
* accepted, will call fn0 on calling thread.  Returns nil.
*/
cljs.core.async.put_BANG_ = (function() {
var put_BANG_ = null;
var put_BANG___2 = (function (port,val){var temp__4124__auto__ = cljs.core.async.impl.protocols.put_BANG_.call(null,port,val,cljs.core.async.fhnop);if(cljs.core.truth_(temp__4124__auto__))
{var ret = temp__4124__auto__;return cljs.core.deref.call(null,ret);
} else
{return true;
}
});
var put_BANG___3 = (function (port,val,fn1){return put_BANG_.call(null,port,val,fn1,true);
});
var put_BANG___4 = (function (port,val,fn1,on_caller_QMARK_){var temp__4124__auto__ = cljs.core.async.impl.protocols.put_BANG_.call(null,port,val,cljs.core.async.fn_handler.call(null,fn1));if(cljs.core.truth_(temp__4124__auto__))
{var retb = temp__4124__auto__;var ret = cljs.core.deref.call(null,retb);if(cljs.core.truth_(on_caller_QMARK_))
{fn1.call(null,ret);
} else
{cljs.core.async.impl.dispatch.run.call(null,((function (ret,retb,temp__4124__auto__){
return (function (){return fn1.call(null,ret);
});})(ret,retb,temp__4124__auto__))
);
}
return ret;
} else
{return true;
}
});
put_BANG_ = function(port,val,fn1,on_caller_QMARK_){
switch(arguments.length){
case 2:
return put_BANG___2.call(this,port,val);
case 3:
return put_BANG___3.call(this,port,val,fn1);
case 4:
return put_BANG___4.call(this,port,val,fn1,on_caller_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
put_BANG_.cljs$core$IFn$_invoke$arity$2 = put_BANG___2;
put_BANG_.cljs$core$IFn$_invoke$arity$3 = put_BANG___3;
put_BANG_.cljs$core$IFn$_invoke$arity$4 = put_BANG___4;
return put_BANG_;
})()
;
cljs.core.async.close_BANG_ = (function close_BANG_(port){return cljs.core.async.impl.protocols.close_BANG_.call(null,port);
});
cljs.core.async.random_array = (function random_array(n){var a = (new Array(n));var n__4409__auto___10457 = n;var x_10458 = (0);while(true){
if((x_10458 < n__4409__auto___10457))
{(a[x_10458] = (0));
{
var G__10459 = (x_10458 + (1));
x_10458 = G__10459;
continue;
}
} else
{}
break;
}
var i = (1);while(true){
if(cljs.core._EQ_.call(null,i,n))
{return a;
} else
{var j = cljs.core.rand_int.call(null,i);(a[i] = (a[j]));
(a[j] = i);
{
var G__10460 = (i + (1));
i = G__10460;
continue;
}
}
break;
}
});
cljs.core.async.alt_flag = (function alt_flag(){var flag = cljs.core.atom.call(null,true);if(typeof cljs.core.async.t10464 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t10464 = (function (flag,alt_flag,meta10465){
this.flag = flag;
this.alt_flag = alt_flag;
this.meta10465 = meta10465;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t10464.cljs$lang$type = true;
cljs.core.async.t10464.cljs$lang$ctorStr = "cljs.core.async/t10464";
cljs.core.async.t10464.cljs$lang$ctorPrWriter = ((function (flag){
return (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t10464");
});})(flag))
;
cljs.core.async.t10464.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t10464.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = ((function (flag){
return (function (_){var self__ = this;
var ___$1 = this;return cljs.core.deref.call(null,self__.flag);
});})(flag))
;
cljs.core.async.t10464.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = ((function (flag){
return (function (_){var self__ = this;
var ___$1 = this;cljs.core.reset_BANG_.call(null,self__.flag,null);
return true;
});})(flag))
;
cljs.core.async.t10464.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (flag){
return (function (_10466){var self__ = this;
var _10466__$1 = this;return self__.meta10465;
});})(flag))
;
cljs.core.async.t10464.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (flag){
return (function (_10466,meta10465__$1){var self__ = this;
var _10466__$1 = this;return (new cljs.core.async.t10464(self__.flag,self__.alt_flag,meta10465__$1));
});})(flag))
;
cljs.core.async.__GT_t10464 = ((function (flag){
return (function __GT_t10464(flag__$1,alt_flag__$1,meta10465){return (new cljs.core.async.t10464(flag__$1,alt_flag__$1,meta10465));
});})(flag))
;
}
return (new cljs.core.async.t10464(flag,alt_flag,null));
});
cljs.core.async.alt_handler = (function alt_handler(flag,cb){if(typeof cljs.core.async.t10470 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t10470 = (function (cb,flag,alt_handler,meta10471){
this.cb = cb;
this.flag = flag;
this.alt_handler = alt_handler;
this.meta10471 = meta10471;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t10470.cljs$lang$type = true;
cljs.core.async.t10470.cljs$lang$ctorStr = "cljs.core.async/t10470";
cljs.core.async.t10470.cljs$lang$ctorPrWriter = (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t10470");
});
cljs.core.async.t10470.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t10470.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.active_QMARK_.call(null,self__.flag);
});
cljs.core.async.t10470.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){var self__ = this;
var ___$1 = this;cljs.core.async.impl.protocols.commit.call(null,self__.flag);
return self__.cb;
});
cljs.core.async.t10470.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_10472){var self__ = this;
var _10472__$1 = this;return self__.meta10471;
});
cljs.core.async.t10470.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_10472,meta10471__$1){var self__ = this;
var _10472__$1 = this;return (new cljs.core.async.t10470(self__.cb,self__.flag,self__.alt_handler,meta10471__$1));
});
cljs.core.async.__GT_t10470 = (function __GT_t10470(cb__$1,flag__$1,alt_handler__$1,meta10471){return (new cljs.core.async.t10470(cb__$1,flag__$1,alt_handler__$1,meta10471));
});
}
return (new cljs.core.async.t10470(cb,flag,alt_handler,null));
});
/**
* returns derefable [val port] if immediate, nil if enqueued
*/
cljs.core.async.do_alts = (function do_alts(fret,ports,opts){var flag = cljs.core.async.alt_flag.call(null);var n = cljs.core.count.call(null,ports);var idxs = cljs.core.async.random_array.call(null,n);var priority = new cljs.core.Keyword(null,"priority","priority",1431093715).cljs$core$IFn$_invoke$arity$1(opts);var ret = (function (){var i = (0);while(true){
if((i < n))
{var idx = (cljs.core.truth_(priority)?i:(idxs[i]));var port = cljs.core.nth.call(null,ports,idx);var wport = ((cljs.core.vector_QMARK_.call(null,port))?port.call(null,(0)):null);var vbox = (cljs.core.truth_(wport)?(function (){var val = port.call(null,(1));return cljs.core.async.impl.protocols.put_BANG_.call(null,wport,val,cljs.core.async.alt_handler.call(null,flag,((function (i,val,idx,port,wport,flag,n,idxs,priority){
return (function (p1__10473_SHARP_){return fret.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__10473_SHARP_,wport], null));
});})(i,val,idx,port,wport,flag,n,idxs,priority))
));
})():cljs.core.async.impl.protocols.take_BANG_.call(null,port,cljs.core.async.alt_handler.call(null,flag,((function (i,idx,port,wport,flag,n,idxs,priority){
return (function (p1__10474_SHARP_){return fret.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__10474_SHARP_,port], null));
});})(i,idx,port,wport,flag,n,idxs,priority))
)));if(cljs.core.truth_(vbox))
{return cljs.core.async.impl.channels.box.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.deref.call(null,vbox),(function (){var or__3553__auto__ = wport;if(cljs.core.truth_(or__3553__auto__))
{return or__3553__auto__;
} else
{return port;
}
})()], null));
} else
{{
var G__10475 = (i + (1));
i = G__10475;
continue;
}
}
} else
{return null;
}
break;
}
})();var or__3553__auto__ = ret;if(cljs.core.truth_(or__3553__auto__))
{return or__3553__auto__;
} else
{if(cljs.core.contains_QMARK_.call(null,opts,new cljs.core.Keyword(null,"default","default",-1987822328)))
{var temp__4126__auto__ = (function (){var and__3541__auto__ = cljs.core.async.impl.protocols.active_QMARK_.call(null,flag);if(cljs.core.truth_(and__3541__auto__))
{return cljs.core.async.impl.protocols.commit.call(null,flag);
} else
{return and__3541__auto__;
}
})();if(cljs.core.truth_(temp__4126__auto__))
{var got = temp__4126__auto__;return cljs.core.async.impl.channels.box.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"default","default",-1987822328).cljs$core$IFn$_invoke$arity$1(opts),new cljs.core.Keyword(null,"default","default",-1987822328)], null));
} else
{return null;
}
} else
{return null;
}
}
});
/**
* Completes at most one of several channel operations. Must be called
* inside a (go ...) block. ports is a vector of channel endpoints,
* which can be either a channel to take from or a vector of
* [channel-to-put-to val-to-put], in any combination. Takes will be
* made as if by <!, and puts will be made as if by >!. Unless
* the :priority option is true, if more than one port operation is
* ready a non-deterministic choice will be made. If no operation is
* ready and a :default value is supplied, [default-val :default] will
* be returned, otherwise alts! will park until the first operation to
* become ready completes. Returns [val port] of the completed
* operation, where val is the value taken for takes, and a
* boolean (true unless already closed, as per put!) for puts.
* 
* opts are passed as :key val ... Supported options:
* 
* :default val - the value to use if none of the operations are immediately ready
* :priority true - (default nil) when true, the operations will be tried in order.
* 
* Note: there is no guarantee that the port exps or val exprs will be
* used, nor in what order should they be, so they should not be
* depended upon for side effects.
* @param {...*} var_args
*/
cljs.core.async.alts_BANG_ = (function() { 
var alts_BANG___delegate = function (ports,p__10476){var map__10478 = p__10476;var map__10478__$1 = ((cljs.core.seq_QMARK_.call(null,map__10478))?cljs.core.apply.call(null,cljs.core.hash_map,map__10478):map__10478);var opts = map__10478__$1;throw (new Error("alts! used not in (go ...) block"));
};
var alts_BANG_ = function (ports,var_args){
var p__10476 = null;if (arguments.length > 1) {
  p__10476 = cljs.core.array_seq(Array.prototype.slice.call(arguments, 1),0);} 
return alts_BANG___delegate.call(this,ports,p__10476);};
alts_BANG_.cljs$lang$maxFixedArity = 1;
alts_BANG_.cljs$lang$applyTo = (function (arglist__10479){
var ports = cljs.core.first(arglist__10479);
var p__10476 = cljs.core.rest(arglist__10479);
return alts_BANG___delegate(ports,p__10476);
});
alts_BANG_.cljs$core$IFn$_invoke$arity$variadic = alts_BANG___delegate;
return alts_BANG_;
})()
;
/**
* Takes elements from the from channel and supplies them to the to
* channel. By default, the to channel will be closed when the from
* channel closes, but can be determined by the close?  parameter. Will
* stop consuming the from channel if the to channel closes
*/
cljs.core.async.pipe = (function() {
var pipe = null;
var pipe__2 = (function (from,to){return pipe.call(null,from,to,true);
});
var pipe__3 = (function (from,to,close_QMARK_){var c__5717__auto___10574 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___10574){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___10574){
return (function (state_10550){var state_val_10551 = (state_10550[(1)]);if((state_val_10551 === (7)))
{var inst_10546 = (state_10550[(2)]);var state_10550__$1 = state_10550;var statearr_10552_10575 = state_10550__$1;(statearr_10552_10575[(2)] = inst_10546);
(statearr_10552_10575[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (1)))
{var state_10550__$1 = state_10550;var statearr_10553_10576 = state_10550__$1;(statearr_10553_10576[(2)] = null);
(statearr_10553_10576[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (4)))
{var inst_10529 = (state_10550[(7)]);var inst_10529__$1 = (state_10550[(2)]);var inst_10530 = (inst_10529__$1 == null);var state_10550__$1 = (function (){var statearr_10554 = state_10550;(statearr_10554[(7)] = inst_10529__$1);
return statearr_10554;
})();if(cljs.core.truth_(inst_10530))
{var statearr_10555_10577 = state_10550__$1;(statearr_10555_10577[(1)] = (5));
} else
{var statearr_10556_10578 = state_10550__$1;(statearr_10556_10578[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (13)))
{var state_10550__$1 = state_10550;var statearr_10557_10579 = state_10550__$1;(statearr_10557_10579[(2)] = null);
(statearr_10557_10579[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (6)))
{var inst_10529 = (state_10550[(7)]);var state_10550__$1 = state_10550;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_10550__$1,(11),to,inst_10529);
} else
{if((state_val_10551 === (3)))
{var inst_10548 = (state_10550[(2)]);var state_10550__$1 = state_10550;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_10550__$1,inst_10548);
} else
{if((state_val_10551 === (12)))
{var state_10550__$1 = state_10550;var statearr_10558_10580 = state_10550__$1;(statearr_10558_10580[(2)] = null);
(statearr_10558_10580[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (2)))
{var state_10550__$1 = state_10550;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10550__$1,(4),from);
} else
{if((state_val_10551 === (11)))
{var inst_10539 = (state_10550[(2)]);var state_10550__$1 = state_10550;if(cljs.core.truth_(inst_10539))
{var statearr_10559_10581 = state_10550__$1;(statearr_10559_10581[(1)] = (12));
} else
{var statearr_10560_10582 = state_10550__$1;(statearr_10560_10582[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (9)))
{var state_10550__$1 = state_10550;var statearr_10561_10583 = state_10550__$1;(statearr_10561_10583[(2)] = null);
(statearr_10561_10583[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (5)))
{var state_10550__$1 = state_10550;if(cljs.core.truth_(close_QMARK_))
{var statearr_10562_10584 = state_10550__$1;(statearr_10562_10584[(1)] = (8));
} else
{var statearr_10563_10585 = state_10550__$1;(statearr_10563_10585[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (14)))
{var inst_10544 = (state_10550[(2)]);var state_10550__$1 = state_10550;var statearr_10564_10586 = state_10550__$1;(statearr_10564_10586[(2)] = inst_10544);
(statearr_10564_10586[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (10)))
{var inst_10536 = (state_10550[(2)]);var state_10550__$1 = state_10550;var statearr_10565_10587 = state_10550__$1;(statearr_10565_10587[(2)] = inst_10536);
(statearr_10565_10587[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10551 === (8)))
{var inst_10533 = cljs.core.async.close_BANG_.call(null,to);var state_10550__$1 = state_10550;var statearr_10566_10588 = state_10550__$1;(statearr_10566_10588[(2)] = inst_10533);
(statearr_10566_10588[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___10574))
;return ((function (switch__5702__auto__,c__5717__auto___10574){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_10570 = [null,null,null,null,null,null,null,null];(statearr_10570[(0)] = state_machine__5703__auto__);
(statearr_10570[(1)] = (1));
return statearr_10570;
});
var state_machine__5703__auto____1 = (function (state_10550){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_10550);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e10571){if((e10571 instanceof Object))
{var ex__5706__auto__ = e10571;var statearr_10572_10589 = state_10550;(statearr_10572_10589[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_10550);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e10571;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__10590 = state_10550;
state_10550 = G__10590;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_10550){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_10550);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___10574))
})();var state__5719__auto__ = (function (){var statearr_10573 = f__5718__auto__.call(null);(statearr_10573[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___10574);
return statearr_10573;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___10574))
);
return to;
});
pipe = function(from,to,close_QMARK_){
switch(arguments.length){
case 2:
return pipe__2.call(this,from,to);
case 3:
return pipe__3.call(this,from,to,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
pipe.cljs$core$IFn$_invoke$arity$2 = pipe__2;
pipe.cljs$core$IFn$_invoke$arity$3 = pipe__3;
return pipe;
})()
;
cljs.core.async.pipeline_STAR_ = (function pipeline_STAR_(n,to,xf,from,close_QMARK_,ex_handler,type){if((n > (0)))
{} else
{throw (new Error(("Assert failed: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.call(null,cljs.core.list(new cljs.core.Symbol(null,"pos?","pos?",-244377722,null),new cljs.core.Symbol(null,"n","n",-2092305744,null)))))));
}
var jobs = cljs.core.async.chan.call(null,n);var results = cljs.core.async.chan.call(null,n);var process = ((function (jobs,results){
return (function (p__10774){var vec__10775 = p__10774;var v = cljs.core.nth.call(null,vec__10775,(0),null);var p = cljs.core.nth.call(null,vec__10775,(1),null);var job = vec__10775;if((job == null))
{cljs.core.async.close_BANG_.call(null,results);
return null;
} else
{var res = cljs.core.async.chan.call(null,(1),xf,ex_handler);var c__5717__auto___10957 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___10957,res,vec__10775,v,p,job,jobs,results){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___10957,res,vec__10775,v,p,job,jobs,results){
return (function (state_10780){var state_val_10781 = (state_10780[(1)]);if((state_val_10781 === (2)))
{var inst_10777 = (state_10780[(2)]);var inst_10778 = cljs.core.async.close_BANG_.call(null,res);var state_10780__$1 = (function (){var statearr_10782 = state_10780;(statearr_10782[(7)] = inst_10777);
return statearr_10782;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_10780__$1,inst_10778);
} else
{if((state_val_10781 === (1)))
{var state_10780__$1 = state_10780;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_10780__$1,(2),res,v);
} else
{return null;
}
}
});})(c__5717__auto___10957,res,vec__10775,v,p,job,jobs,results))
;return ((function (switch__5702__auto__,c__5717__auto___10957,res,vec__10775,v,p,job,jobs,results){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_10786 = [null,null,null,null,null,null,null,null];(statearr_10786[(0)] = state_machine__5703__auto__);
(statearr_10786[(1)] = (1));
return statearr_10786;
});
var state_machine__5703__auto____1 = (function (state_10780){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_10780);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e10787){if((e10787 instanceof Object))
{var ex__5706__auto__ = e10787;var statearr_10788_10958 = state_10780;(statearr_10788_10958[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_10780);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e10787;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__10959 = state_10780;
state_10780 = G__10959;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_10780){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_10780);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___10957,res,vec__10775,v,p,job,jobs,results))
})();var state__5719__auto__ = (function (){var statearr_10789 = f__5718__auto__.call(null);(statearr_10789[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___10957);
return statearr_10789;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___10957,res,vec__10775,v,p,job,jobs,results))
);
cljs.core.async.put_BANG_.call(null,p,res);
return true;
}
});})(jobs,results))
;var async = ((function (jobs,results,process){
return (function (p__10790){var vec__10791 = p__10790;var v = cljs.core.nth.call(null,vec__10791,(0),null);var p = cljs.core.nth.call(null,vec__10791,(1),null);var job = vec__10791;if((job == null))
{cljs.core.async.close_BANG_.call(null,results);
return null;
} else
{var res = cljs.core.async.chan.call(null,(1));xf.call(null,v,res);
cljs.core.async.put_BANG_.call(null,p,res);
return true;
}
});})(jobs,results,process))
;var n__4409__auto___10960 = n;var __10961 = (0);while(true){
if((__10961 < n__4409__auto___10960))
{var G__10792_10962 = (((type instanceof cljs.core.Keyword))?type.fqn:null);switch (G__10792_10962) {
case "async":
var c__5717__auto___10964 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (__10961,c__5717__auto___10964,G__10792_10962,n__4409__auto___10960,jobs,results,process,async){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (__10961,c__5717__auto___10964,G__10792_10962,n__4409__auto___10960,jobs,results,process,async){
return (function (state_10805){var state_val_10806 = (state_10805[(1)]);if((state_val_10806 === (7)))
{var inst_10801 = (state_10805[(2)]);var state_10805__$1 = state_10805;var statearr_10807_10965 = state_10805__$1;(statearr_10807_10965[(2)] = inst_10801);
(statearr_10807_10965[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10806 === (6)))
{var state_10805__$1 = state_10805;var statearr_10808_10966 = state_10805__$1;(statearr_10808_10966[(2)] = null);
(statearr_10808_10966[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10806 === (5)))
{var state_10805__$1 = state_10805;var statearr_10809_10967 = state_10805__$1;(statearr_10809_10967[(2)] = null);
(statearr_10809_10967[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10806 === (4)))
{var inst_10795 = (state_10805[(2)]);var inst_10796 = async.call(null,inst_10795);var state_10805__$1 = state_10805;if(cljs.core.truth_(inst_10796))
{var statearr_10810_10968 = state_10805__$1;(statearr_10810_10968[(1)] = (5));
} else
{var statearr_10811_10969 = state_10805__$1;(statearr_10811_10969[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10806 === (3)))
{var inst_10803 = (state_10805[(2)]);var state_10805__$1 = state_10805;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_10805__$1,inst_10803);
} else
{if((state_val_10806 === (2)))
{var state_10805__$1 = state_10805;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10805__$1,(4),jobs);
} else
{if((state_val_10806 === (1)))
{var state_10805__$1 = state_10805;var statearr_10812_10970 = state_10805__$1;(statearr_10812_10970[(2)] = null);
(statearr_10812_10970[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
});})(__10961,c__5717__auto___10964,G__10792_10962,n__4409__auto___10960,jobs,results,process,async))
;return ((function (__10961,switch__5702__auto__,c__5717__auto___10964,G__10792_10962,n__4409__auto___10960,jobs,results,process,async){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_10816 = [null,null,null,null,null,null,null];(statearr_10816[(0)] = state_machine__5703__auto__);
(statearr_10816[(1)] = (1));
return statearr_10816;
});
var state_machine__5703__auto____1 = (function (state_10805){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_10805);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e10817){if((e10817 instanceof Object))
{var ex__5706__auto__ = e10817;var statearr_10818_10971 = state_10805;(statearr_10818_10971[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_10805);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e10817;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__10972 = state_10805;
state_10805 = G__10972;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_10805){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_10805);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(__10961,switch__5702__auto__,c__5717__auto___10964,G__10792_10962,n__4409__auto___10960,jobs,results,process,async))
})();var state__5719__auto__ = (function (){var statearr_10819 = f__5718__auto__.call(null);(statearr_10819[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___10964);
return statearr_10819;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(__10961,c__5717__auto___10964,G__10792_10962,n__4409__auto___10960,jobs,results,process,async))
);

break;
case "compute":
var c__5717__auto___10973 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (__10961,c__5717__auto___10973,G__10792_10962,n__4409__auto___10960,jobs,results,process,async){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (__10961,c__5717__auto___10973,G__10792_10962,n__4409__auto___10960,jobs,results,process,async){
return (function (state_10832){var state_val_10833 = (state_10832[(1)]);if((state_val_10833 === (7)))
{var inst_10828 = (state_10832[(2)]);var state_10832__$1 = state_10832;var statearr_10834_10974 = state_10832__$1;(statearr_10834_10974[(2)] = inst_10828);
(statearr_10834_10974[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10833 === (6)))
{var state_10832__$1 = state_10832;var statearr_10835_10975 = state_10832__$1;(statearr_10835_10975[(2)] = null);
(statearr_10835_10975[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10833 === (5)))
{var state_10832__$1 = state_10832;var statearr_10836_10976 = state_10832__$1;(statearr_10836_10976[(2)] = null);
(statearr_10836_10976[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10833 === (4)))
{var inst_10822 = (state_10832[(2)]);var inst_10823 = process.call(null,inst_10822);var state_10832__$1 = state_10832;if(cljs.core.truth_(inst_10823))
{var statearr_10837_10977 = state_10832__$1;(statearr_10837_10977[(1)] = (5));
} else
{var statearr_10838_10978 = state_10832__$1;(statearr_10838_10978[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10833 === (3)))
{var inst_10830 = (state_10832[(2)]);var state_10832__$1 = state_10832;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_10832__$1,inst_10830);
} else
{if((state_val_10833 === (2)))
{var state_10832__$1 = state_10832;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10832__$1,(4),jobs);
} else
{if((state_val_10833 === (1)))
{var state_10832__$1 = state_10832;var statearr_10839_10979 = state_10832__$1;(statearr_10839_10979[(2)] = null);
(statearr_10839_10979[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
});})(__10961,c__5717__auto___10973,G__10792_10962,n__4409__auto___10960,jobs,results,process,async))
;return ((function (__10961,switch__5702__auto__,c__5717__auto___10973,G__10792_10962,n__4409__auto___10960,jobs,results,process,async){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_10843 = [null,null,null,null,null,null,null];(statearr_10843[(0)] = state_machine__5703__auto__);
(statearr_10843[(1)] = (1));
return statearr_10843;
});
var state_machine__5703__auto____1 = (function (state_10832){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_10832);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e10844){if((e10844 instanceof Object))
{var ex__5706__auto__ = e10844;var statearr_10845_10980 = state_10832;(statearr_10845_10980[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_10832);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e10844;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__10981 = state_10832;
state_10832 = G__10981;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_10832){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_10832);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(__10961,switch__5702__auto__,c__5717__auto___10973,G__10792_10962,n__4409__auto___10960,jobs,results,process,async))
})();var state__5719__auto__ = (function (){var statearr_10846 = f__5718__auto__.call(null);(statearr_10846[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___10973);
return statearr_10846;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(__10961,c__5717__auto___10973,G__10792_10962,n__4409__auto___10960,jobs,results,process,async))
);

break;
default:
throw (new Error(("No matching clause: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(type))));

}
{
var G__10982 = (__10961 + (1));
__10961 = G__10982;
continue;
}
} else
{}
break;
}
var c__5717__auto___10983 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___10983,jobs,results,process,async){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___10983,jobs,results,process,async){
return (function (state_10868){var state_val_10869 = (state_10868[(1)]);if((state_val_10869 === (9)))
{var inst_10861 = (state_10868[(2)]);var state_10868__$1 = (function (){var statearr_10870 = state_10868;(statearr_10870[(7)] = inst_10861);
return statearr_10870;
})();var statearr_10871_10984 = state_10868__$1;(statearr_10871_10984[(2)] = null);
(statearr_10871_10984[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10869 === (8)))
{var inst_10854 = (state_10868[(8)]);var inst_10859 = (state_10868[(2)]);var state_10868__$1 = (function (){var statearr_10872 = state_10868;(statearr_10872[(9)] = inst_10859);
return statearr_10872;
})();return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_10868__$1,(9),results,inst_10854);
} else
{if((state_val_10869 === (7)))
{var inst_10864 = (state_10868[(2)]);var state_10868__$1 = state_10868;var statearr_10873_10985 = state_10868__$1;(statearr_10873_10985[(2)] = inst_10864);
(statearr_10873_10985[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10869 === (6)))
{var inst_10854 = (state_10868[(8)]);var inst_10849 = (state_10868[(10)]);var inst_10854__$1 = cljs.core.async.chan.call(null,(1));var inst_10855 = cljs.core.PersistentVector.EMPTY_NODE;var inst_10856 = [inst_10849,inst_10854__$1];var inst_10857 = (new cljs.core.PersistentVector(null,2,(5),inst_10855,inst_10856,null));var state_10868__$1 = (function (){var statearr_10874 = state_10868;(statearr_10874[(8)] = inst_10854__$1);
return statearr_10874;
})();return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_10868__$1,(8),jobs,inst_10857);
} else
{if((state_val_10869 === (5)))
{var inst_10852 = cljs.core.async.close_BANG_.call(null,jobs);var state_10868__$1 = state_10868;var statearr_10875_10986 = state_10868__$1;(statearr_10875_10986[(2)] = inst_10852);
(statearr_10875_10986[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10869 === (4)))
{var inst_10849 = (state_10868[(10)]);var inst_10849__$1 = (state_10868[(2)]);var inst_10850 = (inst_10849__$1 == null);var state_10868__$1 = (function (){var statearr_10876 = state_10868;(statearr_10876[(10)] = inst_10849__$1);
return statearr_10876;
})();if(cljs.core.truth_(inst_10850))
{var statearr_10877_10987 = state_10868__$1;(statearr_10877_10987[(1)] = (5));
} else
{var statearr_10878_10988 = state_10868__$1;(statearr_10878_10988[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10869 === (3)))
{var inst_10866 = (state_10868[(2)]);var state_10868__$1 = state_10868;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_10868__$1,inst_10866);
} else
{if((state_val_10869 === (2)))
{var state_10868__$1 = state_10868;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10868__$1,(4),from);
} else
{if((state_val_10869 === (1)))
{var state_10868__$1 = state_10868;var statearr_10879_10989 = state_10868__$1;(statearr_10879_10989[(2)] = null);
(statearr_10879_10989[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___10983,jobs,results,process,async))
;return ((function (switch__5702__auto__,c__5717__auto___10983,jobs,results,process,async){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_10883 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_10883[(0)] = state_machine__5703__auto__);
(statearr_10883[(1)] = (1));
return statearr_10883;
});
var state_machine__5703__auto____1 = (function (state_10868){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_10868);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e10884){if((e10884 instanceof Object))
{var ex__5706__auto__ = e10884;var statearr_10885_10990 = state_10868;(statearr_10885_10990[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_10868);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e10884;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__10991 = state_10868;
state_10868 = G__10991;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_10868){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_10868);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___10983,jobs,results,process,async))
})();var state__5719__auto__ = (function (){var statearr_10886 = f__5718__auto__.call(null);(statearr_10886[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___10983);
return statearr_10886;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___10983,jobs,results,process,async))
);
var c__5717__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto__,jobs,results,process,async){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto__,jobs,results,process,async){
return (function (state_10924){var state_val_10925 = (state_10924[(1)]);if((state_val_10925 === (7)))
{var inst_10920 = (state_10924[(2)]);var state_10924__$1 = state_10924;var statearr_10926_10992 = state_10924__$1;(statearr_10926_10992[(2)] = inst_10920);
(statearr_10926_10992[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (20)))
{var state_10924__$1 = state_10924;var statearr_10927_10993 = state_10924__$1;(statearr_10927_10993[(2)] = null);
(statearr_10927_10993[(1)] = (21));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (1)))
{var state_10924__$1 = state_10924;var statearr_10928_10994 = state_10924__$1;(statearr_10928_10994[(2)] = null);
(statearr_10928_10994[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (4)))
{var inst_10889 = (state_10924[(7)]);var inst_10889__$1 = (state_10924[(2)]);var inst_10890 = (inst_10889__$1 == null);var state_10924__$1 = (function (){var statearr_10929 = state_10924;(statearr_10929[(7)] = inst_10889__$1);
return statearr_10929;
})();if(cljs.core.truth_(inst_10890))
{var statearr_10930_10995 = state_10924__$1;(statearr_10930_10995[(1)] = (5));
} else
{var statearr_10931_10996 = state_10924__$1;(statearr_10931_10996[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (15)))
{var inst_10902 = (state_10924[(8)]);var state_10924__$1 = state_10924;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_10924__$1,(18),to,inst_10902);
} else
{if((state_val_10925 === (21)))
{var inst_10915 = (state_10924[(2)]);var state_10924__$1 = state_10924;var statearr_10932_10997 = state_10924__$1;(statearr_10932_10997[(2)] = inst_10915);
(statearr_10932_10997[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (13)))
{var inst_10917 = (state_10924[(2)]);var state_10924__$1 = (function (){var statearr_10933 = state_10924;(statearr_10933[(9)] = inst_10917);
return statearr_10933;
})();var statearr_10934_10998 = state_10924__$1;(statearr_10934_10998[(2)] = null);
(statearr_10934_10998[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (6)))
{var inst_10889 = (state_10924[(7)]);var state_10924__$1 = state_10924;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10924__$1,(11),inst_10889);
} else
{if((state_val_10925 === (17)))
{var inst_10910 = (state_10924[(2)]);var state_10924__$1 = state_10924;if(cljs.core.truth_(inst_10910))
{var statearr_10935_10999 = state_10924__$1;(statearr_10935_10999[(1)] = (19));
} else
{var statearr_10936_11000 = state_10924__$1;(statearr_10936_11000[(1)] = (20));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (3)))
{var inst_10922 = (state_10924[(2)]);var state_10924__$1 = state_10924;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_10924__$1,inst_10922);
} else
{if((state_val_10925 === (12)))
{var inst_10899 = (state_10924[(10)]);var state_10924__$1 = state_10924;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10924__$1,(14),inst_10899);
} else
{if((state_val_10925 === (2)))
{var state_10924__$1 = state_10924;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10924__$1,(4),results);
} else
{if((state_val_10925 === (19)))
{var state_10924__$1 = state_10924;var statearr_10937_11001 = state_10924__$1;(statearr_10937_11001[(2)] = null);
(statearr_10937_11001[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (11)))
{var inst_10899 = (state_10924[(2)]);var state_10924__$1 = (function (){var statearr_10938 = state_10924;(statearr_10938[(10)] = inst_10899);
return statearr_10938;
})();var statearr_10939_11002 = state_10924__$1;(statearr_10939_11002[(2)] = null);
(statearr_10939_11002[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (9)))
{var state_10924__$1 = state_10924;var statearr_10940_11003 = state_10924__$1;(statearr_10940_11003[(2)] = null);
(statearr_10940_11003[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (5)))
{var state_10924__$1 = state_10924;if(cljs.core.truth_(close_QMARK_))
{var statearr_10941_11004 = state_10924__$1;(statearr_10941_11004[(1)] = (8));
} else
{var statearr_10942_11005 = state_10924__$1;(statearr_10942_11005[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (14)))
{var inst_10904 = (state_10924[(11)]);var inst_10902 = (state_10924[(8)]);var inst_10902__$1 = (state_10924[(2)]);var inst_10903 = (inst_10902__$1 == null);var inst_10904__$1 = cljs.core.not.call(null,inst_10903);var state_10924__$1 = (function (){var statearr_10943 = state_10924;(statearr_10943[(11)] = inst_10904__$1);
(statearr_10943[(8)] = inst_10902__$1);
return statearr_10943;
})();if(inst_10904__$1)
{var statearr_10944_11006 = state_10924__$1;(statearr_10944_11006[(1)] = (15));
} else
{var statearr_10945_11007 = state_10924__$1;(statearr_10945_11007[(1)] = (16));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (16)))
{var inst_10904 = (state_10924[(11)]);var state_10924__$1 = state_10924;var statearr_10946_11008 = state_10924__$1;(statearr_10946_11008[(2)] = inst_10904);
(statearr_10946_11008[(1)] = (17));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (10)))
{var inst_10896 = (state_10924[(2)]);var state_10924__$1 = state_10924;var statearr_10947_11009 = state_10924__$1;(statearr_10947_11009[(2)] = inst_10896);
(statearr_10947_11009[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (18)))
{var inst_10907 = (state_10924[(2)]);var state_10924__$1 = state_10924;var statearr_10948_11010 = state_10924__$1;(statearr_10948_11010[(2)] = inst_10907);
(statearr_10948_11010[(1)] = (17));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10925 === (8)))
{var inst_10893 = cljs.core.async.close_BANG_.call(null,to);var state_10924__$1 = state_10924;var statearr_10949_11011 = state_10924__$1;(statearr_10949_11011[(2)] = inst_10893);
(statearr_10949_11011[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto__,jobs,results,process,async))
;return ((function (switch__5702__auto__,c__5717__auto__,jobs,results,process,async){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_10953 = [null,null,null,null,null,null,null,null,null,null,null,null];(statearr_10953[(0)] = state_machine__5703__auto__);
(statearr_10953[(1)] = (1));
return statearr_10953;
});
var state_machine__5703__auto____1 = (function (state_10924){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_10924);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e10954){if((e10954 instanceof Object))
{var ex__5706__auto__ = e10954;var statearr_10955_11012 = state_10924;(statearr_10955_11012[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_10924);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e10954;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__11013 = state_10924;
state_10924 = G__11013;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_10924){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_10924);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto__,jobs,results,process,async))
})();var state__5719__auto__ = (function (){var statearr_10956 = f__5718__auto__.call(null);(statearr_10956[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto__);
return statearr_10956;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto__,jobs,results,process,async))
);
return c__5717__auto__;
});
/**
* Takes elements from the from channel and supplies them to the to
* channel, subject to the async function af, with parallelism n. af
* must be a function of two arguments, the first an input value and
* the second a channel on which to place the result(s). af must close!
* the channel before returning.  The presumption is that af will
* return immediately, having launched some asynchronous operation
* whose completion/callback will manipulate the result channel. Outputs
* will be returned in order relative to  the inputs. By default, the to
* channel will be closed when the from channel closes, but can be
* determined by the close?  parameter. Will stop consuming the from
* channel if the to channel closes.
*/
cljs.core.async.pipeline_async = (function() {
var pipeline_async = null;
var pipeline_async__4 = (function (n,to,af,from){return pipeline_async.call(null,n,to,af,from,true);
});
var pipeline_async__5 = (function (n,to,af,from,close_QMARK_){return cljs.core.async.pipeline_STAR_.call(null,n,to,af,from,close_QMARK_,null,new cljs.core.Keyword(null,"async","async",1050769601));
});
pipeline_async = function(n,to,af,from,close_QMARK_){
switch(arguments.length){
case 4:
return pipeline_async__4.call(this,n,to,af,from);
case 5:
return pipeline_async__5.call(this,n,to,af,from,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
pipeline_async.cljs$core$IFn$_invoke$arity$4 = pipeline_async__4;
pipeline_async.cljs$core$IFn$_invoke$arity$5 = pipeline_async__5;
return pipeline_async;
})()
;
/**
* Takes elements from the from channel and supplies them to the to
* channel, subject to the transducer xf, with parallelism n. Because
* it is parallel, the transducer will be applied independently to each
* element, not across elements, and may produce zero or more outputs
* per input.  Outputs will be returned in order relative to the
* inputs. By default, the to channel will be closed when the from
* channel closes, but can be determined by the close?  parameter. Will
* stop consuming the from channel if the to channel closes.
* 
* Note this is supplied for API compatibility with the Clojure version.
* Values of N > 1 will not result in actual concurrency in a
* single-threaded runtime.
*/
cljs.core.async.pipeline = (function() {
var pipeline = null;
var pipeline__4 = (function (n,to,xf,from){return pipeline.call(null,n,to,xf,from,true);
});
var pipeline__5 = (function (n,to,xf,from,close_QMARK_){return pipeline.call(null,n,to,xf,from,close_QMARK_,null);
});
var pipeline__6 = (function (n,to,xf,from,close_QMARK_,ex_handler){return cljs.core.async.pipeline_STAR_.call(null,n,to,xf,from,close_QMARK_,ex_handler,new cljs.core.Keyword(null,"compute","compute",1555393130));
});
pipeline = function(n,to,xf,from,close_QMARK_,ex_handler){
switch(arguments.length){
case 4:
return pipeline__4.call(this,n,to,xf,from);
case 5:
return pipeline__5.call(this,n,to,xf,from,close_QMARK_);
case 6:
return pipeline__6.call(this,n,to,xf,from,close_QMARK_,ex_handler);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
pipeline.cljs$core$IFn$_invoke$arity$4 = pipeline__4;
pipeline.cljs$core$IFn$_invoke$arity$5 = pipeline__5;
pipeline.cljs$core$IFn$_invoke$arity$6 = pipeline__6;
return pipeline;
})()
;
/**
* Takes a predicate and a source channel and returns a vector of two
* channels, the first of which will contain the values for which the
* predicate returned true, the second those for which it returned
* false.
* 
* The out channels will be unbuffered by default, or two buf-or-ns can
* be supplied. The channels will close after the source channel has
* closed.
*/
cljs.core.async.split = (function() {
var split = null;
var split__2 = (function (p,ch){return split.call(null,p,ch,null,null);
});
var split__4 = (function (p,ch,t_buf_or_n,f_buf_or_n){var tc = cljs.core.async.chan.call(null,t_buf_or_n);var fc = cljs.core.async.chan.call(null,f_buf_or_n);var c__5717__auto___11114 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___11114,tc,fc){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___11114,tc,fc){
return (function (state_11089){var state_val_11090 = (state_11089[(1)]);if((state_val_11090 === (7)))
{var inst_11085 = (state_11089[(2)]);var state_11089__$1 = state_11089;var statearr_11091_11115 = state_11089__$1;(statearr_11091_11115[(2)] = inst_11085);
(statearr_11091_11115[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (1)))
{var state_11089__$1 = state_11089;var statearr_11092_11116 = state_11089__$1;(statearr_11092_11116[(2)] = null);
(statearr_11092_11116[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (4)))
{var inst_11066 = (state_11089[(7)]);var inst_11066__$1 = (state_11089[(2)]);var inst_11067 = (inst_11066__$1 == null);var state_11089__$1 = (function (){var statearr_11093 = state_11089;(statearr_11093[(7)] = inst_11066__$1);
return statearr_11093;
})();if(cljs.core.truth_(inst_11067))
{var statearr_11094_11117 = state_11089__$1;(statearr_11094_11117[(1)] = (5));
} else
{var statearr_11095_11118 = state_11089__$1;(statearr_11095_11118[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (13)))
{var state_11089__$1 = state_11089;var statearr_11096_11119 = state_11089__$1;(statearr_11096_11119[(2)] = null);
(statearr_11096_11119[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (6)))
{var inst_11066 = (state_11089[(7)]);var inst_11072 = p.call(null,inst_11066);var state_11089__$1 = state_11089;if(cljs.core.truth_(inst_11072))
{var statearr_11097_11120 = state_11089__$1;(statearr_11097_11120[(1)] = (9));
} else
{var statearr_11098_11121 = state_11089__$1;(statearr_11098_11121[(1)] = (10));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (3)))
{var inst_11087 = (state_11089[(2)]);var state_11089__$1 = state_11089;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_11089__$1,inst_11087);
} else
{if((state_val_11090 === (12)))
{var state_11089__$1 = state_11089;var statearr_11099_11122 = state_11089__$1;(statearr_11099_11122[(2)] = null);
(statearr_11099_11122[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (2)))
{var state_11089__$1 = state_11089;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_11089__$1,(4),ch);
} else
{if((state_val_11090 === (11)))
{var inst_11066 = (state_11089[(7)]);var inst_11076 = (state_11089[(2)]);var state_11089__$1 = state_11089;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_11089__$1,(8),inst_11076,inst_11066);
} else
{if((state_val_11090 === (9)))
{var state_11089__$1 = state_11089;var statearr_11100_11123 = state_11089__$1;(statearr_11100_11123[(2)] = tc);
(statearr_11100_11123[(1)] = (11));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (5)))
{var inst_11069 = cljs.core.async.close_BANG_.call(null,tc);var inst_11070 = cljs.core.async.close_BANG_.call(null,fc);var state_11089__$1 = (function (){var statearr_11101 = state_11089;(statearr_11101[(8)] = inst_11069);
return statearr_11101;
})();var statearr_11102_11124 = state_11089__$1;(statearr_11102_11124[(2)] = inst_11070);
(statearr_11102_11124[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (14)))
{var inst_11083 = (state_11089[(2)]);var state_11089__$1 = state_11089;var statearr_11103_11125 = state_11089__$1;(statearr_11103_11125[(2)] = inst_11083);
(statearr_11103_11125[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (10)))
{var state_11089__$1 = state_11089;var statearr_11104_11126 = state_11089__$1;(statearr_11104_11126[(2)] = fc);
(statearr_11104_11126[(1)] = (11));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11090 === (8)))
{var inst_11078 = (state_11089[(2)]);var state_11089__$1 = state_11089;if(cljs.core.truth_(inst_11078))
{var statearr_11105_11127 = state_11089__$1;(statearr_11105_11127[(1)] = (12));
} else
{var statearr_11106_11128 = state_11089__$1;(statearr_11106_11128[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___11114,tc,fc))
;return ((function (switch__5702__auto__,c__5717__auto___11114,tc,fc){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_11110 = [null,null,null,null,null,null,null,null,null];(statearr_11110[(0)] = state_machine__5703__auto__);
(statearr_11110[(1)] = (1));
return statearr_11110;
});
var state_machine__5703__auto____1 = (function (state_11089){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_11089);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e11111){if((e11111 instanceof Object))
{var ex__5706__auto__ = e11111;var statearr_11112_11129 = state_11089;(statearr_11112_11129[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_11089);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e11111;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__11130 = state_11089;
state_11089 = G__11130;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_11089){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_11089);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___11114,tc,fc))
})();var state__5719__auto__ = (function (){var statearr_11113 = f__5718__auto__.call(null);(statearr_11113[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___11114);
return statearr_11113;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___11114,tc,fc))
);
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [tc,fc], null);
});
split = function(p,ch,t_buf_or_n,f_buf_or_n){
switch(arguments.length){
case 2:
return split__2.call(this,p,ch);
case 4:
return split__4.call(this,p,ch,t_buf_or_n,f_buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
split.cljs$core$IFn$_invoke$arity$2 = split__2;
split.cljs$core$IFn$_invoke$arity$4 = split__4;
return split;
})()
;
/**
* f should be a function of 2 arguments. Returns a channel containing
* the single result of applying f to init and the first item from the
* channel, then applying f to that result and the 2nd item, etc. If
* the channel closes without yielding items, returns init and f is not
* called. ch must close before reduce produces a result.
*/
cljs.core.async.reduce = (function reduce(f,init,ch){var c__5717__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto__){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto__){
return (function (state_11177){var state_val_11178 = (state_11177[(1)]);if((state_val_11178 === (7)))
{var inst_11173 = (state_11177[(2)]);var state_11177__$1 = state_11177;var statearr_11179_11195 = state_11177__$1;(statearr_11179_11195[(2)] = inst_11173);
(statearr_11179_11195[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11178 === (6)))
{var inst_11163 = (state_11177[(7)]);var inst_11166 = (state_11177[(8)]);var inst_11170 = f.call(null,inst_11163,inst_11166);var inst_11163__$1 = inst_11170;var state_11177__$1 = (function (){var statearr_11180 = state_11177;(statearr_11180[(7)] = inst_11163__$1);
return statearr_11180;
})();var statearr_11181_11196 = state_11177__$1;(statearr_11181_11196[(2)] = null);
(statearr_11181_11196[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11178 === (5)))
{var inst_11163 = (state_11177[(7)]);var state_11177__$1 = state_11177;var statearr_11182_11197 = state_11177__$1;(statearr_11182_11197[(2)] = inst_11163);
(statearr_11182_11197[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11178 === (4)))
{var inst_11166 = (state_11177[(8)]);var inst_11166__$1 = (state_11177[(2)]);var inst_11167 = (inst_11166__$1 == null);var state_11177__$1 = (function (){var statearr_11183 = state_11177;(statearr_11183[(8)] = inst_11166__$1);
return statearr_11183;
})();if(cljs.core.truth_(inst_11167))
{var statearr_11184_11198 = state_11177__$1;(statearr_11184_11198[(1)] = (5));
} else
{var statearr_11185_11199 = state_11177__$1;(statearr_11185_11199[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11178 === (3)))
{var inst_11175 = (state_11177[(2)]);var state_11177__$1 = state_11177;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_11177__$1,inst_11175);
} else
{if((state_val_11178 === (2)))
{var state_11177__$1 = state_11177;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_11177__$1,(4),ch);
} else
{if((state_val_11178 === (1)))
{var inst_11163 = init;var state_11177__$1 = (function (){var statearr_11186 = state_11177;(statearr_11186[(7)] = inst_11163);
return statearr_11186;
})();var statearr_11187_11200 = state_11177__$1;(statearr_11187_11200[(2)] = null);
(statearr_11187_11200[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
});})(c__5717__auto__))
;return ((function (switch__5702__auto__,c__5717__auto__){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_11191 = [null,null,null,null,null,null,null,null,null];(statearr_11191[(0)] = state_machine__5703__auto__);
(statearr_11191[(1)] = (1));
return statearr_11191;
});
var state_machine__5703__auto____1 = (function (state_11177){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_11177);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e11192){if((e11192 instanceof Object))
{var ex__5706__auto__ = e11192;var statearr_11193_11201 = state_11177;(statearr_11193_11201[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_11177);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e11192;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__11202 = state_11177;
state_11177 = G__11202;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_11177){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_11177);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto__))
})();var state__5719__auto__ = (function (){var statearr_11194 = f__5718__auto__.call(null);(statearr_11194[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto__);
return statearr_11194;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto__))
);
return c__5717__auto__;
});
/**
* Puts the contents of coll into the supplied channel.
* 
* By default the channel will be closed after the items are copied,
* but can be determined by the close? parameter.
* 
* Returns a channel which will close after the items are copied.
*/
cljs.core.async.onto_chan = (function() {
var onto_chan = null;
var onto_chan__2 = (function (ch,coll){return onto_chan.call(null,ch,coll,true);
});
var onto_chan__3 = (function (ch,coll,close_QMARK_){var c__5717__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto__){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto__){
return (function (state_11276){var state_val_11277 = (state_11276[(1)]);if((state_val_11277 === (7)))
{var inst_11258 = (state_11276[(2)]);var state_11276__$1 = state_11276;var statearr_11278_11301 = state_11276__$1;(statearr_11278_11301[(2)] = inst_11258);
(statearr_11278_11301[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (1)))
{var inst_11252 = cljs.core.seq.call(null,coll);var inst_11253 = inst_11252;var state_11276__$1 = (function (){var statearr_11279 = state_11276;(statearr_11279[(7)] = inst_11253);
return statearr_11279;
})();var statearr_11280_11302 = state_11276__$1;(statearr_11280_11302[(2)] = null);
(statearr_11280_11302[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (4)))
{var inst_11253 = (state_11276[(7)]);var inst_11256 = cljs.core.first.call(null,inst_11253);var state_11276__$1 = state_11276;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_11276__$1,(7),ch,inst_11256);
} else
{if((state_val_11277 === (13)))
{var inst_11270 = (state_11276[(2)]);var state_11276__$1 = state_11276;var statearr_11281_11303 = state_11276__$1;(statearr_11281_11303[(2)] = inst_11270);
(statearr_11281_11303[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (6)))
{var inst_11261 = (state_11276[(2)]);var state_11276__$1 = state_11276;if(cljs.core.truth_(inst_11261))
{var statearr_11282_11304 = state_11276__$1;(statearr_11282_11304[(1)] = (8));
} else
{var statearr_11283_11305 = state_11276__$1;(statearr_11283_11305[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (3)))
{var inst_11274 = (state_11276[(2)]);var state_11276__$1 = state_11276;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_11276__$1,inst_11274);
} else
{if((state_val_11277 === (12)))
{var state_11276__$1 = state_11276;var statearr_11284_11306 = state_11276__$1;(statearr_11284_11306[(2)] = null);
(statearr_11284_11306[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (2)))
{var inst_11253 = (state_11276[(7)]);var state_11276__$1 = state_11276;if(cljs.core.truth_(inst_11253))
{var statearr_11285_11307 = state_11276__$1;(statearr_11285_11307[(1)] = (4));
} else
{var statearr_11286_11308 = state_11276__$1;(statearr_11286_11308[(1)] = (5));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (11)))
{var inst_11267 = cljs.core.async.close_BANG_.call(null,ch);var state_11276__$1 = state_11276;var statearr_11287_11309 = state_11276__$1;(statearr_11287_11309[(2)] = inst_11267);
(statearr_11287_11309[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (9)))
{var state_11276__$1 = state_11276;if(cljs.core.truth_(close_QMARK_))
{var statearr_11288_11310 = state_11276__$1;(statearr_11288_11310[(1)] = (11));
} else
{var statearr_11289_11311 = state_11276__$1;(statearr_11289_11311[(1)] = (12));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (5)))
{var inst_11253 = (state_11276[(7)]);var state_11276__$1 = state_11276;var statearr_11290_11312 = state_11276__$1;(statearr_11290_11312[(2)] = inst_11253);
(statearr_11290_11312[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (10)))
{var inst_11272 = (state_11276[(2)]);var state_11276__$1 = state_11276;var statearr_11291_11313 = state_11276__$1;(statearr_11291_11313[(2)] = inst_11272);
(statearr_11291_11313[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11277 === (8)))
{var inst_11253 = (state_11276[(7)]);var inst_11263 = cljs.core.next.call(null,inst_11253);var inst_11253__$1 = inst_11263;var state_11276__$1 = (function (){var statearr_11292 = state_11276;(statearr_11292[(7)] = inst_11253__$1);
return statearr_11292;
})();var statearr_11293_11314 = state_11276__$1;(statearr_11293_11314[(2)] = null);
(statearr_11293_11314[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto__))
;return ((function (switch__5702__auto__,c__5717__auto__){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_11297 = [null,null,null,null,null,null,null,null];(statearr_11297[(0)] = state_machine__5703__auto__);
(statearr_11297[(1)] = (1));
return statearr_11297;
});
var state_machine__5703__auto____1 = (function (state_11276){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_11276);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e11298){if((e11298 instanceof Object))
{var ex__5706__auto__ = e11298;var statearr_11299_11315 = state_11276;(statearr_11299_11315[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_11276);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e11298;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__11316 = state_11276;
state_11276 = G__11316;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_11276){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_11276);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto__))
})();var state__5719__auto__ = (function (){var statearr_11300 = f__5718__auto__.call(null);(statearr_11300[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto__);
return statearr_11300;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto__))
);
return c__5717__auto__;
});
onto_chan = function(ch,coll,close_QMARK_){
switch(arguments.length){
case 2:
return onto_chan__2.call(this,ch,coll);
case 3:
return onto_chan__3.call(this,ch,coll,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
onto_chan.cljs$core$IFn$_invoke$arity$2 = onto_chan__2;
onto_chan.cljs$core$IFn$_invoke$arity$3 = onto_chan__3;
return onto_chan;
})()
;
/**
* Creates and returns a channel which contains the contents of coll,
* closing when exhausted.
*/
cljs.core.async.to_chan = (function to_chan(coll){var ch = cljs.core.async.chan.call(null,cljs.core.bounded_count.call(null,(100),coll));cljs.core.async.onto_chan.call(null,ch,coll);
return ch;
});
cljs.core.async.Mux = (function (){var obj11318 = {};return obj11318;
})();
cljs.core.async.muxch_STAR_ = (function muxch_STAR_(_){if((function (){var and__3541__auto__ = _;if(and__3541__auto__)
{return _.cljs$core$async$Mux$muxch_STAR_$arity$1;
} else
{return and__3541__auto__;
}
})())
{return _.cljs$core$async$Mux$muxch_STAR_$arity$1(_);
} else
{var x__4180__auto__ = (((_ == null))?null:_);return (function (){var or__3553__auto__ = (cljs.core.async.muxch_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.muxch_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mux.muxch*",_);
}
}
})().call(null,_);
}
});
cljs.core.async.Mult = (function (){var obj11320 = {};return obj11320;
})();
cljs.core.async.tap_STAR_ = (function tap_STAR_(m,ch,close_QMARK_){if((function (){var and__3541__auto__ = m;if(and__3541__auto__)
{return m.cljs$core$async$Mult$tap_STAR_$arity$3;
} else
{return and__3541__auto__;
}
})())
{return m.cljs$core$async$Mult$tap_STAR_$arity$3(m,ch,close_QMARK_);
} else
{var x__4180__auto__ = (((m == null))?null:m);return (function (){var or__3553__auto__ = (cljs.core.async.tap_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.tap_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mult.tap*",m);
}
}
})().call(null,m,ch,close_QMARK_);
}
});
cljs.core.async.untap_STAR_ = (function untap_STAR_(m,ch){if((function (){var and__3541__auto__ = m;if(and__3541__auto__)
{return m.cljs$core$async$Mult$untap_STAR_$arity$2;
} else
{return and__3541__auto__;
}
})())
{return m.cljs$core$async$Mult$untap_STAR_$arity$2(m,ch);
} else
{var x__4180__auto__ = (((m == null))?null:m);return (function (){var or__3553__auto__ = (cljs.core.async.untap_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.untap_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mult.untap*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.untap_all_STAR_ = (function untap_all_STAR_(m){if((function (){var and__3541__auto__ = m;if(and__3541__auto__)
{return m.cljs$core$async$Mult$untap_all_STAR_$arity$1;
} else
{return and__3541__auto__;
}
})())
{return m.cljs$core$async$Mult$untap_all_STAR_$arity$1(m);
} else
{var x__4180__auto__ = (((m == null))?null:m);return (function (){var or__3553__auto__ = (cljs.core.async.untap_all_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.untap_all_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mult.untap-all*",m);
}
}
})().call(null,m);
}
});
/**
* Creates and returns a mult(iple) of the supplied channel. Channels
* containing copies of the channel can be created with 'tap', and
* detached with 'untap'.
* 
* Each item is distributed to all taps in parallel and synchronously,
* i.e. each tap must accept before the next item is distributed. Use
* buffering/windowing to prevent slow taps from holding up the mult.
* 
* Items received when there are no taps get dropped.
* 
* If a tap puts to a closed channel, it will be removed from the mult.
*/
cljs.core.async.mult = (function mult(ch){var cs = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);var m = (function (){if(typeof cljs.core.async.t11542 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t11542 = (function (cs,ch,mult,meta11543){
this.cs = cs;
this.ch = ch;
this.mult = mult;
this.meta11543 = meta11543;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t11542.cljs$lang$type = true;
cljs.core.async.t11542.cljs$lang$ctorStr = "cljs.core.async/t11542";
cljs.core.async.t11542.cljs$lang$ctorPrWriter = ((function (cs){
return (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t11542");
});})(cs))
;
cljs.core.async.t11542.prototype.cljs$core$async$Mult$ = true;
cljs.core.async.t11542.prototype.cljs$core$async$Mult$tap_STAR_$arity$3 = ((function (cs){
return (function (_,ch__$2,close_QMARK_){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.assoc,ch__$2,close_QMARK_);
return null;
});})(cs))
;
cljs.core.async.t11542.prototype.cljs$core$async$Mult$untap_STAR_$arity$2 = ((function (cs){
return (function (_,ch__$2){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.dissoc,ch__$2);
return null;
});})(cs))
;
cljs.core.async.t11542.prototype.cljs$core$async$Mult$untap_all_STAR_$arity$1 = ((function (cs){
return (function (_){var self__ = this;
var ___$1 = this;cljs.core.reset_BANG_.call(null,self__.cs,cljs.core.PersistentArrayMap.EMPTY);
return null;
});})(cs))
;
cljs.core.async.t11542.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t11542.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (cs){
return (function (_){var self__ = this;
var ___$1 = this;return self__.ch;
});})(cs))
;
cljs.core.async.t11542.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (cs){
return (function (_11544){var self__ = this;
var _11544__$1 = this;return self__.meta11543;
});})(cs))
;
cljs.core.async.t11542.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (cs){
return (function (_11544,meta11543__$1){var self__ = this;
var _11544__$1 = this;return (new cljs.core.async.t11542(self__.cs,self__.ch,self__.mult,meta11543__$1));
});})(cs))
;
cljs.core.async.__GT_t11542 = ((function (cs){
return (function __GT_t11542(cs__$1,ch__$1,mult__$1,meta11543){return (new cljs.core.async.t11542(cs__$1,ch__$1,mult__$1,meta11543));
});})(cs))
;
}
return (new cljs.core.async.t11542(cs,ch,mult,null));
})();var dchan = cljs.core.async.chan.call(null,(1));var dctr = cljs.core.atom.call(null,null);var done = ((function (cs,m,dchan,dctr){
return (function (_){if((cljs.core.swap_BANG_.call(null,dctr,cljs.core.dec) === (0)))
{return cljs.core.async.put_BANG_.call(null,dchan,true);
} else
{return null;
}
});})(cs,m,dchan,dctr))
;var c__5717__auto___11763 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___11763,cs,m,dchan,dctr,done){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___11763,cs,m,dchan,dctr,done){
return (function (state_11675){var state_val_11676 = (state_11675[(1)]);if((state_val_11676 === (7)))
{var inst_11671 = (state_11675[(2)]);var state_11675__$1 = state_11675;var statearr_11677_11764 = state_11675__$1;(statearr_11677_11764[(2)] = inst_11671);
(statearr_11677_11764[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (20)))
{var inst_11576 = (state_11675[(7)]);var inst_11586 = cljs.core.first.call(null,inst_11576);var inst_11587 = cljs.core.nth.call(null,inst_11586,(0),null);var inst_11588 = cljs.core.nth.call(null,inst_11586,(1),null);var state_11675__$1 = (function (){var statearr_11678 = state_11675;(statearr_11678[(8)] = inst_11587);
return statearr_11678;
})();if(cljs.core.truth_(inst_11588))
{var statearr_11679_11765 = state_11675__$1;(statearr_11679_11765[(1)] = (22));
} else
{var statearr_11680_11766 = state_11675__$1;(statearr_11680_11766[(1)] = (23));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (27)))
{var inst_11616 = (state_11675[(9)]);var inst_11547 = (state_11675[(10)]);var inst_11618 = (state_11675[(11)]);var inst_11623 = (state_11675[(12)]);var inst_11623__$1 = cljs.core._nth.call(null,inst_11616,inst_11618);var inst_11624 = cljs.core.async.put_BANG_.call(null,inst_11623__$1,inst_11547,done);var state_11675__$1 = (function (){var statearr_11681 = state_11675;(statearr_11681[(12)] = inst_11623__$1);
return statearr_11681;
})();if(cljs.core.truth_(inst_11624))
{var statearr_11682_11767 = state_11675__$1;(statearr_11682_11767[(1)] = (30));
} else
{var statearr_11683_11768 = state_11675__$1;(statearr_11683_11768[(1)] = (31));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (1)))
{var state_11675__$1 = state_11675;var statearr_11684_11769 = state_11675__$1;(statearr_11684_11769[(2)] = null);
(statearr_11684_11769[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (24)))
{var inst_11576 = (state_11675[(7)]);var inst_11593 = (state_11675[(2)]);var inst_11594 = cljs.core.next.call(null,inst_11576);var inst_11556 = inst_11594;var inst_11557 = null;var inst_11558 = (0);var inst_11559 = (0);var state_11675__$1 = (function (){var statearr_11685 = state_11675;(statearr_11685[(13)] = inst_11593);
(statearr_11685[(14)] = inst_11556);
(statearr_11685[(15)] = inst_11557);
(statearr_11685[(16)] = inst_11559);
(statearr_11685[(17)] = inst_11558);
return statearr_11685;
})();var statearr_11686_11770 = state_11675__$1;(statearr_11686_11770[(2)] = null);
(statearr_11686_11770[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (39)))
{var state_11675__$1 = state_11675;var statearr_11690_11771 = state_11675__$1;(statearr_11690_11771[(2)] = null);
(statearr_11690_11771[(1)] = (41));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (4)))
{var inst_11547 = (state_11675[(10)]);var inst_11547__$1 = (state_11675[(2)]);var inst_11548 = (inst_11547__$1 == null);var state_11675__$1 = (function (){var statearr_11691 = state_11675;(statearr_11691[(10)] = inst_11547__$1);
return statearr_11691;
})();if(cljs.core.truth_(inst_11548))
{var statearr_11692_11772 = state_11675__$1;(statearr_11692_11772[(1)] = (5));
} else
{var statearr_11693_11773 = state_11675__$1;(statearr_11693_11773[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (15)))
{var inst_11556 = (state_11675[(14)]);var inst_11557 = (state_11675[(15)]);var inst_11559 = (state_11675[(16)]);var inst_11558 = (state_11675[(17)]);var inst_11572 = (state_11675[(2)]);var inst_11573 = (inst_11559 + (1));var tmp11687 = inst_11556;var tmp11688 = inst_11557;var tmp11689 = inst_11558;var inst_11556__$1 = tmp11687;var inst_11557__$1 = tmp11688;var inst_11558__$1 = tmp11689;var inst_11559__$1 = inst_11573;var state_11675__$1 = (function (){var statearr_11694 = state_11675;(statearr_11694[(14)] = inst_11556__$1);
(statearr_11694[(15)] = inst_11557__$1);
(statearr_11694[(18)] = inst_11572);
(statearr_11694[(16)] = inst_11559__$1);
(statearr_11694[(17)] = inst_11558__$1);
return statearr_11694;
})();var statearr_11695_11774 = state_11675__$1;(statearr_11695_11774[(2)] = null);
(statearr_11695_11774[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (21)))
{var inst_11597 = (state_11675[(2)]);var state_11675__$1 = state_11675;var statearr_11699_11775 = state_11675__$1;(statearr_11699_11775[(2)] = inst_11597);
(statearr_11699_11775[(1)] = (18));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (31)))
{var inst_11623 = (state_11675[(12)]);var inst_11627 = done.call(null,null);var inst_11628 = cljs.core.async.untap_STAR_.call(null,m,inst_11623);var state_11675__$1 = (function (){var statearr_11700 = state_11675;(statearr_11700[(19)] = inst_11627);
return statearr_11700;
})();var statearr_11701_11776 = state_11675__$1;(statearr_11701_11776[(2)] = inst_11628);
(statearr_11701_11776[(1)] = (32));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (32)))
{var inst_11617 = (state_11675[(20)]);var inst_11616 = (state_11675[(9)]);var inst_11615 = (state_11675[(21)]);var inst_11618 = (state_11675[(11)]);var inst_11630 = (state_11675[(2)]);var inst_11631 = (inst_11618 + (1));var tmp11696 = inst_11617;var tmp11697 = inst_11616;var tmp11698 = inst_11615;var inst_11615__$1 = tmp11698;var inst_11616__$1 = tmp11697;var inst_11617__$1 = tmp11696;var inst_11618__$1 = inst_11631;var state_11675__$1 = (function (){var statearr_11702 = state_11675;(statearr_11702[(22)] = inst_11630);
(statearr_11702[(20)] = inst_11617__$1);
(statearr_11702[(9)] = inst_11616__$1);
(statearr_11702[(21)] = inst_11615__$1);
(statearr_11702[(11)] = inst_11618__$1);
return statearr_11702;
})();var statearr_11703_11777 = state_11675__$1;(statearr_11703_11777[(2)] = null);
(statearr_11703_11777[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (40)))
{var inst_11643 = (state_11675[(23)]);var inst_11647 = done.call(null,null);var inst_11648 = cljs.core.async.untap_STAR_.call(null,m,inst_11643);var state_11675__$1 = (function (){var statearr_11704 = state_11675;(statearr_11704[(24)] = inst_11647);
return statearr_11704;
})();var statearr_11705_11778 = state_11675__$1;(statearr_11705_11778[(2)] = inst_11648);
(statearr_11705_11778[(1)] = (41));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (33)))
{var inst_11634 = (state_11675[(25)]);var inst_11636 = cljs.core.chunked_seq_QMARK_.call(null,inst_11634);var state_11675__$1 = state_11675;if(inst_11636)
{var statearr_11706_11779 = state_11675__$1;(statearr_11706_11779[(1)] = (36));
} else
{var statearr_11707_11780 = state_11675__$1;(statearr_11707_11780[(1)] = (37));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (13)))
{var inst_11566 = (state_11675[(26)]);var inst_11569 = cljs.core.async.close_BANG_.call(null,inst_11566);var state_11675__$1 = state_11675;var statearr_11708_11781 = state_11675__$1;(statearr_11708_11781[(2)] = inst_11569);
(statearr_11708_11781[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (22)))
{var inst_11587 = (state_11675[(8)]);var inst_11590 = cljs.core.async.close_BANG_.call(null,inst_11587);var state_11675__$1 = state_11675;var statearr_11709_11782 = state_11675__$1;(statearr_11709_11782[(2)] = inst_11590);
(statearr_11709_11782[(1)] = (24));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (36)))
{var inst_11634 = (state_11675[(25)]);var inst_11638 = cljs.core.chunk_first.call(null,inst_11634);var inst_11639 = cljs.core.chunk_rest.call(null,inst_11634);var inst_11640 = cljs.core.count.call(null,inst_11638);var inst_11615 = inst_11639;var inst_11616 = inst_11638;var inst_11617 = inst_11640;var inst_11618 = (0);var state_11675__$1 = (function (){var statearr_11710 = state_11675;(statearr_11710[(20)] = inst_11617);
(statearr_11710[(9)] = inst_11616);
(statearr_11710[(21)] = inst_11615);
(statearr_11710[(11)] = inst_11618);
return statearr_11710;
})();var statearr_11711_11783 = state_11675__$1;(statearr_11711_11783[(2)] = null);
(statearr_11711_11783[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (41)))
{var inst_11634 = (state_11675[(25)]);var inst_11650 = (state_11675[(2)]);var inst_11651 = cljs.core.next.call(null,inst_11634);var inst_11615 = inst_11651;var inst_11616 = null;var inst_11617 = (0);var inst_11618 = (0);var state_11675__$1 = (function (){var statearr_11712 = state_11675;(statearr_11712[(20)] = inst_11617);
(statearr_11712[(9)] = inst_11616);
(statearr_11712[(21)] = inst_11615);
(statearr_11712[(11)] = inst_11618);
(statearr_11712[(27)] = inst_11650);
return statearr_11712;
})();var statearr_11713_11784 = state_11675__$1;(statearr_11713_11784[(2)] = null);
(statearr_11713_11784[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (43)))
{var state_11675__$1 = state_11675;var statearr_11714_11785 = state_11675__$1;(statearr_11714_11785[(2)] = null);
(statearr_11714_11785[(1)] = (44));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (29)))
{var inst_11659 = (state_11675[(2)]);var state_11675__$1 = state_11675;var statearr_11715_11786 = state_11675__$1;(statearr_11715_11786[(2)] = inst_11659);
(statearr_11715_11786[(1)] = (26));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (44)))
{var inst_11668 = (state_11675[(2)]);var state_11675__$1 = (function (){var statearr_11716 = state_11675;(statearr_11716[(28)] = inst_11668);
return statearr_11716;
})();var statearr_11717_11787 = state_11675__$1;(statearr_11717_11787[(2)] = null);
(statearr_11717_11787[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (6)))
{var inst_11607 = (state_11675[(29)]);var inst_11606 = cljs.core.deref.call(null,cs);var inst_11607__$1 = cljs.core.keys.call(null,inst_11606);var inst_11608 = cljs.core.count.call(null,inst_11607__$1);var inst_11609 = cljs.core.reset_BANG_.call(null,dctr,inst_11608);var inst_11614 = cljs.core.seq.call(null,inst_11607__$1);var inst_11615 = inst_11614;var inst_11616 = null;var inst_11617 = (0);var inst_11618 = (0);var state_11675__$1 = (function (){var statearr_11718 = state_11675;(statearr_11718[(20)] = inst_11617);
(statearr_11718[(9)] = inst_11616);
(statearr_11718[(30)] = inst_11609);
(statearr_11718[(21)] = inst_11615);
(statearr_11718[(11)] = inst_11618);
(statearr_11718[(29)] = inst_11607__$1);
return statearr_11718;
})();var statearr_11719_11788 = state_11675__$1;(statearr_11719_11788[(2)] = null);
(statearr_11719_11788[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (28)))
{var inst_11615 = (state_11675[(21)]);var inst_11634 = (state_11675[(25)]);var inst_11634__$1 = cljs.core.seq.call(null,inst_11615);var state_11675__$1 = (function (){var statearr_11720 = state_11675;(statearr_11720[(25)] = inst_11634__$1);
return statearr_11720;
})();if(inst_11634__$1)
{var statearr_11721_11789 = state_11675__$1;(statearr_11721_11789[(1)] = (33));
} else
{var statearr_11722_11790 = state_11675__$1;(statearr_11722_11790[(1)] = (34));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (25)))
{var inst_11617 = (state_11675[(20)]);var inst_11618 = (state_11675[(11)]);var inst_11620 = (inst_11618 < inst_11617);var inst_11621 = inst_11620;var state_11675__$1 = state_11675;if(cljs.core.truth_(inst_11621))
{var statearr_11723_11791 = state_11675__$1;(statearr_11723_11791[(1)] = (27));
} else
{var statearr_11724_11792 = state_11675__$1;(statearr_11724_11792[(1)] = (28));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (34)))
{var state_11675__$1 = state_11675;var statearr_11725_11793 = state_11675__$1;(statearr_11725_11793[(2)] = null);
(statearr_11725_11793[(1)] = (35));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (17)))
{var state_11675__$1 = state_11675;var statearr_11726_11794 = state_11675__$1;(statearr_11726_11794[(2)] = null);
(statearr_11726_11794[(1)] = (18));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (3)))
{var inst_11673 = (state_11675[(2)]);var state_11675__$1 = state_11675;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_11675__$1,inst_11673);
} else
{if((state_val_11676 === (12)))
{var inst_11602 = (state_11675[(2)]);var state_11675__$1 = state_11675;var statearr_11727_11795 = state_11675__$1;(statearr_11727_11795[(2)] = inst_11602);
(statearr_11727_11795[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (2)))
{var state_11675__$1 = state_11675;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_11675__$1,(4),ch);
} else
{if((state_val_11676 === (23)))
{var state_11675__$1 = state_11675;var statearr_11728_11796 = state_11675__$1;(statearr_11728_11796[(2)] = null);
(statearr_11728_11796[(1)] = (24));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (35)))
{var inst_11657 = (state_11675[(2)]);var state_11675__$1 = state_11675;var statearr_11729_11797 = state_11675__$1;(statearr_11729_11797[(2)] = inst_11657);
(statearr_11729_11797[(1)] = (29));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (19)))
{var inst_11576 = (state_11675[(7)]);var inst_11580 = cljs.core.chunk_first.call(null,inst_11576);var inst_11581 = cljs.core.chunk_rest.call(null,inst_11576);var inst_11582 = cljs.core.count.call(null,inst_11580);var inst_11556 = inst_11581;var inst_11557 = inst_11580;var inst_11558 = inst_11582;var inst_11559 = (0);var state_11675__$1 = (function (){var statearr_11730 = state_11675;(statearr_11730[(14)] = inst_11556);
(statearr_11730[(15)] = inst_11557);
(statearr_11730[(16)] = inst_11559);
(statearr_11730[(17)] = inst_11558);
return statearr_11730;
})();var statearr_11731_11798 = state_11675__$1;(statearr_11731_11798[(2)] = null);
(statearr_11731_11798[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (11)))
{var inst_11556 = (state_11675[(14)]);var inst_11576 = (state_11675[(7)]);var inst_11576__$1 = cljs.core.seq.call(null,inst_11556);var state_11675__$1 = (function (){var statearr_11732 = state_11675;(statearr_11732[(7)] = inst_11576__$1);
return statearr_11732;
})();if(inst_11576__$1)
{var statearr_11733_11799 = state_11675__$1;(statearr_11733_11799[(1)] = (16));
} else
{var statearr_11734_11800 = state_11675__$1;(statearr_11734_11800[(1)] = (17));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (9)))
{var inst_11604 = (state_11675[(2)]);var state_11675__$1 = state_11675;var statearr_11735_11801 = state_11675__$1;(statearr_11735_11801[(2)] = inst_11604);
(statearr_11735_11801[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (5)))
{var inst_11554 = cljs.core.deref.call(null,cs);var inst_11555 = cljs.core.seq.call(null,inst_11554);var inst_11556 = inst_11555;var inst_11557 = null;var inst_11558 = (0);var inst_11559 = (0);var state_11675__$1 = (function (){var statearr_11736 = state_11675;(statearr_11736[(14)] = inst_11556);
(statearr_11736[(15)] = inst_11557);
(statearr_11736[(16)] = inst_11559);
(statearr_11736[(17)] = inst_11558);
return statearr_11736;
})();var statearr_11737_11802 = state_11675__$1;(statearr_11737_11802[(2)] = null);
(statearr_11737_11802[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (14)))
{var state_11675__$1 = state_11675;var statearr_11738_11803 = state_11675__$1;(statearr_11738_11803[(2)] = null);
(statearr_11738_11803[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (45)))
{var inst_11665 = (state_11675[(2)]);var state_11675__$1 = state_11675;var statearr_11739_11804 = state_11675__$1;(statearr_11739_11804[(2)] = inst_11665);
(statearr_11739_11804[(1)] = (44));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (26)))
{var inst_11607 = (state_11675[(29)]);var inst_11661 = (state_11675[(2)]);var inst_11662 = cljs.core.seq.call(null,inst_11607);var state_11675__$1 = (function (){var statearr_11740 = state_11675;(statearr_11740[(31)] = inst_11661);
return statearr_11740;
})();if(inst_11662)
{var statearr_11741_11805 = state_11675__$1;(statearr_11741_11805[(1)] = (42));
} else
{var statearr_11742_11806 = state_11675__$1;(statearr_11742_11806[(1)] = (43));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (16)))
{var inst_11576 = (state_11675[(7)]);var inst_11578 = cljs.core.chunked_seq_QMARK_.call(null,inst_11576);var state_11675__$1 = state_11675;if(inst_11578)
{var statearr_11743_11807 = state_11675__$1;(statearr_11743_11807[(1)] = (19));
} else
{var statearr_11744_11808 = state_11675__$1;(statearr_11744_11808[(1)] = (20));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (38)))
{var inst_11654 = (state_11675[(2)]);var state_11675__$1 = state_11675;var statearr_11745_11809 = state_11675__$1;(statearr_11745_11809[(2)] = inst_11654);
(statearr_11745_11809[(1)] = (35));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (30)))
{var state_11675__$1 = state_11675;var statearr_11746_11810 = state_11675__$1;(statearr_11746_11810[(2)] = null);
(statearr_11746_11810[(1)] = (32));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (10)))
{var inst_11557 = (state_11675[(15)]);var inst_11559 = (state_11675[(16)]);var inst_11565 = cljs.core._nth.call(null,inst_11557,inst_11559);var inst_11566 = cljs.core.nth.call(null,inst_11565,(0),null);var inst_11567 = cljs.core.nth.call(null,inst_11565,(1),null);var state_11675__$1 = (function (){var statearr_11747 = state_11675;(statearr_11747[(26)] = inst_11566);
return statearr_11747;
})();if(cljs.core.truth_(inst_11567))
{var statearr_11748_11811 = state_11675__$1;(statearr_11748_11811[(1)] = (13));
} else
{var statearr_11749_11812 = state_11675__$1;(statearr_11749_11812[(1)] = (14));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (18)))
{var inst_11600 = (state_11675[(2)]);var state_11675__$1 = state_11675;var statearr_11750_11813 = state_11675__$1;(statearr_11750_11813[(2)] = inst_11600);
(statearr_11750_11813[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (42)))
{var state_11675__$1 = state_11675;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_11675__$1,(45),dchan);
} else
{if((state_val_11676 === (37)))
{var inst_11643 = (state_11675[(23)]);var inst_11547 = (state_11675[(10)]);var inst_11634 = (state_11675[(25)]);var inst_11643__$1 = cljs.core.first.call(null,inst_11634);var inst_11644 = cljs.core.async.put_BANG_.call(null,inst_11643__$1,inst_11547,done);var state_11675__$1 = (function (){var statearr_11751 = state_11675;(statearr_11751[(23)] = inst_11643__$1);
return statearr_11751;
})();if(cljs.core.truth_(inst_11644))
{var statearr_11752_11814 = state_11675__$1;(statearr_11752_11814[(1)] = (39));
} else
{var statearr_11753_11815 = state_11675__$1;(statearr_11753_11815[(1)] = (40));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_11676 === (8)))
{var inst_11559 = (state_11675[(16)]);var inst_11558 = (state_11675[(17)]);var inst_11561 = (inst_11559 < inst_11558);var inst_11562 = inst_11561;var state_11675__$1 = state_11675;if(cljs.core.truth_(inst_11562))
{var statearr_11754_11816 = state_11675__$1;(statearr_11754_11816[(1)] = (10));
} else
{var statearr_11755_11817 = state_11675__$1;(statearr_11755_11817[(1)] = (11));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___11763,cs,m,dchan,dctr,done))
;return ((function (switch__5702__auto__,c__5717__auto___11763,cs,m,dchan,dctr,done){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_11759 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_11759[(0)] = state_machine__5703__auto__);
(statearr_11759[(1)] = (1));
return statearr_11759;
});
var state_machine__5703__auto____1 = (function (state_11675){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_11675);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e11760){if((e11760 instanceof Object))
{var ex__5706__auto__ = e11760;var statearr_11761_11818 = state_11675;(statearr_11761_11818[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_11675);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e11760;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__11819 = state_11675;
state_11675 = G__11819;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_11675){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_11675);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___11763,cs,m,dchan,dctr,done))
})();var state__5719__auto__ = (function (){var statearr_11762 = f__5718__auto__.call(null);(statearr_11762[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___11763);
return statearr_11762;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___11763,cs,m,dchan,dctr,done))
);
return m;
});
/**
* Copies the mult source onto the supplied channel.
* 
* By default the channel will be closed when the source closes,
* but can be determined by the close? parameter.
*/
cljs.core.async.tap = (function() {
var tap = null;
var tap__2 = (function (mult,ch){return tap.call(null,mult,ch,true);
});
var tap__3 = (function (mult,ch,close_QMARK_){cljs.core.async.tap_STAR_.call(null,mult,ch,close_QMARK_);
return ch;
});
tap = function(mult,ch,close_QMARK_){
switch(arguments.length){
case 2:
return tap__2.call(this,mult,ch);
case 3:
return tap__3.call(this,mult,ch,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
tap.cljs$core$IFn$_invoke$arity$2 = tap__2;
tap.cljs$core$IFn$_invoke$arity$3 = tap__3;
return tap;
})()
;
/**
* Disconnects a target channel from a mult
*/
cljs.core.async.untap = (function untap(mult,ch){return cljs.core.async.untap_STAR_.call(null,mult,ch);
});
/**
* Disconnects all target channels from a mult
*/
cljs.core.async.untap_all = (function untap_all(mult){return cljs.core.async.untap_all_STAR_.call(null,mult);
});
cljs.core.async.Mix = (function (){var obj11821 = {};return obj11821;
})();
cljs.core.async.admix_STAR_ = (function admix_STAR_(m,ch){if((function (){var and__3541__auto__ = m;if(and__3541__auto__)
{return m.cljs$core$async$Mix$admix_STAR_$arity$2;
} else
{return and__3541__auto__;
}
})())
{return m.cljs$core$async$Mix$admix_STAR_$arity$2(m,ch);
} else
{var x__4180__auto__ = (((m == null))?null:m);return (function (){var or__3553__auto__ = (cljs.core.async.admix_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.admix_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.admix*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.unmix_STAR_ = (function unmix_STAR_(m,ch){if((function (){var and__3541__auto__ = m;if(and__3541__auto__)
{return m.cljs$core$async$Mix$unmix_STAR_$arity$2;
} else
{return and__3541__auto__;
}
})())
{return m.cljs$core$async$Mix$unmix_STAR_$arity$2(m,ch);
} else
{var x__4180__auto__ = (((m == null))?null:m);return (function (){var or__3553__auto__ = (cljs.core.async.unmix_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.unmix_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.unmix*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.unmix_all_STAR_ = (function unmix_all_STAR_(m){if((function (){var and__3541__auto__ = m;if(and__3541__auto__)
{return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1;
} else
{return and__3541__auto__;
}
})())
{return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1(m);
} else
{var x__4180__auto__ = (((m == null))?null:m);return (function (){var or__3553__auto__ = (cljs.core.async.unmix_all_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.unmix_all_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.unmix-all*",m);
}
}
})().call(null,m);
}
});
cljs.core.async.toggle_STAR_ = (function toggle_STAR_(m,state_map){if((function (){var and__3541__auto__ = m;if(and__3541__auto__)
{return m.cljs$core$async$Mix$toggle_STAR_$arity$2;
} else
{return and__3541__auto__;
}
})())
{return m.cljs$core$async$Mix$toggle_STAR_$arity$2(m,state_map);
} else
{var x__4180__auto__ = (((m == null))?null:m);return (function (){var or__3553__auto__ = (cljs.core.async.toggle_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.toggle_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.toggle*",m);
}
}
})().call(null,m,state_map);
}
});
cljs.core.async.solo_mode_STAR_ = (function solo_mode_STAR_(m,mode){if((function (){var and__3541__auto__ = m;if(and__3541__auto__)
{return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2;
} else
{return and__3541__auto__;
}
})())
{return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2(m,mode);
} else
{var x__4180__auto__ = (((m == null))?null:m);return (function (){var or__3553__auto__ = (cljs.core.async.solo_mode_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.solo_mode_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.solo-mode*",m);
}
}
})().call(null,m,mode);
}
});
/**
* Creates and returns a mix of one or more input channels which will
* be put on the supplied out channel. Input sources can be added to
* the mix with 'admix', and removed with 'unmix'. A mix supports
* soloing, muting and pausing multiple inputs atomically using
* 'toggle', and can solo using either muting or pausing as determined
* by 'solo-mode'.
* 
* Each channel can have zero or more boolean modes set via 'toggle':
* 
* :solo - when true, only this (ond other soloed) channel(s) will appear
* in the mix output channel. :mute and :pause states of soloed
* channels are ignored. If solo-mode is :mute, non-soloed
* channels are muted, if :pause, non-soloed channels are
* paused.
* 
* :mute - muted channels will have their contents consumed but not included in the mix
* :pause - paused channels will not have their contents consumed (and thus also not included in the mix)
*/
cljs.core.async.mix = (function mix(out){var cs = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);var solo_modes = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"pause","pause",-2095325672),null,new cljs.core.Keyword(null,"mute","mute",1151223646),null], null), null);var attrs = cljs.core.conj.call(null,solo_modes,new cljs.core.Keyword(null,"solo","solo",-316350075));var solo_mode = cljs.core.atom.call(null,new cljs.core.Keyword(null,"mute","mute",1151223646));var change = cljs.core.async.chan.call(null);var changed = ((function (cs,solo_modes,attrs,solo_mode,change){
return (function (){return cljs.core.async.put_BANG_.call(null,change,true);
});})(cs,solo_modes,attrs,solo_mode,change))
;var pick = ((function (cs,solo_modes,attrs,solo_mode,change,changed){
return (function (attr,chs){return cljs.core.reduce_kv.call(null,((function (cs,solo_modes,attrs,solo_mode,change,changed){
return (function (ret,c,v){if(cljs.core.truth_(attr.call(null,v)))
{return cljs.core.conj.call(null,ret,c);
} else
{return ret;
}
});})(cs,solo_modes,attrs,solo_mode,change,changed))
,cljs.core.PersistentHashSet.EMPTY,chs);
});})(cs,solo_modes,attrs,solo_mode,change,changed))
;var calc_state = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick){
return (function (){var chs = cljs.core.deref.call(null,cs);var mode = cljs.core.deref.call(null,solo_mode);var solos = pick.call(null,new cljs.core.Keyword(null,"solo","solo",-316350075),chs);var pauses = pick.call(null,new cljs.core.Keyword(null,"pause","pause",-2095325672),chs);return new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"solos","solos",1441458643),solos,new cljs.core.Keyword(null,"mutes","mutes",1068806309),pick.call(null,new cljs.core.Keyword(null,"mute","mute",1151223646),chs),new cljs.core.Keyword(null,"reads","reads",-1215067361),cljs.core.conj.call(null,(((cljs.core._EQ_.call(null,mode,new cljs.core.Keyword(null,"pause","pause",-2095325672))) && (!(cljs.core.empty_QMARK_.call(null,solos))))?cljs.core.vec.call(null,solos):cljs.core.vec.call(null,cljs.core.remove.call(null,pauses,cljs.core.keys.call(null,chs)))),change)], null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick))
;var m = (function (){if(typeof cljs.core.async.t11941 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t11941 = (function (change,mix,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,meta11942){
this.change = change;
this.mix = mix;
this.solo_mode = solo_mode;
this.pick = pick;
this.cs = cs;
this.calc_state = calc_state;
this.out = out;
this.changed = changed;
this.solo_modes = solo_modes;
this.attrs = attrs;
this.meta11942 = meta11942;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t11941.cljs$lang$type = true;
cljs.core.async.t11941.cljs$lang$ctorStr = "cljs.core.async/t11941";
cljs.core.async.t11941.cljs$lang$ctorPrWriter = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t11941");
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11941.prototype.cljs$core$async$Mix$ = true;
cljs.core.async.t11941.prototype.cljs$core$async$Mix$admix_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,ch){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.assoc,ch,cljs.core.PersistentArrayMap.EMPTY);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11941.prototype.cljs$core$async$Mix$unmix_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,ch){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.dissoc,ch);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11941.prototype.cljs$core$async$Mix$unmix_all_STAR_$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_){var self__ = this;
var ___$1 = this;cljs.core.reset_BANG_.call(null,self__.cs,cljs.core.PersistentArrayMap.EMPTY);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11941.prototype.cljs$core$async$Mix$toggle_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,state_map){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.partial.call(null,cljs.core.merge_with,cljs.core.merge),state_map);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11941.prototype.cljs$core$async$Mix$solo_mode_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,mode){var self__ = this;
var ___$1 = this;if(cljs.core.truth_(self__.solo_modes.call(null,mode)))
{} else
{throw (new Error(("Assert failed: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(("mode must be one of: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(self__.solo_modes)))+"\n"+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.call(null,cljs.core.list(new cljs.core.Symbol(null,"solo-modes","solo-modes",882180540,null),new cljs.core.Symbol(null,"mode","mode",-2000032078,null)))))));
}
cljs.core.reset_BANG_.call(null,self__.solo_mode,mode);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11941.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t11941.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_){var self__ = this;
var ___$1 = this;return self__.out;
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11941.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_11943){var self__ = this;
var _11943__$1 = this;return self__.meta11942;
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11941.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_11943,meta11942__$1){var self__ = this;
var _11943__$1 = this;return (new cljs.core.async.t11941(self__.change,self__.mix,self__.solo_mode,self__.pick,self__.cs,self__.calc_state,self__.out,self__.changed,self__.solo_modes,self__.attrs,meta11942__$1));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.__GT_t11941 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function __GT_t11941(change__$1,mix__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta11942){return (new cljs.core.async.t11941(change__$1,mix__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta11942));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
}
return (new cljs.core.async.t11941(change,mix,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,null));
})();var c__5717__auto___12060 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___12060,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___12060,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function (state_12013){var state_val_12014 = (state_12013[(1)]);if((state_val_12014 === (7)))
{var inst_11957 = (state_12013[(7)]);var inst_11962 = cljs.core.apply.call(null,cljs.core.hash_map,inst_11957);var state_12013__$1 = state_12013;var statearr_12015_12061 = state_12013__$1;(statearr_12015_12061[(2)] = inst_11962);
(statearr_12015_12061[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (20)))
{var inst_11972 = (state_12013[(8)]);var state_12013__$1 = state_12013;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12013__$1,(23),out,inst_11972);
} else
{if((state_val_12014 === (1)))
{var inst_11947 = (state_12013[(9)]);var inst_11947__$1 = calc_state.call(null);var inst_11948 = cljs.core.seq_QMARK_.call(null,inst_11947__$1);var state_12013__$1 = (function (){var statearr_12016 = state_12013;(statearr_12016[(9)] = inst_11947__$1);
return statearr_12016;
})();if(inst_11948)
{var statearr_12017_12062 = state_12013__$1;(statearr_12017_12062[(1)] = (2));
} else
{var statearr_12018_12063 = state_12013__$1;(statearr_12018_12063[(1)] = (3));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (24)))
{var inst_11965 = (state_12013[(10)]);var inst_11957 = inst_11965;var state_12013__$1 = (function (){var statearr_12019 = state_12013;(statearr_12019[(7)] = inst_11957);
return statearr_12019;
})();var statearr_12020_12064 = state_12013__$1;(statearr_12020_12064[(2)] = null);
(statearr_12020_12064[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (4)))
{var inst_11947 = (state_12013[(9)]);var inst_11953 = (state_12013[(2)]);var inst_11954 = cljs.core.get.call(null,inst_11953,new cljs.core.Keyword(null,"reads","reads",-1215067361));var inst_11955 = cljs.core.get.call(null,inst_11953,new cljs.core.Keyword(null,"mutes","mutes",1068806309));var inst_11956 = cljs.core.get.call(null,inst_11953,new cljs.core.Keyword(null,"solos","solos",1441458643));var inst_11957 = inst_11947;var state_12013__$1 = (function (){var statearr_12021 = state_12013;(statearr_12021[(7)] = inst_11957);
(statearr_12021[(11)] = inst_11955);
(statearr_12021[(12)] = inst_11956);
(statearr_12021[(13)] = inst_11954);
return statearr_12021;
})();var statearr_12022_12065 = state_12013__$1;(statearr_12022_12065[(2)] = null);
(statearr_12022_12065[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (15)))
{var state_12013__$1 = state_12013;var statearr_12023_12066 = state_12013__$1;(statearr_12023_12066[(2)] = null);
(statearr_12023_12066[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (21)))
{var inst_11965 = (state_12013[(10)]);var inst_11957 = inst_11965;var state_12013__$1 = (function (){var statearr_12024 = state_12013;(statearr_12024[(7)] = inst_11957);
return statearr_12024;
})();var statearr_12025_12067 = state_12013__$1;(statearr_12025_12067[(2)] = null);
(statearr_12025_12067[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (13)))
{var inst_12009 = (state_12013[(2)]);var state_12013__$1 = state_12013;var statearr_12026_12068 = state_12013__$1;(statearr_12026_12068[(2)] = inst_12009);
(statearr_12026_12068[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (22)))
{var inst_12007 = (state_12013[(2)]);var state_12013__$1 = state_12013;var statearr_12027_12069 = state_12013__$1;(statearr_12027_12069[(2)] = inst_12007);
(statearr_12027_12069[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (6)))
{var inst_12011 = (state_12013[(2)]);var state_12013__$1 = state_12013;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12013__$1,inst_12011);
} else
{if((state_val_12014 === (25)))
{var state_12013__$1 = state_12013;var statearr_12028_12070 = state_12013__$1;(statearr_12028_12070[(2)] = null);
(statearr_12028_12070[(1)] = (26));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (17)))
{var inst_11987 = (state_12013[(14)]);var state_12013__$1 = state_12013;var statearr_12029_12071 = state_12013__$1;(statearr_12029_12071[(2)] = inst_11987);
(statearr_12029_12071[(1)] = (19));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (3)))
{var inst_11947 = (state_12013[(9)]);var state_12013__$1 = state_12013;var statearr_12030_12072 = state_12013__$1;(statearr_12030_12072[(2)] = inst_11947);
(statearr_12030_12072[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (12)))
{var inst_11968 = (state_12013[(15)]);var inst_11987 = (state_12013[(14)]);var inst_11973 = (state_12013[(16)]);var inst_11987__$1 = inst_11968.call(null,inst_11973);var state_12013__$1 = (function (){var statearr_12031 = state_12013;(statearr_12031[(14)] = inst_11987__$1);
return statearr_12031;
})();if(cljs.core.truth_(inst_11987__$1))
{var statearr_12032_12073 = state_12013__$1;(statearr_12032_12073[(1)] = (17));
} else
{var statearr_12033_12074 = state_12013__$1;(statearr_12033_12074[(1)] = (18));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (2)))
{var inst_11947 = (state_12013[(9)]);var inst_11950 = cljs.core.apply.call(null,cljs.core.hash_map,inst_11947);var state_12013__$1 = state_12013;var statearr_12034_12075 = state_12013__$1;(statearr_12034_12075[(2)] = inst_11950);
(statearr_12034_12075[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (23)))
{var inst_11998 = (state_12013[(2)]);var state_12013__$1 = state_12013;if(cljs.core.truth_(inst_11998))
{var statearr_12035_12076 = state_12013__$1;(statearr_12035_12076[(1)] = (24));
} else
{var statearr_12036_12077 = state_12013__$1;(statearr_12036_12077[(1)] = (25));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (19)))
{var inst_11995 = (state_12013[(2)]);var state_12013__$1 = state_12013;if(cljs.core.truth_(inst_11995))
{var statearr_12037_12078 = state_12013__$1;(statearr_12037_12078[(1)] = (20));
} else
{var statearr_12038_12079 = state_12013__$1;(statearr_12038_12079[(1)] = (21));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (11)))
{var inst_11972 = (state_12013[(8)]);var inst_11978 = (inst_11972 == null);var state_12013__$1 = state_12013;if(cljs.core.truth_(inst_11978))
{var statearr_12039_12080 = state_12013__$1;(statearr_12039_12080[(1)] = (14));
} else
{var statearr_12040_12081 = state_12013__$1;(statearr_12040_12081[(1)] = (15));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (9)))
{var inst_11965 = (state_12013[(10)]);var inst_11965__$1 = (state_12013[(2)]);var inst_11966 = cljs.core.get.call(null,inst_11965__$1,new cljs.core.Keyword(null,"reads","reads",-1215067361));var inst_11967 = cljs.core.get.call(null,inst_11965__$1,new cljs.core.Keyword(null,"mutes","mutes",1068806309));var inst_11968 = cljs.core.get.call(null,inst_11965__$1,new cljs.core.Keyword(null,"solos","solos",1441458643));var state_12013__$1 = (function (){var statearr_12041 = state_12013;(statearr_12041[(15)] = inst_11968);
(statearr_12041[(17)] = inst_11967);
(statearr_12041[(10)] = inst_11965__$1);
return statearr_12041;
})();return cljs.core.async.impl.ioc_helpers.ioc_alts_BANG_.call(null,state_12013__$1,(10),inst_11966);
} else
{if((state_val_12014 === (5)))
{var inst_11957 = (state_12013[(7)]);var inst_11960 = cljs.core.seq_QMARK_.call(null,inst_11957);var state_12013__$1 = state_12013;if(inst_11960)
{var statearr_12042_12082 = state_12013__$1;(statearr_12042_12082[(1)] = (7));
} else
{var statearr_12043_12083 = state_12013__$1;(statearr_12043_12083[(1)] = (8));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (14)))
{var inst_11973 = (state_12013[(16)]);var inst_11980 = cljs.core.swap_BANG_.call(null,cs,cljs.core.dissoc,inst_11973);var state_12013__$1 = state_12013;var statearr_12044_12084 = state_12013__$1;(statearr_12044_12084[(2)] = inst_11980);
(statearr_12044_12084[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (26)))
{var inst_12003 = (state_12013[(2)]);var state_12013__$1 = state_12013;var statearr_12045_12085 = state_12013__$1;(statearr_12045_12085[(2)] = inst_12003);
(statearr_12045_12085[(1)] = (22));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (16)))
{var inst_11983 = (state_12013[(2)]);var inst_11984 = calc_state.call(null);var inst_11957 = inst_11984;var state_12013__$1 = (function (){var statearr_12046 = state_12013;(statearr_12046[(7)] = inst_11957);
(statearr_12046[(18)] = inst_11983);
return statearr_12046;
})();var statearr_12047_12086 = state_12013__$1;(statearr_12047_12086[(2)] = null);
(statearr_12047_12086[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (10)))
{var inst_11972 = (state_12013[(8)]);var inst_11973 = (state_12013[(16)]);var inst_11971 = (state_12013[(2)]);var inst_11972__$1 = cljs.core.nth.call(null,inst_11971,(0),null);var inst_11973__$1 = cljs.core.nth.call(null,inst_11971,(1),null);var inst_11974 = (inst_11972__$1 == null);var inst_11975 = cljs.core._EQ_.call(null,inst_11973__$1,change);var inst_11976 = (inst_11974) || (inst_11975);var state_12013__$1 = (function (){var statearr_12048 = state_12013;(statearr_12048[(8)] = inst_11972__$1);
(statearr_12048[(16)] = inst_11973__$1);
return statearr_12048;
})();if(cljs.core.truth_(inst_11976))
{var statearr_12049_12087 = state_12013__$1;(statearr_12049_12087[(1)] = (11));
} else
{var statearr_12050_12088 = state_12013__$1;(statearr_12050_12088[(1)] = (12));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (18)))
{var inst_11968 = (state_12013[(15)]);var inst_11967 = (state_12013[(17)]);var inst_11973 = (state_12013[(16)]);var inst_11990 = cljs.core.empty_QMARK_.call(null,inst_11968);var inst_11991 = inst_11967.call(null,inst_11973);var inst_11992 = cljs.core.not.call(null,inst_11991);var inst_11993 = (inst_11990) && (inst_11992);var state_12013__$1 = state_12013;var statearr_12051_12089 = state_12013__$1;(statearr_12051_12089[(2)] = inst_11993);
(statearr_12051_12089[(1)] = (19));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12014 === (8)))
{var inst_11957 = (state_12013[(7)]);var state_12013__$1 = state_12013;var statearr_12052_12090 = state_12013__$1;(statearr_12052_12090[(2)] = inst_11957);
(statearr_12052_12090[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___12060,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
;return ((function (switch__5702__auto__,c__5717__auto___12060,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_12056 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_12056[(0)] = state_machine__5703__auto__);
(statearr_12056[(1)] = (1));
return statearr_12056;
});
var state_machine__5703__auto____1 = (function (state_12013){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_12013);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e12057){if((e12057 instanceof Object))
{var ex__5706__auto__ = e12057;var statearr_12058_12091 = state_12013;(statearr_12058_12091[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12013);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12057;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12092 = state_12013;
state_12013 = G__12092;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_12013){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_12013);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___12060,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
})();var state__5719__auto__ = (function (){var statearr_12059 = f__5718__auto__.call(null);(statearr_12059[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___12060);
return statearr_12059;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___12060,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
);
return m;
});
/**
* Adds ch as an input to the mix
*/
cljs.core.async.admix = (function admix(mix,ch){return cljs.core.async.admix_STAR_.call(null,mix,ch);
});
/**
* Removes ch as an input to the mix
*/
cljs.core.async.unmix = (function unmix(mix,ch){return cljs.core.async.unmix_STAR_.call(null,mix,ch);
});
/**
* removes all inputs from the mix
*/
cljs.core.async.unmix_all = (function unmix_all(mix){return cljs.core.async.unmix_all_STAR_.call(null,mix);
});
/**
* Atomically sets the state(s) of one or more channels in a mix. The
* state map is a map of channels -> channel-state-map. A
* channel-state-map is a map of attrs -> boolean, where attr is one or
* more of :mute, :pause or :solo. Any states supplied are merged with
* the current state.
* 
* Note that channels can be added to a mix via toggle, which can be
* used to add channels in a particular (e.g. paused) state.
*/
cljs.core.async.toggle = (function toggle(mix,state_map){return cljs.core.async.toggle_STAR_.call(null,mix,state_map);
});
/**
* Sets the solo mode of the mix. mode must be one of :mute or :pause
*/
cljs.core.async.solo_mode = (function solo_mode(mix,mode){return cljs.core.async.solo_mode_STAR_.call(null,mix,mode);
});
cljs.core.async.Pub = (function (){var obj12094 = {};return obj12094;
})();
cljs.core.async.sub_STAR_ = (function sub_STAR_(p,v,ch,close_QMARK_){if((function (){var and__3541__auto__ = p;if(and__3541__auto__)
{return p.cljs$core$async$Pub$sub_STAR_$arity$4;
} else
{return and__3541__auto__;
}
})())
{return p.cljs$core$async$Pub$sub_STAR_$arity$4(p,v,ch,close_QMARK_);
} else
{var x__4180__auto__ = (((p == null))?null:p);return (function (){var or__3553__auto__ = (cljs.core.async.sub_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.sub_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.sub*",p);
}
}
})().call(null,p,v,ch,close_QMARK_);
}
});
cljs.core.async.unsub_STAR_ = (function unsub_STAR_(p,v,ch){if((function (){var and__3541__auto__ = p;if(and__3541__auto__)
{return p.cljs$core$async$Pub$unsub_STAR_$arity$3;
} else
{return and__3541__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_STAR_$arity$3(p,v,ch);
} else
{var x__4180__auto__ = (((p == null))?null:p);return (function (){var or__3553__auto__ = (cljs.core.async.unsub_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.unsub_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.unsub*",p);
}
}
})().call(null,p,v,ch);
}
});
cljs.core.async.unsub_all_STAR_ = (function() {
var unsub_all_STAR_ = null;
var unsub_all_STAR___1 = (function (p){if((function (){var and__3541__auto__ = p;if(and__3541__auto__)
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$1;
} else
{return and__3541__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$1(p);
} else
{var x__4180__auto__ = (((p == null))?null:p);return (function (){var or__3553__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.unsub_all_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.unsub-all*",p);
}
}
})().call(null,p);
}
});
var unsub_all_STAR___2 = (function (p,v){if((function (){var and__3541__auto__ = p;if(and__3541__auto__)
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2;
} else
{return and__3541__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2(p,v);
} else
{var x__4180__auto__ = (((p == null))?null:p);return (function (){var or__3553__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__4180__auto__)]);if(or__3553__auto__)
{return or__3553__auto__;
} else
{var or__3553__auto____$1 = (cljs.core.async.unsub_all_STAR_["_"]);if(or__3553__auto____$1)
{return or__3553__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.unsub-all*",p);
}
}
})().call(null,p,v);
}
});
unsub_all_STAR_ = function(p,v){
switch(arguments.length){
case 1:
return unsub_all_STAR___1.call(this,p);
case 2:
return unsub_all_STAR___2.call(this,p,v);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
unsub_all_STAR_.cljs$core$IFn$_invoke$arity$1 = unsub_all_STAR___1;
unsub_all_STAR_.cljs$core$IFn$_invoke$arity$2 = unsub_all_STAR___2;
return unsub_all_STAR_;
})()
;
/**
* Creates and returns a pub(lication) of the supplied channel,
* partitioned into topics by the topic-fn. topic-fn will be applied to
* each value on the channel and the result will determine the 'topic'
* on which that value will be put. Channels can be subscribed to
* receive copies of topics using 'sub', and unsubscribed using
* 'unsub'. Each topic will be handled by an internal mult on a
* dedicated channel. By default these internal channels are
* unbuffered, but a buf-fn can be supplied which, given a topic,
* creates a buffer with desired properties.
* 
* Each item is distributed to all subs in parallel and synchronously,
* i.e. each sub must accept before the next item is distributed. Use
* buffering/windowing to prevent slow subs from holding up the pub.
* 
* Items received when there are no matching subs get dropped.
* 
* Note that if buf-fns are used then each topic is handled
* asynchronously, i.e. if a channel is subscribed to more than one
* topic it should not expect them to be interleaved identically with
* the source.
*/
cljs.core.async.pub = (function() {
var pub = null;
var pub__2 = (function (ch,topic_fn){return pub.call(null,ch,topic_fn,cljs.core.constantly.call(null,null));
});
var pub__3 = (function (ch,topic_fn,buf_fn){var mults = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);var ensure_mult = ((function (mults){
return (function (topic){var or__3553__auto__ = cljs.core.get.call(null,cljs.core.deref.call(null,mults),topic);if(cljs.core.truth_(or__3553__auto__))
{return or__3553__auto__;
} else
{return cljs.core.get.call(null,cljs.core.swap_BANG_.call(null,mults,((function (or__3553__auto__,mults){
return (function (p1__12095_SHARP_){if(cljs.core.truth_(p1__12095_SHARP_.call(null,topic)))
{return p1__12095_SHARP_;
} else
{return cljs.core.assoc.call(null,p1__12095_SHARP_,topic,cljs.core.async.mult.call(null,cljs.core.async.chan.call(null,buf_fn.call(null,topic))));
}
});})(or__3553__auto__,mults))
),topic);
}
});})(mults))
;var p = (function (){if(typeof cljs.core.async.t12218 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12218 = (function (ensure_mult,mults,buf_fn,topic_fn,ch,pub,meta12219){
this.ensure_mult = ensure_mult;
this.mults = mults;
this.buf_fn = buf_fn;
this.topic_fn = topic_fn;
this.ch = ch;
this.pub = pub;
this.meta12219 = meta12219;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12218.cljs$lang$type = true;
cljs.core.async.t12218.cljs$lang$ctorStr = "cljs.core.async/t12218";
cljs.core.async.t12218.cljs$lang$ctorPrWriter = ((function (mults,ensure_mult){
return (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t12218");
});})(mults,ensure_mult))
;
cljs.core.async.t12218.prototype.cljs$core$async$Pub$ = true;
cljs.core.async.t12218.prototype.cljs$core$async$Pub$sub_STAR_$arity$4 = ((function (mults,ensure_mult){
return (function (p,topic,ch__$2,close_QMARK_){var self__ = this;
var p__$1 = this;var m = self__.ensure_mult.call(null,topic);return cljs.core.async.tap.call(null,m,ch__$2,close_QMARK_);
});})(mults,ensure_mult))
;
cljs.core.async.t12218.prototype.cljs$core$async$Pub$unsub_STAR_$arity$3 = ((function (mults,ensure_mult){
return (function (p,topic,ch__$2){var self__ = this;
var p__$1 = this;var temp__4126__auto__ = cljs.core.get.call(null,cljs.core.deref.call(null,self__.mults),topic);if(cljs.core.truth_(temp__4126__auto__))
{var m = temp__4126__auto__;return cljs.core.async.untap.call(null,m,ch__$2);
} else
{return null;
}
});})(mults,ensure_mult))
;
cljs.core.async.t12218.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$1 = ((function (mults,ensure_mult){
return (function (_){var self__ = this;
var ___$1 = this;return cljs.core.reset_BANG_.call(null,self__.mults,cljs.core.PersistentArrayMap.EMPTY);
});})(mults,ensure_mult))
;
cljs.core.async.t12218.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$2 = ((function (mults,ensure_mult){
return (function (_,topic){var self__ = this;
var ___$1 = this;return cljs.core.swap_BANG_.call(null,self__.mults,cljs.core.dissoc,topic);
});})(mults,ensure_mult))
;
cljs.core.async.t12218.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t12218.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (mults,ensure_mult){
return (function (_){var self__ = this;
var ___$1 = this;return self__.ch;
});})(mults,ensure_mult))
;
cljs.core.async.t12218.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (mults,ensure_mult){
return (function (_12220){var self__ = this;
var _12220__$1 = this;return self__.meta12219;
});})(mults,ensure_mult))
;
cljs.core.async.t12218.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (mults,ensure_mult){
return (function (_12220,meta12219__$1){var self__ = this;
var _12220__$1 = this;return (new cljs.core.async.t12218(self__.ensure_mult,self__.mults,self__.buf_fn,self__.topic_fn,self__.ch,self__.pub,meta12219__$1));
});})(mults,ensure_mult))
;
cljs.core.async.__GT_t12218 = ((function (mults,ensure_mult){
return (function __GT_t12218(ensure_mult__$1,mults__$1,buf_fn__$1,topic_fn__$1,ch__$1,pub__$1,meta12219){return (new cljs.core.async.t12218(ensure_mult__$1,mults__$1,buf_fn__$1,topic_fn__$1,ch__$1,pub__$1,meta12219));
});})(mults,ensure_mult))
;
}
return (new cljs.core.async.t12218(ensure_mult,mults,buf_fn,topic_fn,ch,pub,null));
})();var c__5717__auto___12340 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___12340,mults,ensure_mult,p){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___12340,mults,ensure_mult,p){
return (function (state_12292){var state_val_12293 = (state_12292[(1)]);if((state_val_12293 === (7)))
{var inst_12288 = (state_12292[(2)]);var state_12292__$1 = state_12292;var statearr_12294_12341 = state_12292__$1;(statearr_12294_12341[(2)] = inst_12288);
(statearr_12294_12341[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (20)))
{var state_12292__$1 = state_12292;var statearr_12295_12342 = state_12292__$1;(statearr_12295_12342[(2)] = null);
(statearr_12295_12342[(1)] = (21));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (1)))
{var state_12292__$1 = state_12292;var statearr_12296_12343 = state_12292__$1;(statearr_12296_12343[(2)] = null);
(statearr_12296_12343[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (24)))
{var inst_12271 = (state_12292[(7)]);var inst_12280 = cljs.core.swap_BANG_.call(null,mults,cljs.core.dissoc,inst_12271);var state_12292__$1 = state_12292;var statearr_12297_12344 = state_12292__$1;(statearr_12297_12344[(2)] = inst_12280);
(statearr_12297_12344[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (4)))
{var inst_12223 = (state_12292[(8)]);var inst_12223__$1 = (state_12292[(2)]);var inst_12224 = (inst_12223__$1 == null);var state_12292__$1 = (function (){var statearr_12298 = state_12292;(statearr_12298[(8)] = inst_12223__$1);
return statearr_12298;
})();if(cljs.core.truth_(inst_12224))
{var statearr_12299_12345 = state_12292__$1;(statearr_12299_12345[(1)] = (5));
} else
{var statearr_12300_12346 = state_12292__$1;(statearr_12300_12346[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (15)))
{var inst_12265 = (state_12292[(2)]);var state_12292__$1 = state_12292;var statearr_12301_12347 = state_12292__$1;(statearr_12301_12347[(2)] = inst_12265);
(statearr_12301_12347[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (21)))
{var inst_12285 = (state_12292[(2)]);var state_12292__$1 = (function (){var statearr_12302 = state_12292;(statearr_12302[(9)] = inst_12285);
return statearr_12302;
})();var statearr_12303_12348 = state_12292__$1;(statearr_12303_12348[(2)] = null);
(statearr_12303_12348[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (13)))
{var inst_12247 = (state_12292[(10)]);var inst_12249 = cljs.core.chunked_seq_QMARK_.call(null,inst_12247);var state_12292__$1 = state_12292;if(inst_12249)
{var statearr_12304_12349 = state_12292__$1;(statearr_12304_12349[(1)] = (16));
} else
{var statearr_12305_12350 = state_12292__$1;(statearr_12305_12350[(1)] = (17));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (22)))
{var inst_12277 = (state_12292[(2)]);var state_12292__$1 = state_12292;if(cljs.core.truth_(inst_12277))
{var statearr_12306_12351 = state_12292__$1;(statearr_12306_12351[(1)] = (23));
} else
{var statearr_12307_12352 = state_12292__$1;(statearr_12307_12352[(1)] = (24));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (6)))
{var inst_12223 = (state_12292[(8)]);var inst_12273 = (state_12292[(11)]);var inst_12271 = (state_12292[(7)]);var inst_12271__$1 = topic_fn.call(null,inst_12223);var inst_12272 = cljs.core.deref.call(null,mults);var inst_12273__$1 = cljs.core.get.call(null,inst_12272,inst_12271__$1);var state_12292__$1 = (function (){var statearr_12308 = state_12292;(statearr_12308[(11)] = inst_12273__$1);
(statearr_12308[(7)] = inst_12271__$1);
return statearr_12308;
})();if(cljs.core.truth_(inst_12273__$1))
{var statearr_12309_12353 = state_12292__$1;(statearr_12309_12353[(1)] = (19));
} else
{var statearr_12310_12354 = state_12292__$1;(statearr_12310_12354[(1)] = (20));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (25)))
{var inst_12282 = (state_12292[(2)]);var state_12292__$1 = state_12292;var statearr_12311_12355 = state_12292__$1;(statearr_12311_12355[(2)] = inst_12282);
(statearr_12311_12355[(1)] = (21));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (17)))
{var inst_12247 = (state_12292[(10)]);var inst_12256 = cljs.core.first.call(null,inst_12247);var inst_12257 = cljs.core.async.muxch_STAR_.call(null,inst_12256);var inst_12258 = cljs.core.async.close_BANG_.call(null,inst_12257);var inst_12259 = cljs.core.next.call(null,inst_12247);var inst_12233 = inst_12259;var inst_12234 = null;var inst_12235 = (0);var inst_12236 = (0);var state_12292__$1 = (function (){var statearr_12312 = state_12292;(statearr_12312[(12)] = inst_12236);
(statearr_12312[(13)] = inst_12233);
(statearr_12312[(14)] = inst_12258);
(statearr_12312[(15)] = inst_12235);
(statearr_12312[(16)] = inst_12234);
return statearr_12312;
})();var statearr_12313_12356 = state_12292__$1;(statearr_12313_12356[(2)] = null);
(statearr_12313_12356[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (3)))
{var inst_12290 = (state_12292[(2)]);var state_12292__$1 = state_12292;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12292__$1,inst_12290);
} else
{if((state_val_12293 === (12)))
{var inst_12267 = (state_12292[(2)]);var state_12292__$1 = state_12292;var statearr_12314_12357 = state_12292__$1;(statearr_12314_12357[(2)] = inst_12267);
(statearr_12314_12357[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (2)))
{var state_12292__$1 = state_12292;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12292__$1,(4),ch);
} else
{if((state_val_12293 === (23)))
{var state_12292__$1 = state_12292;var statearr_12315_12358 = state_12292__$1;(statearr_12315_12358[(2)] = null);
(statearr_12315_12358[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (19)))
{var inst_12223 = (state_12292[(8)]);var inst_12273 = (state_12292[(11)]);var inst_12275 = cljs.core.async.muxch_STAR_.call(null,inst_12273);var state_12292__$1 = state_12292;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12292__$1,(22),inst_12275,inst_12223);
} else
{if((state_val_12293 === (11)))
{var inst_12247 = (state_12292[(10)]);var inst_12233 = (state_12292[(13)]);var inst_12247__$1 = cljs.core.seq.call(null,inst_12233);var state_12292__$1 = (function (){var statearr_12316 = state_12292;(statearr_12316[(10)] = inst_12247__$1);
return statearr_12316;
})();if(inst_12247__$1)
{var statearr_12317_12359 = state_12292__$1;(statearr_12317_12359[(1)] = (13));
} else
{var statearr_12318_12360 = state_12292__$1;(statearr_12318_12360[(1)] = (14));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (9)))
{var inst_12269 = (state_12292[(2)]);var state_12292__$1 = state_12292;var statearr_12319_12361 = state_12292__$1;(statearr_12319_12361[(2)] = inst_12269);
(statearr_12319_12361[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (5)))
{var inst_12230 = cljs.core.deref.call(null,mults);var inst_12231 = cljs.core.vals.call(null,inst_12230);var inst_12232 = cljs.core.seq.call(null,inst_12231);var inst_12233 = inst_12232;var inst_12234 = null;var inst_12235 = (0);var inst_12236 = (0);var state_12292__$1 = (function (){var statearr_12320 = state_12292;(statearr_12320[(12)] = inst_12236);
(statearr_12320[(13)] = inst_12233);
(statearr_12320[(15)] = inst_12235);
(statearr_12320[(16)] = inst_12234);
return statearr_12320;
})();var statearr_12321_12362 = state_12292__$1;(statearr_12321_12362[(2)] = null);
(statearr_12321_12362[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (14)))
{var state_12292__$1 = state_12292;var statearr_12325_12363 = state_12292__$1;(statearr_12325_12363[(2)] = null);
(statearr_12325_12363[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (16)))
{var inst_12247 = (state_12292[(10)]);var inst_12251 = cljs.core.chunk_first.call(null,inst_12247);var inst_12252 = cljs.core.chunk_rest.call(null,inst_12247);var inst_12253 = cljs.core.count.call(null,inst_12251);var inst_12233 = inst_12252;var inst_12234 = inst_12251;var inst_12235 = inst_12253;var inst_12236 = (0);var state_12292__$1 = (function (){var statearr_12326 = state_12292;(statearr_12326[(12)] = inst_12236);
(statearr_12326[(13)] = inst_12233);
(statearr_12326[(15)] = inst_12235);
(statearr_12326[(16)] = inst_12234);
return statearr_12326;
})();var statearr_12327_12364 = state_12292__$1;(statearr_12327_12364[(2)] = null);
(statearr_12327_12364[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (10)))
{var inst_12236 = (state_12292[(12)]);var inst_12233 = (state_12292[(13)]);var inst_12235 = (state_12292[(15)]);var inst_12234 = (state_12292[(16)]);var inst_12241 = cljs.core._nth.call(null,inst_12234,inst_12236);var inst_12242 = cljs.core.async.muxch_STAR_.call(null,inst_12241);var inst_12243 = cljs.core.async.close_BANG_.call(null,inst_12242);var inst_12244 = (inst_12236 + (1));var tmp12322 = inst_12233;var tmp12323 = inst_12235;var tmp12324 = inst_12234;var inst_12233__$1 = tmp12322;var inst_12234__$1 = tmp12324;var inst_12235__$1 = tmp12323;var inst_12236__$1 = inst_12244;var state_12292__$1 = (function (){var statearr_12328 = state_12292;(statearr_12328[(12)] = inst_12236__$1);
(statearr_12328[(13)] = inst_12233__$1);
(statearr_12328[(17)] = inst_12243);
(statearr_12328[(15)] = inst_12235__$1);
(statearr_12328[(16)] = inst_12234__$1);
return statearr_12328;
})();var statearr_12329_12365 = state_12292__$1;(statearr_12329_12365[(2)] = null);
(statearr_12329_12365[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (18)))
{var inst_12262 = (state_12292[(2)]);var state_12292__$1 = state_12292;var statearr_12330_12366 = state_12292__$1;(statearr_12330_12366[(2)] = inst_12262);
(statearr_12330_12366[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12293 === (8)))
{var inst_12236 = (state_12292[(12)]);var inst_12235 = (state_12292[(15)]);var inst_12238 = (inst_12236 < inst_12235);var inst_12239 = inst_12238;var state_12292__$1 = state_12292;if(cljs.core.truth_(inst_12239))
{var statearr_12331_12367 = state_12292__$1;(statearr_12331_12367[(1)] = (10));
} else
{var statearr_12332_12368 = state_12292__$1;(statearr_12332_12368[(1)] = (11));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___12340,mults,ensure_mult,p))
;return ((function (switch__5702__auto__,c__5717__auto___12340,mults,ensure_mult,p){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_12336 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_12336[(0)] = state_machine__5703__auto__);
(statearr_12336[(1)] = (1));
return statearr_12336;
});
var state_machine__5703__auto____1 = (function (state_12292){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_12292);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e12337){if((e12337 instanceof Object))
{var ex__5706__auto__ = e12337;var statearr_12338_12369 = state_12292;(statearr_12338_12369[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12292);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12337;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12370 = state_12292;
state_12292 = G__12370;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_12292){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_12292);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___12340,mults,ensure_mult,p))
})();var state__5719__auto__ = (function (){var statearr_12339 = f__5718__auto__.call(null);(statearr_12339[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___12340);
return statearr_12339;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___12340,mults,ensure_mult,p))
);
return p;
});
pub = function(ch,topic_fn,buf_fn){
switch(arguments.length){
case 2:
return pub__2.call(this,ch,topic_fn);
case 3:
return pub__3.call(this,ch,topic_fn,buf_fn);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
pub.cljs$core$IFn$_invoke$arity$2 = pub__2;
pub.cljs$core$IFn$_invoke$arity$3 = pub__3;
return pub;
})()
;
/**
* Subscribes a channel to a topic of a pub.
* 
* By default the channel will be closed when the source closes,
* but can be determined by the close? parameter.
*/
cljs.core.async.sub = (function() {
var sub = null;
var sub__3 = (function (p,topic,ch){return sub.call(null,p,topic,ch,true);
});
var sub__4 = (function (p,topic,ch,close_QMARK_){return cljs.core.async.sub_STAR_.call(null,p,topic,ch,close_QMARK_);
});
sub = function(p,topic,ch,close_QMARK_){
switch(arguments.length){
case 3:
return sub__3.call(this,p,topic,ch);
case 4:
return sub__4.call(this,p,topic,ch,close_QMARK_);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
sub.cljs$core$IFn$_invoke$arity$3 = sub__3;
sub.cljs$core$IFn$_invoke$arity$4 = sub__4;
return sub;
})()
;
/**
* Unsubscribes a channel from a topic of a pub
*/
cljs.core.async.unsub = (function unsub(p,topic,ch){return cljs.core.async.unsub_STAR_.call(null,p,topic,ch);
});
/**
* Unsubscribes all channels from a pub, or a topic of a pub
*/
cljs.core.async.unsub_all = (function() {
var unsub_all = null;
var unsub_all__1 = (function (p){return cljs.core.async.unsub_all_STAR_.call(null,p);
});
var unsub_all__2 = (function (p,topic){return cljs.core.async.unsub_all_STAR_.call(null,p,topic);
});
unsub_all = function(p,topic){
switch(arguments.length){
case 1:
return unsub_all__1.call(this,p);
case 2:
return unsub_all__2.call(this,p,topic);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
unsub_all.cljs$core$IFn$_invoke$arity$1 = unsub_all__1;
unsub_all.cljs$core$IFn$_invoke$arity$2 = unsub_all__2;
return unsub_all;
})()
;
/**
* Takes a function and a collection of source channels, and returns a
* channel which contains the values produced by applying f to the set
* of first items taken from each source channel, followed by applying
* f to the set of second items from each channel, until any one of the
* channels is closed, at which point the output channel will be
* closed. The returned channel will be unbuffered by default, or a
* buf-or-n can be supplied
*/
cljs.core.async.map = (function() {
var map = null;
var map__2 = (function (f,chs){return map.call(null,f,chs,null);
});
var map__3 = (function (f,chs,buf_or_n){var chs__$1 = cljs.core.vec.call(null,chs);var out = cljs.core.async.chan.call(null,buf_or_n);var cnt = cljs.core.count.call(null,chs__$1);var rets = cljs.core.object_array.call(null,cnt);var dchan = cljs.core.async.chan.call(null,(1));var dctr = cljs.core.atom.call(null,null);var done = cljs.core.mapv.call(null,((function (chs__$1,out,cnt,rets,dchan,dctr){
return (function (i){return ((function (chs__$1,out,cnt,rets,dchan,dctr){
return (function (ret){(rets[i] = ret);
if((cljs.core.swap_BANG_.call(null,dctr,cljs.core.dec) === (0)))
{return cljs.core.async.put_BANG_.call(null,dchan,rets.slice((0)));
} else
{return null;
}
});
;})(chs__$1,out,cnt,rets,dchan,dctr))
});})(chs__$1,out,cnt,rets,dchan,dctr))
,cljs.core.range.call(null,cnt));var c__5717__auto___12507 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___12507,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___12507,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function (state_12477){var state_val_12478 = (state_12477[(1)]);if((state_val_12478 === (7)))
{var state_12477__$1 = state_12477;var statearr_12479_12508 = state_12477__$1;(statearr_12479_12508[(2)] = null);
(statearr_12479_12508[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (1)))
{var state_12477__$1 = state_12477;var statearr_12480_12509 = state_12477__$1;(statearr_12480_12509[(2)] = null);
(statearr_12480_12509[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (4)))
{var inst_12441 = (state_12477[(7)]);var inst_12443 = (inst_12441 < cnt);var state_12477__$1 = state_12477;if(cljs.core.truth_(inst_12443))
{var statearr_12481_12510 = state_12477__$1;(statearr_12481_12510[(1)] = (6));
} else
{var statearr_12482_12511 = state_12477__$1;(statearr_12482_12511[(1)] = (7));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (15)))
{var inst_12473 = (state_12477[(2)]);var state_12477__$1 = state_12477;var statearr_12483_12512 = state_12477__$1;(statearr_12483_12512[(2)] = inst_12473);
(statearr_12483_12512[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (13)))
{var inst_12466 = cljs.core.async.close_BANG_.call(null,out);var state_12477__$1 = state_12477;var statearr_12484_12513 = state_12477__$1;(statearr_12484_12513[(2)] = inst_12466);
(statearr_12484_12513[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (6)))
{var state_12477__$1 = state_12477;var statearr_12485_12514 = state_12477__$1;(statearr_12485_12514[(2)] = null);
(statearr_12485_12514[(1)] = (11));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (3)))
{var inst_12475 = (state_12477[(2)]);var state_12477__$1 = state_12477;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12477__$1,inst_12475);
} else
{if((state_val_12478 === (12)))
{var inst_12463 = (state_12477[(8)]);var inst_12463__$1 = (state_12477[(2)]);var inst_12464 = cljs.core.some.call(null,cljs.core.nil_QMARK_,inst_12463__$1);var state_12477__$1 = (function (){var statearr_12486 = state_12477;(statearr_12486[(8)] = inst_12463__$1);
return statearr_12486;
})();if(cljs.core.truth_(inst_12464))
{var statearr_12487_12515 = state_12477__$1;(statearr_12487_12515[(1)] = (13));
} else
{var statearr_12488_12516 = state_12477__$1;(statearr_12488_12516[(1)] = (14));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (2)))
{var inst_12440 = cljs.core.reset_BANG_.call(null,dctr,cnt);var inst_12441 = (0);var state_12477__$1 = (function (){var statearr_12489 = state_12477;(statearr_12489[(7)] = inst_12441);
(statearr_12489[(9)] = inst_12440);
return statearr_12489;
})();var statearr_12490_12517 = state_12477__$1;(statearr_12490_12517[(2)] = null);
(statearr_12490_12517[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (11)))
{var inst_12441 = (state_12477[(7)]);var _ = cljs.core.async.impl.ioc_helpers.add_exception_frame.call(null,state_12477,(10),Object,null,(9));var inst_12450 = chs__$1.call(null,inst_12441);var inst_12451 = done.call(null,inst_12441);var inst_12452 = cljs.core.async.take_BANG_.call(null,inst_12450,inst_12451);var state_12477__$1 = state_12477;var statearr_12491_12518 = state_12477__$1;(statearr_12491_12518[(2)] = inst_12452);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12477__$1);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (9)))
{var inst_12441 = (state_12477[(7)]);var inst_12454 = (state_12477[(2)]);var inst_12455 = (inst_12441 + (1));var inst_12441__$1 = inst_12455;var state_12477__$1 = (function (){var statearr_12492 = state_12477;(statearr_12492[(10)] = inst_12454);
(statearr_12492[(7)] = inst_12441__$1);
return statearr_12492;
})();var statearr_12493_12519 = state_12477__$1;(statearr_12493_12519[(2)] = null);
(statearr_12493_12519[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (5)))
{var inst_12461 = (state_12477[(2)]);var state_12477__$1 = (function (){var statearr_12494 = state_12477;(statearr_12494[(11)] = inst_12461);
return statearr_12494;
})();return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12477__$1,(12),dchan);
} else
{if((state_val_12478 === (14)))
{var inst_12463 = (state_12477[(8)]);var inst_12468 = cljs.core.apply.call(null,f,inst_12463);var state_12477__$1 = state_12477;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12477__$1,(16),out,inst_12468);
} else
{if((state_val_12478 === (16)))
{var inst_12470 = (state_12477[(2)]);var state_12477__$1 = (function (){var statearr_12495 = state_12477;(statearr_12495[(12)] = inst_12470);
return statearr_12495;
})();var statearr_12496_12520 = state_12477__$1;(statearr_12496_12520[(2)] = null);
(statearr_12496_12520[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (10)))
{var inst_12445 = (state_12477[(2)]);var inst_12446 = cljs.core.swap_BANG_.call(null,dctr,cljs.core.dec);var state_12477__$1 = (function (){var statearr_12497 = state_12477;(statearr_12497[(13)] = inst_12445);
return statearr_12497;
})();var statearr_12498_12521 = state_12477__$1;(statearr_12498_12521[(2)] = inst_12446);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12477__$1);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12478 === (8)))
{var inst_12459 = (state_12477[(2)]);var state_12477__$1 = state_12477;var statearr_12499_12522 = state_12477__$1;(statearr_12499_12522[(2)] = inst_12459);
(statearr_12499_12522[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___12507,chs__$1,out,cnt,rets,dchan,dctr,done))
;return ((function (switch__5702__auto__,c__5717__auto___12507,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_12503 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_12503[(0)] = state_machine__5703__auto__);
(statearr_12503[(1)] = (1));
return statearr_12503;
});
var state_machine__5703__auto____1 = (function (state_12477){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_12477);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e12504){if((e12504 instanceof Object))
{var ex__5706__auto__ = e12504;var statearr_12505_12523 = state_12477;(statearr_12505_12523[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12477);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12504;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12524 = state_12477;
state_12477 = G__12524;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_12477){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_12477);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___12507,chs__$1,out,cnt,rets,dchan,dctr,done))
})();var state__5719__auto__ = (function (){var statearr_12506 = f__5718__auto__.call(null);(statearr_12506[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___12507);
return statearr_12506;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___12507,chs__$1,out,cnt,rets,dchan,dctr,done))
);
return out;
});
map = function(f,chs,buf_or_n){
switch(arguments.length){
case 2:
return map__2.call(this,f,chs);
case 3:
return map__3.call(this,f,chs,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
map.cljs$core$IFn$_invoke$arity$2 = map__2;
map.cljs$core$IFn$_invoke$arity$3 = map__3;
return map;
})()
;
/**
* Takes a collection of source channels and returns a channel which
* contains all values taken from them. The returned channel will be
* unbuffered by default, or a buf-or-n can be supplied. The channel
* will close after all the source channels have closed.
*/
cljs.core.async.merge = (function() {
var merge = null;
var merge__1 = (function (chs){return merge.call(null,chs,null);
});
var merge__2 = (function (chs,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__5717__auto___12632 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___12632,out){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___12632,out){
return (function (state_12608){var state_val_12609 = (state_12608[(1)]);if((state_val_12609 === (7)))
{var inst_12587 = (state_12608[(7)]);var inst_12588 = (state_12608[(8)]);var inst_12587__$1 = (state_12608[(2)]);var inst_12588__$1 = cljs.core.nth.call(null,inst_12587__$1,(0),null);var inst_12589 = cljs.core.nth.call(null,inst_12587__$1,(1),null);var inst_12590 = (inst_12588__$1 == null);var state_12608__$1 = (function (){var statearr_12610 = state_12608;(statearr_12610[(7)] = inst_12587__$1);
(statearr_12610[(8)] = inst_12588__$1);
(statearr_12610[(9)] = inst_12589);
return statearr_12610;
})();if(cljs.core.truth_(inst_12590))
{var statearr_12611_12633 = state_12608__$1;(statearr_12611_12633[(1)] = (8));
} else
{var statearr_12612_12634 = state_12608__$1;(statearr_12612_12634[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12609 === (1)))
{var inst_12579 = cljs.core.vec.call(null,chs);var inst_12580 = inst_12579;var state_12608__$1 = (function (){var statearr_12613 = state_12608;(statearr_12613[(10)] = inst_12580);
return statearr_12613;
})();var statearr_12614_12635 = state_12608__$1;(statearr_12614_12635[(2)] = null);
(statearr_12614_12635[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12609 === (4)))
{var inst_12580 = (state_12608[(10)]);var state_12608__$1 = state_12608;return cljs.core.async.impl.ioc_helpers.ioc_alts_BANG_.call(null,state_12608__$1,(7),inst_12580);
} else
{if((state_val_12609 === (6)))
{var inst_12604 = (state_12608[(2)]);var state_12608__$1 = state_12608;var statearr_12615_12636 = state_12608__$1;(statearr_12615_12636[(2)] = inst_12604);
(statearr_12615_12636[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12609 === (3)))
{var inst_12606 = (state_12608[(2)]);var state_12608__$1 = state_12608;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12608__$1,inst_12606);
} else
{if((state_val_12609 === (2)))
{var inst_12580 = (state_12608[(10)]);var inst_12582 = cljs.core.count.call(null,inst_12580);var inst_12583 = (inst_12582 > (0));var state_12608__$1 = state_12608;if(cljs.core.truth_(inst_12583))
{var statearr_12617_12637 = state_12608__$1;(statearr_12617_12637[(1)] = (4));
} else
{var statearr_12618_12638 = state_12608__$1;(statearr_12618_12638[(1)] = (5));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12609 === (11)))
{var inst_12580 = (state_12608[(10)]);var inst_12597 = (state_12608[(2)]);var tmp12616 = inst_12580;var inst_12580__$1 = tmp12616;var state_12608__$1 = (function (){var statearr_12619 = state_12608;(statearr_12619[(11)] = inst_12597);
(statearr_12619[(10)] = inst_12580__$1);
return statearr_12619;
})();var statearr_12620_12639 = state_12608__$1;(statearr_12620_12639[(2)] = null);
(statearr_12620_12639[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12609 === (9)))
{var inst_12588 = (state_12608[(8)]);var state_12608__$1 = state_12608;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12608__$1,(11),out,inst_12588);
} else
{if((state_val_12609 === (5)))
{var inst_12602 = cljs.core.async.close_BANG_.call(null,out);var state_12608__$1 = state_12608;var statearr_12621_12640 = state_12608__$1;(statearr_12621_12640[(2)] = inst_12602);
(statearr_12621_12640[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12609 === (10)))
{var inst_12600 = (state_12608[(2)]);var state_12608__$1 = state_12608;var statearr_12622_12641 = state_12608__$1;(statearr_12622_12641[(2)] = inst_12600);
(statearr_12622_12641[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12609 === (8)))
{var inst_12587 = (state_12608[(7)]);var inst_12588 = (state_12608[(8)]);var inst_12589 = (state_12608[(9)]);var inst_12580 = (state_12608[(10)]);var inst_12592 = (function (){var c = inst_12589;var v = inst_12588;var vec__12585 = inst_12587;var cs = inst_12580;return ((function (c,v,vec__12585,cs,inst_12587,inst_12588,inst_12589,inst_12580,state_val_12609,c__5717__auto___12632,out){
return (function (p1__12525_SHARP_){return cljs.core.not_EQ_.call(null,c,p1__12525_SHARP_);
});
;})(c,v,vec__12585,cs,inst_12587,inst_12588,inst_12589,inst_12580,state_val_12609,c__5717__auto___12632,out))
})();var inst_12593 = cljs.core.filterv.call(null,inst_12592,inst_12580);var inst_12580__$1 = inst_12593;var state_12608__$1 = (function (){var statearr_12623 = state_12608;(statearr_12623[(10)] = inst_12580__$1);
return statearr_12623;
})();var statearr_12624_12642 = state_12608__$1;(statearr_12624_12642[(2)] = null);
(statearr_12624_12642[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___12632,out))
;return ((function (switch__5702__auto__,c__5717__auto___12632,out){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_12628 = [null,null,null,null,null,null,null,null,null,null,null,null];(statearr_12628[(0)] = state_machine__5703__auto__);
(statearr_12628[(1)] = (1));
return statearr_12628;
});
var state_machine__5703__auto____1 = (function (state_12608){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_12608);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e12629){if((e12629 instanceof Object))
{var ex__5706__auto__ = e12629;var statearr_12630_12643 = state_12608;(statearr_12630_12643[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12608);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12629;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12644 = state_12608;
state_12608 = G__12644;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_12608){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_12608);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___12632,out))
})();var state__5719__auto__ = (function (){var statearr_12631 = f__5718__auto__.call(null);(statearr_12631[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___12632);
return statearr_12631;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___12632,out))
);
return out;
});
merge = function(chs,buf_or_n){
switch(arguments.length){
case 1:
return merge__1.call(this,chs);
case 2:
return merge__2.call(this,chs,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
merge.cljs$core$IFn$_invoke$arity$1 = merge__1;
merge.cljs$core$IFn$_invoke$arity$2 = merge__2;
return merge;
})()
;
/**
* Returns a channel containing the single (collection) result of the
* items taken from the channel conjoined to the supplied
* collection. ch must close before into produces a result.
*/
cljs.core.async.into = (function into(coll,ch){return cljs.core.async.reduce.call(null,cljs.core.conj,coll,ch);
});
/**
* Returns a channel that will return, at most, n items from ch. After n items
* have been returned, or ch has been closed, the return chanel will close.
* 
* The output channel is unbuffered by default, unless buf-or-n is given.
*/
cljs.core.async.take = (function() {
var take = null;
var take__2 = (function (n,ch){return take.call(null,n,ch,null);
});
var take__3 = (function (n,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__5717__auto___12737 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___12737,out){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___12737,out){
return (function (state_12714){var state_val_12715 = (state_12714[(1)]);if((state_val_12715 === (7)))
{var inst_12696 = (state_12714[(7)]);var inst_12696__$1 = (state_12714[(2)]);var inst_12697 = (inst_12696__$1 == null);var inst_12698 = cljs.core.not.call(null,inst_12697);var state_12714__$1 = (function (){var statearr_12716 = state_12714;(statearr_12716[(7)] = inst_12696__$1);
return statearr_12716;
})();if(inst_12698)
{var statearr_12717_12738 = state_12714__$1;(statearr_12717_12738[(1)] = (8));
} else
{var statearr_12718_12739 = state_12714__$1;(statearr_12718_12739[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12715 === (1)))
{var inst_12691 = (0);var state_12714__$1 = (function (){var statearr_12719 = state_12714;(statearr_12719[(8)] = inst_12691);
return statearr_12719;
})();var statearr_12720_12740 = state_12714__$1;(statearr_12720_12740[(2)] = null);
(statearr_12720_12740[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12715 === (4)))
{var state_12714__$1 = state_12714;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12714__$1,(7),ch);
} else
{if((state_val_12715 === (6)))
{var inst_12709 = (state_12714[(2)]);var state_12714__$1 = state_12714;var statearr_12721_12741 = state_12714__$1;(statearr_12721_12741[(2)] = inst_12709);
(statearr_12721_12741[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12715 === (3)))
{var inst_12711 = (state_12714[(2)]);var inst_12712 = cljs.core.async.close_BANG_.call(null,out);var state_12714__$1 = (function (){var statearr_12722 = state_12714;(statearr_12722[(9)] = inst_12711);
return statearr_12722;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12714__$1,inst_12712);
} else
{if((state_val_12715 === (2)))
{var inst_12691 = (state_12714[(8)]);var inst_12693 = (inst_12691 < n);var state_12714__$1 = state_12714;if(cljs.core.truth_(inst_12693))
{var statearr_12723_12742 = state_12714__$1;(statearr_12723_12742[(1)] = (4));
} else
{var statearr_12724_12743 = state_12714__$1;(statearr_12724_12743[(1)] = (5));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12715 === (11)))
{var inst_12691 = (state_12714[(8)]);var inst_12701 = (state_12714[(2)]);var inst_12702 = (inst_12691 + (1));var inst_12691__$1 = inst_12702;var state_12714__$1 = (function (){var statearr_12725 = state_12714;(statearr_12725[(8)] = inst_12691__$1);
(statearr_12725[(10)] = inst_12701);
return statearr_12725;
})();var statearr_12726_12744 = state_12714__$1;(statearr_12726_12744[(2)] = null);
(statearr_12726_12744[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12715 === (9)))
{var state_12714__$1 = state_12714;var statearr_12727_12745 = state_12714__$1;(statearr_12727_12745[(2)] = null);
(statearr_12727_12745[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12715 === (5)))
{var state_12714__$1 = state_12714;var statearr_12728_12746 = state_12714__$1;(statearr_12728_12746[(2)] = null);
(statearr_12728_12746[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12715 === (10)))
{var inst_12706 = (state_12714[(2)]);var state_12714__$1 = state_12714;var statearr_12729_12747 = state_12714__$1;(statearr_12729_12747[(2)] = inst_12706);
(statearr_12729_12747[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12715 === (8)))
{var inst_12696 = (state_12714[(7)]);var state_12714__$1 = state_12714;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12714__$1,(11),out,inst_12696);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___12737,out))
;return ((function (switch__5702__auto__,c__5717__auto___12737,out){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_12733 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_12733[(0)] = state_machine__5703__auto__);
(statearr_12733[(1)] = (1));
return statearr_12733;
});
var state_machine__5703__auto____1 = (function (state_12714){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_12714);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e12734){if((e12734 instanceof Object))
{var ex__5706__auto__ = e12734;var statearr_12735_12748 = state_12714;(statearr_12735_12748[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12714);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12734;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12749 = state_12714;
state_12714 = G__12749;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_12714){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_12714);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___12737,out))
})();var state__5719__auto__ = (function (){var statearr_12736 = f__5718__auto__.call(null);(statearr_12736[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___12737);
return statearr_12736;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___12737,out))
);
return out;
});
take = function(n,ch,buf_or_n){
switch(arguments.length){
case 2:
return take__2.call(this,n,ch);
case 3:
return take__3.call(this,n,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
take.cljs$core$IFn$_invoke$arity$2 = take__2;
take.cljs$core$IFn$_invoke$arity$3 = take__3;
return take;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.map_LT_ = (function map_LT_(f,ch){if(typeof cljs.core.async.t12757 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12757 = (function (ch,f,map_LT_,meta12758){
this.ch = ch;
this.f = f;
this.map_LT_ = map_LT_;
this.meta12758 = meta12758;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12757.cljs$lang$type = true;
cljs.core.async.t12757.cljs$lang$ctorStr = "cljs.core.async/t12757";
cljs.core.async.t12757.cljs$lang$ctorPrWriter = (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t12757");
});
cljs.core.async.t12757.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t12757.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.put_BANG_.call(null,self__.ch,val,fn1);
});
cljs.core.async.t12757.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t12757.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;var ret = cljs.core.async.impl.protocols.take_BANG_.call(null,self__.ch,(function (){if(typeof cljs.core.async.t12760 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12760 = (function (fn1,_,meta12758,ch,f,map_LT_,meta12761){
this.fn1 = fn1;
this._ = _;
this.meta12758 = meta12758;
this.ch = ch;
this.f = f;
this.map_LT_ = map_LT_;
this.meta12761 = meta12761;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12760.cljs$lang$type = true;
cljs.core.async.t12760.cljs$lang$ctorStr = "cljs.core.async/t12760";
cljs.core.async.t12760.cljs$lang$ctorPrWriter = ((function (___$1){
return (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t12760");
});})(___$1))
;
cljs.core.async.t12760.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t12760.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;return cljs.core.async.impl.protocols.active_QMARK_.call(null,self__.fn1);
});})(___$1))
;
cljs.core.async.t12760.prototype.cljs$core$async$impl$protocols$Handler$lock_id$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;return cljs.core.async.impl.protocols.lock_id.call(null,self__.fn1);
});})(___$1))
;
cljs.core.async.t12760.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;var f1 = cljs.core.async.impl.protocols.commit.call(null,self__.fn1);return ((function (f1,___$4,___$1){
return (function (p1__12750_SHARP_){return f1.call(null,(((p1__12750_SHARP_ == null))?null:self__.f.call(null,p1__12750_SHARP_)));
});
;})(f1,___$4,___$1))
});})(___$1))
;
cljs.core.async.t12760.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (___$1){
return (function (_12762){var self__ = this;
var _12762__$1 = this;return self__.meta12761;
});})(___$1))
;
cljs.core.async.t12760.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (___$1){
return (function (_12762,meta12761__$1){var self__ = this;
var _12762__$1 = this;return (new cljs.core.async.t12760(self__.fn1,self__._,self__.meta12758,self__.ch,self__.f,self__.map_LT_,meta12761__$1));
});})(___$1))
;
cljs.core.async.__GT_t12760 = ((function (___$1){
return (function __GT_t12760(fn1__$1,___$2,meta12758__$1,ch__$2,f__$2,map_LT___$2,meta12761){return (new cljs.core.async.t12760(fn1__$1,___$2,meta12758__$1,ch__$2,f__$2,map_LT___$2,meta12761));
});})(___$1))
;
}
return (new cljs.core.async.t12760(fn1,___$1,self__.meta12758,self__.ch,self__.f,self__.map_LT_,null));
})());if(cljs.core.truth_((function (){var and__3541__auto__ = ret;if(cljs.core.truth_(and__3541__auto__))
{return !((cljs.core.deref.call(null,ret) == null));
} else
{return and__3541__auto__;
}
})()))
{return cljs.core.async.impl.channels.box.call(null,self__.f.call(null,cljs.core.deref.call(null,ret)));
} else
{return ret;
}
});
cljs.core.async.t12757.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t12757.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_.call(null,self__.ch);
});
cljs.core.async.t12757.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.closed_QMARK_.call(null,self__.ch);
});
cljs.core.async.t12757.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_12759){var self__ = this;
var _12759__$1 = this;return self__.meta12758;
});
cljs.core.async.t12757.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_12759,meta12758__$1){var self__ = this;
var _12759__$1 = this;return (new cljs.core.async.t12757(self__.ch,self__.f,self__.map_LT_,meta12758__$1));
});
cljs.core.async.__GT_t12757 = (function __GT_t12757(ch__$1,f__$1,map_LT___$1,meta12758){return (new cljs.core.async.t12757(ch__$1,f__$1,map_LT___$1,meta12758));
});
}
return (new cljs.core.async.t12757(ch,f,map_LT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.map_GT_ = (function map_GT_(f,ch){if(typeof cljs.core.async.t12766 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12766 = (function (ch,f,map_GT_,meta12767){
this.ch = ch;
this.f = f;
this.map_GT_ = map_GT_;
this.meta12767 = meta12767;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12766.cljs$lang$type = true;
cljs.core.async.t12766.cljs$lang$ctorStr = "cljs.core.async/t12766";
cljs.core.async.t12766.cljs$lang$ctorPrWriter = (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t12766");
});
cljs.core.async.t12766.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t12766.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.put_BANG_.call(null,self__.ch,self__.f.call(null,val),fn1);
});
cljs.core.async.t12766.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t12766.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.take_BANG_.call(null,self__.ch,fn1);
});
cljs.core.async.t12766.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t12766.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_.call(null,self__.ch);
});
cljs.core.async.t12766.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_12768){var self__ = this;
var _12768__$1 = this;return self__.meta12767;
});
cljs.core.async.t12766.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_12768,meta12767__$1){var self__ = this;
var _12768__$1 = this;return (new cljs.core.async.t12766(self__.ch,self__.f,self__.map_GT_,meta12767__$1));
});
cljs.core.async.__GT_t12766 = (function __GT_t12766(ch__$1,f__$1,map_GT___$1,meta12767){return (new cljs.core.async.t12766(ch__$1,f__$1,map_GT___$1,meta12767));
});
}
return (new cljs.core.async.t12766(ch,f,map_GT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.filter_GT_ = (function filter_GT_(p,ch){if(typeof cljs.core.async.t12772 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12772 = (function (ch,p,filter_GT_,meta12773){
this.ch = ch;
this.p = p;
this.filter_GT_ = filter_GT_;
this.meta12773 = meta12773;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12772.cljs$lang$type = true;
cljs.core.async.t12772.cljs$lang$ctorStr = "cljs.core.async/t12772";
cljs.core.async.t12772.cljs$lang$ctorPrWriter = (function (this__4120__auto__,writer__4121__auto__,opt__4122__auto__){return cljs.core._write.call(null,writer__4121__auto__,"cljs.core.async/t12772");
});
cljs.core.async.t12772.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t12772.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;if(cljs.core.truth_(self__.p.call(null,val)))
{return cljs.core.async.impl.protocols.put_BANG_.call(null,self__.ch,val,fn1);
} else
{return cljs.core.async.impl.channels.box.call(null,cljs.core.not.call(null,cljs.core.async.impl.protocols.closed_QMARK_.call(null,self__.ch)));
}
});
cljs.core.async.t12772.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t12772.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.take_BANG_.call(null,self__.ch,fn1);
});
cljs.core.async.t12772.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t12772.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_.call(null,self__.ch);
});
cljs.core.async.t12772.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.closed_QMARK_.call(null,self__.ch);
});
cljs.core.async.t12772.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_12774){var self__ = this;
var _12774__$1 = this;return self__.meta12773;
});
cljs.core.async.t12772.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_12774,meta12773__$1){var self__ = this;
var _12774__$1 = this;return (new cljs.core.async.t12772(self__.ch,self__.p,self__.filter_GT_,meta12773__$1));
});
cljs.core.async.__GT_t12772 = (function __GT_t12772(ch__$1,p__$1,filter_GT___$1,meta12773){return (new cljs.core.async.t12772(ch__$1,p__$1,filter_GT___$1,meta12773));
});
}
return (new cljs.core.async.t12772(ch,p,filter_GT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.remove_GT_ = (function remove_GT_(p,ch){return cljs.core.async.filter_GT_.call(null,cljs.core.complement.call(null,p),ch);
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.filter_LT_ = (function() {
var filter_LT_ = null;
var filter_LT___2 = (function (p,ch){return filter_LT_.call(null,p,ch,null);
});
var filter_LT___3 = (function (p,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__5717__auto___12857 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___12857,out){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___12857,out){
return (function (state_12836){var state_val_12837 = (state_12836[(1)]);if((state_val_12837 === (7)))
{var inst_12832 = (state_12836[(2)]);var state_12836__$1 = state_12836;var statearr_12838_12858 = state_12836__$1;(statearr_12838_12858[(2)] = inst_12832);
(statearr_12838_12858[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12837 === (1)))
{var state_12836__$1 = state_12836;var statearr_12839_12859 = state_12836__$1;(statearr_12839_12859[(2)] = null);
(statearr_12839_12859[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12837 === (4)))
{var inst_12818 = (state_12836[(7)]);var inst_12818__$1 = (state_12836[(2)]);var inst_12819 = (inst_12818__$1 == null);var state_12836__$1 = (function (){var statearr_12840 = state_12836;(statearr_12840[(7)] = inst_12818__$1);
return statearr_12840;
})();if(cljs.core.truth_(inst_12819))
{var statearr_12841_12860 = state_12836__$1;(statearr_12841_12860[(1)] = (5));
} else
{var statearr_12842_12861 = state_12836__$1;(statearr_12842_12861[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12837 === (6)))
{var inst_12818 = (state_12836[(7)]);var inst_12823 = p.call(null,inst_12818);var state_12836__$1 = state_12836;if(cljs.core.truth_(inst_12823))
{var statearr_12843_12862 = state_12836__$1;(statearr_12843_12862[(1)] = (8));
} else
{var statearr_12844_12863 = state_12836__$1;(statearr_12844_12863[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12837 === (3)))
{var inst_12834 = (state_12836[(2)]);var state_12836__$1 = state_12836;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_12836__$1,inst_12834);
} else
{if((state_val_12837 === (2)))
{var state_12836__$1 = state_12836;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_12836__$1,(4),ch);
} else
{if((state_val_12837 === (11)))
{var inst_12826 = (state_12836[(2)]);var state_12836__$1 = state_12836;var statearr_12845_12864 = state_12836__$1;(statearr_12845_12864[(2)] = inst_12826);
(statearr_12845_12864[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12837 === (9)))
{var state_12836__$1 = state_12836;var statearr_12846_12865 = state_12836__$1;(statearr_12846_12865[(2)] = null);
(statearr_12846_12865[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12837 === (5)))
{var inst_12821 = cljs.core.async.close_BANG_.call(null,out);var state_12836__$1 = state_12836;var statearr_12847_12866 = state_12836__$1;(statearr_12847_12866[(2)] = inst_12821);
(statearr_12847_12866[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12837 === (10)))
{var inst_12829 = (state_12836[(2)]);var state_12836__$1 = (function (){var statearr_12848 = state_12836;(statearr_12848[(8)] = inst_12829);
return statearr_12848;
})();var statearr_12849_12867 = state_12836__$1;(statearr_12849_12867[(2)] = null);
(statearr_12849_12867[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_12837 === (8)))
{var inst_12818 = (state_12836[(7)]);var state_12836__$1 = state_12836;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_12836__$1,(11),out,inst_12818);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___12857,out))
;return ((function (switch__5702__auto__,c__5717__auto___12857,out){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_12853 = [null,null,null,null,null,null,null,null,null];(statearr_12853[(0)] = state_machine__5703__auto__);
(statearr_12853[(1)] = (1));
return statearr_12853;
});
var state_machine__5703__auto____1 = (function (state_12836){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_12836);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e12854){if((e12854 instanceof Object))
{var ex__5706__auto__ = e12854;var statearr_12855_12868 = state_12836;(statearr_12855_12868[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_12836);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e12854;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__12869 = state_12836;
state_12836 = G__12869;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_12836){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_12836);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___12857,out))
})();var state__5719__auto__ = (function (){var statearr_12856 = f__5718__auto__.call(null);(statearr_12856[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___12857);
return statearr_12856;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___12857,out))
);
return out;
});
filter_LT_ = function(p,ch,buf_or_n){
switch(arguments.length){
case 2:
return filter_LT___2.call(this,p,ch);
case 3:
return filter_LT___3.call(this,p,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
filter_LT_.cljs$core$IFn$_invoke$arity$2 = filter_LT___2;
filter_LT_.cljs$core$IFn$_invoke$arity$3 = filter_LT___3;
return filter_LT_;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.remove_LT_ = (function() {
var remove_LT_ = null;
var remove_LT___2 = (function (p,ch){return remove_LT_.call(null,p,ch,null);
});
var remove_LT___3 = (function (p,ch,buf_or_n){return cljs.core.async.filter_LT_.call(null,cljs.core.complement.call(null,p),ch,buf_or_n);
});
remove_LT_ = function(p,ch,buf_or_n){
switch(arguments.length){
case 2:
return remove_LT___2.call(this,p,ch);
case 3:
return remove_LT___3.call(this,p,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
remove_LT_.cljs$core$IFn$_invoke$arity$2 = remove_LT___2;
remove_LT_.cljs$core$IFn$_invoke$arity$3 = remove_LT___3;
return remove_LT_;
})()
;
cljs.core.async.mapcat_STAR_ = (function mapcat_STAR_(f,in$,out){var c__5717__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto__){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto__){
return (function (state_13035){var state_val_13036 = (state_13035[(1)]);if((state_val_13036 === (7)))
{var inst_13031 = (state_13035[(2)]);var state_13035__$1 = state_13035;var statearr_13037_13078 = state_13035__$1;(statearr_13037_13078[(2)] = inst_13031);
(statearr_13037_13078[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (20)))
{var inst_13001 = (state_13035[(7)]);var inst_13012 = (state_13035[(2)]);var inst_13013 = cljs.core.next.call(null,inst_13001);var inst_12987 = inst_13013;var inst_12988 = null;var inst_12989 = (0);var inst_12990 = (0);var state_13035__$1 = (function (){var statearr_13038 = state_13035;(statearr_13038[(8)] = inst_12989);
(statearr_13038[(9)] = inst_12988);
(statearr_13038[(10)] = inst_13012);
(statearr_13038[(11)] = inst_12987);
(statearr_13038[(12)] = inst_12990);
return statearr_13038;
})();var statearr_13039_13079 = state_13035__$1;(statearr_13039_13079[(2)] = null);
(statearr_13039_13079[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (1)))
{var state_13035__$1 = state_13035;var statearr_13040_13080 = state_13035__$1;(statearr_13040_13080[(2)] = null);
(statearr_13040_13080[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (4)))
{var inst_12976 = (state_13035[(13)]);var inst_12976__$1 = (state_13035[(2)]);var inst_12977 = (inst_12976__$1 == null);var state_13035__$1 = (function (){var statearr_13041 = state_13035;(statearr_13041[(13)] = inst_12976__$1);
return statearr_13041;
})();if(cljs.core.truth_(inst_12977))
{var statearr_13042_13081 = state_13035__$1;(statearr_13042_13081[(1)] = (5));
} else
{var statearr_13043_13082 = state_13035__$1;(statearr_13043_13082[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (15)))
{var state_13035__$1 = state_13035;var statearr_13047_13083 = state_13035__$1;(statearr_13047_13083[(2)] = null);
(statearr_13047_13083[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (21)))
{var state_13035__$1 = state_13035;var statearr_13048_13084 = state_13035__$1;(statearr_13048_13084[(2)] = null);
(statearr_13048_13084[(1)] = (23));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (13)))
{var inst_12989 = (state_13035[(8)]);var inst_12988 = (state_13035[(9)]);var inst_12987 = (state_13035[(11)]);var inst_12990 = (state_13035[(12)]);var inst_12997 = (state_13035[(2)]);var inst_12998 = (inst_12990 + (1));var tmp13044 = inst_12989;var tmp13045 = inst_12988;var tmp13046 = inst_12987;var inst_12987__$1 = tmp13046;var inst_12988__$1 = tmp13045;var inst_12989__$1 = tmp13044;var inst_12990__$1 = inst_12998;var state_13035__$1 = (function (){var statearr_13049 = state_13035;(statearr_13049[(8)] = inst_12989__$1);
(statearr_13049[(9)] = inst_12988__$1);
(statearr_13049[(11)] = inst_12987__$1);
(statearr_13049[(12)] = inst_12990__$1);
(statearr_13049[(14)] = inst_12997);
return statearr_13049;
})();var statearr_13050_13085 = state_13035__$1;(statearr_13050_13085[(2)] = null);
(statearr_13050_13085[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (22)))
{var state_13035__$1 = state_13035;var statearr_13051_13086 = state_13035__$1;(statearr_13051_13086[(2)] = null);
(statearr_13051_13086[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (6)))
{var inst_12976 = (state_13035[(13)]);var inst_12985 = f.call(null,inst_12976);var inst_12986 = cljs.core.seq.call(null,inst_12985);var inst_12987 = inst_12986;var inst_12988 = null;var inst_12989 = (0);var inst_12990 = (0);var state_13035__$1 = (function (){var statearr_13052 = state_13035;(statearr_13052[(8)] = inst_12989);
(statearr_13052[(9)] = inst_12988);
(statearr_13052[(11)] = inst_12987);
(statearr_13052[(12)] = inst_12990);
return statearr_13052;
})();var statearr_13053_13087 = state_13035__$1;(statearr_13053_13087[(2)] = null);
(statearr_13053_13087[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (17)))
{var inst_13001 = (state_13035[(7)]);var inst_13005 = cljs.core.chunk_first.call(null,inst_13001);var inst_13006 = cljs.core.chunk_rest.call(null,inst_13001);var inst_13007 = cljs.core.count.call(null,inst_13005);var inst_12987 = inst_13006;var inst_12988 = inst_13005;var inst_12989 = inst_13007;var inst_12990 = (0);var state_13035__$1 = (function (){var statearr_13054 = state_13035;(statearr_13054[(8)] = inst_12989);
(statearr_13054[(9)] = inst_12988);
(statearr_13054[(11)] = inst_12987);
(statearr_13054[(12)] = inst_12990);
return statearr_13054;
})();var statearr_13055_13088 = state_13035__$1;(statearr_13055_13088[(2)] = null);
(statearr_13055_13088[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (3)))
{var inst_13033 = (state_13035[(2)]);var state_13035__$1 = state_13035;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13035__$1,inst_13033);
} else
{if((state_val_13036 === (12)))
{var inst_13021 = (state_13035[(2)]);var state_13035__$1 = state_13035;var statearr_13056_13089 = state_13035__$1;(statearr_13056_13089[(2)] = inst_13021);
(statearr_13056_13089[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (2)))
{var state_13035__$1 = state_13035;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13035__$1,(4),in$);
} else
{if((state_val_13036 === (23)))
{var inst_13029 = (state_13035[(2)]);var state_13035__$1 = state_13035;var statearr_13057_13090 = state_13035__$1;(statearr_13057_13090[(2)] = inst_13029);
(statearr_13057_13090[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (19)))
{var inst_13016 = (state_13035[(2)]);var state_13035__$1 = state_13035;var statearr_13058_13091 = state_13035__$1;(statearr_13058_13091[(2)] = inst_13016);
(statearr_13058_13091[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (11)))
{var inst_13001 = (state_13035[(7)]);var inst_12987 = (state_13035[(11)]);var inst_13001__$1 = cljs.core.seq.call(null,inst_12987);var state_13035__$1 = (function (){var statearr_13059 = state_13035;(statearr_13059[(7)] = inst_13001__$1);
return statearr_13059;
})();if(inst_13001__$1)
{var statearr_13060_13092 = state_13035__$1;(statearr_13060_13092[(1)] = (14));
} else
{var statearr_13061_13093 = state_13035__$1;(statearr_13061_13093[(1)] = (15));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (9)))
{var inst_13023 = (state_13035[(2)]);var inst_13024 = cljs.core.async.impl.protocols.closed_QMARK_.call(null,out);var state_13035__$1 = (function (){var statearr_13062 = state_13035;(statearr_13062[(15)] = inst_13023);
return statearr_13062;
})();if(cljs.core.truth_(inst_13024))
{var statearr_13063_13094 = state_13035__$1;(statearr_13063_13094[(1)] = (21));
} else
{var statearr_13064_13095 = state_13035__$1;(statearr_13064_13095[(1)] = (22));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (5)))
{var inst_12979 = cljs.core.async.close_BANG_.call(null,out);var state_13035__$1 = state_13035;var statearr_13065_13096 = state_13035__$1;(statearr_13065_13096[(2)] = inst_12979);
(statearr_13065_13096[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (14)))
{var inst_13001 = (state_13035[(7)]);var inst_13003 = cljs.core.chunked_seq_QMARK_.call(null,inst_13001);var state_13035__$1 = state_13035;if(inst_13003)
{var statearr_13066_13097 = state_13035__$1;(statearr_13066_13097[(1)] = (17));
} else
{var statearr_13067_13098 = state_13035__$1;(statearr_13067_13098[(1)] = (18));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (16)))
{var inst_13019 = (state_13035[(2)]);var state_13035__$1 = state_13035;var statearr_13068_13099 = state_13035__$1;(statearr_13068_13099[(2)] = inst_13019);
(statearr_13068_13099[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13036 === (10)))
{var inst_12988 = (state_13035[(9)]);var inst_12990 = (state_13035[(12)]);var inst_12995 = cljs.core._nth.call(null,inst_12988,inst_12990);var state_13035__$1 = state_13035;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13035__$1,(13),out,inst_12995);
} else
{if((state_val_13036 === (18)))
{var inst_13001 = (state_13035[(7)]);var inst_13010 = cljs.core.first.call(null,inst_13001);var state_13035__$1 = state_13035;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13035__$1,(20),out,inst_13010);
} else
{if((state_val_13036 === (8)))
{var inst_12989 = (state_13035[(8)]);var inst_12990 = (state_13035[(12)]);var inst_12992 = (inst_12990 < inst_12989);var inst_12993 = inst_12992;var state_13035__$1 = state_13035;if(cljs.core.truth_(inst_12993))
{var statearr_13069_13100 = state_13035__$1;(statearr_13069_13100[(1)] = (10));
} else
{var statearr_13070_13101 = state_13035__$1;(statearr_13070_13101[(1)] = (11));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto__))
;return ((function (switch__5702__auto__,c__5717__auto__){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_13074 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13074[(0)] = state_machine__5703__auto__);
(statearr_13074[(1)] = (1));
return statearr_13074;
});
var state_machine__5703__auto____1 = (function (state_13035){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_13035);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e13075){if((e13075 instanceof Object))
{var ex__5706__auto__ = e13075;var statearr_13076_13102 = state_13035;(statearr_13076_13102[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13035);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13075;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13103 = state_13035;
state_13035 = G__13103;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_13035){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_13035);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto__))
})();var state__5719__auto__ = (function (){var statearr_13077 = f__5718__auto__.call(null);(statearr_13077[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto__);
return statearr_13077;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto__))
);
return c__5717__auto__;
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.mapcat_LT_ = (function() {
var mapcat_LT_ = null;
var mapcat_LT___2 = (function (f,in$){return mapcat_LT_.call(null,f,in$,null);
});
var mapcat_LT___3 = (function (f,in$,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);cljs.core.async.mapcat_STAR_.call(null,f,in$,out);
return out;
});
mapcat_LT_ = function(f,in$,buf_or_n){
switch(arguments.length){
case 2:
return mapcat_LT___2.call(this,f,in$);
case 3:
return mapcat_LT___3.call(this,f,in$,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
mapcat_LT_.cljs$core$IFn$_invoke$arity$2 = mapcat_LT___2;
mapcat_LT_.cljs$core$IFn$_invoke$arity$3 = mapcat_LT___3;
return mapcat_LT_;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.mapcat_GT_ = (function() {
var mapcat_GT_ = null;
var mapcat_GT___2 = (function (f,out){return mapcat_GT_.call(null,f,out,null);
});
var mapcat_GT___3 = (function (f,out,buf_or_n){var in$ = cljs.core.async.chan.call(null,buf_or_n);cljs.core.async.mapcat_STAR_.call(null,f,in$,out);
return in$;
});
mapcat_GT_ = function(f,out,buf_or_n){
switch(arguments.length){
case 2:
return mapcat_GT___2.call(this,f,out);
case 3:
return mapcat_GT___3.call(this,f,out,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
mapcat_GT_.cljs$core$IFn$_invoke$arity$2 = mapcat_GT___2;
mapcat_GT_.cljs$core$IFn$_invoke$arity$3 = mapcat_GT___3;
return mapcat_GT_;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.unique = (function() {
var unique = null;
var unique__1 = (function (ch){return unique.call(null,ch,null);
});
var unique__2 = (function (ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__5717__auto___13200 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___13200,out){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___13200,out){
return (function (state_13175){var state_val_13176 = (state_13175[(1)]);if((state_val_13176 === (7)))
{var inst_13170 = (state_13175[(2)]);var state_13175__$1 = state_13175;var statearr_13177_13201 = state_13175__$1;(statearr_13177_13201[(2)] = inst_13170);
(statearr_13177_13201[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13176 === (1)))
{var inst_13152 = null;var state_13175__$1 = (function (){var statearr_13178 = state_13175;(statearr_13178[(7)] = inst_13152);
return statearr_13178;
})();var statearr_13179_13202 = state_13175__$1;(statearr_13179_13202[(2)] = null);
(statearr_13179_13202[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13176 === (4)))
{var inst_13155 = (state_13175[(8)]);var inst_13155__$1 = (state_13175[(2)]);var inst_13156 = (inst_13155__$1 == null);var inst_13157 = cljs.core.not.call(null,inst_13156);var state_13175__$1 = (function (){var statearr_13180 = state_13175;(statearr_13180[(8)] = inst_13155__$1);
return statearr_13180;
})();if(inst_13157)
{var statearr_13181_13203 = state_13175__$1;(statearr_13181_13203[(1)] = (5));
} else
{var statearr_13182_13204 = state_13175__$1;(statearr_13182_13204[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13176 === (6)))
{var state_13175__$1 = state_13175;var statearr_13183_13205 = state_13175__$1;(statearr_13183_13205[(2)] = null);
(statearr_13183_13205[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13176 === (3)))
{var inst_13172 = (state_13175[(2)]);var inst_13173 = cljs.core.async.close_BANG_.call(null,out);var state_13175__$1 = (function (){var statearr_13184 = state_13175;(statearr_13184[(9)] = inst_13172);
return statearr_13184;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13175__$1,inst_13173);
} else
{if((state_val_13176 === (2)))
{var state_13175__$1 = state_13175;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13175__$1,(4),ch);
} else
{if((state_val_13176 === (11)))
{var inst_13155 = (state_13175[(8)]);var inst_13164 = (state_13175[(2)]);var inst_13152 = inst_13155;var state_13175__$1 = (function (){var statearr_13185 = state_13175;(statearr_13185[(7)] = inst_13152);
(statearr_13185[(10)] = inst_13164);
return statearr_13185;
})();var statearr_13186_13206 = state_13175__$1;(statearr_13186_13206[(2)] = null);
(statearr_13186_13206[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13176 === (9)))
{var inst_13155 = (state_13175[(8)]);var state_13175__$1 = state_13175;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13175__$1,(11),out,inst_13155);
} else
{if((state_val_13176 === (5)))
{var inst_13152 = (state_13175[(7)]);var inst_13155 = (state_13175[(8)]);var inst_13159 = cljs.core._EQ_.call(null,inst_13155,inst_13152);var state_13175__$1 = state_13175;if(inst_13159)
{var statearr_13188_13207 = state_13175__$1;(statearr_13188_13207[(1)] = (8));
} else
{var statearr_13189_13208 = state_13175__$1;(statearr_13189_13208[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13176 === (10)))
{var inst_13167 = (state_13175[(2)]);var state_13175__$1 = state_13175;var statearr_13190_13209 = state_13175__$1;(statearr_13190_13209[(2)] = inst_13167);
(statearr_13190_13209[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13176 === (8)))
{var inst_13152 = (state_13175[(7)]);var tmp13187 = inst_13152;var inst_13152__$1 = tmp13187;var state_13175__$1 = (function (){var statearr_13191 = state_13175;(statearr_13191[(7)] = inst_13152__$1);
return statearr_13191;
})();var statearr_13192_13210 = state_13175__$1;(statearr_13192_13210[(2)] = null);
(statearr_13192_13210[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___13200,out))
;return ((function (switch__5702__auto__,c__5717__auto___13200,out){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_13196 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_13196[(0)] = state_machine__5703__auto__);
(statearr_13196[(1)] = (1));
return statearr_13196;
});
var state_machine__5703__auto____1 = (function (state_13175){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_13175);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e13197){if((e13197 instanceof Object))
{var ex__5706__auto__ = e13197;var statearr_13198_13211 = state_13175;(statearr_13198_13211[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13175);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13197;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13212 = state_13175;
state_13175 = G__13212;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_13175){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_13175);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___13200,out))
})();var state__5719__auto__ = (function (){var statearr_13199 = f__5718__auto__.call(null);(statearr_13199[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___13200);
return statearr_13199;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___13200,out))
);
return out;
});
unique = function(ch,buf_or_n){
switch(arguments.length){
case 1:
return unique__1.call(this,ch);
case 2:
return unique__2.call(this,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
unique.cljs$core$IFn$_invoke$arity$1 = unique__1;
unique.cljs$core$IFn$_invoke$arity$2 = unique__2;
return unique;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.partition = (function() {
var partition = null;
var partition__2 = (function (n,ch){return partition.call(null,n,ch,null);
});
var partition__3 = (function (n,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__5717__auto___13347 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___13347,out){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___13347,out){
return (function (state_13317){var state_val_13318 = (state_13317[(1)]);if((state_val_13318 === (7)))
{var inst_13313 = (state_13317[(2)]);var state_13317__$1 = state_13317;var statearr_13319_13348 = state_13317__$1;(statearr_13319_13348[(2)] = inst_13313);
(statearr_13319_13348[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (1)))
{var inst_13280 = (new Array(n));var inst_13281 = inst_13280;var inst_13282 = (0);var state_13317__$1 = (function (){var statearr_13320 = state_13317;(statearr_13320[(7)] = inst_13282);
(statearr_13320[(8)] = inst_13281);
return statearr_13320;
})();var statearr_13321_13349 = state_13317__$1;(statearr_13321_13349[(2)] = null);
(statearr_13321_13349[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (4)))
{var inst_13285 = (state_13317[(9)]);var inst_13285__$1 = (state_13317[(2)]);var inst_13286 = (inst_13285__$1 == null);var inst_13287 = cljs.core.not.call(null,inst_13286);var state_13317__$1 = (function (){var statearr_13322 = state_13317;(statearr_13322[(9)] = inst_13285__$1);
return statearr_13322;
})();if(inst_13287)
{var statearr_13323_13350 = state_13317__$1;(statearr_13323_13350[(1)] = (5));
} else
{var statearr_13324_13351 = state_13317__$1;(statearr_13324_13351[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (15)))
{var inst_13307 = (state_13317[(2)]);var state_13317__$1 = state_13317;var statearr_13325_13352 = state_13317__$1;(statearr_13325_13352[(2)] = inst_13307);
(statearr_13325_13352[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (13)))
{var state_13317__$1 = state_13317;var statearr_13326_13353 = state_13317__$1;(statearr_13326_13353[(2)] = null);
(statearr_13326_13353[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (6)))
{var inst_13282 = (state_13317[(7)]);var inst_13303 = (inst_13282 > (0));var state_13317__$1 = state_13317;if(cljs.core.truth_(inst_13303))
{var statearr_13327_13354 = state_13317__$1;(statearr_13327_13354[(1)] = (12));
} else
{var statearr_13328_13355 = state_13317__$1;(statearr_13328_13355[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (3)))
{var inst_13315 = (state_13317[(2)]);var state_13317__$1 = state_13317;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13317__$1,inst_13315);
} else
{if((state_val_13318 === (12)))
{var inst_13281 = (state_13317[(8)]);var inst_13305 = cljs.core.vec.call(null,inst_13281);var state_13317__$1 = state_13317;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13317__$1,(15),out,inst_13305);
} else
{if((state_val_13318 === (2)))
{var state_13317__$1 = state_13317;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13317__$1,(4),ch);
} else
{if((state_val_13318 === (11)))
{var inst_13297 = (state_13317[(2)]);var inst_13298 = (new Array(n));var inst_13281 = inst_13298;var inst_13282 = (0);var state_13317__$1 = (function (){var statearr_13329 = state_13317;(statearr_13329[(7)] = inst_13282);
(statearr_13329[(8)] = inst_13281);
(statearr_13329[(10)] = inst_13297);
return statearr_13329;
})();var statearr_13330_13356 = state_13317__$1;(statearr_13330_13356[(2)] = null);
(statearr_13330_13356[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (9)))
{var inst_13281 = (state_13317[(8)]);var inst_13295 = cljs.core.vec.call(null,inst_13281);var state_13317__$1 = state_13317;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13317__$1,(11),out,inst_13295);
} else
{if((state_val_13318 === (5)))
{var inst_13282 = (state_13317[(7)]);var inst_13281 = (state_13317[(8)]);var inst_13285 = (state_13317[(9)]);var inst_13290 = (state_13317[(11)]);var inst_13289 = (inst_13281[inst_13282] = inst_13285);var inst_13290__$1 = (inst_13282 + (1));var inst_13291 = (inst_13290__$1 < n);var state_13317__$1 = (function (){var statearr_13331 = state_13317;(statearr_13331[(12)] = inst_13289);
(statearr_13331[(11)] = inst_13290__$1);
return statearr_13331;
})();if(cljs.core.truth_(inst_13291))
{var statearr_13332_13357 = state_13317__$1;(statearr_13332_13357[(1)] = (8));
} else
{var statearr_13333_13358 = state_13317__$1;(statearr_13333_13358[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (14)))
{var inst_13310 = (state_13317[(2)]);var inst_13311 = cljs.core.async.close_BANG_.call(null,out);var state_13317__$1 = (function (){var statearr_13335 = state_13317;(statearr_13335[(13)] = inst_13310);
return statearr_13335;
})();var statearr_13336_13359 = state_13317__$1;(statearr_13336_13359[(2)] = inst_13311);
(statearr_13336_13359[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (10)))
{var inst_13301 = (state_13317[(2)]);var state_13317__$1 = state_13317;var statearr_13337_13360 = state_13317__$1;(statearr_13337_13360[(2)] = inst_13301);
(statearr_13337_13360[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13318 === (8)))
{var inst_13281 = (state_13317[(8)]);var inst_13290 = (state_13317[(11)]);var tmp13334 = inst_13281;var inst_13281__$1 = tmp13334;var inst_13282 = inst_13290;var state_13317__$1 = (function (){var statearr_13338 = state_13317;(statearr_13338[(7)] = inst_13282);
(statearr_13338[(8)] = inst_13281__$1);
return statearr_13338;
})();var statearr_13339_13361 = state_13317__$1;(statearr_13339_13361[(2)] = null);
(statearr_13339_13361[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___13347,out))
;return ((function (switch__5702__auto__,c__5717__auto___13347,out){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_13343 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13343[(0)] = state_machine__5703__auto__);
(statearr_13343[(1)] = (1));
return statearr_13343;
});
var state_machine__5703__auto____1 = (function (state_13317){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_13317);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e13344){if((e13344 instanceof Object))
{var ex__5706__auto__ = e13344;var statearr_13345_13362 = state_13317;(statearr_13345_13362[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13317);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13344;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13363 = state_13317;
state_13317 = G__13363;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_13317){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_13317);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___13347,out))
})();var state__5719__auto__ = (function (){var statearr_13346 = f__5718__auto__.call(null);(statearr_13346[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___13347);
return statearr_13346;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___13347,out))
);
return out;
});
partition = function(n,ch,buf_or_n){
switch(arguments.length){
case 2:
return partition__2.call(this,n,ch);
case 3:
return partition__3.call(this,n,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
partition.cljs$core$IFn$_invoke$arity$2 = partition__2;
partition.cljs$core$IFn$_invoke$arity$3 = partition__3;
return partition;
})()
;
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.partition_by = (function() {
var partition_by = null;
var partition_by__2 = (function (f,ch){return partition_by.call(null,f,ch,null);
});
var partition_by__3 = (function (f,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__5717__auto___13506 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___13506,out){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___13506,out){
return (function (state_13476){var state_val_13477 = (state_13476[(1)]);if((state_val_13477 === (7)))
{var inst_13472 = (state_13476[(2)]);var state_13476__$1 = state_13476;var statearr_13478_13507 = state_13476__$1;(statearr_13478_13507[(2)] = inst_13472);
(statearr_13478_13507[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (1)))
{var inst_13435 = [];var inst_13436 = inst_13435;var inst_13437 = new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123);var state_13476__$1 = (function (){var statearr_13479 = state_13476;(statearr_13479[(7)] = inst_13436);
(statearr_13479[(8)] = inst_13437);
return statearr_13479;
})();var statearr_13480_13508 = state_13476__$1;(statearr_13480_13508[(2)] = null);
(statearr_13480_13508[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (4)))
{var inst_13440 = (state_13476[(9)]);var inst_13440__$1 = (state_13476[(2)]);var inst_13441 = (inst_13440__$1 == null);var inst_13442 = cljs.core.not.call(null,inst_13441);var state_13476__$1 = (function (){var statearr_13481 = state_13476;(statearr_13481[(9)] = inst_13440__$1);
return statearr_13481;
})();if(inst_13442)
{var statearr_13482_13509 = state_13476__$1;(statearr_13482_13509[(1)] = (5));
} else
{var statearr_13483_13510 = state_13476__$1;(statearr_13483_13510[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (15)))
{var inst_13466 = (state_13476[(2)]);var state_13476__$1 = state_13476;var statearr_13484_13511 = state_13476__$1;(statearr_13484_13511[(2)] = inst_13466);
(statearr_13484_13511[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (13)))
{var state_13476__$1 = state_13476;var statearr_13485_13512 = state_13476__$1;(statearr_13485_13512[(2)] = null);
(statearr_13485_13512[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (6)))
{var inst_13436 = (state_13476[(7)]);var inst_13461 = inst_13436.length;var inst_13462 = (inst_13461 > (0));var state_13476__$1 = state_13476;if(cljs.core.truth_(inst_13462))
{var statearr_13486_13513 = state_13476__$1;(statearr_13486_13513[(1)] = (12));
} else
{var statearr_13487_13514 = state_13476__$1;(statearr_13487_13514[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (3)))
{var inst_13474 = (state_13476[(2)]);var state_13476__$1 = state_13476;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13476__$1,inst_13474);
} else
{if((state_val_13477 === (12)))
{var inst_13436 = (state_13476[(7)]);var inst_13464 = cljs.core.vec.call(null,inst_13436);var state_13476__$1 = state_13476;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13476__$1,(15),out,inst_13464);
} else
{if((state_val_13477 === (2)))
{var state_13476__$1 = state_13476;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13476__$1,(4),ch);
} else
{if((state_val_13477 === (11)))
{var inst_13440 = (state_13476[(9)]);var inst_13444 = (state_13476[(10)]);var inst_13454 = (state_13476[(2)]);var inst_13455 = [];var inst_13456 = inst_13455.push(inst_13440);var inst_13436 = inst_13455;var inst_13437 = inst_13444;var state_13476__$1 = (function (){var statearr_13488 = state_13476;(statearr_13488[(11)] = inst_13456);
(statearr_13488[(7)] = inst_13436);
(statearr_13488[(12)] = inst_13454);
(statearr_13488[(8)] = inst_13437);
return statearr_13488;
})();var statearr_13489_13515 = state_13476__$1;(statearr_13489_13515[(2)] = null);
(statearr_13489_13515[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (9)))
{var inst_13436 = (state_13476[(7)]);var inst_13452 = cljs.core.vec.call(null,inst_13436);var state_13476__$1 = state_13476;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13476__$1,(11),out,inst_13452);
} else
{if((state_val_13477 === (5)))
{var inst_13440 = (state_13476[(9)]);var inst_13437 = (state_13476[(8)]);var inst_13444 = (state_13476[(10)]);var inst_13444__$1 = f.call(null,inst_13440);var inst_13445 = cljs.core._EQ_.call(null,inst_13444__$1,inst_13437);var inst_13446 = cljs.core.keyword_identical_QMARK_.call(null,inst_13437,new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123));var inst_13447 = (inst_13445) || (inst_13446);var state_13476__$1 = (function (){var statearr_13490 = state_13476;(statearr_13490[(10)] = inst_13444__$1);
return statearr_13490;
})();if(cljs.core.truth_(inst_13447))
{var statearr_13491_13516 = state_13476__$1;(statearr_13491_13516[(1)] = (8));
} else
{var statearr_13492_13517 = state_13476__$1;(statearr_13492_13517[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (14)))
{var inst_13469 = (state_13476[(2)]);var inst_13470 = cljs.core.async.close_BANG_.call(null,out);var state_13476__$1 = (function (){var statearr_13494 = state_13476;(statearr_13494[(13)] = inst_13469);
return statearr_13494;
})();var statearr_13495_13518 = state_13476__$1;(statearr_13495_13518[(2)] = inst_13470);
(statearr_13495_13518[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (10)))
{var inst_13459 = (state_13476[(2)]);var state_13476__$1 = state_13476;var statearr_13496_13519 = state_13476__$1;(statearr_13496_13519[(2)] = inst_13459);
(statearr_13496_13519[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13477 === (8)))
{var inst_13436 = (state_13476[(7)]);var inst_13440 = (state_13476[(9)]);var inst_13444 = (state_13476[(10)]);var inst_13449 = inst_13436.push(inst_13440);var tmp13493 = inst_13436;var inst_13436__$1 = tmp13493;var inst_13437 = inst_13444;var state_13476__$1 = (function (){var statearr_13497 = state_13476;(statearr_13497[(7)] = inst_13436__$1);
(statearr_13497[(8)] = inst_13437);
(statearr_13497[(14)] = inst_13449);
return statearr_13497;
})();var statearr_13498_13520 = state_13476__$1;(statearr_13498_13520[(2)] = null);
(statearr_13498_13520[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
});})(c__5717__auto___13506,out))
;return ((function (switch__5702__auto__,c__5717__auto___13506,out){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_13502 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13502[(0)] = state_machine__5703__auto__);
(statearr_13502[(1)] = (1));
return statearr_13502;
});
var state_machine__5703__auto____1 = (function (state_13476){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_13476);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e13503){if((e13503 instanceof Object))
{var ex__5706__auto__ = e13503;var statearr_13504_13521 = state_13476;(statearr_13504_13521[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13476);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13503;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13522 = state_13476;
state_13476 = G__13522;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_13476){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_13476);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___13506,out))
})();var state__5719__auto__ = (function (){var statearr_13505 = f__5718__auto__.call(null);(statearr_13505[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___13506);
return statearr_13505;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___13506,out))
);
return out;
});
partition_by = function(f,ch,buf_or_n){
switch(arguments.length){
case 2:
return partition_by__2.call(this,f,ch);
case 3:
return partition_by__3.call(this,f,ch,buf_or_n);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
partition_by.cljs$core$IFn$_invoke$arity$2 = partition_by__2;
partition_by.cljs$core$IFn$_invoke$arity$3 = partition_by__3;
return partition_by;
})()
;
