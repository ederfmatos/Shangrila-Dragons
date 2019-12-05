package com.spectron.dragoesdeshangrila.interfaces;

import java.util.List;
import java.util.Random;

public interface LevelOptionsInterface {

    List<Integer> whenRemoveOneDragon();

    List<Integer> whenRemoveTwoDragons();

    List<Integer> whenRemoveThreeDragons();

    default int get(int number) {
        if(whenRemoveOneDragon().contains(number)) {
            return 1;
        }

        if(whenRemoveTwoDragons().contains(number)) {
            return 2;
        }

        if(whenRemoveThreeDragons().contains(number)) {
            return 3;
        }

        return new Random().nextInt(3) + 1;
     }


}
