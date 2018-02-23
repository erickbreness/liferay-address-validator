package com.rivetlogic.language.override;

import java.util.Enumeration;
import java.util.ResourceBundle;
import org.osgi.service.component.annotations.Component;
import com.liferay.portal.kernel.language.UTF8Control;

@Component(
        property = { "language.id=en_US" },
        service = ResourceBundle.class
)
public class CustomLanguageComponent extends ResourceBundle {

    ResourceBundle bundle = ResourceBundle.getBundle("content.Language", UTF8Control.INSTANCE);

    @Override
    protected Object handleGetObject(String key) {
        System.out.println("getting key"+key);
        return bundle.getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return bundle.getKeys();
    }

}