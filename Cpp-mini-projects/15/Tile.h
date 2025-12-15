#include <iostream>

#ifndef TILE
#define TILE

class Tile {
    public:
        Tile() = default;
        Tile(int value): value(value) {};

        bool isEmpty() { return value == 0; }

        int getNum() { return value; }

    private:
        int value;
};

std::ostream& operator<<(std::ostream &out, Tile &tile) {
    if (tile.isEmpty()) {
        out << "    ";
    } else{
        out << (tile.getNum() > 9 ? " " : "  ") << tile.getNum() << " ";
    }
    return out;
}
#endif