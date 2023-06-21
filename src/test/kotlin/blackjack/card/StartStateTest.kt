package blackjack.card

import domain.card.BlackjackCard
import domain.card.CardNumber
import domain.card.Suit
import domain.state.Blackjack
import domain.state.StartState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class StartStateTest {

    @ParameterizedTest
    @MethodSource("getStartStateTestData")
    fun `플레이어는 카드 두 장을 지급 받으면 시작(Start)이라는 상태가 된다`(card1: BlackjackCard, card2: BlackjackCard) {
        val actual = StartState.start(card1, card2)
        assertThat(actual is StartState).isTrue
    }

    @ParameterizedTest
    @MethodSource("getBlackjackStateTestData")
    fun `플레이어는 시작(Start) 상태에서 카드가 A 한 장과 10에 해당하는 카드(10, J, K, Q 중 하나)라면 블랙잭(Blackjack)이라는 종료 상태가 된다`(
        card1: BlackjackCard,
        card2: BlackjackCard,
    ) {
        val actual = StartState.start(card1, card2)
        assertThat(actual is Blackjack).isTrue
    }

    companion object {
        @JvmStatic
        fun getStartStateTestData(): List<Arguments> = listOf(
            Arguments.of(
                BlackjackCard(suit = Suit.HEART, number = CardNumber.ACE),
                BlackjackCard(suit = Suit.CLUB, number = CardNumber.TWO),
            ),
            Arguments.of(
                BlackjackCard(suit = Suit.SPADE, number = CardNumber.JACK),
                BlackjackCard(suit = Suit.HEART, number = CardNumber.JACK),
            ),
            Arguments.of(
                BlackjackCard(suit = Suit.SPADE, number = CardNumber.TEN),
                BlackjackCard(suit = Suit.HEART, number = CardNumber.FIVE),
            ),
        )

        @JvmStatic
        fun getBlackjackStateTestData(): List<Arguments> = listOf(
            Arguments.of(
                BlackjackCard(suit = Suit.HEART, number = CardNumber.ACE),
                BlackjackCard(suit = Suit.CLUB, number = CardNumber.JACK),
            ),
            Arguments.of(
                BlackjackCard(suit = Suit.SPADE, number = CardNumber.ACE),
                BlackjackCard(suit = Suit.HEART, number = CardNumber.QUEEN),
            ),
            Arguments.of(
                BlackjackCard(suit = Suit.SPADE, number = CardNumber.TEN),
                BlackjackCard(suit = Suit.HEART, number = CardNumber.ACE),
            ),
            Arguments.of(
                BlackjackCard(suit = Suit.SPADE, number = CardNumber.KING),
                BlackjackCard(suit = Suit.HEART, number = CardNumber.ACE),
            ),
        )
    }
}
