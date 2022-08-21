package com.liferay.translation.translator.deepl.internal.translator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorPacket;
import com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(
        configurationPid = "com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration",
        service = Translator.class
)
public class DeepLTranslator implements Translator {
    @Override
    public boolean isEnabled(long companyId) throws ConfigurationException {
        DeepLTranslatorConfiguration
                deepLTranslatorConfiguration =
                _configurationProvider.getCompanyConfiguration(
                        DeepLTranslatorConfiguration.class, companyId);

        return deepLTranslatorConfiguration.enabled() &&
                !Validator.isBlank(
                        deepLTranslatorConfiguration.
                                serviceAccountPrivateKey());
    }

    @Override
    public TranslatorPacket translate(TranslatorPacket translatorPacket) throws PortalException {
        return null;
    }

    @Reference
    private ConfigurationProvider _configurationProvider;
}
