package com.bank.transfer.domain

import android.util.Log

class BankLog {
    companion object {
        private const val LOG_TAG = "BankLog"
        private const val IS_LOGGING_ENABLED = true

        internal fun v(msg: String) {
            v(LOG_TAG, msg)
        }

        internal fun v(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.v(tag, msg)
        }

        internal fun d(msg: String) {
            d(LOG_TAG, msg)
        }

        internal fun d(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.d(tag, msg)
        }

        internal fun i(msg: String) {
            i(LOG_TAG, msg)
        }

        internal fun i(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.i(tag, msg)
        }

        internal fun w(msg: String) {
            w(LOG_TAG, msg)
        }

        internal fun w(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.w(tag, msg)
        }

        internal fun e(msg: String) {
            e(LOG_TAG, msg)
        }

        internal fun e(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.e(tag, msg)
        }
    }
}
