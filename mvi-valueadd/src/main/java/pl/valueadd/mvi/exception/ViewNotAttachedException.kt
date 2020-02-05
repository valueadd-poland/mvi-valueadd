package pl.valueadd.mvi.exception

internal class ViewNotAttachedException :
    RuntimeException("View was called before that has been attached to presenter")