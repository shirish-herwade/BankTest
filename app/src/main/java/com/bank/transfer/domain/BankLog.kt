package com.bank.transfer.domain

import android.util.Log

class BankLog {
    companion object {
        private const val LOG_TAG = "BankLog"
        private const val IS_LOGGING_ENABLED = true

        fun v(msg: String) {
            v(LOG_TAG, msg)
        }

        fun v(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.v(tag, msg)
        }

        fun d(msg: String) {
            d(LOG_TAG, msg)
        }

        fun d(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.d(tag, msg)
        }

        fun i(msg: String) {
            i(LOG_TAG, msg)
        }

        fun i(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.i(tag, msg)
        }

        fun w(msg: String) {
            w(LOG_TAG, msg)
        }

        fun w(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.w(tag, msg)
        }

        fun e(msg: String) {
            e(LOG_TAG, msg)
        }

        fun e(tag: String, msg: String) {
            if (IS_LOGGING_ENABLED)
                Log.e(tag, msg)
        }
    }
}
