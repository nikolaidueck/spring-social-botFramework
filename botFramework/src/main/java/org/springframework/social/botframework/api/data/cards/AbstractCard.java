package org.springframework.social.botframework.api.data.cards;

import org.springframework.social.botframework.api.dict.CardType;
import org.springframework.social.botframework.api.json.CardTypeSerializer;
import org.springframework.social.botframework.api.data.Attachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Anton Leliuk
 */
public abstract class AbstractCard extends CardElement {

    @JsonIgnore
    public <C extends AbstractCard> Attachment<C> toAttachment(){
        return new Attachment<C>().content((C) this).contentType(getCardType().getType());
    }

    @JsonSerialize(using = CardTypeSerializer.class)
    public abstract CardType getCardType();

}
