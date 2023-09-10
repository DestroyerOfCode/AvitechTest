package org.avitech.business;

public interface Buffer<T> {

  void blockingPut(final T value);

  T blockingGet();
}
