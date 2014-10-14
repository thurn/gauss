ErroringFirebase is a simple utility for testing code that encounters Firebase errors. It's able to specify that a particular firebase method call on a particular path should fail and invoke an error handler. The first constructor parameter to ErroringFirebase is the Firebase URL. The second parameter is a substring of the path you want to fail -- any path containing this string will fail. The final parameter is the name of the Firebase method that should fail when invoked.

This example illustrates the use of ErroringFirebase:

```java
ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "child", "setValue");
erroringFirebase.child("child").setValue(12, new Firebase.CompletionListener() {
  @Override
  public void onComplete(FirebaseError firebaseError, Firebase firebase) {
    assertNotNull(firebaseError);
  }
});
```