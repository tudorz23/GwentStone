*Designed by Marius-Tudor Zaharia, August 2023*

# GwentStone

---

## Table of contents
1. [What is GwentStone](#what-is-gwentstone)
2. [Gameplay presentation](#gameplay-presentation)
3. [Card formats](#card-formats)
4. [The board](#the-board)
5. [I/O details](#io-details)
6. [Implementation details](#implementation-details)

## What is GwentStone?
* GwentStone is a table card game written in the Java language.

---

## Gameplay presentation
* There are two players that are drawn multiple decks of cards. For each game, 
  they will choose one deck to start the game with.
* At the beginning of the game, each player is assigned a hero card and a coin is tossed
  to decide which player will start.
* The game is turn-based, meaning that each player will have a turn, that will be explicitly
  ended.
* After both players ended their turns, a new round begins.
* At the beginning of every round, each player gets as much *mana* as the round's number,
  then both of them draw one card from their respective deck and place it in their hand.
* From round 10 onwards, players only get 10 *mana* each per round.
* Players can put cards from their hand down on the board, but this action requires *mana*.
* Every card from the board can attack other cards and cards have a predefined health bar.
* A card cannot attack multiple times during the same turn of a player.
* A card can be *frozen* (meaning it cannot attack or use any special ability).
* The game ends when one of the players' hero dies.
* Multiple games can be played, thus we must make sure the player's pack of decks is
  left intact after one game, so he can pick any of the decks for the following games.
* Each player has a metric for the number of wins and the total number of games played.

---

## Card formats
1. ### Minion cards
   * Attributes: *mana, health, attackDamage, description, colors, name*.
   * Can be placed on the board.
   * There are two subtypes:
        * normal Minion: ***Sentinel, Berserker, Goliath, Warden***;
        * special Minion: ***Miraj, The Ripper, Disciple, The Cursed One***.
   * Special ones have certain special abilities, that can be used during the game.
   * Goliath and Warden are 'Tank' cards, meaning that, if they are present on the table,
     they must be attacked first, before any of the other cards from the same row.
   * Can be *frozen*.
   * Player must have enough *mana* to place Minion on the board.
   * Can attack only *once* every turn.

2. ### Environment cards
   * Attributes: *mana, description, colors, name*.
   * Cannot be placed on the board, they are simply *used*.
   * After being used, they are *removed* from the player's hand.
   * They have special *abilities*, targeting one of the enemy's *rows*.
   * *Cannot* be frozen.
   * Player must have enough *mana* to use Environment card.
   * **Firestorm, Winterfell, Heart Hound**.

3. ### Hero cards
   * Attributes: mana, description, colors, name.
   * At the beginning of the game, health is set at 30.
   * Each player's hero is specified at the start of a game.
   * Not placed on the board.
   * If the hero has 0 health left, the owning player **loses** and the game **ends**.
   * They have special *abilities*, targeting one of the enemy's *rows*.
   * Player must have enough *mana* to use hero's ability.
   * Can attack only *once* every turn.
   * *Cannot* be frozen.

---

## The board
* This is where the cards will interact with each other.
* Can be pictured as a 4 x 5 matrix, with each player having 2 rows assigned.
* ***Rows 0 and 1*** are for ***player 2***, while ***rows 2 and 3*** are for ***player 1***.
* Each player has a **front** row and a **back** row.
* ***Rows 1 and 2*** are ***front*** rows, while ***rows 0 and 3*** are ***back rows***.
* Cards are placed on the row from **left to right**.
* If a card gets to 0 health, it is *removed* from the table and all the cards from its right
  are *shifted* to the left by one place.

---

## I/O details
* The input is read from a JSON file.
* The output is also written to a JSON file.
* There are 3 types of input commands:
   * Gameplay commands;
   * State commands;
   * Statistics commands.
* The checker tests can be run from the **Main** class.

---

## Implementation details

### Design choices
* There is the basic **Card** class, which is extended by the specific card types,
  adding their respective abilities.
* Cards are kept in **Deck** instances, which are grouped to form a *packDeck*,
  representing each player's pack of decks assigned to him at the beginning of a
  session.
* For every game, one single *deck* is chosen and deep-copied from the *packDeck*,
  so the player is able to chose the respective deck again, for a future game.
* The board is simulated by using two classes:
   * **Row** class, which contains an *ArrayList* of **Card** instances.
      * This allows us to add and remove Cards on a row without worrying about
        resizing or shifting.
   * **Board** class, which contains an *array* of **Row** instances.
* For writing the output in JSON format, there are two classes in *printers* package:
   * **SuccessPrinter** - used for State and Statistics commands;
   * **ErrorPrinter** - used to check for possible errors occurring during Gameplay
     commands. It first searches for errors, and if it finds any, uses helper methods
     to print them in JSON format.

### Program flow
* The input is read from the JSON file into the classes from the *fileio* package, which are
  then converted to our specific types of classes through the **Converter** class.
* The **Game** class controls the flow of the gameplay.
* At the start of a game session, the **Game** class manages the drawing of decks to the
  players. Then, it takes one game at a time, preparing the players for it.
* The flow for one game is as follows:
   * The *playGame* method iterates through the commands from the input.
   * Depending on what type of command it encounters, methods from **ErrorPrinter**
     or **SuccessPrinter** are called to write the output in JSON format.
   * In gameplay commands case, after checking for errors, the actual game move is executed.
   * When one hero card has no *health* left, the current game is over and statistics are
     updated.

### OOP  principles used
* ***Inheritance***
   * **Card** class is inherited by **Minion**, **Environment** and **Hero** classes.
   * Each of the extending classes are also extended by specific classes that implement
     special abilities.
* ***Polymorphism***
   * **Environment** class has a method named *useAbility* which is overridden in the
     extending classes.
   * Something very similar happens with the **SpecialMinion** class and its extenders.
* ***Encapsulation***
   * Almost all fields of every single class has the *private* access specifier and *getter*
     and *setter* methods are implemented for each one.
   * The exceptions are in the **Row** and **Board** class, whose fields are left *public*,
     because their only role is to store **Card** instances, which are already well
     encapsulated.

---
