#include "../Random.h"

#include <iostream>
#include <string>
#include <array>
#include <algorithm>
#include <cassert>

#ifndef CARDS
#define CARDS

namespace Cards {
    struct Card {
        enum Rank {
            rank_ace,
            rank_2,
            rank_3,
            rank_4,
            rank_5,
            rank_6,
            rank_7,
            rank_8,
            rank_9,
            rank_10,
            rank_jack,
            rank_queen,
            rank_king,
            max_ranks
        };

        enum Suit {
            suit_clubs,
            suit_diamonds,
            suit_hearts,
            suit_spades,
            max_suits
        };

        static constexpr std::array<Rank, 13> all_ranks 
            { rank_ace, rank_2, rank_3, rank_4, rank_5, rank_6, rank_7, rank_8, rank_9, rank_10, rank_jack, rank_queen, rank_king };
        static constexpr std::array<Suit, 4> all_suits
            { suit_clubs, suit_diamonds, suit_hearts, suit_spades };

        Rank rank {};
        Suit suit {};


        friend std::ostream& operator<<(std::ostream& out, const Card& card) {
            const std::array<std::string, 13> rank_prefixes = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
            const std::array<std::string, 4> suit_prefixes = { "C", "D", "H", "S" };

            out << rank_prefixes[card.rank] << suit_prefixes[card.suit];
            return out;
        };

        int value() const {
            constexpr std::array<int, 13> values = { 11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10 };
            return values[rank];
        }
    };


    class Deck {
        private:
            std::array<Card, 52> deck {};
            int next_card {0};
        public:
            Deck() {
                for (int i = 0; i < Card::all_suits.size(); ++i) {
                    for (int j = 0; j < Card::all_ranks.size(); ++j) {
                        deck[i * j + j] = Card { Card::all_ranks[j], Card::all_suits[i] };
                    }
                }
            }

            Card& dealCard() {
                assert(next_card < deck.size() && "dealCard ran out of cards");
                return deck[next_card++];
            }

            void shuffle() {
                std::shuffle(deck.begin(), deck.end(), Random::generator);
                next_card = 0;
            }

            int getNextCard() {
                return next_card;
            };

            int getDeckSize() {
                return deck.size();
            }
    };

    struct Player {
        int score {};
        int numAces {};

        void incIfAce(const Card& card) {
            if (card.rank == Card::rank_ace) {
                numAces += 1;
            }
        }
    };

    enum WinState {
        PLAYER,
        DEALER,
        TIE,
    };

    using namespace Cards;

    namespace Settings {
        constexpr int playerBust { 21 };
        constexpr int dealerStop { 17 };
    }

    WinState play(Deck& deck) {
        std::array<Card, 3> cards {};
        for (int i = 0; i < 3; ++i) {
            if (deck.getNextCard() == deck.getDeckSize()) {
                deck.shuffle();
            }
            cards[i] = deck.dealCard();
        }

        Player dealer = {cards[0].value()};
        Player player = {cards[1].value() + cards[2].value()};

        std::cout << "The dealer is showing " << cards[0] << " (" << dealer.score << ")\n";
        dealer.incIfAce(cards[0]);

        std::cout << "You have: " << cards[1] << " " << cards[2] << " (" << player.score << ")\n";
        player.incIfAce(cards[1]);
        player.incIfAce(cards[2]);

        while (true) {
            std::cout << "(h) to hit, or (s) to stand: ";
            std::string move;
            std::cin >> move;

            if (move == "h") {
                Card curCard = deck.dealCard();
                player.score += curCard.value();
                player.incIfAce(curCard);
                std::cout << "You were dealt " << curCard << ". You now have " << player.score << '\n';
            } else if (move == "s") {
                break;
            } else {
                std::cout << "Invalid player action, try again.";
            }

            if (player.numAces > 0) {
                player.score -= 10;
                player.numAces -= 1;
            }
            if (player.score > Settings::playerBust) {
                std::cout << "You went bust!" << '\n';
                return DEALER;
            }
        }

        while (dealer.score < Settings::dealerStop) {
            Card curCard = deck.dealCard();
            dealer.score += curCard.value();
            dealer.incIfAce(curCard);
            std::cout << "The dealer flips a " << curCard << ". They now have: " << dealer.score << '\n';
        }

        if (dealer.numAces > 0) {
            dealer.score -= 10;
            dealer.numAces -= 1;
        }
        if (dealer.score > Settings::playerBust) {
            std::cout << "The dealer went bust! \n";
            return PLAYER;
        }

        if (dealer.score < player.score) {
            return PLAYER;
        } else if (dealer.score > player.score) {
            return DEALER;
        } else {
            return TIE;
        }
   }
}


#endif