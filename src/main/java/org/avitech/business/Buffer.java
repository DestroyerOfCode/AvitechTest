package org.avitech.business;

import org.avitech.models.Command;

public interface Buffer {

  void blockingPut(final Command value);

  void blockingGet();
}
