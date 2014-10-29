/**
 * @fileoverview
 * @suppress {checkTypes|undefinedNames}
 */

/**
 * @constructor
 * @param {string} url
 * @return {!Firebase}
 */
function Firebase(url) {}

/**
 * @param {string} authToken
 * @param {function()=} onComplete
 * @param {function()=} onCancel
 */
Firebase.prototype.auth = function(authToken, onComplete, onCancel) {}

Firebase.prototype.unauth = function() {}

/**
 * @param {string} child
 * @return {!Firebase}
 */
Firebase.prototype.child = function(child) {}

/**
 * @return {Firebase}
 */
Firebase.prototype.parent = function() {}

/**
 * @return {!Firebase}
 */
Firebase.prototype.root = function() {}

/**
 * @return {string}
 */
Firebase.prototype.name = function() {}

/**
 * @return {string}
 */
Firebase.prototype.toString = function() {}

/**
 * @param {?} value
 * @param {function()=} onComplete
 */
Firebase.prototype.set = function(value, onComplete) {}

/**
 * @param {?} value
 * @param {function()=} onComplete
 */
Firebase.prototype.update = function(value, onComplete) {}

/**
 * @param {function()=} onComplete
 */
Firebase.prototype.remove = function(onComplete) {}

/**
 * @param {?=} value
 * @param {function()=} onComplete
 * @return {!Firebase}
 */
Firebase.prototype.push = function(value, onComplete) {}

/**
 * @param {?} value
 * @param {?} priority
 * @param {function()=} onComplete
 */
Firebase.prototype.setWithPriority = function(value, priority, onComplete) {}

/**
 * @param {?} priority
 * @param {function()=} onComplete
 */
Firebase.prototype.setPriority = function(priority, onComplete) {}

/**
 * @param {function()} updateFunction
 * @param {function()=} onComplete
 * @param {boolean=} applyLocally
 */
Firebase.prototype.transaction = function(updateFunction, onComplete, applyLocally) {}

/**
 * @param {string} eventType
 * @param {function(!DataSnapshot)} callback
 * @param {function()=} cancelCallback
 * @param {?=} context
 * @return {function()}
 */
Firebase.prototype.on = function(eventType, callback, cancelCallback, context) {}

/**
 * @param {string} eventType
 * @param {function()} callback
 * @param {?=} context
 */
Firebase.prototype.off = function(eventType, callback, context) {}

/**
 * @param {string} eventType
 * @param {function(!DataSnapshot)} successCallback
 * @param {function()=} failureCallback
 * @param {?=} context
 */
Firebase.prototype.once = function(eventType, successCallback, failureCallback, context) {}

/**
 * @param {number} limit
 */
Firebase.prototype.limit = function(limit) {}

/**
 * @param {?} priority
 * @param {string} name
 * @return {!Firebase}
 */
Firebase.prototype.startAt = function(priority, name) {}

/**
 * @param {?} priority
 * @param {string} name
 * @return {!Firebase}
 */
Firebase.prototype.endAt = function(priority, name) {}

Firebase.prototype.goOffline = function() {}

Firebase.prototype.goOnline = function() {}

// Queries are not currently recognized as a separate type.

/**
 * @return {!Firebase}
 */
Firebase.prototype.ref = function() {}

/**
 * @constructor
 * @return {!DataSnapshot}
 */
function DataSnapshot() {}

/**
 * @return {?}
 */
DataSnapshot.prototype.val = function() {}

/**
 * @param {string} childPath
 * @return {!DataSnapshot}
 */
DataSnapshot.prototype.child = function(childPath) {}

/**
 * @param {function()} childAction
 * @return {boolean}
 */
DataSnapshot.prototype.forEach = function(childAction) {}

/**
 * @param {string} childPath
 * @return {boolean}
 */
DataSnapshot.prototype.hasChild = function(childPath) {}

/**
 * @return {boolean}
 */
DataSnapshot.prototype.hasChildren = function() {}

/**
 * @return {string}
 */
DataSnapshot.prototype.name = function() {}

/**
 * @return {number}
 */
DataSnapshot.prototype.numChildren = function() {}

/**
 * @return {!Firebase}
 */
DataSnapshot.prototype.ref = function() {}

/**
 * @return {?}
 */
DataSnapshot.prototype.getPriority = function() {}

/**
 * @return {?}
 */
DataSnapshot.prototype.exportVal = function() {}
