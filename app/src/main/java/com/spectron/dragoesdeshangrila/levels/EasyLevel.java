package com.spectron.dragoesdeshangrila.levels;

import com.spectron.dragoesdeshangrila.interfaces.LevelOptionsInterface;

import java.util.Collections;
import java.util.List;

public class EasyLevel implements LevelOptionsInterface {

    @Override
    public List<Integer> whenRemoveOneDragon() {
        return Collections.singletonList(1);
    }

    @Override
    public List<Integer> whenRemoveTwoDragons() {
        return Collections.singletonList(2);
    }

    @Override
    public List<Integer> whenRemoveThreeDragons() {
        return Collections.singletonList(3);
    }

}
