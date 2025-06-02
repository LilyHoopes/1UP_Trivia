/*
 * This is an enum for the possible states of a door in the maze:
 * OPEN (correct answer), CLOSED (unanswered), LOCKED (wrong answer).
 */

package Model;

/**
 * Represents the state of a door in the maze.
 *
 * OPEN – The door was answered correctly and can be passed through.
 * CLOSED – The door has not been attempted yet.
 * LOCKED – The door was attempted, but the answer was incorrect, and it is now locked.
 *
 * author Lily Hoopes
 * author Komalpreet Dhaliwal
 * author Christiannel Maningat
 * version 5/7/2025
 */

public enum DoorState {
    OPEN, CLOSED, LOCKED;
}
