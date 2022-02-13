# str8tsScala

This is an attempt to solve the game str8ts(https://www.janko.at/Raetsel/Straights/index.htm). It uses the backtracking technique to solve the afore mentioned game. As it uses brute force, it is a very demanding algorithm and not best optimized.
__Time Complexity:__ O(n³)

## Requirements
- [scala 2.13.6](https://www.scala-lang.org/download/2.13.6.html)
- [Java JDK 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)

## How to run
- There is a script to run. In the project root's folder, run ./scripts/run.sh
- Commands are meant to run on linux only.

## Informations
There is a method `create_grid` which is where the grid are set stactically. For each cell, you must modify its value as you want.
On the main method are the size one must create a table. There is no verification regarding the size, so it will either also accept a very large number as well as negative numbers, so be careful.

- The static cells are represented by -1
- The empty cells are represented by 0
- The cell already field by the game must be multiplied by 10 and must in the range of 1 to table's size

## Output
Result as long as it was solved will be printed on the terminal

## Issues
- Current version is on an infinite loop