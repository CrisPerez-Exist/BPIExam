package com.example.BPIExam.helper;

import org.springframework.stereotype.Component;

@Component
public class SetMessage {

    public String setDeleteMessage(String type, int id) {
        return String.format("%s with ID %s has been deleted.", type, id);
    }
}
