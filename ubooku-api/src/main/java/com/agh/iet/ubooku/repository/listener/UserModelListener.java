package com.agh.iet.ubooku.repository.listener;


import com.agh.iet.ubooku.model.auth.User;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserModelListener extends AbstractMongoEventListener<User> {


    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        event.getSource().setCreatedAt(new Date().toInstant());
        event.getSource().setUpdatedAt(new Date().toInstant());
    }

    @Override
    public void onBeforeSave(BeforeSaveEvent<User> event) {
        event.getSource().setUpdatedAt(new Date().toInstant());
    }
}
