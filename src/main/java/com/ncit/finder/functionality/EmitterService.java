package com.ncit.finder.functionality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Component
public class EmitterService {

    List<SseEmitter> emitters = new ArrayList<>();

    public void addEmitter(SseEmitter emitter) {
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitters.add(emitter);
    }

    public void pushNotification(int userId, int count) {
         System.out.println("pushing notification for user "+userId);
        List<SseEmitter> deadEmitters = new ArrayList<>();
        Map<String, Integer> notificationData = new HashMap<>();
        notificationData.put("userId", userId);
        notificationData.put("count", count);

      
        emitters.forEach(emitter -> {
            try {
                emitter
                .send(
                    SseEmitter
                    .event()
                    .data(notificationData)
                );

            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });

        emitters.removeAll(deadEmitters);
    }

}
