package pl.valueadd.mvi.exception

import java.lang.RuntimeException

internal class ViewWasNotDetachedException : RuntimeException("Detach previous view first.")