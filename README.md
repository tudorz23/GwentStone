*Designed by Marius-Tudor Zaharia, August 2023*

# GwentStone

---

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
* After round 10, players only get 10 mana each per round.
* Players can put cards from their hand down on the board, but this action requires mana.
* Every card from the board can attack other cards and cards have a predefined health bar.
* A card cannot attack multiple times during the same turn of a player.
* A card can be frozen (meaning it cannot attack or use any special ability).
* The game ends when one of the players' hero dies.
* Multiple games can be played, thus we must make sure the player's pack of decks is
left intact after one game, so he can pick any of the decks for the next game.
* Each player has a metric for the number of wins and the total number of games played.

---

## Card format
1. ### Minion cards
   * Attributes: mana, health, attackDamage, description, colors, name.
   * Can be placed on the board.
   * There are two subtypes:
        * normal Minion: ***Sentinel, Berserker, Goliath, Warden***;
        * special Minion: ***Miraj, The Ripper, Disciple, The Cursed One***.
   * Special ones have certain special abilities, that can be used during the game.
   * Goliath and Warden are 'Tank' cards, meaning that, if they are present on the table,
   they must be attacked first, before any of the other cards from the same row.
   * Can be frozen.
   * Player must have enough *mana* to place Minion on the board.
   * Can attack only once every turn.

2. ### Environment cards
   * Attributes: mana, description, colors, name.
   * Cannot be placed on the board, they are just used.
   * After being used, they are removed from tha player's hand.
   * They have special abilities, targeting one of the enemy's rows.
   * Cannot be frozen.
   * Player must have enough *mana* to use Environment card.
   * **Firestorm, Winterfell, Heart Hound**.

3. ### Hero cards
   * Attributes: mana, description, colors, name.
   * At the beginning of the game, health is set at 30.
   * Each player's hero is specified at the start of a game.
   * Not placed on the board.
   * If the hero has 0 health left, the player loses and the game **ends**.
   * They have special abilities, targeting one of the enemy's rows.
   * Player must have enough *mana* to use hero's ability.
   * Can attack only once every turn.
   * Cannot be frozen.

---

## The board
* This is where the cards will interact with each other.
* Can be pictures as a 4 x 5 matrix, with each player having 2 rows assigned.
* ***Rows 0 and 1*** are for ***player 2***, while ***rows 2 and 3*** are for ***player 1***.
* Each player has a **front** row and a **back** row.
* ***Rows 1 and 2*** are ***front*** rows, while ***rows 0 and 3*** are ***back rows***.
* Cards are placed on the row from left to right.
* If a card gets to 0 health, it is removed from the table and all the cards from its right
are shifted to the left by one place.

---
