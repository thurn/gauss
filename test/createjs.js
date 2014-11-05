var init = function() {
  var stage = new createjs.Stage("demoCanvas");
  stage.autoClear = true;

  var circle = new createjs.Shape();
  circle.graphics.beginFill("red").drawCircle(0, 0, 50);
  circle.x = 100;
  circle.y = 100;

  var onComplete = function() {};

  createjs.Tween.get(circle).to({x: 400}, 500).call(onComplete);

  stage.addChild(circle);

  createjs.Ticker.addEventListener("tick", stage);
  stage.update();
};