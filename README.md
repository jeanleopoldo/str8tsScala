# str8tsScala

This is an attempt to solve the game [str8ts](https://www.janko.at/Raetsel/Straights/index.htm). It uses the backtracking technique to solve the afore mentioned game. As it uses brute force, it is a very demanding algorithm and not best optimized.
__Time Complexity:__ O(n³)

## Requirements
- [scala 2.13.6](https://www.scala-lang.org/download/2.13.6.html)
- [Java JDK 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)

## How to run
- There is a script to run. In the project's root folder, run ./scripts/run.sh
- Commands are meant to run on linux only.

## Informations
1. There is a method `create_grid` which is where the grid is set stactically. For each cell, you must modify its value as you want.
On the main method is the size one must fill to create a table sizeXsize. There is no verification regarding the size, so it will either accept a very large number as well as negative numbers, so be careful.

- The static cells are represented by -1
- The empty cells are represented by 0
- The cell already filled by the game must be multiplied by 10 and must in the range of 1 to table's size

2. The core method is `solve`. It receives a grid of size nXn and recursively tries to find the first viable solution via _backtracking_. It is a brute force testing all possibilities.

3. Another very important method is `can_assign_number`. In this method there are all the restrictions that the solver neeeds in order to place a number into a cell. Such as, verifyng if the cell is a static cell itself or it had been informed a number in the first grid configuration.

4. Once a cell has all its numbers tested and still cannot place a number into it, then the algorithm goes back in the tree until it finds another cell that had not all its numbers tested.

5. The algorithm only tries numbers from 1 to _size_. Size being the length of the grid one informed at the begining of the running

## Output
The result as long as it was solved will be printed on the terminal.

## Issues
- Current version is on an infinite loop