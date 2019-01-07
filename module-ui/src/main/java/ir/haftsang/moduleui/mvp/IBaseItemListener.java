package ir.haftsang.moduleui.mvp;


import android.support.annotation.Nullable;

/**
 * Created by p.kokabi on 3/13/2018.
 */

public interface IBaseItemListener {
    void onClick(@Nullable Enum anEnum, @Nullable Integer position, @Nullable Object model, @Nullable Integer viewId);
}