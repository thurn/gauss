// Compiled by ClojureScript 0.0-2322
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
cljs.core.async.fn_handler = (function fn_handler(f){if(typeof cljs.core.async.t10458 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t10458 = (function (f,fn_handler,meta10459){
this.f = f;
this.fn_handler = fn_handler;
this.meta10459 = meta10459;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t10458.cljs$lang$type = true;
cljs.core.async.t10458.cljs$lang$ctorStr = "cljs.core.async/t10458";
cljs.core.async.t10458.cljs$lang$ctorPrWriter = (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t10458");
});
cljs.core.async.t10458.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t10458.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return true;
});
cljs.core.async.t10458.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return self__.f;
});
cljs.core.async.t10458.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_10460){var self__ = this;
var _10460__$1 = this;return self__.meta10459;
});
cljs.core.async.t10458.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_10460,meta10459__$1){var self__ = this;
var _10460__$1 = this;return (new cljs.core.async.t10458(self__.f,self__.fn_handler,meta10459__$1));
});
cljs.core.async.__GT_t10458 = (function __GT_t10458(f__$1,fn_handler__$1,meta10459){return (new cljs.core.async.t10458(f__$1,fn_handler__$1,meta10459));
});
}
return (new cljs.core.async.t10458(f,fn_handler,null));
});
/**
* Returns a fixed buffer of size n. When full, puts will block/park.
*/
cljs.core.async.buffer = (function buffer(n){return cljs.core.async.impl.buffers.fixed_buffer(n);
});
/**
* Returns a buffer of size n. When full, puts will complete but
* val will be dropped (no transfer).
*/
cljs.core.async.dropping_buffer = (function dropping_buffer(n){return cljs.core.async.impl.buffers.dropping_buffer(n);
});
/**
* Returns a buffer of size n. When full, puts will complete, and be
* buffered, but oldest elements in buffer will be dropped (not
* transferred).
*/
cljs.core.async.sliding_buffer = (function sliding_buffer(n){return cljs.core.async.impl.buffers.sliding_buffer(n);
});
/**
* Returns true if a channel created with buff will never block. That is to say,
* puts into this buffer will never cause the buffer to be full.
*/
cljs.core.async.unblocking_buffer_QMARK_ = (function unblocking_buffer_QMARK_(buff){var G__10462 = buff;if(G__10462)
{var bit__4210__auto__ = null;if(cljs.core.truth_((function (){var or__3560__auto__ = bit__4210__auto__;if(cljs.core.truth_(or__3560__auto__))
{return or__3560__auto__;
} else
{return G__10462.cljs$core$async$impl$protocols$UnblockingBuffer$;
}
})()))
{return true;
} else
{if((!G__10462.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_(cljs.core.async.impl.protocols.UnblockingBuffer,G__10462);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_(cljs.core.async.impl.protocols.UnblockingBuffer,G__10462);
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
var chan__0 = (function (){return chan.cljs$core$IFn$_invoke$arity$1(null);
});
var chan__1 = (function (buf_or_n){return chan.cljs$core$IFn$_invoke$arity$3(buf_or_n,null,null);
});
var chan__2 = (function (buf_or_n,xform){return chan.cljs$core$IFn$_invoke$arity$3(buf_or_n,xform,null);
});
var chan__3 = (function (buf_or_n,xform,ex_handler){var buf_or_n__$1 = ((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(buf_or_n,(0)))?null:buf_or_n);if(cljs.core.truth_(xform))
{if(cljs.core.truth_(buf_or_n__$1))
{} else
{throw (new Error(("Assert failed: buffer must be supplied when transducer is\n"+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.cljs$core$IFn$_invoke$arity$variadic(cljs.core.array_seq([new cljs.core.Symbol(null,"buf-or-n","buf-or-n",-1646815050,null)], 0))))));
}
} else
{}
return cljs.core.async.impl.channels.chan.cljs$core$IFn$_invoke$arity$3(((typeof buf_or_n__$1 === 'number')?cljs.core.async.buffer(buf_or_n__$1):buf_or_n__$1),xform,ex_handler);
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
cljs.core.async.timeout = (function timeout(msecs){return cljs.core.async.impl.timers.timeout(msecs);
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
var take_BANG___2 = (function (port,fn1){return take_BANG_.cljs$core$IFn$_invoke$arity$3(port,fn1,true);
});
var take_BANG___3 = (function (port,fn1,on_caller_QMARK_){var ret = cljs.core.async.impl.protocols.take_BANG_(port,cljs.core.async.fn_handler(fn1));if(cljs.core.truth_(ret))
{var val_10463 = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(ret) : cljs.core.deref.call(null,ret));if(cljs.core.truth_(on_caller_QMARK_))
{(fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(val_10463) : fn1.call(null,val_10463));
} else
{cljs.core.async.impl.dispatch.run(((function (val_10463,ret){
return (function (){return (fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(val_10463) : fn1.call(null,val_10463));
});})(val_10463,ret))
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
cljs.core.async.fhnop = cljs.core.async.fn_handler(cljs.core.async.nop);
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
var put_BANG___2 = (function (port,val){var temp__4124__auto__ = cljs.core.async.impl.protocols.put_BANG_(port,val,cljs.core.async.fhnop);if(cljs.core.truth_(temp__4124__auto__))
{var ret = temp__4124__auto__;return (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(ret) : cljs.core.deref.call(null,ret));
} else
{return true;
}
});
var put_BANG___3 = (function (port,val,fn1){return put_BANG_.cljs$core$IFn$_invoke$arity$4(port,val,fn1,true);
});
var put_BANG___4 = (function (port,val,fn1,on_caller_QMARK_){var temp__4124__auto__ = cljs.core.async.impl.protocols.put_BANG_(port,val,cljs.core.async.fn_handler(fn1));if(cljs.core.truth_(temp__4124__auto__))
{var retb = temp__4124__auto__;var ret = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(retb) : cljs.core.deref.call(null,retb));if(cljs.core.truth_(on_caller_QMARK_))
{(fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(ret) : fn1.call(null,ret));
} else
{cljs.core.async.impl.dispatch.run(((function (ret,retb,temp__4124__auto__){
return (function (){return (fn1.cljs$core$IFn$_invoke$arity$1 ? fn1.cljs$core$IFn$_invoke$arity$1(ret) : fn1.call(null,ret));
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
cljs.core.async.close_BANG_ = (function close_BANG_(port){return cljs.core.async.impl.protocols.close_BANG_(port);
});
cljs.core.async.random_array = (function random_array(n){var a = (new Array(n));var n__4416__auto___10464 = n;var x_10465 = (0);while(true){
if((x_10465 < n__4416__auto___10464))
{(a[x_10465] = (0));
{
var G__10466 = (x_10465 + (1));
x_10465 = G__10466;
continue;
}
} else
{}
break;
}
var i = (1);while(true){
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(i,n))
{return a;
} else
{var j = cljs.core.rand_int(i);(a[i] = (a[j]));
(a[j] = i);
{
var G__10467 = (i + (1));
i = G__10467;
continue;
}
}
break;
}
});
cljs.core.async.alt_flag = (function alt_flag(){var flag = (cljs.core.atom.cljs$core$IFn$_invoke$arity$1 ? cljs.core.atom.cljs$core$IFn$_invoke$arity$1(true) : cljs.core.atom.call(null,true));if(typeof cljs.core.async.t10471 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t10471 = (function (flag,alt_flag,meta10472){
this.flag = flag;
this.alt_flag = alt_flag;
this.meta10472 = meta10472;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t10471.cljs$lang$type = true;
cljs.core.async.t10471.cljs$lang$ctorStr = "cljs.core.async/t10471";
cljs.core.async.t10471.cljs$lang$ctorPrWriter = ((function (flag){
return (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t10471");
});})(flag))
;
cljs.core.async.t10471.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t10471.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = ((function (flag){
return (function (_){var self__ = this;
var ___$1 = this;return (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(self__.flag) : cljs.core.deref.call(null,self__.flag));
});})(flag))
;
cljs.core.async.t10471.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = ((function (flag){
return (function (_){var self__ = this;
var ___$1 = this;(cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2 ? cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2(self__.flag,null) : cljs.core.reset_BANG_.call(null,self__.flag,null));
return true;
});})(flag))
;
cljs.core.async.t10471.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (flag){
return (function (_10473){var self__ = this;
var _10473__$1 = this;return self__.meta10472;
});})(flag))
;
cljs.core.async.t10471.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (flag){
return (function (_10473,meta10472__$1){var self__ = this;
var _10473__$1 = this;return (new cljs.core.async.t10471(self__.flag,self__.alt_flag,meta10472__$1));
});})(flag))
;
cljs.core.async.__GT_t10471 = ((function (flag){
return (function __GT_t10471(flag__$1,alt_flag__$1,meta10472){return (new cljs.core.async.t10471(flag__$1,alt_flag__$1,meta10472));
});})(flag))
;
}
return (new cljs.core.async.t10471(flag,alt_flag,null));
});
cljs.core.async.alt_handler = (function alt_handler(flag,cb){if(typeof cljs.core.async.t10477 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t10477 = (function (cb,flag,alt_handler,meta10478){
this.cb = cb;
this.flag = flag;
this.alt_handler = alt_handler;
this.meta10478 = meta10478;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t10477.cljs$lang$type = true;
cljs.core.async.t10477.cljs$lang$ctorStr = "cljs.core.async/t10477";
cljs.core.async.t10477.cljs$lang$ctorPrWriter = (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t10477");
});
cljs.core.async.t10477.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t10477.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.active_QMARK_(self__.flag);
});
cljs.core.async.t10477.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){var self__ = this;
var ___$1 = this;cljs.core.async.impl.protocols.commit(self__.flag);
return self__.cb;
});
cljs.core.async.t10477.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_10479){var self__ = this;
var _10479__$1 = this;return self__.meta10478;
});
cljs.core.async.t10477.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_10479,meta10478__$1){var self__ = this;
var _10479__$1 = this;return (new cljs.core.async.t10477(self__.cb,self__.flag,self__.alt_handler,meta10478__$1));
});
cljs.core.async.__GT_t10477 = (function __GT_t10477(cb__$1,flag__$1,alt_handler__$1,meta10478){return (new cljs.core.async.t10477(cb__$1,flag__$1,alt_handler__$1,meta10478));
});
}
return (new cljs.core.async.t10477(cb,flag,alt_handler,null));
});
/**
* returns derefable [val port] if immediate, nil if enqueued
*/
cljs.core.async.do_alts = (function do_alts(fret,ports,opts){var flag = cljs.core.async.alt_flag();var n = cljs.core.count(ports);var idxs = cljs.core.async.random_array(n);var priority = cljs.core.constant$keyword$44.cljs$core$IFn$_invoke$arity$1(opts);var ret = (function (){var i = (0);while(true){
if((i < n))
{var idx = (cljs.core.truth_(priority)?i:(idxs[i]));var port = cljs.core.nth.cljs$core$IFn$_invoke$arity$2(ports,idx);var wport = ((cljs.core.vector_QMARK_(port))?(port.cljs$core$IFn$_invoke$arity$1 ? port.cljs$core$IFn$_invoke$arity$1((0)) : port.call(null,(0))):null);var vbox = (cljs.core.truth_(wport)?(function (){var val = (port.cljs$core$IFn$_invoke$arity$1 ? port.cljs$core$IFn$_invoke$arity$1((1)) : port.call(null,(1)));return cljs.core.async.impl.protocols.put_BANG_(wport,val,cljs.core.async.alt_handler(flag,((function (i,val,idx,port,wport,flag,n,idxs,priority){
return (function (p1__10480_SHARP_){return (fret.cljs$core$IFn$_invoke$arity$1 ? fret.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__10480_SHARP_,wport], null)) : fret.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__10480_SHARP_,wport], null)));
});})(i,val,idx,port,wport,flag,n,idxs,priority))
));
})():cljs.core.async.impl.protocols.take_BANG_(port,cljs.core.async.alt_handler(flag,((function (i,idx,port,wport,flag,n,idxs,priority){
return (function (p1__10481_SHARP_){return (fret.cljs$core$IFn$_invoke$arity$1 ? fret.cljs$core$IFn$_invoke$arity$1(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__10481_SHARP_,port], null)) : fret.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__10481_SHARP_,port], null)));
});})(i,idx,port,wport,flag,n,idxs,priority))
)));if(cljs.core.truth_(vbox))
{return cljs.core.async.impl.channels.box(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(vbox) : cljs.core.deref.call(null,vbox)),(function (){var or__3560__auto__ = wport;if(cljs.core.truth_(or__3560__auto__))
{return or__3560__auto__;
} else
{return port;
}
})()], null));
} else
{{
var G__10482 = (i + (1));
i = G__10482;
continue;
}
}
} else
{return null;
}
break;
}
})();var or__3560__auto__ = ret;if(cljs.core.truth_(or__3560__auto__))
{return or__3560__auto__;
} else
{if(cljs.core.contains_QMARK_(opts,cljs.core.constant$keyword$7))
{var temp__4126__auto__ = (function (){var and__3548__auto__ = flag.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1(null);if(cljs.core.truth_(and__3548__auto__))
{return flag.cljs$core$async$impl$protocols$Handler$commit$arity$1(null);
} else
{return and__3548__auto__;
}
})();if(cljs.core.truth_(temp__4126__auto__))
{var got = temp__4126__auto__;return cljs.core.async.impl.channels.box(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.constant$keyword$7.cljs$core$IFn$_invoke$arity$1(opts),cljs.core.constant$keyword$7], null));
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
var alts_BANG___delegate = function (ports,p__10483){var map__10485 = p__10483;var map__10485__$1 = ((cljs.core.seq_QMARK_(map__10485))?cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.hash_map,map__10485):map__10485);var opts = map__10485__$1;throw (new Error("alts! used not in (go ...) block"));
};
var alts_BANG_ = function (ports,var_args){
var p__10483 = null;if (arguments.length > 1) {
  p__10483 = cljs.core.array_seq(Array.prototype.slice.call(arguments, 1),0);} 
return alts_BANG___delegate.call(this,ports,p__10483);};
alts_BANG_.cljs$lang$maxFixedArity = 1;
alts_BANG_.cljs$lang$applyTo = (function (arglist__10486){
var ports = cljs.core.first(arglist__10486);
var p__10483 = cljs.core.rest(arglist__10486);
return alts_BANG___delegate(ports,p__10483);
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
var pipe__2 = (function (from,to){return pipe.cljs$core$IFn$_invoke$arity$3(from,to,true);
});
var pipe__3 = (function (from,to,close_QMARK_){var c__5724__auto___10581 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___10581){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___10581){
return (function (state_10557){var state_val_10558 = (state_10557[(1)]);if((state_val_10558 === (7)))
{var inst_10553 = (state_10557[(2)]);var state_10557__$1 = state_10557;var statearr_10559_10582 = state_10557__$1;(statearr_10559_10582[(2)] = inst_10553);
(statearr_10559_10582[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (1)))
{var state_10557__$1 = state_10557;var statearr_10560_10583 = state_10557__$1;(statearr_10560_10583[(2)] = null);
(statearr_10560_10583[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (4)))
{var inst_10536 = (state_10557[(7)]);var inst_10536__$1 = (state_10557[(2)]);var inst_10537 = (inst_10536__$1 == null);var state_10557__$1 = (function (){var statearr_10561 = state_10557;(statearr_10561[(7)] = inst_10536__$1);
return statearr_10561;
})();if(cljs.core.truth_(inst_10537))
{var statearr_10562_10584 = state_10557__$1;(statearr_10562_10584[(1)] = (5));
} else
{var statearr_10563_10585 = state_10557__$1;(statearr_10563_10585[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (13)))
{var state_10557__$1 = state_10557;var statearr_10564_10586 = state_10557__$1;(statearr_10564_10586[(2)] = null);
(statearr_10564_10586[(1)] = (14));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (6)))
{var inst_10536 = (state_10557[(7)]);var state_10557__$1 = state_10557;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_10557__$1,(11),to,inst_10536);
} else
{if((state_val_10558 === (3)))
{var inst_10555 = (state_10557[(2)]);var state_10557__$1 = state_10557;return cljs.core.async.impl.ioc_helpers.return_chan(state_10557__$1,inst_10555);
} else
{if((state_val_10558 === (12)))
{var state_10557__$1 = state_10557;var statearr_10565_10587 = state_10557__$1;(statearr_10565_10587[(2)] = null);
(statearr_10565_10587[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (2)))
{var state_10557__$1 = state_10557;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_10557__$1,(4),from);
} else
{if((state_val_10558 === (11)))
{var inst_10546 = (state_10557[(2)]);var state_10557__$1 = state_10557;if(cljs.core.truth_(inst_10546))
{var statearr_10566_10588 = state_10557__$1;(statearr_10566_10588[(1)] = (12));
} else
{var statearr_10567_10589 = state_10557__$1;(statearr_10567_10589[(1)] = (13));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (9)))
{var state_10557__$1 = state_10557;var statearr_10568_10590 = state_10557__$1;(statearr_10568_10590[(2)] = null);
(statearr_10568_10590[(1)] = (10));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (5)))
{var state_10557__$1 = state_10557;if(cljs.core.truth_(close_QMARK_))
{var statearr_10569_10591 = state_10557__$1;(statearr_10569_10591[(1)] = (8));
} else
{var statearr_10570_10592 = state_10557__$1;(statearr_10570_10592[(1)] = (9));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (14)))
{var inst_10551 = (state_10557[(2)]);var state_10557__$1 = state_10557;var statearr_10571_10593 = state_10557__$1;(statearr_10571_10593[(2)] = inst_10551);
(statearr_10571_10593[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (10)))
{var inst_10543 = (state_10557[(2)]);var state_10557__$1 = state_10557;var statearr_10572_10594 = state_10557__$1;(statearr_10572_10594[(2)] = inst_10543);
(statearr_10572_10594[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10558 === (8)))
{var inst_10540 = cljs.core.async.close_BANG_(to);var state_10557__$1 = state_10557;var statearr_10573_10595 = state_10557__$1;(statearr_10573_10595[(2)] = inst_10540);
(statearr_10573_10595[(1)] = (10));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___10581))
;return ((function (switch__5709__auto__,c__5724__auto___10581){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_10577 = [null,null,null,null,null,null,null,null];(statearr_10577[(0)] = state_machine__5710__auto__);
(statearr_10577[(1)] = (1));
return statearr_10577;
});
var state_machine__5710__auto____1 = (function (state_10557){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_10557);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e10578){if((e10578 instanceof Object))
{var ex__5713__auto__ = e10578;var statearr_10579_10596 = state_10557;(statearr_10579_10596[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_10557);
return cljs.core.constant$keyword$38;
} else
{throw e10578;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__10597 = state_10557;
state_10557 = G__10597;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_10557){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_10557);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___10581))
})();var state__5726__auto__ = (function (){var statearr_10580 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_10580[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___10581);
return statearr_10580;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___10581))
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
{throw (new Error(("Assert failed: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.cljs$core$IFn$_invoke$arity$variadic(cljs.core.array_seq([cljs.core.list(new cljs.core.Symbol(null,"pos?","pos?",-244377722,null),new cljs.core.Symbol(null,"n","n",-2092305744,null))], 0))))));
}
var jobs = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(n);var results = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(n);var process = ((function (jobs,results){
return (function (p__10781){var vec__10782 = p__10781;var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__10782,(0),null);var p = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__10782,(1),null);var job = vec__10782;if((job == null))
{cljs.core.async.close_BANG_(results);
return null;
} else
{var res = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$3((1),xf,ex_handler);var c__5724__auto___10964 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___10964,res,vec__10782,v,p,job,jobs,results){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___10964,res,vec__10782,v,p,job,jobs,results){
return (function (state_10787){var state_val_10788 = (state_10787[(1)]);if((state_val_10788 === (2)))
{var inst_10784 = (state_10787[(2)]);var inst_10785 = cljs.core.async.close_BANG_(res);var state_10787__$1 = (function (){var statearr_10789 = state_10787;(statearr_10789[(7)] = inst_10784);
return statearr_10789;
})();return cljs.core.async.impl.ioc_helpers.return_chan(state_10787__$1,inst_10785);
} else
{if((state_val_10788 === (1)))
{var state_10787__$1 = state_10787;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_10787__$1,(2),res,v);
} else
{return null;
}
}
});})(c__5724__auto___10964,res,vec__10782,v,p,job,jobs,results))
;return ((function (switch__5709__auto__,c__5724__auto___10964,res,vec__10782,v,p,job,jobs,results){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_10793 = [null,null,null,null,null,null,null,null];(statearr_10793[(0)] = state_machine__5710__auto__);
(statearr_10793[(1)] = (1));
return statearr_10793;
});
var state_machine__5710__auto____1 = (function (state_10787){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_10787);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e10794){if((e10794 instanceof Object))
{var ex__5713__auto__ = e10794;var statearr_10795_10965 = state_10787;(statearr_10795_10965[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_10787);
return cljs.core.constant$keyword$38;
} else
{throw e10794;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__10966 = state_10787;
state_10787 = G__10966;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_10787){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_10787);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___10964,res,vec__10782,v,p,job,jobs,results))
})();var state__5726__auto__ = (function (){var statearr_10796 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_10796[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___10964);
return statearr_10796;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___10964,res,vec__10782,v,p,job,jobs,results))
);
cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(p,res);
return true;
}
});})(jobs,results))
;var async = ((function (jobs,results,process){
return (function (p__10797){var vec__10798 = p__10797;var v = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__10798,(0),null);var p = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__10798,(1),null);var job = vec__10798;if((job == null))
{cljs.core.async.close_BANG_(results);
return null;
} else
{var res = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));(xf.cljs$core$IFn$_invoke$arity$2 ? xf.cljs$core$IFn$_invoke$arity$2(v,res) : xf.call(null,v,res));
cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(p,res);
return true;
}
});})(jobs,results,process))
;var n__4416__auto___10967 = n;var __10968 = (0);while(true){
if((__10968 < n__4416__auto___10967))
{var G__10799_10969 = (((type instanceof cljs.core.Keyword))?type.fqn:null);switch (G__10799_10969) {
case "async":
var c__5724__auto___10971 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (__10968,c__5724__auto___10971,G__10799_10969,n__4416__auto___10967,jobs,results,process,async){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (__10968,c__5724__auto___10971,G__10799_10969,n__4416__auto___10967,jobs,results,process,async){
return (function (state_10812){var state_val_10813 = (state_10812[(1)]);if((state_val_10813 === (7)))
{var inst_10808 = (state_10812[(2)]);var state_10812__$1 = state_10812;var statearr_10814_10972 = state_10812__$1;(statearr_10814_10972[(2)] = inst_10808);
(statearr_10814_10972[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10813 === (6)))
{var state_10812__$1 = state_10812;var statearr_10815_10973 = state_10812__$1;(statearr_10815_10973[(2)] = null);
(statearr_10815_10973[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10813 === (5)))
{var state_10812__$1 = state_10812;var statearr_10816_10974 = state_10812__$1;(statearr_10816_10974[(2)] = null);
(statearr_10816_10974[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10813 === (4)))
{var inst_10802 = (state_10812[(2)]);var inst_10803 = async(inst_10802);var state_10812__$1 = state_10812;if(cljs.core.truth_(inst_10803))
{var statearr_10817_10975 = state_10812__$1;(statearr_10817_10975[(1)] = (5));
} else
{var statearr_10818_10976 = state_10812__$1;(statearr_10818_10976[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10813 === (3)))
{var inst_10810 = (state_10812[(2)]);var state_10812__$1 = state_10812;return cljs.core.async.impl.ioc_helpers.return_chan(state_10812__$1,inst_10810);
} else
{if((state_val_10813 === (2)))
{var state_10812__$1 = state_10812;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_10812__$1,(4),jobs);
} else
{if((state_val_10813 === (1)))
{var state_10812__$1 = state_10812;var statearr_10819_10977 = state_10812__$1;(statearr_10819_10977[(2)] = null);
(statearr_10819_10977[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{return null;
}
}
}
}
}
}
}
});})(__10968,c__5724__auto___10971,G__10799_10969,n__4416__auto___10967,jobs,results,process,async))
;return ((function (__10968,switch__5709__auto__,c__5724__auto___10971,G__10799_10969,n__4416__auto___10967,jobs,results,process,async){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_10823 = [null,null,null,null,null,null,null];(statearr_10823[(0)] = state_machine__5710__auto__);
(statearr_10823[(1)] = (1));
return statearr_10823;
});
var state_machine__5710__auto____1 = (function (state_10812){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_10812);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e10824){if((e10824 instanceof Object))
{var ex__5713__auto__ = e10824;var statearr_10825_10978 = state_10812;(statearr_10825_10978[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_10812);
return cljs.core.constant$keyword$38;
} else
{throw e10824;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__10979 = state_10812;
state_10812 = G__10979;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_10812){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_10812);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(__10968,switch__5709__auto__,c__5724__auto___10971,G__10799_10969,n__4416__auto___10967,jobs,results,process,async))
})();var state__5726__auto__ = (function (){var statearr_10826 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_10826[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___10971);
return statearr_10826;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(__10968,c__5724__auto___10971,G__10799_10969,n__4416__auto___10967,jobs,results,process,async))
);

break;
case "compute":
var c__5724__auto___10980 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (__10968,c__5724__auto___10980,G__10799_10969,n__4416__auto___10967,jobs,results,process,async){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (__10968,c__5724__auto___10980,G__10799_10969,n__4416__auto___10967,jobs,results,process,async){
return (function (state_10839){var state_val_10840 = (state_10839[(1)]);if((state_val_10840 === (7)))
{var inst_10835 = (state_10839[(2)]);var state_10839__$1 = state_10839;var statearr_10841_10981 = state_10839__$1;(statearr_10841_10981[(2)] = inst_10835);
(statearr_10841_10981[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10840 === (6)))
{var state_10839__$1 = state_10839;var statearr_10842_10982 = state_10839__$1;(statearr_10842_10982[(2)] = null);
(statearr_10842_10982[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10840 === (5)))
{var state_10839__$1 = state_10839;var statearr_10843_10983 = state_10839__$1;(statearr_10843_10983[(2)] = null);
(statearr_10843_10983[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10840 === (4)))
{var inst_10829 = (state_10839[(2)]);var inst_10830 = process(inst_10829);var state_10839__$1 = state_10839;if(cljs.core.truth_(inst_10830))
{var statearr_10844_10984 = state_10839__$1;(statearr_10844_10984[(1)] = (5));
} else
{var statearr_10845_10985 = state_10839__$1;(statearr_10845_10985[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10840 === (3)))
{var inst_10837 = (state_10839[(2)]);var state_10839__$1 = state_10839;return cljs.core.async.impl.ioc_helpers.return_chan(state_10839__$1,inst_10837);
} else
{if((state_val_10840 === (2)))
{var state_10839__$1 = state_10839;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_10839__$1,(4),jobs);
} else
{if((state_val_10840 === (1)))
{var state_10839__$1 = state_10839;var statearr_10846_10986 = state_10839__$1;(statearr_10846_10986[(2)] = null);
(statearr_10846_10986[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{return null;
}
}
}
}
}
}
}
});})(__10968,c__5724__auto___10980,G__10799_10969,n__4416__auto___10967,jobs,results,process,async))
;return ((function (__10968,switch__5709__auto__,c__5724__auto___10980,G__10799_10969,n__4416__auto___10967,jobs,results,process,async){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_10850 = [null,null,null,null,null,null,null];(statearr_10850[(0)] = state_machine__5710__auto__);
(statearr_10850[(1)] = (1));
return statearr_10850;
});
var state_machine__5710__auto____1 = (function (state_10839){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_10839);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e10851){if((e10851 instanceof Object))
{var ex__5713__auto__ = e10851;var statearr_10852_10987 = state_10839;(statearr_10852_10987[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_10839);
return cljs.core.constant$keyword$38;
} else
{throw e10851;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__10988 = state_10839;
state_10839 = G__10988;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_10839){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_10839);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(__10968,switch__5709__auto__,c__5724__auto___10980,G__10799_10969,n__4416__auto___10967,jobs,results,process,async))
})();var state__5726__auto__ = (function (){var statearr_10853 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_10853[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___10980);
return statearr_10853;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(__10968,c__5724__auto___10980,G__10799_10969,n__4416__auto___10967,jobs,results,process,async))
);

break;
default:
throw (new Error(("No matching clause: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(type))));

}
{
var G__10989 = (__10968 + (1));
__10968 = G__10989;
continue;
}
} else
{}
break;
}
var c__5724__auto___10990 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___10990,jobs,results,process,async){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___10990,jobs,results,process,async){
return (function (state_10875){var state_val_10876 = (state_10875[(1)]);if((state_val_10876 === (9)))
{var inst_10868 = (state_10875[(2)]);var state_10875__$1 = (function (){var statearr_10877 = state_10875;(statearr_10877[(7)] = inst_10868);
return statearr_10877;
})();var statearr_10878_10991 = state_10875__$1;(statearr_10878_10991[(2)] = null);
(statearr_10878_10991[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10876 === (8)))
{var inst_10861 = (state_10875[(8)]);var inst_10866 = (state_10875[(2)]);var state_10875__$1 = (function (){var statearr_10879 = state_10875;(statearr_10879[(9)] = inst_10866);
return statearr_10879;
})();return cljs.core.async.impl.ioc_helpers.put_BANG_(state_10875__$1,(9),results,inst_10861);
} else
{if((state_val_10876 === (7)))
{var inst_10871 = (state_10875[(2)]);var state_10875__$1 = state_10875;var statearr_10880_10992 = state_10875__$1;(statearr_10880_10992[(2)] = inst_10871);
(statearr_10880_10992[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10876 === (6)))
{var inst_10861 = (state_10875[(8)]);var inst_10856 = (state_10875[(10)]);var inst_10861__$1 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));var inst_10862 = cljs.core.PersistentVector.EMPTY_NODE;var inst_10863 = [inst_10856,inst_10861__$1];var inst_10864 = (new cljs.core.PersistentVector(null,2,(5),inst_10862,inst_10863,null));var state_10875__$1 = (function (){var statearr_10881 = state_10875;(statearr_10881[(8)] = inst_10861__$1);
return statearr_10881;
})();return cljs.core.async.impl.ioc_helpers.put_BANG_(state_10875__$1,(8),jobs,inst_10864);
} else
{if((state_val_10876 === (5)))
{var inst_10859 = cljs.core.async.close_BANG_(jobs);var state_10875__$1 = state_10875;var statearr_10882_10993 = state_10875__$1;(statearr_10882_10993[(2)] = inst_10859);
(statearr_10882_10993[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10876 === (4)))
{var inst_10856 = (state_10875[(10)]);var inst_10856__$1 = (state_10875[(2)]);var inst_10857 = (inst_10856__$1 == null);var state_10875__$1 = (function (){var statearr_10883 = state_10875;(statearr_10883[(10)] = inst_10856__$1);
return statearr_10883;
})();if(cljs.core.truth_(inst_10857))
{var statearr_10884_10994 = state_10875__$1;(statearr_10884_10994[(1)] = (5));
} else
{var statearr_10885_10995 = state_10875__$1;(statearr_10885_10995[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10876 === (3)))
{var inst_10873 = (state_10875[(2)]);var state_10875__$1 = state_10875;return cljs.core.async.impl.ioc_helpers.return_chan(state_10875__$1,inst_10873);
} else
{if((state_val_10876 === (2)))
{var state_10875__$1 = state_10875;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_10875__$1,(4),from);
} else
{if((state_val_10876 === (1)))
{var state_10875__$1 = state_10875;var statearr_10886_10996 = state_10875__$1;(statearr_10886_10996[(2)] = null);
(statearr_10886_10996[(1)] = (2));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___10990,jobs,results,process,async))
;return ((function (switch__5709__auto__,c__5724__auto___10990,jobs,results,process,async){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_10890 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_10890[(0)] = state_machine__5710__auto__);
(statearr_10890[(1)] = (1));
return statearr_10890;
});
var state_machine__5710__auto____1 = (function (state_10875){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_10875);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e10891){if((e10891 instanceof Object))
{var ex__5713__auto__ = e10891;var statearr_10892_10997 = state_10875;(statearr_10892_10997[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_10875);
return cljs.core.constant$keyword$38;
} else
{throw e10891;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__10998 = state_10875;
state_10875 = G__10998;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_10875){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_10875);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___10990,jobs,results,process,async))
})();var state__5726__auto__ = (function (){var statearr_10893 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_10893[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___10990);
return statearr_10893;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___10990,jobs,results,process,async))
);
var c__5724__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto__,jobs,results,process,async){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto__,jobs,results,process,async){
return (function (state_10931){var state_val_10932 = (state_10931[(1)]);if((state_val_10932 === (7)))
{var inst_10927 = (state_10931[(2)]);var state_10931__$1 = state_10931;var statearr_10933_10999 = state_10931__$1;(statearr_10933_10999[(2)] = inst_10927);
(statearr_10933_10999[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (20)))
{var state_10931__$1 = state_10931;var statearr_10934_11000 = state_10931__$1;(statearr_10934_11000[(2)] = null);
(statearr_10934_11000[(1)] = (21));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (1)))
{var state_10931__$1 = state_10931;var statearr_10935_11001 = state_10931__$1;(statearr_10935_11001[(2)] = null);
(statearr_10935_11001[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (4)))
{var inst_10896 = (state_10931[(7)]);var inst_10896__$1 = (state_10931[(2)]);var inst_10897 = (inst_10896__$1 == null);var state_10931__$1 = (function (){var statearr_10936 = state_10931;(statearr_10936[(7)] = inst_10896__$1);
return statearr_10936;
})();if(cljs.core.truth_(inst_10897))
{var statearr_10937_11002 = state_10931__$1;(statearr_10937_11002[(1)] = (5));
} else
{var statearr_10938_11003 = state_10931__$1;(statearr_10938_11003[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (15)))
{var inst_10909 = (state_10931[(8)]);var state_10931__$1 = state_10931;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_10931__$1,(18),to,inst_10909);
} else
{if((state_val_10932 === (21)))
{var inst_10922 = (state_10931[(2)]);var state_10931__$1 = state_10931;var statearr_10939_11004 = state_10931__$1;(statearr_10939_11004[(2)] = inst_10922);
(statearr_10939_11004[(1)] = (13));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (13)))
{var inst_10924 = (state_10931[(2)]);var state_10931__$1 = (function (){var statearr_10940 = state_10931;(statearr_10940[(9)] = inst_10924);
return statearr_10940;
})();var statearr_10941_11005 = state_10931__$1;(statearr_10941_11005[(2)] = null);
(statearr_10941_11005[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (6)))
{var inst_10896 = (state_10931[(7)]);var state_10931__$1 = state_10931;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_10931__$1,(11),inst_10896);
} else
{if((state_val_10932 === (17)))
{var inst_10917 = (state_10931[(2)]);var state_10931__$1 = state_10931;if(cljs.core.truth_(inst_10917))
{var statearr_10942_11006 = state_10931__$1;(statearr_10942_11006[(1)] = (19));
} else
{var statearr_10943_11007 = state_10931__$1;(statearr_10943_11007[(1)] = (20));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (3)))
{var inst_10929 = (state_10931[(2)]);var state_10931__$1 = state_10931;return cljs.core.async.impl.ioc_helpers.return_chan(state_10931__$1,inst_10929);
} else
{if((state_val_10932 === (12)))
{var inst_10906 = (state_10931[(10)]);var state_10931__$1 = state_10931;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_10931__$1,(14),inst_10906);
} else
{if((state_val_10932 === (2)))
{var state_10931__$1 = state_10931;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_10931__$1,(4),results);
} else
{if((state_val_10932 === (19)))
{var state_10931__$1 = state_10931;var statearr_10944_11008 = state_10931__$1;(statearr_10944_11008[(2)] = null);
(statearr_10944_11008[(1)] = (12));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (11)))
{var inst_10906 = (state_10931[(2)]);var state_10931__$1 = (function (){var statearr_10945 = state_10931;(statearr_10945[(10)] = inst_10906);
return statearr_10945;
})();var statearr_10946_11009 = state_10931__$1;(statearr_10946_11009[(2)] = null);
(statearr_10946_11009[(1)] = (12));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (9)))
{var state_10931__$1 = state_10931;var statearr_10947_11010 = state_10931__$1;(statearr_10947_11010[(2)] = null);
(statearr_10947_11010[(1)] = (10));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (5)))
{var state_10931__$1 = state_10931;if(cljs.core.truth_(close_QMARK_))
{var statearr_10948_11011 = state_10931__$1;(statearr_10948_11011[(1)] = (8));
} else
{var statearr_10949_11012 = state_10931__$1;(statearr_10949_11012[(1)] = (9));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (14)))
{var inst_10909 = (state_10931[(8)]);var inst_10911 = (state_10931[(11)]);var inst_10909__$1 = (state_10931[(2)]);var inst_10910 = (inst_10909__$1 == null);var inst_10911__$1 = cljs.core.not(inst_10910);var state_10931__$1 = (function (){var statearr_10950 = state_10931;(statearr_10950[(8)] = inst_10909__$1);
(statearr_10950[(11)] = inst_10911__$1);
return statearr_10950;
})();if(inst_10911__$1)
{var statearr_10951_11013 = state_10931__$1;(statearr_10951_11013[(1)] = (15));
} else
{var statearr_10952_11014 = state_10931__$1;(statearr_10952_11014[(1)] = (16));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (16)))
{var inst_10911 = (state_10931[(11)]);var state_10931__$1 = state_10931;var statearr_10953_11015 = state_10931__$1;(statearr_10953_11015[(2)] = inst_10911);
(statearr_10953_11015[(1)] = (17));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (10)))
{var inst_10903 = (state_10931[(2)]);var state_10931__$1 = state_10931;var statearr_10954_11016 = state_10931__$1;(statearr_10954_11016[(2)] = inst_10903);
(statearr_10954_11016[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (18)))
{var inst_10914 = (state_10931[(2)]);var state_10931__$1 = state_10931;var statearr_10955_11017 = state_10931__$1;(statearr_10955_11017[(2)] = inst_10914);
(statearr_10955_11017[(1)] = (17));
return cljs.core.constant$keyword$38;
} else
{if((state_val_10932 === (8)))
{var inst_10900 = cljs.core.async.close_BANG_(to);var state_10931__$1 = state_10931;var statearr_10956_11018 = state_10931__$1;(statearr_10956_11018[(2)] = inst_10900);
(statearr_10956_11018[(1)] = (10));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto__,jobs,results,process,async))
;return ((function (switch__5709__auto__,c__5724__auto__,jobs,results,process,async){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_10960 = [null,null,null,null,null,null,null,null,null,null,null,null];(statearr_10960[(0)] = state_machine__5710__auto__);
(statearr_10960[(1)] = (1));
return statearr_10960;
});
var state_machine__5710__auto____1 = (function (state_10931){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_10931);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e10961){if((e10961 instanceof Object))
{var ex__5713__auto__ = e10961;var statearr_10962_11019 = state_10931;(statearr_10962_11019[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_10931);
return cljs.core.constant$keyword$38;
} else
{throw e10961;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__11020 = state_10931;
state_10931 = G__11020;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_10931){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_10931);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto__,jobs,results,process,async))
})();var state__5726__auto__ = (function (){var statearr_10963 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_10963[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto__);
return statearr_10963;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto__,jobs,results,process,async))
);
return c__5724__auto__;
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
var pipeline_async__4 = (function (n,to,af,from){return pipeline_async.cljs$core$IFn$_invoke$arity$5(n,to,af,from,true);
});
var pipeline_async__5 = (function (n,to,af,from,close_QMARK_){return cljs.core.async.pipeline_STAR_(n,to,af,from,close_QMARK_,null,cljs.core.constant$keyword$45);
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
var pipeline__4 = (function (n,to,xf,from){return pipeline.cljs$core$IFn$_invoke$arity$5(n,to,xf,from,true);
});
var pipeline__5 = (function (n,to,xf,from,close_QMARK_){return pipeline.cljs$core$IFn$_invoke$arity$6(n,to,xf,from,close_QMARK_,null);
});
var pipeline__6 = (function (n,to,xf,from,close_QMARK_,ex_handler){return cljs.core.async.pipeline_STAR_(n,to,xf,from,close_QMARK_,ex_handler,cljs.core.constant$keyword$46);
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
var split__2 = (function (p,ch){return split.cljs$core$IFn$_invoke$arity$4(p,ch,null,null);
});
var split__4 = (function (p,ch,t_buf_or_n,f_buf_or_n){var tc = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(t_buf_or_n);var fc = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(f_buf_or_n);var c__5724__auto___11121 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___11121,tc,fc){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___11121,tc,fc){
return (function (state_11096){var state_val_11097 = (state_11096[(1)]);if((state_val_11097 === (7)))
{var inst_11092 = (state_11096[(2)]);var state_11096__$1 = state_11096;var statearr_11098_11122 = state_11096__$1;(statearr_11098_11122[(2)] = inst_11092);
(statearr_11098_11122[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (1)))
{var state_11096__$1 = state_11096;var statearr_11099_11123 = state_11096__$1;(statearr_11099_11123[(2)] = null);
(statearr_11099_11123[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (4)))
{var inst_11073 = (state_11096[(7)]);var inst_11073__$1 = (state_11096[(2)]);var inst_11074 = (inst_11073__$1 == null);var state_11096__$1 = (function (){var statearr_11100 = state_11096;(statearr_11100[(7)] = inst_11073__$1);
return statearr_11100;
})();if(cljs.core.truth_(inst_11074))
{var statearr_11101_11124 = state_11096__$1;(statearr_11101_11124[(1)] = (5));
} else
{var statearr_11102_11125 = state_11096__$1;(statearr_11102_11125[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (13)))
{var state_11096__$1 = state_11096;var statearr_11103_11126 = state_11096__$1;(statearr_11103_11126[(2)] = null);
(statearr_11103_11126[(1)] = (14));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (6)))
{var inst_11073 = (state_11096[(7)]);var inst_11079 = (p.cljs$core$IFn$_invoke$arity$1 ? p.cljs$core$IFn$_invoke$arity$1(inst_11073) : p.call(null,inst_11073));var state_11096__$1 = state_11096;if(cljs.core.truth_(inst_11079))
{var statearr_11104_11127 = state_11096__$1;(statearr_11104_11127[(1)] = (9));
} else
{var statearr_11105_11128 = state_11096__$1;(statearr_11105_11128[(1)] = (10));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (3)))
{var inst_11094 = (state_11096[(2)]);var state_11096__$1 = state_11096;return cljs.core.async.impl.ioc_helpers.return_chan(state_11096__$1,inst_11094);
} else
{if((state_val_11097 === (12)))
{var state_11096__$1 = state_11096;var statearr_11106_11129 = state_11096__$1;(statearr_11106_11129[(2)] = null);
(statearr_11106_11129[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (2)))
{var state_11096__$1 = state_11096;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_11096__$1,(4),ch);
} else
{if((state_val_11097 === (11)))
{var inst_11073 = (state_11096[(7)]);var inst_11083 = (state_11096[(2)]);var state_11096__$1 = state_11096;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_11096__$1,(8),inst_11083,inst_11073);
} else
{if((state_val_11097 === (9)))
{var state_11096__$1 = state_11096;var statearr_11107_11130 = state_11096__$1;(statearr_11107_11130[(2)] = tc);
(statearr_11107_11130[(1)] = (11));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (5)))
{var inst_11076 = cljs.core.async.close_BANG_(tc);var inst_11077 = cljs.core.async.close_BANG_(fc);var state_11096__$1 = (function (){var statearr_11108 = state_11096;(statearr_11108[(8)] = inst_11076);
return statearr_11108;
})();var statearr_11109_11131 = state_11096__$1;(statearr_11109_11131[(2)] = inst_11077);
(statearr_11109_11131[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (14)))
{var inst_11090 = (state_11096[(2)]);var state_11096__$1 = state_11096;var statearr_11110_11132 = state_11096__$1;(statearr_11110_11132[(2)] = inst_11090);
(statearr_11110_11132[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (10)))
{var state_11096__$1 = state_11096;var statearr_11111_11133 = state_11096__$1;(statearr_11111_11133[(2)] = fc);
(statearr_11111_11133[(1)] = (11));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11097 === (8)))
{var inst_11085 = (state_11096[(2)]);var state_11096__$1 = state_11096;if(cljs.core.truth_(inst_11085))
{var statearr_11112_11134 = state_11096__$1;(statearr_11112_11134[(1)] = (12));
} else
{var statearr_11113_11135 = state_11096__$1;(statearr_11113_11135[(1)] = (13));
}
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___11121,tc,fc))
;return ((function (switch__5709__auto__,c__5724__auto___11121,tc,fc){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_11117 = [null,null,null,null,null,null,null,null,null];(statearr_11117[(0)] = state_machine__5710__auto__);
(statearr_11117[(1)] = (1));
return statearr_11117;
});
var state_machine__5710__auto____1 = (function (state_11096){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_11096);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e11118){if((e11118 instanceof Object))
{var ex__5713__auto__ = e11118;var statearr_11119_11136 = state_11096;(statearr_11119_11136[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_11096);
return cljs.core.constant$keyword$38;
} else
{throw e11118;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__11137 = state_11096;
state_11096 = G__11137;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_11096){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_11096);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___11121,tc,fc))
})();var state__5726__auto__ = (function (){var statearr_11120 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_11120[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___11121);
return statearr_11120;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___11121,tc,fc))
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
cljs.core.async.reduce = (function reduce(f,init,ch){var c__5724__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto__){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto__){
return (function (state_11184){var state_val_11185 = (state_11184[(1)]);if((state_val_11185 === (7)))
{var inst_11180 = (state_11184[(2)]);var state_11184__$1 = state_11184;var statearr_11186_11202 = state_11184__$1;(statearr_11186_11202[(2)] = inst_11180);
(statearr_11186_11202[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11185 === (6)))
{var inst_11173 = (state_11184[(7)]);var inst_11170 = (state_11184[(8)]);var inst_11177 = (f.cljs$core$IFn$_invoke$arity$2 ? f.cljs$core$IFn$_invoke$arity$2(inst_11170,inst_11173) : f.call(null,inst_11170,inst_11173));var inst_11170__$1 = inst_11177;var state_11184__$1 = (function (){var statearr_11187 = state_11184;(statearr_11187[(8)] = inst_11170__$1);
return statearr_11187;
})();var statearr_11188_11203 = state_11184__$1;(statearr_11188_11203[(2)] = null);
(statearr_11188_11203[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11185 === (5)))
{var inst_11170 = (state_11184[(8)]);var state_11184__$1 = state_11184;var statearr_11189_11204 = state_11184__$1;(statearr_11189_11204[(2)] = inst_11170);
(statearr_11189_11204[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11185 === (4)))
{var inst_11173 = (state_11184[(7)]);var inst_11173__$1 = (state_11184[(2)]);var inst_11174 = (inst_11173__$1 == null);var state_11184__$1 = (function (){var statearr_11190 = state_11184;(statearr_11190[(7)] = inst_11173__$1);
return statearr_11190;
})();if(cljs.core.truth_(inst_11174))
{var statearr_11191_11205 = state_11184__$1;(statearr_11191_11205[(1)] = (5));
} else
{var statearr_11192_11206 = state_11184__$1;(statearr_11192_11206[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11185 === (3)))
{var inst_11182 = (state_11184[(2)]);var state_11184__$1 = state_11184;return cljs.core.async.impl.ioc_helpers.return_chan(state_11184__$1,inst_11182);
} else
{if((state_val_11185 === (2)))
{var state_11184__$1 = state_11184;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_11184__$1,(4),ch);
} else
{if((state_val_11185 === (1)))
{var inst_11170 = init;var state_11184__$1 = (function (){var statearr_11193 = state_11184;(statearr_11193[(8)] = inst_11170);
return statearr_11193;
})();var statearr_11194_11207 = state_11184__$1;(statearr_11194_11207[(2)] = null);
(statearr_11194_11207[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{return null;
}
}
}
}
}
}
}
});})(c__5724__auto__))
;return ((function (switch__5709__auto__,c__5724__auto__){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_11198 = [null,null,null,null,null,null,null,null,null];(statearr_11198[(0)] = state_machine__5710__auto__);
(statearr_11198[(1)] = (1));
return statearr_11198;
});
var state_machine__5710__auto____1 = (function (state_11184){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_11184);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e11199){if((e11199 instanceof Object))
{var ex__5713__auto__ = e11199;var statearr_11200_11208 = state_11184;(statearr_11200_11208[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_11184);
return cljs.core.constant$keyword$38;
} else
{throw e11199;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__11209 = state_11184;
state_11184 = G__11209;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_11184){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_11184);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto__))
})();var state__5726__auto__ = (function (){var statearr_11201 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_11201[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto__);
return statearr_11201;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto__))
);
return c__5724__auto__;
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
var onto_chan__2 = (function (ch,coll){return onto_chan.cljs$core$IFn$_invoke$arity$3(ch,coll,true);
});
var onto_chan__3 = (function (ch,coll,close_QMARK_){var c__5724__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto__){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto__){
return (function (state_11283){var state_val_11284 = (state_11283[(1)]);if((state_val_11284 === (7)))
{var inst_11265 = (state_11283[(2)]);var state_11283__$1 = state_11283;var statearr_11285_11308 = state_11283__$1;(statearr_11285_11308[(2)] = inst_11265);
(statearr_11285_11308[(1)] = (6));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (1)))
{var inst_11259 = cljs.core.seq(coll);var inst_11260 = inst_11259;var state_11283__$1 = (function (){var statearr_11286 = state_11283;(statearr_11286[(7)] = inst_11260);
return statearr_11286;
})();var statearr_11287_11309 = state_11283__$1;(statearr_11287_11309[(2)] = null);
(statearr_11287_11309[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (4)))
{var inst_11260 = (state_11283[(7)]);var inst_11263 = cljs.core.first(inst_11260);var state_11283__$1 = state_11283;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_11283__$1,(7),ch,inst_11263);
} else
{if((state_val_11284 === (13)))
{var inst_11277 = (state_11283[(2)]);var state_11283__$1 = state_11283;var statearr_11288_11310 = state_11283__$1;(statearr_11288_11310[(2)] = inst_11277);
(statearr_11288_11310[(1)] = (10));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (6)))
{var inst_11268 = (state_11283[(2)]);var state_11283__$1 = state_11283;if(cljs.core.truth_(inst_11268))
{var statearr_11289_11311 = state_11283__$1;(statearr_11289_11311[(1)] = (8));
} else
{var statearr_11290_11312 = state_11283__$1;(statearr_11290_11312[(1)] = (9));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (3)))
{var inst_11281 = (state_11283[(2)]);var state_11283__$1 = state_11283;return cljs.core.async.impl.ioc_helpers.return_chan(state_11283__$1,inst_11281);
} else
{if((state_val_11284 === (12)))
{var state_11283__$1 = state_11283;var statearr_11291_11313 = state_11283__$1;(statearr_11291_11313[(2)] = null);
(statearr_11291_11313[(1)] = (13));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (2)))
{var inst_11260 = (state_11283[(7)]);var state_11283__$1 = state_11283;if(cljs.core.truth_(inst_11260))
{var statearr_11292_11314 = state_11283__$1;(statearr_11292_11314[(1)] = (4));
} else
{var statearr_11293_11315 = state_11283__$1;(statearr_11293_11315[(1)] = (5));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (11)))
{var inst_11274 = cljs.core.async.close_BANG_(ch);var state_11283__$1 = state_11283;var statearr_11294_11316 = state_11283__$1;(statearr_11294_11316[(2)] = inst_11274);
(statearr_11294_11316[(1)] = (13));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (9)))
{var state_11283__$1 = state_11283;if(cljs.core.truth_(close_QMARK_))
{var statearr_11295_11317 = state_11283__$1;(statearr_11295_11317[(1)] = (11));
} else
{var statearr_11296_11318 = state_11283__$1;(statearr_11296_11318[(1)] = (12));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (5)))
{var inst_11260 = (state_11283[(7)]);var state_11283__$1 = state_11283;var statearr_11297_11319 = state_11283__$1;(statearr_11297_11319[(2)] = inst_11260);
(statearr_11297_11319[(1)] = (6));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (10)))
{var inst_11279 = (state_11283[(2)]);var state_11283__$1 = state_11283;var statearr_11298_11320 = state_11283__$1;(statearr_11298_11320[(2)] = inst_11279);
(statearr_11298_11320[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11284 === (8)))
{var inst_11260 = (state_11283[(7)]);var inst_11270 = cljs.core.next(inst_11260);var inst_11260__$1 = inst_11270;var state_11283__$1 = (function (){var statearr_11299 = state_11283;(statearr_11299[(7)] = inst_11260__$1);
return statearr_11299;
})();var statearr_11300_11321 = state_11283__$1;(statearr_11300_11321[(2)] = null);
(statearr_11300_11321[(1)] = (2));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto__))
;return ((function (switch__5709__auto__,c__5724__auto__){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_11304 = [null,null,null,null,null,null,null,null];(statearr_11304[(0)] = state_machine__5710__auto__);
(statearr_11304[(1)] = (1));
return statearr_11304;
});
var state_machine__5710__auto____1 = (function (state_11283){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_11283);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e11305){if((e11305 instanceof Object))
{var ex__5713__auto__ = e11305;var statearr_11306_11322 = state_11283;(statearr_11306_11322[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_11283);
return cljs.core.constant$keyword$38;
} else
{throw e11305;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__11323 = state_11283;
state_11283 = G__11323;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_11283){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_11283);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto__))
})();var state__5726__auto__ = (function (){var statearr_11307 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_11307[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto__);
return statearr_11307;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto__))
);
return c__5724__auto__;
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
cljs.core.async.to_chan = (function to_chan(coll){var ch = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(cljs.core.bounded_count((100),coll));cljs.core.async.onto_chan.cljs$core$IFn$_invoke$arity$2(ch,coll);
return ch;
});
cljs.core.async.Mux = (function (){var obj11325 = {};return obj11325;
})();
cljs.core.async.muxch_STAR_ = (function muxch_STAR_(_){if((function (){var and__3548__auto__ = _;if(and__3548__auto__)
{return _.cljs$core$async$Mux$muxch_STAR_$arity$1;
} else
{return and__3548__auto__;
}
})())
{return _.cljs$core$async$Mux$muxch_STAR_$arity$1(_);
} else
{var x__4187__auto__ = (((_ == null))?null:_);return (function (){var or__3560__auto__ = (cljs.core.async.muxch_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.muxch_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Mux.muxch*",_);
}
}
})().call(null,_);
}
});
cljs.core.async.Mult = (function (){var obj11327 = {};return obj11327;
})();
cljs.core.async.tap_STAR_ = (function tap_STAR_(m,ch,close_QMARK_){if((function (){var and__3548__auto__ = m;if(and__3548__auto__)
{return m.cljs$core$async$Mult$tap_STAR_$arity$3;
} else
{return and__3548__auto__;
}
})())
{return m.cljs$core$async$Mult$tap_STAR_$arity$3(m,ch,close_QMARK_);
} else
{var x__4187__auto__ = (((m == null))?null:m);return (function (){var or__3560__auto__ = (cljs.core.async.tap_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.tap_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Mult.tap*",m);
}
}
})().call(null,m,ch,close_QMARK_);
}
});
cljs.core.async.untap_STAR_ = (function untap_STAR_(m,ch){if((function (){var and__3548__auto__ = m;if(and__3548__auto__)
{return m.cljs$core$async$Mult$untap_STAR_$arity$2;
} else
{return and__3548__auto__;
}
})())
{return m.cljs$core$async$Mult$untap_STAR_$arity$2(m,ch);
} else
{var x__4187__auto__ = (((m == null))?null:m);return (function (){var or__3560__auto__ = (cljs.core.async.untap_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.untap_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Mult.untap*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.untap_all_STAR_ = (function untap_all_STAR_(m){if((function (){var and__3548__auto__ = m;if(and__3548__auto__)
{return m.cljs$core$async$Mult$untap_all_STAR_$arity$1;
} else
{return and__3548__auto__;
}
})())
{return m.cljs$core$async$Mult$untap_all_STAR_$arity$1(m);
} else
{var x__4187__auto__ = (((m == null))?null:m);return (function (){var or__3560__auto__ = (cljs.core.async.untap_all_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.untap_all_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Mult.untap-all*",m);
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
cljs.core.async.mult = (function mult(ch){var cs = (cljs.core.atom.cljs$core$IFn$_invoke$arity$1 ? cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY) : cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY));var m = (function (){if(typeof cljs.core.async.t11549 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t11549 = (function (cs,ch,mult,meta11550){
this.cs = cs;
this.ch = ch;
this.mult = mult;
this.meta11550 = meta11550;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t11549.cljs$lang$type = true;
cljs.core.async.t11549.cljs$lang$ctorStr = "cljs.core.async/t11549";
cljs.core.async.t11549.cljs$lang$ctorPrWriter = ((function (cs){
return (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t11549");
});})(cs))
;
cljs.core.async.t11549.prototype.cljs$core$async$Mult$ = true;
cljs.core.async.t11549.prototype.cljs$core$async$Mult$tap_STAR_$arity$3 = ((function (cs){
return (function (_,ch__$2,close_QMARK_){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(self__.cs,cljs.core.assoc,ch__$2,close_QMARK_);
return null;
});})(cs))
;
cljs.core.async.t11549.prototype.cljs$core$async$Mult$untap_STAR_$arity$2 = ((function (cs){
return (function (_,ch__$2){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.cs,cljs.core.dissoc,ch__$2);
return null;
});})(cs))
;
cljs.core.async.t11549.prototype.cljs$core$async$Mult$untap_all_STAR_$arity$1 = ((function (cs){
return (function (_){var self__ = this;
var ___$1 = this;(cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2 ? cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2(self__.cs,cljs.core.PersistentArrayMap.EMPTY) : cljs.core.reset_BANG_.call(null,self__.cs,cljs.core.PersistentArrayMap.EMPTY));
return null;
});})(cs))
;
cljs.core.async.t11549.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t11549.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (cs){
return (function (_){var self__ = this;
var ___$1 = this;return self__.ch;
});})(cs))
;
cljs.core.async.t11549.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (cs){
return (function (_11551){var self__ = this;
var _11551__$1 = this;return self__.meta11550;
});})(cs))
;
cljs.core.async.t11549.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (cs){
return (function (_11551,meta11550__$1){var self__ = this;
var _11551__$1 = this;return (new cljs.core.async.t11549(self__.cs,self__.ch,self__.mult,meta11550__$1));
});})(cs))
;
cljs.core.async.__GT_t11549 = ((function (cs){
return (function __GT_t11549(cs__$1,ch__$1,mult__$1,meta11550){return (new cljs.core.async.t11549(cs__$1,ch__$1,mult__$1,meta11550));
});})(cs))
;
}
return (new cljs.core.async.t11549(cs,ch,mult,null));
})();var dchan = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));var dctr = (cljs.core.atom.cljs$core$IFn$_invoke$arity$1 ? cljs.core.atom.cljs$core$IFn$_invoke$arity$1(null) : cljs.core.atom.call(null,null));var done = ((function (cs,m,dchan,dctr){
return (function (_){if((cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(dctr,cljs.core.dec) === (0)))
{return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(dchan,true);
} else
{return null;
}
});})(cs,m,dchan,dctr))
;var c__5724__auto___11770 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___11770,cs,m,dchan,dctr,done){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___11770,cs,m,dchan,dctr,done){
return (function (state_11682){var state_val_11683 = (state_11682[(1)]);if((state_val_11683 === (7)))
{var inst_11678 = (state_11682[(2)]);var state_11682__$1 = state_11682;var statearr_11684_11771 = state_11682__$1;(statearr_11684_11771[(2)] = inst_11678);
(statearr_11684_11771[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (20)))
{var inst_11583 = (state_11682[(7)]);var inst_11593 = cljs.core.first(inst_11583);var inst_11594 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_11593,(0),null);var inst_11595 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_11593,(1),null);var state_11682__$1 = (function (){var statearr_11685 = state_11682;(statearr_11685[(8)] = inst_11594);
return statearr_11685;
})();if(cljs.core.truth_(inst_11595))
{var statearr_11686_11772 = state_11682__$1;(statearr_11686_11772[(1)] = (22));
} else
{var statearr_11687_11773 = state_11682__$1;(statearr_11687_11773[(1)] = (23));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (27)))
{var inst_11630 = (state_11682[(9)]);var inst_11623 = (state_11682[(10)]);var inst_11554 = (state_11682[(11)]);var inst_11625 = (state_11682[(12)]);var inst_11630__$1 = cljs.core._nth.cljs$core$IFn$_invoke$arity$2(inst_11623,inst_11625);var inst_11631 = cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$3(inst_11630__$1,inst_11554,done);var state_11682__$1 = (function (){var statearr_11688 = state_11682;(statearr_11688[(9)] = inst_11630__$1);
return statearr_11688;
})();if(cljs.core.truth_(inst_11631))
{var statearr_11689_11774 = state_11682__$1;(statearr_11689_11774[(1)] = (30));
} else
{var statearr_11690_11775 = state_11682__$1;(statearr_11690_11775[(1)] = (31));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (1)))
{var state_11682__$1 = state_11682;var statearr_11691_11776 = state_11682__$1;(statearr_11691_11776[(2)] = null);
(statearr_11691_11776[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (24)))
{var inst_11583 = (state_11682[(7)]);var inst_11600 = (state_11682[(2)]);var inst_11601 = cljs.core.next(inst_11583);var inst_11563 = inst_11601;var inst_11564 = null;var inst_11565 = (0);var inst_11566 = (0);var state_11682__$1 = (function (){var statearr_11692 = state_11682;(statearr_11692[(13)] = inst_11600);
(statearr_11692[(14)] = inst_11563);
(statearr_11692[(15)] = inst_11564);
(statearr_11692[(16)] = inst_11566);
(statearr_11692[(17)] = inst_11565);
return statearr_11692;
})();var statearr_11693_11777 = state_11682__$1;(statearr_11693_11777[(2)] = null);
(statearr_11693_11777[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (39)))
{var state_11682__$1 = state_11682;var statearr_11697_11778 = state_11682__$1;(statearr_11697_11778[(2)] = null);
(statearr_11697_11778[(1)] = (41));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (4)))
{var inst_11554 = (state_11682[(11)]);var inst_11554__$1 = (state_11682[(2)]);var inst_11555 = (inst_11554__$1 == null);var state_11682__$1 = (function (){var statearr_11698 = state_11682;(statearr_11698[(11)] = inst_11554__$1);
return statearr_11698;
})();if(cljs.core.truth_(inst_11555))
{var statearr_11699_11779 = state_11682__$1;(statearr_11699_11779[(1)] = (5));
} else
{var statearr_11700_11780 = state_11682__$1;(statearr_11700_11780[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (15)))
{var inst_11563 = (state_11682[(14)]);var inst_11564 = (state_11682[(15)]);var inst_11566 = (state_11682[(16)]);var inst_11565 = (state_11682[(17)]);var inst_11579 = (state_11682[(2)]);var inst_11580 = (inst_11566 + (1));var tmp11694 = inst_11563;var tmp11695 = inst_11564;var tmp11696 = inst_11565;var inst_11563__$1 = tmp11694;var inst_11564__$1 = tmp11695;var inst_11565__$1 = tmp11696;var inst_11566__$1 = inst_11580;var state_11682__$1 = (function (){var statearr_11701 = state_11682;(statearr_11701[(18)] = inst_11579);
(statearr_11701[(14)] = inst_11563__$1);
(statearr_11701[(15)] = inst_11564__$1);
(statearr_11701[(16)] = inst_11566__$1);
(statearr_11701[(17)] = inst_11565__$1);
return statearr_11701;
})();var statearr_11702_11781 = state_11682__$1;(statearr_11702_11781[(2)] = null);
(statearr_11702_11781[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (21)))
{var inst_11604 = (state_11682[(2)]);var state_11682__$1 = state_11682;var statearr_11706_11782 = state_11682__$1;(statearr_11706_11782[(2)] = inst_11604);
(statearr_11706_11782[(1)] = (18));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (31)))
{var inst_11630 = (state_11682[(9)]);var inst_11634 = done(null);var inst_11635 = m.cljs$core$async$Mult$untap_STAR_$arity$2(null,inst_11630);var state_11682__$1 = (function (){var statearr_11707 = state_11682;(statearr_11707[(19)] = inst_11634);
return statearr_11707;
})();var statearr_11708_11783 = state_11682__$1;(statearr_11708_11783[(2)] = inst_11635);
(statearr_11708_11783[(1)] = (32));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (32)))
{var inst_11624 = (state_11682[(20)]);var inst_11623 = (state_11682[(10)]);var inst_11622 = (state_11682[(21)]);var inst_11625 = (state_11682[(12)]);var inst_11637 = (state_11682[(2)]);var inst_11638 = (inst_11625 + (1));var tmp11703 = inst_11624;var tmp11704 = inst_11623;var tmp11705 = inst_11622;var inst_11622__$1 = tmp11705;var inst_11623__$1 = tmp11704;var inst_11624__$1 = tmp11703;var inst_11625__$1 = inst_11638;var state_11682__$1 = (function (){var statearr_11709 = state_11682;(statearr_11709[(22)] = inst_11637);
(statearr_11709[(20)] = inst_11624__$1);
(statearr_11709[(10)] = inst_11623__$1);
(statearr_11709[(21)] = inst_11622__$1);
(statearr_11709[(12)] = inst_11625__$1);
return statearr_11709;
})();var statearr_11710_11784 = state_11682__$1;(statearr_11710_11784[(2)] = null);
(statearr_11710_11784[(1)] = (25));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (40)))
{var inst_11650 = (state_11682[(23)]);var inst_11654 = done(null);var inst_11655 = m.cljs$core$async$Mult$untap_STAR_$arity$2(null,inst_11650);var state_11682__$1 = (function (){var statearr_11711 = state_11682;(statearr_11711[(24)] = inst_11654);
return statearr_11711;
})();var statearr_11712_11785 = state_11682__$1;(statearr_11712_11785[(2)] = inst_11655);
(statearr_11712_11785[(1)] = (41));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (33)))
{var inst_11641 = (state_11682[(25)]);var inst_11643 = cljs.core.chunked_seq_QMARK_(inst_11641);var state_11682__$1 = state_11682;if(inst_11643)
{var statearr_11713_11786 = state_11682__$1;(statearr_11713_11786[(1)] = (36));
} else
{var statearr_11714_11787 = state_11682__$1;(statearr_11714_11787[(1)] = (37));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (13)))
{var inst_11573 = (state_11682[(26)]);var inst_11576 = cljs.core.async.close_BANG_(inst_11573);var state_11682__$1 = state_11682;var statearr_11715_11788 = state_11682__$1;(statearr_11715_11788[(2)] = inst_11576);
(statearr_11715_11788[(1)] = (15));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (22)))
{var inst_11594 = (state_11682[(8)]);var inst_11597 = cljs.core.async.close_BANG_(inst_11594);var state_11682__$1 = state_11682;var statearr_11716_11789 = state_11682__$1;(statearr_11716_11789[(2)] = inst_11597);
(statearr_11716_11789[(1)] = (24));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (36)))
{var inst_11641 = (state_11682[(25)]);var inst_11645 = cljs.core.chunk_first(inst_11641);var inst_11646 = cljs.core.chunk_rest(inst_11641);var inst_11647 = cljs.core.count(inst_11645);var inst_11622 = inst_11646;var inst_11623 = inst_11645;var inst_11624 = inst_11647;var inst_11625 = (0);var state_11682__$1 = (function (){var statearr_11717 = state_11682;(statearr_11717[(20)] = inst_11624);
(statearr_11717[(10)] = inst_11623);
(statearr_11717[(21)] = inst_11622);
(statearr_11717[(12)] = inst_11625);
return statearr_11717;
})();var statearr_11718_11790 = state_11682__$1;(statearr_11718_11790[(2)] = null);
(statearr_11718_11790[(1)] = (25));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (41)))
{var inst_11641 = (state_11682[(25)]);var inst_11657 = (state_11682[(2)]);var inst_11658 = cljs.core.next(inst_11641);var inst_11622 = inst_11658;var inst_11623 = null;var inst_11624 = (0);var inst_11625 = (0);var state_11682__$1 = (function (){var statearr_11719 = state_11682;(statearr_11719[(27)] = inst_11657);
(statearr_11719[(20)] = inst_11624);
(statearr_11719[(10)] = inst_11623);
(statearr_11719[(21)] = inst_11622);
(statearr_11719[(12)] = inst_11625);
return statearr_11719;
})();var statearr_11720_11791 = state_11682__$1;(statearr_11720_11791[(2)] = null);
(statearr_11720_11791[(1)] = (25));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (43)))
{var state_11682__$1 = state_11682;var statearr_11721_11792 = state_11682__$1;(statearr_11721_11792[(2)] = null);
(statearr_11721_11792[(1)] = (44));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (29)))
{var inst_11666 = (state_11682[(2)]);var state_11682__$1 = state_11682;var statearr_11722_11793 = state_11682__$1;(statearr_11722_11793[(2)] = inst_11666);
(statearr_11722_11793[(1)] = (26));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (44)))
{var inst_11675 = (state_11682[(2)]);var state_11682__$1 = (function (){var statearr_11723 = state_11682;(statearr_11723[(28)] = inst_11675);
return statearr_11723;
})();var statearr_11724_11794 = state_11682__$1;(statearr_11724_11794[(2)] = null);
(statearr_11724_11794[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (6)))
{var inst_11614 = (state_11682[(29)]);var inst_11613 = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(cs) : cljs.core.deref.call(null,cs));var inst_11614__$1 = cljs.core.keys(inst_11613);var inst_11615 = cljs.core.count(inst_11614__$1);var inst_11616 = (cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2 ? cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2(dctr,inst_11615) : cljs.core.reset_BANG_.call(null,dctr,inst_11615));var inst_11621 = cljs.core.seq(inst_11614__$1);var inst_11622 = inst_11621;var inst_11623 = null;var inst_11624 = (0);var inst_11625 = (0);var state_11682__$1 = (function (){var statearr_11725 = state_11682;(statearr_11725[(30)] = inst_11616);
(statearr_11725[(29)] = inst_11614__$1);
(statearr_11725[(20)] = inst_11624);
(statearr_11725[(10)] = inst_11623);
(statearr_11725[(21)] = inst_11622);
(statearr_11725[(12)] = inst_11625);
return statearr_11725;
})();var statearr_11726_11795 = state_11682__$1;(statearr_11726_11795[(2)] = null);
(statearr_11726_11795[(1)] = (25));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (28)))
{var inst_11641 = (state_11682[(25)]);var inst_11622 = (state_11682[(21)]);var inst_11641__$1 = cljs.core.seq(inst_11622);var state_11682__$1 = (function (){var statearr_11727 = state_11682;(statearr_11727[(25)] = inst_11641__$1);
return statearr_11727;
})();if(inst_11641__$1)
{var statearr_11728_11796 = state_11682__$1;(statearr_11728_11796[(1)] = (33));
} else
{var statearr_11729_11797 = state_11682__$1;(statearr_11729_11797[(1)] = (34));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (25)))
{var inst_11624 = (state_11682[(20)]);var inst_11625 = (state_11682[(12)]);var inst_11627 = (inst_11625 < inst_11624);var inst_11628 = inst_11627;var state_11682__$1 = state_11682;if(cljs.core.truth_(inst_11628))
{var statearr_11730_11798 = state_11682__$1;(statearr_11730_11798[(1)] = (27));
} else
{var statearr_11731_11799 = state_11682__$1;(statearr_11731_11799[(1)] = (28));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (34)))
{var state_11682__$1 = state_11682;var statearr_11732_11800 = state_11682__$1;(statearr_11732_11800[(2)] = null);
(statearr_11732_11800[(1)] = (35));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (17)))
{var state_11682__$1 = state_11682;var statearr_11733_11801 = state_11682__$1;(statearr_11733_11801[(2)] = null);
(statearr_11733_11801[(1)] = (18));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (3)))
{var inst_11680 = (state_11682[(2)]);var state_11682__$1 = state_11682;return cljs.core.async.impl.ioc_helpers.return_chan(state_11682__$1,inst_11680);
} else
{if((state_val_11683 === (12)))
{var inst_11609 = (state_11682[(2)]);var state_11682__$1 = state_11682;var statearr_11734_11802 = state_11682__$1;(statearr_11734_11802[(2)] = inst_11609);
(statearr_11734_11802[(1)] = (9));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (2)))
{var state_11682__$1 = state_11682;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_11682__$1,(4),ch);
} else
{if((state_val_11683 === (23)))
{var state_11682__$1 = state_11682;var statearr_11735_11803 = state_11682__$1;(statearr_11735_11803[(2)] = null);
(statearr_11735_11803[(1)] = (24));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (35)))
{var inst_11664 = (state_11682[(2)]);var state_11682__$1 = state_11682;var statearr_11736_11804 = state_11682__$1;(statearr_11736_11804[(2)] = inst_11664);
(statearr_11736_11804[(1)] = (29));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (19)))
{var inst_11583 = (state_11682[(7)]);var inst_11587 = cljs.core.chunk_first(inst_11583);var inst_11588 = cljs.core.chunk_rest(inst_11583);var inst_11589 = cljs.core.count(inst_11587);var inst_11563 = inst_11588;var inst_11564 = inst_11587;var inst_11565 = inst_11589;var inst_11566 = (0);var state_11682__$1 = (function (){var statearr_11737 = state_11682;(statearr_11737[(14)] = inst_11563);
(statearr_11737[(15)] = inst_11564);
(statearr_11737[(16)] = inst_11566);
(statearr_11737[(17)] = inst_11565);
return statearr_11737;
})();var statearr_11738_11805 = state_11682__$1;(statearr_11738_11805[(2)] = null);
(statearr_11738_11805[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (11)))
{var inst_11583 = (state_11682[(7)]);var inst_11563 = (state_11682[(14)]);var inst_11583__$1 = cljs.core.seq(inst_11563);var state_11682__$1 = (function (){var statearr_11739 = state_11682;(statearr_11739[(7)] = inst_11583__$1);
return statearr_11739;
})();if(inst_11583__$1)
{var statearr_11740_11806 = state_11682__$1;(statearr_11740_11806[(1)] = (16));
} else
{var statearr_11741_11807 = state_11682__$1;(statearr_11741_11807[(1)] = (17));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (9)))
{var inst_11611 = (state_11682[(2)]);var state_11682__$1 = state_11682;var statearr_11742_11808 = state_11682__$1;(statearr_11742_11808[(2)] = inst_11611);
(statearr_11742_11808[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (5)))
{var inst_11561 = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(cs) : cljs.core.deref.call(null,cs));var inst_11562 = cljs.core.seq(inst_11561);var inst_11563 = inst_11562;var inst_11564 = null;var inst_11565 = (0);var inst_11566 = (0);var state_11682__$1 = (function (){var statearr_11743 = state_11682;(statearr_11743[(14)] = inst_11563);
(statearr_11743[(15)] = inst_11564);
(statearr_11743[(16)] = inst_11566);
(statearr_11743[(17)] = inst_11565);
return statearr_11743;
})();var statearr_11744_11809 = state_11682__$1;(statearr_11744_11809[(2)] = null);
(statearr_11744_11809[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (14)))
{var state_11682__$1 = state_11682;var statearr_11745_11810 = state_11682__$1;(statearr_11745_11810[(2)] = null);
(statearr_11745_11810[(1)] = (15));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (45)))
{var inst_11672 = (state_11682[(2)]);var state_11682__$1 = state_11682;var statearr_11746_11811 = state_11682__$1;(statearr_11746_11811[(2)] = inst_11672);
(statearr_11746_11811[(1)] = (44));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (26)))
{var inst_11614 = (state_11682[(29)]);var inst_11668 = (state_11682[(2)]);var inst_11669 = cljs.core.seq(inst_11614);var state_11682__$1 = (function (){var statearr_11747 = state_11682;(statearr_11747[(31)] = inst_11668);
return statearr_11747;
})();if(inst_11669)
{var statearr_11748_11812 = state_11682__$1;(statearr_11748_11812[(1)] = (42));
} else
{var statearr_11749_11813 = state_11682__$1;(statearr_11749_11813[(1)] = (43));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (16)))
{var inst_11583 = (state_11682[(7)]);var inst_11585 = cljs.core.chunked_seq_QMARK_(inst_11583);var state_11682__$1 = state_11682;if(inst_11585)
{var statearr_11750_11814 = state_11682__$1;(statearr_11750_11814[(1)] = (19));
} else
{var statearr_11751_11815 = state_11682__$1;(statearr_11751_11815[(1)] = (20));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (38)))
{var inst_11661 = (state_11682[(2)]);var state_11682__$1 = state_11682;var statearr_11752_11816 = state_11682__$1;(statearr_11752_11816[(2)] = inst_11661);
(statearr_11752_11816[(1)] = (35));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (30)))
{var state_11682__$1 = state_11682;var statearr_11753_11817 = state_11682__$1;(statearr_11753_11817[(2)] = null);
(statearr_11753_11817[(1)] = (32));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (10)))
{var inst_11564 = (state_11682[(15)]);var inst_11566 = (state_11682[(16)]);var inst_11572 = cljs.core._nth.cljs$core$IFn$_invoke$arity$2(inst_11564,inst_11566);var inst_11573 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_11572,(0),null);var inst_11574 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_11572,(1),null);var state_11682__$1 = (function (){var statearr_11754 = state_11682;(statearr_11754[(26)] = inst_11573);
return statearr_11754;
})();if(cljs.core.truth_(inst_11574))
{var statearr_11755_11818 = state_11682__$1;(statearr_11755_11818[(1)] = (13));
} else
{var statearr_11756_11819 = state_11682__$1;(statearr_11756_11819[(1)] = (14));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (18)))
{var inst_11607 = (state_11682[(2)]);var state_11682__$1 = state_11682;var statearr_11757_11820 = state_11682__$1;(statearr_11757_11820[(2)] = inst_11607);
(statearr_11757_11820[(1)] = (12));
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (42)))
{var state_11682__$1 = state_11682;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_11682__$1,(45),dchan);
} else
{if((state_val_11683 === (37)))
{var inst_11641 = (state_11682[(25)]);var inst_11554 = (state_11682[(11)]);var inst_11650 = (state_11682[(23)]);var inst_11650__$1 = cljs.core.first(inst_11641);var inst_11651 = cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$3(inst_11650__$1,inst_11554,done);var state_11682__$1 = (function (){var statearr_11758 = state_11682;(statearr_11758[(23)] = inst_11650__$1);
return statearr_11758;
})();if(cljs.core.truth_(inst_11651))
{var statearr_11759_11821 = state_11682__$1;(statearr_11759_11821[(1)] = (39));
} else
{var statearr_11760_11822 = state_11682__$1;(statearr_11760_11822[(1)] = (40));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_11683 === (8)))
{var inst_11566 = (state_11682[(16)]);var inst_11565 = (state_11682[(17)]);var inst_11568 = (inst_11566 < inst_11565);var inst_11569 = inst_11568;var state_11682__$1 = state_11682;if(cljs.core.truth_(inst_11569))
{var statearr_11761_11823 = state_11682__$1;(statearr_11761_11823[(1)] = (10));
} else
{var statearr_11762_11824 = state_11682__$1;(statearr_11762_11824[(1)] = (11));
}
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___11770,cs,m,dchan,dctr,done))
;return ((function (switch__5709__auto__,c__5724__auto___11770,cs,m,dchan,dctr,done){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_11766 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_11766[(0)] = state_machine__5710__auto__);
(statearr_11766[(1)] = (1));
return statearr_11766;
});
var state_machine__5710__auto____1 = (function (state_11682){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_11682);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e11767){if((e11767 instanceof Object))
{var ex__5713__auto__ = e11767;var statearr_11768_11825 = state_11682;(statearr_11768_11825[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_11682);
return cljs.core.constant$keyword$38;
} else
{throw e11767;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__11826 = state_11682;
state_11682 = G__11826;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_11682){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_11682);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___11770,cs,m,dchan,dctr,done))
})();var state__5726__auto__ = (function (){var statearr_11769 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_11769[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___11770);
return statearr_11769;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___11770,cs,m,dchan,dctr,done))
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
var tap__2 = (function (mult,ch){return tap.cljs$core$IFn$_invoke$arity$3(mult,ch,true);
});
var tap__3 = (function (mult,ch,close_QMARK_){cljs.core.async.tap_STAR_(mult,ch,close_QMARK_);
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
cljs.core.async.untap = (function untap(mult,ch){return cljs.core.async.untap_STAR_(mult,ch);
});
/**
* Disconnects all target channels from a mult
*/
cljs.core.async.untap_all = (function untap_all(mult){return cljs.core.async.untap_all_STAR_(mult);
});
cljs.core.async.Mix = (function (){var obj11828 = {};return obj11828;
})();
cljs.core.async.admix_STAR_ = (function admix_STAR_(m,ch){if((function (){var and__3548__auto__ = m;if(and__3548__auto__)
{return m.cljs$core$async$Mix$admix_STAR_$arity$2;
} else
{return and__3548__auto__;
}
})())
{return m.cljs$core$async$Mix$admix_STAR_$arity$2(m,ch);
} else
{var x__4187__auto__ = (((m == null))?null:m);return (function (){var or__3560__auto__ = (cljs.core.async.admix_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.admix_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Mix.admix*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.unmix_STAR_ = (function unmix_STAR_(m,ch){if((function (){var and__3548__auto__ = m;if(and__3548__auto__)
{return m.cljs$core$async$Mix$unmix_STAR_$arity$2;
} else
{return and__3548__auto__;
}
})())
{return m.cljs$core$async$Mix$unmix_STAR_$arity$2(m,ch);
} else
{var x__4187__auto__ = (((m == null))?null:m);return (function (){var or__3560__auto__ = (cljs.core.async.unmix_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.unmix_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Mix.unmix*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.unmix_all_STAR_ = (function unmix_all_STAR_(m){if((function (){var and__3548__auto__ = m;if(and__3548__auto__)
{return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1;
} else
{return and__3548__auto__;
}
})())
{return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1(m);
} else
{var x__4187__auto__ = (((m == null))?null:m);return (function (){var or__3560__auto__ = (cljs.core.async.unmix_all_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.unmix_all_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Mix.unmix-all*",m);
}
}
})().call(null,m);
}
});
cljs.core.async.toggle_STAR_ = (function toggle_STAR_(m,state_map){if((function (){var and__3548__auto__ = m;if(and__3548__auto__)
{return m.cljs$core$async$Mix$toggle_STAR_$arity$2;
} else
{return and__3548__auto__;
}
})())
{return m.cljs$core$async$Mix$toggle_STAR_$arity$2(m,state_map);
} else
{var x__4187__auto__ = (((m == null))?null:m);return (function (){var or__3560__auto__ = (cljs.core.async.toggle_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.toggle_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Mix.toggle*",m);
}
}
})().call(null,m,state_map);
}
});
cljs.core.async.solo_mode_STAR_ = (function solo_mode_STAR_(m,mode){if((function (){var and__3548__auto__ = m;if(and__3548__auto__)
{return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2;
} else
{return and__3548__auto__;
}
})())
{return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2(m,mode);
} else
{var x__4187__auto__ = (((m == null))?null:m);return (function (){var or__3560__auto__ = (cljs.core.async.solo_mode_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.solo_mode_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Mix.solo-mode*",m);
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
cljs.core.async.mix = (function mix(out){var cs = (cljs.core.atom.cljs$core$IFn$_invoke$arity$1 ? cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY) : cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY));var solo_modes = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [cljs.core.constant$keyword$47,null,cljs.core.constant$keyword$48,null], null), null);var attrs = cljs.core.conj.cljs$core$IFn$_invoke$arity$2(solo_modes,cljs.core.constant$keyword$49);var solo_mode = (cljs.core.atom.cljs$core$IFn$_invoke$arity$1 ? cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.constant$keyword$48) : cljs.core.atom.call(null,cljs.core.constant$keyword$48));var change = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$0();var changed = ((function (cs,solo_modes,attrs,solo_mode,change){
return (function (){return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(change,true);
});})(cs,solo_modes,attrs,solo_mode,change))
;var pick = ((function (cs,solo_modes,attrs,solo_mode,change,changed){
return (function (attr,chs){return cljs.core.reduce_kv(((function (cs,solo_modes,attrs,solo_mode,change,changed){
return (function (ret,c,v){if(cljs.core.truth_((attr.cljs$core$IFn$_invoke$arity$1 ? attr.cljs$core$IFn$_invoke$arity$1(v) : attr.call(null,v))))
{return cljs.core.conj.cljs$core$IFn$_invoke$arity$2(ret,c);
} else
{return ret;
}
});})(cs,solo_modes,attrs,solo_mode,change,changed))
,cljs.core.PersistentHashSet.EMPTY,chs);
});})(cs,solo_modes,attrs,solo_mode,change,changed))
;var calc_state = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick){
return (function (){var chs = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(cs) : cljs.core.deref.call(null,cs));var mode = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(solo_mode) : cljs.core.deref.call(null,solo_mode));var solos = pick(cljs.core.constant$keyword$49,chs);var pauses = pick(cljs.core.constant$keyword$47,chs);return new cljs.core.PersistentArrayMap(null, 3, [cljs.core.constant$keyword$50,solos,cljs.core.constant$keyword$51,pick(cljs.core.constant$keyword$48,chs),cljs.core.constant$keyword$52,cljs.core.conj.cljs$core$IFn$_invoke$arity$2((((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(mode,cljs.core.constant$keyword$47)) && (!(cljs.core.empty_QMARK_(solos))))?cljs.core.vec(solos):cljs.core.vec(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(pauses,cljs.core.keys(chs)))),change)], null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick))
;var m = (function (){if(typeof cljs.core.async.t11948 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t11948 = (function (change,mix,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,meta11949){
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
this.meta11949 = meta11949;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t11948.cljs$lang$type = true;
cljs.core.async.t11948.cljs$lang$ctorStr = "cljs.core.async/t11948";
cljs.core.async.t11948.cljs$lang$ctorPrWriter = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t11948");
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11948.prototype.cljs$core$async$Mix$ = true;
cljs.core.async.t11948.prototype.cljs$core$async$Mix$admix_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,ch){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(self__.cs,cljs.core.assoc,ch,cljs.core.PersistentArrayMap.EMPTY);
return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11948.prototype.cljs$core$async$Mix$unmix_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,ch){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.cs,cljs.core.dissoc,ch);
return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11948.prototype.cljs$core$async$Mix$unmix_all_STAR_$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_){var self__ = this;
var ___$1 = this;(cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2 ? cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2(self__.cs,cljs.core.PersistentArrayMap.EMPTY) : cljs.core.reset_BANG_.call(null,self__.cs,cljs.core.PersistentArrayMap.EMPTY));
return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11948.prototype.cljs$core$async$Mix$toggle_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,state_map){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.cs,cljs.core.partial.cljs$core$IFn$_invoke$arity$2(cljs.core.merge_with,cljs.core.merge),state_map);
return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11948.prototype.cljs$core$async$Mix$solo_mode_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,mode){var self__ = this;
var ___$1 = this;if(cljs.core.truth_((self__.solo_modes.cljs$core$IFn$_invoke$arity$1 ? self__.solo_modes.cljs$core$IFn$_invoke$arity$1(mode) : self__.solo_modes.call(null,mode))))
{} else
{throw (new Error(("Assert failed: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(("mode must be one of: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(self__.solo_modes)))+"\n"+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.cljs$core$IFn$_invoke$arity$variadic(cljs.core.array_seq([cljs.core.list(new cljs.core.Symbol(null,"solo-modes","solo-modes",882180540,null),new cljs.core.Symbol(null,"mode","mode",-2000032078,null))], 0))))));
}
(cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2 ? cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2(self__.solo_mode,mode) : cljs.core.reset_BANG_.call(null,self__.solo_mode,mode));
return (self__.changed.cljs$core$IFn$_invoke$arity$0 ? self__.changed.cljs$core$IFn$_invoke$arity$0() : self__.changed.call(null));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11948.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t11948.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_){var self__ = this;
var ___$1 = this;return self__.out;
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11948.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_11950){var self__ = this;
var _11950__$1 = this;return self__.meta11949;
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t11948.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_11950,meta11949__$1){var self__ = this;
var _11950__$1 = this;return (new cljs.core.async.t11948(self__.change,self__.mix,self__.solo_mode,self__.pick,self__.cs,self__.calc_state,self__.out,self__.changed,self__.solo_modes,self__.attrs,meta11949__$1));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.__GT_t11948 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function __GT_t11948(change__$1,mix__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta11949){return (new cljs.core.async.t11948(change__$1,mix__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta11949));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
}
return (new cljs.core.async.t11948(change,mix,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,null));
})();var c__5724__auto___12067 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___12067,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___12067,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function (state_12020){var state_val_12021 = (state_12020[(1)]);if((state_val_12021 === (7)))
{var inst_11964 = (state_12020[(7)]);var inst_11969 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.hash_map,inst_11964);var state_12020__$1 = state_12020;var statearr_12022_12068 = state_12020__$1;(statearr_12022_12068[(2)] = inst_11969);
(statearr_12022_12068[(1)] = (9));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (20)))
{var inst_11979 = (state_12020[(8)]);var state_12020__$1 = state_12020;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_12020__$1,(23),out,inst_11979);
} else
{if((state_val_12021 === (1)))
{var inst_11954 = (state_12020[(9)]);var inst_11954__$1 = calc_state();var inst_11955 = cljs.core.seq_QMARK_(inst_11954__$1);var state_12020__$1 = (function (){var statearr_12023 = state_12020;(statearr_12023[(9)] = inst_11954__$1);
return statearr_12023;
})();if(inst_11955)
{var statearr_12024_12069 = state_12020__$1;(statearr_12024_12069[(1)] = (2));
} else
{var statearr_12025_12070 = state_12020__$1;(statearr_12025_12070[(1)] = (3));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (24)))
{var inst_11972 = (state_12020[(10)]);var inst_11964 = inst_11972;var state_12020__$1 = (function (){var statearr_12026 = state_12020;(statearr_12026[(7)] = inst_11964);
return statearr_12026;
})();var statearr_12027_12071 = state_12020__$1;(statearr_12027_12071[(2)] = null);
(statearr_12027_12071[(1)] = (5));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (4)))
{var inst_11954 = (state_12020[(9)]);var inst_11960 = (state_12020[(2)]);var inst_11961 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_11960,cljs.core.constant$keyword$52);var inst_11962 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_11960,cljs.core.constant$keyword$51);var inst_11963 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_11960,cljs.core.constant$keyword$50);var inst_11964 = inst_11954;var state_12020__$1 = (function (){var statearr_12028 = state_12020;(statearr_12028[(11)] = inst_11961);
(statearr_12028[(7)] = inst_11964);
(statearr_12028[(12)] = inst_11963);
(statearr_12028[(13)] = inst_11962);
return statearr_12028;
})();var statearr_12029_12072 = state_12020__$1;(statearr_12029_12072[(2)] = null);
(statearr_12029_12072[(1)] = (5));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (15)))
{var state_12020__$1 = state_12020;var statearr_12030_12073 = state_12020__$1;(statearr_12030_12073[(2)] = null);
(statearr_12030_12073[(1)] = (16));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (21)))
{var inst_11972 = (state_12020[(10)]);var inst_11964 = inst_11972;var state_12020__$1 = (function (){var statearr_12031 = state_12020;(statearr_12031[(7)] = inst_11964);
return statearr_12031;
})();var statearr_12032_12074 = state_12020__$1;(statearr_12032_12074[(2)] = null);
(statearr_12032_12074[(1)] = (5));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (13)))
{var inst_12016 = (state_12020[(2)]);var state_12020__$1 = state_12020;var statearr_12033_12075 = state_12020__$1;(statearr_12033_12075[(2)] = inst_12016);
(statearr_12033_12075[(1)] = (6));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (22)))
{var inst_12014 = (state_12020[(2)]);var state_12020__$1 = state_12020;var statearr_12034_12076 = state_12020__$1;(statearr_12034_12076[(2)] = inst_12014);
(statearr_12034_12076[(1)] = (13));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (6)))
{var inst_12018 = (state_12020[(2)]);var state_12020__$1 = state_12020;return cljs.core.async.impl.ioc_helpers.return_chan(state_12020__$1,inst_12018);
} else
{if((state_val_12021 === (25)))
{var state_12020__$1 = state_12020;var statearr_12035_12077 = state_12020__$1;(statearr_12035_12077[(2)] = null);
(statearr_12035_12077[(1)] = (26));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (17)))
{var inst_11994 = (state_12020[(14)]);var state_12020__$1 = state_12020;var statearr_12036_12078 = state_12020__$1;(statearr_12036_12078[(2)] = inst_11994);
(statearr_12036_12078[(1)] = (19));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (3)))
{var inst_11954 = (state_12020[(9)]);var state_12020__$1 = state_12020;var statearr_12037_12079 = state_12020__$1;(statearr_12037_12079[(2)] = inst_11954);
(statearr_12037_12079[(1)] = (4));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (12)))
{var inst_11975 = (state_12020[(15)]);var inst_11980 = (state_12020[(16)]);var inst_11994 = (state_12020[(14)]);var inst_11994__$1 = (inst_11975.cljs$core$IFn$_invoke$arity$1 ? inst_11975.cljs$core$IFn$_invoke$arity$1(inst_11980) : inst_11975.call(null,inst_11980));var state_12020__$1 = (function (){var statearr_12038 = state_12020;(statearr_12038[(14)] = inst_11994__$1);
return statearr_12038;
})();if(cljs.core.truth_(inst_11994__$1))
{var statearr_12039_12080 = state_12020__$1;(statearr_12039_12080[(1)] = (17));
} else
{var statearr_12040_12081 = state_12020__$1;(statearr_12040_12081[(1)] = (18));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (2)))
{var inst_11954 = (state_12020[(9)]);var inst_11957 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.hash_map,inst_11954);var state_12020__$1 = state_12020;var statearr_12041_12082 = state_12020__$1;(statearr_12041_12082[(2)] = inst_11957);
(statearr_12041_12082[(1)] = (4));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (23)))
{var inst_12005 = (state_12020[(2)]);var state_12020__$1 = state_12020;if(cljs.core.truth_(inst_12005))
{var statearr_12042_12083 = state_12020__$1;(statearr_12042_12083[(1)] = (24));
} else
{var statearr_12043_12084 = state_12020__$1;(statearr_12043_12084[(1)] = (25));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (19)))
{var inst_12002 = (state_12020[(2)]);var state_12020__$1 = state_12020;if(cljs.core.truth_(inst_12002))
{var statearr_12044_12085 = state_12020__$1;(statearr_12044_12085[(1)] = (20));
} else
{var statearr_12045_12086 = state_12020__$1;(statearr_12045_12086[(1)] = (21));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (11)))
{var inst_11979 = (state_12020[(8)]);var inst_11985 = (inst_11979 == null);var state_12020__$1 = state_12020;if(cljs.core.truth_(inst_11985))
{var statearr_12046_12087 = state_12020__$1;(statearr_12046_12087[(1)] = (14));
} else
{var statearr_12047_12088 = state_12020__$1;(statearr_12047_12088[(1)] = (15));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (9)))
{var inst_11972 = (state_12020[(10)]);var inst_11972__$1 = (state_12020[(2)]);var inst_11973 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_11972__$1,cljs.core.constant$keyword$52);var inst_11974 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_11972__$1,cljs.core.constant$keyword$51);var inst_11975 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_11972__$1,cljs.core.constant$keyword$50);var state_12020__$1 = (function (){var statearr_12048 = state_12020;(statearr_12048[(10)] = inst_11972__$1);
(statearr_12048[(15)] = inst_11975);
(statearr_12048[(17)] = inst_11974);
return statearr_12048;
})();return cljs.core.async.impl.ioc_helpers.ioc_alts_BANG_(state_12020__$1,(10),inst_11973);
} else
{if((state_val_12021 === (5)))
{var inst_11964 = (state_12020[(7)]);var inst_11967 = cljs.core.seq_QMARK_(inst_11964);var state_12020__$1 = state_12020;if(inst_11967)
{var statearr_12049_12089 = state_12020__$1;(statearr_12049_12089[(1)] = (7));
} else
{var statearr_12050_12090 = state_12020__$1;(statearr_12050_12090[(1)] = (8));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (14)))
{var inst_11980 = (state_12020[(16)]);var inst_11987 = cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(cs,cljs.core.dissoc,inst_11980);var state_12020__$1 = state_12020;var statearr_12051_12091 = state_12020__$1;(statearr_12051_12091[(2)] = inst_11987);
(statearr_12051_12091[(1)] = (16));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (26)))
{var inst_12010 = (state_12020[(2)]);var state_12020__$1 = state_12020;var statearr_12052_12092 = state_12020__$1;(statearr_12052_12092[(2)] = inst_12010);
(statearr_12052_12092[(1)] = (22));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (16)))
{var inst_11990 = (state_12020[(2)]);var inst_11991 = calc_state();var inst_11964 = inst_11991;var state_12020__$1 = (function (){var statearr_12053 = state_12020;(statearr_12053[(7)] = inst_11964);
(statearr_12053[(18)] = inst_11990);
return statearr_12053;
})();var statearr_12054_12093 = state_12020__$1;(statearr_12054_12093[(2)] = null);
(statearr_12054_12093[(1)] = (5));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (10)))
{var inst_11979 = (state_12020[(8)]);var inst_11980 = (state_12020[(16)]);var inst_11978 = (state_12020[(2)]);var inst_11979__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_11978,(0),null);var inst_11980__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_11978,(1),null);var inst_11981 = (inst_11979__$1 == null);var inst_11982 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(inst_11980__$1,change);var inst_11983 = (inst_11981) || (inst_11982);var state_12020__$1 = (function (){var statearr_12055 = state_12020;(statearr_12055[(8)] = inst_11979__$1);
(statearr_12055[(16)] = inst_11980__$1);
return statearr_12055;
})();if(cljs.core.truth_(inst_11983))
{var statearr_12056_12094 = state_12020__$1;(statearr_12056_12094[(1)] = (11));
} else
{var statearr_12057_12095 = state_12020__$1;(statearr_12057_12095[(1)] = (12));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (18)))
{var inst_11975 = (state_12020[(15)]);var inst_11974 = (state_12020[(17)]);var inst_11980 = (state_12020[(16)]);var inst_11997 = cljs.core.empty_QMARK_(inst_11975);var inst_11998 = (inst_11974.cljs$core$IFn$_invoke$arity$1 ? inst_11974.cljs$core$IFn$_invoke$arity$1(inst_11980) : inst_11974.call(null,inst_11980));var inst_11999 = cljs.core.not(inst_11998);var inst_12000 = (inst_11997) && (inst_11999);var state_12020__$1 = state_12020;var statearr_12058_12096 = state_12020__$1;(statearr_12058_12096[(2)] = inst_12000);
(statearr_12058_12096[(1)] = (19));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12021 === (8)))
{var inst_11964 = (state_12020[(7)]);var state_12020__$1 = state_12020;var statearr_12059_12097 = state_12020__$1;(statearr_12059_12097[(2)] = inst_11964);
(statearr_12059_12097[(1)] = (9));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___12067,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
;return ((function (switch__5709__auto__,c__5724__auto___12067,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_12063 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_12063[(0)] = state_machine__5710__auto__);
(statearr_12063[(1)] = (1));
return statearr_12063;
});
var state_machine__5710__auto____1 = (function (state_12020){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_12020);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e12064){if((e12064 instanceof Object))
{var ex__5713__auto__ = e12064;var statearr_12065_12098 = state_12020;(statearr_12065_12098[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_12020);
return cljs.core.constant$keyword$38;
} else
{throw e12064;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__12099 = state_12020;
state_12020 = G__12099;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_12020){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_12020);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___12067,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
})();var state__5726__auto__ = (function (){var statearr_12066 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_12066[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___12067);
return statearr_12066;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___12067,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
);
return m;
});
/**
* Adds ch as an input to the mix
*/
cljs.core.async.admix = (function admix(mix,ch){return cljs.core.async.admix_STAR_(mix,ch);
});
/**
* Removes ch as an input to the mix
*/
cljs.core.async.unmix = (function unmix(mix,ch){return cljs.core.async.unmix_STAR_(mix,ch);
});
/**
* removes all inputs from the mix
*/
cljs.core.async.unmix_all = (function unmix_all(mix){return cljs.core.async.unmix_all_STAR_(mix);
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
cljs.core.async.toggle = (function toggle(mix,state_map){return cljs.core.async.toggle_STAR_(mix,state_map);
});
/**
* Sets the solo mode of the mix. mode must be one of :mute or :pause
*/
cljs.core.async.solo_mode = (function solo_mode(mix,mode){return cljs.core.async.solo_mode_STAR_(mix,mode);
});
cljs.core.async.Pub = (function (){var obj12101 = {};return obj12101;
})();
cljs.core.async.sub_STAR_ = (function sub_STAR_(p,v,ch,close_QMARK_){if((function (){var and__3548__auto__ = p;if(and__3548__auto__)
{return p.cljs$core$async$Pub$sub_STAR_$arity$4;
} else
{return and__3548__auto__;
}
})())
{return p.cljs$core$async$Pub$sub_STAR_$arity$4(p,v,ch,close_QMARK_);
} else
{var x__4187__auto__ = (((p == null))?null:p);return (function (){var or__3560__auto__ = (cljs.core.async.sub_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.sub_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Pub.sub*",p);
}
}
})().call(null,p,v,ch,close_QMARK_);
}
});
cljs.core.async.unsub_STAR_ = (function unsub_STAR_(p,v,ch){if((function (){var and__3548__auto__ = p;if(and__3548__auto__)
{return p.cljs$core$async$Pub$unsub_STAR_$arity$3;
} else
{return and__3548__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_STAR_$arity$3(p,v,ch);
} else
{var x__4187__auto__ = (((p == null))?null:p);return (function (){var or__3560__auto__ = (cljs.core.async.unsub_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.unsub_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Pub.unsub*",p);
}
}
})().call(null,p,v,ch);
}
});
cljs.core.async.unsub_all_STAR_ = (function() {
var unsub_all_STAR_ = null;
var unsub_all_STAR___1 = (function (p){if((function (){var and__3548__auto__ = p;if(and__3548__auto__)
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$1;
} else
{return and__3548__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$1(p);
} else
{var x__4187__auto__ = (((p == null))?null:p);return (function (){var or__3560__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.unsub_all_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Pub.unsub-all*",p);
}
}
})().call(null,p);
}
});
var unsub_all_STAR___2 = (function (p,v){if((function (){var and__3548__auto__ = p;if(and__3548__auto__)
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2;
} else
{return and__3548__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2(p,v);
} else
{var x__4187__auto__ = (((p == null))?null:p);return (function (){var or__3560__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__4187__auto__)]);if(or__3560__auto__)
{return or__3560__auto__;
} else
{var or__3560__auto____$1 = (cljs.core.async.unsub_all_STAR_["_"]);if(or__3560__auto____$1)
{return or__3560__auto____$1;
} else
{throw cljs.core.missing_protocol("Pub.unsub-all*",p);
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
var pub__2 = (function (ch,topic_fn){return pub.cljs$core$IFn$_invoke$arity$3(ch,topic_fn,cljs.core.constantly(null));
});
var pub__3 = (function (ch,topic_fn,buf_fn){var mults = (cljs.core.atom.cljs$core$IFn$_invoke$arity$1 ? cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY) : cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY));var ensure_mult = ((function (mults){
return (function (topic){var or__3560__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2((cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(mults) : cljs.core.deref.call(null,mults)),topic);if(cljs.core.truth_(or__3560__auto__))
{return or__3560__auto__;
} else
{return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(mults,((function (or__3560__auto__,mults){
return (function (p1__12102_SHARP_){if(cljs.core.truth_((p1__12102_SHARP_.cljs$core$IFn$_invoke$arity$1 ? p1__12102_SHARP_.cljs$core$IFn$_invoke$arity$1(topic) : p1__12102_SHARP_.call(null,topic))))
{return p1__12102_SHARP_;
} else
{return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(p1__12102_SHARP_,topic,cljs.core.async.mult(cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((buf_fn.cljs$core$IFn$_invoke$arity$1 ? buf_fn.cljs$core$IFn$_invoke$arity$1(topic) : buf_fn.call(null,topic)))));
}
});})(or__3560__auto__,mults))
),topic);
}
});})(mults))
;var p = (function (){if(typeof cljs.core.async.t12225 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12225 = (function (ensure_mult,mults,buf_fn,topic_fn,ch,pub,meta12226){
this.ensure_mult = ensure_mult;
this.mults = mults;
this.buf_fn = buf_fn;
this.topic_fn = topic_fn;
this.ch = ch;
this.pub = pub;
this.meta12226 = meta12226;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12225.cljs$lang$type = true;
cljs.core.async.t12225.cljs$lang$ctorStr = "cljs.core.async/t12225";
cljs.core.async.t12225.cljs$lang$ctorPrWriter = ((function (mults,ensure_mult){
return (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t12225");
});})(mults,ensure_mult))
;
cljs.core.async.t12225.prototype.cljs$core$async$Pub$ = true;
cljs.core.async.t12225.prototype.cljs$core$async$Pub$sub_STAR_$arity$4 = ((function (mults,ensure_mult){
return (function (p,topic,ch__$2,close_QMARK_){var self__ = this;
var p__$1 = this;var m = (self__.ensure_mult.cljs$core$IFn$_invoke$arity$1 ? self__.ensure_mult.cljs$core$IFn$_invoke$arity$1(topic) : self__.ensure_mult.call(null,topic));return cljs.core.async.tap.cljs$core$IFn$_invoke$arity$3(m,ch__$2,close_QMARK_);
});})(mults,ensure_mult))
;
cljs.core.async.t12225.prototype.cljs$core$async$Pub$unsub_STAR_$arity$3 = ((function (mults,ensure_mult){
return (function (p,topic,ch__$2){var self__ = this;
var p__$1 = this;var temp__4126__auto__ = cljs.core.get.cljs$core$IFn$_invoke$arity$2((cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(self__.mults) : cljs.core.deref.call(null,self__.mults)),topic);if(cljs.core.truth_(temp__4126__auto__))
{var m = temp__4126__auto__;return cljs.core.async.untap(m,ch__$2);
} else
{return null;
}
});})(mults,ensure_mult))
;
cljs.core.async.t12225.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$1 = ((function (mults,ensure_mult){
return (function (_){var self__ = this;
var ___$1 = this;return (cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2 ? cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2(self__.mults,cljs.core.PersistentArrayMap.EMPTY) : cljs.core.reset_BANG_.call(null,self__.mults,cljs.core.PersistentArrayMap.EMPTY));
});})(mults,ensure_mult))
;
cljs.core.async.t12225.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$2 = ((function (mults,ensure_mult){
return (function (_,topic){var self__ = this;
var ___$1 = this;return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(self__.mults,cljs.core.dissoc,topic);
});})(mults,ensure_mult))
;
cljs.core.async.t12225.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t12225.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (mults,ensure_mult){
return (function (_){var self__ = this;
var ___$1 = this;return self__.ch;
});})(mults,ensure_mult))
;
cljs.core.async.t12225.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (mults,ensure_mult){
return (function (_12227){var self__ = this;
var _12227__$1 = this;return self__.meta12226;
});})(mults,ensure_mult))
;
cljs.core.async.t12225.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (mults,ensure_mult){
return (function (_12227,meta12226__$1){var self__ = this;
var _12227__$1 = this;return (new cljs.core.async.t12225(self__.ensure_mult,self__.mults,self__.buf_fn,self__.topic_fn,self__.ch,self__.pub,meta12226__$1));
});})(mults,ensure_mult))
;
cljs.core.async.__GT_t12225 = ((function (mults,ensure_mult){
return (function __GT_t12225(ensure_mult__$1,mults__$1,buf_fn__$1,topic_fn__$1,ch__$1,pub__$1,meta12226){return (new cljs.core.async.t12225(ensure_mult__$1,mults__$1,buf_fn__$1,topic_fn__$1,ch__$1,pub__$1,meta12226));
});})(mults,ensure_mult))
;
}
return (new cljs.core.async.t12225(ensure_mult,mults,buf_fn,topic_fn,ch,pub,null));
})();var c__5724__auto___12347 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___12347,mults,ensure_mult,p){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___12347,mults,ensure_mult,p){
return (function (state_12299){var state_val_12300 = (state_12299[(1)]);if((state_val_12300 === (7)))
{var inst_12295 = (state_12299[(2)]);var state_12299__$1 = state_12299;var statearr_12301_12348 = state_12299__$1;(statearr_12301_12348[(2)] = inst_12295);
(statearr_12301_12348[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (20)))
{var state_12299__$1 = state_12299;var statearr_12302_12349 = state_12299__$1;(statearr_12302_12349[(2)] = null);
(statearr_12302_12349[(1)] = (21));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (1)))
{var state_12299__$1 = state_12299;var statearr_12303_12350 = state_12299__$1;(statearr_12303_12350[(2)] = null);
(statearr_12303_12350[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (24)))
{var inst_12278 = (state_12299[(7)]);var inst_12287 = cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(mults,cljs.core.dissoc,inst_12278);var state_12299__$1 = state_12299;var statearr_12304_12351 = state_12299__$1;(statearr_12304_12351[(2)] = inst_12287);
(statearr_12304_12351[(1)] = (25));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (4)))
{var inst_12230 = (state_12299[(8)]);var inst_12230__$1 = (state_12299[(2)]);var inst_12231 = (inst_12230__$1 == null);var state_12299__$1 = (function (){var statearr_12305 = state_12299;(statearr_12305[(8)] = inst_12230__$1);
return statearr_12305;
})();if(cljs.core.truth_(inst_12231))
{var statearr_12306_12352 = state_12299__$1;(statearr_12306_12352[(1)] = (5));
} else
{var statearr_12307_12353 = state_12299__$1;(statearr_12307_12353[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (15)))
{var inst_12272 = (state_12299[(2)]);var state_12299__$1 = state_12299;var statearr_12308_12354 = state_12299__$1;(statearr_12308_12354[(2)] = inst_12272);
(statearr_12308_12354[(1)] = (12));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (21)))
{var inst_12292 = (state_12299[(2)]);var state_12299__$1 = (function (){var statearr_12309 = state_12299;(statearr_12309[(9)] = inst_12292);
return statearr_12309;
})();var statearr_12310_12355 = state_12299__$1;(statearr_12310_12355[(2)] = null);
(statearr_12310_12355[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (13)))
{var inst_12254 = (state_12299[(10)]);var inst_12256 = cljs.core.chunked_seq_QMARK_(inst_12254);var state_12299__$1 = state_12299;if(inst_12256)
{var statearr_12311_12356 = state_12299__$1;(statearr_12311_12356[(1)] = (16));
} else
{var statearr_12312_12357 = state_12299__$1;(statearr_12312_12357[(1)] = (17));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (22)))
{var inst_12284 = (state_12299[(2)]);var state_12299__$1 = state_12299;if(cljs.core.truth_(inst_12284))
{var statearr_12313_12358 = state_12299__$1;(statearr_12313_12358[(1)] = (23));
} else
{var statearr_12314_12359 = state_12299__$1;(statearr_12314_12359[(1)] = (24));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (6)))
{var inst_12278 = (state_12299[(7)]);var inst_12230 = (state_12299[(8)]);var inst_12280 = (state_12299[(11)]);var inst_12278__$1 = (topic_fn.cljs$core$IFn$_invoke$arity$1 ? topic_fn.cljs$core$IFn$_invoke$arity$1(inst_12230) : topic_fn.call(null,inst_12230));var inst_12279 = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(mults) : cljs.core.deref.call(null,mults));var inst_12280__$1 = cljs.core.get.cljs$core$IFn$_invoke$arity$2(inst_12279,inst_12278__$1);var state_12299__$1 = (function (){var statearr_12315 = state_12299;(statearr_12315[(7)] = inst_12278__$1);
(statearr_12315[(11)] = inst_12280__$1);
return statearr_12315;
})();if(cljs.core.truth_(inst_12280__$1))
{var statearr_12316_12360 = state_12299__$1;(statearr_12316_12360[(1)] = (19));
} else
{var statearr_12317_12361 = state_12299__$1;(statearr_12317_12361[(1)] = (20));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (25)))
{var inst_12289 = (state_12299[(2)]);var state_12299__$1 = state_12299;var statearr_12318_12362 = state_12299__$1;(statearr_12318_12362[(2)] = inst_12289);
(statearr_12318_12362[(1)] = (21));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (17)))
{var inst_12254 = (state_12299[(10)]);var inst_12263 = cljs.core.first(inst_12254);var inst_12264 = cljs.core.async.muxch_STAR_(inst_12263);var inst_12265 = cljs.core.async.close_BANG_(inst_12264);var inst_12266 = cljs.core.next(inst_12254);var inst_12240 = inst_12266;var inst_12241 = null;var inst_12242 = (0);var inst_12243 = (0);var state_12299__$1 = (function (){var statearr_12319 = state_12299;(statearr_12319[(12)] = inst_12240);
(statearr_12319[(13)] = inst_12242);
(statearr_12319[(14)] = inst_12265);
(statearr_12319[(15)] = inst_12241);
(statearr_12319[(16)] = inst_12243);
return statearr_12319;
})();var statearr_12320_12363 = state_12299__$1;(statearr_12320_12363[(2)] = null);
(statearr_12320_12363[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (3)))
{var inst_12297 = (state_12299[(2)]);var state_12299__$1 = state_12299;return cljs.core.async.impl.ioc_helpers.return_chan(state_12299__$1,inst_12297);
} else
{if((state_val_12300 === (12)))
{var inst_12274 = (state_12299[(2)]);var state_12299__$1 = state_12299;var statearr_12321_12364 = state_12299__$1;(statearr_12321_12364[(2)] = inst_12274);
(statearr_12321_12364[(1)] = (9));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (2)))
{var state_12299__$1 = state_12299;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_12299__$1,(4),ch);
} else
{if((state_val_12300 === (23)))
{var state_12299__$1 = state_12299;var statearr_12322_12365 = state_12299__$1;(statearr_12322_12365[(2)] = null);
(statearr_12322_12365[(1)] = (25));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (19)))
{var inst_12230 = (state_12299[(8)]);var inst_12280 = (state_12299[(11)]);var inst_12282 = cljs.core.async.muxch_STAR_(inst_12280);var state_12299__$1 = state_12299;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_12299__$1,(22),inst_12282,inst_12230);
} else
{if((state_val_12300 === (11)))
{var inst_12240 = (state_12299[(12)]);var inst_12254 = (state_12299[(10)]);var inst_12254__$1 = cljs.core.seq(inst_12240);var state_12299__$1 = (function (){var statearr_12323 = state_12299;(statearr_12323[(10)] = inst_12254__$1);
return statearr_12323;
})();if(inst_12254__$1)
{var statearr_12324_12366 = state_12299__$1;(statearr_12324_12366[(1)] = (13));
} else
{var statearr_12325_12367 = state_12299__$1;(statearr_12325_12367[(1)] = (14));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (9)))
{var inst_12276 = (state_12299[(2)]);var state_12299__$1 = state_12299;var statearr_12326_12368 = state_12299__$1;(statearr_12326_12368[(2)] = inst_12276);
(statearr_12326_12368[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (5)))
{var inst_12237 = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(mults) : cljs.core.deref.call(null,mults));var inst_12238 = cljs.core.vals(inst_12237);var inst_12239 = cljs.core.seq(inst_12238);var inst_12240 = inst_12239;var inst_12241 = null;var inst_12242 = (0);var inst_12243 = (0);var state_12299__$1 = (function (){var statearr_12327 = state_12299;(statearr_12327[(12)] = inst_12240);
(statearr_12327[(13)] = inst_12242);
(statearr_12327[(15)] = inst_12241);
(statearr_12327[(16)] = inst_12243);
return statearr_12327;
})();var statearr_12328_12369 = state_12299__$1;(statearr_12328_12369[(2)] = null);
(statearr_12328_12369[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (14)))
{var state_12299__$1 = state_12299;var statearr_12332_12370 = state_12299__$1;(statearr_12332_12370[(2)] = null);
(statearr_12332_12370[(1)] = (15));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (16)))
{var inst_12254 = (state_12299[(10)]);var inst_12258 = cljs.core.chunk_first(inst_12254);var inst_12259 = cljs.core.chunk_rest(inst_12254);var inst_12260 = cljs.core.count(inst_12258);var inst_12240 = inst_12259;var inst_12241 = inst_12258;var inst_12242 = inst_12260;var inst_12243 = (0);var state_12299__$1 = (function (){var statearr_12333 = state_12299;(statearr_12333[(12)] = inst_12240);
(statearr_12333[(13)] = inst_12242);
(statearr_12333[(15)] = inst_12241);
(statearr_12333[(16)] = inst_12243);
return statearr_12333;
})();var statearr_12334_12371 = state_12299__$1;(statearr_12334_12371[(2)] = null);
(statearr_12334_12371[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (10)))
{var inst_12240 = (state_12299[(12)]);var inst_12242 = (state_12299[(13)]);var inst_12241 = (state_12299[(15)]);var inst_12243 = (state_12299[(16)]);var inst_12248 = cljs.core._nth.cljs$core$IFn$_invoke$arity$2(inst_12241,inst_12243);var inst_12249 = cljs.core.async.muxch_STAR_(inst_12248);var inst_12250 = cljs.core.async.close_BANG_(inst_12249);var inst_12251 = (inst_12243 + (1));var tmp12329 = inst_12240;var tmp12330 = inst_12242;var tmp12331 = inst_12241;var inst_12240__$1 = tmp12329;var inst_12241__$1 = tmp12331;var inst_12242__$1 = tmp12330;var inst_12243__$1 = inst_12251;var state_12299__$1 = (function (){var statearr_12335 = state_12299;(statearr_12335[(12)] = inst_12240__$1);
(statearr_12335[(13)] = inst_12242__$1);
(statearr_12335[(17)] = inst_12250);
(statearr_12335[(15)] = inst_12241__$1);
(statearr_12335[(16)] = inst_12243__$1);
return statearr_12335;
})();var statearr_12336_12372 = state_12299__$1;(statearr_12336_12372[(2)] = null);
(statearr_12336_12372[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (18)))
{var inst_12269 = (state_12299[(2)]);var state_12299__$1 = state_12299;var statearr_12337_12373 = state_12299__$1;(statearr_12337_12373[(2)] = inst_12269);
(statearr_12337_12373[(1)] = (15));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12300 === (8)))
{var inst_12242 = (state_12299[(13)]);var inst_12243 = (state_12299[(16)]);var inst_12245 = (inst_12243 < inst_12242);var inst_12246 = inst_12245;var state_12299__$1 = state_12299;if(cljs.core.truth_(inst_12246))
{var statearr_12338_12374 = state_12299__$1;(statearr_12338_12374[(1)] = (10));
} else
{var statearr_12339_12375 = state_12299__$1;(statearr_12339_12375[(1)] = (11));
}
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___12347,mults,ensure_mult,p))
;return ((function (switch__5709__auto__,c__5724__auto___12347,mults,ensure_mult,p){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_12343 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_12343[(0)] = state_machine__5710__auto__);
(statearr_12343[(1)] = (1));
return statearr_12343;
});
var state_machine__5710__auto____1 = (function (state_12299){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_12299);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e12344){if((e12344 instanceof Object))
{var ex__5713__auto__ = e12344;var statearr_12345_12376 = state_12299;(statearr_12345_12376[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_12299);
return cljs.core.constant$keyword$38;
} else
{throw e12344;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__12377 = state_12299;
state_12299 = G__12377;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_12299){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_12299);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___12347,mults,ensure_mult,p))
})();var state__5726__auto__ = (function (){var statearr_12346 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_12346[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___12347);
return statearr_12346;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___12347,mults,ensure_mult,p))
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
var sub__3 = (function (p,topic,ch){return sub.cljs$core$IFn$_invoke$arity$4(p,topic,ch,true);
});
var sub__4 = (function (p,topic,ch,close_QMARK_){return cljs.core.async.sub_STAR_(p,topic,ch,close_QMARK_);
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
cljs.core.async.unsub = (function unsub(p,topic,ch){return cljs.core.async.unsub_STAR_(p,topic,ch);
});
/**
* Unsubscribes all channels from a pub, or a topic of a pub
*/
cljs.core.async.unsub_all = (function() {
var unsub_all = null;
var unsub_all__1 = (function (p){return cljs.core.async.unsub_all_STAR_.cljs$core$IFn$_invoke$arity$1(p);
});
var unsub_all__2 = (function (p,topic){return cljs.core.async.unsub_all_STAR_.cljs$core$IFn$_invoke$arity$2(p,topic);
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
var map__2 = (function (f,chs){return map.cljs$core$IFn$_invoke$arity$3(f,chs,null);
});
var map__3 = (function (f,chs,buf_or_n){var chs__$1 = cljs.core.vec(chs);var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);var cnt = cljs.core.count(chs__$1);var rets = cljs.core.object_array.cljs$core$IFn$_invoke$arity$1(cnt);var dchan = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));var dctr = (cljs.core.atom.cljs$core$IFn$_invoke$arity$1 ? cljs.core.atom.cljs$core$IFn$_invoke$arity$1(null) : cljs.core.atom.call(null,null));var done = cljs.core.mapv.cljs$core$IFn$_invoke$arity$2(((function (chs__$1,out,cnt,rets,dchan,dctr){
return (function (i){return ((function (chs__$1,out,cnt,rets,dchan,dctr){
return (function (ret){(rets[i] = ret);
if((cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(dctr,cljs.core.dec) === (0)))
{return cljs.core.async.put_BANG_.cljs$core$IFn$_invoke$arity$2(dchan,rets.slice((0)));
} else
{return null;
}
});
;})(chs__$1,out,cnt,rets,dchan,dctr))
});})(chs__$1,out,cnt,rets,dchan,dctr))
,cljs.core.range.cljs$core$IFn$_invoke$arity$1(cnt));var c__5724__auto___12514 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___12514,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___12514,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function (state_12484){var state_val_12485 = (state_12484[(1)]);if((state_val_12485 === (7)))
{var state_12484__$1 = state_12484;var statearr_12486_12515 = state_12484__$1;(statearr_12486_12515[(2)] = null);
(statearr_12486_12515[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (1)))
{var state_12484__$1 = state_12484;var statearr_12487_12516 = state_12484__$1;(statearr_12487_12516[(2)] = null);
(statearr_12487_12516[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (4)))
{var inst_12448 = (state_12484[(7)]);var inst_12450 = (inst_12448 < cnt);var state_12484__$1 = state_12484;if(cljs.core.truth_(inst_12450))
{var statearr_12488_12517 = state_12484__$1;(statearr_12488_12517[(1)] = (6));
} else
{var statearr_12489_12518 = state_12484__$1;(statearr_12489_12518[(1)] = (7));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (15)))
{var inst_12480 = (state_12484[(2)]);var state_12484__$1 = state_12484;var statearr_12490_12519 = state_12484__$1;(statearr_12490_12519[(2)] = inst_12480);
(statearr_12490_12519[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (13)))
{var inst_12473 = cljs.core.async.close_BANG_(out);var state_12484__$1 = state_12484;var statearr_12491_12520 = state_12484__$1;(statearr_12491_12520[(2)] = inst_12473);
(statearr_12491_12520[(1)] = (15));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (6)))
{var state_12484__$1 = state_12484;var statearr_12492_12521 = state_12484__$1;(statearr_12492_12521[(2)] = null);
(statearr_12492_12521[(1)] = (11));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (3)))
{var inst_12482 = (state_12484[(2)]);var state_12484__$1 = state_12484;return cljs.core.async.impl.ioc_helpers.return_chan(state_12484__$1,inst_12482);
} else
{if((state_val_12485 === (12)))
{var inst_12470 = (state_12484[(8)]);var inst_12470__$1 = (state_12484[(2)]);var inst_12471 = cljs.core.some(cljs.core.nil_QMARK_,inst_12470__$1);var state_12484__$1 = (function (){var statearr_12493 = state_12484;(statearr_12493[(8)] = inst_12470__$1);
return statearr_12493;
})();if(cljs.core.truth_(inst_12471))
{var statearr_12494_12522 = state_12484__$1;(statearr_12494_12522[(1)] = (13));
} else
{var statearr_12495_12523 = state_12484__$1;(statearr_12495_12523[(1)] = (14));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (2)))
{var inst_12447 = (cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2 ? cljs.core.reset_BANG_.cljs$core$IFn$_invoke$arity$2(dctr,cnt) : cljs.core.reset_BANG_.call(null,dctr,cnt));var inst_12448 = (0);var state_12484__$1 = (function (){var statearr_12496 = state_12484;(statearr_12496[(9)] = inst_12447);
(statearr_12496[(7)] = inst_12448);
return statearr_12496;
})();var statearr_12497_12524 = state_12484__$1;(statearr_12497_12524[(2)] = null);
(statearr_12497_12524[(1)] = (4));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (11)))
{var inst_12448 = (state_12484[(7)]);var _ = cljs.core.async.impl.ioc_helpers.add_exception_frame(state_12484,(10),Object,null,(9));var inst_12457 = (chs__$1.cljs$core$IFn$_invoke$arity$1 ? chs__$1.cljs$core$IFn$_invoke$arity$1(inst_12448) : chs__$1.call(null,inst_12448));var inst_12458 = (done.cljs$core$IFn$_invoke$arity$1 ? done.cljs$core$IFn$_invoke$arity$1(inst_12448) : done.call(null,inst_12448));var inst_12459 = cljs.core.async.take_BANG_.cljs$core$IFn$_invoke$arity$2(inst_12457,inst_12458);var state_12484__$1 = state_12484;var statearr_12498_12525 = state_12484__$1;(statearr_12498_12525[(2)] = inst_12459);
cljs.core.async.impl.ioc_helpers.process_exception(state_12484__$1);
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (9)))
{var inst_12448 = (state_12484[(7)]);var inst_12461 = (state_12484[(2)]);var inst_12462 = (inst_12448 + (1));var inst_12448__$1 = inst_12462;var state_12484__$1 = (function (){var statearr_12499 = state_12484;(statearr_12499[(10)] = inst_12461);
(statearr_12499[(7)] = inst_12448__$1);
return statearr_12499;
})();var statearr_12500_12526 = state_12484__$1;(statearr_12500_12526[(2)] = null);
(statearr_12500_12526[(1)] = (4));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (5)))
{var inst_12468 = (state_12484[(2)]);var state_12484__$1 = (function (){var statearr_12501 = state_12484;(statearr_12501[(11)] = inst_12468);
return statearr_12501;
})();return cljs.core.async.impl.ioc_helpers.take_BANG_(state_12484__$1,(12),dchan);
} else
{if((state_val_12485 === (14)))
{var inst_12470 = (state_12484[(8)]);var inst_12475 = cljs.core.apply.cljs$core$IFn$_invoke$arity$2(f,inst_12470);var state_12484__$1 = state_12484;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_12484__$1,(16),out,inst_12475);
} else
{if((state_val_12485 === (16)))
{var inst_12477 = (state_12484[(2)]);var state_12484__$1 = (function (){var statearr_12502 = state_12484;(statearr_12502[(12)] = inst_12477);
return statearr_12502;
})();var statearr_12503_12527 = state_12484__$1;(statearr_12503_12527[(2)] = null);
(statearr_12503_12527[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (10)))
{var inst_12452 = (state_12484[(2)]);var inst_12453 = cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(dctr,cljs.core.dec);var state_12484__$1 = (function (){var statearr_12504 = state_12484;(statearr_12504[(13)] = inst_12452);
return statearr_12504;
})();var statearr_12505_12528 = state_12484__$1;(statearr_12505_12528[(2)] = inst_12453);
cljs.core.async.impl.ioc_helpers.process_exception(state_12484__$1);
return cljs.core.constant$keyword$38;
} else
{if((state_val_12485 === (8)))
{var inst_12466 = (state_12484[(2)]);var state_12484__$1 = state_12484;var statearr_12506_12529 = state_12484__$1;(statearr_12506_12529[(2)] = inst_12466);
(statearr_12506_12529[(1)] = (5));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___12514,chs__$1,out,cnt,rets,dchan,dctr,done))
;return ((function (switch__5709__auto__,c__5724__auto___12514,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_12510 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_12510[(0)] = state_machine__5710__auto__);
(statearr_12510[(1)] = (1));
return statearr_12510;
});
var state_machine__5710__auto____1 = (function (state_12484){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_12484);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e12511){if((e12511 instanceof Object))
{var ex__5713__auto__ = e12511;var statearr_12512_12530 = state_12484;(statearr_12512_12530[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_12484);
return cljs.core.constant$keyword$38;
} else
{throw e12511;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__12531 = state_12484;
state_12484 = G__12531;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_12484){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_12484);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___12514,chs__$1,out,cnt,rets,dchan,dctr,done))
})();var state__5726__auto__ = (function (){var statearr_12513 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_12513[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___12514);
return statearr_12513;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___12514,chs__$1,out,cnt,rets,dchan,dctr,done))
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
var merge__1 = (function (chs){return merge.cljs$core$IFn$_invoke$arity$2(chs,null);
});
var merge__2 = (function (chs,buf_or_n){var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);var c__5724__auto___12639 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___12639,out){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___12639,out){
return (function (state_12615){var state_val_12616 = (state_12615[(1)]);if((state_val_12616 === (7)))
{var inst_12594 = (state_12615[(7)]);var inst_12595 = (state_12615[(8)]);var inst_12594__$1 = (state_12615[(2)]);var inst_12595__$1 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_12594__$1,(0),null);var inst_12596 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(inst_12594__$1,(1),null);var inst_12597 = (inst_12595__$1 == null);var state_12615__$1 = (function (){var statearr_12617 = state_12615;(statearr_12617[(7)] = inst_12594__$1);
(statearr_12617[(8)] = inst_12595__$1);
(statearr_12617[(9)] = inst_12596);
return statearr_12617;
})();if(cljs.core.truth_(inst_12597))
{var statearr_12618_12640 = state_12615__$1;(statearr_12618_12640[(1)] = (8));
} else
{var statearr_12619_12641 = state_12615__$1;(statearr_12619_12641[(1)] = (9));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12616 === (1)))
{var inst_12586 = cljs.core.vec(chs);var inst_12587 = inst_12586;var state_12615__$1 = (function (){var statearr_12620 = state_12615;(statearr_12620[(10)] = inst_12587);
return statearr_12620;
})();var statearr_12621_12642 = state_12615__$1;(statearr_12621_12642[(2)] = null);
(statearr_12621_12642[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12616 === (4)))
{var inst_12587 = (state_12615[(10)]);var state_12615__$1 = state_12615;return cljs.core.async.impl.ioc_helpers.ioc_alts_BANG_(state_12615__$1,(7),inst_12587);
} else
{if((state_val_12616 === (6)))
{var inst_12611 = (state_12615[(2)]);var state_12615__$1 = state_12615;var statearr_12622_12643 = state_12615__$1;(statearr_12622_12643[(2)] = inst_12611);
(statearr_12622_12643[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12616 === (3)))
{var inst_12613 = (state_12615[(2)]);var state_12615__$1 = state_12615;return cljs.core.async.impl.ioc_helpers.return_chan(state_12615__$1,inst_12613);
} else
{if((state_val_12616 === (2)))
{var inst_12587 = (state_12615[(10)]);var inst_12589 = cljs.core.count(inst_12587);var inst_12590 = (inst_12589 > (0));var state_12615__$1 = state_12615;if(cljs.core.truth_(inst_12590))
{var statearr_12624_12644 = state_12615__$1;(statearr_12624_12644[(1)] = (4));
} else
{var statearr_12625_12645 = state_12615__$1;(statearr_12625_12645[(1)] = (5));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12616 === (11)))
{var inst_12587 = (state_12615[(10)]);var inst_12604 = (state_12615[(2)]);var tmp12623 = inst_12587;var inst_12587__$1 = tmp12623;var state_12615__$1 = (function (){var statearr_12626 = state_12615;(statearr_12626[(10)] = inst_12587__$1);
(statearr_12626[(11)] = inst_12604);
return statearr_12626;
})();var statearr_12627_12646 = state_12615__$1;(statearr_12627_12646[(2)] = null);
(statearr_12627_12646[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12616 === (9)))
{var inst_12595 = (state_12615[(8)]);var state_12615__$1 = state_12615;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_12615__$1,(11),out,inst_12595);
} else
{if((state_val_12616 === (5)))
{var inst_12609 = cljs.core.async.close_BANG_(out);var state_12615__$1 = state_12615;var statearr_12628_12647 = state_12615__$1;(statearr_12628_12647[(2)] = inst_12609);
(statearr_12628_12647[(1)] = (6));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12616 === (10)))
{var inst_12607 = (state_12615[(2)]);var state_12615__$1 = state_12615;var statearr_12629_12648 = state_12615__$1;(statearr_12629_12648[(2)] = inst_12607);
(statearr_12629_12648[(1)] = (6));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12616 === (8)))
{var inst_12587 = (state_12615[(10)]);var inst_12594 = (state_12615[(7)]);var inst_12595 = (state_12615[(8)]);var inst_12596 = (state_12615[(9)]);var inst_12599 = (function (){var c = inst_12596;var v = inst_12595;var vec__12592 = inst_12594;var cs = inst_12587;return ((function (c,v,vec__12592,cs,inst_12587,inst_12594,inst_12595,inst_12596,state_val_12616,c__5724__auto___12639,out){
return (function (p1__12532_SHARP_){return cljs.core.not_EQ_.cljs$core$IFn$_invoke$arity$2(c,p1__12532_SHARP_);
});
;})(c,v,vec__12592,cs,inst_12587,inst_12594,inst_12595,inst_12596,state_val_12616,c__5724__auto___12639,out))
})();var inst_12600 = cljs.core.filterv(inst_12599,inst_12587);var inst_12587__$1 = inst_12600;var state_12615__$1 = (function (){var statearr_12630 = state_12615;(statearr_12630[(10)] = inst_12587__$1);
return statearr_12630;
})();var statearr_12631_12649 = state_12615__$1;(statearr_12631_12649[(2)] = null);
(statearr_12631_12649[(1)] = (2));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___12639,out))
;return ((function (switch__5709__auto__,c__5724__auto___12639,out){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_12635 = [null,null,null,null,null,null,null,null,null,null,null,null];(statearr_12635[(0)] = state_machine__5710__auto__);
(statearr_12635[(1)] = (1));
return statearr_12635;
});
var state_machine__5710__auto____1 = (function (state_12615){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_12615);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e12636){if((e12636 instanceof Object))
{var ex__5713__auto__ = e12636;var statearr_12637_12650 = state_12615;(statearr_12637_12650[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_12615);
return cljs.core.constant$keyword$38;
} else
{throw e12636;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__12651 = state_12615;
state_12615 = G__12651;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_12615){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_12615);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___12639,out))
})();var state__5726__auto__ = (function (){var statearr_12638 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_12638[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___12639);
return statearr_12638;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___12639,out))
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
cljs.core.async.into = (function into(coll,ch){return cljs.core.async.reduce(cljs.core.conj,coll,ch);
});
/**
* Returns a channel that will return, at most, n items from ch. After n items
* have been returned, or ch has been closed, the return chanel will close.
* 
* The output channel is unbuffered by default, unless buf-or-n is given.
*/
cljs.core.async.take = (function() {
var take = null;
var take__2 = (function (n,ch){return take.cljs$core$IFn$_invoke$arity$3(n,ch,null);
});
var take__3 = (function (n,ch,buf_or_n){var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);var c__5724__auto___12744 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___12744,out){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___12744,out){
return (function (state_12721){var state_val_12722 = (state_12721[(1)]);if((state_val_12722 === (7)))
{var inst_12703 = (state_12721[(7)]);var inst_12703__$1 = (state_12721[(2)]);var inst_12704 = (inst_12703__$1 == null);var inst_12705 = cljs.core.not(inst_12704);var state_12721__$1 = (function (){var statearr_12723 = state_12721;(statearr_12723[(7)] = inst_12703__$1);
return statearr_12723;
})();if(inst_12705)
{var statearr_12724_12745 = state_12721__$1;(statearr_12724_12745[(1)] = (8));
} else
{var statearr_12725_12746 = state_12721__$1;(statearr_12725_12746[(1)] = (9));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12722 === (1)))
{var inst_12698 = (0);var state_12721__$1 = (function (){var statearr_12726 = state_12721;(statearr_12726[(8)] = inst_12698);
return statearr_12726;
})();var statearr_12727_12747 = state_12721__$1;(statearr_12727_12747[(2)] = null);
(statearr_12727_12747[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12722 === (4)))
{var state_12721__$1 = state_12721;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_12721__$1,(7),ch);
} else
{if((state_val_12722 === (6)))
{var inst_12716 = (state_12721[(2)]);var state_12721__$1 = state_12721;var statearr_12728_12748 = state_12721__$1;(statearr_12728_12748[(2)] = inst_12716);
(statearr_12728_12748[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12722 === (3)))
{var inst_12718 = (state_12721[(2)]);var inst_12719 = cljs.core.async.close_BANG_(out);var state_12721__$1 = (function (){var statearr_12729 = state_12721;(statearr_12729[(9)] = inst_12718);
return statearr_12729;
})();return cljs.core.async.impl.ioc_helpers.return_chan(state_12721__$1,inst_12719);
} else
{if((state_val_12722 === (2)))
{var inst_12698 = (state_12721[(8)]);var inst_12700 = (inst_12698 < n);var state_12721__$1 = state_12721;if(cljs.core.truth_(inst_12700))
{var statearr_12730_12749 = state_12721__$1;(statearr_12730_12749[(1)] = (4));
} else
{var statearr_12731_12750 = state_12721__$1;(statearr_12731_12750[(1)] = (5));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12722 === (11)))
{var inst_12698 = (state_12721[(8)]);var inst_12708 = (state_12721[(2)]);var inst_12709 = (inst_12698 + (1));var inst_12698__$1 = inst_12709;var state_12721__$1 = (function (){var statearr_12732 = state_12721;(statearr_12732[(10)] = inst_12708);
(statearr_12732[(8)] = inst_12698__$1);
return statearr_12732;
})();var statearr_12733_12751 = state_12721__$1;(statearr_12733_12751[(2)] = null);
(statearr_12733_12751[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12722 === (9)))
{var state_12721__$1 = state_12721;var statearr_12734_12752 = state_12721__$1;(statearr_12734_12752[(2)] = null);
(statearr_12734_12752[(1)] = (10));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12722 === (5)))
{var state_12721__$1 = state_12721;var statearr_12735_12753 = state_12721__$1;(statearr_12735_12753[(2)] = null);
(statearr_12735_12753[(1)] = (6));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12722 === (10)))
{var inst_12713 = (state_12721[(2)]);var state_12721__$1 = state_12721;var statearr_12736_12754 = state_12721__$1;(statearr_12736_12754[(2)] = inst_12713);
(statearr_12736_12754[(1)] = (6));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12722 === (8)))
{var inst_12703 = (state_12721[(7)]);var state_12721__$1 = state_12721;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_12721__$1,(11),out,inst_12703);
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
});})(c__5724__auto___12744,out))
;return ((function (switch__5709__auto__,c__5724__auto___12744,out){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_12740 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_12740[(0)] = state_machine__5710__auto__);
(statearr_12740[(1)] = (1));
return statearr_12740;
});
var state_machine__5710__auto____1 = (function (state_12721){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_12721);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e12741){if((e12741 instanceof Object))
{var ex__5713__auto__ = e12741;var statearr_12742_12755 = state_12721;(statearr_12742_12755[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_12721);
return cljs.core.constant$keyword$38;
} else
{throw e12741;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__12756 = state_12721;
state_12721 = G__12756;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_12721){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_12721);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___12744,out))
})();var state__5726__auto__ = (function (){var statearr_12743 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_12743[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___12744);
return statearr_12743;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___12744,out))
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
cljs.core.async.map_LT_ = (function map_LT_(f,ch){if(typeof cljs.core.async.t12764 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12764 = (function (ch,f,map_LT_,meta12765){
this.ch = ch;
this.f = f;
this.map_LT_ = map_LT_;
this.meta12765 = meta12765;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12764.cljs$lang$type = true;
cljs.core.async.t12764.cljs$lang$ctorStr = "cljs.core.async/t12764";
cljs.core.async.t12764.cljs$lang$ctorPrWriter = (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t12764");
});
cljs.core.async.t12764.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t12764.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.put_BANG_(self__.ch,val,fn1);
});
cljs.core.async.t12764.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t12764.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;var ret = cljs.core.async.impl.protocols.take_BANG_(self__.ch,(function (){if(typeof cljs.core.async.t12767 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12767 = (function (fn1,_,meta12765,ch,f,map_LT_,meta12768){
this.fn1 = fn1;
this._ = _;
this.meta12765 = meta12765;
this.ch = ch;
this.f = f;
this.map_LT_ = map_LT_;
this.meta12768 = meta12768;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12767.cljs$lang$type = true;
cljs.core.async.t12767.cljs$lang$ctorStr = "cljs.core.async/t12767";
cljs.core.async.t12767.cljs$lang$ctorPrWriter = ((function (___$1){
return (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t12767");
});})(___$1))
;
cljs.core.async.t12767.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t12767.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;return cljs.core.async.impl.protocols.active_QMARK_(self__.fn1);
});})(___$1))
;
cljs.core.async.t12767.prototype.cljs$core$async$impl$protocols$Handler$lock_id$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;return (cljs.core.async.impl.protocols.lock_id.cljs$core$IFn$_invoke$arity$1 ? cljs.core.async.impl.protocols.lock_id.cljs$core$IFn$_invoke$arity$1(self__.fn1) : cljs.core.async.impl.protocols.lock_id.call(null,self__.fn1));
});})(___$1))
;
cljs.core.async.t12767.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;var f1 = cljs.core.async.impl.protocols.commit(self__.fn1);return ((function (f1,___$4,___$1){
return (function (p1__12757_SHARP_){return (f1.cljs$core$IFn$_invoke$arity$1 ? f1.cljs$core$IFn$_invoke$arity$1((((p1__12757_SHARP_ == null))?null:(self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1(p1__12757_SHARP_) : self__.f.call(null,p1__12757_SHARP_)))) : f1.call(null,(((p1__12757_SHARP_ == null))?null:(self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1(p1__12757_SHARP_) : self__.f.call(null,p1__12757_SHARP_)))));
});
;})(f1,___$4,___$1))
});})(___$1))
;
cljs.core.async.t12767.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (___$1){
return (function (_12769){var self__ = this;
var _12769__$1 = this;return self__.meta12768;
});})(___$1))
;
cljs.core.async.t12767.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (___$1){
return (function (_12769,meta12768__$1){var self__ = this;
var _12769__$1 = this;return (new cljs.core.async.t12767(self__.fn1,self__._,self__.meta12765,self__.ch,self__.f,self__.map_LT_,meta12768__$1));
});})(___$1))
;
cljs.core.async.__GT_t12767 = ((function (___$1){
return (function __GT_t12767(fn1__$1,___$2,meta12765__$1,ch__$2,f__$2,map_LT___$2,meta12768){return (new cljs.core.async.t12767(fn1__$1,___$2,meta12765__$1,ch__$2,f__$2,map_LT___$2,meta12768));
});})(___$1))
;
}
return (new cljs.core.async.t12767(fn1,___$1,self__.meta12765,self__.ch,self__.f,self__.map_LT_,null));
})());if(cljs.core.truth_((function (){var and__3548__auto__ = ret;if(cljs.core.truth_(and__3548__auto__))
{return !(((cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(ret) : cljs.core.deref.call(null,ret)) == null));
} else
{return and__3548__auto__;
}
})()))
{return cljs.core.async.impl.channels.box((self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1((cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(ret) : cljs.core.deref.call(null,ret))) : self__.f.call(null,(cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(ret) : cljs.core.deref.call(null,ret)))));
} else
{return ret;
}
});
cljs.core.async.t12764.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t12764.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_(self__.ch);
});
cljs.core.async.t12764.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.closed_QMARK_(self__.ch);
});
cljs.core.async.t12764.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_12766){var self__ = this;
var _12766__$1 = this;return self__.meta12765;
});
cljs.core.async.t12764.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_12766,meta12765__$1){var self__ = this;
var _12766__$1 = this;return (new cljs.core.async.t12764(self__.ch,self__.f,self__.map_LT_,meta12765__$1));
});
cljs.core.async.__GT_t12764 = (function __GT_t12764(ch__$1,f__$1,map_LT___$1,meta12765){return (new cljs.core.async.t12764(ch__$1,f__$1,map_LT___$1,meta12765));
});
}
return (new cljs.core.async.t12764(ch,f,map_LT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.map_GT_ = (function map_GT_(f,ch){if(typeof cljs.core.async.t12773 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12773 = (function (ch,f,map_GT_,meta12774){
this.ch = ch;
this.f = f;
this.map_GT_ = map_GT_;
this.meta12774 = meta12774;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12773.cljs$lang$type = true;
cljs.core.async.t12773.cljs$lang$ctorStr = "cljs.core.async/t12773";
cljs.core.async.t12773.cljs$lang$ctorPrWriter = (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t12773");
});
cljs.core.async.t12773.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t12773.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.put_BANG_(self__.ch,(self__.f.cljs$core$IFn$_invoke$arity$1 ? self__.f.cljs$core$IFn$_invoke$arity$1(val) : self__.f.call(null,val)),fn1);
});
cljs.core.async.t12773.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t12773.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.take_BANG_(self__.ch,fn1);
});
cljs.core.async.t12773.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t12773.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_(self__.ch);
});
cljs.core.async.t12773.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_12775){var self__ = this;
var _12775__$1 = this;return self__.meta12774;
});
cljs.core.async.t12773.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_12775,meta12774__$1){var self__ = this;
var _12775__$1 = this;return (new cljs.core.async.t12773(self__.ch,self__.f,self__.map_GT_,meta12774__$1));
});
cljs.core.async.__GT_t12773 = (function __GT_t12773(ch__$1,f__$1,map_GT___$1,meta12774){return (new cljs.core.async.t12773(ch__$1,f__$1,map_GT___$1,meta12774));
});
}
return (new cljs.core.async.t12773(ch,f,map_GT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.filter_GT_ = (function filter_GT_(p,ch){if(typeof cljs.core.async.t12779 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12779 = (function (ch,p,filter_GT_,meta12780){
this.ch = ch;
this.p = p;
this.filter_GT_ = filter_GT_;
this.meta12780 = meta12780;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12779.cljs$lang$type = true;
cljs.core.async.t12779.cljs$lang$ctorStr = "cljs.core.async/t12779";
cljs.core.async.t12779.cljs$lang$ctorPrWriter = (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async/t12779");
});
cljs.core.async.t12779.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t12779.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;if(cljs.core.truth_((self__.p.cljs$core$IFn$_invoke$arity$1 ? self__.p.cljs$core$IFn$_invoke$arity$1(val) : self__.p.call(null,val))))
{return cljs.core.async.impl.protocols.put_BANG_(self__.ch,val,fn1);
} else
{return cljs.core.async.impl.channels.box(cljs.core.not(cljs.core.async.impl.protocols.closed_QMARK_(self__.ch)));
}
});
cljs.core.async.t12779.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t12779.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.take_BANG_(self__.ch,fn1);
});
cljs.core.async.t12779.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t12779.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_(self__.ch);
});
cljs.core.async.t12779.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.closed_QMARK_(self__.ch);
});
cljs.core.async.t12779.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_12781){var self__ = this;
var _12781__$1 = this;return self__.meta12780;
});
cljs.core.async.t12779.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_12781,meta12780__$1){var self__ = this;
var _12781__$1 = this;return (new cljs.core.async.t12779(self__.ch,self__.p,self__.filter_GT_,meta12780__$1));
});
cljs.core.async.__GT_t12779 = (function __GT_t12779(ch__$1,p__$1,filter_GT___$1,meta12780){return (new cljs.core.async.t12779(ch__$1,p__$1,filter_GT___$1,meta12780));
});
}
return (new cljs.core.async.t12779(ch,p,filter_GT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.remove_GT_ = (function remove_GT_(p,ch){return cljs.core.async.filter_GT_(cljs.core.complement(p),ch);
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.filter_LT_ = (function() {
var filter_LT_ = null;
var filter_LT___2 = (function (p,ch){return filter_LT_.cljs$core$IFn$_invoke$arity$3(p,ch,null);
});
var filter_LT___3 = (function (p,ch,buf_or_n){var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);var c__5724__auto___12864 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___12864,out){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___12864,out){
return (function (state_12843){var state_val_12844 = (state_12843[(1)]);if((state_val_12844 === (7)))
{var inst_12839 = (state_12843[(2)]);var state_12843__$1 = state_12843;var statearr_12845_12865 = state_12843__$1;(statearr_12845_12865[(2)] = inst_12839);
(statearr_12845_12865[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12844 === (1)))
{var state_12843__$1 = state_12843;var statearr_12846_12866 = state_12843__$1;(statearr_12846_12866[(2)] = null);
(statearr_12846_12866[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12844 === (4)))
{var inst_12825 = (state_12843[(7)]);var inst_12825__$1 = (state_12843[(2)]);var inst_12826 = (inst_12825__$1 == null);var state_12843__$1 = (function (){var statearr_12847 = state_12843;(statearr_12847[(7)] = inst_12825__$1);
return statearr_12847;
})();if(cljs.core.truth_(inst_12826))
{var statearr_12848_12867 = state_12843__$1;(statearr_12848_12867[(1)] = (5));
} else
{var statearr_12849_12868 = state_12843__$1;(statearr_12849_12868[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12844 === (6)))
{var inst_12825 = (state_12843[(7)]);var inst_12830 = (p.cljs$core$IFn$_invoke$arity$1 ? p.cljs$core$IFn$_invoke$arity$1(inst_12825) : p.call(null,inst_12825));var state_12843__$1 = state_12843;if(cljs.core.truth_(inst_12830))
{var statearr_12850_12869 = state_12843__$1;(statearr_12850_12869[(1)] = (8));
} else
{var statearr_12851_12870 = state_12843__$1;(statearr_12851_12870[(1)] = (9));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_12844 === (3)))
{var inst_12841 = (state_12843[(2)]);var state_12843__$1 = state_12843;return cljs.core.async.impl.ioc_helpers.return_chan(state_12843__$1,inst_12841);
} else
{if((state_val_12844 === (2)))
{var state_12843__$1 = state_12843;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_12843__$1,(4),ch);
} else
{if((state_val_12844 === (11)))
{var inst_12833 = (state_12843[(2)]);var state_12843__$1 = state_12843;var statearr_12852_12871 = state_12843__$1;(statearr_12852_12871[(2)] = inst_12833);
(statearr_12852_12871[(1)] = (10));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12844 === (9)))
{var state_12843__$1 = state_12843;var statearr_12853_12872 = state_12843__$1;(statearr_12853_12872[(2)] = null);
(statearr_12853_12872[(1)] = (10));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12844 === (5)))
{var inst_12828 = cljs.core.async.close_BANG_(out);var state_12843__$1 = state_12843;var statearr_12854_12873 = state_12843__$1;(statearr_12854_12873[(2)] = inst_12828);
(statearr_12854_12873[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12844 === (10)))
{var inst_12836 = (state_12843[(2)]);var state_12843__$1 = (function (){var statearr_12855 = state_12843;(statearr_12855[(8)] = inst_12836);
return statearr_12855;
})();var statearr_12856_12874 = state_12843__$1;(statearr_12856_12874[(2)] = null);
(statearr_12856_12874[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_12844 === (8)))
{var inst_12825 = (state_12843[(7)]);var state_12843__$1 = state_12843;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_12843__$1,(11),out,inst_12825);
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
});})(c__5724__auto___12864,out))
;return ((function (switch__5709__auto__,c__5724__auto___12864,out){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_12860 = [null,null,null,null,null,null,null,null,null];(statearr_12860[(0)] = state_machine__5710__auto__);
(statearr_12860[(1)] = (1));
return statearr_12860;
});
var state_machine__5710__auto____1 = (function (state_12843){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_12843);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e12861){if((e12861 instanceof Object))
{var ex__5713__auto__ = e12861;var statearr_12862_12875 = state_12843;(statearr_12862_12875[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_12843);
return cljs.core.constant$keyword$38;
} else
{throw e12861;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__12876 = state_12843;
state_12843 = G__12876;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_12843){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_12843);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___12864,out))
})();var state__5726__auto__ = (function (){var statearr_12863 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_12863[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___12864);
return statearr_12863;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___12864,out))
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
var remove_LT___2 = (function (p,ch){return remove_LT_.cljs$core$IFn$_invoke$arity$3(p,ch,null);
});
var remove_LT___3 = (function (p,ch,buf_or_n){return cljs.core.async.filter_LT_.cljs$core$IFn$_invoke$arity$3(cljs.core.complement(p),ch,buf_or_n);
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
cljs.core.async.mapcat_STAR_ = (function mapcat_STAR_(f,in$,out){var c__5724__auto__ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto__){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto__){
return (function (state_13042){var state_val_13043 = (state_13042[(1)]);if((state_val_13043 === (7)))
{var inst_13038 = (state_13042[(2)]);var state_13042__$1 = state_13042;var statearr_13044_13085 = state_13042__$1;(statearr_13044_13085[(2)] = inst_13038);
(statearr_13044_13085[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (20)))
{var inst_13008 = (state_13042[(7)]);var inst_13019 = (state_13042[(2)]);var inst_13020 = cljs.core.next(inst_13008);var inst_12994 = inst_13020;var inst_12995 = null;var inst_12996 = (0);var inst_12997 = (0);var state_13042__$1 = (function (){var statearr_13045 = state_13042;(statearr_13045[(8)] = inst_13019);
(statearr_13045[(9)] = inst_12995);
(statearr_13045[(10)] = inst_12994);
(statearr_13045[(11)] = inst_12996);
(statearr_13045[(12)] = inst_12997);
return statearr_13045;
})();var statearr_13046_13086 = state_13042__$1;(statearr_13046_13086[(2)] = null);
(statearr_13046_13086[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (1)))
{var state_13042__$1 = state_13042;var statearr_13047_13087 = state_13042__$1;(statearr_13047_13087[(2)] = null);
(statearr_13047_13087[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (4)))
{var inst_12983 = (state_13042[(13)]);var inst_12983__$1 = (state_13042[(2)]);var inst_12984 = (inst_12983__$1 == null);var state_13042__$1 = (function (){var statearr_13048 = state_13042;(statearr_13048[(13)] = inst_12983__$1);
return statearr_13048;
})();if(cljs.core.truth_(inst_12984))
{var statearr_13049_13088 = state_13042__$1;(statearr_13049_13088[(1)] = (5));
} else
{var statearr_13050_13089 = state_13042__$1;(statearr_13050_13089[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (15)))
{var state_13042__$1 = state_13042;var statearr_13054_13090 = state_13042__$1;(statearr_13054_13090[(2)] = null);
(statearr_13054_13090[(1)] = (16));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (21)))
{var state_13042__$1 = state_13042;var statearr_13055_13091 = state_13042__$1;(statearr_13055_13091[(2)] = null);
(statearr_13055_13091[(1)] = (23));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (13)))
{var inst_12995 = (state_13042[(9)]);var inst_12994 = (state_13042[(10)]);var inst_12996 = (state_13042[(11)]);var inst_12997 = (state_13042[(12)]);var inst_13004 = (state_13042[(2)]);var inst_13005 = (inst_12997 + (1));var tmp13051 = inst_12995;var tmp13052 = inst_12994;var tmp13053 = inst_12996;var inst_12994__$1 = tmp13052;var inst_12995__$1 = tmp13051;var inst_12996__$1 = tmp13053;var inst_12997__$1 = inst_13005;var state_13042__$1 = (function (){var statearr_13056 = state_13042;(statearr_13056[(9)] = inst_12995__$1);
(statearr_13056[(10)] = inst_12994__$1);
(statearr_13056[(14)] = inst_13004);
(statearr_13056[(11)] = inst_12996__$1);
(statearr_13056[(12)] = inst_12997__$1);
return statearr_13056;
})();var statearr_13057_13092 = state_13042__$1;(statearr_13057_13092[(2)] = null);
(statearr_13057_13092[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (22)))
{var state_13042__$1 = state_13042;var statearr_13058_13093 = state_13042__$1;(statearr_13058_13093[(2)] = null);
(statearr_13058_13093[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (6)))
{var inst_12983 = (state_13042[(13)]);var inst_12992 = (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(inst_12983) : f.call(null,inst_12983));var inst_12993 = cljs.core.seq(inst_12992);var inst_12994 = inst_12993;var inst_12995 = null;var inst_12996 = (0);var inst_12997 = (0);var state_13042__$1 = (function (){var statearr_13059 = state_13042;(statearr_13059[(9)] = inst_12995);
(statearr_13059[(10)] = inst_12994);
(statearr_13059[(11)] = inst_12996);
(statearr_13059[(12)] = inst_12997);
return statearr_13059;
})();var statearr_13060_13094 = state_13042__$1;(statearr_13060_13094[(2)] = null);
(statearr_13060_13094[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (17)))
{var inst_13008 = (state_13042[(7)]);var inst_13012 = cljs.core.chunk_first(inst_13008);var inst_13013 = cljs.core.chunk_rest(inst_13008);var inst_13014 = cljs.core.count(inst_13012);var inst_12994 = inst_13013;var inst_12995 = inst_13012;var inst_12996 = inst_13014;var inst_12997 = (0);var state_13042__$1 = (function (){var statearr_13061 = state_13042;(statearr_13061[(9)] = inst_12995);
(statearr_13061[(10)] = inst_12994);
(statearr_13061[(11)] = inst_12996);
(statearr_13061[(12)] = inst_12997);
return statearr_13061;
})();var statearr_13062_13095 = state_13042__$1;(statearr_13062_13095[(2)] = null);
(statearr_13062_13095[(1)] = (8));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (3)))
{var inst_13040 = (state_13042[(2)]);var state_13042__$1 = state_13042;return cljs.core.async.impl.ioc_helpers.return_chan(state_13042__$1,inst_13040);
} else
{if((state_val_13043 === (12)))
{var inst_13028 = (state_13042[(2)]);var state_13042__$1 = state_13042;var statearr_13063_13096 = state_13042__$1;(statearr_13063_13096[(2)] = inst_13028);
(statearr_13063_13096[(1)] = (9));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (2)))
{var state_13042__$1 = state_13042;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_13042__$1,(4),in$);
} else
{if((state_val_13043 === (23)))
{var inst_13036 = (state_13042[(2)]);var state_13042__$1 = state_13042;var statearr_13064_13097 = state_13042__$1;(statearr_13064_13097[(2)] = inst_13036);
(statearr_13064_13097[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (19)))
{var inst_13023 = (state_13042[(2)]);var state_13042__$1 = state_13042;var statearr_13065_13098 = state_13042__$1;(statearr_13065_13098[(2)] = inst_13023);
(statearr_13065_13098[(1)] = (16));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (11)))
{var inst_13008 = (state_13042[(7)]);var inst_12994 = (state_13042[(10)]);var inst_13008__$1 = cljs.core.seq(inst_12994);var state_13042__$1 = (function (){var statearr_13066 = state_13042;(statearr_13066[(7)] = inst_13008__$1);
return statearr_13066;
})();if(inst_13008__$1)
{var statearr_13067_13099 = state_13042__$1;(statearr_13067_13099[(1)] = (14));
} else
{var statearr_13068_13100 = state_13042__$1;(statearr_13068_13100[(1)] = (15));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (9)))
{var inst_13030 = (state_13042[(2)]);var inst_13031 = cljs.core.async.impl.protocols.closed_QMARK_(out);var state_13042__$1 = (function (){var statearr_13069 = state_13042;(statearr_13069[(15)] = inst_13030);
return statearr_13069;
})();if(cljs.core.truth_(inst_13031))
{var statearr_13070_13101 = state_13042__$1;(statearr_13070_13101[(1)] = (21));
} else
{var statearr_13071_13102 = state_13042__$1;(statearr_13071_13102[(1)] = (22));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (5)))
{var inst_12986 = cljs.core.async.close_BANG_(out);var state_13042__$1 = state_13042;var statearr_13072_13103 = state_13042__$1;(statearr_13072_13103[(2)] = inst_12986);
(statearr_13072_13103[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (14)))
{var inst_13008 = (state_13042[(7)]);var inst_13010 = cljs.core.chunked_seq_QMARK_(inst_13008);var state_13042__$1 = state_13042;if(inst_13010)
{var statearr_13073_13104 = state_13042__$1;(statearr_13073_13104[(1)] = (17));
} else
{var statearr_13074_13105 = state_13042__$1;(statearr_13074_13105[(1)] = (18));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (16)))
{var inst_13026 = (state_13042[(2)]);var state_13042__$1 = state_13042;var statearr_13075_13106 = state_13042__$1;(statearr_13075_13106[(2)] = inst_13026);
(statearr_13075_13106[(1)] = (12));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13043 === (10)))
{var inst_12995 = (state_13042[(9)]);var inst_12997 = (state_13042[(12)]);var inst_13002 = cljs.core._nth.cljs$core$IFn$_invoke$arity$2(inst_12995,inst_12997);var state_13042__$1 = state_13042;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_13042__$1,(13),out,inst_13002);
} else
{if((state_val_13043 === (18)))
{var inst_13008 = (state_13042[(7)]);var inst_13017 = cljs.core.first(inst_13008);var state_13042__$1 = state_13042;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_13042__$1,(20),out,inst_13017);
} else
{if((state_val_13043 === (8)))
{var inst_12996 = (state_13042[(11)]);var inst_12997 = (state_13042[(12)]);var inst_12999 = (inst_12997 < inst_12996);var inst_13000 = inst_12999;var state_13042__$1 = state_13042;if(cljs.core.truth_(inst_13000))
{var statearr_13076_13107 = state_13042__$1;(statearr_13076_13107[(1)] = (10));
} else
{var statearr_13077_13108 = state_13042__$1;(statearr_13077_13108[(1)] = (11));
}
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto__))
;return ((function (switch__5709__auto__,c__5724__auto__){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_13081 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13081[(0)] = state_machine__5710__auto__);
(statearr_13081[(1)] = (1));
return statearr_13081;
});
var state_machine__5710__auto____1 = (function (state_13042){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_13042);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e13082){if((e13082 instanceof Object))
{var ex__5713__auto__ = e13082;var statearr_13083_13109 = state_13042;(statearr_13083_13109[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_13042);
return cljs.core.constant$keyword$38;
} else
{throw e13082;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__13110 = state_13042;
state_13042 = G__13110;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_13042){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_13042);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto__))
})();var state__5726__auto__ = (function (){var statearr_13084 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_13084[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto__);
return statearr_13084;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto__))
);
return c__5724__auto__;
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.mapcat_LT_ = (function() {
var mapcat_LT_ = null;
var mapcat_LT___2 = (function (f,in$){return mapcat_LT_.cljs$core$IFn$_invoke$arity$3(f,in$,null);
});
var mapcat_LT___3 = (function (f,in$,buf_or_n){var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);cljs.core.async.mapcat_STAR_(f,in$,out);
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
var mapcat_GT___2 = (function (f,out){return mapcat_GT_.cljs$core$IFn$_invoke$arity$3(f,out,null);
});
var mapcat_GT___3 = (function (f,out,buf_or_n){var in$ = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);cljs.core.async.mapcat_STAR_(f,in$,out);
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
var unique__1 = (function (ch){return unique.cljs$core$IFn$_invoke$arity$2(ch,null);
});
var unique__2 = (function (ch,buf_or_n){var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);var c__5724__auto___13207 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___13207,out){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___13207,out){
return (function (state_13182){var state_val_13183 = (state_13182[(1)]);if((state_val_13183 === (7)))
{var inst_13177 = (state_13182[(2)]);var state_13182__$1 = state_13182;var statearr_13184_13208 = state_13182__$1;(statearr_13184_13208[(2)] = inst_13177);
(statearr_13184_13208[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13183 === (1)))
{var inst_13159 = null;var state_13182__$1 = (function (){var statearr_13185 = state_13182;(statearr_13185[(7)] = inst_13159);
return statearr_13185;
})();var statearr_13186_13209 = state_13182__$1;(statearr_13186_13209[(2)] = null);
(statearr_13186_13209[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13183 === (4)))
{var inst_13162 = (state_13182[(8)]);var inst_13162__$1 = (state_13182[(2)]);var inst_13163 = (inst_13162__$1 == null);var inst_13164 = cljs.core.not(inst_13163);var state_13182__$1 = (function (){var statearr_13187 = state_13182;(statearr_13187[(8)] = inst_13162__$1);
return statearr_13187;
})();if(inst_13164)
{var statearr_13188_13210 = state_13182__$1;(statearr_13188_13210[(1)] = (5));
} else
{var statearr_13189_13211 = state_13182__$1;(statearr_13189_13211[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13183 === (6)))
{var state_13182__$1 = state_13182;var statearr_13190_13212 = state_13182__$1;(statearr_13190_13212[(2)] = null);
(statearr_13190_13212[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13183 === (3)))
{var inst_13179 = (state_13182[(2)]);var inst_13180 = cljs.core.async.close_BANG_(out);var state_13182__$1 = (function (){var statearr_13191 = state_13182;(statearr_13191[(9)] = inst_13179);
return statearr_13191;
})();return cljs.core.async.impl.ioc_helpers.return_chan(state_13182__$1,inst_13180);
} else
{if((state_val_13183 === (2)))
{var state_13182__$1 = state_13182;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_13182__$1,(4),ch);
} else
{if((state_val_13183 === (11)))
{var inst_13162 = (state_13182[(8)]);var inst_13171 = (state_13182[(2)]);var inst_13159 = inst_13162;var state_13182__$1 = (function (){var statearr_13192 = state_13182;(statearr_13192[(7)] = inst_13159);
(statearr_13192[(10)] = inst_13171);
return statearr_13192;
})();var statearr_13193_13213 = state_13182__$1;(statearr_13193_13213[(2)] = null);
(statearr_13193_13213[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13183 === (9)))
{var inst_13162 = (state_13182[(8)]);var state_13182__$1 = state_13182;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_13182__$1,(11),out,inst_13162);
} else
{if((state_val_13183 === (5)))
{var inst_13159 = (state_13182[(7)]);var inst_13162 = (state_13182[(8)]);var inst_13166 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(inst_13162,inst_13159);var state_13182__$1 = state_13182;if(inst_13166)
{var statearr_13195_13214 = state_13182__$1;(statearr_13195_13214[(1)] = (8));
} else
{var statearr_13196_13215 = state_13182__$1;(statearr_13196_13215[(1)] = (9));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13183 === (10)))
{var inst_13174 = (state_13182[(2)]);var state_13182__$1 = state_13182;var statearr_13197_13216 = state_13182__$1;(statearr_13197_13216[(2)] = inst_13174);
(statearr_13197_13216[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13183 === (8)))
{var inst_13159 = (state_13182[(7)]);var tmp13194 = inst_13159;var inst_13159__$1 = tmp13194;var state_13182__$1 = (function (){var statearr_13198 = state_13182;(statearr_13198[(7)] = inst_13159__$1);
return statearr_13198;
})();var statearr_13199_13217 = state_13182__$1;(statearr_13199_13217[(2)] = null);
(statearr_13199_13217[(1)] = (2));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___13207,out))
;return ((function (switch__5709__auto__,c__5724__auto___13207,out){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_13203 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_13203[(0)] = state_machine__5710__auto__);
(statearr_13203[(1)] = (1));
return statearr_13203;
});
var state_machine__5710__auto____1 = (function (state_13182){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_13182);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e13204){if((e13204 instanceof Object))
{var ex__5713__auto__ = e13204;var statearr_13205_13218 = state_13182;(statearr_13205_13218[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_13182);
return cljs.core.constant$keyword$38;
} else
{throw e13204;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__13219 = state_13182;
state_13182 = G__13219;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_13182){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_13182);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___13207,out))
})();var state__5726__auto__ = (function (){var statearr_13206 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_13206[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___13207);
return statearr_13206;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___13207,out))
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
var partition__2 = (function (n,ch){return partition.cljs$core$IFn$_invoke$arity$3(n,ch,null);
});
var partition__3 = (function (n,ch,buf_or_n){var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);var c__5724__auto___13354 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___13354,out){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___13354,out){
return (function (state_13324){var state_val_13325 = (state_13324[(1)]);if((state_val_13325 === (7)))
{var inst_13320 = (state_13324[(2)]);var state_13324__$1 = state_13324;var statearr_13326_13355 = state_13324__$1;(statearr_13326_13355[(2)] = inst_13320);
(statearr_13326_13355[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (1)))
{var inst_13287 = (new Array(n));var inst_13288 = inst_13287;var inst_13289 = (0);var state_13324__$1 = (function (){var statearr_13327 = state_13324;(statearr_13327[(7)] = inst_13289);
(statearr_13327[(8)] = inst_13288);
return statearr_13327;
})();var statearr_13328_13356 = state_13324__$1;(statearr_13328_13356[(2)] = null);
(statearr_13328_13356[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (4)))
{var inst_13292 = (state_13324[(9)]);var inst_13292__$1 = (state_13324[(2)]);var inst_13293 = (inst_13292__$1 == null);var inst_13294 = cljs.core.not(inst_13293);var state_13324__$1 = (function (){var statearr_13329 = state_13324;(statearr_13329[(9)] = inst_13292__$1);
return statearr_13329;
})();if(inst_13294)
{var statearr_13330_13357 = state_13324__$1;(statearr_13330_13357[(1)] = (5));
} else
{var statearr_13331_13358 = state_13324__$1;(statearr_13331_13358[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (15)))
{var inst_13314 = (state_13324[(2)]);var state_13324__$1 = state_13324;var statearr_13332_13359 = state_13324__$1;(statearr_13332_13359[(2)] = inst_13314);
(statearr_13332_13359[(1)] = (14));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (13)))
{var state_13324__$1 = state_13324;var statearr_13333_13360 = state_13324__$1;(statearr_13333_13360[(2)] = null);
(statearr_13333_13360[(1)] = (14));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (6)))
{var inst_13289 = (state_13324[(7)]);var inst_13310 = (inst_13289 > (0));var state_13324__$1 = state_13324;if(cljs.core.truth_(inst_13310))
{var statearr_13334_13361 = state_13324__$1;(statearr_13334_13361[(1)] = (12));
} else
{var statearr_13335_13362 = state_13324__$1;(statearr_13335_13362[(1)] = (13));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (3)))
{var inst_13322 = (state_13324[(2)]);var state_13324__$1 = state_13324;return cljs.core.async.impl.ioc_helpers.return_chan(state_13324__$1,inst_13322);
} else
{if((state_val_13325 === (12)))
{var inst_13288 = (state_13324[(8)]);var inst_13312 = cljs.core.vec(inst_13288);var state_13324__$1 = state_13324;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_13324__$1,(15),out,inst_13312);
} else
{if((state_val_13325 === (2)))
{var state_13324__$1 = state_13324;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_13324__$1,(4),ch);
} else
{if((state_val_13325 === (11)))
{var inst_13304 = (state_13324[(2)]);var inst_13305 = (new Array(n));var inst_13288 = inst_13305;var inst_13289 = (0);var state_13324__$1 = (function (){var statearr_13336 = state_13324;(statearr_13336[(7)] = inst_13289);
(statearr_13336[(8)] = inst_13288);
(statearr_13336[(10)] = inst_13304);
return statearr_13336;
})();var statearr_13337_13363 = state_13324__$1;(statearr_13337_13363[(2)] = null);
(statearr_13337_13363[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (9)))
{var inst_13288 = (state_13324[(8)]);var inst_13302 = cljs.core.vec(inst_13288);var state_13324__$1 = state_13324;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_13324__$1,(11),out,inst_13302);
} else
{if((state_val_13325 === (5)))
{var inst_13289 = (state_13324[(7)]);var inst_13288 = (state_13324[(8)]);var inst_13297 = (state_13324[(11)]);var inst_13292 = (state_13324[(9)]);var inst_13296 = (inst_13288[inst_13289] = inst_13292);var inst_13297__$1 = (inst_13289 + (1));var inst_13298 = (inst_13297__$1 < n);var state_13324__$1 = (function (){var statearr_13338 = state_13324;(statearr_13338[(12)] = inst_13296);
(statearr_13338[(11)] = inst_13297__$1);
return statearr_13338;
})();if(cljs.core.truth_(inst_13298))
{var statearr_13339_13364 = state_13324__$1;(statearr_13339_13364[(1)] = (8));
} else
{var statearr_13340_13365 = state_13324__$1;(statearr_13340_13365[(1)] = (9));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (14)))
{var inst_13317 = (state_13324[(2)]);var inst_13318 = cljs.core.async.close_BANG_(out);var state_13324__$1 = (function (){var statearr_13342 = state_13324;(statearr_13342[(13)] = inst_13317);
return statearr_13342;
})();var statearr_13343_13366 = state_13324__$1;(statearr_13343_13366[(2)] = inst_13318);
(statearr_13343_13366[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (10)))
{var inst_13308 = (state_13324[(2)]);var state_13324__$1 = state_13324;var statearr_13344_13367 = state_13324__$1;(statearr_13344_13367[(2)] = inst_13308);
(statearr_13344_13367[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13325 === (8)))
{var inst_13288 = (state_13324[(8)]);var inst_13297 = (state_13324[(11)]);var tmp13341 = inst_13288;var inst_13288__$1 = tmp13341;var inst_13289 = inst_13297;var state_13324__$1 = (function (){var statearr_13345 = state_13324;(statearr_13345[(7)] = inst_13289);
(statearr_13345[(8)] = inst_13288__$1);
return statearr_13345;
})();var statearr_13346_13368 = state_13324__$1;(statearr_13346_13368[(2)] = null);
(statearr_13346_13368[(1)] = (2));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___13354,out))
;return ((function (switch__5709__auto__,c__5724__auto___13354,out){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_13350 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13350[(0)] = state_machine__5710__auto__);
(statearr_13350[(1)] = (1));
return statearr_13350;
});
var state_machine__5710__auto____1 = (function (state_13324){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_13324);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e13351){if((e13351 instanceof Object))
{var ex__5713__auto__ = e13351;var statearr_13352_13369 = state_13324;(statearr_13352_13369[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_13324);
return cljs.core.constant$keyword$38;
} else
{throw e13351;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__13370 = state_13324;
state_13324 = G__13370;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_13324){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_13324);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___13354,out))
})();var state__5726__auto__ = (function (){var statearr_13353 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_13353[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___13354);
return statearr_13353;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___13354,out))
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
var partition_by__2 = (function (f,ch){return partition_by.cljs$core$IFn$_invoke$arity$3(f,ch,null);
});
var partition_by__3 = (function (f,ch,buf_or_n){var out = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1(buf_or_n);var c__5724__auto___13513 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___13513,out){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___13513,out){
return (function (state_13483){var state_val_13484 = (state_13483[(1)]);if((state_val_13484 === (7)))
{var inst_13479 = (state_13483[(2)]);var state_13483__$1 = state_13483;var statearr_13485_13514 = state_13483__$1;(statearr_13485_13514[(2)] = inst_13479);
(statearr_13485_13514[(1)] = (3));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (1)))
{var inst_13442 = [];var inst_13443 = inst_13442;var inst_13444 = cljs.core.constant$keyword$53;var state_13483__$1 = (function (){var statearr_13486 = state_13483;(statearr_13486[(7)] = inst_13444);
(statearr_13486[(8)] = inst_13443);
return statearr_13486;
})();var statearr_13487_13515 = state_13483__$1;(statearr_13487_13515[(2)] = null);
(statearr_13487_13515[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (4)))
{var inst_13447 = (state_13483[(9)]);var inst_13447__$1 = (state_13483[(2)]);var inst_13448 = (inst_13447__$1 == null);var inst_13449 = cljs.core.not(inst_13448);var state_13483__$1 = (function (){var statearr_13488 = state_13483;(statearr_13488[(9)] = inst_13447__$1);
return statearr_13488;
})();if(inst_13449)
{var statearr_13489_13516 = state_13483__$1;(statearr_13489_13516[(1)] = (5));
} else
{var statearr_13490_13517 = state_13483__$1;(statearr_13490_13517[(1)] = (6));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (15)))
{var inst_13473 = (state_13483[(2)]);var state_13483__$1 = state_13483;var statearr_13491_13518 = state_13483__$1;(statearr_13491_13518[(2)] = inst_13473);
(statearr_13491_13518[(1)] = (14));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (13)))
{var state_13483__$1 = state_13483;var statearr_13492_13519 = state_13483__$1;(statearr_13492_13519[(2)] = null);
(statearr_13492_13519[(1)] = (14));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (6)))
{var inst_13443 = (state_13483[(8)]);var inst_13468 = inst_13443.length;var inst_13469 = (inst_13468 > (0));var state_13483__$1 = state_13483;if(cljs.core.truth_(inst_13469))
{var statearr_13493_13520 = state_13483__$1;(statearr_13493_13520[(1)] = (12));
} else
{var statearr_13494_13521 = state_13483__$1;(statearr_13494_13521[(1)] = (13));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (3)))
{var inst_13481 = (state_13483[(2)]);var state_13483__$1 = state_13483;return cljs.core.async.impl.ioc_helpers.return_chan(state_13483__$1,inst_13481);
} else
{if((state_val_13484 === (12)))
{var inst_13443 = (state_13483[(8)]);var inst_13471 = cljs.core.vec(inst_13443);var state_13483__$1 = state_13483;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_13483__$1,(15),out,inst_13471);
} else
{if((state_val_13484 === (2)))
{var state_13483__$1 = state_13483;return cljs.core.async.impl.ioc_helpers.take_BANG_(state_13483__$1,(4),ch);
} else
{if((state_val_13484 === (11)))
{var inst_13451 = (state_13483[(10)]);var inst_13447 = (state_13483[(9)]);var inst_13461 = (state_13483[(2)]);var inst_13462 = [];var inst_13463 = inst_13462.push(inst_13447);var inst_13443 = inst_13462;var inst_13444 = inst_13451;var state_13483__$1 = (function (){var statearr_13495 = state_13483;(statearr_13495[(11)] = inst_13463);
(statearr_13495[(7)] = inst_13444);
(statearr_13495[(8)] = inst_13443);
(statearr_13495[(12)] = inst_13461);
return statearr_13495;
})();var statearr_13496_13522 = state_13483__$1;(statearr_13496_13522[(2)] = null);
(statearr_13496_13522[(1)] = (2));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (9)))
{var inst_13443 = (state_13483[(8)]);var inst_13459 = cljs.core.vec(inst_13443);var state_13483__$1 = state_13483;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_13483__$1,(11),out,inst_13459);
} else
{if((state_val_13484 === (5)))
{var inst_13451 = (state_13483[(10)]);var inst_13444 = (state_13483[(7)]);var inst_13447 = (state_13483[(9)]);var inst_13451__$1 = (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(inst_13447) : f.call(null,inst_13447));var inst_13452 = cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(inst_13451__$1,inst_13444);var inst_13453 = cljs.core.keyword_identical_QMARK_(inst_13444,cljs.core.constant$keyword$53);var inst_13454 = (inst_13452) || (inst_13453);var state_13483__$1 = (function (){var statearr_13497 = state_13483;(statearr_13497[(10)] = inst_13451__$1);
return statearr_13497;
})();if(cljs.core.truth_(inst_13454))
{var statearr_13498_13523 = state_13483__$1;(statearr_13498_13523[(1)] = (8));
} else
{var statearr_13499_13524 = state_13483__$1;(statearr_13499_13524[(1)] = (9));
}
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (14)))
{var inst_13476 = (state_13483[(2)]);var inst_13477 = cljs.core.async.close_BANG_(out);var state_13483__$1 = (function (){var statearr_13501 = state_13483;(statearr_13501[(13)] = inst_13476);
return statearr_13501;
})();var statearr_13502_13525 = state_13483__$1;(statearr_13502_13525[(2)] = inst_13477);
(statearr_13502_13525[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (10)))
{var inst_13466 = (state_13483[(2)]);var state_13483__$1 = state_13483;var statearr_13503_13526 = state_13483__$1;(statearr_13503_13526[(2)] = inst_13466);
(statearr_13503_13526[(1)] = (7));
return cljs.core.constant$keyword$38;
} else
{if((state_val_13484 === (8)))
{var inst_13451 = (state_13483[(10)]);var inst_13443 = (state_13483[(8)]);var inst_13447 = (state_13483[(9)]);var inst_13456 = inst_13443.push(inst_13447);var tmp13500 = inst_13443;var inst_13443__$1 = tmp13500;var inst_13444 = inst_13451;var state_13483__$1 = (function (){var statearr_13504 = state_13483;(statearr_13504[(14)] = inst_13456);
(statearr_13504[(7)] = inst_13444);
(statearr_13504[(8)] = inst_13443__$1);
return statearr_13504;
})();var statearr_13505_13527 = state_13483__$1;(statearr_13505_13527[(2)] = null);
(statearr_13505_13527[(1)] = (2));
return cljs.core.constant$keyword$38;
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
});})(c__5724__auto___13513,out))
;return ((function (switch__5709__auto__,c__5724__auto___13513,out){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_13509 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13509[(0)] = state_machine__5710__auto__);
(statearr_13509[(1)] = (1));
return statearr_13509;
});
var state_machine__5710__auto____1 = (function (state_13483){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_13483);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e13510){if((e13510 instanceof Object))
{var ex__5713__auto__ = e13510;var statearr_13511_13528 = state_13483;(statearr_13511_13528[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_13483);
return cljs.core.constant$keyword$38;
} else
{throw e13510;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__13529 = state_13483;
state_13483 = G__13529;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_13483){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_13483);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___13513,out))
})();var state__5726__auto__ = (function (){var statearr_13512 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_13512[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___13513);
return statearr_13512;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___13513,out))
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
