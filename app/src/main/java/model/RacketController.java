package model;

public interface RacketController {
    enum State { GOING_UP, IDLE, GOING_DOWN }
    State getState();
    enum PowerBall {ELECTRIK, FIRE, GRASS, WATER}
    PowerBall getPowerBall();
}
