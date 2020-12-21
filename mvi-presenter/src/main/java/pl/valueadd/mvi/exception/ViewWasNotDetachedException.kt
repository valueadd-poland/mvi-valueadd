package pl.valueadd.mvi.exception

class ViewWasNotDetachedException internal constructor() :
    RuntimeException("Detach previous view first.")