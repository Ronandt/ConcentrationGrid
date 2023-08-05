package com.example.concentrationgrid.presentation.concentration_grid.states
/**
 * This enum class [GameState] defines the possible states of the concentration game.
 *
 * - [Playing]: The game is currently in progress, with the player finding the numbers in the specified order.
 * - [Won]: The player has successfully clicked all the numbers in order in the grid and has won the game.
 * - [Lost]: The game has ended, and the player did not click all the numbers in order in the grid within the given time.
 * - [NotStarted]: The game has not yet begun.
 */
enum class GameState {
    Playing,
    Won,
    Lost,
    NotStarted
}

