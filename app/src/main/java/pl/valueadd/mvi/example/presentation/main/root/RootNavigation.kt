package pl.valueadd.mvi.example.presentation.main.root

import androidx.annotation.IntDef
import pl.valueadd.mvi.example.presentation.main.root.RootNavigation.Type.FIRST
import pl.valueadd.mvi.example.presentation.main.root.RootNavigation.Type.SECOND
import pl.valueadd.mvi.example.presentation.main.root.RootNavigation.Type.THIRD

@IntDef(
    value = [
        FIRST,
        SECOND,
        THIRD
    ]
)
@Retention(AnnotationRetention.SOURCE)
annotation class RootNavigation {

    companion object Type {

        const val FIRST = 0

        const val SECOND = 1

        const val THIRD = 2
    }
}