package org.springframework.social.botFramework.api.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.botFramework.api.BotFramework;
import org.springframework.social.botFramework.api.data.to.Activity;
import org.springframework.social.botFramework.api.data.to.AttachmentData;
import org.springframework.social.botFramework.api.data.to.AttachmentInfo;
import org.springframework.social.botFramework.api.data.to.ChannelAccount;
import org.springframework.social.botFramework.api.data.to.ConversationParameters;
import org.springframework.social.botFramework.api.data.to.ResourceResponse;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Anton Leliuk
 */
public class BotFrameworkTemplate extends AbstractOAuth2ApiBinding implements BotFramework {

    private String skypeUrl;
    private String apiVersion;

    public BotFrameworkTemplate(String accessToken, String skypeUrl, String apiVersion) {
        super(accessToken);
        this.skypeUrl = skypeUrl;
        this.apiVersion = apiVersion;
    }

    @Override
    public byte[] getAttachment(String attachmentId, String viewId) {
        return getRestTemplate().getForEntity(getMainUrl().pathSegment("attachments", attachmentId, "views", viewId).toUriString(), byte[].class).getBody();
    }

    @Override
    public AttachmentInfo getAttachmentInfo(String attachmentId) {
//        /v3/attachments/{attachmentId}
        return null;
    }

    @Override
    public ResourceResponse createConversation(ConversationParameters parameters) {
        return getRestTemplate().postForObject(getMainUrl().path("conversations").toUriString(), parameters, ResourceResponse.class);
    }

    @Override
    public List<ChannelAccount> listActivityMembers(String conversationId, String activityId) {
        return null;
    }

    @Override
    public List<ChannelAccount> listConversationMembers(String conversationId) {
        return null;
    }

    @Override
    public void replyToActivity(String conversationId, Activity activity) {
        getRestTemplate().postForLocation(buildConversationUrl(conversationId).path("activities").toUriString(), activity);
    }

    @Override
    public void sendToConversation(String conversationId, Activity activity) {
        getRestTemplate().postForLocation(buildConversationUrl(conversationId).path("activities").toUriString(), activity);
    }

    @Override
    public ResourceResponse uploadAttachment(String conversationId, AttachmentData attachmentUpload) {
        return null;
    }

    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new LoggingClientHttpRequestInterceptor());
    }

//    @Override
//    public Object sendMessage(String recipient, Activity activity) {
//        return getRestTemplate().postForObject(buildConversationUrl(recipient).path("activities").toUriString(), activity, Object.class);
//    }

    private UriComponentsBuilder buildConversationUrl(String skypeId){
        return getMainUrl().pathSegment("conversations", skypeId);
    }

    private UriComponentsBuilder getMainUrl() {
        return UriComponentsBuilder.fromHttpUrl(skypeUrl).pathSegment(apiVersion);
    }

    private class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            StringBuilder sb = new StringBuilder(System.lineSeparator())
                    .append("-------------------").append(System.lineSeparator())
                    .append("URL = ").append(request.getURI()).append(System.lineSeparator())
                    .append("Headers = ").append(request.getHeaders()).append(System.lineSeparator())
                    .append("Body = ").append(new String(body)).append(System.lineSeparator())
                    .append("-------------------");
            System.out.println(sb);
            return execution.execute(request, body);
        }
    }
}