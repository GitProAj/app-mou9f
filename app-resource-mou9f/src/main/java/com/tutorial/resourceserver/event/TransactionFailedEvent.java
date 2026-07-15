package com.tutorial.resourceserver.event;
import com.tutorial.resourceserver.entity.ClientMou9f;
import org.springframework.context.ApplicationEvent;

public class TransactionFailedEvent extends ApplicationEvent {

    private final ClientMou9f clientMou9f;
    String reason;
    public TransactionFailedEvent(Object source, ClientMou9f clientMou9f, String reason) {
        super(source);
        this.clientMou9f = clientMou9f;
        this.reason = reason;
//        this.sagaId = clientMou9f != null ? clientMou9f.getSagaId() : null;
    }

    public TransactionFailedEvent(Object source, ClientMou9f client) {
        super(source);
        this.clientMou9f = client;
        this.reason = "Transaction failed";
//        this.sagaId = client != null ? client.getSagaId() : null;
    }

    public ClientMou9f getClient() {
        return clientMou9f;
    }

//    public String getReason() {
//        return reason;
//    }

//    public String getSagaId() {
//        return sagaId;
//    }
}
