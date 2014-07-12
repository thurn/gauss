package com.tinlib.shared;

import com.tinlib.inject.*;
import com.tinlib.message.Buses;

public class TinModule implements Module {
  @Override
  public void configure(Binder binder) {
    binder.bindSingletonKey(TinKeys.BUS,
        Initializers.returnValue(Buses.newBus()));
    binder.bindSingletonKey(TinKeys.KEYED_LISTENER_SERVICE,
        Initializers.returnValue(new KeyedListenerService()));
  }
}
