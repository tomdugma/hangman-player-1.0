package com.netomedia.exam.hangman.model;

public class ServerResponse {

    private String token;
    private String hangman;
    private Boolean correct;
    private int failedAttempts;
    private boolean gameEnded;
    private String error;

    public ServerResponse() {

    }

    public ServerResponse(String token, String hangman, int failedAttempts, String error) {
        this.token = token;
        this.hangman = hangman;
        this.failedAttempts = failedAttempts;
        this.error = error;
    }

    public ServerResponse(String token, String hangman) {
        this.token = token;
        this.hangman = hangman;
    }

    public ServerResponse(String token, String hangman, Boolean correct) {
        this.token = token;
        this.hangman = hangman;
        this.correct = correct;
    }

    public ServerResponse(String token, String hangman, Boolean correct, boolean gameEnded, int failedAttempts) {
        this.token = token;
        this.hangman = hangman;
        this.correct = correct;
        this.gameEnded = gameEnded;
        this.failedAttempts = failedAttempts;
    }

    public ServerResponse(String token, String hangman, Boolean correct, boolean gameEnded, int failedAttempts, String error) {
        this.token = token;
        this.hangman = hangman;
        this.correct = correct;
        this.gameEnded = gameEnded;
        this.failedAttempts = failedAttempts;
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public String getHangman() {
        return hangman;
    }

    public Boolean isCorrect() {
        return correct;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ServerResponse [token=" + token + ", hangman=" + hangman + ", correct=" + correct + ", failedAttempts="
                + failedAttempts + ", gameEnded=" + gameEnded + "]";
    }

}
