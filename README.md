# Zeus

Zeus is a multi-purpose JDA command framework designed to make creating bots easier!

# Contents
- [Setup](#setup)
- [Commands](#commands)
    - [Creating a Slash Command](#creating-a-slash-command)
    - [Creating a Message Command](#creating-a-message-command)
    - [Creating a User Context Command](#creating-a-user-context-command)
    - [Creating a Message Context Command](#creating-a-message-context-command)
    - [Creating a Slash Command Group](#creating-a-slash-command-group)
    - [Registering commands](#registering-commands)
- [Components](#components)
    - [AutoButton](#autobutton)
    - [AutoEmbed](#autoembed)
    - [AutoModal](#automodal)
    - [AutoSelectMenu](#autoselectmenu)

## Setup

Setting up Zeus is done via a setup function. Once you have created your discord bot, setup Zeus with the `setup()` method.

**Example:**
```kotlin
val jda = JDABuilder.createDefault("token").build()
Zeus.setup(
    api = jda,
    globalMessagePrefix = "!",
    prefixGuildMap = mutableMapOf(
        GUILD_ID_HERE to "."
    ),
    guilds = listOf(
        GUILD_ID_HERE
    )
)
```

Let's break that down:
 - `api`: Your JDA instance
 - `globalMessagePrefix`: For message commands, the prefix that is accepted bot-wide, unless another prefix is set for a guild
 - `prefixGuildMap`: A map of guild (server) IDs to their own prefix. This will bypass`globalMessagePrefix`
 - `guilds` : A list of guild (server) IDs to set the guild-only commands to. (See [Registering Commands](#registering-commands))


## Commands

Zeus creates and registers commands via objects and annotations (`@Annotation`). To create a command, you must create an object and inherrit from `Command`:

```kotlin
import org.bspoones.zeus.command.Command

object MyCommand: Command() {}
```

### Registering commands

Once you have created your command object, head over to your main bot declaration class and add the object to the command registry:

```kotlin
// See setup guide above

CommandRegistry.registerCommands(
    MyCommand::class,
    guildOnly = true
)
```

Let's break that down:
- `MyCommand::class` class of your command, this is a `vararg` input so you can add as many command objects as you want, seperated by a comma (`,`)
- `guildOnly` default to `false`, this will decide if all commands you make are guild-only (they will register at a guild level). This is really useful for personal bots or for testing purposes, as commands are registered instantly. Set to `false` (or don't set it at all) to register all commands globally. This may take a few minutes to register.

### Creating a Slash Command

Slash commands are registered via the `@SlashCommand` annotation. This will register and map the function to a slash command:

```kotlin
object MyCommand: Command() {
    
    @SlashCommand("ping","Send a pong to the chat")
    fun onNameCommand(
        event: SlashCommandInteractionEvent
    ) {
        event.reply("Pong!").queue()
    }
}
```



### Creating a Message Command

### Creating a User Context Command

### Creating a Message Context Command

### Creating a Slash Command Group

### GuildOnly

### NSFW

### Command Choices

#### StringChoice
#### DoubleChoice
#### LongChoice

### Variable Command Choices

#### VariableStringChoice
#### VariableDoubleChoice
#### VariableLongChoice

## Components

### AutoButton

### AutoEmbed

### AutoModal

### AutoSelectMenu



Made by BSpoones 🥄


