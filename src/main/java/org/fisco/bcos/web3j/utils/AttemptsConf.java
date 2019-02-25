package org.fisco.bcos.web3j.utils;

public class AttemptsConf {
  public static Integer sleepDuration = 1500;
  public static Integer attempts = 40;

  public AttemptsConf(int sleepDuration, int attempts) {
    this.sleepDuration = sleepDuration;
    this.attempts = attempts;
  }

  public int getSleepDuration() {
    return sleepDuration;
  }

  public void setSleepDuration(int sleepDuration) {
    AttemptsConf.sleepDuration = sleepDuration;
  }

  public int getAttempts() {
    return attempts;
  }

  public void setAttempts(int attempts) {
    AttemptsConf.attempts = attempts;
  }
}
