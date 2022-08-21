package com.liferay.translation.translator.deepl.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Yasuyuki Takeo
 */
@ExtendedObjectClassDefinition(
        category = "translation",
        scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
        id = "com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration",
        localization = "content/Language",
        name = "deepl-translator-configuration-name"
)
public interface DeepLTranslatorConfiguration {
    @Meta.AD(
            deflt = "false",
            description = "enabled-description[deepl-translation]",
            name = "enabled", required = false
    )
    public boolean enabled();

    @Meta.AD(
            deflt = "", description = "service-account-private-key-description",
            name = "service-account-private-key", required = false
    )
    public String serviceAccountPrivateKey();

}