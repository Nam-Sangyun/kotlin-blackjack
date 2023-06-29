package domain.game

import domain.card.Cards
import domain.card.Deck
import domain.dto.IssuedCardResult
import domain.player.Dealer
import domain.player.Player
import domain.player.PlayerBetAmounts
import domain.player.Players
import domain.state.State

class BlackjackGame(private val deck: Deck, playerBetAmounts: PlayerBetAmounts) {

    val players: Players

    val dealer: Dealer

    init {
        this.players = Players.createPlayers(playerBetAmounts)
        this.dealer = Dealer()
    }

    fun initGame(): IssuedCardResult {
        players.forEach { player -> player.initGame(initCards()) }
        dealer.initGame(initCards())
        return IssuedCardResult(players = players, dealer = dealer)
    }

    private fun initCards() = Cards(listOf(deck.issueCard(), deck.issueCard()))

    fun gameStart(
        isIssueCard: (player: Player) -> Boolean,
        showMessage: (player: Player) -> Unit,
    ): GameResult {
        players.forEach { player ->
            playGame(player = player, isIssueCard, showMessage = showMessageWithCheck(showMessage))
        }
        playGame(
            player = dealer,
            isIssueCard = { dealer.isDrawable() },
            showMessage = showMessageWithCheck(showMessage),
        )
        return GameResult(players = players, dealer = dealer)
    }

    private fun playGame(
        player: Player,
        isIssueCard: (player: Player) -> Boolean,
        showMessage: (player: Player) -> Unit,
    ) {
        while (!player.isTerminated()) {
            gameProgress(isIssueCard, player)

            showMessage(player)
        }
    }

    private fun gameProgress(isIssueCard: (player: Player) -> Boolean, player: Player) {
        when (isIssueCard(player)) {
            true -> issueCard(player)
            else -> player.stop()
        }
    }

    private fun showMessageWithCheck(showMessage: (player: Player) -> Unit): (player: Player) -> Unit =
        { if (!it.isTerminated()) showMessage(it) }

    private fun issueCard(player: Player): State {
        return player.draw(this.deck.issueCard())
    }

    companion object {
        const val BLACKJACK_GAME_DECK_SIZE = 6
    }
}
