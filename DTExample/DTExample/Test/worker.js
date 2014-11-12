postMessage("Starting work");
onmessage = function(event) {
  postMessage("Hi " + event.data);
};