// Compiled by ClojureScript 0.0-2311
goog.provide('clojurescript.core');
goog.require('cljs.core');
goog.require('cljs.core.async');
goog.require('weasel.repl');
goog.require('weasel.repl');
goog.require('cljs.core.async');
goog.require('cljs.core.async');
weasel.repl.connect.call(null,"ws://localhost:9001",new cljs.core.Keyword(null,"verbose","verbose",1694226060),true);
clojurescript.core.ch = cljs.core.async.chan.call(null,(1));
var c__5717__auto___9095 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__5717__auto___9095){
return (function (){var f__5718__auto__ = (function (){var switch__5702__auto__ = ((function (c__5717__auto___9095){
return (function (state_9085){var state_val_9086 = (state_9085[(1)]);if((state_val_9086 === (3)))
{var inst_9082 = (state_9085[(2)]);var inst_9083 = alert(inst_9082);var state_9085__$1 = state_9085;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_9085__$1,inst_9083);
} else
{if((state_val_9086 === (2)))
{var inst_9080 = (state_9085[(2)]);var state_9085__$1 = (function (){var statearr_9087 = state_9085;(statearr_9087[(7)] = inst_9080);
return statearr_9087;
})();return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_9085__$1,(3),clojurescript.core.ch);
} else
{if((state_val_9086 === (1)))
{var state_9085__$1 = state_9085;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_9085__$1,(2),clojurescript.core.ch,"hello");
} else
{return null;
}
}
}
});})(c__5717__auto___9095))
;return ((function (switch__5702__auto__,c__5717__auto___9095){
return (function() {
var state_machine__5703__auto__ = null;
var state_machine__5703__auto____0 = (function (){var statearr_9091 = [null,null,null,null,null,null,null,null];(statearr_9091[(0)] = state_machine__5703__auto__);
(statearr_9091[(1)] = (1));
return statearr_9091;
});
var state_machine__5703__auto____1 = (function (state_9085){while(true){
var ret_value__5704__auto__ = (function (){try{while(true){
var result__5705__auto__ = switch__5702__auto__.call(null,state_9085);if(cljs.core.keyword_identical_QMARK_.call(null,result__5705__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__5705__auto__;
}
break;
}
}catch (e9092){if((e9092 instanceof Object))
{var ex__5706__auto__ = e9092;var statearr_9093_9096 = state_9085;(statearr_9093_9096[(5)] = ex__5706__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_9085);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e9092;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__5704__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__9097 = state_9085;
state_9085 = G__9097;
continue;
}
} else
{return ret_value__5704__auto__;
}
break;
}
});
state_machine__5703__auto__ = function(state_9085){
switch(arguments.length){
case 0:
return state_machine__5703__auto____0.call(this);
case 1:
return state_machine__5703__auto____1.call(this,state_9085);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__5703__auto____0;
state_machine__5703__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__5703__auto____1;
return state_machine__5703__auto__;
})()
;})(switch__5702__auto__,c__5717__auto___9095))
})();var state__5719__auto__ = (function (){var statearr_9094 = f__5718__auto__.call(null);(statearr_9094[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__5717__auto___9095);
return statearr_9094;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__5719__auto__);
});})(c__5717__auto___9095))
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
