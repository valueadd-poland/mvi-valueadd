package pl.valueadd.mvi.exception

class ViewNotAttachedException :
    RuntimeException("View was called before that has been attached to presenter")