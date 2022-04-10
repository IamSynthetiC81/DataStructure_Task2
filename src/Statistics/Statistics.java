package src.Statistics;

import java.time.Duration;
import java.time.Instant;

public class Statistics {
    Instant StartTime;
    Instant EndTime;

    /** Flag to indicate whether the timer is running. */
    private Boolean IsRunning;

    /** Counter for the number of times the function has been called */
    Integer Counter;

    public Statistics() {
        StartTime = null;
        EndTime = null;
        IsRunning = null;
    }

    /**
     * Starts the timer
     */
    public void Start() {
        EndTime = null;                                 // Reset the end time
        StartTime = Instant.now();                      // Set the start time
        IsRunning = true;                               // Set the timer to running
    }

    /**
     * Stops the timer
     */
    public void End() {
        EndTime = Instant.now();                        // Set the end time
        IsRunning = false;                              // Set the timer to not running
    }

    /**
     * Returns the time elapsed since the timer was started
     * @return Duration between the Start and End Instances.
     */
    public Duration getDuration() {
        assert !IsRunning;                              // If the timer is running, the duration is not correct
        return Duration.between(StartTime, EndTime);    // Returns the duration between the start and end time
    }

    /**
     * Increments the counter
     */
    public void Increment() {
        Counter++;
    }

    /**
     * Returns the current value of the counter
     * @return int value of the counter
     */
    public int getCounter(){
        return Counter;
    }

    /**
     * Resets the counter
     */
    public void Reset(){
        Counter = 0;
    }
}
