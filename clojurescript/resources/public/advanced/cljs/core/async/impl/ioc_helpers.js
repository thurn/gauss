// Compiled by ClojureScript 0.0-2322
goog.provide('cljs.core.async.impl.ioc_helpers');
goog.require('cljs.core');
goog.require('cljs.core.async.impl.protocols');
goog.require('cljs.core.async.impl.protocols');
cljs.core.async.impl.ioc_helpers.FN_IDX = (0);
cljs.core.async.impl.ioc_helpers.STATE_IDX = (1);
cljs.core.async.impl.ioc_helpers.VALUE_IDX = (2);
cljs.core.async.impl.ioc_helpers.BINDINGS_IDX = (3);
cljs.core.async.impl.ioc_helpers.EXCEPTION_FRAMES = (4);
cljs.core.async.impl.ioc_helpers.CURRENT_EXCEPTION = (5);
cljs.core.async.impl.ioc_helpers.USER_START_IDX = (6);
cljs.core.async.impl.ioc_helpers.aset_object = (function aset_object(arr,idx,o){return (arr[idx][o]);
});
cljs.core.async.impl.ioc_helpers.aget_object = (function aget_object(arr,idx){return (arr[idx]);
});
/**
* Returns true if the machine is in a finished state
*/
cljs.core.async.impl.ioc_helpers.finished_QMARK_ = (function finished_QMARK_(state_array){return cljs.core.keyword_identical_QMARK_((state_array[cljs.core.async.impl.ioc_helpers.STATE_IDX]),cljs.core.constant$keyword$37);
});
cljs.core.async.impl.ioc_helpers.fn_handler = (function fn_handler(f){if(typeof cljs.core.async.impl.ioc_helpers.t13557 !== 'undefined')
{} else
{
/**
* @constructor
*/
cljs.core.async.impl.ioc_helpers.t13557 = (function (f,fn_handler,meta13558){
this.f = f;
this.fn_handler = fn_handler;
this.meta13558 = meta13558;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
cljs.core.async.impl.ioc_helpers.t13557.cljs$lang$type = true;
cljs.core.async.impl.ioc_helpers.t13557.cljs$lang$ctorStr = "cljs.core.async.impl.ioc-helpers/t13557";
cljs.core.async.impl.ioc_helpers.t13557.cljs$lang$ctorPrWriter = (function (this__4127__auto__,writer__4128__auto__,opt__4129__auto__){return cljs.core._write(writer__4128__auto__,"cljs.core.async.impl.ioc-helpers/t13557");
});
cljs.core.async.impl.ioc_helpers.t13557.prototype.cljs$core$async$impl$protocols$Handler$ = true;
cljs.core.async.impl.ioc_helpers.t13557.prototype.cljs$core$async$impl$protocols$Handler$active_QMARK_$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return true;
});
cljs.core.async.impl.ioc_helpers.t13557.prototype.cljs$core$async$impl$protocols$Handler$commit$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return self__.f;
});
cljs.core.async.impl.ioc_helpers.t13557.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_13559){var self__ = this;
var _13559__$1 = this;return self__.meta13558;
});
cljs.core.async.impl.ioc_helpers.t13557.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_13559,meta13558__$1){var self__ = this;
var _13559__$1 = this;return (new cljs.core.async.impl.ioc_helpers.t13557(self__.f,self__.fn_handler,meta13558__$1));
});
cljs.core.async.impl.ioc_helpers.__GT_t13557 = (function __GT_t13557(f__$1,fn_handler__$1,meta13558){return (new cljs.core.async.impl.ioc_helpers.t13557(f__$1,fn_handler__$1,meta13558));
});
}
return (new cljs.core.async.impl.ioc_helpers.t13557(f,fn_handler,null));
});
cljs.core.async.impl.ioc_helpers.run_state_machine = (function run_state_machine(state){return cljs.core.async.impl.ioc_helpers.aget_object(state,cljs.core.async.impl.ioc_helpers.FN_IDX).call(null,state);
});
cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped = (function run_state_machine_wrapped(state){try{return cljs.core.async.impl.ioc_helpers.run_state_machine(state);
}catch (e13561){if((e13561 instanceof Object))
{var ex = e13561;cljs.core.async.impl.ioc_helpers.aget_object(state,cljs.core.async.impl.ioc_helpers.USER_START_IDX).cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1(null);
throw ex;
} else
{throw e13561;

}
}});
cljs.core.async.impl.ioc_helpers.take_BANG_ = (function take_BANG_(state,blk,c){var temp__4124__auto__ = c.cljs$core$async$impl$protocols$ReadPort$take_BANG_$arity$2(null,cljs.core.async.impl.ioc_helpers.fn_handler((function (x){var statearr_13564_13566 = state;(statearr_13564_13566[cljs.core.async.impl.ioc_helpers.VALUE_IDX] = x);
(statearr_13564_13566[cljs.core.async.impl.ioc_helpers.STATE_IDX] = blk);
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state);
})));if(cljs.core.truth_(temp__4124__auto__))
{var cb = temp__4124__auto__;var statearr_13565_13567 = state;(statearr_13565_13567[cljs.core.async.impl.ioc_helpers.VALUE_IDX] = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(cb) : cljs.core.deref.call(null,cb)));
(statearr_13565_13567[cljs.core.async.impl.ioc_helpers.STATE_IDX] = blk);
return cljs.core.constant$keyword$38;
} else
{return null;
}
});
cljs.core.async.impl.ioc_helpers.put_BANG_ = (function put_BANG_(state,blk,c,val){var temp__4124__auto__ = c.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3(null,val,cljs.core.async.impl.ioc_helpers.fn_handler((function (ret_val){var statearr_13570_13572 = state;(statearr_13570_13572[cljs.core.async.impl.ioc_helpers.VALUE_IDX] = ret_val);
(statearr_13570_13572[cljs.core.async.impl.ioc_helpers.STATE_IDX] = blk);
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state);
})));if(cljs.core.truth_(temp__4124__auto__))
{var cb = temp__4124__auto__;var statearr_13571_13573 = state;(statearr_13571_13573[cljs.core.async.impl.ioc_helpers.VALUE_IDX] = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(cb) : cljs.core.deref.call(null,cb)));
(statearr_13571_13573[cljs.core.async.impl.ioc_helpers.STATE_IDX] = blk);
return cljs.core.constant$keyword$38;
} else
{return null;
}
});
/**
* @param {...*} var_args
*/
cljs.core.async.impl.ioc_helpers.ioc_alts_BANG_ = (function() { 
var ioc_alts_BANG___delegate = function (state,cont_block,ports,p__13574){var map__13579 = p__13574;var map__13579__$1 = ((cljs.core.seq_QMARK_(map__13579))?cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.hash_map,map__13579):map__13579);var opts = map__13579__$1;var statearr_13580_13583 = state;(statearr_13580_13583[cljs.core.async.impl.ioc_helpers.STATE_IDX] = cont_block);
var temp__4126__auto__ = cljs.core.async.do_alts(((function (map__13579,map__13579__$1,opts){
return (function (val){var statearr_13581_13584 = state;(statearr_13581_13584[cljs.core.async.impl.ioc_helpers.VALUE_IDX] = val);
return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state);
});})(map__13579,map__13579__$1,opts))
,ports,opts);if(cljs.core.truth_(temp__4126__auto__))
{var cb = temp__4126__auto__;var statearr_13582_13585 = state;(statearr_13582_13585[cljs.core.async.impl.ioc_helpers.VALUE_IDX] = (cljs.core.deref.cljs$core$IFn$_invoke$arity$1 ? cljs.core.deref.cljs$core$IFn$_invoke$arity$1(cb) : cljs.core.deref.call(null,cb)));
return cljs.core.constant$keyword$38;
} else
{return null;
}
};
var ioc_alts_BANG_ = function (state,cont_block,ports,var_args){
var p__13574 = null;if (arguments.length > 3) {
  p__13574 = cljs.core.array_seq(Array.prototype.slice.call(arguments, 3),0);} 
return ioc_alts_BANG___delegate.call(this,state,cont_block,ports,p__13574);};
ioc_alts_BANG_.cljs$lang$maxFixedArity = 3;
ioc_alts_BANG_.cljs$lang$applyTo = (function (arglist__13586){
var state = cljs.core.first(arglist__13586);
arglist__13586 = cljs.core.next(arglist__13586);
var cont_block = cljs.core.first(arglist__13586);
arglist__13586 = cljs.core.next(arglist__13586);
var ports = cljs.core.first(arglist__13586);
var p__13574 = cljs.core.rest(arglist__13586);
return ioc_alts_BANG___delegate(state,cont_block,ports,p__13574);
});
ioc_alts_BANG_.cljs$core$IFn$_invoke$arity$variadic = ioc_alts_BANG___delegate;
return ioc_alts_BANG_;
})()
;
cljs.core.async.impl.ioc_helpers.return_chan = (function return_chan(state,value){var c = (state[cljs.core.async.impl.ioc_helpers.USER_START_IDX]);if((value == null))
{} else
{c.cljs$core$async$impl$protocols$WritePort$put_BANG_$arity$3(null,value,cljs.core.async.impl.ioc_helpers.fn_handler(((function (c){
return (function (){return null;
});})(c))
));
}
c.cljs$core$async$impl$protocols$Channel$close_BANG_$arity$1(null);
return c;
});

