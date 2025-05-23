package ru.tinkoff.kora.ksp.common

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier
import ru.tinkoff.kora.ksp.common.CommonClassNames.isFlow
import ru.tinkoff.kora.ksp.common.CommonClassNames.isFlux
import ru.tinkoff.kora.ksp.common.CommonClassNames.isCompletionStage
import ru.tinkoff.kora.ksp.common.CommonClassNames.isDeferred
import ru.tinkoff.kora.ksp.common.CommonClassNames.isFuture
import ru.tinkoff.kora.ksp.common.CommonClassNames.isList
import ru.tinkoff.kora.ksp.common.CommonClassNames.isMono
import ru.tinkoff.kora.ksp.common.CommonClassNames.isPublisher
import ru.tinkoff.kora.ksp.common.CommonClassNames.isVoid
import ru.tinkoff.kora.ksp.common.FunctionUtils.isDeferred
import ru.tinkoff.kora.ksp.common.FunctionUtils.isVoid

object FunctionUtils {

    fun KSFunctionDeclaration.isFlux() = returnType!!.resolve().isFlux()
    fun KSFunctionDeclaration.isMono() = returnType!!.resolve().isMono()
    fun KSFunctionDeclaration.isPublisher() = returnType!!.resolve().isPublisher()
    fun KSFunctionDeclaration.isFlow() = returnType!!.resolve().isFlow()
    fun KSFunctionDeclaration.isFuture() = returnType!!.resolve().isFuture()
    fun KSFunctionDeclaration.isCompletionStage() = returnType!!.resolve().isCompletionStage()
    fun KSFunctionDeclaration.isList() = returnType!!.resolve().isList()
    fun KSFunctionDeclaration.isSuspend() = modifiers.contains(Modifier.SUSPEND)
    fun KSFunctionDeclaration.isDeferred() = returnType!!.isDeferred()
    fun KSFunctionDeclaration.isVoid() = returnType!!.isVoid()
    fun KSFunctionDeclaration.isFlowVoid() = isFlow() && returnType!!.resolve().arguments.firstOrNull()?.type?.isVoid() ?: false
    fun KSFunctionDeclaration.isMonoVoid() = isMono() && returnType!!.resolve().arguments.firstOrNull()?.type?.isVoid() ?: false
    fun KSFunctionDeclaration.isCompletionStageVoid() = isCompletionStage() && returnType!!.resolve().arguments.firstOrNull()?.type?.isVoid() ?: false
}
