package com.spectron.dragoesdeshangrila.levels;

import com.spectron.dragoesdeshangrila.interfaces.LevelOptionsInterface;

import java.util.Arrays;
import java.util.List;

public class HardLevel implements LevelOptionsInterface {

    @Override
    public List<Integer> whenRemoveOneDragon() {
        return Arrays.asList(1, 5, 9, 13);
    }

    @Override
    public List<Integer> whenRemoveTwoDragons() {
        return Arrays.asList(2, 6, 10, 14);
    }

    @Override
    public List<Integer> whenRemoveThreeDragons() {
        return Arrays.asList(3, 7, 11);
    }

}
