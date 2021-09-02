package com.ecabs.backendtechnicaltask.producer.sender.internalmessage;

public interface OutboundMessageBuilder<T> {

    OutboundMessage prepare(T msgBody);

    OperationType getOperationType();

}