package com.liferay.translation.translator.deepl.internal.translator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.url.URLBuilder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.translation.translator.deepl.internal.constants.DeepLConstants;
import com.liferay.translation.translator.deepl.internal.model.TranslateResponse;
import com.liferay.translation.translator.deepl.internal.util.JSONUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Yasuyuki Takeo
 */
@Component(
    immediate = true,
    service = DeepLClient.class
)
public class DeepLClient {

//  @Retry(
//      acceptor = ExceptionRetryAcceptor.class,
//      properties = {
//          @Property(
//              name = ExceptionRetryAcceptor.EXCEPTION_NAME,
//              value = "org.hibernate.StaleObjectStateException"
//          )
//      }
//  )
  public TranslateResponse execute(String authKey, String text, String sourcelang, String targetLang, String url) throws IOException {
    String rawRes = _fetch(authKey, text, sourcelang, targetLang, url);

    return JSONUtil.toObject(rawRes,TranslateResponse.class );
  }

  protected String _fetch(
      String authKey, String text, String sourcelang, String targetLang, String url) throws IOException {

    // Build request
    Http.Options options = new Http.Options();

    options.setLocation(
        URLBuilder.create(
            url
        ).addParameter(
            DeepLConstants.AUTH_KEY, authKey
        ).build());

    options.addHeader(
        HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED);
//    options.setBody(
//        _getTranslatorPacketPayload(translatorPacket),
//        ContentTypes.APPLICATION_JSON, StringPool.UTF8);
    options.addPart(DeepLConstants.AUTH_KEY, authKey);
    options.addPart(DeepLConstants.TEXT, text);
    options.addPart(DeepLConstants.SOURCE_LANG, sourcelang);
    options.addPart(DeepLConstants.TARGET_LANG, targetLang);
    options.setMethod(Http.Method.POST);

    // Fetch data
    String ret = _http.URLtoString(options);
    Http.Response response = options.getResponse();

    Response.Status status = Response.Status.fromStatusCode(response.getResponseCode());

    if (status == Response.Status.OK) {
      return ret;
    } else if (status == Response.Status.TOO_MANY_REQUESTS) {
      _log.info("TOO_MANY_REQUESTS. Retry after a while");
      return "";
    }

    return ret;
  }

  @Reference
  private Http _http;

  private static final Log _log = LogFactoryUtil.getLog(
      DeepLClient.class);
}
