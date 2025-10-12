#include "Cards.h"
#include <iostream>

namespace Cards {
    constexpr std::array<Card::Rank, 13> Card::all_ranks;
    constexpr std::array<Card::Suit, 4> Card::all_suits;
}

int main() {
    using namespace Cards;

    Card card { Card::rank_5, Card::suit_hearts };
    std::cout << card << '\n';

    for (const auto& suit : Card::all_suits)
        for (const auto& rank : Card::all_ranks)
            std::cout << Card { rank, suit } << ' ';
    std::cout << '\n';

    Deck deck{};
    std::cout << deck.dealCard() << ' ' << deck.dealCard() << ' ' << deck.dealCard() << '\n';

    deck.shuffle();
    std::cout << deck.dealCard() << ' ' << deck.dealCard() << ' ' << deck.dealCard() << '\n';


    Cards::WinState out = play(deck);
    if (out == DEALER) {
        std::cout << "Dealer wins!";
    } else if (out == PLAYER) {
        std::cout << "Player wins!";
    } else {
        std::cout << "Tie!";
    }

    return 0;
}