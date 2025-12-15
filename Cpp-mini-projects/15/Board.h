#include <array>
#include <assert.h>

#include "Tile.h"
#include "../Random.h"

#ifndef BOARD
#define BOARD

class Direction {
    public:
        enum Type {
            UP, 
            DOWN,
            LEFT,
            RIGHT,
            maxDirections,
        };
        Direction() = default;
        Direction(Type type): type(type) {};

        Direction operator-() const {
            switch (type) {
                case Direction::UP: return Direction::DOWN;
                case Direction::DOWN: return Direction::UP;
                case Direction::LEFT: return Direction::RIGHT;
                case Direction::RIGHT: return Direction::LEFT;
                default: break;
            }
            assert(0 && "Unsupported direction was passed!");
            return Direction::UP;
        }

        Type getType() {return type; }
    private:
        Type type;
};

Direction getRandomDirection() {
    return Direction(static_cast<Direction::Type>(Random::generateInt(0, Direction::Type::maxDirections - 1)));
}

struct Point {
    int x {};
    int y {};
    Point getAdjacentPoint(Direction direction) const {
        switch(direction.getType()) {
            case Direction::UP: return Point{x-1, y};
            case Direction::DOWN: return Point{x+1, y};
            case Direction::LEFT: return Point{x, y-1};
            case Direction::RIGHT: return Point{x, y+1};
            default: break;
        }
        assert(0 && "Unsupported direction was passed!");
        return *this;
    } 

    friend bool operator==(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }
    friend bool operator!=(Point p1, Point p2) {
        return p1.x != p2.x || p1.y != p2.y;
    }
};

class Board {
    public:
        Board() {
            for (int i = 0; i < tiles.size(); i++) {
                for (int j = 0; j < tiles[i].size(); j++) {
                    tiles[i][j] = Tile((i * 4 + j + 1) % 16);
                }
            }
            winState = tiles;
            for (int i = 0; i <  Random::generateInt(100, 200); i++) {
                moveTile(getRandomDirection());
            }
        }

        void moveTile(Direction direction) {
            for (int i = 0; i < tiles.size(); i++) {
                for (int j = 0; j < tiles[i].size(); j++) {
                    if (tiles[i][j].isEmpty()) {
                        Point swap = Point{i, j}.getAdjacentPoint(-direction);
                        if (swap.x >= 0 && swap.x < 4 && swap.y >= 0 && swap.y < 4) {
                            tiles[i][j] = tiles[swap.x][swap.y];
                            tiles[swap.x][swap.y] = Tile(0);
                        }
                        break;
                    }
                }
            }
            if (hasWon()) {
                std::cout << "You win!";
            }
        }

        bool hasWon() {
            for (int i = 0; i < tiles.size(); i++) {
                for (int j = 0; j < tiles[i].size(); j++) {
                    if (tiles[i][j].getNum() != winState[i][j].getNum()) {
                        return false;
                    }
                }
            }
            return true;
        }
        std::array<std::array<Tile, 4>, 4> getTiles() {
            return tiles;
        }  
    private:
        std::array<std::array<Tile, 4>, 4> tiles{};
        std::array<std::array<Tile, 4>, 4> winState{};

};

std::ostream& operator<<(std::ostream &out, Board &board) {
    for (auto &row : board.getTiles()) {
        for(Tile &tile : row) {
            std::cout << tile;
        }
        std::cout << '\n';
    }
    return out;
}

#endif