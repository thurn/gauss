package ca.thurn.noughts.shared.entities;

import com.firebase.client.*;

public class EntityMutator {
  public static interface Mutation<E extends Entity<E>, B extends Entity.EntityBuilder<E>> {
    public void mutate(B builder);

    public void onComplete(E entity);

    public void onError(FirebaseError error, boolean committed);
  }

  public static <E extends Entity<E>, B extends Entity.EntityBuilder<E>> void mutateEntity(
      Firebase ref, final Entity.EntityDeserializer<E> deserializer,
      final Mutation<E, B> mutation) {
    ref.runTransaction(new Transaction.Handler() {
      @Override
      public Transaction.Result doTransaction(MutableData mutableData) {
        // Firebase frequently returns null values initially and then only
        // returns the actual value at the location if you return success.
        if (mutableData.getValue() == null) return Transaction.success(mutableData);

        // This is safe for the default implementations -- toBuilder always
        // returns the associated builder type.
        @SuppressWarnings("unchecked")
        B builder = (B)deserializer.fromMutableData(mutableData).toBuilder();
        try {
          mutation.mutate(builder);
        } catch(Error error) {
          // Firebase silently swallows Errors! This makes debugging extremely
          // difficult. Instead, we crash the program.
          System.err.println(error);
          System.exit(1);
        }
        mutableData.setValue(builder.build().serialize());
        return Transaction.success(mutableData);
      }

      @Override
      public void onComplete(FirebaseError error, boolean committed, DataSnapshot dataSnapshot) {
        if (error == null && committed) {
          mutation.onComplete(deserializer.fromDataSnapshot(dataSnapshot));
        } else {
          mutation.onError(error, committed);
        }
      }
    });
  }
}
