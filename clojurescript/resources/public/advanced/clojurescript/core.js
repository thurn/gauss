// Compiled by ClojureScript 0.0-2322
goog.provide('clojurescript.core');
goog.require('cljs.core');
goog.require('cljs.core.async');
goog.require('weasel.repl');
goog.require('weasel.repl');
goog.require('cljs.core.async');
goog.require('cljs.core.async');
weasel.repl.connect.cljs$core$IFn$_invoke$arity$variadic("ws://localhost:9001",cljs.core.array_seq([cljs.core.constant$keyword$32,true], 0));
clojurescript.core.ch = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));
var c__5724__auto___9102 = cljs.core.async.chan.cljs$core$IFn$_invoke$arity$1((1));cljs.core.async.impl.dispatch.run(((function (c__5724__auto___9102){
return (function (){var f__5725__auto__ = (function (){var switch__5709__auto__ = ((function (c__5724__auto___9102){
return (function (state_9092){var state_val_9093 = (state_9092[(1)]);if((state_val_9093 === (3)))
{var inst_9089 = (state_9092[(2)]);var inst_9090 = alert(inst_9089);var state_9092__$1 = state_9092;return cljs.core.async.impl.ioc_helpers.return_chan(state_9092__$1,inst_9090);
} else
{if((state_val_9093 === (2)))
{var inst_9087 = (state_9092[(2)]);var state_9092__$1 = (function (){var statearr_9094 = state_9092;(statearr_9094[(7)] = inst_9087);
return statearr_9094;
})();return cljs.core.async.impl.ioc_helpers.take_BANG_(state_9092__$1,(3),clojurescript.core.ch);
} else
{if((state_val_9093 === (1)))
{var state_9092__$1 = state_9092;return cljs.core.async.impl.ioc_helpers.put_BANG_(state_9092__$1,(2),clojurescript.core.ch,"hello");
} else
{return null;
}
}
}
});})(c__5724__auto___9102))
;return ((function (switch__5709__auto__,c__5724__auto___9102){
return (function() {
var state_machine__5710__auto__ = null;
var state_machine__5710__auto____0 = (function (){var statearr_9098 = [null,null,null,null,null,null,null,null];(statearr_9098[(0)] = state_machine__5710__auto__);
(statearr_9098[(1)] = (1));
return statearr_9098;
});
var state_machine__5710__auto____1 = (function (state_9092){while(true){
var ret_value__5711__auto__ = (function (){try{while(true){
var result__5712__auto__ = switch__5709__auto__(state_9092);if(cljs.core.keyword_identical_QMARK_(result__5712__auto__,cljs.core.constant$keyword$38))
{{
continue;
}
} else
{return result__5712__auto__;
}
break;
}
}catch (e9099){if((e9099 instanceof Object))
{var ex__5713__auto__ = e9099;var statearr_9100_9103 = state_9092;(statearr_9100_9103[(5)] = ex__5713__auto__);
cljs.core.async.impl.ioc_helpers.process_exception(state_9092);
return cljs.core.constant$keyword$38;
} else
{throw e9099;

}
}})();if(cljs.core.keyword_identical_QMARK_(ret_value__5711__auto__,cljs.core.constant$keyword$38))
{{
var G__9104 = state_9092;
state_9092 = G__9104;
continue;
}
} else
{return ret_value__5711__auto__;
}
break;
}
});
state_machine__5710__auto__ = function(state_9092){
switch(arguments.length){
case 0:
return state_machine__5710__auto____0.call(this);
case 1:
return state_machine__5710__auto____1.call(this,state_9092);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5710__auto____0;
state_machine__5710__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5710__auto____1;
return state_machine__5710__auto__;
})()
;})(switch__5709__auto__,c__5724__auto___9102))
})();var state__5726__auto__ = (function (){var statearr_9101 = (f__5725__auto__.cljs$core$IFn$_invoke$arity$0 ? f__5725__auto__.cljs$core$IFn$_invoke$arity$0() : f__5725__auto__.call(null));(statearr_9101[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5724__auto___9102);
return statearr_9101;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped(state__5726__auto__);
});})(c__5724__auto___9102))
);
clojurescript.core.AssetLoader = PIXI.AssetLoader;
clojurescript.core.BaseTexture = PIXI.BaseTexture;
clojurescript.core.Texture = PIXI.Texture;
clojurescript.core.CanvasRenderer = PIXI.CanvasRenderer;
clojurescript.core.WebGLRenderer = PIXI.WebGLRenderer;
clojurescript.core.Stage = PIXI.Stage;
clojurescript.core.DisplayObjectContainer = PIXI.DisplayObjectContainer;
clojurescript.core.Sprite = PIXI.Sprite;
clojurescript.core.MovieClip = PIXI.MovieClip;
clojurescript.core.Graphics = PIXI.Graphics;
clojurescript.core.Text = PIXI.Text;
clojurescript.core.Point = PIXI.Point;
clojurescript.core.stage = (new clojurescript.core.Stage((6750105)));
clojurescript.core.renderer = PIXI.autoDetectRenderer((400),(300));
document.body.appendChild(clojurescript.core.renderer.view);
clojurescript.core.texture = clojurescript.core.Texture.fromImage("bunny.png");
clojurescript.core.bunny = (new clojurescript.core.Sprite(clojurescript.core.texture));
clojurescript.core.bunny.anchor.x = 0.5;
clojurescript.core.bunny.anchor.y = 0.5;
clojurescript.core.bunny.position.x = (200);
clojurescript.core.bunny.position.y = (150);
clojurescript.core.stage.addChild(clojurescript.core.bunny);
clojurescript.core.animate = (function animate(){requestAnimFrame(animate);
clojurescript.core.bunny.rotation = (0.1 + clojurescript.core.bunny.rotation);
return clojurescript.core.renderer.render(clojurescript.core.stage);
});
requestAnimFrame(clojurescript.core.animate);
