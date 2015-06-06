package org.bgu.communication.protocol;

public interface ServerProtocolFactory<T> {
   AsyncServerProtocol<T> create();
}
