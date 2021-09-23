import co.touchlab.kermit.Kermit
import com.surrus.common.di.CommonModule
import com.surrus.common.repository.PeopleInSpaceRepositoryInterface
import com.surrus.common.repository.PlatformModuleImpl
import kotlinx.coroutines.InternalCoroutinesApi
import react.child
import react.createContext
import react.dom.render

object AppDependencies {
    val module = CommonModule(PlatformModuleImpl())
    val repository: PeopleInSpaceRepositoryInterface get() = module.repo()
    val logger: Kermit get() = module.kermit
}

val AppDependenciesContext = createContext<AppDependencies>()

@InternalCoroutinesApi
fun main() {
    render(kotlinx.browser.document.getElementById("root")) {
        AppDependenciesContext.Provider(AppDependencies) {
            child(App)
        }
    }
}