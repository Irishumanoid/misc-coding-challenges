#include <string>
#include <algorithm>
#include <limits>

#include "Tile.h"
#include "Board.h"
#include "../Random.h"
#include <assert.h>


std::ostream& operator<<(std::ostream &out, Direction direction) {
    switch (direction.getType()) {
        case Direction::UP: out << "up"; break;
        case Direction::DOWN: out << "down"; break;
        case Direction::LEFT: out << "left"; break;
        case Direction::RIGHT: out << "right"; break;
        default: break;
    }
    return out;
}

namespace UserInput {
    static constexpr std::array<char, 5> valid = {'w', 'a', 's', 'd', 'q'};

    bool isDirValid(char input) {
        return std::find(valid.begin(), valid.end(), input) != valid.end();
    }

    char getCommandFromUser() {
        char input{};
        while (true) {
            std::cin >> input;
            if (isDirValid(input)) {
                std::cout << "Valid command: " << input << '\n';
                if (input == 'q') {
                    std::cout << "\n\nBye!\n\n";
                }
                return input;
            } else {
                std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            }
        }
    }

    static Direction getDirection(char command) {
        switch (command) {
            case 'w': return Direction(Direction::Type::UP);
            case 'a': return Direction(Direction::Type::LEFT);
            case 's': return Direction(Direction::Type::DOWN);
            case 'd': return Direction(Direction::Type::RIGHT);
            default: break;
        }
        assert(0 && "Unsupported direction was passed!");
        return Direction::UP;
    }
}

constexpr int g_consoleLines{ 25 };
int main(int argc, char const *argv[]) {
    std::cout << std::string(g_consoleLines, '\n');

    Board board {};
    std::cout << board;

    std::cout << "Enter a command: ";
    while (true) {
        char command;
        std::cin >> command;
        if (command == 'q') {
            break;
        }
        if (UserInput::isDirValid(command)) {
            Direction dir = UserInput::getDirection(command);
            std::cout << "You entered direction: " << dir << '\n';
            board.moveTile(dir);
            if (board.hasWon()) {
                break;
            }
            std::cout << board; 
        }
    }

    return 0;
}
