package com.liferay.translation.translator.deepl.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Dictionary;

/**
 * @author Yasuyuki Takeo
 */
@Component(
        immediate = true,
        property = "model.class.name=com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration",
        service = ConfigurationModelListener.class
)
public class DeepLTranslatorConfigurationModelListener implements ConfigurationModelListener {
    @Override
    public void onBeforeSave(String pid, Dictionary<String, Object> properties)
            throws ConfigurationModelListenerException {

        boolean enabled = GetterUtil.getBoolean(properties.get("enabled"));
        String serviceAccountPrivateKey = GetterUtil.getString(
                properties.get("serviceAccountPrivateKey"));

        if (enabled && !_isValid(serviceAccountPrivateKey)) {
            throw new ConfigurationModelListenerException(
                    _language.get(
                            LocaleThreadLocal.getThemeDisplayLocale(),
                            "the-service-account-private-key-must-be-in-json-format"),
                    DeepLTranslatorConfiguration.class, getClass(),
                    properties);
        }
    }

    private boolean _isValid(String serviceAccountPrivateKey) {
        try {
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
                    serviceAccountPrivateKey);

            return jsonObject.length() > 0;
        } catch (Exception exception) {
            return false;
        }
    }

    @Reference
    private Language _language;
}
