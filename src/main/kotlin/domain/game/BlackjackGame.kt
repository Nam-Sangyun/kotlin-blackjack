package domain.game

import domain.card.Deck
import domain.player.Player
import domain.player.Players
import domain.state.State
import domain.state.TerminationState

class BlackjackGame(private val deck: Deck) {

    fun initGame(playerNames: List<String>): Players {
        require(PLAYERS_RANGE.contains(playerNames.size)) { "플레이어 수는 1 ~ 8명이어야 합니다." }
        return Players(
            playerNames.map {
                Player(it, deck.issueCard(), deck.issueCard())
            },
        )
    }

    fun isTerminatedPlayer(player: Player): Boolean {
        return player.state is TerminationState
    }

    fun issueCard(player: Player): State {
        return player.draw(this.deck.issueCard())
    }

    fun stopIssueCard(player: Player): State {
        return player.stop()
    }

    companion object {
        private const val MAX_PLAYER_SIZE = 8
        private const val MIN_PLAYER_SIZE = 1
        private val PLAYERS_RANGE = IntRange(MIN_PLAYER_SIZE, MAX_PLAYER_SIZE)
        val BLACKJACK_GAME_DECK_SIZE = 6
    }
}
