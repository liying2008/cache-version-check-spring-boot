/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

class CacheVersionMismatchException(
    val checkState: VersionCheckState? = null,
    message: String? = null
) : IllegalStateException(message ?: buildMessage(checkState)) {

    companion object {
        private fun buildMessage(state: VersionCheckState?): String {
            if (state == null) {
                return "Cache version check failed: unknown state"
            }
            return "Cache version mismatch for ${state.trigger.name}: " +
                "expected ${state.expectedVersion}, " +
                "existing ${state.actualVersion}"
        }
    }
}
