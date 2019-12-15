package com.spectron.dragoesdeshangrila.enumerations;

import androidx.annotation.NonNull;

import com.spectron.dragoesdeshangrila.R;
import com.spectron.dragoesdeshangrila.interfaces.LevelOptionsInterface;
import com.spectron.dragoesdeshangrila.levels.EasyLevel;
import com.spectron.dragoesdeshangrila.levels.HardLevel;
import com.spectron.dragoesdeshangrila.levels.MediumLevel;

public enum LevelEnum {

    EASY(new EasyLevel(), R.string.easy),
    MEDIUM(new MediumLevel(), R.string.medium),
    HARD(new HardLevel(), R.string.hard);

    private LevelOptionsInterface action;
    private int name;

    LevelEnum(LevelOptionsInterface action, int name) {
        this.action = action;
        this.name = name;
    }

    public LevelOptionsInterface getAction() {
        return action;
    }

    public int getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name();
    }
}
