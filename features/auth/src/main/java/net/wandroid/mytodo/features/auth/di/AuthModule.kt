package net.wandroid.mytodo.features.auth.di

import net.wandroid.mytodo.features.auth.AuthInterceptorProvider
import net.wandroid.mytodo.features.auth.UserAuthManager
import net.wandroid.mytodo.features.auth.data.firebase.FirebaseAuthManager
import net.wandroid.mytodo.features.auth.data.firebase.FirebaseAuthProvider
import net.wandroid.mytodo.features.auth.data.firebase.FirebaseAuthProviderImpl
import net.wandroid.mytodo.features.auth.data.remote.AuthInterceptorProviderImpl
import net.wandroid.mytodo.features.auth.data.remote.RetrofitAuthInterceptor
import net.wandroid.mytodo.features.auth.data.repositories.FirebaseAuthRepository
import net.wandroid.mytodo.features.auth.domain.managers.UserAuthManagerImpl
import net.wandroid.mytodo.features.auth.domain.model.repositories.AuthRepository
import net.wandroid.mytodo.features.auth.domain.user_cases.CreateUserUseCase
import net.wandroid.mytodo.features.auth.domain.user_cases.GetLoginStateUseCase
import net.wandroid.mytodo.features.auth.domain.user_cases.GetUserInfoUseCase
import net.wandroid.mytodo.features.auth.domain.user_cases.LoginUserUseCase
import net.wandroid.mytodo.features.auth.domain.user_cases.LogoutUserUseCase
import net.wandroid.mytodo.features.auth.presentation.create_user_screen.CreateUserViewModel
import net.wandroid.mytodo.features.auth.presentation.login_screen.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {

    single<FirebaseAuthProvider> {
        FirebaseAuthProviderImpl()
    }

    singleOf(::FirebaseAuthManager)

    single<AuthRepository> {
        FirebaseAuthRepository(get())
    }

    factory<AuthInterceptorProvider> {
        val authInterceptor = RetrofitAuthInterceptor(get())
        AuthInterceptorProviderImpl(authInterceptor)
    }

    factoryOf(::GetUserInfoUseCase)

    factoryOf(::GetLoginStateUseCase)
    single<UserAuthManager> {
        UserAuthManagerImpl(get(), get(), get())
    }

    factoryOf(::CreateUserUseCase)
    viewModelOf(::CreateUserViewModel)

    factoryOf(::LoginUserUseCase)
    factoryOf(::LogoutUserUseCase)
    viewModelOf(::LoginViewModel)
}