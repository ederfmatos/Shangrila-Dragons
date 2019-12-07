package com.spectron.dragoesdeshangrila.enumerations;

import androidx.annotation.NonNull;

import com.spectron.dragoesdeshangrila.interfaces.LevelOptionsInterface;
import com.spectron.dragoesdeshangrila.levels.EasyLevel;
import com.spectron.dragoesdeshangrila.levels.HardLevel;
import com.spectron.dragoesdeshangrila.levels.MediumLevel;

public enum LevelEnum {

    EASY(new EasyLevel()),
    MEDIUM(new MediumLevel()),
    HARD(new HardLevel());

    private LevelOptionsInterface action;

    LevelEnum(LevelOptionsInterface action) {
        this.action = action;
    }

    public LevelOptionsInterface getAction() {
        return action;
    }

    @NonNull
    @Override
    public String toString() {
        return name();
    }
}
