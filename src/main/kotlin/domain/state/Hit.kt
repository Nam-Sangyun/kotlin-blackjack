package domain.state

import domain.card.BlackjackCard
import domain.card.BlackjackCards

class Hit(cards: BlackjackCards) : ProceedingState(cards) {

    override fun draw(card: BlackjackCard): State {
        val currentCards = BlackjackCards(this.getCards().plus(card))
        return if (currentCards.isDrawable()) Hit(cards = currentCards) else Hit(currentCards)
    }

    override fun stop(): State = Hit(BlackjackCards(emptyList()))
}