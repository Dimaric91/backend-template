package cex.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

public interface SubscriptionService {

    void subscribe(Session session);
    void unsubscribe(Session session);
    void handle(Session session, SubscriptionMessage message);
    void broadcast(ResourceMessage message);

    @NoArgsConstructor
    @AllArgsConstructor
    class SubscriptionMessage {

        public enum Type {
            SUBSCRIBE,
            UNSUBSCRIBE
        }

        public Type type;
        public String pattern;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    class ResourceMessage {

        public String alias;
        public Type type;

        public Object object;

        public enum Type {
            CREATE,
            REMOVE,
            UPDATE
        }
    }
}
