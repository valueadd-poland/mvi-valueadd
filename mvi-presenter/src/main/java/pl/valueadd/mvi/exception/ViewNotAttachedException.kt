package pl.valueadd.mvi.exception

class ViewNotAttachedException internal constructor() :
    RuntimeException("View was called before that has been attached to presenter")