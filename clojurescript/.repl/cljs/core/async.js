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
cljs.core.async.fn_handler = (function fn_handler(f){if(typeof cljs.core.async.t12992 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t12992 = (function (f,fn_handler,meta12993){
this.f = f;
this.fn_handler = fn_handler;
this.meta12993 = meta12993;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t12992.cljs$lang$type = true;
cljs.core.async.t12992.cljs$lang$ctorStr = "cljs.core.async/t12992";
cljs.core.async.t12992.cljs$lang$ctorPrWriter = (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t12992");
});
cljs.core.async.t12992.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t12992.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return true;
});
cljs.core.async.t12992.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return self__.f;
});
cljs.core.async.t12992.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_12994){var self__ = this;
var _12994__$1 = this;return self__.meta12993;
});
cljs.core.async.t12992.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_12994,meta12993__$1){var self__ = this;
var _12994__$1 = this;return (new cljs.core.async.t12992(self__.f,self__.fn_handler,meta12993__$1));
});
cljs.core.async.__GT_t12992 = (function __GT_t12992(f__$1,fn_handler__$1,meta12993){return (new cljs.core.async.t12992(f__$1,fn_handler__$1,meta12993));
});
}
return (new cljs.core.async.t12992(f,fn_handler,null));
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
cljs.core.async.unblocking_buffer_QMARK_ = (function unblocking_buffer_QMARK_(buff){var G__12996 = buff;if(G__12996)
{var bit__7958__auto__ = null;if(cljs.core.truth_((function (){var or__7308__auto__ = bit__7958__auto__;if(cljs.core.truth_(or__7308__auto__))
{return or__7308__auto__;
} else
{return G__12996.cljs$core$async$impl$protocols$UnblockingBuffer$;
}
})()))
{return true;
} else
{if((!G__12996.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,cljs.core.async.impl.protocols.UnblockingBuffer,G__12996);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,cljs.core.async.impl.protocols.UnblockingBuffer,G__12996);
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
{var val_12997 = cljs.core.deref.call(null,ret);if(cljs.core.truth_(on_caller_QMARK_))
{fn1.call(null,val_12997);
} else
{cljs.core.async.impl.dispatch.run.call(null,((function (val_12997,ret){
return (function (){return fn1.call(null,val_12997);
});})(val_12997,ret))
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
cljs.core.async.random_array = (function random_array(n){var a = (new Array(n));var n__8164__auto___12998 = n;var x_12999 = (0);while(true){
if((x_12999 < n__8164__auto___12998))
{(a[x_12999] = (0));
{
var G__13000 = (x_12999 + (1));
x_12999 = G__13000;
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
var G__13001 = (i + (1));
i = G__13001;
continue;
}
}
break;
}
});
cljs.core.async.alt_flag = (function alt_flag(){var flag = cljs.core.atom.call(null,true);if(typeof cljs.core.async.t13005 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t13005 = (function (flag,alt_flag,meta13006){
this.flag = flag;
this.alt_flag = alt_flag;
this.meta13006 = meta13006;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t13005.cljs$lang$type = true;
cljs.core.async.t13005.cljs$lang$ctorStr = "cljs.core.async/t13005";
cljs.core.async.t13005.cljs$lang$ctorPrWriter = ((function (flag){
return (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t13005");
});})(flag))
;
cljs.core.async.t13005.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t13005.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = ((function (flag){
return (function (_){var self__ = this;
var ___$1 = this;return cljs.core.deref.call(null,self__.flag);
});})(flag))
;
cljs.core.async.t13005.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = ((function (flag){
return (function (_){var self__ = this;
var ___$1 = this;cljs.core.reset_BANG_.call(null,self__.flag,null);
return true;
});})(flag))
;
cljs.core.async.t13005.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (flag){
return (function (_13007){var self__ = this;
var _13007__$1 = this;return self__.meta13006;
});})(flag))
;
cljs.core.async.t13005.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (flag){
return (function (_13007,meta13006__$1){var self__ = this;
var _13007__$1 = this;return (new cljs.core.async.t13005(self__.flag,self__.alt_flag,meta13006__$1));
});})(flag))
;
cljs.core.async.__GT_t13005 = ((function (flag){
return (function __GT_t13005(flag__$1,alt_flag__$1,meta13006){return (new cljs.core.async.t13005(flag__$1,alt_flag__$1,meta13006));
});})(flag))
;
}
return (new cljs.core.async.t13005(flag,alt_flag,null));
});
cljs.core.async.alt_handler = (function alt_handler(flag,cb){if(typeof cljs.core.async.t13011 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t13011 = (function (cb,flag,alt_handler,meta13012){
this.cb = cb;
this.flag = flag;
this.alt_handler = alt_handler;
this.meta13012 = meta13012;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t13011.cljs$lang$type = true;
cljs.core.async.t13011.cljs$lang$ctorStr = "cljs.core.async/t13011";
cljs.core.async.t13011.cljs$lang$ctorPrWriter = (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t13011");
});
cljs.core.async.t13011.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t13011.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.active_QMARK_.call(null,self__.flag);
});
cljs.core.async.t13011.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){var self__ = this;
var ___$1 = this;cljs.core.async.impl.protocols.commit.call(null,self__.flag);
return self__.cb;
});
cljs.core.async.t13011.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_13013){var self__ = this;
var _13013__$1 = this;return self__.meta13012;
});
cljs.core.async.t13011.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_13013,meta13012__$1){var self__ = this;
var _13013__$1 = this;return (new cljs.core.async.t13011(self__.cb,self__.flag,self__.alt_handler,meta13012__$1));
});
cljs.core.async.__GT_t13011 = (function __GT_t13011(cb__$1,flag__$1,alt_handler__$1,meta13012){return (new cljs.core.async.t13011(cb__$1,flag__$1,alt_handler__$1,meta13012));
});
}
return (new cljs.core.async.t13011(cb,flag,alt_handler,null));
});
/**
* returns derefable [val port] if immediate, nil if enqueued
*/
cljs.core.async.do_alts = (function do_alts(fret,ports,opts){var flag = cljs.core.async.alt_flag.call(null);var n = cljs.core.count.call(null,ports);var idxs = cljs.core.async.random_array.call(null,n);var priority = new cljs.core.Keyword(null,"priority","priority",1431093715).cljs$core$IFn$_invoke$arity$1(opts);var ret = (function (){var i = (0);while(true){
if((i < n))
{var idx = (cljs.core.truth_(priority)?i:(idxs[i]));var port = cljs.core.nth.call(null,ports,idx);var wport = ((cljs.core.vector_QMARK_.call(null,port))?port.call(null,(0)):null);var vbox = (cljs.core.truth_(wport)?(function (){var val = port.call(null,(1));return cljs.core.async.impl.protocols.put_BANG_.call(null,wport,val,cljs.core.async.alt_handler.call(null,flag,((function (i,val,idx,port,wport,flag,n,idxs,priority){
return (function (p1__13014_SHARP_){return fret.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__13014_SHARP_,wport], null));
});})(i,val,idx,port,wport,flag,n,idxs,priority))
));
})():cljs.core.async.impl.protocols.take_BANG_.call(null,port,cljs.core.async.alt_handler.call(null,flag,((function (i,idx,port,wport,flag,n,idxs,priority){
return (function (p1__13015_SHARP_){return fret.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__13015_SHARP_,port], null));
});})(i,idx,port,wport,flag,n,idxs,priority))
)));if(cljs.core.truth_(vbox))
{return cljs.core.async.impl.channels.box.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.deref.call(null,vbox),(function (){var or__7308__auto__ = wport;if(cljs.core.truth_(or__7308__auto__))
{return or__7308__auto__;
} else
{return port;
}
})()], null));
} else
{{
var G__13016 = (i + (1));
i = G__13016;
continue;
}
}
} else
{return null;
}
break;
}
})();var or__7308__auto__ = ret;if(cljs.core.truth_(or__7308__auto__))
{return or__7308__auto__;
} else
{if(cljs.core.contains_QMARK_.call(null,opts,new cljs.core.Keyword(null,"default","default",-1987822328)))
{var temp__4126__auto__ = (function (){var and__7296__auto__ = cljs.core.async.impl.protocols.active_QMARK_.call(null,flag);if(cljs.core.truth_(and__7296__auto__))
{return cljs.core.async.impl.protocols.commit.call(null,flag);
} else
{return and__7296__auto__;
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
var alts_BANG___delegate = function (ports,p__13017){var map__13019 = p__13017;var map__13019__$1 = ((cljs.core.seq_QMARK_.call(null,map__13019))?cljs.core.apply.call(null,cljs.core.hash_map,map__13019):map__13019);var opts = map__13019__$1;throw (new Error("alts! used not in (go ...) block"));
};
var alts_BANG_ = function (ports,var_args){
var p__13017 = null;if (arguments.length > 1) {
  p__13017 = cljs.core.array_seq(Array.prototype.slice.call(arguments, 1),0);} 
return alts_BANG___delegate.call(this,ports,p__13017);};
alts_BANG_.cljs$lang$maxFixedArity = 1;
alts_BANG_.cljs$lang$applyTo = (function (arglist__13020){
var ports = cljs.core.first(arglist__13020);
var p__13017 = cljs.core.rest(arglist__13020);
return alts_BANG___delegate(ports,p__13017);
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
var pipe__3 = (function (from,to,close_QMARK_){var c__10224__auto___13115 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___13115){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___13115){
return (function (state_13091){var state_val_13092 = (state_13091[(1)]);if((state_val_13092 === (7)))
{var inst_13087 = (state_13091[(2)]);var state_13091__$1 = state_13091;var statearr_13093_13116 = state_13091__$1;(statearr_13093_13116[(2)] = inst_13087);
(statearr_13093_13116[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (1)))
{var state_13091__$1 = state_13091;var statearr_13094_13117 = state_13091__$1;(statearr_13094_13117[(2)] = null);
(statearr_13094_13117[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (4)))
{var inst_13070 = (state_13091[(7)]);var inst_13070__$1 = (state_13091[(2)]);var inst_13071 = (inst_13070__$1 == null);var state_13091__$1 = (function (){var statearr_13095 = state_13091;(statearr_13095[(7)] = inst_13070__$1);
return statearr_13095;
})();if(cljs.core.truth_(inst_13071))
{var statearr_13096_13118 = state_13091__$1;(statearr_13096_13118[(1)] = (5));
} else
{var statearr_13097_13119 = state_13091__$1;(statearr_13097_13119[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (13)))
{var state_13091__$1 = state_13091;var statearr_13098_13120 = state_13091__$1;(statearr_13098_13120[(2)] = null);
(statearr_13098_13120[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (6)))
{var inst_13070 = (state_13091[(7)]);var state_13091__$1 = state_13091;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13091__$1,(11),to,inst_13070);
} else
{if((state_val_13092 === (3)))
{var inst_13089 = (state_13091[(2)]);var state_13091__$1 = state_13091;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13091__$1,inst_13089);
} else
{if((state_val_13092 === (12)))
{var state_13091__$1 = state_13091;var statearr_13099_13121 = state_13091__$1;(statearr_13099_13121[(2)] = null);
(statearr_13099_13121[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (2)))
{var state_13091__$1 = state_13091;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13091__$1,(4),from);
} else
{if((state_val_13092 === (11)))
{var inst_13080 = (state_13091[(2)]);var state_13091__$1 = state_13091;if(cljs.core.truth_(inst_13080))
{var statearr_13100_13122 = state_13091__$1;(statearr_13100_13122[(1)] = (12));
} else
{var statearr_13101_13123 = state_13091__$1;(statearr_13101_13123[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (9)))
{var state_13091__$1 = state_13091;var statearr_13102_13124 = state_13091__$1;(statearr_13102_13124[(2)] = null);
(statearr_13102_13124[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (5)))
{var state_13091__$1 = state_13091;if(cljs.core.truth_(close_QMARK_))
{var statearr_13103_13125 = state_13091__$1;(statearr_13103_13125[(1)] = (8));
} else
{var statearr_13104_13126 = state_13091__$1;(statearr_13104_13126[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (14)))
{var inst_13085 = (state_13091[(2)]);var state_13091__$1 = state_13091;var statearr_13105_13127 = state_13091__$1;(statearr_13105_13127[(2)] = inst_13085);
(statearr_13105_13127[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (10)))
{var inst_13077 = (state_13091[(2)]);var state_13091__$1 = state_13091;var statearr_13106_13128 = state_13091__$1;(statearr_13106_13128[(2)] = inst_13077);
(statearr_13106_13128[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13092 === (8)))
{var inst_13074 = cljs.core.async.close_BANG_.call(null,to);var state_13091__$1 = state_13091;var statearr_13107_13129 = state_13091__$1;(statearr_13107_13129[(2)] = inst_13074);
(statearr_13107_13129[(1)] = (10));
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
});})(c__10224__auto___13115))
;return ((function (switch__10159__auto__,c__10224__auto___13115){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_13111 = [null,null,null,null,null,null,null,null];(statearr_13111[(0)] = state_machine__10160__auto__);
(statearr_13111[(1)] = (1));
return statearr_13111;
});
var state_machine__10160__auto____1 = (function (state_13091){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_13091);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e13112){if((e13112 instanceof Object))
{var ex__10163__auto__ = e13112;var statearr_13113_13130 = state_13091;(statearr_13113_13130[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13091);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13112;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13131 = state_13091;
state_13091 = G__13131;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_13091){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_13091);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___13115))
})();var state__10226__auto__ = (function (){var statearr_13114 = f__10225__auto__.call(null);(statearr_13114[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___13115);
return statearr_13114;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___13115))
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
return (function (p__13315){var vec__13316 = p__13315;var v = cljs.core.nth.call(null,vec__13316,(0),null);var p = cljs.core.nth.call(null,vec__13316,(1),null);var job = vec__13316;if((job == null))
{cljs.core.async.close_BANG_.call(null,results);
return null;
} else
{var res = cljs.core.async.chan.call(null,(1),xf,ex_handler);var c__10224__auto___13498 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___13498,res,vec__13316,v,p,job,jobs,results){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___13498,res,vec__13316,v,p,job,jobs,results){
return (function (state_13321){var state_val_13322 = (state_13321[(1)]);if((state_val_13322 === (2)))
{var inst_13318 = (state_13321[(2)]);var inst_13319 = cljs.core.async.close_BANG_.call(null,res);var state_13321__$1 = (function (){var statearr_13323 = state_13321;(statearr_13323[(7)] = inst_13318);
return statearr_13323;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13321__$1,inst_13319);
} else
{if((state_val_13322 === (1)))
{var state_13321__$1 = state_13321;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13321__$1,(2),res,v);
} else
{return null;
}
}
});})(c__10224__auto___13498,res,vec__13316,v,p,job,jobs,results))
;return ((function (switch__10159__auto__,c__10224__auto___13498,res,vec__13316,v,p,job,jobs,results){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_13327 = [null,null,null,null,null,null,null,null];(statearr_13327[(0)] = state_machine__10160__auto__);
(statearr_13327[(1)] = (1));
return statearr_13327;
});
var state_machine__10160__auto____1 = (function (state_13321){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_13321);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e13328){if((e13328 instanceof Object))
{var ex__10163__auto__ = e13328;var statearr_13329_13499 = state_13321;(statearr_13329_13499[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13321);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13328;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13500 = state_13321;
state_13321 = G__13500;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_13321){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_13321);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___13498,res,vec__13316,v,p,job,jobs,results))
})();var state__10226__auto__ = (function (){var statearr_13330 = f__10225__auto__.call(null);(statearr_13330[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___13498);
return statearr_13330;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___13498,res,vec__13316,v,p,job,jobs,results))
);
cljs.core.async.put_BANG_.call(null,p,res);
return true;
}
});})(jobs,results))
;var async = ((function (jobs,results,process){
return (function (p__13331){var vec__13332 = p__13331;var v = cljs.core.nth.call(null,vec__13332,(0),null);var p = cljs.core.nth.call(null,vec__13332,(1),null);var job = vec__13332;if((job == null))
{cljs.core.async.close_BANG_.call(null,results);
return null;
} else
{var res = cljs.core.async.chan.call(null,(1));xf.call(null,v,res);
cljs.core.async.put_BANG_.call(null,p,res);
return true;
}
});})(jobs,results,process))
;var n__8164__auto___13501 = n;var __13502 = (0);while(true){
if((__13502 < n__8164__auto___13501))
{var G__13333_13503 = (((type instanceof cljs.core.Keyword))?type.fqn:null);switch (G__13333_13503) {
case "async":
var c__10224__auto___13505 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (__13502,c__10224__auto___13505,G__13333_13503,n__8164__auto___13501,jobs,results,process,async){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (__13502,c__10224__auto___13505,G__13333_13503,n__8164__auto___13501,jobs,results,process,async){
return (function (state_13346){var state_val_13347 = (state_13346[(1)]);if((state_val_13347 === (7)))
{var inst_13342 = (state_13346[(2)]);var state_13346__$1 = state_13346;var statearr_13348_13506 = state_13346__$1;(statearr_13348_13506[(2)] = inst_13342);
(statearr_13348_13506[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13347 === (6)))
{var state_13346__$1 = state_13346;var statearr_13349_13507 = state_13346__$1;(statearr_13349_13507[(2)] = null);
(statearr_13349_13507[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13347 === (5)))
{var state_13346__$1 = state_13346;var statearr_13350_13508 = state_13346__$1;(statearr_13350_13508[(2)] = null);
(statearr_13350_13508[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13347 === (4)))
{var inst_13336 = (state_13346[(2)]);var inst_13337 = async.call(null,inst_13336);var state_13346__$1 = state_13346;if(cljs.core.truth_(inst_13337))
{var statearr_13351_13509 = state_13346__$1;(statearr_13351_13509[(1)] = (5));
} else
{var statearr_13352_13510 = state_13346__$1;(statearr_13352_13510[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13347 === (3)))
{var inst_13344 = (state_13346[(2)]);var state_13346__$1 = state_13346;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13346__$1,inst_13344);
} else
{if((state_val_13347 === (2)))
{var state_13346__$1 = state_13346;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13346__$1,(4),jobs);
} else
{if((state_val_13347 === (1)))
{var state_13346__$1 = state_13346;var statearr_13353_13511 = state_13346__$1;(statearr_13353_13511[(2)] = null);
(statearr_13353_13511[(1)] = (2));
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
});})(__13502,c__10224__auto___13505,G__13333_13503,n__8164__auto___13501,jobs,results,process,async))
;return ((function (__13502,switch__10159__auto__,c__10224__auto___13505,G__13333_13503,n__8164__auto___13501,jobs,results,process,async){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_13357 = [null,null,null,null,null,null,null];(statearr_13357[(0)] = state_machine__10160__auto__);
(statearr_13357[(1)] = (1));
return statearr_13357;
});
var state_machine__10160__auto____1 = (function (state_13346){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_13346);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e13358){if((e13358 instanceof Object))
{var ex__10163__auto__ = e13358;var statearr_13359_13512 = state_13346;(statearr_13359_13512[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13346);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13358;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13513 = state_13346;
state_13346 = G__13513;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_13346){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_13346);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(__13502,switch__10159__auto__,c__10224__auto___13505,G__13333_13503,n__8164__auto___13501,jobs,results,process,async))
})();var state__10226__auto__ = (function (){var statearr_13360 = f__10225__auto__.call(null);(statearr_13360[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___13505);
return statearr_13360;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(__13502,c__10224__auto___13505,G__13333_13503,n__8164__auto___13501,jobs,results,process,async))
);

break;
case "compute":
var c__10224__auto___13514 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (__13502,c__10224__auto___13514,G__13333_13503,n__8164__auto___13501,jobs,results,process,async){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (__13502,c__10224__auto___13514,G__13333_13503,n__8164__auto___13501,jobs,results,process,async){
return (function (state_13373){var state_val_13374 = (state_13373[(1)]);if((state_val_13374 === (7)))
{var inst_13369 = (state_13373[(2)]);var state_13373__$1 = state_13373;var statearr_13375_13515 = state_13373__$1;(statearr_13375_13515[(2)] = inst_13369);
(statearr_13375_13515[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13374 === (6)))
{var state_13373__$1 = state_13373;var statearr_13376_13516 = state_13373__$1;(statearr_13376_13516[(2)] = null);
(statearr_13376_13516[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13374 === (5)))
{var state_13373__$1 = state_13373;var statearr_13377_13517 = state_13373__$1;(statearr_13377_13517[(2)] = null);
(statearr_13377_13517[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13374 === (4)))
{var inst_13363 = (state_13373[(2)]);var inst_13364 = process.call(null,inst_13363);var state_13373__$1 = state_13373;if(cljs.core.truth_(inst_13364))
{var statearr_13378_13518 = state_13373__$1;(statearr_13378_13518[(1)] = (5));
} else
{var statearr_13379_13519 = state_13373__$1;(statearr_13379_13519[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13374 === (3)))
{var inst_13371 = (state_13373[(2)]);var state_13373__$1 = state_13373;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13373__$1,inst_13371);
} else
{if((state_val_13374 === (2)))
{var state_13373__$1 = state_13373;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13373__$1,(4),jobs);
} else
{if((state_val_13374 === (1)))
{var state_13373__$1 = state_13373;var statearr_13380_13520 = state_13373__$1;(statearr_13380_13520[(2)] = null);
(statearr_13380_13520[(1)] = (2));
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
});})(__13502,c__10224__auto___13514,G__13333_13503,n__8164__auto___13501,jobs,results,process,async))
;return ((function (__13502,switch__10159__auto__,c__10224__auto___13514,G__13333_13503,n__8164__auto___13501,jobs,results,process,async){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_13384 = [null,null,null,null,null,null,null];(statearr_13384[(0)] = state_machine__10160__auto__);
(statearr_13384[(1)] = (1));
return statearr_13384;
});
var state_machine__10160__auto____1 = (function (state_13373){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_13373);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e13385){if((e13385 instanceof Object))
{var ex__10163__auto__ = e13385;var statearr_13386_13521 = state_13373;(statearr_13386_13521[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13373);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13385;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13522 = state_13373;
state_13373 = G__13522;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_13373){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_13373);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(__13502,switch__10159__auto__,c__10224__auto___13514,G__13333_13503,n__8164__auto___13501,jobs,results,process,async))
})();var state__10226__auto__ = (function (){var statearr_13387 = f__10225__auto__.call(null);(statearr_13387[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___13514);
return statearr_13387;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(__13502,c__10224__auto___13514,G__13333_13503,n__8164__auto___13501,jobs,results,process,async))
);

break;
default:
throw (new Error(("No matching clause: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(type))));

}
{
var G__13523 = (__13502 + (1));
__13502 = G__13523;
continue;
}
} else
{}
break;
}
var c__10224__auto___13524 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___13524,jobs,results,process,async){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___13524,jobs,results,process,async){
return (function (state_13409){var state_val_13410 = (state_13409[(1)]);if((state_val_13410 === (9)))
{var inst_13402 = (state_13409[(2)]);var state_13409__$1 = (function (){var statearr_13411 = state_13409;(statearr_13411[(7)] = inst_13402);
return statearr_13411;
})();var statearr_13412_13525 = state_13409__$1;(statearr_13412_13525[(2)] = null);
(statearr_13412_13525[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13410 === (8)))
{var inst_13395 = (state_13409[(8)]);var inst_13400 = (state_13409[(2)]);var state_13409__$1 = (function (){var statearr_13413 = state_13409;(statearr_13413[(9)] = inst_13400);
return statearr_13413;
})();return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13409__$1,(9),results,inst_13395);
} else
{if((state_val_13410 === (7)))
{var inst_13405 = (state_13409[(2)]);var state_13409__$1 = state_13409;var statearr_13414_13526 = state_13409__$1;(statearr_13414_13526[(2)] = inst_13405);
(statearr_13414_13526[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13410 === (6)))
{var inst_13395 = (state_13409[(8)]);var inst_13390 = (state_13409[(10)]);var inst_13395__$1 = cljs.core.async.chan.call(null,(1));var inst_13396 = cljs.core.PersistentVector.EMPTY_NODE;var inst_13397 = [inst_13390,inst_13395__$1];var inst_13398 = (new cljs.core.PersistentVector(null,2,(5),inst_13396,inst_13397,null));var state_13409__$1 = (function (){var statearr_13415 = state_13409;(statearr_13415[(8)] = inst_13395__$1);
return statearr_13415;
})();return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13409__$1,(8),jobs,inst_13398);
} else
{if((state_val_13410 === (5)))
{var inst_13393 = cljs.core.async.close_BANG_.call(null,jobs);var state_13409__$1 = state_13409;var statearr_13416_13527 = state_13409__$1;(statearr_13416_13527[(2)] = inst_13393);
(statearr_13416_13527[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13410 === (4)))
{var inst_13390 = (state_13409[(10)]);var inst_13390__$1 = (state_13409[(2)]);var inst_13391 = (inst_13390__$1 == null);var state_13409__$1 = (function (){var statearr_13417 = state_13409;(statearr_13417[(10)] = inst_13390__$1);
return statearr_13417;
})();if(cljs.core.truth_(inst_13391))
{var statearr_13418_13528 = state_13409__$1;(statearr_13418_13528[(1)] = (5));
} else
{var statearr_13419_13529 = state_13409__$1;(statearr_13419_13529[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13410 === (3)))
{var inst_13407 = (state_13409[(2)]);var state_13409__$1 = state_13409;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13409__$1,inst_13407);
} else
{if((state_val_13410 === (2)))
{var state_13409__$1 = state_13409;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13409__$1,(4),from);
} else
{if((state_val_13410 === (1)))
{var state_13409__$1 = state_13409;var statearr_13420_13530 = state_13409__$1;(statearr_13420_13530[(2)] = null);
(statearr_13420_13530[(1)] = (2));
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
});})(c__10224__auto___13524,jobs,results,process,async))
;return ((function (switch__10159__auto__,c__10224__auto___13524,jobs,results,process,async){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_13424 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_13424[(0)] = state_machine__10160__auto__);
(statearr_13424[(1)] = (1));
return statearr_13424;
});
var state_machine__10160__auto____1 = (function (state_13409){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_13409);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e13425){if((e13425 instanceof Object))
{var ex__10163__auto__ = e13425;var statearr_13426_13531 = state_13409;(statearr_13426_13531[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13409);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13425;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13532 = state_13409;
state_13409 = G__13532;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_13409){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_13409);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___13524,jobs,results,process,async))
})();var state__10226__auto__ = (function (){var statearr_13427 = f__10225__auto__.call(null);(statearr_13427[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___13524);
return statearr_13427;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___13524,jobs,results,process,async))
);
var c__10224__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto__,jobs,results,process,async){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto__,jobs,results,process,async){
return (function (state_13465){var state_val_13466 = (state_13465[(1)]);if((state_val_13466 === (7)))
{var inst_13461 = (state_13465[(2)]);var state_13465__$1 = state_13465;var statearr_13467_13533 = state_13465__$1;(statearr_13467_13533[(2)] = inst_13461);
(statearr_13467_13533[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (20)))
{var state_13465__$1 = state_13465;var statearr_13468_13534 = state_13465__$1;(statearr_13468_13534[(2)] = null);
(statearr_13468_13534[(1)] = (21));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (1)))
{var state_13465__$1 = state_13465;var statearr_13469_13535 = state_13465__$1;(statearr_13469_13535[(2)] = null);
(statearr_13469_13535[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (4)))
{var inst_13430 = (state_13465[(7)]);var inst_13430__$1 = (state_13465[(2)]);var inst_13431 = (inst_13430__$1 == null);var state_13465__$1 = (function (){var statearr_13470 = state_13465;(statearr_13470[(7)] = inst_13430__$1);
return statearr_13470;
})();if(cljs.core.truth_(inst_13431))
{var statearr_13471_13536 = state_13465__$1;(statearr_13471_13536[(1)] = (5));
} else
{var statearr_13472_13537 = state_13465__$1;(statearr_13472_13537[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (15)))
{var inst_13443 = (state_13465[(8)]);var state_13465__$1 = state_13465;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13465__$1,(18),to,inst_13443);
} else
{if((state_val_13466 === (21)))
{var inst_13456 = (state_13465[(2)]);var state_13465__$1 = state_13465;var statearr_13473_13538 = state_13465__$1;(statearr_13473_13538[(2)] = inst_13456);
(statearr_13473_13538[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (13)))
{var inst_13458 = (state_13465[(2)]);var state_13465__$1 = (function (){var statearr_13474 = state_13465;(statearr_13474[(9)] = inst_13458);
return statearr_13474;
})();var statearr_13475_13539 = state_13465__$1;(statearr_13475_13539[(2)] = null);
(statearr_13475_13539[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (6)))
{var inst_13430 = (state_13465[(7)]);var state_13465__$1 = state_13465;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13465__$1,(11),inst_13430);
} else
{if((state_val_13466 === (17)))
{var inst_13451 = (state_13465[(2)]);var state_13465__$1 = state_13465;if(cljs.core.truth_(inst_13451))
{var statearr_13476_13540 = state_13465__$1;(statearr_13476_13540[(1)] = (19));
} else
{var statearr_13477_13541 = state_13465__$1;(statearr_13477_13541[(1)] = (20));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (3)))
{var inst_13463 = (state_13465[(2)]);var state_13465__$1 = state_13465;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13465__$1,inst_13463);
} else
{if((state_val_13466 === (12)))
{var inst_13440 = (state_13465[(10)]);var state_13465__$1 = state_13465;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13465__$1,(14),inst_13440);
} else
{if((state_val_13466 === (2)))
{var state_13465__$1 = state_13465;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13465__$1,(4),results);
} else
{if((state_val_13466 === (19)))
{var state_13465__$1 = state_13465;var statearr_13478_13542 = state_13465__$1;(statearr_13478_13542[(2)] = null);
(statearr_13478_13542[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (11)))
{var inst_13440 = (state_13465[(2)]);var state_13465__$1 = (function (){var statearr_13479 = state_13465;(statearr_13479[(10)] = inst_13440);
return statearr_13479;
})();var statearr_13480_13543 = state_13465__$1;(statearr_13480_13543[(2)] = null);
(statearr_13480_13543[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (9)))
{var state_13465__$1 = state_13465;var statearr_13481_13544 = state_13465__$1;(statearr_13481_13544[(2)] = null);
(statearr_13481_13544[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (5)))
{var state_13465__$1 = state_13465;if(cljs.core.truth_(close_QMARK_))
{var statearr_13482_13545 = state_13465__$1;(statearr_13482_13545[(1)] = (8));
} else
{var statearr_13483_13546 = state_13465__$1;(statearr_13483_13546[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (14)))
{var inst_13443 = (state_13465[(8)]);var inst_13445 = (state_13465[(11)]);var inst_13443__$1 = (state_13465[(2)]);var inst_13444 = (inst_13443__$1 == null);var inst_13445__$1 = cljs.core.not.call(null,inst_13444);var state_13465__$1 = (function (){var statearr_13484 = state_13465;(statearr_13484[(8)] = inst_13443__$1);
(statearr_13484[(11)] = inst_13445__$1);
return statearr_13484;
})();if(inst_13445__$1)
{var statearr_13485_13547 = state_13465__$1;(statearr_13485_13547[(1)] = (15));
} else
{var statearr_13486_13548 = state_13465__$1;(statearr_13486_13548[(1)] = (16));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (16)))
{var inst_13445 = (state_13465[(11)]);var state_13465__$1 = state_13465;var statearr_13487_13549 = state_13465__$1;(statearr_13487_13549[(2)] = inst_13445);
(statearr_13487_13549[(1)] = (17));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (10)))
{var inst_13437 = (state_13465[(2)]);var state_13465__$1 = state_13465;var statearr_13488_13550 = state_13465__$1;(statearr_13488_13550[(2)] = inst_13437);
(statearr_13488_13550[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (18)))
{var inst_13448 = (state_13465[(2)]);var state_13465__$1 = state_13465;var statearr_13489_13551 = state_13465__$1;(statearr_13489_13551[(2)] = inst_13448);
(statearr_13489_13551[(1)] = (17));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13466 === (8)))
{var inst_13434 = cljs.core.async.close_BANG_.call(null,to);var state_13465__$1 = state_13465;var statearr_13490_13552 = state_13465__$1;(statearr_13490_13552[(2)] = inst_13434);
(statearr_13490_13552[(1)] = (10));
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
});})(c__10224__auto__,jobs,results,process,async))
;return ((function (switch__10159__auto__,c__10224__auto__,jobs,results,process,async){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_13494 = [null,null,null,null,null,null,null,null,null,null,null,null];(statearr_13494[(0)] = state_machine__10160__auto__);
(statearr_13494[(1)] = (1));
return statearr_13494;
});
var state_machine__10160__auto____1 = (function (state_13465){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_13465);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e13495){if((e13495 instanceof Object))
{var ex__10163__auto__ = e13495;var statearr_13496_13553 = state_13465;(statearr_13496_13553[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13465);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13495;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13554 = state_13465;
state_13465 = G__13554;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_13465){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_13465);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto__,jobs,results,process,async))
})();var state__10226__auto__ = (function (){var statearr_13497 = f__10225__auto__.call(null);(statearr_13497[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto__);
return statearr_13497;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto__,jobs,results,process,async))
);
return c__10224__auto__;
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
var split__4 = (function (p,ch,t_buf_or_n,f_buf_or_n){var tc = cljs.core.async.chan.call(null,t_buf_or_n);var fc = cljs.core.async.chan.call(null,f_buf_or_n);var c__10224__auto___13655 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___13655,tc,fc){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___13655,tc,fc){
return (function (state_13630){var state_val_13631 = (state_13630[(1)]);if((state_val_13631 === (7)))
{var inst_13626 = (state_13630[(2)]);var state_13630__$1 = state_13630;var statearr_13632_13656 = state_13630__$1;(statearr_13632_13656[(2)] = inst_13626);
(statearr_13632_13656[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (1)))
{var state_13630__$1 = state_13630;var statearr_13633_13657 = state_13630__$1;(statearr_13633_13657[(2)] = null);
(statearr_13633_13657[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (4)))
{var inst_13607 = (state_13630[(7)]);var inst_13607__$1 = (state_13630[(2)]);var inst_13608 = (inst_13607__$1 == null);var state_13630__$1 = (function (){var statearr_13634 = state_13630;(statearr_13634[(7)] = inst_13607__$1);
return statearr_13634;
})();if(cljs.core.truth_(inst_13608))
{var statearr_13635_13658 = state_13630__$1;(statearr_13635_13658[(1)] = (5));
} else
{var statearr_13636_13659 = state_13630__$1;(statearr_13636_13659[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (13)))
{var state_13630__$1 = state_13630;var statearr_13637_13660 = state_13630__$1;(statearr_13637_13660[(2)] = null);
(statearr_13637_13660[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (6)))
{var inst_13607 = (state_13630[(7)]);var inst_13613 = p.call(null,inst_13607);var state_13630__$1 = state_13630;if(cljs.core.truth_(inst_13613))
{var statearr_13638_13661 = state_13630__$1;(statearr_13638_13661[(1)] = (9));
} else
{var statearr_13639_13662 = state_13630__$1;(statearr_13639_13662[(1)] = (10));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (3)))
{var inst_13628 = (state_13630[(2)]);var state_13630__$1 = state_13630;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13630__$1,inst_13628);
} else
{if((state_val_13631 === (12)))
{var state_13630__$1 = state_13630;var statearr_13640_13663 = state_13630__$1;(statearr_13640_13663[(2)] = null);
(statearr_13640_13663[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (2)))
{var state_13630__$1 = state_13630;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13630__$1,(4),ch);
} else
{if((state_val_13631 === (11)))
{var inst_13607 = (state_13630[(7)]);var inst_13617 = (state_13630[(2)]);var state_13630__$1 = state_13630;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13630__$1,(8),inst_13617,inst_13607);
} else
{if((state_val_13631 === (9)))
{var state_13630__$1 = state_13630;var statearr_13641_13664 = state_13630__$1;(statearr_13641_13664[(2)] = tc);
(statearr_13641_13664[(1)] = (11));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (5)))
{var inst_13610 = cljs.core.async.close_BANG_.call(null,tc);var inst_13611 = cljs.core.async.close_BANG_.call(null,fc);var state_13630__$1 = (function (){var statearr_13642 = state_13630;(statearr_13642[(8)] = inst_13610);
return statearr_13642;
})();var statearr_13643_13665 = state_13630__$1;(statearr_13643_13665[(2)] = inst_13611);
(statearr_13643_13665[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (14)))
{var inst_13624 = (state_13630[(2)]);var state_13630__$1 = state_13630;var statearr_13644_13666 = state_13630__$1;(statearr_13644_13666[(2)] = inst_13624);
(statearr_13644_13666[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (10)))
{var state_13630__$1 = state_13630;var statearr_13645_13667 = state_13630__$1;(statearr_13645_13667[(2)] = fc);
(statearr_13645_13667[(1)] = (11));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13631 === (8)))
{var inst_13619 = (state_13630[(2)]);var state_13630__$1 = state_13630;if(cljs.core.truth_(inst_13619))
{var statearr_13646_13668 = state_13630__$1;(statearr_13646_13668[(1)] = (12));
} else
{var statearr_13647_13669 = state_13630__$1;(statearr_13647_13669[(1)] = (13));
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
});})(c__10224__auto___13655,tc,fc))
;return ((function (switch__10159__auto__,c__10224__auto___13655,tc,fc){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_13651 = [null,null,null,null,null,null,null,null,null];(statearr_13651[(0)] = state_machine__10160__auto__);
(statearr_13651[(1)] = (1));
return statearr_13651;
});
var state_machine__10160__auto____1 = (function (state_13630){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_13630);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e13652){if((e13652 instanceof Object))
{var ex__10163__auto__ = e13652;var statearr_13653_13670 = state_13630;(statearr_13653_13670[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13630);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13652;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13671 = state_13630;
state_13630 = G__13671;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_13630){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_13630);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___13655,tc,fc))
})();var state__10226__auto__ = (function (){var statearr_13654 = f__10225__auto__.call(null);(statearr_13654[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___13655);
return statearr_13654;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___13655,tc,fc))
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
cljs.core.async.reduce = (function reduce(f,init,ch){var c__10224__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto__){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto__){
return (function (state_13718){var state_val_13719 = (state_13718[(1)]);if((state_val_13719 === (7)))
{var inst_13714 = (state_13718[(2)]);var state_13718__$1 = state_13718;var statearr_13720_13736 = state_13718__$1;(statearr_13720_13736[(2)] = inst_13714);
(statearr_13720_13736[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13719 === (6)))
{var inst_13707 = (state_13718[(7)]);var inst_13704 = (state_13718[(8)]);var inst_13711 = f.call(null,inst_13704,inst_13707);var inst_13704__$1 = inst_13711;var state_13718__$1 = (function (){var statearr_13721 = state_13718;(statearr_13721[(8)] = inst_13704__$1);
return statearr_13721;
})();var statearr_13722_13737 = state_13718__$1;(statearr_13722_13737[(2)] = null);
(statearr_13722_13737[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13719 === (5)))
{var inst_13704 = (state_13718[(8)]);var state_13718__$1 = state_13718;var statearr_13723_13738 = state_13718__$1;(statearr_13723_13738[(2)] = inst_13704);
(statearr_13723_13738[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13719 === (4)))
{var inst_13707 = (state_13718[(7)]);var inst_13707__$1 = (state_13718[(2)]);var inst_13708 = (inst_13707__$1 == null);var state_13718__$1 = (function (){var statearr_13724 = state_13718;(statearr_13724[(7)] = inst_13707__$1);
return statearr_13724;
})();if(cljs.core.truth_(inst_13708))
{var statearr_13725_13739 = state_13718__$1;(statearr_13725_13739[(1)] = (5));
} else
{var statearr_13726_13740 = state_13718__$1;(statearr_13726_13740[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13719 === (3)))
{var inst_13716 = (state_13718[(2)]);var state_13718__$1 = state_13718;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13718__$1,inst_13716);
} else
{if((state_val_13719 === (2)))
{var state_13718__$1 = state_13718;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13718__$1,(4),ch);
} else
{if((state_val_13719 === (1)))
{var inst_13704 = init;var state_13718__$1 = (function (){var statearr_13727 = state_13718;(statearr_13727[(8)] = inst_13704);
return statearr_13727;
})();var statearr_13728_13741 = state_13718__$1;(statearr_13728_13741[(2)] = null);
(statearr_13728_13741[(1)] = (2));
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
});})(c__10224__auto__))
;return ((function (switch__10159__auto__,c__10224__auto__){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_13732 = [null,null,null,null,null,null,null,null,null];(statearr_13732[(0)] = state_machine__10160__auto__);
(statearr_13732[(1)] = (1));
return statearr_13732;
});
var state_machine__10160__auto____1 = (function (state_13718){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_13718);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e13733){if((e13733 instanceof Object))
{var ex__10163__auto__ = e13733;var statearr_13734_13742 = state_13718;(statearr_13734_13742[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13718);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13733;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13743 = state_13718;
state_13718 = G__13743;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_13718){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_13718);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto__))
})();var state__10226__auto__ = (function (){var statearr_13735 = f__10225__auto__.call(null);(statearr_13735[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto__);
return statearr_13735;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto__))
);
return c__10224__auto__;
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
var onto_chan__3 = (function (ch,coll,close_QMARK_){var c__10224__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto__){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto__){
return (function (state_13817){var state_val_13818 = (state_13817[(1)]);if((state_val_13818 === (7)))
{var inst_13799 = (state_13817[(2)]);var state_13817__$1 = state_13817;var statearr_13819_13842 = state_13817__$1;(statearr_13819_13842[(2)] = inst_13799);
(statearr_13819_13842[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (1)))
{var inst_13793 = cljs.core.seq.call(null,coll);var inst_13794 = inst_13793;var state_13817__$1 = (function (){var statearr_13820 = state_13817;(statearr_13820[(7)] = inst_13794);
return statearr_13820;
})();var statearr_13821_13843 = state_13817__$1;(statearr_13821_13843[(2)] = null);
(statearr_13821_13843[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (4)))
{var inst_13794 = (state_13817[(7)]);var inst_13797 = cljs.core.first.call(null,inst_13794);var state_13817__$1 = state_13817;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13817__$1,(7),ch,inst_13797);
} else
{if((state_val_13818 === (13)))
{var inst_13811 = (state_13817[(2)]);var state_13817__$1 = state_13817;var statearr_13822_13844 = state_13817__$1;(statearr_13822_13844[(2)] = inst_13811);
(statearr_13822_13844[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (6)))
{var inst_13802 = (state_13817[(2)]);var state_13817__$1 = state_13817;if(cljs.core.truth_(inst_13802))
{var statearr_13823_13845 = state_13817__$1;(statearr_13823_13845[(1)] = (8));
} else
{var statearr_13824_13846 = state_13817__$1;(statearr_13824_13846[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (3)))
{var inst_13815 = (state_13817[(2)]);var state_13817__$1 = state_13817;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13817__$1,inst_13815);
} else
{if((state_val_13818 === (12)))
{var state_13817__$1 = state_13817;var statearr_13825_13847 = state_13817__$1;(statearr_13825_13847[(2)] = null);
(statearr_13825_13847[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (2)))
{var inst_13794 = (state_13817[(7)]);var state_13817__$1 = state_13817;if(cljs.core.truth_(inst_13794))
{var statearr_13826_13848 = state_13817__$1;(statearr_13826_13848[(1)] = (4));
} else
{var statearr_13827_13849 = state_13817__$1;(statearr_13827_13849[(1)] = (5));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (11)))
{var inst_13808 = cljs.core.async.close_BANG_.call(null,ch);var state_13817__$1 = state_13817;var statearr_13828_13850 = state_13817__$1;(statearr_13828_13850[(2)] = inst_13808);
(statearr_13828_13850[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (9)))
{var state_13817__$1 = state_13817;if(cljs.core.truth_(close_QMARK_))
{var statearr_13829_13851 = state_13817__$1;(statearr_13829_13851[(1)] = (11));
} else
{var statearr_13830_13852 = state_13817__$1;(statearr_13830_13852[(1)] = (12));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (5)))
{var inst_13794 = (state_13817[(7)]);var state_13817__$1 = state_13817;var statearr_13831_13853 = state_13817__$1;(statearr_13831_13853[(2)] = inst_13794);
(statearr_13831_13853[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (10)))
{var inst_13813 = (state_13817[(2)]);var state_13817__$1 = state_13817;var statearr_13832_13854 = state_13817__$1;(statearr_13832_13854[(2)] = inst_13813);
(statearr_13832_13854[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13818 === (8)))
{var inst_13794 = (state_13817[(7)]);var inst_13804 = cljs.core.next.call(null,inst_13794);var inst_13794__$1 = inst_13804;var state_13817__$1 = (function (){var statearr_13833 = state_13817;(statearr_13833[(7)] = inst_13794__$1);
return statearr_13833;
})();var statearr_13834_13855 = state_13817__$1;(statearr_13834_13855[(2)] = null);
(statearr_13834_13855[(1)] = (2));
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
});})(c__10224__auto__))
;return ((function (switch__10159__auto__,c__10224__auto__){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_13838 = [null,null,null,null,null,null,null,null];(statearr_13838[(0)] = state_machine__10160__auto__);
(statearr_13838[(1)] = (1));
return statearr_13838;
});
var state_machine__10160__auto____1 = (function (state_13817){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_13817);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e13839){if((e13839 instanceof Object))
{var ex__10163__auto__ = e13839;var statearr_13840_13856 = state_13817;(statearr_13840_13856[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13817);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13839;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13857 = state_13817;
state_13817 = G__13857;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_13817){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_13817);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto__))
})();var state__10226__auto__ = (function (){var statearr_13841 = f__10225__auto__.call(null);(statearr_13841[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto__);
return statearr_13841;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto__))
);
return c__10224__auto__;
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
cljs.core.async.Mux = (function (){var obj13859 = {};return obj13859;
})();
cljs.core.async.muxch_STAR_ = (function muxch_STAR_(_){if((function (){var and__7296__auto__ = _;if(and__7296__auto__)
{return _.cljs$core$async$Mux$muxch_STAR_$arity$1;
} else
{return and__7296__auto__;
}
})())
{return _.cljs$core$async$Mux$muxch_STAR_$arity$1(_);
} else
{var x__7935__auto__ = (((_ == null))?null:_);return (function (){var or__7308__auto__ = (cljs.core.async.muxch_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.muxch_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mux.muxch*",_);
}
}
})().call(null,_);
}
});
cljs.core.async.Mult = (function (){var obj13861 = {};return obj13861;
})();
cljs.core.async.tap_STAR_ = (function tap_STAR_(m,ch,close_QMARK_){if((function (){var and__7296__auto__ = m;if(and__7296__auto__)
{return m.cljs$core$async$Mult$tap_STAR_$arity$3;
} else
{return and__7296__auto__;
}
})())
{return m.cljs$core$async$Mult$tap_STAR_$arity$3(m,ch,close_QMARK_);
} else
{var x__7935__auto__ = (((m == null))?null:m);return (function (){var or__7308__auto__ = (cljs.core.async.tap_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.tap_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mult.tap*",m);
}
}
})().call(null,m,ch,close_QMARK_);
}
});
cljs.core.async.untap_STAR_ = (function untap_STAR_(m,ch){if((function (){var and__7296__auto__ = m;if(and__7296__auto__)
{return m.cljs$core$async$Mult$untap_STAR_$arity$2;
} else
{return and__7296__auto__;
}
})())
{return m.cljs$core$async$Mult$untap_STAR_$arity$2(m,ch);
} else
{var x__7935__auto__ = (((m == null))?null:m);return (function (){var or__7308__auto__ = (cljs.core.async.untap_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.untap_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mult.untap*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.untap_all_STAR_ = (function untap_all_STAR_(m){if((function (){var and__7296__auto__ = m;if(and__7296__auto__)
{return m.cljs$core$async$Mult$untap_all_STAR_$arity$1;
} else
{return and__7296__auto__;
}
})())
{return m.cljs$core$async$Mult$untap_all_STAR_$arity$1(m);
} else
{var x__7935__auto__ = (((m == null))?null:m);return (function (){var or__7308__auto__ = (cljs.core.async.untap_all_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.untap_all_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
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
cljs.core.async.mult = (function mult(ch){var cs = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);var m = (function (){if(typeof cljs.core.async.t14083 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t14083 = (function (cs,ch,mult,meta14084){
this.cs = cs;
this.ch = ch;
this.mult = mult;
this.meta14084 = meta14084;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t14083.cljs$lang$type = true;
cljs.core.async.t14083.cljs$lang$ctorStr = "cljs.core.async/t14083";
cljs.core.async.t14083.cljs$lang$ctorPrWriter = ((function (cs){
return (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t14083");
});})(cs))
;
cljs.core.async.t14083.prototype.cljs$core$async$Mult$ = true;
cljs.core.async.t14083.prototype.cljs$core$async$Mult$tap_STAR_$arity$3 = ((function (cs){
return (function (_,ch__$2,close_QMARK_){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.assoc,ch__$2,close_QMARK_);
return null;
});})(cs))
;
cljs.core.async.t14083.prototype.cljs$core$async$Mult$untap_STAR_$arity$2 = ((function (cs){
return (function (_,ch__$2){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.dissoc,ch__$2);
return null;
});})(cs))
;
cljs.core.async.t14083.prototype.cljs$core$async$Mult$untap_all_STAR_$arity$1 = ((function (cs){
return (function (_){var self__ = this;
var ___$1 = this;cljs.core.reset_BANG_.call(null,self__.cs,cljs.core.PersistentArrayMap.EMPTY);
return null;
});})(cs))
;
cljs.core.async.t14083.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t14083.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (cs){
return (function (_){var self__ = this;
var ___$1 = this;return self__.ch;
});})(cs))
;
cljs.core.async.t14083.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (cs){
return (function (_14085){var self__ = this;
var _14085__$1 = this;return self__.meta14084;
});})(cs))
;
cljs.core.async.t14083.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (cs){
return (function (_14085,meta14084__$1){var self__ = this;
var _14085__$1 = this;return (new cljs.core.async.t14083(self__.cs,self__.ch,self__.mult,meta14084__$1));
});})(cs))
;
cljs.core.async.__GT_t14083 = ((function (cs){
return (function __GT_t14083(cs__$1,ch__$1,mult__$1,meta14084){return (new cljs.core.async.t14083(cs__$1,ch__$1,mult__$1,meta14084));
});})(cs))
;
}
return (new cljs.core.async.t14083(cs,ch,mult,null));
})();var dchan = cljs.core.async.chan.call(null,(1));var dctr = cljs.core.atom.call(null,null);var done = ((function (cs,m,dchan,dctr){
return (function (_){if((cljs.core.swap_BANG_.call(null,dctr,cljs.core.dec) === (0)))
{return cljs.core.async.put_BANG_.call(null,dchan,true);
} else
{return null;
}
});})(cs,m,dchan,dctr))
;var c__10224__auto___14304 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___14304,cs,m,dchan,dctr,done){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___14304,cs,m,dchan,dctr,done){
return (function (state_14216){var state_val_14217 = (state_14216[(1)]);if((state_val_14217 === (7)))
{var inst_14212 = (state_14216[(2)]);var state_14216__$1 = state_14216;var statearr_14218_14305 = state_14216__$1;(statearr_14218_14305[(2)] = inst_14212);
(statearr_14218_14305[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (20)))
{var inst_14117 = (state_14216[(7)]);var inst_14127 = cljs.core.first.call(null,inst_14117);var inst_14128 = cljs.core.nth.call(null,inst_14127,(0),null);var inst_14129 = cljs.core.nth.call(null,inst_14127,(1),null);var state_14216__$1 = (function (){var statearr_14219 = state_14216;(statearr_14219[(8)] = inst_14128);
return statearr_14219;
})();if(cljs.core.truth_(inst_14129))
{var statearr_14220_14306 = state_14216__$1;(statearr_14220_14306[(1)] = (22));
} else
{var statearr_14221_14307 = state_14216__$1;(statearr_14221_14307[(1)] = (23));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (27)))
{var inst_14088 = (state_14216[(9)]);var inst_14157 = (state_14216[(10)]);var inst_14159 = (state_14216[(11)]);var inst_14164 = (state_14216[(12)]);var inst_14164__$1 = cljs.core._nth.call(null,inst_14157,inst_14159);var inst_14165 = cljs.core.async.put_BANG_.call(null,inst_14164__$1,inst_14088,done);var state_14216__$1 = (function (){var statearr_14222 = state_14216;(statearr_14222[(12)] = inst_14164__$1);
return statearr_14222;
})();if(cljs.core.truth_(inst_14165))
{var statearr_14223_14308 = state_14216__$1;(statearr_14223_14308[(1)] = (30));
} else
{var statearr_14224_14309 = state_14216__$1;(statearr_14224_14309[(1)] = (31));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (1)))
{var state_14216__$1 = state_14216;var statearr_14225_14310 = state_14216__$1;(statearr_14225_14310[(2)] = null);
(statearr_14225_14310[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (24)))
{var inst_14117 = (state_14216[(7)]);var inst_14134 = (state_14216[(2)]);var inst_14135 = cljs.core.next.call(null,inst_14117);var inst_14097 = inst_14135;var inst_14098 = null;var inst_14099 = (0);var inst_14100 = (0);var state_14216__$1 = (function (){var statearr_14226 = state_14216;(statearr_14226[(13)] = inst_14098);
(statearr_14226[(14)] = inst_14099);
(statearr_14226[(15)] = inst_14134);
(statearr_14226[(16)] = inst_14100);
(statearr_14226[(17)] = inst_14097);
return statearr_14226;
})();var statearr_14227_14311 = state_14216__$1;(statearr_14227_14311[(2)] = null);
(statearr_14227_14311[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (39)))
{var state_14216__$1 = state_14216;var statearr_14231_14312 = state_14216__$1;(statearr_14231_14312[(2)] = null);
(statearr_14231_14312[(1)] = (41));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (4)))
{var inst_14088 = (state_14216[(9)]);var inst_14088__$1 = (state_14216[(2)]);var inst_14089 = (inst_14088__$1 == null);var state_14216__$1 = (function (){var statearr_14232 = state_14216;(statearr_14232[(9)] = inst_14088__$1);
return statearr_14232;
})();if(cljs.core.truth_(inst_14089))
{var statearr_14233_14313 = state_14216__$1;(statearr_14233_14313[(1)] = (5));
} else
{var statearr_14234_14314 = state_14216__$1;(statearr_14234_14314[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (15)))
{var inst_14098 = (state_14216[(13)]);var inst_14099 = (state_14216[(14)]);var inst_14100 = (state_14216[(16)]);var inst_14097 = (state_14216[(17)]);var inst_14113 = (state_14216[(2)]);var inst_14114 = (inst_14100 + (1));var tmp14228 = inst_14098;var tmp14229 = inst_14099;var tmp14230 = inst_14097;var inst_14097__$1 = tmp14230;var inst_14098__$1 = tmp14228;var inst_14099__$1 = tmp14229;var inst_14100__$1 = inst_14114;var state_14216__$1 = (function (){var statearr_14235 = state_14216;(statearr_14235[(13)] = inst_14098__$1);
(statearr_14235[(14)] = inst_14099__$1);
(statearr_14235[(16)] = inst_14100__$1);
(statearr_14235[(18)] = inst_14113);
(statearr_14235[(17)] = inst_14097__$1);
return statearr_14235;
})();var statearr_14236_14315 = state_14216__$1;(statearr_14236_14315[(2)] = null);
(statearr_14236_14315[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (21)))
{var inst_14138 = (state_14216[(2)]);var state_14216__$1 = state_14216;var statearr_14240_14316 = state_14216__$1;(statearr_14240_14316[(2)] = inst_14138);
(statearr_14240_14316[(1)] = (18));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (31)))
{var inst_14164 = (state_14216[(12)]);var inst_14168 = done.call(null,null);var inst_14169 = cljs.core.async.untap_STAR_.call(null,m,inst_14164);var state_14216__$1 = (function (){var statearr_14241 = state_14216;(statearr_14241[(19)] = inst_14168);
return statearr_14241;
})();var statearr_14242_14317 = state_14216__$1;(statearr_14242_14317[(2)] = inst_14169);
(statearr_14242_14317[(1)] = (32));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (32)))
{var inst_14156 = (state_14216[(20)]);var inst_14157 = (state_14216[(10)]);var inst_14159 = (state_14216[(11)]);var inst_14158 = (state_14216[(21)]);var inst_14171 = (state_14216[(2)]);var inst_14172 = (inst_14159 + (1));var tmp14237 = inst_14156;var tmp14238 = inst_14157;var tmp14239 = inst_14158;var inst_14156__$1 = tmp14237;var inst_14157__$1 = tmp14238;var inst_14158__$1 = tmp14239;var inst_14159__$1 = inst_14172;var state_14216__$1 = (function (){var statearr_14243 = state_14216;(statearr_14243[(22)] = inst_14171);
(statearr_14243[(20)] = inst_14156__$1);
(statearr_14243[(10)] = inst_14157__$1);
(statearr_14243[(11)] = inst_14159__$1);
(statearr_14243[(21)] = inst_14158__$1);
return statearr_14243;
})();var statearr_14244_14318 = state_14216__$1;(statearr_14244_14318[(2)] = null);
(statearr_14244_14318[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (40)))
{var inst_14184 = (state_14216[(23)]);var inst_14188 = done.call(null,null);var inst_14189 = cljs.core.async.untap_STAR_.call(null,m,inst_14184);var state_14216__$1 = (function (){var statearr_14245 = state_14216;(statearr_14245[(24)] = inst_14188);
return statearr_14245;
})();var statearr_14246_14319 = state_14216__$1;(statearr_14246_14319[(2)] = inst_14189);
(statearr_14246_14319[(1)] = (41));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (33)))
{var inst_14175 = (state_14216[(25)]);var inst_14177 = cljs.core.chunked_seq_QMARK_.call(null,inst_14175);var state_14216__$1 = state_14216;if(inst_14177)
{var statearr_14247_14320 = state_14216__$1;(statearr_14247_14320[(1)] = (36));
} else
{var statearr_14248_14321 = state_14216__$1;(statearr_14248_14321[(1)] = (37));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (13)))
{var inst_14107 = (state_14216[(26)]);var inst_14110 = cljs.core.async.close_BANG_.call(null,inst_14107);var state_14216__$1 = state_14216;var statearr_14249_14322 = state_14216__$1;(statearr_14249_14322[(2)] = inst_14110);
(statearr_14249_14322[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (22)))
{var inst_14128 = (state_14216[(8)]);var inst_14131 = cljs.core.async.close_BANG_.call(null,inst_14128);var state_14216__$1 = state_14216;var statearr_14250_14323 = state_14216__$1;(statearr_14250_14323[(2)] = inst_14131);
(statearr_14250_14323[(1)] = (24));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (36)))
{var inst_14175 = (state_14216[(25)]);var inst_14179 = cljs.core.chunk_first.call(null,inst_14175);var inst_14180 = cljs.core.chunk_rest.call(null,inst_14175);var inst_14181 = cljs.core.count.call(null,inst_14179);var inst_14156 = inst_14180;var inst_14157 = inst_14179;var inst_14158 = inst_14181;var inst_14159 = (0);var state_14216__$1 = (function (){var statearr_14251 = state_14216;(statearr_14251[(20)] = inst_14156);
(statearr_14251[(10)] = inst_14157);
(statearr_14251[(11)] = inst_14159);
(statearr_14251[(21)] = inst_14158);
return statearr_14251;
})();var statearr_14252_14324 = state_14216__$1;(statearr_14252_14324[(2)] = null);
(statearr_14252_14324[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (41)))
{var inst_14175 = (state_14216[(25)]);var inst_14191 = (state_14216[(2)]);var inst_14192 = cljs.core.next.call(null,inst_14175);var inst_14156 = inst_14192;var inst_14157 = null;var inst_14158 = (0);var inst_14159 = (0);var state_14216__$1 = (function (){var statearr_14253 = state_14216;(statearr_14253[(20)] = inst_14156);
(statearr_14253[(10)] = inst_14157);
(statearr_14253[(11)] = inst_14159);
(statearr_14253[(27)] = inst_14191);
(statearr_14253[(21)] = inst_14158);
return statearr_14253;
})();var statearr_14254_14325 = state_14216__$1;(statearr_14254_14325[(2)] = null);
(statearr_14254_14325[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (43)))
{var state_14216__$1 = state_14216;var statearr_14255_14326 = state_14216__$1;(statearr_14255_14326[(2)] = null);
(statearr_14255_14326[(1)] = (44));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (29)))
{var inst_14200 = (state_14216[(2)]);var state_14216__$1 = state_14216;var statearr_14256_14327 = state_14216__$1;(statearr_14256_14327[(2)] = inst_14200);
(statearr_14256_14327[(1)] = (26));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (44)))
{var inst_14209 = (state_14216[(2)]);var state_14216__$1 = (function (){var statearr_14257 = state_14216;(statearr_14257[(28)] = inst_14209);
return statearr_14257;
})();var statearr_14258_14328 = state_14216__$1;(statearr_14258_14328[(2)] = null);
(statearr_14258_14328[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (6)))
{var inst_14148 = (state_14216[(29)]);var inst_14147 = cljs.core.deref.call(null,cs);var inst_14148__$1 = cljs.core.keys.call(null,inst_14147);var inst_14149 = cljs.core.count.call(null,inst_14148__$1);var inst_14150 = cljs.core.reset_BANG_.call(null,dctr,inst_14149);var inst_14155 = cljs.core.seq.call(null,inst_14148__$1);var inst_14156 = inst_14155;var inst_14157 = null;var inst_14158 = (0);var inst_14159 = (0);var state_14216__$1 = (function (){var statearr_14259 = state_14216;(statearr_14259[(20)] = inst_14156);
(statearr_14259[(10)] = inst_14157);
(statearr_14259[(11)] = inst_14159);
(statearr_14259[(29)] = inst_14148__$1);
(statearr_14259[(30)] = inst_14150);
(statearr_14259[(21)] = inst_14158);
return statearr_14259;
})();var statearr_14260_14329 = state_14216__$1;(statearr_14260_14329[(2)] = null);
(statearr_14260_14329[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (28)))
{var inst_14156 = (state_14216[(20)]);var inst_14175 = (state_14216[(25)]);var inst_14175__$1 = cljs.core.seq.call(null,inst_14156);var state_14216__$1 = (function (){var statearr_14261 = state_14216;(statearr_14261[(25)] = inst_14175__$1);
return statearr_14261;
})();if(inst_14175__$1)
{var statearr_14262_14330 = state_14216__$1;(statearr_14262_14330[(1)] = (33));
} else
{var statearr_14263_14331 = state_14216__$1;(statearr_14263_14331[(1)] = (34));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (25)))
{var inst_14159 = (state_14216[(11)]);var inst_14158 = (state_14216[(21)]);var inst_14161 = (inst_14159 < inst_14158);var inst_14162 = inst_14161;var state_14216__$1 = state_14216;if(cljs.core.truth_(inst_14162))
{var statearr_14264_14332 = state_14216__$1;(statearr_14264_14332[(1)] = (27));
} else
{var statearr_14265_14333 = state_14216__$1;(statearr_14265_14333[(1)] = (28));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (34)))
{var state_14216__$1 = state_14216;var statearr_14266_14334 = state_14216__$1;(statearr_14266_14334[(2)] = null);
(statearr_14266_14334[(1)] = (35));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (17)))
{var state_14216__$1 = state_14216;var statearr_14267_14335 = state_14216__$1;(statearr_14267_14335[(2)] = null);
(statearr_14267_14335[(1)] = (18));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (3)))
{var inst_14214 = (state_14216[(2)]);var state_14216__$1 = state_14216;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14216__$1,inst_14214);
} else
{if((state_val_14217 === (12)))
{var inst_14143 = (state_14216[(2)]);var state_14216__$1 = state_14216;var statearr_14268_14336 = state_14216__$1;(statearr_14268_14336[(2)] = inst_14143);
(statearr_14268_14336[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (2)))
{var state_14216__$1 = state_14216;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_14216__$1,(4),ch);
} else
{if((state_val_14217 === (23)))
{var state_14216__$1 = state_14216;var statearr_14269_14337 = state_14216__$1;(statearr_14269_14337[(2)] = null);
(statearr_14269_14337[(1)] = (24));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (35)))
{var inst_14198 = (state_14216[(2)]);var state_14216__$1 = state_14216;var statearr_14270_14338 = state_14216__$1;(statearr_14270_14338[(2)] = inst_14198);
(statearr_14270_14338[(1)] = (29));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (19)))
{var inst_14117 = (state_14216[(7)]);var inst_14121 = cljs.core.chunk_first.call(null,inst_14117);var inst_14122 = cljs.core.chunk_rest.call(null,inst_14117);var inst_14123 = cljs.core.count.call(null,inst_14121);var inst_14097 = inst_14122;var inst_14098 = inst_14121;var inst_14099 = inst_14123;var inst_14100 = (0);var state_14216__$1 = (function (){var statearr_14271 = state_14216;(statearr_14271[(13)] = inst_14098);
(statearr_14271[(14)] = inst_14099);
(statearr_14271[(16)] = inst_14100);
(statearr_14271[(17)] = inst_14097);
return statearr_14271;
})();var statearr_14272_14339 = state_14216__$1;(statearr_14272_14339[(2)] = null);
(statearr_14272_14339[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (11)))
{var inst_14117 = (state_14216[(7)]);var inst_14097 = (state_14216[(17)]);var inst_14117__$1 = cljs.core.seq.call(null,inst_14097);var state_14216__$1 = (function (){var statearr_14273 = state_14216;(statearr_14273[(7)] = inst_14117__$1);
return statearr_14273;
})();if(inst_14117__$1)
{var statearr_14274_14340 = state_14216__$1;(statearr_14274_14340[(1)] = (16));
} else
{var statearr_14275_14341 = state_14216__$1;(statearr_14275_14341[(1)] = (17));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (9)))
{var inst_14145 = (state_14216[(2)]);var state_14216__$1 = state_14216;var statearr_14276_14342 = state_14216__$1;(statearr_14276_14342[(2)] = inst_14145);
(statearr_14276_14342[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (5)))
{var inst_14095 = cljs.core.deref.call(null,cs);var inst_14096 = cljs.core.seq.call(null,inst_14095);var inst_14097 = inst_14096;var inst_14098 = null;var inst_14099 = (0);var inst_14100 = (0);var state_14216__$1 = (function (){var statearr_14277 = state_14216;(statearr_14277[(13)] = inst_14098);
(statearr_14277[(14)] = inst_14099);
(statearr_14277[(16)] = inst_14100);
(statearr_14277[(17)] = inst_14097);
return statearr_14277;
})();var statearr_14278_14343 = state_14216__$1;(statearr_14278_14343[(2)] = null);
(statearr_14278_14343[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (14)))
{var state_14216__$1 = state_14216;var statearr_14279_14344 = state_14216__$1;(statearr_14279_14344[(2)] = null);
(statearr_14279_14344[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (45)))
{var inst_14206 = (state_14216[(2)]);var state_14216__$1 = state_14216;var statearr_14280_14345 = state_14216__$1;(statearr_14280_14345[(2)] = inst_14206);
(statearr_14280_14345[(1)] = (44));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (26)))
{var inst_14148 = (state_14216[(29)]);var inst_14202 = (state_14216[(2)]);var inst_14203 = cljs.core.seq.call(null,inst_14148);var state_14216__$1 = (function (){var statearr_14281 = state_14216;(statearr_14281[(31)] = inst_14202);
return statearr_14281;
})();if(inst_14203)
{var statearr_14282_14346 = state_14216__$1;(statearr_14282_14346[(1)] = (42));
} else
{var statearr_14283_14347 = state_14216__$1;(statearr_14283_14347[(1)] = (43));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (16)))
{var inst_14117 = (state_14216[(7)]);var inst_14119 = cljs.core.chunked_seq_QMARK_.call(null,inst_14117);var state_14216__$1 = state_14216;if(inst_14119)
{var statearr_14284_14348 = state_14216__$1;(statearr_14284_14348[(1)] = (19));
} else
{var statearr_14285_14349 = state_14216__$1;(statearr_14285_14349[(1)] = (20));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (38)))
{var inst_14195 = (state_14216[(2)]);var state_14216__$1 = state_14216;var statearr_14286_14350 = state_14216__$1;(statearr_14286_14350[(2)] = inst_14195);
(statearr_14286_14350[(1)] = (35));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (30)))
{var state_14216__$1 = state_14216;var statearr_14287_14351 = state_14216__$1;(statearr_14287_14351[(2)] = null);
(statearr_14287_14351[(1)] = (32));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (10)))
{var inst_14098 = (state_14216[(13)]);var inst_14100 = (state_14216[(16)]);var inst_14106 = cljs.core._nth.call(null,inst_14098,inst_14100);var inst_14107 = cljs.core.nth.call(null,inst_14106,(0),null);var inst_14108 = cljs.core.nth.call(null,inst_14106,(1),null);var state_14216__$1 = (function (){var statearr_14288 = state_14216;(statearr_14288[(26)] = inst_14107);
return statearr_14288;
})();if(cljs.core.truth_(inst_14108))
{var statearr_14289_14352 = state_14216__$1;(statearr_14289_14352[(1)] = (13));
} else
{var statearr_14290_14353 = state_14216__$1;(statearr_14290_14353[(1)] = (14));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (18)))
{var inst_14141 = (state_14216[(2)]);var state_14216__$1 = state_14216;var statearr_14291_14354 = state_14216__$1;(statearr_14291_14354[(2)] = inst_14141);
(statearr_14291_14354[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (42)))
{var state_14216__$1 = state_14216;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_14216__$1,(45),dchan);
} else
{if((state_val_14217 === (37)))
{var inst_14184 = (state_14216[(23)]);var inst_14088 = (state_14216[(9)]);var inst_14175 = (state_14216[(25)]);var inst_14184__$1 = cljs.core.first.call(null,inst_14175);var inst_14185 = cljs.core.async.put_BANG_.call(null,inst_14184__$1,inst_14088,done);var state_14216__$1 = (function (){var statearr_14292 = state_14216;(statearr_14292[(23)] = inst_14184__$1);
return statearr_14292;
})();if(cljs.core.truth_(inst_14185))
{var statearr_14293_14355 = state_14216__$1;(statearr_14293_14355[(1)] = (39));
} else
{var statearr_14294_14356 = state_14216__$1;(statearr_14294_14356[(1)] = (40));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14217 === (8)))
{var inst_14099 = (state_14216[(14)]);var inst_14100 = (state_14216[(16)]);var inst_14102 = (inst_14100 < inst_14099);var inst_14103 = inst_14102;var state_14216__$1 = state_14216;if(cljs.core.truth_(inst_14103))
{var statearr_14295_14357 = state_14216__$1;(statearr_14295_14357[(1)] = (10));
} else
{var statearr_14296_14358 = state_14216__$1;(statearr_14296_14358[(1)] = (11));
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
});})(c__10224__auto___14304,cs,m,dchan,dctr,done))
;return ((function (switch__10159__auto__,c__10224__auto___14304,cs,m,dchan,dctr,done){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_14300 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_14300[(0)] = state_machine__10160__auto__);
(statearr_14300[(1)] = (1));
return statearr_14300;
});
var state_machine__10160__auto____1 = (function (state_14216){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_14216);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e14301){if((e14301 instanceof Object))
{var ex__10163__auto__ = e14301;var statearr_14302_14359 = state_14216;(statearr_14302_14359[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14216);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14301;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14360 = state_14216;
state_14216 = G__14360;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_14216){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_14216);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___14304,cs,m,dchan,dctr,done))
})();var state__10226__auto__ = (function (){var statearr_14303 = f__10225__auto__.call(null);(statearr_14303[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___14304);
return statearr_14303;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___14304,cs,m,dchan,dctr,done))
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
cljs.core.async.Mix = (function (){var obj14362 = {};return obj14362;
})();
cljs.core.async.admix_STAR_ = (function admix_STAR_(m,ch){if((function (){var and__7296__auto__ = m;if(and__7296__auto__)
{return m.cljs$core$async$Mix$admix_STAR_$arity$2;
} else
{return and__7296__auto__;
}
})())
{return m.cljs$core$async$Mix$admix_STAR_$arity$2(m,ch);
} else
{var x__7935__auto__ = (((m == null))?null:m);return (function (){var or__7308__auto__ = (cljs.core.async.admix_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.admix_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.admix*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.unmix_STAR_ = (function unmix_STAR_(m,ch){if((function (){var and__7296__auto__ = m;if(and__7296__auto__)
{return m.cljs$core$async$Mix$unmix_STAR_$arity$2;
} else
{return and__7296__auto__;
}
})())
{return m.cljs$core$async$Mix$unmix_STAR_$arity$2(m,ch);
} else
{var x__7935__auto__ = (((m == null))?null:m);return (function (){var or__7308__auto__ = (cljs.core.async.unmix_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.unmix_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.unmix*",m);
}
}
})().call(null,m,ch);
}
});
cljs.core.async.unmix_all_STAR_ = (function unmix_all_STAR_(m){if((function (){var and__7296__auto__ = m;if(and__7296__auto__)
{return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1;
} else
{return and__7296__auto__;
}
})())
{return m.cljs$core$async$Mix$unmix_all_STAR_$arity$1(m);
} else
{var x__7935__auto__ = (((m == null))?null:m);return (function (){var or__7308__auto__ = (cljs.core.async.unmix_all_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.unmix_all_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.unmix-all*",m);
}
}
})().call(null,m);
}
});
cljs.core.async.toggle_STAR_ = (function toggle_STAR_(m,state_map){if((function (){var and__7296__auto__ = m;if(and__7296__auto__)
{return m.cljs$core$async$Mix$toggle_STAR_$arity$2;
} else
{return and__7296__auto__;
}
})())
{return m.cljs$core$async$Mix$toggle_STAR_$arity$2(m,state_map);
} else
{var x__7935__auto__ = (((m == null))?null:m);return (function (){var or__7308__auto__ = (cljs.core.async.toggle_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.toggle_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Mix.toggle*",m);
}
}
})().call(null,m,state_map);
}
});
cljs.core.async.solo_mode_STAR_ = (function solo_mode_STAR_(m,mode){if((function (){var and__7296__auto__ = m;if(and__7296__auto__)
{return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2;
} else
{return and__7296__auto__;
}
})())
{return m.cljs$core$async$Mix$solo_mode_STAR_$arity$2(m,mode);
} else
{var x__7935__auto__ = (((m == null))?null:m);return (function (){var or__7308__auto__ = (cljs.core.async.solo_mode_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.solo_mode_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
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
;var m = (function (){if(typeof cljs.core.async.t14482 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t14482 = (function (change,mix,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,meta14483){
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
this.meta14483 = meta14483;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t14482.cljs$lang$type = true;
cljs.core.async.t14482.cljs$lang$ctorStr = "cljs.core.async/t14482";
cljs.core.async.t14482.cljs$lang$ctorPrWriter = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t14482");
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t14482.prototype.cljs$core$async$Mix$ = true;
cljs.core.async.t14482.prototype.cljs$core$async$Mix$admix_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,ch){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.assoc,ch,cljs.core.PersistentArrayMap.EMPTY);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t14482.prototype.cljs$core$async$Mix$unmix_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,ch){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.dissoc,ch);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t14482.prototype.cljs$core$async$Mix$unmix_all_STAR_$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_){var self__ = this;
var ___$1 = this;cljs.core.reset_BANG_.call(null,self__.cs,cljs.core.PersistentArrayMap.EMPTY);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t14482.prototype.cljs$core$async$Mix$toggle_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,state_map){var self__ = this;
var ___$1 = this;cljs.core.swap_BANG_.call(null,self__.cs,cljs.core.partial.call(null,cljs.core.merge_with,cljs.core.merge),state_map);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t14482.prototype.cljs$core$async$Mix$solo_mode_STAR_$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_,mode){var self__ = this;
var ___$1 = this;if(cljs.core.truth_(self__.solo_modes.call(null,mode)))
{} else
{throw (new Error(("Assert failed: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(("mode must be one of: "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(self__.solo_modes)))+"\n"+cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.pr_str.call(null,cljs.core.list(new cljs.core.Symbol(null,"solo-modes","solo-modes",882180540,null),new cljs.core.Symbol(null,"mode","mode",-2000032078,null)))))));
}
cljs.core.reset_BANG_.call(null,self__.solo_mode,mode);
return self__.changed.call(null);
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t14482.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t14482.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_){var self__ = this;
var ___$1 = this;return self__.out;
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t14482.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_14484){var self__ = this;
var _14484__$1 = this;return self__.meta14483;
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.t14482.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function (_14484,meta14483__$1){var self__ = this;
var _14484__$1 = this;return (new cljs.core.async.t14482(self__.change,self__.mix,self__.solo_mode,self__.pick,self__.cs,self__.calc_state,self__.out,self__.changed,self__.solo_modes,self__.attrs,meta14483__$1));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
cljs.core.async.__GT_t14482 = ((function (cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state){
return (function __GT_t14482(change__$1,mix__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta14483){return (new cljs.core.async.t14482(change__$1,mix__$1,solo_mode__$1,pick__$1,cs__$1,calc_state__$1,out__$1,changed__$1,solo_modes__$1,attrs__$1,meta14483));
});})(cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state))
;
}
return (new cljs.core.async.t14482(change,mix,solo_mode,pick,cs,calc_state,out,changed,solo_modes,attrs,null));
})();var c__10224__auto___14601 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___14601,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___14601,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function (state_14554){var state_val_14555 = (state_14554[(1)]);if((state_val_14555 === (7)))
{var inst_14498 = (state_14554[(7)]);var inst_14503 = cljs.core.apply.call(null,cljs.core.hash_map,inst_14498);var state_14554__$1 = state_14554;var statearr_14556_14602 = state_14554__$1;(statearr_14556_14602[(2)] = inst_14503);
(statearr_14556_14602[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (20)))
{var inst_14513 = (state_14554[(8)]);var state_14554__$1 = state_14554;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14554__$1,(23),out,inst_14513);
} else
{if((state_val_14555 === (1)))
{var inst_14488 = (state_14554[(9)]);var inst_14488__$1 = calc_state.call(null);var inst_14489 = cljs.core.seq_QMARK_.call(null,inst_14488__$1);var state_14554__$1 = (function (){var statearr_14557 = state_14554;(statearr_14557[(9)] = inst_14488__$1);
return statearr_14557;
})();if(inst_14489)
{var statearr_14558_14603 = state_14554__$1;(statearr_14558_14603[(1)] = (2));
} else
{var statearr_14559_14604 = state_14554__$1;(statearr_14559_14604[(1)] = (3));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (24)))
{var inst_14506 = (state_14554[(10)]);var inst_14498 = inst_14506;var state_14554__$1 = (function (){var statearr_14560 = state_14554;(statearr_14560[(7)] = inst_14498);
return statearr_14560;
})();var statearr_14561_14605 = state_14554__$1;(statearr_14561_14605[(2)] = null);
(statearr_14561_14605[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (4)))
{var inst_14488 = (state_14554[(9)]);var inst_14494 = (state_14554[(2)]);var inst_14495 = cljs.core.get.call(null,inst_14494,new cljs.core.Keyword(null,"reads","reads",-1215067361));var inst_14496 = cljs.core.get.call(null,inst_14494,new cljs.core.Keyword(null,"mutes","mutes",1068806309));var inst_14497 = cljs.core.get.call(null,inst_14494,new cljs.core.Keyword(null,"solos","solos",1441458643));var inst_14498 = inst_14488;var state_14554__$1 = (function (){var statearr_14562 = state_14554;(statearr_14562[(11)] = inst_14497);
(statearr_14562[(12)] = inst_14496);
(statearr_14562[(7)] = inst_14498);
(statearr_14562[(13)] = inst_14495);
return statearr_14562;
})();var statearr_14563_14606 = state_14554__$1;(statearr_14563_14606[(2)] = null);
(statearr_14563_14606[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (15)))
{var state_14554__$1 = state_14554;var statearr_14564_14607 = state_14554__$1;(statearr_14564_14607[(2)] = null);
(statearr_14564_14607[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (21)))
{var inst_14506 = (state_14554[(10)]);var inst_14498 = inst_14506;var state_14554__$1 = (function (){var statearr_14565 = state_14554;(statearr_14565[(7)] = inst_14498);
return statearr_14565;
})();var statearr_14566_14608 = state_14554__$1;(statearr_14566_14608[(2)] = null);
(statearr_14566_14608[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (13)))
{var inst_14550 = (state_14554[(2)]);var state_14554__$1 = state_14554;var statearr_14567_14609 = state_14554__$1;(statearr_14567_14609[(2)] = inst_14550);
(statearr_14567_14609[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (22)))
{var inst_14548 = (state_14554[(2)]);var state_14554__$1 = state_14554;var statearr_14568_14610 = state_14554__$1;(statearr_14568_14610[(2)] = inst_14548);
(statearr_14568_14610[(1)] = (13));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (6)))
{var inst_14552 = (state_14554[(2)]);var state_14554__$1 = state_14554;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14554__$1,inst_14552);
} else
{if((state_val_14555 === (25)))
{var state_14554__$1 = state_14554;var statearr_14569_14611 = state_14554__$1;(statearr_14569_14611[(2)] = null);
(statearr_14569_14611[(1)] = (26));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (17)))
{var inst_14528 = (state_14554[(14)]);var state_14554__$1 = state_14554;var statearr_14570_14612 = state_14554__$1;(statearr_14570_14612[(2)] = inst_14528);
(statearr_14570_14612[(1)] = (19));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (3)))
{var inst_14488 = (state_14554[(9)]);var state_14554__$1 = state_14554;var statearr_14571_14613 = state_14554__$1;(statearr_14571_14613[(2)] = inst_14488);
(statearr_14571_14613[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (12)))
{var inst_14514 = (state_14554[(15)]);var inst_14528 = (state_14554[(14)]);var inst_14509 = (state_14554[(16)]);var inst_14528__$1 = inst_14509.call(null,inst_14514);var state_14554__$1 = (function (){var statearr_14572 = state_14554;(statearr_14572[(14)] = inst_14528__$1);
return statearr_14572;
})();if(cljs.core.truth_(inst_14528__$1))
{var statearr_14573_14614 = state_14554__$1;(statearr_14573_14614[(1)] = (17));
} else
{var statearr_14574_14615 = state_14554__$1;(statearr_14574_14615[(1)] = (18));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (2)))
{var inst_14488 = (state_14554[(9)]);var inst_14491 = cljs.core.apply.call(null,cljs.core.hash_map,inst_14488);var state_14554__$1 = state_14554;var statearr_14575_14616 = state_14554__$1;(statearr_14575_14616[(2)] = inst_14491);
(statearr_14575_14616[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (23)))
{var inst_14539 = (state_14554[(2)]);var state_14554__$1 = state_14554;if(cljs.core.truth_(inst_14539))
{var statearr_14576_14617 = state_14554__$1;(statearr_14576_14617[(1)] = (24));
} else
{var statearr_14577_14618 = state_14554__$1;(statearr_14577_14618[(1)] = (25));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (19)))
{var inst_14536 = (state_14554[(2)]);var state_14554__$1 = state_14554;if(cljs.core.truth_(inst_14536))
{var statearr_14578_14619 = state_14554__$1;(statearr_14578_14619[(1)] = (20));
} else
{var statearr_14579_14620 = state_14554__$1;(statearr_14579_14620[(1)] = (21));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (11)))
{var inst_14513 = (state_14554[(8)]);var inst_14519 = (inst_14513 == null);var state_14554__$1 = state_14554;if(cljs.core.truth_(inst_14519))
{var statearr_14580_14621 = state_14554__$1;(statearr_14580_14621[(1)] = (14));
} else
{var statearr_14581_14622 = state_14554__$1;(statearr_14581_14622[(1)] = (15));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (9)))
{var inst_14506 = (state_14554[(10)]);var inst_14506__$1 = (state_14554[(2)]);var inst_14507 = cljs.core.get.call(null,inst_14506__$1,new cljs.core.Keyword(null,"reads","reads",-1215067361));var inst_14508 = cljs.core.get.call(null,inst_14506__$1,new cljs.core.Keyword(null,"mutes","mutes",1068806309));var inst_14509 = cljs.core.get.call(null,inst_14506__$1,new cljs.core.Keyword(null,"solos","solos",1441458643));var state_14554__$1 = (function (){var statearr_14582 = state_14554;(statearr_14582[(10)] = inst_14506__$1);
(statearr_14582[(17)] = inst_14508);
(statearr_14582[(16)] = inst_14509);
return statearr_14582;
})();return cljs.core.async.impl.ioc_helpers.ioc_alts_BANG_.call(null,state_14554__$1,(10),inst_14507);
} else
{if((state_val_14555 === (5)))
{var inst_14498 = (state_14554[(7)]);var inst_14501 = cljs.core.seq_QMARK_.call(null,inst_14498);var state_14554__$1 = state_14554;if(inst_14501)
{var statearr_14583_14623 = state_14554__$1;(statearr_14583_14623[(1)] = (7));
} else
{var statearr_14584_14624 = state_14554__$1;(statearr_14584_14624[(1)] = (8));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (14)))
{var inst_14514 = (state_14554[(15)]);var inst_14521 = cljs.core.swap_BANG_.call(null,cs,cljs.core.dissoc,inst_14514);var state_14554__$1 = state_14554;var statearr_14585_14625 = state_14554__$1;(statearr_14585_14625[(2)] = inst_14521);
(statearr_14585_14625[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (26)))
{var inst_14544 = (state_14554[(2)]);var state_14554__$1 = state_14554;var statearr_14586_14626 = state_14554__$1;(statearr_14586_14626[(2)] = inst_14544);
(statearr_14586_14626[(1)] = (22));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (16)))
{var inst_14524 = (state_14554[(2)]);var inst_14525 = calc_state.call(null);var inst_14498 = inst_14525;var state_14554__$1 = (function (){var statearr_14587 = state_14554;(statearr_14587[(18)] = inst_14524);
(statearr_14587[(7)] = inst_14498);
return statearr_14587;
})();var statearr_14588_14627 = state_14554__$1;(statearr_14588_14627[(2)] = null);
(statearr_14588_14627[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (10)))
{var inst_14514 = (state_14554[(15)]);var inst_14513 = (state_14554[(8)]);var inst_14512 = (state_14554[(2)]);var inst_14513__$1 = cljs.core.nth.call(null,inst_14512,(0),null);var inst_14514__$1 = cljs.core.nth.call(null,inst_14512,(1),null);var inst_14515 = (inst_14513__$1 == null);var inst_14516 = cljs.core._EQ_.call(null,inst_14514__$1,change);var inst_14517 = (inst_14515) || (inst_14516);var state_14554__$1 = (function (){var statearr_14589 = state_14554;(statearr_14589[(15)] = inst_14514__$1);
(statearr_14589[(8)] = inst_14513__$1);
return statearr_14589;
})();if(cljs.core.truth_(inst_14517))
{var statearr_14590_14628 = state_14554__$1;(statearr_14590_14628[(1)] = (11));
} else
{var statearr_14591_14629 = state_14554__$1;(statearr_14591_14629[(1)] = (12));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (18)))
{var inst_14514 = (state_14554[(15)]);var inst_14508 = (state_14554[(17)]);var inst_14509 = (state_14554[(16)]);var inst_14531 = cljs.core.empty_QMARK_.call(null,inst_14509);var inst_14532 = inst_14508.call(null,inst_14514);var inst_14533 = cljs.core.not.call(null,inst_14532);var inst_14534 = (inst_14531) && (inst_14533);var state_14554__$1 = state_14554;var statearr_14592_14630 = state_14554__$1;(statearr_14592_14630[(2)] = inst_14534);
(statearr_14592_14630[(1)] = (19));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14555 === (8)))
{var inst_14498 = (state_14554[(7)]);var state_14554__$1 = state_14554;var statearr_14593_14631 = state_14554__$1;(statearr_14593_14631[(2)] = inst_14498);
(statearr_14593_14631[(1)] = (9));
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
});})(c__10224__auto___14601,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
;return ((function (switch__10159__auto__,c__10224__auto___14601,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_14597 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_14597[(0)] = state_machine__10160__auto__);
(statearr_14597[(1)] = (1));
return statearr_14597;
});
var state_machine__10160__auto____1 = (function (state_14554){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_14554);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e14598){if((e14598 instanceof Object))
{var ex__10163__auto__ = e14598;var statearr_14599_14632 = state_14554;(statearr_14599_14632[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14554);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14598;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14633 = state_14554;
state_14554 = G__14633;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_14554){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_14554);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___14601,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
})();var state__10226__auto__ = (function (){var statearr_14600 = f__10225__auto__.call(null);(statearr_14600[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___14601);
return statearr_14600;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___14601,cs,solo_modes,attrs,solo_mode,change,changed,pick,calc_state,m))
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
cljs.core.async.Pub = (function (){var obj14635 = {};return obj14635;
})();
cljs.core.async.sub_STAR_ = (function sub_STAR_(p,v,ch,close_QMARK_){if((function (){var and__7296__auto__ = p;if(and__7296__auto__)
{return p.cljs$core$async$Pub$sub_STAR_$arity$4;
} else
{return and__7296__auto__;
}
})())
{return p.cljs$core$async$Pub$sub_STAR_$arity$4(p,v,ch,close_QMARK_);
} else
{var x__7935__auto__ = (((p == null))?null:p);return (function (){var or__7308__auto__ = (cljs.core.async.sub_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.sub_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.sub*",p);
}
}
})().call(null,p,v,ch,close_QMARK_);
}
});
cljs.core.async.unsub_STAR_ = (function unsub_STAR_(p,v,ch){if((function (){var and__7296__auto__ = p;if(and__7296__auto__)
{return p.cljs$core$async$Pub$unsub_STAR_$arity$3;
} else
{return and__7296__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_STAR_$arity$3(p,v,ch);
} else
{var x__7935__auto__ = (((p == null))?null:p);return (function (){var or__7308__auto__ = (cljs.core.async.unsub_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.unsub_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.unsub*",p);
}
}
})().call(null,p,v,ch);
}
});
cljs.core.async.unsub_all_STAR_ = (function() {
var unsub_all_STAR_ = null;
var unsub_all_STAR___1 = (function (p){if((function (){var and__7296__auto__ = p;if(and__7296__auto__)
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$1;
} else
{return and__7296__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$1(p);
} else
{var x__7935__auto__ = (((p == null))?null:p);return (function (){var or__7308__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.unsub_all_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"Pub.unsub-all*",p);
}
}
})().call(null,p);
}
});
var unsub_all_STAR___2 = (function (p,v){if((function (){var and__7296__auto__ = p;if(and__7296__auto__)
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2;
} else
{return and__7296__auto__;
}
})())
{return p.cljs$core$async$Pub$unsub_all_STAR_$arity$2(p,v);
} else
{var x__7935__auto__ = (((p == null))?null:p);return (function (){var or__7308__auto__ = (cljs.core.async.unsub_all_STAR_[goog.typeOf(x__7935__auto__)]);if(or__7308__auto__)
{return or__7308__auto__;
} else
{var or__7308__auto____$1 = (cljs.core.async.unsub_all_STAR_["_"]);if(or__7308__auto____$1)
{return or__7308__auto____$1;
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
return (function (topic){var or__7308__auto__ = cljs.core.get.call(null,cljs.core.deref.call(null,mults),topic);if(cljs.core.truth_(or__7308__auto__))
{return or__7308__auto__;
} else
{return cljs.core.get.call(null,cljs.core.swap_BANG_.call(null,mults,((function (or__7308__auto__,mults){
return (function (p1__14636_SHARP_){if(cljs.core.truth_(p1__14636_SHARP_.call(null,topic)))
{return p1__14636_SHARP_;
} else
{return cljs.core.assoc.call(null,p1__14636_SHARP_,topic,cljs.core.async.mult.call(null,cljs.core.async.chan.call(null,buf_fn.call(null,topic))));
}
});})(or__7308__auto__,mults))
),topic);
}
});})(mults))
;var p = (function (){if(typeof cljs.core.async.t14759 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t14759 = (function (ensure_mult,mults,buf_fn,topic_fn,ch,pub,meta14760){
this.ensure_mult = ensure_mult;
this.mults = mults;
this.buf_fn = buf_fn;
this.topic_fn = topic_fn;
this.ch = ch;
this.pub = pub;
this.meta14760 = meta14760;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t14759.cljs$lang$type = true;
cljs.core.async.t14759.cljs$lang$ctorStr = "cljs.core.async/t14759";
cljs.core.async.t14759.cljs$lang$ctorPrWriter = ((function (mults,ensure_mult){
return (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t14759");
});})(mults,ensure_mult))
;
cljs.core.async.t14759.prototype.cljs$core$async$Pub$ = true;
cljs.core.async.t14759.prototype.cljs$core$async$Pub$sub_STAR_$arity$4 = ((function (mults,ensure_mult){
return (function (p,topic,ch__$2,close_QMARK_){var self__ = this;
var p__$1 = this;var m = self__.ensure_mult.call(null,topic);return cljs.core.async.tap.call(null,m,ch__$2,close_QMARK_);
});})(mults,ensure_mult))
;
cljs.core.async.t14759.prototype.cljs$core$async$Pub$unsub_STAR_$arity$3 = ((function (mults,ensure_mult){
return (function (p,topic,ch__$2){var self__ = this;
var p__$1 = this;var temp__4126__auto__ = cljs.core.get.call(null,cljs.core.deref.call(null,self__.mults),topic);if(cljs.core.truth_(temp__4126__auto__))
{var m = temp__4126__auto__;return cljs.core.async.untap.call(null,m,ch__$2);
} else
{return null;
}
});})(mults,ensure_mult))
;
cljs.core.async.t14759.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$1 = ((function (mults,ensure_mult){
return (function (_){var self__ = this;
var ___$1 = this;return cljs.core.reset_BANG_.call(null,self__.mults,cljs.core.PersistentArrayMap.EMPTY);
});})(mults,ensure_mult))
;
cljs.core.async.t14759.prototype.cljs$core$async$Pub$unsub_all_STAR_$arity$2 = ((function (mults,ensure_mult){
return (function (_,topic){var self__ = this;
var ___$1 = this;return cljs.core.swap_BANG_.call(null,self__.mults,cljs.core.dissoc,topic);
});})(mults,ensure_mult))
;
cljs.core.async.t14759.prototype.cljs$core$async$Mux$ = true;
cljs.core.async.t14759.prototype.cljs$core$async$Mux$muxch_STAR_$arity$1 = ((function (mults,ensure_mult){
return (function (_){var self__ = this;
var ___$1 = this;return self__.ch;
});})(mults,ensure_mult))
;
cljs.core.async.t14759.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (mults,ensure_mult){
return (function (_14761){var self__ = this;
var _14761__$1 = this;return self__.meta14760;
});})(mults,ensure_mult))
;
cljs.core.async.t14759.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (mults,ensure_mult){
return (function (_14761,meta14760__$1){var self__ = this;
var _14761__$1 = this;return (new cljs.core.async.t14759(self__.ensure_mult,self__.mults,self__.buf_fn,self__.topic_fn,self__.ch,self__.pub,meta14760__$1));
});})(mults,ensure_mult))
;
cljs.core.async.__GT_t14759 = ((function (mults,ensure_mult){
return (function __GT_t14759(ensure_mult__$1,mults__$1,buf_fn__$1,topic_fn__$1,ch__$1,pub__$1,meta14760){return (new cljs.core.async.t14759(ensure_mult__$1,mults__$1,buf_fn__$1,topic_fn__$1,ch__$1,pub__$1,meta14760));
});})(mults,ensure_mult))
;
}
return (new cljs.core.async.t14759(ensure_mult,mults,buf_fn,topic_fn,ch,pub,null));
})();var c__10224__auto___14881 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___14881,mults,ensure_mult,p){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___14881,mults,ensure_mult,p){
return (function (state_14833){var state_val_14834 = (state_14833[(1)]);if((state_val_14834 === (7)))
{var inst_14829 = (state_14833[(2)]);var state_14833__$1 = state_14833;var statearr_14835_14882 = state_14833__$1;(statearr_14835_14882[(2)] = inst_14829);
(statearr_14835_14882[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (20)))
{var state_14833__$1 = state_14833;var statearr_14836_14883 = state_14833__$1;(statearr_14836_14883[(2)] = null);
(statearr_14836_14883[(1)] = (21));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (1)))
{var state_14833__$1 = state_14833;var statearr_14837_14884 = state_14833__$1;(statearr_14837_14884[(2)] = null);
(statearr_14837_14884[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (24)))
{var inst_14812 = (state_14833[(7)]);var inst_14821 = cljs.core.swap_BANG_.call(null,mults,cljs.core.dissoc,inst_14812);var state_14833__$1 = state_14833;var statearr_14838_14885 = state_14833__$1;(statearr_14838_14885[(2)] = inst_14821);
(statearr_14838_14885[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (4)))
{var inst_14764 = (state_14833[(8)]);var inst_14764__$1 = (state_14833[(2)]);var inst_14765 = (inst_14764__$1 == null);var state_14833__$1 = (function (){var statearr_14839 = state_14833;(statearr_14839[(8)] = inst_14764__$1);
return statearr_14839;
})();if(cljs.core.truth_(inst_14765))
{var statearr_14840_14886 = state_14833__$1;(statearr_14840_14886[(1)] = (5));
} else
{var statearr_14841_14887 = state_14833__$1;(statearr_14841_14887[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (15)))
{var inst_14806 = (state_14833[(2)]);var state_14833__$1 = state_14833;var statearr_14842_14888 = state_14833__$1;(statearr_14842_14888[(2)] = inst_14806);
(statearr_14842_14888[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (21)))
{var inst_14826 = (state_14833[(2)]);var state_14833__$1 = (function (){var statearr_14843 = state_14833;(statearr_14843[(9)] = inst_14826);
return statearr_14843;
})();var statearr_14844_14889 = state_14833__$1;(statearr_14844_14889[(2)] = null);
(statearr_14844_14889[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (13)))
{var inst_14788 = (state_14833[(10)]);var inst_14790 = cljs.core.chunked_seq_QMARK_.call(null,inst_14788);var state_14833__$1 = state_14833;if(inst_14790)
{var statearr_14845_14890 = state_14833__$1;(statearr_14845_14890[(1)] = (16));
} else
{var statearr_14846_14891 = state_14833__$1;(statearr_14846_14891[(1)] = (17));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (22)))
{var inst_14818 = (state_14833[(2)]);var state_14833__$1 = state_14833;if(cljs.core.truth_(inst_14818))
{var statearr_14847_14892 = state_14833__$1;(statearr_14847_14892[(1)] = (23));
} else
{var statearr_14848_14893 = state_14833__$1;(statearr_14848_14893[(1)] = (24));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (6)))
{var inst_14814 = (state_14833[(11)]);var inst_14812 = (state_14833[(7)]);var inst_14764 = (state_14833[(8)]);var inst_14812__$1 = topic_fn.call(null,inst_14764);var inst_14813 = cljs.core.deref.call(null,mults);var inst_14814__$1 = cljs.core.get.call(null,inst_14813,inst_14812__$1);var state_14833__$1 = (function (){var statearr_14849 = state_14833;(statearr_14849[(11)] = inst_14814__$1);
(statearr_14849[(7)] = inst_14812__$1);
return statearr_14849;
})();if(cljs.core.truth_(inst_14814__$1))
{var statearr_14850_14894 = state_14833__$1;(statearr_14850_14894[(1)] = (19));
} else
{var statearr_14851_14895 = state_14833__$1;(statearr_14851_14895[(1)] = (20));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (25)))
{var inst_14823 = (state_14833[(2)]);var state_14833__$1 = state_14833;var statearr_14852_14896 = state_14833__$1;(statearr_14852_14896[(2)] = inst_14823);
(statearr_14852_14896[(1)] = (21));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (17)))
{var inst_14788 = (state_14833[(10)]);var inst_14797 = cljs.core.first.call(null,inst_14788);var inst_14798 = cljs.core.async.muxch_STAR_.call(null,inst_14797);var inst_14799 = cljs.core.async.close_BANG_.call(null,inst_14798);var inst_14800 = cljs.core.next.call(null,inst_14788);var inst_14774 = inst_14800;var inst_14775 = null;var inst_14776 = (0);var inst_14777 = (0);var state_14833__$1 = (function (){var statearr_14853 = state_14833;(statearr_14853[(12)] = inst_14777);
(statearr_14853[(13)] = inst_14774);
(statearr_14853[(14)] = inst_14776);
(statearr_14853[(15)] = inst_14775);
(statearr_14853[(16)] = inst_14799);
return statearr_14853;
})();var statearr_14854_14897 = state_14833__$1;(statearr_14854_14897[(2)] = null);
(statearr_14854_14897[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (3)))
{var inst_14831 = (state_14833[(2)]);var state_14833__$1 = state_14833;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_14833__$1,inst_14831);
} else
{if((state_val_14834 === (12)))
{var inst_14808 = (state_14833[(2)]);var state_14833__$1 = state_14833;var statearr_14855_14898 = state_14833__$1;(statearr_14855_14898[(2)] = inst_14808);
(statearr_14855_14898[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (2)))
{var state_14833__$1 = state_14833;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_14833__$1,(4),ch);
} else
{if((state_val_14834 === (23)))
{var state_14833__$1 = state_14833;var statearr_14856_14899 = state_14833__$1;(statearr_14856_14899[(2)] = null);
(statearr_14856_14899[(1)] = (25));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (19)))
{var inst_14814 = (state_14833[(11)]);var inst_14764 = (state_14833[(8)]);var inst_14816 = cljs.core.async.muxch_STAR_.call(null,inst_14814);var state_14833__$1 = state_14833;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_14833__$1,(22),inst_14816,inst_14764);
} else
{if((state_val_14834 === (11)))
{var inst_14774 = (state_14833[(13)]);var inst_14788 = (state_14833[(10)]);var inst_14788__$1 = cljs.core.seq.call(null,inst_14774);var state_14833__$1 = (function (){var statearr_14857 = state_14833;(statearr_14857[(10)] = inst_14788__$1);
return statearr_14857;
})();if(inst_14788__$1)
{var statearr_14858_14900 = state_14833__$1;(statearr_14858_14900[(1)] = (13));
} else
{var statearr_14859_14901 = state_14833__$1;(statearr_14859_14901[(1)] = (14));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (9)))
{var inst_14810 = (state_14833[(2)]);var state_14833__$1 = state_14833;var statearr_14860_14902 = state_14833__$1;(statearr_14860_14902[(2)] = inst_14810);
(statearr_14860_14902[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (5)))
{var inst_14771 = cljs.core.deref.call(null,mults);var inst_14772 = cljs.core.vals.call(null,inst_14771);var inst_14773 = cljs.core.seq.call(null,inst_14772);var inst_14774 = inst_14773;var inst_14775 = null;var inst_14776 = (0);var inst_14777 = (0);var state_14833__$1 = (function (){var statearr_14861 = state_14833;(statearr_14861[(12)] = inst_14777);
(statearr_14861[(13)] = inst_14774);
(statearr_14861[(14)] = inst_14776);
(statearr_14861[(15)] = inst_14775);
return statearr_14861;
})();var statearr_14862_14903 = state_14833__$1;(statearr_14862_14903[(2)] = null);
(statearr_14862_14903[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (14)))
{var state_14833__$1 = state_14833;var statearr_14866_14904 = state_14833__$1;(statearr_14866_14904[(2)] = null);
(statearr_14866_14904[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (16)))
{var inst_14788 = (state_14833[(10)]);var inst_14792 = cljs.core.chunk_first.call(null,inst_14788);var inst_14793 = cljs.core.chunk_rest.call(null,inst_14788);var inst_14794 = cljs.core.count.call(null,inst_14792);var inst_14774 = inst_14793;var inst_14775 = inst_14792;var inst_14776 = inst_14794;var inst_14777 = (0);var state_14833__$1 = (function (){var statearr_14867 = state_14833;(statearr_14867[(12)] = inst_14777);
(statearr_14867[(13)] = inst_14774);
(statearr_14867[(14)] = inst_14776);
(statearr_14867[(15)] = inst_14775);
return statearr_14867;
})();var statearr_14868_14905 = state_14833__$1;(statearr_14868_14905[(2)] = null);
(statearr_14868_14905[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (10)))
{var inst_14777 = (state_14833[(12)]);var inst_14774 = (state_14833[(13)]);var inst_14776 = (state_14833[(14)]);var inst_14775 = (state_14833[(15)]);var inst_14782 = cljs.core._nth.call(null,inst_14775,inst_14777);var inst_14783 = cljs.core.async.muxch_STAR_.call(null,inst_14782);var inst_14784 = cljs.core.async.close_BANG_.call(null,inst_14783);var inst_14785 = (inst_14777 + (1));var tmp14863 = inst_14774;var tmp14864 = inst_14776;var tmp14865 = inst_14775;var inst_14774__$1 = tmp14863;var inst_14775__$1 = tmp14865;var inst_14776__$1 = tmp14864;var inst_14777__$1 = inst_14785;var state_14833__$1 = (function (){var statearr_14869 = state_14833;(statearr_14869[(12)] = inst_14777__$1);
(statearr_14869[(13)] = inst_14774__$1);
(statearr_14869[(14)] = inst_14776__$1);
(statearr_14869[(15)] = inst_14775__$1);
(statearr_14869[(17)] = inst_14784);
return statearr_14869;
})();var statearr_14870_14906 = state_14833__$1;(statearr_14870_14906[(2)] = null);
(statearr_14870_14906[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (18)))
{var inst_14803 = (state_14833[(2)]);var state_14833__$1 = state_14833;var statearr_14871_14907 = state_14833__$1;(statearr_14871_14907[(2)] = inst_14803);
(statearr_14871_14907[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_14834 === (8)))
{var inst_14777 = (state_14833[(12)]);var inst_14776 = (state_14833[(14)]);var inst_14779 = (inst_14777 < inst_14776);var inst_14780 = inst_14779;var state_14833__$1 = state_14833;if(cljs.core.truth_(inst_14780))
{var statearr_14872_14908 = state_14833__$1;(statearr_14872_14908[(1)] = (10));
} else
{var statearr_14873_14909 = state_14833__$1;(statearr_14873_14909[(1)] = (11));
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
});})(c__10224__auto___14881,mults,ensure_mult,p))
;return ((function (switch__10159__auto__,c__10224__auto___14881,mults,ensure_mult,p){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_14877 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_14877[(0)] = state_machine__10160__auto__);
(statearr_14877[(1)] = (1));
return statearr_14877;
});
var state_machine__10160__auto____1 = (function (state_14833){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_14833);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e14878){if((e14878 instanceof Object))
{var ex__10163__auto__ = e14878;var statearr_14879_14910 = state_14833;(statearr_14879_14910[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_14833);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e14878;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__14911 = state_14833;
state_14833 = G__14911;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_14833){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_14833);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___14881,mults,ensure_mult,p))
})();var state__10226__auto__ = (function (){var statearr_14880 = f__10225__auto__.call(null);(statearr_14880[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___14881);
return statearr_14880;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___14881,mults,ensure_mult,p))
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
,cljs.core.range.call(null,cnt));var c__10224__auto___15048 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___15048,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___15048,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function (state_15018){var state_val_15019 = (state_15018[(1)]);if((state_val_15019 === (7)))
{var state_15018__$1 = state_15018;var statearr_15020_15049 = state_15018__$1;(statearr_15020_15049[(2)] = null);
(statearr_15020_15049[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (1)))
{var state_15018__$1 = state_15018;var statearr_15021_15050 = state_15018__$1;(statearr_15021_15050[(2)] = null);
(statearr_15021_15050[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (4)))
{var inst_14982 = (state_15018[(7)]);var inst_14984 = (inst_14982 < cnt);var state_15018__$1 = state_15018;if(cljs.core.truth_(inst_14984))
{var statearr_15022_15051 = state_15018__$1;(statearr_15022_15051[(1)] = (6));
} else
{var statearr_15023_15052 = state_15018__$1;(statearr_15023_15052[(1)] = (7));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (15)))
{var inst_15014 = (state_15018[(2)]);var state_15018__$1 = state_15018;var statearr_15024_15053 = state_15018__$1;(statearr_15024_15053[(2)] = inst_15014);
(statearr_15024_15053[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (13)))
{var inst_15007 = cljs.core.async.close_BANG_.call(null,out);var state_15018__$1 = state_15018;var statearr_15025_15054 = state_15018__$1;(statearr_15025_15054[(2)] = inst_15007);
(statearr_15025_15054[(1)] = (15));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (6)))
{var state_15018__$1 = state_15018;var statearr_15026_15055 = state_15018__$1;(statearr_15026_15055[(2)] = null);
(statearr_15026_15055[(1)] = (11));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (3)))
{var inst_15016 = (state_15018[(2)]);var state_15018__$1 = state_15018;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_15018__$1,inst_15016);
} else
{if((state_val_15019 === (12)))
{var inst_15004 = (state_15018[(8)]);var inst_15004__$1 = (state_15018[(2)]);var inst_15005 = cljs.core.some.call(null,cljs.core.nil_QMARK_,inst_15004__$1);var state_15018__$1 = (function (){var statearr_15027 = state_15018;(statearr_15027[(8)] = inst_15004__$1);
return statearr_15027;
})();if(cljs.core.truth_(inst_15005))
{var statearr_15028_15056 = state_15018__$1;(statearr_15028_15056[(1)] = (13));
} else
{var statearr_15029_15057 = state_15018__$1;(statearr_15029_15057[(1)] = (14));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (2)))
{var inst_14981 = cljs.core.reset_BANG_.call(null,dctr,cnt);var inst_14982 = (0);var state_15018__$1 = (function (){var statearr_15030 = state_15018;(statearr_15030[(7)] = inst_14982);
(statearr_15030[(9)] = inst_14981);
return statearr_15030;
})();var statearr_15031_15058 = state_15018__$1;(statearr_15031_15058[(2)] = null);
(statearr_15031_15058[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (11)))
{var inst_14982 = (state_15018[(7)]);var _ = cljs.core.async.impl.ioc_helpers.add_exception_frame.call(null,state_15018,(10),Object,null,(9));var inst_14991 = chs__$1.call(null,inst_14982);var inst_14992 = done.call(null,inst_14982);var inst_14993 = cljs.core.async.take_BANG_.call(null,inst_14991,inst_14992);var state_15018__$1 = state_15018;var statearr_15032_15059 = state_15018__$1;(statearr_15032_15059[(2)] = inst_14993);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_15018__$1);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (9)))
{var inst_14982 = (state_15018[(7)]);var inst_14995 = (state_15018[(2)]);var inst_14996 = (inst_14982 + (1));var inst_14982__$1 = inst_14996;var state_15018__$1 = (function (){var statearr_15033 = state_15018;(statearr_15033[(10)] = inst_14995);
(statearr_15033[(7)] = inst_14982__$1);
return statearr_15033;
})();var statearr_15034_15060 = state_15018__$1;(statearr_15034_15060[(2)] = null);
(statearr_15034_15060[(1)] = (4));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (5)))
{var inst_15002 = (state_15018[(2)]);var state_15018__$1 = (function (){var statearr_15035 = state_15018;(statearr_15035[(11)] = inst_15002);
return statearr_15035;
})();return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_15018__$1,(12),dchan);
} else
{if((state_val_15019 === (14)))
{var inst_15004 = (state_15018[(8)]);var inst_15009 = cljs.core.apply.call(null,f,inst_15004);var state_15018__$1 = state_15018;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_15018__$1,(16),out,inst_15009);
} else
{if((state_val_15019 === (16)))
{var inst_15011 = (state_15018[(2)]);var state_15018__$1 = (function (){var statearr_15036 = state_15018;(statearr_15036[(12)] = inst_15011);
return statearr_15036;
})();var statearr_15037_15061 = state_15018__$1;(statearr_15037_15061[(2)] = null);
(statearr_15037_15061[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (10)))
{var inst_14986 = (state_15018[(2)]);var inst_14987 = cljs.core.swap_BANG_.call(null,dctr,cljs.core.dec);var state_15018__$1 = (function (){var statearr_15038 = state_15018;(statearr_15038[(13)] = inst_14986);
return statearr_15038;
})();var statearr_15039_15062 = state_15018__$1;(statearr_15039_15062[(2)] = inst_14987);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_15018__$1);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15019 === (8)))
{var inst_15000 = (state_15018[(2)]);var state_15018__$1 = state_15018;var statearr_15040_15063 = state_15018__$1;(statearr_15040_15063[(2)] = inst_15000);
(statearr_15040_15063[(1)] = (5));
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
});})(c__10224__auto___15048,chs__$1,out,cnt,rets,dchan,dctr,done))
;return ((function (switch__10159__auto__,c__10224__auto___15048,chs__$1,out,cnt,rets,dchan,dctr,done){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_15044 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_15044[(0)] = state_machine__10160__auto__);
(statearr_15044[(1)] = (1));
return statearr_15044;
});
var state_machine__10160__auto____1 = (function (state_15018){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_15018);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e15045){if((e15045 instanceof Object))
{var ex__10163__auto__ = e15045;var statearr_15046_15064 = state_15018;(statearr_15046_15064[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_15018);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e15045;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__15065 = state_15018;
state_15018 = G__15065;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_15018){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_15018);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___15048,chs__$1,out,cnt,rets,dchan,dctr,done))
})();var state__10226__auto__ = (function (){var statearr_15047 = f__10225__auto__.call(null);(statearr_15047[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___15048);
return statearr_15047;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___15048,chs__$1,out,cnt,rets,dchan,dctr,done))
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
var merge__2 = (function (chs,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__10224__auto___15173 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___15173,out){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___15173,out){
return (function (state_15149){var state_val_15150 = (state_15149[(1)]);if((state_val_15150 === (7)))
{var inst_15129 = (state_15149[(7)]);var inst_15128 = (state_15149[(8)]);var inst_15128__$1 = (state_15149[(2)]);var inst_15129__$1 = cljs.core.nth.call(null,inst_15128__$1,(0),null);var inst_15130 = cljs.core.nth.call(null,inst_15128__$1,(1),null);var inst_15131 = (inst_15129__$1 == null);var state_15149__$1 = (function (){var statearr_15151 = state_15149;(statearr_15151[(7)] = inst_15129__$1);
(statearr_15151[(8)] = inst_15128__$1);
(statearr_15151[(9)] = inst_15130);
return statearr_15151;
})();if(cljs.core.truth_(inst_15131))
{var statearr_15152_15174 = state_15149__$1;(statearr_15152_15174[(1)] = (8));
} else
{var statearr_15153_15175 = state_15149__$1;(statearr_15153_15175[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15150 === (1)))
{var inst_15120 = cljs.core.vec.call(null,chs);var inst_15121 = inst_15120;var state_15149__$1 = (function (){var statearr_15154 = state_15149;(statearr_15154[(10)] = inst_15121);
return statearr_15154;
})();var statearr_15155_15176 = state_15149__$1;(statearr_15155_15176[(2)] = null);
(statearr_15155_15176[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15150 === (4)))
{var inst_15121 = (state_15149[(10)]);var state_15149__$1 = state_15149;return cljs.core.async.impl.ioc_helpers.ioc_alts_BANG_.call(null,state_15149__$1,(7),inst_15121);
} else
{if((state_val_15150 === (6)))
{var inst_15145 = (state_15149[(2)]);var state_15149__$1 = state_15149;var statearr_15156_15177 = state_15149__$1;(statearr_15156_15177[(2)] = inst_15145);
(statearr_15156_15177[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15150 === (3)))
{var inst_15147 = (state_15149[(2)]);var state_15149__$1 = state_15149;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_15149__$1,inst_15147);
} else
{if((state_val_15150 === (2)))
{var inst_15121 = (state_15149[(10)]);var inst_15123 = cljs.core.count.call(null,inst_15121);var inst_15124 = (inst_15123 > (0));var state_15149__$1 = state_15149;if(cljs.core.truth_(inst_15124))
{var statearr_15158_15178 = state_15149__$1;(statearr_15158_15178[(1)] = (4));
} else
{var statearr_15159_15179 = state_15149__$1;(statearr_15159_15179[(1)] = (5));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15150 === (11)))
{var inst_15121 = (state_15149[(10)]);var inst_15138 = (state_15149[(2)]);var tmp15157 = inst_15121;var inst_15121__$1 = tmp15157;var state_15149__$1 = (function (){var statearr_15160 = state_15149;(statearr_15160[(11)] = inst_15138);
(statearr_15160[(10)] = inst_15121__$1);
return statearr_15160;
})();var statearr_15161_15180 = state_15149__$1;(statearr_15161_15180[(2)] = null);
(statearr_15161_15180[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15150 === (9)))
{var inst_15129 = (state_15149[(7)]);var state_15149__$1 = state_15149;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_15149__$1,(11),out,inst_15129);
} else
{if((state_val_15150 === (5)))
{var inst_15143 = cljs.core.async.close_BANG_.call(null,out);var state_15149__$1 = state_15149;var statearr_15162_15181 = state_15149__$1;(statearr_15162_15181[(2)] = inst_15143);
(statearr_15162_15181[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15150 === (10)))
{var inst_15141 = (state_15149[(2)]);var state_15149__$1 = state_15149;var statearr_15163_15182 = state_15149__$1;(statearr_15163_15182[(2)] = inst_15141);
(statearr_15163_15182[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15150 === (8)))
{var inst_15121 = (state_15149[(10)]);var inst_15129 = (state_15149[(7)]);var inst_15128 = (state_15149[(8)]);var inst_15130 = (state_15149[(9)]);var inst_15133 = (function (){var c = inst_15130;var v = inst_15129;var vec__15126 = inst_15128;var cs = inst_15121;return ((function (c,v,vec__15126,cs,inst_15121,inst_15129,inst_15128,inst_15130,state_val_15150,c__10224__auto___15173,out){
return (function (p1__15066_SHARP_){return cljs.core.not_EQ_.call(null,c,p1__15066_SHARP_);
});
;})(c,v,vec__15126,cs,inst_15121,inst_15129,inst_15128,inst_15130,state_val_15150,c__10224__auto___15173,out))
})();var inst_15134 = cljs.core.filterv.call(null,inst_15133,inst_15121);var inst_15121__$1 = inst_15134;var state_15149__$1 = (function (){var statearr_15164 = state_15149;(statearr_15164[(10)] = inst_15121__$1);
return statearr_15164;
})();var statearr_15165_15183 = state_15149__$1;(statearr_15165_15183[(2)] = null);
(statearr_15165_15183[(1)] = (2));
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
});})(c__10224__auto___15173,out))
;return ((function (switch__10159__auto__,c__10224__auto___15173,out){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_15169 = [null,null,null,null,null,null,null,null,null,null,null,null];(statearr_15169[(0)] = state_machine__10160__auto__);
(statearr_15169[(1)] = (1));
return statearr_15169;
});
var state_machine__10160__auto____1 = (function (state_15149){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_15149);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e15170){if((e15170 instanceof Object))
{var ex__10163__auto__ = e15170;var statearr_15171_15184 = state_15149;(statearr_15171_15184[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_15149);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e15170;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__15185 = state_15149;
state_15149 = G__15185;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_15149){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_15149);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___15173,out))
})();var state__10226__auto__ = (function (){var statearr_15172 = f__10225__auto__.call(null);(statearr_15172[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___15173);
return statearr_15172;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___15173,out))
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
var take__3 = (function (n,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__10224__auto___15278 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___15278,out){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___15278,out){
return (function (state_15255){var state_val_15256 = (state_15255[(1)]);if((state_val_15256 === (7)))
{var inst_15237 = (state_15255[(7)]);var inst_15237__$1 = (state_15255[(2)]);var inst_15238 = (inst_15237__$1 == null);var inst_15239 = cljs.core.not.call(null,inst_15238);var state_15255__$1 = (function (){var statearr_15257 = state_15255;(statearr_15257[(7)] = inst_15237__$1);
return statearr_15257;
})();if(inst_15239)
{var statearr_15258_15279 = state_15255__$1;(statearr_15258_15279[(1)] = (8));
} else
{var statearr_15259_15280 = state_15255__$1;(statearr_15259_15280[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15256 === (1)))
{var inst_15232 = (0);var state_15255__$1 = (function (){var statearr_15260 = state_15255;(statearr_15260[(8)] = inst_15232);
return statearr_15260;
})();var statearr_15261_15281 = state_15255__$1;(statearr_15261_15281[(2)] = null);
(statearr_15261_15281[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15256 === (4)))
{var state_15255__$1 = state_15255;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_15255__$1,(7),ch);
} else
{if((state_val_15256 === (6)))
{var inst_15250 = (state_15255[(2)]);var state_15255__$1 = state_15255;var statearr_15262_15282 = state_15255__$1;(statearr_15262_15282[(2)] = inst_15250);
(statearr_15262_15282[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15256 === (3)))
{var inst_15252 = (state_15255[(2)]);var inst_15253 = cljs.core.async.close_BANG_.call(null,out);var state_15255__$1 = (function (){var statearr_15263 = state_15255;(statearr_15263[(9)] = inst_15252);
return statearr_15263;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_15255__$1,inst_15253);
} else
{if((state_val_15256 === (2)))
{var inst_15232 = (state_15255[(8)]);var inst_15234 = (inst_15232 < n);var state_15255__$1 = state_15255;if(cljs.core.truth_(inst_15234))
{var statearr_15264_15283 = state_15255__$1;(statearr_15264_15283[(1)] = (4));
} else
{var statearr_15265_15284 = state_15255__$1;(statearr_15265_15284[(1)] = (5));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15256 === (11)))
{var inst_15232 = (state_15255[(8)]);var inst_15242 = (state_15255[(2)]);var inst_15243 = (inst_15232 + (1));var inst_15232__$1 = inst_15243;var state_15255__$1 = (function (){var statearr_15266 = state_15255;(statearr_15266[(10)] = inst_15242);
(statearr_15266[(8)] = inst_15232__$1);
return statearr_15266;
})();var statearr_15267_15285 = state_15255__$1;(statearr_15267_15285[(2)] = null);
(statearr_15267_15285[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15256 === (9)))
{var state_15255__$1 = state_15255;var statearr_15268_15286 = state_15255__$1;(statearr_15268_15286[(2)] = null);
(statearr_15268_15286[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15256 === (5)))
{var state_15255__$1 = state_15255;var statearr_15269_15287 = state_15255__$1;(statearr_15269_15287[(2)] = null);
(statearr_15269_15287[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15256 === (10)))
{var inst_15247 = (state_15255[(2)]);var state_15255__$1 = state_15255;var statearr_15270_15288 = state_15255__$1;(statearr_15270_15288[(2)] = inst_15247);
(statearr_15270_15288[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15256 === (8)))
{var inst_15237 = (state_15255[(7)]);var state_15255__$1 = state_15255;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_15255__$1,(11),out,inst_15237);
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
});})(c__10224__auto___15278,out))
;return ((function (switch__10159__auto__,c__10224__auto___15278,out){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_15274 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_15274[(0)] = state_machine__10160__auto__);
(statearr_15274[(1)] = (1));
return statearr_15274;
});
var state_machine__10160__auto____1 = (function (state_15255){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_15255);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e15275){if((e15275 instanceof Object))
{var ex__10163__auto__ = e15275;var statearr_15276_15289 = state_15255;(statearr_15276_15289[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_15255);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e15275;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__15290 = state_15255;
state_15255 = G__15290;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_15255){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_15255);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___15278,out))
})();var state__10226__auto__ = (function (){var statearr_15277 = f__10225__auto__.call(null);(statearr_15277[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___15278);
return statearr_15277;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___15278,out))
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
cljs.core.async.map_LT_ = (function map_LT_(f,ch){if(typeof cljs.core.async.t15298 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t15298 = (function (ch,f,map_LT_,meta15299){
this.ch = ch;
this.f = f;
this.map_LT_ = map_LT_;
this.meta15299 = meta15299;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t15298.cljs$lang$type = true;
cljs.core.async.t15298.cljs$lang$ctorStr = "cljs.core.async/t15298";
cljs.core.async.t15298.cljs$lang$ctorPrWriter = (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t15298");
});
cljs.core.async.t15298.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t15298.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.put_BANG_.call(null,self__.ch,val,fn1);
});
cljs.core.async.t15298.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t15298.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;var ret = cljs.core.async.impl.protocols.take_BANG_.call(null,self__.ch,(function (){if(typeof cljs.core.async.t15301 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t15301 = (function (fn1,_,meta15299,ch,f,map_LT_,meta15302){
this.fn1 = fn1;
this._ = _;
this.meta15299 = meta15299;
this.ch = ch;
this.f = f;
this.map_LT_ = map_LT_;
this.meta15302 = meta15302;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t15301.cljs$lang$type = true;
cljs.core.async.t15301.cljs$lang$ctorStr = "cljs.core.async/t15301";
cljs.core.async.t15301.cljs$lang$ctorPrWriter = ((function (___$1){
return (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t15301");
});})(___$1))
;
cljs.core.async.t15301.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.t15301.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;return cljs.core.async.impl.protocols.active_QMARK_.call(null,self__.fn1);
});})(___$1))
;
cljs.core.async.t15301.prototype.cljs$core$async$impl$protocols$Handler$lock_id$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;return cljs.core.async.impl.protocols.lock_id.call(null,self__.fn1);
});})(___$1))
;
cljs.core.async.t15301.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = ((function (___$1){
return (function (___$3){var self__ = this;
var ___$4 = this;var f1 = cljs.core.async.impl.protocols.commit.call(null,self__.fn1);return ((function (f1,___$4,___$1){
return (function (p1__15291_SHARP_){return f1.call(null,(((p1__15291_SHARP_ == null))?null:self__.f.call(null,p1__15291_SHARP_)));
});
;})(f1,___$4,___$1))
});})(___$1))
;
cljs.core.async.t15301.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (___$1){
return (function (_15303){var self__ = this;
var _15303__$1 = this;return self__.meta15302;
});})(___$1))
;
cljs.core.async.t15301.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (___$1){
return (function (_15303,meta15302__$1){var self__ = this;
var _15303__$1 = this;return (new cljs.core.async.t15301(self__.fn1,self__._,self__.meta15299,self__.ch,self__.f,self__.map_LT_,meta15302__$1));
});})(___$1))
;
cljs.core.async.__GT_t15301 = ((function (___$1){
return (function __GT_t15301(fn1__$1,___$2,meta15299__$1,ch__$2,f__$2,map_LT___$2,meta15302){return (new cljs.core.async.t15301(fn1__$1,___$2,meta15299__$1,ch__$2,f__$2,map_LT___$2,meta15302));
});})(___$1))
;
}
return (new cljs.core.async.t15301(fn1,___$1,self__.meta15299,self__.ch,self__.f,self__.map_LT_,null));
})());if(cljs.core.truth_((function (){var and__7296__auto__ = ret;if(cljs.core.truth_(and__7296__auto__))
{return !((cljs.core.deref.call(null,ret) == null));
} else
{return and__7296__auto__;
}
})()))
{return cljs.core.async.impl.channels.box.call(null,self__.f.call(null,cljs.core.deref.call(null,ret)));
} else
{return ret;
}
});
cljs.core.async.t15298.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t15298.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_.call(null,self__.ch);
});
cljs.core.async.t15298.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.closed_QMARK_.call(null,self__.ch);
});
cljs.core.async.t15298.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_15300){var self__ = this;
var _15300__$1 = this;return self__.meta15299;
});
cljs.core.async.t15298.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_15300,meta15299__$1){var self__ = this;
var _15300__$1 = this;return (new cljs.core.async.t15298(self__.ch,self__.f,self__.map_LT_,meta15299__$1));
});
cljs.core.async.__GT_t15298 = (function __GT_t15298(ch__$1,f__$1,map_LT___$1,meta15299){return (new cljs.core.async.t15298(ch__$1,f__$1,map_LT___$1,meta15299));
});
}
return (new cljs.core.async.t15298(ch,f,map_LT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.map_GT_ = (function map_GT_(f,ch){if(typeof cljs.core.async.t15307 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t15307 = (function (ch,f,map_GT_,meta15308){
this.ch = ch;
this.f = f;
this.map_GT_ = map_GT_;
this.meta15308 = meta15308;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t15307.cljs$lang$type = true;
cljs.core.async.t15307.cljs$lang$ctorStr = "cljs.core.async/t15307";
cljs.core.async.t15307.cljs$lang$ctorPrWriter = (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t15307");
});
cljs.core.async.t15307.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t15307.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.put_BANG_.call(null,self__.ch,self__.f.call(null,val),fn1);
});
cljs.core.async.t15307.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t15307.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.take_BANG_.call(null,self__.ch,fn1);
});
cljs.core.async.t15307.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t15307.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_.call(null,self__.ch);
});
cljs.core.async.t15307.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_15309){var self__ = this;
var _15309__$1 = this;return self__.meta15308;
});
cljs.core.async.t15307.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_15309,meta15308__$1){var self__ = this;
var _15309__$1 = this;return (new cljs.core.async.t15307(self__.ch,self__.f,self__.map_GT_,meta15308__$1));
});
cljs.core.async.__GT_t15307 = (function __GT_t15307(ch__$1,f__$1,map_GT___$1,meta15308){return (new cljs.core.async.t15307(ch__$1,f__$1,map_GT___$1,meta15308));
});
}
return (new cljs.core.async.t15307(ch,f,map_GT_,null));
});
/**
* Deprecated - this function will be removed. Use transducer instead
*/
cljs.core.async.filter_GT_ = (function filter_GT_(p,ch){if(typeof cljs.core.async.t15313 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.t15313 = (function (ch,p,filter_GT_,meta15314){
this.ch = ch;
this.p = p;
this.filter_GT_ = filter_GT_;
this.meta15314 = meta15314;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.t15313.cljs$lang$type = true;
cljs.core.async.t15313.cljs$lang$ctorStr = "cljs.core.async/t15313";
cljs.core.async.t15313.cljs$lang$ctorPrWriter = (function (this__7875__auto__,writer__7876__auto__,opt__7877__auto__){return cljs.core._write.call(null,writer__7876__auto__,"cljs.core.async/t15313");
});
cljs.core.async.t15313.prototype.cljs$core$async$impl$protocols$WritePort$ = true;
cljs.core.async.t15313.prototype.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3 = (function (_,val,fn1){var self__ = this;
var ___$1 = this;if(cljs.core.truth_(self__.p.call(null,val)))
{return cljs.core.async.impl.protocols.put_BANG_.call(null,self__.ch,val,fn1);
} else
{return cljs.core.async.impl.channels.box.call(null,cljs.core.not.call(null,cljs.core.async.impl.protocols.closed_QMARK_.call(null,self__.ch)));
}
});
cljs.core.async.t15313.prototype.cljs$core$async$impl$protocols$ReadPort$ = true;
cljs.core.async.t15313.prototype.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2 = (function (_,fn1){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.take_BANG_.call(null,self__.ch,fn1);
});
cljs.core.async.t15313.prototype.cljs$core$async$impl$protocols$Channel$ = true;
cljs.core.async.t15313.prototype.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.close_BANG_.call(null,self__.ch);
});
cljs.core.async.t15313.prototype.cljs$core$async$impl$protocols$Channel$closed_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return cljs.core.async.impl.protocols.closed_QMARK_.call(null,self__.ch);
});
cljs.core.async.t15313.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_15315){var self__ = this;
var _15315__$1 = this;return self__.meta15314;
});
cljs.core.async.t15313.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_15315,meta15314__$1){var self__ = this;
var _15315__$1 = this;return (new cljs.core.async.t15313(self__.ch,self__.p,self__.filter_GT_,meta15314__$1));
});
cljs.core.async.__GT_t15313 = (function __GT_t15313(ch__$1,p__$1,filter_GT___$1,meta15314){return (new cljs.core.async.t15313(ch__$1,p__$1,filter_GT___$1,meta15314));
});
}
return (new cljs.core.async.t15313(ch,p,filter_GT_,null));
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
var filter_LT___3 = (function (p,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__10224__auto___15398 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___15398,out){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___15398,out){
return (function (state_15377){var state_val_15378 = (state_15377[(1)]);if((state_val_15378 === (7)))
{var inst_15373 = (state_15377[(2)]);var state_15377__$1 = state_15377;var statearr_15379_15399 = state_15377__$1;(statearr_15379_15399[(2)] = inst_15373);
(statearr_15379_15399[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15378 === (1)))
{var state_15377__$1 = state_15377;var statearr_15380_15400 = state_15377__$1;(statearr_15380_15400[(2)] = null);
(statearr_15380_15400[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15378 === (4)))
{var inst_15359 = (state_15377[(7)]);var inst_15359__$1 = (state_15377[(2)]);var inst_15360 = (inst_15359__$1 == null);var state_15377__$1 = (function (){var statearr_15381 = state_15377;(statearr_15381[(7)] = inst_15359__$1);
return statearr_15381;
})();if(cljs.core.truth_(inst_15360))
{var statearr_15382_15401 = state_15377__$1;(statearr_15382_15401[(1)] = (5));
} else
{var statearr_15383_15402 = state_15377__$1;(statearr_15383_15402[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15378 === (6)))
{var inst_15359 = (state_15377[(7)]);var inst_15364 = p.call(null,inst_15359);var state_15377__$1 = state_15377;if(cljs.core.truth_(inst_15364))
{var statearr_15384_15403 = state_15377__$1;(statearr_15384_15403[(1)] = (8));
} else
{var statearr_15385_15404 = state_15377__$1;(statearr_15385_15404[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15378 === (3)))
{var inst_15375 = (state_15377[(2)]);var state_15377__$1 = state_15377;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_15377__$1,inst_15375);
} else
{if((state_val_15378 === (2)))
{var state_15377__$1 = state_15377;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_15377__$1,(4),ch);
} else
{if((state_val_15378 === (11)))
{var inst_15367 = (state_15377[(2)]);var state_15377__$1 = state_15377;var statearr_15386_15405 = state_15377__$1;(statearr_15386_15405[(2)] = inst_15367);
(statearr_15386_15405[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15378 === (9)))
{var state_15377__$1 = state_15377;var statearr_15387_15406 = state_15377__$1;(statearr_15387_15406[(2)] = null);
(statearr_15387_15406[(1)] = (10));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15378 === (5)))
{var inst_15362 = cljs.core.async.close_BANG_.call(null,out);var state_15377__$1 = state_15377;var statearr_15388_15407 = state_15377__$1;(statearr_15388_15407[(2)] = inst_15362);
(statearr_15388_15407[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15378 === (10)))
{var inst_15370 = (state_15377[(2)]);var state_15377__$1 = (function (){var statearr_15389 = state_15377;(statearr_15389[(8)] = inst_15370);
return statearr_15389;
})();var statearr_15390_15408 = state_15377__$1;(statearr_15390_15408[(2)] = null);
(statearr_15390_15408[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15378 === (8)))
{var inst_15359 = (state_15377[(7)]);var state_15377__$1 = state_15377;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_15377__$1,(11),out,inst_15359);
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
});})(c__10224__auto___15398,out))
;return ((function (switch__10159__auto__,c__10224__auto___15398,out){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_15394 = [null,null,null,null,null,null,null,null,null];(statearr_15394[(0)] = state_machine__10160__auto__);
(statearr_15394[(1)] = (1));
return statearr_15394;
});
var state_machine__10160__auto____1 = (function (state_15377){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_15377);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e15395){if((e15395 instanceof Object))
{var ex__10163__auto__ = e15395;var statearr_15396_15409 = state_15377;(statearr_15396_15409[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_15377);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e15395;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__15410 = state_15377;
state_15377 = G__15410;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_15377){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_15377);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___15398,out))
})();var state__10226__auto__ = (function (){var statearr_15397 = f__10225__auto__.call(null);(statearr_15397[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___15398);
return statearr_15397;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___15398,out))
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
cljs.core.async.mapcat_STAR_ = (function mapcat_STAR_(f,in$,out){var c__10224__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto__){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto__){
return (function (state_15576){var state_val_15577 = (state_15576[(1)]);if((state_val_15577 === (7)))
{var inst_15572 = (state_15576[(2)]);var state_15576__$1 = state_15576;var statearr_15578_15619 = state_15576__$1;(statearr_15578_15619[(2)] = inst_15572);
(statearr_15578_15619[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (20)))
{var inst_15542 = (state_15576[(7)]);var inst_15553 = (state_15576[(2)]);var inst_15554 = cljs.core.next.call(null,inst_15542);var inst_15528 = inst_15554;var inst_15529 = null;var inst_15530 = (0);var inst_15531 = (0);var state_15576__$1 = (function (){var statearr_15579 = state_15576;(statearr_15579[(8)] = inst_15531);
(statearr_15579[(9)] = inst_15530);
(statearr_15579[(10)] = inst_15528);
(statearr_15579[(11)] = inst_15529);
(statearr_15579[(12)] = inst_15553);
return statearr_15579;
})();var statearr_15580_15620 = state_15576__$1;(statearr_15580_15620[(2)] = null);
(statearr_15580_15620[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (1)))
{var state_15576__$1 = state_15576;var statearr_15581_15621 = state_15576__$1;(statearr_15581_15621[(2)] = null);
(statearr_15581_15621[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (4)))
{var inst_15517 = (state_15576[(13)]);var inst_15517__$1 = (state_15576[(2)]);var inst_15518 = (inst_15517__$1 == null);var state_15576__$1 = (function (){var statearr_15582 = state_15576;(statearr_15582[(13)] = inst_15517__$1);
return statearr_15582;
})();if(cljs.core.truth_(inst_15518))
{var statearr_15583_15622 = state_15576__$1;(statearr_15583_15622[(1)] = (5));
} else
{var statearr_15584_15623 = state_15576__$1;(statearr_15584_15623[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (15)))
{var state_15576__$1 = state_15576;var statearr_15588_15624 = state_15576__$1;(statearr_15588_15624[(2)] = null);
(statearr_15588_15624[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (21)))
{var state_15576__$1 = state_15576;var statearr_15589_15625 = state_15576__$1;(statearr_15589_15625[(2)] = null);
(statearr_15589_15625[(1)] = (23));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (13)))
{var inst_15531 = (state_15576[(8)]);var inst_15530 = (state_15576[(9)]);var inst_15528 = (state_15576[(10)]);var inst_15529 = (state_15576[(11)]);var inst_15538 = (state_15576[(2)]);var inst_15539 = (inst_15531 + (1));var tmp15585 = inst_15530;var tmp15586 = inst_15528;var tmp15587 = inst_15529;var inst_15528__$1 = tmp15586;var inst_15529__$1 = tmp15587;var inst_15530__$1 = tmp15585;var inst_15531__$1 = inst_15539;var state_15576__$1 = (function (){var statearr_15590 = state_15576;(statearr_15590[(8)] = inst_15531__$1);
(statearr_15590[(9)] = inst_15530__$1);
(statearr_15590[(10)] = inst_15528__$1);
(statearr_15590[(14)] = inst_15538);
(statearr_15590[(11)] = inst_15529__$1);
return statearr_15590;
})();var statearr_15591_15626 = state_15576__$1;(statearr_15591_15626[(2)] = null);
(statearr_15591_15626[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (22)))
{var state_15576__$1 = state_15576;var statearr_15592_15627 = state_15576__$1;(statearr_15592_15627[(2)] = null);
(statearr_15592_15627[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (6)))
{var inst_15517 = (state_15576[(13)]);var inst_15526 = f.call(null,inst_15517);var inst_15527 = cljs.core.seq.call(null,inst_15526);var inst_15528 = inst_15527;var inst_15529 = null;var inst_15530 = (0);var inst_15531 = (0);var state_15576__$1 = (function (){var statearr_15593 = state_15576;(statearr_15593[(8)] = inst_15531);
(statearr_15593[(9)] = inst_15530);
(statearr_15593[(10)] = inst_15528);
(statearr_15593[(11)] = inst_15529);
return statearr_15593;
})();var statearr_15594_15628 = state_15576__$1;(statearr_15594_15628[(2)] = null);
(statearr_15594_15628[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (17)))
{var inst_15542 = (state_15576[(7)]);var inst_15546 = cljs.core.chunk_first.call(null,inst_15542);var inst_15547 = cljs.core.chunk_rest.call(null,inst_15542);var inst_15548 = cljs.core.count.call(null,inst_15546);var inst_15528 = inst_15547;var inst_15529 = inst_15546;var inst_15530 = inst_15548;var inst_15531 = (0);var state_15576__$1 = (function (){var statearr_15595 = state_15576;(statearr_15595[(8)] = inst_15531);
(statearr_15595[(9)] = inst_15530);
(statearr_15595[(10)] = inst_15528);
(statearr_15595[(11)] = inst_15529);
return statearr_15595;
})();var statearr_15596_15629 = state_15576__$1;(statearr_15596_15629[(2)] = null);
(statearr_15596_15629[(1)] = (8));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (3)))
{var inst_15574 = (state_15576[(2)]);var state_15576__$1 = state_15576;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_15576__$1,inst_15574);
} else
{if((state_val_15577 === (12)))
{var inst_15562 = (state_15576[(2)]);var state_15576__$1 = state_15576;var statearr_15597_15630 = state_15576__$1;(statearr_15597_15630[(2)] = inst_15562);
(statearr_15597_15630[(1)] = (9));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (2)))
{var state_15576__$1 = state_15576;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_15576__$1,(4),in$);
} else
{if((state_val_15577 === (23)))
{var inst_15570 = (state_15576[(2)]);var state_15576__$1 = state_15576;var statearr_15598_15631 = state_15576__$1;(statearr_15598_15631[(2)] = inst_15570);
(statearr_15598_15631[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (19)))
{var inst_15557 = (state_15576[(2)]);var state_15576__$1 = state_15576;var statearr_15599_15632 = state_15576__$1;(statearr_15599_15632[(2)] = inst_15557);
(statearr_15599_15632[(1)] = (16));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (11)))
{var inst_15528 = (state_15576[(10)]);var inst_15542 = (state_15576[(7)]);var inst_15542__$1 = cljs.core.seq.call(null,inst_15528);var state_15576__$1 = (function (){var statearr_15600 = state_15576;(statearr_15600[(7)] = inst_15542__$1);
return statearr_15600;
})();if(inst_15542__$1)
{var statearr_15601_15633 = state_15576__$1;(statearr_15601_15633[(1)] = (14));
} else
{var statearr_15602_15634 = state_15576__$1;(statearr_15602_15634[(1)] = (15));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (9)))
{var inst_15564 = (state_15576[(2)]);var inst_15565 = cljs.core.async.impl.protocols.closed_QMARK_.call(null,out);var state_15576__$1 = (function (){var statearr_15603 = state_15576;(statearr_15603[(15)] = inst_15564);
return statearr_15603;
})();if(cljs.core.truth_(inst_15565))
{var statearr_15604_15635 = state_15576__$1;(statearr_15604_15635[(1)] = (21));
} else
{var statearr_15605_15636 = state_15576__$1;(statearr_15605_15636[(1)] = (22));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (5)))
{var inst_15520 = cljs.core.async.close_BANG_.call(null,out);var state_15576__$1 = state_15576;var statearr_15606_15637 = state_15576__$1;(statearr_15606_15637[(2)] = inst_15520);
(statearr_15606_15637[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (14)))
{var inst_15542 = (state_15576[(7)]);var inst_15544 = cljs.core.chunked_seq_QMARK_.call(null,inst_15542);var state_15576__$1 = state_15576;if(inst_15544)
{var statearr_15607_15638 = state_15576__$1;(statearr_15607_15638[(1)] = (17));
} else
{var statearr_15608_15639 = state_15576__$1;(statearr_15608_15639[(1)] = (18));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (16)))
{var inst_15560 = (state_15576[(2)]);var state_15576__$1 = state_15576;var statearr_15609_15640 = state_15576__$1;(statearr_15609_15640[(2)] = inst_15560);
(statearr_15609_15640[(1)] = (12));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15577 === (10)))
{var inst_15531 = (state_15576[(8)]);var inst_15529 = (state_15576[(11)]);var inst_15536 = cljs.core._nth.call(null,inst_15529,inst_15531);var state_15576__$1 = state_15576;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_15576__$1,(13),out,inst_15536);
} else
{if((state_val_15577 === (18)))
{var inst_15542 = (state_15576[(7)]);var inst_15551 = cljs.core.first.call(null,inst_15542);var state_15576__$1 = state_15576;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_15576__$1,(20),out,inst_15551);
} else
{if((state_val_15577 === (8)))
{var inst_15531 = (state_15576[(8)]);var inst_15530 = (state_15576[(9)]);var inst_15533 = (inst_15531 < inst_15530);var inst_15534 = inst_15533;var state_15576__$1 = state_15576;if(cljs.core.truth_(inst_15534))
{var statearr_15610_15641 = state_15576__$1;(statearr_15610_15641[(1)] = (10));
} else
{var statearr_15611_15642 = state_15576__$1;(statearr_15611_15642[(1)] = (11));
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
});})(c__10224__auto__))
;return ((function (switch__10159__auto__,c__10224__auto__){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_15615 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_15615[(0)] = state_machine__10160__auto__);
(statearr_15615[(1)] = (1));
return statearr_15615;
});
var state_machine__10160__auto____1 = (function (state_15576){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_15576);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e15616){if((e15616 instanceof Object))
{var ex__10163__auto__ = e15616;var statearr_15617_15643 = state_15576;(statearr_15617_15643[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_15576);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e15616;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__15644 = state_15576;
state_15576 = G__15644;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_15576){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_15576);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto__))
})();var state__10226__auto__ = (function (){var statearr_15618 = f__10225__auto__.call(null);(statearr_15618[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto__);
return statearr_15618;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto__))
);
return c__10224__auto__;
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
var unique__2 = (function (ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__10224__auto___15741 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___15741,out){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___15741,out){
return (function (state_15716){var state_val_15717 = (state_15716[(1)]);if((state_val_15717 === (7)))
{var inst_15711 = (state_15716[(2)]);var state_15716__$1 = state_15716;var statearr_15718_15742 = state_15716__$1;(statearr_15718_15742[(2)] = inst_15711);
(statearr_15718_15742[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15717 === (1)))
{var inst_15693 = null;var state_15716__$1 = (function (){var statearr_15719 = state_15716;(statearr_15719[(7)] = inst_15693);
return statearr_15719;
})();var statearr_15720_15743 = state_15716__$1;(statearr_15720_15743[(2)] = null);
(statearr_15720_15743[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15717 === (4)))
{var inst_15696 = (state_15716[(8)]);var inst_15696__$1 = (state_15716[(2)]);var inst_15697 = (inst_15696__$1 == null);var inst_15698 = cljs.core.not.call(null,inst_15697);var state_15716__$1 = (function (){var statearr_15721 = state_15716;(statearr_15721[(8)] = inst_15696__$1);
return statearr_15721;
})();if(inst_15698)
{var statearr_15722_15744 = state_15716__$1;(statearr_15722_15744[(1)] = (5));
} else
{var statearr_15723_15745 = state_15716__$1;(statearr_15723_15745[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15717 === (6)))
{var state_15716__$1 = state_15716;var statearr_15724_15746 = state_15716__$1;(statearr_15724_15746[(2)] = null);
(statearr_15724_15746[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15717 === (3)))
{var inst_15713 = (state_15716[(2)]);var inst_15714 = cljs.core.async.close_BANG_.call(null,out);var state_15716__$1 = (function (){var statearr_15725 = state_15716;(statearr_15725[(9)] = inst_15713);
return statearr_15725;
})();return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_15716__$1,inst_15714);
} else
{if((state_val_15717 === (2)))
{var state_15716__$1 = state_15716;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_15716__$1,(4),ch);
} else
{if((state_val_15717 === (11)))
{var inst_15696 = (state_15716[(8)]);var inst_15705 = (state_15716[(2)]);var inst_15693 = inst_15696;var state_15716__$1 = (function (){var statearr_15726 = state_15716;(statearr_15726[(7)] = inst_15693);
(statearr_15726[(10)] = inst_15705);
return statearr_15726;
})();var statearr_15727_15747 = state_15716__$1;(statearr_15727_15747[(2)] = null);
(statearr_15727_15747[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15717 === (9)))
{var inst_15696 = (state_15716[(8)]);var state_15716__$1 = state_15716;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_15716__$1,(11),out,inst_15696);
} else
{if((state_val_15717 === (5)))
{var inst_15693 = (state_15716[(7)]);var inst_15696 = (state_15716[(8)]);var inst_15700 = cljs.core._EQ_.call(null,inst_15696,inst_15693);var state_15716__$1 = state_15716;if(inst_15700)
{var statearr_15729_15748 = state_15716__$1;(statearr_15729_15748[(1)] = (8));
} else
{var statearr_15730_15749 = state_15716__$1;(statearr_15730_15749[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15717 === (10)))
{var inst_15708 = (state_15716[(2)]);var state_15716__$1 = state_15716;var statearr_15731_15750 = state_15716__$1;(statearr_15731_15750[(2)] = inst_15708);
(statearr_15731_15750[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15717 === (8)))
{var inst_15693 = (state_15716[(7)]);var tmp15728 = inst_15693;var inst_15693__$1 = tmp15728;var state_15716__$1 = (function (){var statearr_15732 = state_15716;(statearr_15732[(7)] = inst_15693__$1);
return statearr_15732;
})();var statearr_15733_15751 = state_15716__$1;(statearr_15733_15751[(2)] = null);
(statearr_15733_15751[(1)] = (2));
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
});})(c__10224__auto___15741,out))
;return ((function (switch__10159__auto__,c__10224__auto___15741,out){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_15737 = [null,null,null,null,null,null,null,null,null,null,null];(statearr_15737[(0)] = state_machine__10160__auto__);
(statearr_15737[(1)] = (1));
return statearr_15737;
});
var state_machine__10160__auto____1 = (function (state_15716){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_15716);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e15738){if((e15738 instanceof Object))
{var ex__10163__auto__ = e15738;var statearr_15739_15752 = state_15716;(statearr_15739_15752[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_15716);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e15738;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__15753 = state_15716;
state_15716 = G__15753;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_15716){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_15716);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___15741,out))
})();var state__10226__auto__ = (function (){var statearr_15740 = f__10225__auto__.call(null);(statearr_15740[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___15741);
return statearr_15740;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___15741,out))
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
var partition__3 = (function (n,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__10224__auto___15888 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___15888,out){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___15888,out){
return (function (state_15858){var state_val_15859 = (state_15858[(1)]);if((state_val_15859 === (7)))
{var inst_15854 = (state_15858[(2)]);var state_15858__$1 = state_15858;var statearr_15860_15889 = state_15858__$1;(statearr_15860_15889[(2)] = inst_15854);
(statearr_15860_15889[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (1)))
{var inst_15821 = (new Array(n));var inst_15822 = inst_15821;var inst_15823 = (0);var state_15858__$1 = (function (){var statearr_15861 = state_15858;(statearr_15861[(7)] = inst_15823);
(statearr_15861[(8)] = inst_15822);
return statearr_15861;
})();var statearr_15862_15890 = state_15858__$1;(statearr_15862_15890[(2)] = null);
(statearr_15862_15890[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (4)))
{var inst_15826 = (state_15858[(9)]);var inst_15826__$1 = (state_15858[(2)]);var inst_15827 = (inst_15826__$1 == null);var inst_15828 = cljs.core.not.call(null,inst_15827);var state_15858__$1 = (function (){var statearr_15863 = state_15858;(statearr_15863[(9)] = inst_15826__$1);
return statearr_15863;
})();if(inst_15828)
{var statearr_15864_15891 = state_15858__$1;(statearr_15864_15891[(1)] = (5));
} else
{var statearr_15865_15892 = state_15858__$1;(statearr_15865_15892[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (15)))
{var inst_15848 = (state_15858[(2)]);var state_15858__$1 = state_15858;var statearr_15866_15893 = state_15858__$1;(statearr_15866_15893[(2)] = inst_15848);
(statearr_15866_15893[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (13)))
{var state_15858__$1 = state_15858;var statearr_15867_15894 = state_15858__$1;(statearr_15867_15894[(2)] = null);
(statearr_15867_15894[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (6)))
{var inst_15823 = (state_15858[(7)]);var inst_15844 = (inst_15823 > (0));var state_15858__$1 = state_15858;if(cljs.core.truth_(inst_15844))
{var statearr_15868_15895 = state_15858__$1;(statearr_15868_15895[(1)] = (12));
} else
{var statearr_15869_15896 = state_15858__$1;(statearr_15869_15896[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (3)))
{var inst_15856 = (state_15858[(2)]);var state_15858__$1 = state_15858;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_15858__$1,inst_15856);
} else
{if((state_val_15859 === (12)))
{var inst_15822 = (state_15858[(8)]);var inst_15846 = cljs.core.vec.call(null,inst_15822);var state_15858__$1 = state_15858;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_15858__$1,(15),out,inst_15846);
} else
{if((state_val_15859 === (2)))
{var state_15858__$1 = state_15858;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_15858__$1,(4),ch);
} else
{if((state_val_15859 === (11)))
{var inst_15838 = (state_15858[(2)]);var inst_15839 = (new Array(n));var inst_15822 = inst_15839;var inst_15823 = (0);var state_15858__$1 = (function (){var statearr_15870 = state_15858;(statearr_15870[(10)] = inst_15838);
(statearr_15870[(7)] = inst_15823);
(statearr_15870[(8)] = inst_15822);
return statearr_15870;
})();var statearr_15871_15897 = state_15858__$1;(statearr_15871_15897[(2)] = null);
(statearr_15871_15897[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (9)))
{var inst_15822 = (state_15858[(8)]);var inst_15836 = cljs.core.vec.call(null,inst_15822);var state_15858__$1 = state_15858;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_15858__$1,(11),out,inst_15836);
} else
{if((state_val_15859 === (5)))
{var inst_15831 = (state_15858[(11)]);var inst_15826 = (state_15858[(9)]);var inst_15823 = (state_15858[(7)]);var inst_15822 = (state_15858[(8)]);var inst_15830 = (inst_15822[inst_15823] = inst_15826);var inst_15831__$1 = (inst_15823 + (1));var inst_15832 = (inst_15831__$1 < n);var state_15858__$1 = (function (){var statearr_15872 = state_15858;(statearr_15872[(11)] = inst_15831__$1);
(statearr_15872[(12)] = inst_15830);
return statearr_15872;
})();if(cljs.core.truth_(inst_15832))
{var statearr_15873_15898 = state_15858__$1;(statearr_15873_15898[(1)] = (8));
} else
{var statearr_15874_15899 = state_15858__$1;(statearr_15874_15899[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (14)))
{var inst_15851 = (state_15858[(2)]);var inst_15852 = cljs.core.async.close_BANG_.call(null,out);var state_15858__$1 = (function (){var statearr_15876 = state_15858;(statearr_15876[(13)] = inst_15851);
return statearr_15876;
})();var statearr_15877_15900 = state_15858__$1;(statearr_15877_15900[(2)] = inst_15852);
(statearr_15877_15900[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (10)))
{var inst_15842 = (state_15858[(2)]);var state_15858__$1 = state_15858;var statearr_15878_15901 = state_15858__$1;(statearr_15878_15901[(2)] = inst_15842);
(statearr_15878_15901[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_15859 === (8)))
{var inst_15831 = (state_15858[(11)]);var inst_15822 = (state_15858[(8)]);var tmp15875 = inst_15822;var inst_15822__$1 = tmp15875;var inst_15823 = inst_15831;var state_15858__$1 = (function (){var statearr_15879 = state_15858;(statearr_15879[(7)] = inst_15823);
(statearr_15879[(8)] = inst_15822__$1);
return statearr_15879;
})();var statearr_15880_15902 = state_15858__$1;(statearr_15880_15902[(2)] = null);
(statearr_15880_15902[(1)] = (2));
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
});})(c__10224__auto___15888,out))
;return ((function (switch__10159__auto__,c__10224__auto___15888,out){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_15884 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_15884[(0)] = state_machine__10160__auto__);
(statearr_15884[(1)] = (1));
return statearr_15884;
});
var state_machine__10160__auto____1 = (function (state_15858){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_15858);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e15885){if((e15885 instanceof Object))
{var ex__10163__auto__ = e15885;var statearr_15886_15903 = state_15858;(statearr_15886_15903[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_15858);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e15885;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__15904 = state_15858;
state_15858 = G__15904;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_15858){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_15858);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___15888,out))
})();var state__10226__auto__ = (function (){var statearr_15887 = f__10225__auto__.call(null);(statearr_15887[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___15888);
return statearr_15887;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___15888,out))
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
var partition_by__3 = (function (f,ch,buf_or_n){var out = cljs.core.async.chan.call(null,buf_or_n);var c__10224__auto___16047 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__10224__auto___16047,out){
return (function (){var f__10225__auto__ = (function (){var switch__10159__auto__ = ((function (c__10224__auto___16047,out){
return (function (state_16017){var state_val_16018 = (state_16017[(1)]);if((state_val_16018 === (7)))
{var inst_16013 = (state_16017[(2)]);var state_16017__$1 = state_16017;var statearr_16019_16048 = state_16017__$1;(statearr_16019_16048[(2)] = inst_16013);
(statearr_16019_16048[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (1)))
{var inst_15976 = [];var inst_15977 = inst_15976;var inst_15978 = new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123);var state_16017__$1 = (function (){var statearr_16020 = state_16017;(statearr_16020[(7)] = inst_15977);
(statearr_16020[(8)] = inst_15978);
return statearr_16020;
})();var statearr_16021_16049 = state_16017__$1;(statearr_16021_16049[(2)] = null);
(statearr_16021_16049[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (4)))
{var inst_15981 = (state_16017[(9)]);var inst_15981__$1 = (state_16017[(2)]);var inst_15982 = (inst_15981__$1 == null);var inst_15983 = cljs.core.not.call(null,inst_15982);var state_16017__$1 = (function (){var statearr_16022 = state_16017;(statearr_16022[(9)] = inst_15981__$1);
return statearr_16022;
})();if(inst_15983)
{var statearr_16023_16050 = state_16017__$1;(statearr_16023_16050[(1)] = (5));
} else
{var statearr_16024_16051 = state_16017__$1;(statearr_16024_16051[(1)] = (6));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (15)))
{var inst_16007 = (state_16017[(2)]);var state_16017__$1 = state_16017;var statearr_16025_16052 = state_16017__$1;(statearr_16025_16052[(2)] = inst_16007);
(statearr_16025_16052[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (13)))
{var state_16017__$1 = state_16017;var statearr_16026_16053 = state_16017__$1;(statearr_16026_16053[(2)] = null);
(statearr_16026_16053[(1)] = (14));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (6)))
{var inst_15977 = (state_16017[(7)]);var inst_16002 = inst_15977.length;var inst_16003 = (inst_16002 > (0));var state_16017__$1 = state_16017;if(cljs.core.truth_(inst_16003))
{var statearr_16027_16054 = state_16017__$1;(statearr_16027_16054[(1)] = (12));
} else
{var statearr_16028_16055 = state_16017__$1;(statearr_16028_16055[(1)] = (13));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (3)))
{var inst_16015 = (state_16017[(2)]);var state_16017__$1 = state_16017;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_16017__$1,inst_16015);
} else
{if((state_val_16018 === (12)))
{var inst_15977 = (state_16017[(7)]);var inst_16005 = cljs.core.vec.call(null,inst_15977);var state_16017__$1 = state_16017;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_16017__$1,(15),out,inst_16005);
} else
{if((state_val_16018 === (2)))
{var state_16017__$1 = state_16017;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_16017__$1,(4),ch);
} else
{if((state_val_16018 === (11)))
{var inst_15985 = (state_16017[(10)]);var inst_15981 = (state_16017[(9)]);var inst_15995 = (state_16017[(2)]);var inst_15996 = [];var inst_15997 = inst_15996.push(inst_15981);var inst_15977 = inst_15996;var inst_15978 = inst_15985;var state_16017__$1 = (function (){var statearr_16029 = state_16017;(statearr_16029[(7)] = inst_15977);
(statearr_16029[(8)] = inst_15978);
(statearr_16029[(11)] = inst_15995);
(statearr_16029[(12)] = inst_15997);
return statearr_16029;
})();var statearr_16030_16056 = state_16017__$1;(statearr_16030_16056[(2)] = null);
(statearr_16030_16056[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (9)))
{var inst_15977 = (state_16017[(7)]);var inst_15993 = cljs.core.vec.call(null,inst_15977);var state_16017__$1 = state_16017;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_16017__$1,(11),out,inst_15993);
} else
{if((state_val_16018 === (5)))
{var inst_15978 = (state_16017[(8)]);var inst_15985 = (state_16017[(10)]);var inst_15981 = (state_16017[(9)]);var inst_15985__$1 = f.call(null,inst_15981);var inst_15986 = cljs.core._EQ_.call(null,inst_15985__$1,inst_15978);var inst_15987 = cljs.core.keyword_identical_QMARK_.call(null,inst_15978,new cljs.core.Keyword("cljs.core.async","nothing","cljs.core.async/nothing",-69252123));var inst_15988 = (inst_15986) || (inst_15987);var state_16017__$1 = (function (){var statearr_16031 = state_16017;(statearr_16031[(10)] = inst_15985__$1);
return statearr_16031;
})();if(cljs.core.truth_(inst_15988))
{var statearr_16032_16057 = state_16017__$1;(statearr_16032_16057[(1)] = (8));
} else
{var statearr_16033_16058 = state_16017__$1;(statearr_16033_16058[(1)] = (9));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (14)))
{var inst_16010 = (state_16017[(2)]);var inst_16011 = cljs.core.async.close_BANG_.call(null,out);var state_16017__$1 = (function (){var statearr_16035 = state_16017;(statearr_16035[(13)] = inst_16010);
return statearr_16035;
})();var statearr_16036_16059 = state_16017__$1;(statearr_16036_16059[(2)] = inst_16011);
(statearr_16036_16059[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (10)))
{var inst_16000 = (state_16017[(2)]);var state_16017__$1 = state_16017;var statearr_16037_16060 = state_16017__$1;(statearr_16037_16060[(2)] = inst_16000);
(statearr_16037_16060[(1)] = (7));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_16018 === (8)))
{var inst_15977 = (state_16017[(7)]);var inst_15985 = (state_16017[(10)]);var inst_15981 = (state_16017[(9)]);var inst_15990 = inst_15977.push(inst_15981);var tmp16034 = inst_15977;var inst_15977__$1 = tmp16034;var inst_15978 = inst_15985;var state_16017__$1 = (function (){var statearr_16038 = state_16017;(statearr_16038[(7)] = inst_15977__$1);
(statearr_16038[(8)] = inst_15978);
(statearr_16038[(14)] = inst_15990);
return statearr_16038;
})();var statearr_16039_16061 = state_16017__$1;(statearr_16039_16061[(2)] = null);
(statearr_16039_16061[(1)] = (2));
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
});})(c__10224__auto___16047,out))
;return ((function (switch__10159__auto__,c__10224__auto___16047,out){
return (function() {
var state_machine__10160__auto__ = null;
var state_machine__10160__auto____0 = (function (){var statearr_16043 = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null];(statearr_16043[(0)] = state_machine__10160__auto__);
(statearr_16043[(1)] = (1));
return statearr_16043;
});
var state_machine__10160__auto____1 = (function (state_16017){while(true){
var ret_value__10161__auto__ = (function (){try{while(true){
var result__10162__auto__ = switch__10159__auto__.call(null,state_16017);if(cljs.core.keyword_identical_QMARK_.call(null,result__10162__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__10162__auto__;
}
break;
}
}catch (e16044){if((e16044 instanceof Object))
{var ex__10163__auto__ = e16044;var statearr_16045_16062 = state_16017;(statearr_16045_16062[(5)] = ex__10163__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_16017);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e16044;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__10161__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__16063 = state_16017;
state_16017 = G__16063;
continue;
}
} else
{return ret_value__10161__auto__;
}
break;
}
});
state_machine__10160__auto__ = function(state_16017){
switch(arguments.length){
case 0:
return state_machine__10160__auto____0.call(this);
case 1:
return state_machine__10160__auto____1.call(this,state_16017);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__10160__auto____0;
state_machine__10160__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__10160__auto____1;
return state_machine__10160__auto__;
})()
;})(switch__10159__auto__,c__10224__auto___16047,out))
})();var state__10226__auto__ = (function (){var statearr_16046 = f__10225__auto__.call(null);(statearr_16046[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__10224__auto___16047);
return statearr_16046;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__10226__auto__);
});})(c__10224__auto___16047,out))
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
