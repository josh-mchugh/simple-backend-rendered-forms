package com.jmchugh.demo.component;

import lombok.Value;
import org.springframework.ui.Model;

public class ToastrResponse {

    public enum Type {
        SUCCESS,
        INFO,
        WARNING,
        ERROR
    }

    private Model model;

    public ToastrResponse(Model model) {

        this.model = model;
    }

    public ToastrResponse toast(String message, Type type) {

        model.addAttribute("message", message);
        model.addAttribute("type", type);

        return this;
    }

    public ToastrResponse event(String contentId, String eventName) {

        model.addAttribute("event", new Event(contentId, eventName));

        return this;
    }

    public String build() {

        return "component/partial-toastr";
    }

    @Value
    public static class Event {

        private final String id;
        private final String name;
    }
}