/**
* @constructor
* @param {*} catch_block
* @param {*} catch_exception
* @param {*} finally_block
* @param {*} continue_block
* @param {*} prev
* @param {*} __meta
* @param {*} __extmap
* @param {*=} __meta 
* @param {*=} __extmap
*/
cljs.core.async.impl.ioc_helpers.ExceptionFrame = (function (catch_block,catch_exception,finally_block,continue_block,prev,__meta,__extmap){
this.catch_block = catch_block;
this.catch_exception = catch_exception;
this.finally_block = finally_block;
this.continue_block = continue_block;
this.prev = prev;
this.__meta = __meta;
this.__extmap = __extmap;
this.cljs$lang$protocol_mask$partition0$ = 2229667594;
this.cljs$lang$protocol_mask$partition1$ = 8192;
if(arguments.length>5){
this.__meta = __meta;
this.__extmap = __extmap;
} else {
this.__meta=null;
this.__extmap=null;
}
})
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this__4146__auto__,k__4147__auto__){var self__ = this;
var this__4146__auto____$1 = this;return cljs.core._lookup.cljs$core$IFn$_invoke$arity$3(this__4146__auto____$1,k__4147__auto__,null);
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this__4148__auto__,k13588,else__4149__auto__){var self__ = this;
var this__4148__auto____$1 = this;var G__13590 = (((k13588 instanceof cljs.core.Keyword))?k13588.fqn:null);switch (G__13590) {
case "prev":
return self__.prev;

break;
case "continue-block":
return self__.continue_block;

break;
case "finally-block":
return self__.finally_block;

break;
case "catch-exception":
return self__.catch_exception;

break;
case "catch-block":
return self__.catch_block;

break;
default:
return cljs.core.get.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k13588,else__4149__auto__);

}
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (this__4160__auto__,writer__4161__auto__,opts__4162__auto__){var self__ = this;
var this__4160__auto____$1 = this;var pr_pair__4163__auto__ = ((function (this__4160__auto____$1){
return (function (keyval__4164__auto__){return cljs.core.pr_sequential_writer(writer__4161__auto__,cljs.core.pr_writer,""," ","",opts__4162__auto__,keyval__4164__auto__);
});})(this__4160__auto____$1))
;return cljs.core.pr_sequential_writer(writer__4161__auto__,pr_pair__4163__auto__,"#cljs.core.async.impl.ioc-helpers.ExceptionFrame{",", ","}",opts__4162__auto__,cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 5, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$39,self__.catch_block],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$40,self__.catch_exception],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$41,self__.finally_block],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$42,self__.continue_block],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$43,self__.prev],null))], null),self__.__extmap));
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$IMeta$_meta$arity$1 = (function (this__4144__auto__){var self__ = this;
var this__4144__auto____$1 = this;return self__.__meta;
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (this__4140__auto__){var self__ = this;
var this__4140__auto____$1 = this;return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(self__.catch_block,self__.catch_exception,self__.finally_block,self__.continue_block,self__.prev,self__.__meta,self__.__extmap,self__.__hash));
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$ICounted$_count$arity$1 = (function (this__4150__auto__){var self__ = this;
var this__4150__auto____$1 = this;return (5 + cljs.core.count(self__.__extmap));
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$IHash$_hash$arity$1 = (function (this__4141__auto__){var self__ = this;
var this__4141__auto____$1 = this;var h__3971__auto__ = self__.__hash;if(!((h__3971__auto__ == null)))
{return h__3971__auto__;
} else
{var h__3971__auto____$1 = cljs.core.hash_imap(this__4141__auto____$1);self__.__hash = h__3971__auto____$1;
return h__3971__auto____$1;
}
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (this__4142__auto__,other__4143__auto__){var self__ = this;
var this__4142__auto____$1 = this;if(cljs.core.truth_((function (){var and__3548__auto__ = other__4143__auto__;if(cljs.core.truth_(and__3548__auto__))
{return ((this__4142__auto____$1.constructor === other__4143__auto__.constructor)) && (cljs.core.equiv_map(this__4142__auto____$1,other__4143__auto__));
} else
{return and__3548__auto__;
}
})()))
{return true;
} else
{return false;
}
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (this__4155__auto__,k__4156__auto__){var self__ = this;
var this__4155__auto____$1 = this;if(cljs.core.contains_QMARK_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 5, [cljs.core.constant$keyword$41,null,cljs.core.constant$keyword$39,null,cljs.core.constant$keyword$40,null,cljs.core.constant$keyword$43,null,cljs.core.constant$keyword$42,null], null), null),k__4156__auto__))
{return cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(cljs.core.with_meta(cljs.core.into.cljs$core$IFn$_invoke$arity$2(cljs.core.PersistentArrayMap.EMPTY,this__4155__auto____$1),self__.__meta),k__4156__auto__);
} else
{return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(self__.catch_block,self__.catch_exception,self__.finally_block,self__.continue_block,self__.prev,self__.__meta,cljs.core.not_empty(cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(self__.__extmap,k__4156__auto__)),null));
}
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (this__4153__auto__,k__4154__auto__,G__13587){var self__ = this;
var this__4153__auto____$1 = this;var pred__13591 = cljs.core.keyword_identical_QMARK_;var expr__13592 = k__4154__auto__;if(cljs.core.truth_((pred__13591.cljs$core$IFn$_invoke$arity$2 ? pred__13591.cljs$core$IFn$_invoke$arity$2(cljs.core.constant$keyword$39,expr__13592) : pred__13591.call(null,cljs.core.constant$keyword$39,expr__13592))))
{return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(G__13587,self__.catch_exception,self__.finally_block,self__.continue_block,self__.prev,self__.__meta,self__.__extmap,null));
} else
{if(cljs.core.truth_((pred__13591.cljs$core$IFn$_invoke$arity$2 ? pred__13591.cljs$core$IFn$_invoke$arity$2(cljs.core.constant$keyword$40,expr__13592) : pred__13591.call(null,cljs.core.constant$keyword$40,expr__13592))))
{return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(self__.catch_block,G__13587,self__.finally_block,self__.continue_block,self__.prev,self__.__meta,self__.__extmap,null));
} else
{if(cljs.core.truth_((pred__13591.cljs$core$IFn$_invoke$arity$2 ? pred__13591.cljs$core$IFn$_invoke$arity$2(cljs.core.constant$keyword$41,expr__13592) : pred__13591.call(null,cljs.core.constant$keyword$41,expr__13592))))
{return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(self__.catch_block,self__.catch_exception,G__13587,self__.continue_block,self__.prev,self__.__meta,self__.__extmap,null));
} else
{if(cljs.core.truth_((pred__13591.cljs$core$IFn$_invoke$arity$2 ? pred__13591.cljs$core$IFn$_invoke$arity$2(cljs.core.constant$keyword$42,expr__13592) : pred__13591.call(null,cljs.core.constant$keyword$42,expr__13592))))
{return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(self__.catch_block,self__.catch_exception,self__.finally_block,G__13587,self__.prev,self__.__meta,self__.__extmap,null));
} else
{if(cljs.core.truth_((pred__13591.cljs$core$IFn$_invoke$arity$2 ? pred__13591.cljs$core$IFn$_invoke$arity$2(cljs.core.constant$keyword$43,expr__13592) : pred__13591.call(null,cljs.core.constant$keyword$43,expr__13592))))
{return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(self__.catch_block,self__.catch_exception,self__.finally_block,self__.continue_block,G__13587,self__.__meta,self__.__extmap,null));
} else
{return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(self__.catch_block,self__.catch_exception,self__.finally_block,self__.continue_block,self__.prev,self__.__meta,cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(self__.__extmap,k__4154__auto__,G__13587),null));
}
}
}
}
}
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this__4158__auto__){var self__ = this;
var this__4158__auto____$1 = this;return cljs.core.seq(cljs.core.concat.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 5, 5, cljs.core.PersistentVector.EMPTY_NODE, [(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$39,self__.catch_block],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$40,self__.catch_exception],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$41,self__.finally_block],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$42,self__.continue_block],null)),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[cljs.core.constant$keyword$43,self__.prev],null))], null),self__.__extmap));
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (this__4145__auto__,G__13587){var self__ = this;
var this__4145__auto____$1 = this;return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(self__.catch_block,self__.catch_exception,self__.finally_block,self__.continue_block,self__.prev,G__13587,self__.__extmap,self__.__hash));
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.prototype.cljs$core$ICollection$_conj$arity$2 = (function (this__4151__auto__,entry__4152__auto__){var self__ = this;
var this__4151__auto____$1 = this;if(cljs.core.vector_QMARK_(entry__4152__auto__))
{return cljs.core._assoc(this__4151__auto____$1,cljs.core._nth.cljs$core$IFn$_invoke$arity$2(entry__4152__auto__,(0)),cljs.core._nth.cljs$core$IFn$_invoke$arity$2(entry__4152__auto__,(1)));
} else
{return cljs.core.reduce.cljs$core$IFn$_invoke$arity$3(cljs.core._conj,this__4151__auto____$1,entry__4152__auto__);
}
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.cljs$lang$type = true;
cljs.core.async.impl.ioc_helpers.ExceptionFrame.cljs$lang$ctorPrSeq = (function (this__4180__auto__){return cljs.core._conj(cljs.core.List.EMPTY,"cljs.core.async.impl.ioc-helpers/ExceptionFrame");
});
cljs.core.async.impl.ioc_helpers.ExceptionFrame.cljs$lang$ctorPrWriter = (function (this__4180__auto__,writer__4181__auto__){return cljs.core._write(writer__4181__auto__,"cljs.core.async.impl.ioc-helpers/ExceptionFrame");
});
cljs.core.async.impl.ioc_helpers.__GT_ExceptionFrame = (function __GT_ExceptionFrame(catch_block,catch_exception,finally_block,continue_block,prev){return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(catch_block,catch_exception,finally_block,continue_block,prev));
});
cljs.core.async.impl.ioc_helpers.map__GT_ExceptionFrame = (function map__GT_ExceptionFrame(G__13589){return (new cljs.core.async.impl.ioc_helpers.ExceptionFrame(cljs.core.constant$keyword$39.cljs$core$IFn$_invoke$arity$1(G__13589),cljs.core.constant$keyword$40.cljs$core$IFn$_invoke$arity$1(G__13589),cljs.core.constant$keyword$41.cljs$core$IFn$_invoke$arity$1(G__13589),cljs.core.constant$keyword$42.cljs$core$IFn$_invoke$arity$1(G__13589),cljs.core.constant$keyword$43.cljs$core$IFn$_invoke$arity$1(G__13589),null,cljs.core.dissoc.cljs$core$IFn$_invoke$arity$variadic(G__13589,cljs.core.constant$keyword$39,cljs.core.array_seq([cljs.core.constant$keyword$40,cljs.core.constant$keyword$41,cljs.core.constant$keyword$42,cljs.core.constant$keyword$43], 0))));
});
cljs.core.async.impl.ioc_helpers.add_exception_frame = (function add_exception_frame(state,catch_block,catch_exception,finally_block,continue_block){var statearr_13596 = state;(statearr_13596[cljs.core.async.impl.ioc_helpers.EXCEPTION_FRAMES] = cljs.core.async.impl.ioc_helpers.__GT_ExceptionFrame(catch_block,catch_exception,finally_block,continue_block,cljs.core.async.impl.ioc_helpers.aget_object(state,cljs.core.async.impl.ioc_helpers.EXCEPTION_FRAMES)));
return statearr_13596;
});
cljs.core.async.impl.ioc_helpers.process_exception = (function process_exception(state){while(true){
var exception_frame = cljs.core.async.impl.ioc_helpers.aget_object(state,cljs.core.async.impl.ioc_helpers.EXCEPTION_FRAMES);var catch_block = cljs.core.constant$keyword$39.cljs$core$IFn$_invoke$arity$1(exception_frame);var catch_exception = cljs.core.constant$keyword$40.cljs$core$IFn$_invoke$arity$1(exception_frame);var exception = cljs.core.async.impl.ioc_helpers.aget_object(state,cljs.core.async.impl.ioc_helpers.CURRENT_EXCEPTION);if(cljs.core.truth_((function (){var and__3548__auto__ = exception;if(cljs.core.truth_(and__3548__auto__))
{return cljs.core.not(exception_frame);
} else
{return and__3548__auto__;
}
})()))
{throw exception;
} else
{if(cljs.core.truth_((function (){var and__3548__auto__ = exception;if(cljs.core.truth_(and__3548__auto__))
{var and__3548__auto____$1 = catch_block;if(cljs.core.truth_(and__3548__auto____$1))
{return (exception instanceof catch_exception);
} else
{return and__3548__auto____$1;
}
} else
{return and__3548__auto__;
}
})()))
{var statearr_13602 = state;(statearr_13602[cljs.core.async.impl.ioc_helpers.STATE_IDX] = catch_block);
(statearr_13602[cljs.core.async.impl.ioc_helpers.VALUE_IDX] = exception);
(statearr_13602[cljs.core.async.impl.ioc_helpers.CURRENT_EXCEPTION] = null);
(statearr_13602[cljs.core.async.impl.ioc_helpers.EXCEPTION_FRAMES] = cljs.core.assoc.cljs$core$IFn$_invoke$arity$variadic(exception_frame,cljs.core.constant$keyword$39,null,cljs.core.array_seq([cljs.core.constant$keyword$40,null], 0)));
return statearr_13602;
} else
{if(cljs.core.truth_((function (){var and__3548__auto__ = exception;if(cljs.core.truth_(and__3548__auto__))
{return (cljs.core.not(catch_block)) && (cljs.core.not(cljs.core.constant$keyword$41.cljs$core$IFn$_invoke$arity$1(exception_frame)));
} else
{return and__3548__auto__;
}
})()))
{var statearr_13603_13607 = state;(statearr_13603_13607[cljs.core.async.impl.ioc_helpers.EXCEPTION_FRAMES] = cljs.core.constant$keyword$43.cljs$core$IFn$_invoke$arity$1(exception_frame));
{
var G__13608 = state;
state = G__13608;
continue;
}
} else
{if(cljs.core.truth_((function (){var and__3548__auto__ = exception;if(cljs.core.truth_(and__3548__auto__))
{var and__3548__auto____$1 = cljs.core.not(catch_block);if(and__3548__auto____$1)
{return cljs.core.constant$keyword$41.cljs$core$IFn$_invoke$arity$1(exception_frame);
} else
{return and__3548__auto____$1;
}
} else
{return and__3548__auto__;
}
})()))
{var statearr_13604 = state;(statearr_13604[cljs.core.async.impl.ioc_helpers.STATE_IDX] = cljs.core.constant$keyword$41.cljs$core$IFn$_invoke$arity$1(exception_frame));
(statearr_13604[cljs.core.async.impl.ioc_helpers.EXCEPTION_FRAMES] = cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(exception_frame,cljs.core.constant$keyword$41,null));
return statearr_13604;
} else
{if(cljs.core.truth_((function (){var and__3548__auto__ = cljs.core.not(exception);if(and__3548__auto__)
{return cljs.core.constant$keyword$41.cljs$core$IFn$_invoke$arity$1(exception_frame);
} else
{return and__3548__auto__;
}
})()))
{var statearr_13605 = state;(statearr_13605[cljs.core.async.impl.ioc_helpers.STATE_IDX] = cljs.core.constant$keyword$41.cljs$core$IFn$_invoke$arity$1(exception_frame));
(statearr_13605[cljs.core.async.impl.ioc_helpers.EXCEPTION_FRAMES] = cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(exception_frame,cljs.core.constant$keyword$41,null));
return statearr_13605;
} else
{if((cljs.core.not(exception)) && (cljs.core.not(cljs.core.constant$keyword$41.cljs$core$IFn$_invoke$arity$1(exception_frame))))
{var statearr_13606 = state;(statearr_13606[cljs.core.async.impl.ioc_helpers.STATE_IDX] = cljs.core.constant$keyword$42.cljs$core$IFn$_invoke$arity$1(exception_frame));
(statearr_13606[cljs.core.async.impl.ioc_helpers.EXCEPTION_FRAMES] = cljs.core.constant$keyword$43.cljs$core$IFn$_invoke$arity$1(exception_frame));
return statearr_13606;
} else
{throw (new Error("No matching clause"));

}
}
}
}
}
}
break;
}
});
