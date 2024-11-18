package com.rahul.lotteryassignment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a single Line in the lottery system.
 * Each line contains 3 numbers and a calculated result based on the rules.
 */
@Getter
@Setter
public class Line {


    private List<Integer> numbers;  //Numbers in the line (always 3 numbers)
    private int result; //The result of this line based on lottery rules

    /**
     * @param num1 First number in the line (0-2)
     * @param num2 Second number in the line (0-2)
     * @param num3 Third number in the line (0-2)
     * @author Rahul Kumar, reader.rahul@gmail.com
     * Constructs a Line with three numbers and calculates its result.
     */
    public Line(@Min(0) @Max(2) int num1, @Min(0) @Max(2) int num2, @Min(0) @Max(2) int num3) {
        this.numbers = Arrays.asList(num1, num2, num3);
        this.result = calculateResult();
    }

    /**
     * Calculates the result of this line based on lottery rules:
     * - 10 points if the sum of numbers equals 2.
     * - 5 points if all numbers are the same.
     * - 1 point if the first number differs from the others.
     * - 0 points otherwise.
     *
     * @return Calculated result for the line
     */
    private int calculateResult() {
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        if (sum == 2) return 10;
        if (numbers.get(0).equals(numbers.get(1)) && numbers.get(1).equals(numbers.get(2))) return 5;// all are same
        if (!numbers.get(0).equals(numbers.get(1)) && !numbers.get(0).equals(numbers.get(2)))
            return 1;// 1st number different from 2nd and 3rd
        return 0;
    }
}
