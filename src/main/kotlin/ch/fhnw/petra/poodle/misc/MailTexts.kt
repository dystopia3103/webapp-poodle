package ch.fhnw.petra.poodle.misc

sealed interface MailTexts {
    val subject: String
    val text: String

    data object Invitation : MailTexts {
        override val subject: String
            get() = "Poodle - Invitation to participate: {eventName}"

        override val text: String
            get() = """
                <html>
                <body>
                    <p>Hi there, this is Poodle.</p><br/>
                    <p>You have been invited to participate in an event: {eventName}.</p>
                    <p>Are you in?! Great. Click on this link to vote, when you can participate:</p><br/>
                    
                    <p><a href="{eventLink}">{eventLink}</a></p>
                </body>
            </html>     
            """.trimIndent()
    }

    data object Reminder : MailTexts {
        override val subject: String
            get() = "Poodle - Reminder to participate: {eventName}"

        override val text: String
            get() = """
                <html>
                <body>
                    <p>Hi there, this is Poodle again.</p><br/>
                    <p>Just wanted to remind you you have been invited to participate in an event: {eventName}.</p>
                    <p>Are you in?! Great. Click on this link to vote, when you can participate:</p><br/>
                    
                    <p><a href="{eventLink}">{eventLink}</a></p>
                </body>
            </html>     
            """.trimIndent()
    }

    data object Meeting : MailTexts {
        override val subject: String
            get() = "Poodle - The meeting is fixed: {eventName}"

        override val text: String
            get() = """
                <html>
                <body>
                    <p>Hi there, this is Poodle.</p><br/>
                    <p>The organizer just fixed a date for the meeting: {eventName}.</p>
                    <p>You will find all the info under this link:</p><br/>                   
                    
                    <p><a href="{eventLink}">{eventLink}</a></p>
                </body>
            </html>     
            """.trimIndent()
    }

    fun String.eventName(eventName: String): String {
        return this.replace("{eventName}", eventName)
    }

    fun String.eventLink(eventLink: String): String {
        return this.replace("{eventLink}", eventLink)
    }

}