package com.aslanovaslan.mynotesapplication.ui.internal

import com.aslanovaslan.mynotesapplication.db.Note

class EventbusSender {
    internal class SendUpdatedNote(val note:Note)
}