package com.hdrescuer.hdrescuer.common;

public interface OnSimpleDialogClick {

    public abstract void onPositiveButtonClick(String description);

    public abstract void onPositiveButtonClick();

    public abstract void onNegativeButtonClick();
}
