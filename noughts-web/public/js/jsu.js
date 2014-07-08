goog.provide("jsu");

goog.require("nts.JavascriptUtils");

/**
 * Converts a JS array to a java.util.List.
 * @param {!Array.<?>=} jsArray A javascript array.
 * @return {?} An opaque instance of java.util.List.
 */
jsu.javaList = function(jsArray) {
  jsArray = jsArray || [];
  var result = nts.JavascriptUtils.emptyListWrapper();
  for (var i = 0; i < jsArray.length; ++i) {
    result.add(jsArray[i]);
  }
  return result.getList();
}

/**
 * Converts a Java list to a JS array.
 * @param {?} javaList An opaque java.util.List instance.
 * @return {!Array.<?>} A javascript array.
 */
jsu.jsArray = function(javaList) {
  var result = [];
  var wrapper = nts.JavascriptUtils.listWrapper(javaList);
  for (var i = 0; i < wrapper.size(); ++i) {
    result.push(wrapper.get(i));
  }
  return result;
}

/**
 * Converts a JS object to a java.util.Map.
 * @param {!Object=} jsObject A javascript object.
 * @return {?} An opaque instance of java.util.Map.
 */
jsu.javaMap = function(jsObject) {
  jsObject = jsObject || {};
  var result = nts.JavascriptUtils.emptyMapWrapper();
  for (var key in jsObject) {
    if (jsObject.hasOwnProperty(key)) {
      result.put(key, jsObject[key]);
    }
  }
  return result.getMap();
}

/**
 * Converts a Java map to a JS object.
 * @param {?} javaMap An opaque java.util.Map instance.
 * @return {!Object} A javascript object.
 */
jsu.jsObject = function(javaMap) {
  var mapWrapper = nts.JavascriptUtils.mapWrapper(javaMap);
  var result = {};
  for (var i = 0; i < mapWrapper.getKeyCount(); ++i) {
    var key = mapWrapper.getKey(i);
    result[key] = mapWrapper.get(key);
  }
  return result;
}
